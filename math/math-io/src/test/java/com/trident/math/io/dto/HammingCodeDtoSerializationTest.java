package com.trident.math.io.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.trident.math.field.GaloisFieldOverPrimeType;
import org.junit.jupiter.api.Test;

import java.util.List;

class HammingCodeDtoSerializationTest {
    @Test
    void test() throws Exception {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModules(new GuavaModule());
        var hammingCode = ImmutableHammingCodeDto.builder()
                .fieldType(GaloisFieldOverPrimeType.GF5)
                .generatorMatrix(ImmutableNaturalMatrixDto.builder()
                        .addMatrix(List.of(1L, 2L, 3L))
                        .addMatrix(List.of(1L, 2L, 3L))
                        .build())
                .build();
        var serialized = objectMapper.writeValueAsString(hammingCode);
        String expected = "{\"fieldType\":\"GF5\",\"generatorMatrix\":{\"matrix\":[[1,2,3],[1,2,3]]}}";
        assertEquals(expected, serialized);
        var deserialized = objectMapper.readValue(serialized, HammingCodeDto.class);
        assertEquals(hammingCode, deserialized);
    }
}
