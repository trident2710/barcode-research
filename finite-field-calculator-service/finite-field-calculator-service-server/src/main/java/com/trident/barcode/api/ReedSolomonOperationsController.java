package com.trident.barcode.api;

import com.trident.math.PolyUtil;
import com.trident.math.field.GFP;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    record PolyResponse(String poly){}
}
