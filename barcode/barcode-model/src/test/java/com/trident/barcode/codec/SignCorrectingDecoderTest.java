package com.trident.barcode.codec;

import com.trident.barcode.correction.CorrectionStrategies;
import com.trident.barcode.model.BarcodeDictionaries;
import com.trident.barcode.model.ImmutableCode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SignCorrectingDecoderTest {

    @Test
    void test() {
        var code = ImmutableCode.of(List.of(
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
                2, 2, 0, 2, 1, 1, 0, 2, 1
        ));

        var decoder = new SignCorrectingDecoder(BarcodeDictionaries.BASE_59, CorrectionStrategies.BCH_9_3);

        assertEquals("HeLlO, Світ!", decoder.decode(code));
    }

}