package com.trident.math.reedsolomon;

import com.google.common.annotations.VisibleForTesting;
import com.trident.math.PolyUtil;
import com.trident.math.field.FiniteFieldEquation;
import com.trident.math.field.GFP;
import com.trident.math.field.GFPElement;
import com.trident.math.field.GFPUtil;
import com.trident.math.matrix.FieldMatrixUtil;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.util.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.trident.math.PolyUtil.*;
import static com.trident.math.PolyUtil.degree;

public final class ReedSolomonUtil {

    public static List<GFPElement> calculateSyndrome(int controlDigitsCount, GFP generatorField, GFPElement generatorFieldPrimitiveElement, FieldMatrix<GFPElement> encoded) {
        return IntStream.range(1, controlDigitsCount + 1)
                .boxed()
                .map(i -> ReedSolomonUtil.calculateSyndromeElement(generatorField, generatorFieldPrimitiveElement, i, encoded))
                .collect(Collectors.toList());
    }

    public static GFPElement calculateSyndromeElement(GFP generatorField, GFPElement generatorFieldPrimitiveElement, int index, FieldMatrix<GFPElement> encoded) {
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

    public static FieldMatrix<GFPElement> createSyndromePolynomial(GFP generatorField, List<GFPElement> syndrome) {
        var withLeadingOne = Stream.concat(Stream.of(generatorField.getOne()), syndrome.stream())
                .toList()
                .toArray(new GFPElement[]{});
        return FieldMatrixUtil.matrixRow(withLeadingOne);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static FieldMatrix<GFPElement> calculateModifiedSyndromePolynomial(int controlDigitsCount, GFP generatorField,
                                                                              Optional<FieldMatrix<GFPElement>> erasureLocatorsPolynomial, FieldMatrix<GFPElement> syndromePolynomial) {
        int moduloPower = controlDigitsCount + 1;
        var poly = erasureLocatorsPolynomial
                .map(it -> multiplyPolynomials(syndromePolynomial, it))
                .orElse(syndromePolynomial);
        var moduled = polyModulo(generatorField, poly, moduloPower);
        moduled.setEntry(0, 0, generatorField.getZero());
        return moduled;
    }

    public static FieldMatrix<GFPElement> polyModulo(GFP generatorField, FieldMatrix<GFPElement> poly, int moduloPower) {
        int maxNonZeroElementIndex = maxNonZeroElementIndexFrom(generatorField, poly, moduloPower - 1);
        return FieldMatrixUtil.matrixRow(Arrays.copyOfRange(poly.getRow(0),
                0, Math.min(moduloPower, Math.max(1, maxNonZeroElementIndex + 1))));
    }

    public static int maxNonZeroElementIndexFrom(GFP generatorField, FieldMatrix<GFPElement> poly, int start) {
        for (int i = start; i >= 0; i--) {
            if (!poly.getEntry(0, i).equals(generatorField.getZero())) {
                return i;
            }
        }
        return -1;
    }

    public static FieldMatrix<GFPElement> calculateErrorSyndrome(FieldMatrix<GFPElement> modifiedSyndromePoly,
                                                                 int erasureLocatorsCount) {
        return FieldMatrixUtil.matrixRow(Arrays.copyOfRange(modifiedSyndromePoly.getRow(0),
                modifiedSyndromePoly.getColumnDimension() - erasureLocatorsCount * 2,
                modifiedSyndromePoly.getColumnDimension()));
    }

    // Berleckamp-Messi
    public static Optional<FieldMatrix<GFPElement>> calculateErrorLocatorsPolynomial(GFP generatorField, FieldMatrix<GFPElement> errorSyndrome, int xi) {
        var SIG = poly(generatorField, 1);
        int h = 0;
        int F = 0;
        FieldMatrix<GFPElement> T;
        FieldMatrix<GFPElement> L = poly(generatorField, 1);


        do {
            h++;
            GFPElement dh = generatorField.getZero();

            for (int j = 0; j <= F; j++) {
                GFPElement sigi = SIG.getColumnDimension() <= j ? generatorField.getZero() : SIG.getEntry(0, j);
                dh = dh.add(sigi.multiply(errorSyndrome.getEntry(0, h - j - 1)));
            }

            if (dh.isZero()) {
                L = multiplyPolynomials(poly(generatorField, 0, 1), L);
            } else {
                long[] elems1 = new long[]{0, dh.digitalRepresentation()};
                var dhx = poly(generatorField, elems1);
                var product = multiplyPolynomials(dhx, L);
                T = subtractPolynomials(SIG, product);

                if (2 * F <= h - 1) {
                    long[] elems = new long[]{dh.reciprocal().digitalRepresentation()};
                    L = multiplyPolynomials(poly(generatorField, elems), SIG);
                    SIG = T;
                    F = h - F;
                } else {
                    SIG = T;
                    L = multiplyPolynomials(poly(generatorField, 0, 1), L);
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


    public static List<GFPElement> calculateErrorLocators(FieldMatrix<GFPElement> errorLocatorsPolynomial) {
        var equationRoots = FiniteFieldEquation.solveEquation(errorLocatorsPolynomial);
        return equationRoots.stream()
                .map(GFPElement::reciprocal)
                .collect(Collectors.toList());
    }

    public static List<GFPElement> findErasureLocators(GFP generatorField, GFPElement generatorFieldPrimitiveElement, List<Integer> erasurePositions) {
        var erasureLocators = new ArrayList<GFPElement>();
        for (Integer erasurePosition : erasurePositions) {
            erasureLocators.add(generatorField.pow(generatorFieldPrimitiveElement, erasurePosition));
        }
        return erasureLocators;
    }

    public static Optional<FieldMatrix<GFPElement>> calculateErasureLocatorsPolynomial(GFP generatorField, List<GFPElement> erasureLocators) {
        return erasureLocators.stream()
                .map(locator -> FieldMatrixUtil.matrixRow(generatorField.getOne(), locator.negate()))
                .reduce(PolyUtil::multiplyPolynomials);
    }

    public static GFPElement calculateMutationDiffs(GFP generatorField, GFPElement mutationLocator, List<GFPElement> mutationLocators) {
        var otherMutationLocators = new ArrayList<>(mutationLocators);
        otherMutationLocators.remove(mutationLocator);
        return otherMutationLocators.stream()
                .map(l -> generatorField.getOne().subtract(mutationLocator.reciprocal().multiply(l)))
                .reduce(GFPElement::multiply)
                .orElseThrow();
    }

    public static GFPElement calculateMutationValuesPolyValueFor(FieldMatrix<GFPElement> mutationValuesPoly, GFPElement mutationLocator) {
        return FiniteFieldEquation.calculatePolyValue(mutationValuesPoly, mutationLocator.reciprocal());
    }

    public static GFPElement calculateMutationValue(GFP generatorField, FieldMatrix<GFPElement> mutationValuesPoly, List<GFPElement> mutationLocators, GFPElement locator) {
        return calculateMutationValuesPolyValueFor(mutationValuesPoly, locator)
                .divide(calculateMutationDiffs(generatorField, locator, mutationLocators));
    }

    public static List<Pair<Integer, GFPElement>> calculateCorrectionValues(List<GFPElement> mutationValues, List<GFPElement> mutationLocators) {
        var correctionPositions = mutationLocators.stream()
                .map(GFPUtil::powerOfPrimitive)
                .toList();

        var result = new ArrayList<Pair<Integer, GFPElement>>();
        for (int i = 0; i < correctionPositions.size(); i++) {
            result.add(Pair.create(correctionPositions.get(i), mutationValues.get(i)));
        }

        return result;
    }

    public static List<GFPElement> calculateMutationValues(GFP generatorField, FieldMatrix<GFPElement> mutationValuesPoly, List<GFPElement> mutationLocators) {
        return mutationLocators.stream()
                .map(locator -> calculateMutationValue(generatorField, mutationValuesPoly, mutationLocators, locator))
                .collect(Collectors.toList());
    }

    public static List<GFPElement> mutationLocators(List<GFPElement> erasureLocators, List<GFPElement> errorLocators) {
        return Stream.concat(erasureLocators.stream(), errorLocators.stream())
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    public static FieldMatrix<GFPElement> calculateMutationValuesPoly(int controlDigitsCount, GFP generatorField, FieldMatrix<GFPElement> errorLocatorsPoly, FieldMatrix<GFPElement> modifiedSyndromePoly) {
        var V = modifiedSyndromePoly.copy();
        V.setEntry(0, 0, generatorField.getOne());
        var sigma = errorLocatorsPoly;
        var poly = multiplyPolynomials(V, sigma);
        var power = controlDigitsCount + 1;
        return polyModulo(generatorField, poly, power);
    }

    private ReedSolomonUtil() {
    }
}
