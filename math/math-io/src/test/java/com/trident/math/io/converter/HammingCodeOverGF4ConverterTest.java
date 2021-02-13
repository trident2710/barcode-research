package com.trident.math.io.converter;

import com.trident.math.field.GaloisFieldOverPoly;
import com.trident.math.field.GaloisFieldOverPolyElement;
import com.trident.math.hamming.HammingCode;
import com.trident.math.io.dto.GaloisFieldDto;
import com.trident.math.io.dto.ImmutableGaloisFieldDto;
import com.trident.math.io.dto.ImmutableHammingCodeDto;
import com.trident.math.io.dto.ImmutableNaturalMatrixDto;
import org.apache.commons.math3.linear.FieldMatrix;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.trident.math.field.GaloisFields.GF_2_2;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrix;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HammingCodeOverGF4ConverterTest {
    // H(7, 4)
    // 0 0 0 0 1 0 0
    // 1 1 1 0 0 1 0
    // 1 2 3 1 0 0 1
    private static final FieldMatrix<GaloisFieldOverPolyElement> GENERATOR = toFieldMatrix(new long[][]{
            new long[]{0, 0, 0, 1},
            new long[]{1, 1, 1, 0},
            new long[]{1, 2, 3, 1}
    }, GF_2_2);

    private static final HammingCode<GaloisFieldOverPolyElement, GaloisFieldOverPoly> HAMMING_CODE = new HammingCode<>(GENERATOR);

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
        assertEquals(expected, HammingCodeConverter.toDto(HAMMING_CODE));
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
        assertEquals(HAMMING_CODE, HammingCodeConverter.fromDto(dto, GaloisFieldOverPoly.class, new GaloisFieldOverPolyElement[0]));
    }
}
