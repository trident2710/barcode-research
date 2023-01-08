package com.trident.math.reedsolomon;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.trident.math.PolyUtil;
import com.trident.math.field.FiniteFieldEquation;
import com.trident.math.field.GFP;
import com.trident.math.field.GFPElement;
import com.trident.math.field.GFPUtil;
import com.trident.math.matrix.FieldMatrixUtil;
import com.trident.math.reedsolomon.CorrectionResult.CorrectionStatus;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.util.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.trident.math.PolyUtil.*;
import static com.trident.math.field.GFPUtil.primitiveElement;
import static com.trident.math.matrix.FieldMatrixUtil.matrixRowOfValue;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;

public class ReedSolomonCode {

    private final FieldMatrix<GFPElement> generatorPolynomial;

    private final GFP generatorField;
    private final GFPElement generatorFieldPrimitiveElement;
    private final int controlDigitsCount;

    public ReedSolomonCode(FieldMatrix<GFPElement> generatorPolynomial) {
        Preconditions.checkArgument(generatorPolynomial.getRowDimension() == 1);
        Preconditions.checkArgument(generatorPolynomial.getColumnDimension() > 1);
        this.generatorPolynomial = generatorPolynomial;
        this.generatorField = (GFP) generatorPolynomial.getField();
        this.generatorFieldPrimitiveElement = primitiveElement(generatorField);
        this.controlDigitsCount = generatorPolynomial.getColumnDimension() - 1;
    }

    public FieldMatrix<GFPElement> encode(FieldMatrix<GFPElement> message) {
        return PolyUtil.multiplyPolynomials(message, generatorPolynomial);
    }

    public CorrectionResult decode(FieldMatrix<GFPElement> message, List<Integer> erasurePositions) {
        var erasureLocators = findErasureLocators(erasurePositions);
        int erasureLocatorsCount = erasureLocators.size();
        if (erasureLocatorsCount > controlDigitsCount) {
            return ImmutableCorrectionResult.of(CorrectionStatus.TOO_MUCH_ERASURE, message, Optional.empty());
        }

        var erasureLocatorsPolynomial = calculateErasureLocatorsPolynomial(erasureLocators);

        int errorsMaxCount = (controlDigitsCount - erasureLocatorsCount) / 2;

        var syndrome = calculateSyndrome(message);

        boolean isZeroSyndrome = syndrome.stream()
                .allMatch(it -> it.equals(generatorField.getZero()));

        if (isZeroSyndrome) {
            return ImmutableCorrectionResult.of(CorrectionStatus.NO_ERROR, message, Optional.empty());
        }

        var syndromePolynomial = createSyndromePolynomial(syndrome);

        var modifiedSyndromePolynomial = calculateModifiedSyndromePolynomial(erasureLocatorsPolynomial, syndromePolynomial);

        var errorSyndrome = calculateErrorSyndrome(modifiedSyndromePolynomial, errorsMaxCount);

        var errorLocatorsPolyOptional = calculateErrorLocatorsPolynomial(errorSyndrome, errorsMaxCount);

        if (errorLocatorsPolyOptional.isEmpty()) {
            return ImmutableCorrectionResult.of(CorrectionStatus.MORE_THAN_XI_ERRORS, message, Optional.empty());
        }

        var errorLocatorsPoly = errorLocatorsPolyOptional.get();

        var errorLocators = calculateErrorLocators(errorLocatorsPoly);

        var mutationValuesPoly = calculateMutationValuesPoly(errorLocatorsPoly, modifiedSyndromePolynomial);

        var mutationLocators = mutationLocators(erasureLocators, errorLocators);

        var mutationValues = calculateMutationValues(mutationValuesPoly, mutationLocators);

        var correctionValues = calculateCorrectionValues(mutationValues, mutationLocators);

        var correction = matrixRowOfValue(generatorField.getZero(), message.getColumnDimension());

        correctionValues.forEach(it -> correction.setEntry(0, it.getKey(), it.getValue()));

        return ImmutableCorrectionResult.of(CorrectionStatus.ERROR_CORRECTED, message, Optional.of(correction));
    }

    @VisibleForTesting
    List<GFPElement> calculateErrorLocators(FieldMatrix<GFPElement> errorLocatorsPolynomial) {
        var equationRoots = FiniteFieldEquation.solveEquation(errorLocatorsPolynomial);
        return equationRoots.stream()
                .map(GFPElement::reciprocal)
                .collect(Collectors.toList());
    }

    // Berleckamp-Messi
    @VisibleForTesting
    Optional<FieldMatrix<GFPElement>> calculateErrorLocatorsPolynomial(FieldMatrix<GFPElement> errorSyndrome, int xi) {
        var SIG = poly(1);
        int h = 0;
        int F = 0;
        FieldMatrix<GFPElement> T;
        FieldMatrix<GFPElement> L = poly(1);


        do {
            h++;
            GFPElement dh = generatorField.getZero();

            for (int j = 0; j <= F; j++) {
                GFPElement sigi = SIG.getColumnDimension() <= j ? generatorField.getZero() : SIG.getEntry(0, j);
                dh = dh.add(sigi.multiply(errorSyndrome.getEntry(0, h - j - 1)));
            }

            if (dh.isZero()) {
                L = multiplyPolynomials(poly(0, 1), L);
            } else {
                var dhx = poly(0, dh.digitalRepresentation());
                var product = multiplyPolynomials(dhx, L);
                T = subtractPolynomials(SIG, product);

                if (2 * F <= h - 1) {
                    L = multiplyPolynomials(poly(dh.reciprocal().digitalRepresentation()), SIG);
                    SIG = T;
                    F = h - F;
                } else {
                    SIG = T;
                    L = multiplyPolynomials(poly(0, 1), L);
                }

            }

            if (h == 2 * xi) {
                if (degree(SIG) == F) {
                    return Optional.of(SIG);
                } else {
                    return Optional.empty();
                }
            }
        } while (true);
    }

    private FieldMatrix<GFPElement> poly(long... elems) {
        return toFieldMatrixRow(elems, generatorField);
    }

    @VisibleForTesting
    FieldMatrix<GFPElement> calculateErrorSyndrome(FieldMatrix<GFPElement> modifiedSyndromePoly,
                                                   int erasureLocatorsCount) {
        return FieldMatrixUtil.matrixRow(Arrays.copyOfRange(modifiedSyndromePoly.getRow(0),
                modifiedSyndromePoly.getColumnDimension() - erasureLocatorsCount * 2,
                modifiedSyndromePoly.getColumnDimension()));
    }

    @VisibleForTesting
    FieldMatrix<GFPElement> calculateModifiedSyndromePolynomial(
            Optional<FieldMatrix<GFPElement>> erasureLocatorsPolynomial, FieldMatrix<GFPElement> syndromePolynomial) {
        int moduloPower = controlDigitsCount + 1;
        var poly = erasureLocatorsPolynomial
                .map(it -> multiplyPolynomials(syndromePolynomial, it))
                .orElse(syndromePolynomial);
        var moduled = polyModulo(poly, moduloPower);
        moduled.setEntry(0, 0, generatorField.getZero());
        return moduled;
    }

    private FieldMatrix<GFPElement> polyModulo(FieldMatrix<GFPElement> poly, int moduloPower) {
        int maxNonZeroElementIndex = maxNonZeroElementIndexFrom(poly, moduloPower - 1);
        return FieldMatrixUtil.matrixRow(Arrays.copyOfRange(poly.getRow(0),
                0, Math.min(moduloPower, Math.max(1, maxNonZeroElementIndex + 1))));
    }

    private int maxNonZeroElementIndexFrom(FieldMatrix<GFPElement> poly, int start) {
        for (int i = start; i >= 0; i--) {
            if (!poly.getEntry(0, i).equals(generatorField.getZero())) {
                return i;
            }
        }
        return -1;
    }

    @VisibleForTesting
    FieldMatrix<GFPElement> createSyndromePolynomial(List<GFPElement> syndrome) {
        var withLeadingOne = Stream.concat(Stream.of(generatorField.getOne()), syndrome.stream())
                .collect(Collectors.toList())
                .toArray(new GFPElement[]{});
        return FieldMatrixUtil.matrixRow(withLeadingOne);
    }

    @VisibleForTesting
    List<GFPElement> calculateSyndrome(FieldMatrix<GFPElement> encoded) {
        return IntStream.range(1, controlDigitsCount + 1)
                .boxed()
                .map(i -> calculateSyndromeElement(i, encoded))
                .collect(Collectors.toList());
    }

    @VisibleForTesting
    GFPElement calculateSyndromeElement(int index, FieldMatrix<GFPElement> encoded) {
        List<GFPElement> products = new ArrayList<>();
        var poweredPrimitive = generatorField.pow(generatorFieldPrimitiveElement, index);
        for (int i = 0; i < encoded.getColumnDimension(); i++) {
            var elem = generatorField.mul(generatorField.pow(poweredPrimitive, i), encoded.getEntry(0, i));
            products.add(elem);
        }

        return products.stream()
                .reduce(generatorField::add)
                .orElseThrow();
    }

    @VisibleForTesting
    int correctErrorsCount(int erasureLocatorsCount) {
        return (controlDigitsCount - erasureLocatorsCount) / 2;
    }

    @VisibleForTesting
    Optional<FieldMatrix<GFPElement>> calculateErasureLocatorsPolynomial(List<GFPElement> erasureLocators) {
        return erasureLocators.stream()
                .map(locator -> FieldMatrixUtil.matrixRow(generatorField.getOne(), locator.negate()))
                .reduce(PolyUtil::multiplyPolynomials);
    }

    @VisibleForTesting
    List<GFPElement> findErasureLocators(List<Integer> erasurePositions) {
        var erasureLocators = new ArrayList<GFPElement>();
        for (int i = 0; i < erasurePositions.size(); i++) {
            erasureLocators.add(generatorField.pow(generatorFieldPrimitiveElement, erasurePositions.get(i)));
        }
        return erasureLocators;
    }


    @VisibleForTesting
    FieldMatrix<GFPElement> calculateMutationValuesPoly(FieldMatrix<GFPElement> errorLocatorsPoly, FieldMatrix<GFPElement> modifiedSyndromePoly) {
        var V = modifiedSyndromePoly.copy();
        V.setEntry(0, 0, generatorField.getOne());
        var sigma = errorLocatorsPoly;
        var poly = multiplyPolynomials(V, sigma);
        var power = controlDigitsCount + 1;
        return polyModulo(poly, power);
    }

    @VisibleForTesting
    List<GFPElement> mutationLocators(List<GFPElement> erasureLocators, List<GFPElement> errorLocators) {
        return Stream.concat(erasureLocators.stream(), errorLocators.stream())
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    @VisibleForTesting
    List<GFPElement> calculateMutationValues(FieldMatrix<GFPElement> mutationValuesPoly, List<GFPElement> mutationLocators) {
        return mutationLocators.stream()
                .map(locator -> calculateMutationValue(mutationValuesPoly, mutationLocators, locator))
                .collect(Collectors.toList());
    }

    private GFPElement calculateMutationValue(FieldMatrix<GFPElement> mutationValuesPoly, List<GFPElement> mutationLocators, GFPElement locator) {
        return calculateMutationValuesPolyValueFor(mutationValuesPoly, locator)
                .divide(calculateMutationDiffs(locator, mutationLocators));
    }

    private GFPElement calculateMutationValuesPolyValueFor(FieldMatrix<GFPElement> mutationValuesPoly, GFPElement mutationLocator) {
        return FiniteFieldEquation.calculatePolyValue(mutationValuesPoly, mutationLocator.reciprocal());
    }

    private GFPElement calculateMutationDiffs(GFPElement mutationLocator, List<GFPElement> mutationLocators) {
        var otherMutationLocators = new ArrayList<>(mutationLocators);
        otherMutationLocators.remove(mutationLocator);
        return otherMutationLocators.stream()
                .map(l -> generatorField.getOne().subtract(mutationLocator.reciprocal().multiply(l)))
                .reduce(GFPElement::multiply)
                .orElseThrow();
    }

    @VisibleForTesting
    List<Pair<Integer, GFPElement>> calculateCorrectionValues(List<GFPElement> mutationValues, List<GFPElement> mutationLocators) {
        var correctionPositions = mutationLocators.stream()
                .map(GFPUtil::powerOfPrimitive)
                .collect(Collectors.toList());

        var result = new ArrayList<Pair<Integer, GFPElement>>();
        for (int i = 0; i < correctionPositions.size(); i++) {
            result.add(Pair.create(correctionPositions.get(i), mutationValues.get(i)));
        }

        return result;
    }
}
