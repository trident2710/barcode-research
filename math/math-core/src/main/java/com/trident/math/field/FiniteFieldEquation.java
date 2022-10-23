package com.trident.math.field;

import org.apache.commons.math3.linear.FieldMatrix;

import java.util.ArrayList;
import java.util.List;

public final class FiniteFieldEquation {

    private FiniteFieldEquation() {
    }

    // solves an AX + B = 0 equation in finite fields by Chen procedure
    public static <FieldElem extends GFElement<FieldElem>> FieldElem solveLinearEquation(
            GF<FieldElem> field, FieldElem a, FieldElem b) {
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

    // solves an AX^2 + BX + C = 0 equation in finite fields by Chen procedure
    public static <FieldElem extends GFElement<FieldElem>> List<FieldElem> solveSquaredEquation(
            GF<FieldElem> field, FieldElem a, FieldElem b, FieldElem c) {
        var roots = new ArrayList<FieldElem>();

        if (c.equals(field.getZero())) {
            roots.add(field.getZero());
        }

        var iterator = field.iterator();
        while (iterator.hasNext()) {
            var x = iterator.next();
            var result = x.multiply(x).multiply(a).add(x.multiply(b)).add(c);
            if (result.equals(field.getZero())) {
                roots.add(x);
            }
        }

        return roots;
    }

    public static <FieldElem extends GFElement<FieldElem>> List<FieldElem> solveEquation(
            FieldMatrix<FieldElem> equationPoly) {
        GF<FieldElem> gf = (GF<FieldElem>) equationPoly.getField();
        var iterator = gf.iterator();

        var roots = new ArrayList<FieldElem>();

        while (iterator.hasNext()) {
            var x = iterator.next();
            FieldElem res = gf.getZero();
            for (int i = 0; i < equationPoly.getColumnDimension(); i++) {
                var coef = equationPoly.getEntry(0, i);
                res = res.add(coef.multiply(gf.pow(x, i)));
            }
            if (res.isZero()) {
                roots.add(x);
            }
        }

        return roots;
    }
}
