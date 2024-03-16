package com.trident.barcode.api;

import com.trident.math.field.GFP;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FiniteFieldCalculationsController {

    @RequestMapping(path = "/finite-fields/gfp/inverse", method = RequestMethod.GET)
    public ResponseEntity<Long> findInverse(@RequestParam int fieldPrime, @RequestParam int fieldElement) {
        var field = GFP.of(fieldPrime);
        return ResponseEntity.ok(field.inv(field.getOfValue(fieldElement)).digitalRepresentation());
    }
}
