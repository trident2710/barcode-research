package com.trident.math.field;

import java.util.HashSet;
import java.util.List;

public final class GFPSimplePrimitiveElementCalculator {

    private static final List<Integer> KNOWN_PRIMES =
            List.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71);

    public static GFPElement primitiveElement(GFP field) {
        return KNOWN_PRIMES.stream()
                .filter(it -> checkForPrimitive(it, field))
                .findFirst()
                .map(field::getOfValue)
                .orElseThrow();
    }

    private static boolean checkForPrimitive(long primeNumber, GFP field) {
        var members = new HashSet<Long>();
        for (int i = 1; i < field.elementsCount(); i++) {
            var powered = field.pow(field.getOfValue(primeNumber), i);
            if (!members.add(powered.digitalRepresentation())) {
                return false;
            }
        }
        return true;
    }
}
