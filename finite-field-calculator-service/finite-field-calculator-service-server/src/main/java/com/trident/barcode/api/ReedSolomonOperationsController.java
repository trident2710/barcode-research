package com.trident.barcode.api;

import com.trident.math.PolyUtil;
import com.trident.math.field.GFP;
import com.trident.math.reedsolomon.ReedSolomonUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    record PolyResponse(String poly){}

    record ErasureLocatorsPolyRequest(int fieldPrime, int primitiveElement, List<Integer> erasurePositions) {}
}
