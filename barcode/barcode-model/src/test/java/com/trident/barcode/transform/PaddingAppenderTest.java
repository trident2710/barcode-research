package com.trident.barcode.transform;

import com.trident.barcode.BarcodeRawExtractor;
import com.trident.barcode.model.BarcodeDictionaries;
import com.trident.barcode.model.BarcodeSign;
import com.trident.barcode.padding.NearestSquarePaddingStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaddingAppenderTest {

    @Test
    void test() {
        var expected = "HeLlO, Світ!{pad}{pad}{pad}{pad}";
        var intermediateCode = new PaddingAppender(new NearestSquarePaddingStrategy())
                .transform(new BarcodeRawExtractor(BarcodeDictionaries.BASE_59).extract("HeLlO, Світ!")).signs();
        var actual = intermediateCode.stream()
                .map(BarcodeSign::sign)
                .reduce("", String::concat);
        assertEquals(expected, actual);
    }

}