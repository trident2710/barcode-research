package com.trident.math.reedsolomon;

import com.trident.math.field.GFPElement;
import org.apache.commons.math3.linear.FieldMatrix;

import static com.trident.math.field.GaloisFields.GF11;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;

public final class ReedSolomonGeneratorPolynomials {

    public static final FieldMatrix<GFPElement> GF_11_R6 = toFieldMatrixRow(new long[]{2, 8, 2, 7, 5, 6, 1}, GF11);


    private ReedSolomonGeneratorPolynomials() {
    }
}
