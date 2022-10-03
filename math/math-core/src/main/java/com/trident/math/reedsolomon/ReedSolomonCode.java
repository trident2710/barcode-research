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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.trident.math.field.GFPSimplePrimitiveElementCalculator.primitiveElement;

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
        if (erasureLocators.size() > controlDigitsCount) {
            return ImmutableCorrectionResult.of(CorrectionStatus.TOO_MUCH_ERASURE);
        }

        var erasureLocatorsPolynomial = calculateErasureLocatorsPolynomial(erasureLocators);

        int correctErrorsCount = correctErrorsCount(erasureLocators.size());

        var syndrome = calculateSyndrome(encoded);


        return null;
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
