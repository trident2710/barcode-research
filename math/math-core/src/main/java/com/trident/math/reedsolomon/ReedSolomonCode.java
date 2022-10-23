package com.trident.math.reedsolomon;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.trident.math.PolyUtil;
import com.trident.math.field.GFP;
import com.trident.math.field.GFPElement;
import com.trident.math.matrix.FieldMatrixUtil;
import com.trident.math.reedsolomon.CorrectionResult.CorrectionStatus;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.trident.math.PolyUtil.degree;
import static com.trident.math.PolyUtil.multiplyPolynomials;
import static com.trident.math.PolyUtil.subtractPolynomials;
import static com.trident.math.field.GFPSimplePrimitiveElementCalculator.primitiveElement;
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

    public CorrectionResult decode(FieldMatrix<GFPElement> encoded) {
        var erasureLocators = findErasureLocators(encoded);
        int erasureLocatorsCount = erasureLocators.size();
        if (erasureLocatorsCount > controlDigitsCount) {
            return ImmutableCorrectionResult.of(CorrectionStatus.TOO_MUCH_ERASURE);
        }

        var erasureLocatorsPolynomial = calculateErasureLocatorsPolynomial(erasureLocators);

        int correctErrorsCount = correctErrorsCount(erasureLocatorsCount);

        var syndrome = calculateSyndrome(encoded);

        var syndromePolynomial = createSyndromePolynomial(syndrome);

        var modifiedSyndromePolynomial = calculateModifiedSyndromePolynomial(erasureLocatorsPolynomial, syndromePolynomial);

        var errorSyndrome = calculateErrorSyndrome(modifiedSyndromePolynomial, erasureLocatorsCount);

        var errorLocatorsPolynomial = calculateErrorLocatorsPolynomial(errorSyndrome, erasureLocatorsCount);


        return null;
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
    FieldMatrix<GFPElement> calculateModifiedSyndromePolynomial
            (FieldMatrix<GFPElement> erasureLocatorsPolynomial, FieldMatrix<GFPElement> syndromePolynomial) {
        int moduloPower = controlDigitsCount + 1;
        var poly = multiplyPolynomials(syndromePolynomial, erasureLocatorsPolynomial);
        var moduled = polyModulo(poly, moduloPower);
        moduled.setEntry(0, 0, generatorField.getZero());
        return moduled;
    }

    private FieldMatrix<GFPElement> polyModulo(FieldMatrix<GFPElement> poly, int moduloPower) {
        return FieldMatrixUtil.matrixRow(Arrays.copyOfRange(poly.getRow(0), 0, moduloPower));
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
    FieldMatrix<GFPElement> calculateErasureLocatorsPolynomial(List<GFPElement> erasureLocators) {
        return erasureLocators.stream()
                .map(locator -> FieldMatrixUtil.matrixRow(generatorField.getOne(), locator.negate()))
                .reduce(PolyUtil::multiplyPolynomials)
                .orElseThrow();
    }

    @VisibleForTesting
    List<GFPElement> findErasureLocators(FieldMatrix<GFPElement> message) {
        var erasureLocators = new ArrayList<GFPElement>();
        for (int i = 0; i < message.getColumnDimension(); i++) {
            var elem = message.getEntry(0, i);
            if (elem.equals(generatorField.getZero())) {
                erasureLocators.add(generatorField.pow(generatorFieldPrimitiveElement, i));
            }
        }
        return erasureLocators;
    }
}
