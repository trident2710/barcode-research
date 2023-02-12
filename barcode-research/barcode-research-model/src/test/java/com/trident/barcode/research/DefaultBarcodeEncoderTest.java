package com.trident.barcode.research;

import com.trident.barcode.research.model.BarcodeDictionaries;
import com.trident.barcode.research.model.BarcodeSign;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultBarcodeEncoderTest {

    @Test
    void test() {
        var encoder = new DefaultBarcodeEncoder(BarcodeDictionaries.BASE_59);
        var expected = List.of(
                0, 2, 1, 2, 2, 3, 3, 2, 0,
                1, 1, 3, 1, 3, 2, 3, 3, 3,
                1, 2, 2, 0, 3, 0, 3, 1, 0,
                1, 1, 3, 1, 3, 2, 3, 3, 3,
                0, 3, 2, 3, 3, 1, 1, 3, 0,
                1, 1, 3, 1, 3, 2, 3, 3, 3,
                0, 3, 2, 3, 3, 1, 1, 3, 0,
                1, 1, 3, 1, 3, 2, 3, 3, 3,
                3, 1, 1, 0, 2, 0, 2, 3, 0,
                2, 2, 2, 0, 2, 0, 0, 0, 2,
                3, 0, 0, 0, 1, 0, 3, 1, 2,
                2, 3, 0, 2, 2, 3, 0, 3, 3,
                1, 2, 3, 3, 2, 1, 1, 3, 1,
                3, 0, 1, 3, 0, 1, 1, 3, 3,
                3, 2, 3, 3, 1, 1, 3, 0, 0,
                3, 0, 2, 1, 3, 2, 0, 2, 0,
                2, 0, 0, 0, 3, 0, 2, 3, 1,
                1, 1, 3, 1, 3, 2, 3, 3, 3,
                1, 2, 0, 1, 1, 2, 0, 2, 2
        );

        var code = encoder.encode("HeLlO, Світ!");
        assertEquals(expected, code.data());
    }

    @Test
    void testIntermediateCode() {
        var encoder = new DefaultBarcodeEncoder(BarcodeDictionaries.BASE_59);
        var expected = "H{setB}e{setA}L{setB}l{setA}O, {setC}С{setD}віт{setA}!";

        var intermediateCode = encoder.buildIntermediateCode("HeLlO, Світ!");
        var actual = intermediateCode.stream()
                .map(BarcodeSign::sign)
                .reduce("", String::concat);
        assertEquals(expected, actual);
    }

}