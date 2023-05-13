package com.trident.barcode.transform;

import com.trident.barcode.BarcodeRawExtractor;
import com.trident.barcode.model.BarcodeDictionaries;
import com.trident.barcode.model.BarcodeSign;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CharsetSwitchAppenderTest {

    @Test
    void test() {
        var expected = "H{setB}e{setA}L{setB}l{setA}O, {setC}С{setD}віт{setA}!";

        var intermediateCode = new CharsetSwitchAppender()
                .transform(new BarcodeRawExtractor(BarcodeDictionaries.BASE_59).extract("HeLlO, Світ!")).signs();
        var actual = intermediateCode.stream()
                .map(BarcodeSign::sign)
                .reduce("", String::concat);
        assertEquals(expected, actual);
    }
}