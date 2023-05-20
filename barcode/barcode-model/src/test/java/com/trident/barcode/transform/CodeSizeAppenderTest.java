package com.trident.barcode.transform;

import com.trident.barcode.model.BarcodeDictionaries;
import com.trident.barcode.model.ImmutableBarcode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CodeSizeAppenderTest {

    @Test
    void testCodeSizeDigitsCount() {
        assertEquals(2, CodeSizeAppender.calculateCodeSizeSignCount(BarcodeDictionaries.BASE_41));
    }

    @Test
    void test() {
        var barcode = ImmutableBarcode.builder()
                .dictionary(BarcodeDictionaries.BASE_41)
                .addSigns(BarcodeDictionaries.BASE_41.findSign("A").orElseThrow())
                .addSigns(BarcodeDictionaries.BASE_41.findSign("B").orElseThrow())
                .addSigns(BarcodeDictionaries.BASE_41.findSign("C").orElseThrow())
                .build();

        var appender = new CodeSizeAppender(BarcodeDictionaries.BASE_41);

        var expected = ImmutableBarcode.builder()
                .dictionary(BarcodeDictionaries.BASE_41)
                .addSigns(BarcodeDictionaries.BASE_41.findSign("0").orElseThrow())
                .addSigns(BarcodeDictionaries.BASE_41.findSign("3").orElseThrow())
                .addSigns(BarcodeDictionaries.BASE_41.findSign("A").orElseThrow())
                .addSigns(BarcodeDictionaries.BASE_41.findSign("B").orElseThrow())
                .addSigns(BarcodeDictionaries.BASE_41.findSign("C").orElseThrow())
                .build();

        assertEquals(expected, appender.transform(barcode));
    }

    @Test
    void testSize2Digits() {
        var barcode = ImmutableBarcode.builder()
                .dictionary(BarcodeDictionaries.BASE_41)
                .addSigns(BarcodeDictionaries.BASE_41.findSign("1").orElseThrow())
                .addSigns(BarcodeDictionaries.BASE_41.findSign("2").orElseThrow())
                .addSigns(BarcodeDictionaries.BASE_41.findSign("3").orElseThrow())
                .addSigns(BarcodeDictionaries.BASE_41.findSign("4").orElseThrow())
                .addSigns(BarcodeDictionaries.BASE_41.findSign("5").orElseThrow())
                .addSigns(BarcodeDictionaries.BASE_41.findSign("6").orElseThrow())
                .addSigns(BarcodeDictionaries.BASE_41.findSign("7").orElseThrow())
                .addSigns(BarcodeDictionaries.BASE_41.findSign("8").orElseThrow())
                .addSigns(BarcodeDictionaries.BASE_41.findSign("9").orElseThrow())
                .addSigns(BarcodeDictionaries.BASE_41.findSign("0").orElseThrow())
                .build();

        var appender = new CodeSizeAppender(BarcodeDictionaries.BASE_41);

        var expected = ImmutableBarcode.builder()
                .dictionary(BarcodeDictionaries.BASE_41)
                .addSigns(BarcodeDictionaries.BASE_41.findSign("1").orElseThrow())
                .addSigns(BarcodeDictionaries.BASE_41.findSign("0").orElseThrow())
                .addSigns(BarcodeDictionaries.BASE_41.findSign("1").orElseThrow())
                .addSigns(BarcodeDictionaries.BASE_41.findSign("2").orElseThrow())
                .addSigns(BarcodeDictionaries.BASE_41.findSign("3").orElseThrow())
                .addSigns(BarcodeDictionaries.BASE_41.findSign("4").orElseThrow())
                .addSigns(BarcodeDictionaries.BASE_41.findSign("5").orElseThrow())
                .addSigns(BarcodeDictionaries.BASE_41.findSign("6").orElseThrow())
                .addSigns(BarcodeDictionaries.BASE_41.findSign("7").orElseThrow())
                .addSigns(BarcodeDictionaries.BASE_41.findSign("8").orElseThrow())
                .addSigns(BarcodeDictionaries.BASE_41.findSign("9").orElseThrow())
                .addSigns(BarcodeDictionaries.BASE_41.findSign("0").orElseThrow())
                .build();

        assertEquals(expected, appender.transform(barcode));
    }
}