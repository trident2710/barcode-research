package com.trident.barcode.transform;

import com.trident.barcode.model.BarcodeDictionaries;
import com.trident.barcode.model.BarcodeSign;
import com.trident.barcode.padding.NearestSquarePaddingStrategy;
import org.junit.jupiter.api.Test;

import static com.trident.barcode.padding.PaddingStrategy.NO_PADDING;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultIntermediateCodeGeneratorTest {

    @Test
    void testIntermediateCode() {
        var generator = new DefaultIntermediateCodeGenerator(BarcodeDictionaries.BASE_59, NO_PADDING);
        var expected = "H{setB}e{setA}L{setB}l{setA}O, {setC}С{setD}віт{setA}!";

        var intermediateCode = generator.buildIntermediateCode("HeLlO, Світ!");
        var actual = intermediateCode.stream()
                .map(BarcodeSign::sign)
                .reduce("", String::concat);
        assertEquals(expected, actual);
    }

    @Test
    void testCharsetSwitchFirst() {
        var generator = new DefaultIntermediateCodeGenerator(BarcodeDictionaries.BASE_59, NO_PADDING);
        var expected = "{setB}he{setA}L{setB}l{setA}O, {setC}С{setD}віт{setA}!";

        var intermediateCode = generator.buildIntermediateCode("heLlO, Світ!");
        var actual = intermediateCode.stream()
                .map(BarcodeSign::sign)
                .reduce("", String::concat);
        assertEquals(expected, actual);
    }

    @Test
    void testPadding() {
        var generator = new DefaultIntermediateCodeGenerator(BarcodeDictionaries.BASE_59, new NearestSquarePaddingStrategy());
        var expected = "H{setB}e{setA}L{setB}l{setA}O, {setC}С{setD}віт{setA}!{pad}{pad}{pad}{pad}{pad}{pad}";

        var intermediateCode = generator.buildIntermediateCode("HeLlO, Світ!");
        var actual = intermediateCode.stream()
                .map(BarcodeSign::sign)
                .reduce("", String::concat);
        assertEquals(expected, actual);
    }
}