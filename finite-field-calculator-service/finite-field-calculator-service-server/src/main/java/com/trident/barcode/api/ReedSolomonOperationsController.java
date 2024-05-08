package com.trident.barcode.api;

import com.trident.math.PolyUtil;
import com.trident.math.field.GFP;
import com.trident.math.matrix.FieldMatrixUtil;
import com.trident.math.reedsolomon.ReedSolomonUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

import static com.trident.math.field.GaloisFields.GF11;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;
import static com.trident.math.reedsolomon.ReedSolomonGeneratorPolynomials.generatorPolynomial;

@SuppressWarnings("ClassEscapesDefinedScope")
@RestController
public class ReedSolomonOperationsController {

    @RequestMapping(path = "/reed-solomon/generator-poly", method = RequestMethod.GET)
    public PolyResponse findInverse(@RequestParam int fieldPrime, @RequestParam int primitiveElement, @RequestParam int power) {
        var field = GFP.of(fieldPrime);
        return Optional.of(generatorPolynomial(field.getOfValue(primitiveElement), power))
                .map(PolyUtil::toLongArray)
                .map(PolyUtil::polyToString)
                .map(PolyResponse::new)
                .orElseThrow();
    }

    @RequestMapping(path = "/reed-solomon/erasure-locators-poly", method = RequestMethod.POST)
    public PolyResponse findErasureLocatorsPoly(@RequestBody ErasureLocatorsPolyRequest request) {
        var field = GFP.of(request.fieldPrime);
        var erasureLocators = ReedSolomonUtil.findErasureLocators(field, field.getOfValue(request.primitiveElement), request.erasurePositions);
        var erasureLocatorsPoly = ReedSolomonUtil.calculateErasureLocatorsPolynomial(field, erasureLocators);
        return erasureLocatorsPoly
                .map(PolyUtil::toLongArray)
                .map(PolyUtil::polyToString)
                .map(PolyResponse::new)
                .orElseThrow();
    }

    @RequestMapping(path = "/reed-solomon/error-locators-poly", method = RequestMethod.POST)
    public PolyResponse findErrorLocatorsPoly(@RequestBody ErrorLocatorsPolyRequest request) {
        var field = GFP.of(request.fieldPrime);
        var syndrome = toFieldMatrixRow(request.errorsSyndrome.stream().mapToLong(x -> x).toArray(), GF11);
        return ReedSolomonUtil.calculateErrorLocatorsPolynomial(field, syndrome, request.errorsCount)
                .map(PolyUtil::toLongArray)
                .map(PolyUtil::polyToString)
                .map(PolyResponse::new)
                .orElseThrow();
    }

    record PolyResponse(String poly){}

    record ErasureLocatorsPolyRequest(int fieldPrime, int primitiveElement, List<Integer> erasurePositions) {}

    record ErrorLocatorsPolyRequest(int fieldPrime, List<Integer> errorsSyndrome, int errorsCount) {}
}
