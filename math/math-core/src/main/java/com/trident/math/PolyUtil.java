package com.trident.math;

import cc.redberry.rings.poly.univar.UnivariatePolynomialZp64;
import com.trident.math.field.GFP;
import com.trident.math.field.GFPElement;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.stream.Stream;

import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;

public final class PolyUtil {
    public static String polyToString(long[] poly) {
        if (poly.length == 1) {
            return poly[0] == 0
                    ? String.valueOf(0)
                    : coefficientToString(poly[0], 0);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = poly.length - 1; i >= 0; i--) {
            var coefficient = coefficientToString(poly[i], i);
            sb.append(coefficient);
            if (i > 0) {
                if (!coefficient.isEmpty()) {
                    sb.append(" + ");
                }
            } else {
                if (coefficient.isEmpty()) {
                    sb.replace(sb.length() - 3, sb.length(), "");
                }
            }
        }
        return sb.toString();
    }

    private static String coefficientToString(long coefficient, int exponent) {
        if (coefficient == 0) {
            return "";
        }

        switch (exponent) {
            case 0:
                return String.valueOf(coefficient);
            case 1:
                return coefficient == 1
                        ? "x"
                        : coefficient + "x";
            default:
                return coefficient == 1
                        ? "x^" + exponent
                        : coefficient + "x^" + exponent;
        }
    }

    public static FieldMatrix<GFPElement> multiplyPolynomials(FieldMatrix<GFPElement> first, FieldMatrix<GFPElement> second) {
        return to(from(first).multiply(from(second)));
    }

    public static UnivariatePolynomialZp64 from(FieldMatrix<GFPElement> poly) {
        var data = Stream.of(poly.getRow(0))
                .mapToLong(GFPElement::digitalRepresentation)
                .toArray();
        long modulo = ((GFP) poly.getField()).prime();

        return UnivariatePolynomialZp64.create(modulo, data);
    }

    public static FieldMatrix<GFPElement> to(UnivariatePolynomialZp64 poly) {
        return toFieldMatrixRow(poly.stream().toArray(), GFP.of(poly.modulus()));
    }

    private PolyUtil() {
    }
}
