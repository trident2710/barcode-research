package com.trident.barcode.codec;

import com.trident.barcode.model.BarcodeDictionaries;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SimpleEncoderTest {

    @Test
    void test() {
        var encoder = new BarcodeEncoderImpl(BarcodeDictionaries.BASE_59, new SimpleCodingStrategy());
        var expected = List.of(
                0, 2, 3, 3, 2, 2, 1, 2, 0,
                3, 3, 3, 2, 3, 1, 3, 1, 1,
                0, 1, 3, 0, 3, 0, 2, 2, 1,
                3, 3, 3, 2, 3, 1, 3, 1, 1,
                0, 3, 1, 1, 3, 3, 2, 3, 0,
                3, 3, 3, 2, 3, 1, 3, 1, 1,
                0, 3, 1, 1, 3, 3, 2, 3, 0,
                3, 3, 3, 2, 3, 1, 3, 1, 1,
                0, 3, 2, 0, 2, 0, 1, 1, 3,
                2, 0, 0, 0, 2, 0, 2, 2, 2,
                2, 1, 3, 0, 1, 0, 0, 0, 3,
                3, 3, 0, 3, 2, 2, 0, 3, 2,
                1, 3, 1, 1, 2, 3, 3, 2, 1,
                3, 3, 1, 1, 0, 3, 1, 0, 3,
                0, 0, 3, 1, 1, 3, 3, 2, 3,
                0, 2, 0, 2, 3, 1, 2, 0, 3,
                1, 3, 2, 0, 3, 0, 0, 0, 2,
                3, 3, 3, 2, 3, 1, 3, 1, 1,
                2, 2, 0, 2, 1, 1, 0, 2, 1,
                3, 3, 2, 0, 1, 0, 2, 2, 0,
                3, 3, 2, 0, 1, 0, 2, 2, 0,
                3, 3, 2, 0, 1, 0, 2, 2, 0,
                3, 3, 2, 0, 1, 0, 2, 2, 0,
                3, 3, 2, 0, 1, 0, 2, 2, 0,
                3, 3, 2, 0, 1, 0, 2, 2, 0
        );

        var code = encoder.encode("HeLlO, Світ!");
        Assertions.assertEquals(expected, code.data());
    }

    @Test
    void testUnexpectedSign() {
        var encoder = new BarcodeEncoderImpl(BarcodeDictionaries.BASE_41, new SimpleCodingStrategy());

        assertDoesNotThrow(() -> encoder.encode("HELLO"));

        assertThrows(Exception.class, () -> encoder.encode("HELLo"));
    }
}