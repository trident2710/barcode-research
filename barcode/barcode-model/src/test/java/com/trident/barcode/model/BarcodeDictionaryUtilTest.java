package com.trident.barcode.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.trident.barcode.model.BarcodeDictionaryUtil.fromCodeString;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BarcodeDictionaryUtilTest {

    @Test
    void testFromCodeString() {
        assertEquals(List.of(0, 0, 1, 2, 2, 1, 1, 3, 1), fromCodeString("001221131"));
    }

}