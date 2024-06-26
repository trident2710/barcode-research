package com.trident.math;

import cc.redberry.rings.poly.univar.UnivariatePolynomialZp64;
import com.google.common.base.Preconditions;
import com.trident.math.field.GFP;
import com.trident.math.field.GFPElement;
import com.trident.math.matrix.FieldMatrixUtil;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.util.Pair;

import java.util.Optional;
import java.util.stream.Stream;

import static com.trident.math.matrix.FieldMatrixUtil.isZero;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;

public final class PolyUtil {

    public static FieldMatrix<GFPElement> poly(GFP generatorField, long... elems) {
        return toFieldMatrixRow(elems, generatorField);
    }

    public static long[] stringToPoly(String polynomial) {
        var terms = polynomial.replaceAll("\\s+", "").split("\\+");
        long[] coefficients = new long[getMaxDegree(terms) + 1];
        Stream.of(terms).forEach(term -> coefficients[getDegree(term)] = getCoefficient(term));

        return coefficients;
    }

    private static int getMaxDegree(String[] terms) {
        return Stream.of(terms)
                .map(PolyUtil::getDegree)
                .max(Integer::compareTo)
                .orElseThrow();
    }

    private static int getDegree(String term) {
        return term.contains("x")
                ? term.contains("^") ? Integer.parseInt(term.split("\\^")[1]) : 1
                : 0;
    }

    private static long getCoefficient(String term) {
        return Optional.of(term)
                .map(x -> x.split("\\^")[0])
                .map(x -> x.replace("x", ""))
                .map(x -> x.isEmpty() ? 1 : Long.parseLong(x))
                .orElseThrow();
    }

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

    public static int degree(FieldMatrix<GFPElement> poly) {
        return from(poly).degree();
    }

    public static FieldMatrix<GFPElement> subtractPolynomials(FieldMatrix<GFPElement> first, FieldMatrix<GFPElement> second) {
        return to(from(first).subtract(from(second)));
    }

    public static FieldMatrix<GFPElement> addPolynomials(FieldMatrix<GFPElement> first, FieldMatrix<GFPElement> second) {
        return to(from(first).add(from(second)));
    }

    public static FieldMatrix<GFPElement> multiplyPolynomialsByModulo(FieldMatrix<GFPElement> first, FieldMatrix<GFPElement> second, FieldMatrix<GFPElement> modulo) {
        var multiplied = multiplyPolynomials(first, second);

        if (getHighestDegree(modulo) > getHighestDegree(multiplied)) {
            return multiplied;
        }

        var dividedByModulo = dividePolynomialsWithRest(multiplied, modulo);
        return dividedByModulo.getSecond();
    }

    public static FieldMatrix<GFPElement> multiplyPolynomials(FieldMatrix<GFPElement> first, FieldMatrix<GFPElement> second) {
        return to(from(first).multiply(from(second)));
    }

    public static FieldMatrix<GFPElement> dividePolynomials(FieldMatrix<GFPElement> divisible, FieldMatrix<GFPElement> divisor) {
        return dividePolynomialsWithRest(divisible, divisor).getFirst();
    }

    public static Pair<FieldMatrix<GFPElement>, FieldMatrix<GFPElement>> dividePolynomialsWithRest(FieldMatrix<GFPElement> divisible, FieldMatrix<GFPElement> divisor) {
        Preconditions.checkArgument(degree(divisible) >= degree(divisor));

        var rest = divisible;
        var result = FieldMatrixUtil.matrixRow(divisible.getField().getZero());
        while (!isZero(rest) && (degree(rest) >= degree(divisor))) {
            var multiplyCoefficient = getHighestDegreeElement(rest).divide(getHighestDegreeElement(divisor));
            var degree = degree(rest) - degree(divisor);
            var monomial = monomial(multiplyCoefficient, degree);
            var multiplication = multiplyPolynomials(divisor, monomial);
            rest = subtractPolynomials(rest, multiplication);
            result = addPolynomials(result, monomial);
        }
        return Pair.create(result, rest);
    }

    public static FieldMatrix<GFPElement> monomial(GFPElement coefficient, int degree) {
        return to(UnivariatePolynomialZp64.monomial(
                coefficient.getField().prime(), coefficient.digitalRepresentation(), degree));
    }

    public static GFPElement getHighestDegreeElement(FieldMatrix<GFPElement> first) {
        return first.getEntry(0, first.getColumnDimension() - 1);
    }

    public static long getHighestDegree(FieldMatrix<GFPElement> first) {
        return first.getColumnDimension() - 1;
    }

    public static long[] toLongArray(FieldMatrix<GFPElement> poly) {
        return Stream.of(poly.getRow(0))
                .mapToLong(GFPElement::digitalRepresentation)
                .toArray();
    }

    public static UnivariatePolynomialZp64 from(FieldMatrix<GFPElement> poly) {
        var data = toLongArray(poly);
        long modulo = ((GFP) poly.getField()).prime();

        return UnivariatePolynomialZp64.create(modulo, data);
    }

    public static FieldMatrix<GFPElement> to(UnivariatePolynomialZp64 poly) {
        return toFieldMatrixRow(poly.stream().toArray(), GFP.of(poly.modulus()));
    }

    private PolyUtil() {
    }
}
