package com.trident.barcode.api;

import com.trident.math.field.FiniteFieldEquation;
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
    public MulPolyResponse mulPoly(@RequestBody MulPolyRequest mulPolyRequest) {
        var field = GFP.of(mulPolyRequest.fieldPrime());
        var firstPoly = toFieldMatrixRow(stringToPoly(mulPolyRequest.polyFirst()), field);
        var secondPoly = toFieldMatrixRow(stringToPoly(mulPolyRequest.polySecond()), field);
        return new MulPolyResponse(polyToString(toLongArray(multiplyPolynomials(firstPoly, secondPoly))));
    }

    @RequestMapping(path = "/finite-fields/gfp/mul-poly-by-modulo", method = RequestMethod.POST)
    public MulPolyResponse mulPolyByModulo(@RequestBody MulPolyByModuloRequest request) {
        var field = GFP.of(request.fieldPrime());
        var firstPoly = toFieldMatrixRow(stringToPoly(request.polyFirst()), field);
        var secondPoly = toFieldMatrixRow(stringToPoly(request.polySecond()), field);
        var modulo = toFieldMatrixRow(stringToPoly(request.modulo()), field);
        return new MulPolyResponse(polyToString(toLongArray(multiplyPolynomialsByModulo(firstPoly, secondPoly, modulo))));
    }

    @RequestMapping(path = "/finite-fields/gfp/div-poly", method = RequestMethod.POST)
    public DivPolyResponse divPoly(@RequestBody DivPolyRequest divPolyRequest) {
        var field = GFP.of(divPolyRequest.fieldPrime());
        var divisible = toFieldMatrixRow(stringToPoly(divPolyRequest.divisible()), field);
        var divisor = toFieldMatrixRow(stringToPoly(divPolyRequest.divisor()), field);
        var result = dividePolynomialsWithRest(divisible, divisor);
        return new DivPolyResponse(polyToString(toLongArray(result.getFirst())), polyToString(toLongArray(result.getSecond())));
    }

    @RequestMapping(path = "/finite-fields/gfp/poly-val", method = RequestMethod.POST)
    public Long polyVal(@RequestBody PolyValueRequest request) {
        var field = GFP.of(request.fieldPrime);
        var polynom = toFieldMatrixRow(stringToPoly(request.poly), field);
        var result = FiniteFieldEquation.calculatePolyValue(polynom, field.getOfValue(request.point));
        return result.digitalRepresentation();
    }


    private record MulPolyRequest(int fieldPrime, String polyFirst, String polySecond) {}

    private record MulPolyByModuloRequest(int fieldPrime, String polyFirst, String polySecond, String modulo) {}

    private record MulPolyResponse(String res) {}

    private record DivPolyRequest(int fieldPrime, String divisible, String divisor) {}

    private record DivPolyResponse(String result, String rest) {}

    private record PolyValueRequest(int fieldPrime, int point, String poly) {}
}
