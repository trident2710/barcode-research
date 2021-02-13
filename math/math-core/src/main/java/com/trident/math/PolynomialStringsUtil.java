package com.trident.math;

public final class PolynomialStringsUtil {
    private PolynomialStringsUtil() {
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
}
