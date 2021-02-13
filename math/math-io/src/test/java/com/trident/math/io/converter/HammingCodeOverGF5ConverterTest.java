package com.trident.math.io.converter;

import com.trident.math.field.GaloisFieldOverPrime;
import com.trident.math.field.GaloisFieldOverPrimeElement;
import com.trident.math.io.dto.ImmutableGaloisFieldDto;
import com.trident.math.io.dto.ImmutableHammingCodeDto;
import com.trident.math.io.dto.ImmutableNaturalMatrixDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.trident.math.field.GaloisFields.GF5;
import static com.trident.math.hamming.HammingCodes.HAMMING_5_3_GF_5;
import static com.trident.math.io.converter.HammingCodeConverter.fromDto;
import static com.trident.math.io.converter.HammingCodeConverter.toDto;
import static com.trident.math.io.dto.GaloisFieldDto.Type.GFP;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HammingCodeOverGF5ConverterTest {

    @Test
    void testToDto() {
        var expected = ImmutableHammingCodeDto.builder()
                .field(ImmutableGaloisFieldDto.builder()
                        .type(GFP)
                        .prime(GF5.prime())
                        .build())
                .generatorMatrix(ImmutableNaturalMatrixDto.builder()
                        .addMatrix(List.of(1L, 1L, 1L))
                        .addMatrix(List.of(1L, 2L, 3L))
                        .build())
                .build();
        assertEquals(expected, toDto(HAMMING_5_3_GF_5));
    }

    @Test
    void testFromDto() {
        var dto = ImmutableHammingCodeDto.builder()
                .field(ImmutableGaloisFieldDto.builder()
                        .type(GFP)
                        .prime(GF5.prime())
                        .build())
                .generatorMatrix(ImmutableNaturalMatrixDto.builder()
                        .addMatrix(List.of(1L, 1L, 1L))
                        .addMatrix(List.of(1L, 2L, 3L))
                        .build())
                .build();
        assertEquals(HAMMING_5_3_GF_5, fromDto(dto, GaloisFieldOverPrime.class, new GaloisFieldOverPrimeElement[0]));
    }
}