package com.trident.math.io.converter;

import com.trident.math.field.GaloisFieldOverPoly;
import com.trident.math.field.GaloisFieldOverPolyElement;
import com.trident.math.io.dto.GaloisFieldDto;
import com.trident.math.io.dto.ImmutableGaloisFieldDto;
import com.trident.math.io.dto.ImmutableHammingCodeDto;
import com.trident.math.io.dto.ImmutableNaturalMatrixDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.trident.math.field.GaloisFields.GF_2_2;
import static com.trident.math.hamming.HammingCodes.HAMMING_7_4_GF_2_2;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HammingCodeOverGF4ConverterTest {

    @Test
    void testToDto() {
        var expected = ImmutableHammingCodeDto.builder()
                .field(ImmutableGaloisFieldDto.builder()
                        .type(GaloisFieldDto.Type.GFPM)
                        .prime(GF_2_2.prime())
                        .exponent(GF_2_2.exponent())
                        .irreduciblePoly(GF_2_2.irreduciblePoly())
                        .build())
                .generatorMatrix(ImmutableNaturalMatrixDto.builder()
                        .addMatrix(List.of(0L, 0L, 0L, 1L))
                        .addMatrix(List.of(1L, 1L, 1L, 0L))
                        .addMatrix(List.of(1L, 2L, 3L, 1L))
                        .build())
                .build();
        assertEquals(expected, HammingCodeConverter.toDto(HAMMING_7_4_GF_2_2));
    }

    @Test
    void testFromDto() {
        var dto = ImmutableHammingCodeDto.builder()
                .field(ImmutableGaloisFieldDto.builder()
                        .type(GaloisFieldDto.Type.GFPM)
                        .prime(GF_2_2.prime())
                        .exponent(GF_2_2.exponent())
                        .irreduciblePoly(GF_2_2.irreduciblePoly())
                        .build())
                .generatorMatrix(ImmutableNaturalMatrixDto.builder()
                        .addMatrix(List.of(0L, 0L, 0L, 1L))
                        .addMatrix(List.of(1L, 1L, 1L, 0L))
                        .addMatrix(List.of(1L, 2L, 3L, 1L))
                        .build())
                .build();
        assertEquals(HAMMING_7_4_GF_2_2, HammingCodeConverter.fromDto(dto, GaloisFieldOverPoly.class, new GaloisFieldOverPolyElement[0]));
    }
}
