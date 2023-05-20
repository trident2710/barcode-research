package com.trident.barcode.reedsolomon;

import com.trident.barcode.BarcodeRawExtractor;
import com.trident.barcode.model.BarcodeDictionaries;
import com.trident.barcode.model.BarcodeSign;
import com.trident.math.reedsolomon.ReedSolomonCode;
import org.junit.jupiter.api.Test;

import static com.trident.math.reedsolomon.ReedSolomonGeneratorPolynomials.GF_41_R6;
import static com.trident.math.reedsolomon.ReedSolomonGeneratorPolynomials.GF_59_R6;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReedSolomonErrorCorrectingCodeGeneratorTest {

    @Test
    void testBase59() {
        var expected = "A{setB}U9M-Y.C";

        var code = new ReedSolomonErrorCorrectingCodeGenerator(new ReedSolomonCode(GF_59_R6))
                .transform(new BarcodeRawExtractor(BarcodeDictionaries.BASE_59).extract("abc")).signs();

        var actual = code.stream()
                .map(BarcodeSign::sign)
                .reduce("", String::concat);

        assertEquals(expected, actual);
    }

    @Test
    void testBase41() {
        var expected = "A8KUT92ZC";

        var code = new ReedSolomonErrorCorrectingCodeGenerator(new ReedSolomonCode(GF_41_R6))
                .transform(new BarcodeRawExtractor(BarcodeDictionaries.BASE_41).extract("AБ;")).signs();

        var actual = code.stream()
                .map(BarcodeSign::sign)
                .reduce("", String::concat);

        assertEquals(expected, actual);
    }

}