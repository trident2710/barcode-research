package com.trident.math.field;

import org.junit.jupiter.api.Test;

import static com.trident.math.field.GaloisFields.GF_2_3;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GFPMPowerRepresentationMapperTest {

    @Test
    void test() {
        var mapper = GFPMPowerRepresentationMapper.create(GF_2_3);
        assertEquals(-1, mapper.getPower(GF_2_3.getZero()));
        var iterator = GF_2_3.iterator();
        int power = 0;
        while (iterator.hasNext()) {
            assertEquals(power, mapper.getPower(iterator.next()));
            power++;
        }
    }
}