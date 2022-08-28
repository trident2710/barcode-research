package com.trident.math.reedsolomon;

import cc.redberry.rings.poly.univar.UnivariatePolynomialZp64;
import com.trident.math.field.GFP;
import com.trident.math.field.GFPElement;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.stream.Stream;

import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;

public class ReedSolomonCode {

    private final FieldMatrix<GFPElement> generatorPolynomial;

    public ReedSolomonCode(FieldMatrix<GFPElement> generatorPolynomial) {
        this.generatorPolynomial = generatorPolynomial;
    }

    public FieldMatrix<GFPElement> encode(FieldMatrix<GFPElement> message) {
        return multiplyPolynomials(message, generatorPolynomial);
    }

    private FieldMatrix<GFPElement> multiplyPolynomials(FieldMatrix<GFPElement> message, FieldMatrix<GFPElement> generatorPolynomial) {
        return to(from(message).multiply(from(generatorPolynomial)));
    }


    private UnivariatePolynomialZp64 from(FieldMatrix<GFPElement> message) {
        var data = Stream.of(message.getRow(0))
                .mapToLong(GFPElement::digitalRepresentation)
                .toArray();
        long modulo = ((GFP) message.getField()).prime();

        return UnivariatePolynomialZp64.create(modulo, data);
    }

    private FieldMatrix<GFPElement> to(UnivariatePolynomialZp64 poly) {
        return toFieldMatrixRow(poly.stream().toArray(), GFP.of(poly.modulus()));
    }
}
