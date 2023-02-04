package com.trident.math.reedsolomon;

import com.trident.math.PolyUtil;
import com.trident.math.field.GFP;
import com.trident.math.field.GFPElement;
import com.trident.math.matrix.FieldMatrixUtil;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.stream.IntStream;

import static com.trident.math.field.GaloisFields.GF11;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;

public final class ReedSolomonGeneratorPolynomials {

    public static final FieldMatrix<GFPElement> GF_11_R6 = toFieldMatrixRow(new long[]{2, 8, 2, 7, 5, 6, 1}, GF11);

    public static final FieldMatrix<GFPElement> GF_59_R6 = toFieldMatrixRow(new long[]{56, 26, 42, 46, 16, 51, 1}, GFP.of(59));

    public static final FieldMatrix<GFPElement> GF_41_R6 = toFieldMatrixRow(new long[]{34, 24, 13, 34, 8, 12, 1}, GFP.of(41));


    // f(x) = (x - primitiveElement)(x-primitiveElement^2)...(x-primitiveElement^length)
    public static FieldMatrix<GFPElement> generatorPolynomial(GFPElement primitiveElement, int length) {
        return IntStream.range(1, length + 1)
                .boxed()
                .map(power -> {
                    var coef = primitiveElement.getField().pow(primitiveElement, power).negate();
                    var x = primitiveElement.getField().getOne();
                    return FieldMatrixUtil.matrixRow(coef, x);
                })
                .reduce(FieldMatrixUtil.matrixRow(primitiveElement.getField().getOne()), PolyUtil::multiplyPolynomials);

    }

    private ReedSolomonGeneratorPolynomials() {
    }
}
