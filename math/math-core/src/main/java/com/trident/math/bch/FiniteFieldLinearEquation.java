package com.trident.math.bch;

import com.trident.math.field.GaloisField;
import org.apache.commons.math3.FieldElement;

public final class FiniteFieldLinearEquation {

    private FiniteFieldLinearEquation() {
    }

    // solves an AX + B = 0 equation in finite fields by Chen procedure
    public static <FieldElem extends FieldElement<FieldElem>> FieldElem solve(GaloisField<FieldElem> field, FieldElem a, FieldElem b) {
        if (b.equals(field.getZero())) {
            return field.getZero();
        }

        if (a.equals(field.getZero())) {
            throw new IllegalArgumentException("A should not be zero");
        }

        var iterator = field.iterator();
        while (iterator.hasNext()) {
            var value = iterator.next();
            var result = value.multiply(a).add(b);
            if (result.equals(field.getZero())) {
                return value;
            }
        }

        return null;
    }
}
