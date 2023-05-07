package com.trident.barcode.research;

import com.trident.barcode.research.model.BarcodeDictionaries;
import com.trident.barcode.research.model.BarcodeSign;
import org.junit.jupiter.api.Test;

import static com.trident.barcode.research.PaddingStrategy.NO_PADDING;
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
    void testPadding() {
        var generator = new DefaultIntermediateCodeGenerator(BarcodeDictionaries.BASE_59, new FixedSizePaddingStrategy(5));
        var expected = "H{setB}e{setA}L{setB}l{setA}O, {setC}С{setD}віт{setA}!{pad}{pad}{pad}{pad}{pad}{pad}";

        var intermediateCode = generator.buildIntermediateCode("HeLlO, Світ!");
        var actual = intermediateCode.stream()
                .map(BarcodeSign::sign)
                .reduce("", String::concat);
        assertEquals(expected, actual);
    }
}