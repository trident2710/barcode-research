package com.trident.math.io.converter;

import static com.trident.math.field.GaloisFieldType.GF5;
import static com.trident.math.matrix.FieldMatrixUtil.createMatrixOfRows;
import static com.trident.math.matrix.FieldMatrixUtil.matrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.trident.math.field.GaloisFieldOverPrimeElement;
import com.trident.math.hamming.HammingCode;
import com.trident.math.io.dto.ImmutableHammingCodeDto;
import com.trident.math.io.dto.ImmutableNaturalMatrixDto;
import org.apache.commons.math3.linear.FieldMatrix;
import org.junit.jupiter.api.Test;

import java.util.List;

class HammingCodeConverterTest {
    private static final GaloisFieldOverPrimeElement ONE = GF5.field().getOne();
    private static final GaloisFieldOverPrimeElement TWO = GF5.field().getOfValue(2);
    private static final GaloisFieldOverPrimeElement THREE = GF5.field().getOfValue(3);

    private static final FieldMatrix<GaloisFieldOverPrimeElement> GENERATOR = createMatrixOfRows(
            matrixRow(ONE, ONE, ONE),
            matrixRow(ONE, TWO, THREE)
    );

    private static final HammingCode HAMMING_CODE = new HammingCode(GENERATOR);

    @Test
    void testToDto() {
        var expected = ImmutableHammingCodeDto.builder()
                .fieldType(GF5)
                .generatorMatrix(ImmutableNaturalMatrixDto.builder()
                        .addMatrix(List.of(1L, 1L, 1L))
                        .addMatrix(List.of(1L, 2L, 3L))
                        .build())
                .build();
        assertEquals(expected, HammingCodeConverter.toDto(HAMMING_CODE));
    }
}