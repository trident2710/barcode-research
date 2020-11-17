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

import static com.trident.math.field.GaloisFieldOverPoly.GF4;
import static com.trident.math.matrix.FieldMatrixUtil.createMatrixOfRows;
import static com.trident.math.matrix.FieldMatrixUtil.matrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HammingCodeOverGF4ConverterTest {
    private static final GaloisFieldOverPolyElement ZERO = GF4.getZero();
    private static final GaloisFieldOverPolyElement ONE = GF4.getOne();
    private static final GaloisFieldOverPolyElement TWO = GF4.getOfValue(2);
    private static final GaloisFieldOverPolyElement THREE = GF4.getOfValue(3);

    // H(7, 4)
    // 0 0 0 0 1 0 0
    // 1 1 1 0 0 1 0
    // 1 2 3 1 0 0 1
    private static final FieldMatrix<GaloisFieldOverPolyElement> GENERATOR = createMatrixOfRows(
            matrixRow(ZERO, ZERO, ZERO, ONE),
            matrixRow(ONE, ONE, ONE, ZERO),
            matrixRow(ONE, TWO, THREE, ONE)
    );

    private static final HammingCode<GaloisFieldOverPolyElement, GaloisFieldOverPoly> HAMMING_CODE = new HammingCode<>(GENERATOR);

    @Test
    void testToDto() {
        var expected = ImmutableHammingCodeDto.builder()
                .field(ImmutableGaloisFieldDto.builder()
                        .type(GaloisFieldDto.Type.GFPM)
                        .prime(GF4.prime())
                        .exponent(GF4.exponent())
                        .irreduciblePoly(GF4.irreduciblePoly())
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
                        .prime(GF4.prime())
                        .exponent(GF4.exponent())
                        .irreduciblePoly(GF4.irreduciblePoly())
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
