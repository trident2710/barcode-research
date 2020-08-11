package com.trident.math.io.converter;

import static com.trident.math.field.GaloisFieldOverPrimeType.GF5;
import static com.trident.math.matrix.FieldMatrixUtil.createMatrixOfRows;
import static com.trident.math.matrix.FieldMatrixUtil.matrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.trident.math.field.GaloisFieldOverPrimeElement;
import com.trident.math.io.dto.ImmutableNaturalMatrixDto;
import org.junit.jupiter.api.Test;

import java.util.List;

class GaloisFieldOverPrimeMatrixConverterTest {

    private static final GaloisFieldOverPrimeElement ONE = GF5.field().getOne();

    @Test
    void testToDto() {
        var matrix = createMatrixOfRows(
                matrixRow(ONE, ONE, ONE),
                matrixRow(ONE, ONE, ONE),
                matrixRow(ONE, ONE, ONE));

        var expected = ImmutableNaturalMatrixDto.builder()
                .addAllMatrix(List.of(
                        List.of(1L, 1L, 1L),
                        List.of(1L, 1L, 1L),
                        List.of(1L, 1L, 1L)))
                .build();
        assertEquals(expected, GaloisFieldOverPrimeMatrixConverter.toDto(matrix));
    }

    @Test
    void testFromDto() {
        var matrix = ImmutableNaturalMatrixDto.builder()
                .addAllMatrix(List.of(
                        List.of(1L, 1L, 1L),
                        List.of(1L, 1L, 1L),
                        List.of(1L, 1L, 1L)))
                .build();

        var expected = createMatrixOfRows(
                matrixRow(ONE, ONE, ONE),
                matrixRow(ONE, ONE, ONE),
                matrixRow(ONE, ONE, ONE));

        assertEquals(expected, GaloisFieldOverPrimeMatrixConverter.fromDto(5, matrix));
    }
}