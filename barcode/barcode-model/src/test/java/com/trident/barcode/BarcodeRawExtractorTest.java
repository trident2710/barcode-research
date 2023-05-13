package com.trident.barcode;

import com.trident.barcode.model.BarcodeDictionaries;
import com.trident.barcode.model.ImmutableBarcode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BarcodeRawExtractorTest {

    @Test
    void test() {
        var expectedBarcode = ImmutableBarcode.builder()
                .dictionary(BarcodeDictionaries.BASE_59)
                .signs(List.of(
                        BarcodeDictionaries.BASE_59.findSign("A").orElseThrow(),
                        BarcodeDictionaries.BASE_59.findSign("b").orElseThrow(),
                        BarcodeDictionaries.BASE_59.findSign("Ц").orElseThrow(),
                        BarcodeDictionaries.BASE_59.findSign("д").orElseThrow()
                ))
                .build();
        assertEquals(expectedBarcode, new BarcodeRawExtractor(BarcodeDictionaries.BASE_59).extract("AbЦд"));
    }
}