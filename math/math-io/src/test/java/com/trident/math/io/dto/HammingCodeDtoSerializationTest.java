package com.trident.math.io.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HammingCodeDtoSerializationTest {
    @Test
    void test() throws Exception {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModules(new GuavaModule());
        var hammingCode = ImmutableHammingCodeDto.builder()
                .field(ImmutableGaloisFieldDto.builder()
                        .type(GaloisFieldDto.Type.GFP)
                        .prime(5)
                        .build())
                .generatorMatrix(ImmutableNaturalMatrixDto.builder()
                        .addMatrix(List.of(1L, 2L, 3L))
                        .addMatrix(List.of(1L, 2L, 3L))
                        .build())
                .build();
        var serialized = objectMapper.writeValueAsString(hammingCode);
        String expected = "{\"field\":{\"type\":\"GFP\",\"prime\":5,\"exponent\":null,\"irreduciblePoly\":null},\"generatorMatrix\":{\"matrix\":[[1,2,3],[1,2,3]]}}";
        assertEquals(expected, serialized);
        var deserialized = objectMapper.readValue(serialized, HammingCodeDto.class);
        assertEquals(hammingCode, deserialized);
    }
}
