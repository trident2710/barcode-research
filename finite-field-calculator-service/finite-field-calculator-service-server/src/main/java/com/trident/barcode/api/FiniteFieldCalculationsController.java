package com.trident.barcode.api;

import com.trident.math.field.GFP;
import org.springframework.web.bind.annotation.*;

import static com.trident.math.PolyUtil.*;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;

@SuppressWarnings("ClassEscapesDefinedScope")
@RestController
public class FiniteFieldCalculationsController {

    @RequestMapping(path = "/finite-fields/gfp/inverse", method = RequestMethod.GET)
    public Long findInverse(@RequestParam int fieldPrime, @RequestParam int fieldElement) {
        var field = GFP.of(fieldPrime);
        return field.inv(field.getOfValue(fieldElement)).digitalRepresentation();
    }

    @RequestMapping(path = "/finite-fields/gfp/sum", method = RequestMethod.GET)
    public Long findSum(@RequestParam int fieldPrime, @RequestParam int fieldFirstElement, @RequestParam int fieldSecondElement) {
        var field = GFP.of(fieldPrime);
        return field.add(field.getOfValue(fieldFirstElement), field.getOfValue(fieldSecondElement)).digitalRepresentation();
    }

    @RequestMapping(path = "/finite-fields/gfp/exp", method = RequestMethod.GET)
    public Long findExp(@RequestParam int fieldPrime, @RequestParam int fieldElement, @RequestParam int exp) {
        var field = GFP.of(fieldPrime);
        return field.pow(field.getOfValue(fieldElement), exp).digitalRepresentation();
    }

    @RequestMapping(path = "/finite-fields/gfp/mul", method = RequestMethod.GET)
    public Long findMul(@RequestParam int fieldPrime, @RequestParam int fieldFirstElement, @RequestParam int fieldSecondElement) {
        var field = GFP.of(fieldPrime);
        return field.mul(field.getOfValue(fieldFirstElement), field.getOfValue(fieldSecondElement)).digitalRepresentation();
    }

    @RequestMapping(path = "/finite-fields/gfp/neg", method = RequestMethod.GET)
    public Long findNeg(@RequestParam int fieldPrime, @RequestParam int fieldElement) {
        var field = GFP.of(fieldPrime);
        return field.neg(field.getOfValue(fieldElement)).digitalRepresentation();
    }

    @RequestMapping(path = "/finite-fields/gfp/mul-poly", method = RequestMethod.POST)
    public MulPolyResponse mulPoly(@RequestBody PolyMulBody polyMulBody) {
        System.out.println(polyMulBody);
        var field = GFP.of(polyMulBody.fieldPrime());
        var firstPoly = toFieldMatrixRow(stringToPoly(polyMulBody.polyFirst()), field);
        var secondPoly = toFieldMatrixRow(stringToPoly(polyMulBody.polySecond()), field);
        return new MulPolyResponse(polyToString(toLongArray(multiplyPolynomials(firstPoly, secondPoly))));
    }

    private record PolyMulBody(int fieldPrime, String polyFirst, String polySecond) {}

    private record MulPolyResponse(String res) {}
}
