package com.trident.math.io.converter;

import com.trident.math.field.GaloisFieldOverPrimeElement;
import com.trident.math.field.GaloisFieldOverPrimeType;
import com.trident.math.io.dto.ImmutableNaturalMatrixDto;
import com.trident.math.io.dto.NaturalMatrixDto;
import com.trident.math.matrix.FieldMatrixUtil;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.ArrayList;
import java.util.List;

public class GaloisFieldOverPrimeMatrixConverter {
    public static NaturalMatrixDto toDto(FieldMatrix<GaloisFieldOverPrimeElement> matrix) {
        var result = new ArrayList<List<Long>>();
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            var row = new ArrayList<Long>();
            for (int j = 0; j < matrix.getColumnDimension(); j++) {
                row.add(matrix.getEntry(i, j).getAsLong());
            }
            result.add(row);
        }
        return ImmutableNaturalMatrixDto.builder()
                .addAllMatrix(result)
                .build();
    }

    public static FieldMatrix<GaloisFieldOverPrimeElement> fromDto(GaloisFieldOverPrimeType fieldType, NaturalMatrixDto dto) {
        var elements = dto.matrix();
        var field = fieldType.field();
        FieldMatrix<GaloisFieldOverPrimeElement> result = null;
        for (List<Long> row : elements) {
            var rowArray = row.stream()
                    .map(field::getOfValue)
                    .toArray(GaloisFieldOverPrimeElement[]::new);
            var matrixRow = FieldMatrixUtil.matrixRow(rowArray);
            result = result == null
                    ? matrixRow
                    : FieldMatrixUtil.concatBottom(result, matrixRow);

        }
        return result;
    }
}
