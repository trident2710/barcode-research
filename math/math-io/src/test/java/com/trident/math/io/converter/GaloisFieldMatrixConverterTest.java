package com.trident.math.io.converter;

import com.trident.math.field.GaloisFieldOverPrimeElement;
import com.trident.math.io.dto.ImmutableNaturalMatrixDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.trident.math.field.GaloisFields.GF5;
import static com.trident.math.matrix.FieldMatrixUtil.createMatrixOfRows;
import static com.trident.math.matrix.FieldMatrixUtil.matrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GaloisFieldMatrixConverterTest {

    private static final GaloisFieldOverPrimeElement ONE = GF5.getOne();

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
        assertEquals(expected, GaloisFieldMatrixConverter.toDto(matrix));
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

        assertEquals(expected, GaloisFieldMatrixConverter.fromDto(GF5, matrix, new GaloisFieldOverPrimeElement[0]));
    }
}