package com.trident.math.io.converter;

import com.trident.math.field.GF;
import com.trident.math.field.GFElement;
import com.trident.math.io.dto.ImmutableNaturalMatrixDto;
import com.trident.math.io.dto.NaturalMatrixDto;
import com.trident.math.matrix.FieldMatrixUtil;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GaloisFieldMatrixConverter {
    public static <FE extends GFElement<FE>> NaturalMatrixDto toDto(FieldMatrix<FE> matrix) {
        var result = new ArrayList<List<Long>>();
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            var row = new ArrayList<Long>();
            for (int j = 0; j < matrix.getColumnDimension(); j++) {
                row.add(matrix.getEntry(i, j).digitalRepresentation());
            }
            result.add(row);
        }
        return ImmutableNaturalMatrixDto.builder()
                .addAllMatrix(result)
                .build();
    }

    public static <FE extends GFElement<FE>> FieldMatrix<FE> fromDto(GF<FE> field, NaturalMatrixDto dto, FE[] arrayRef) {
        var elements = dto.matrix();
        FieldMatrix<FE> result = null;
        for (List<Long> row : elements) {
            var rowArray = row.stream()
                    .map(field::getOfValue)
                    .collect(Collectors.toList())
                    .toArray(arrayRef);
            var matrixRow = FieldMatrixUtil.matrixRow(rowArray);
            result = result == null
                    ? matrixRow
                    : FieldMatrixUtil.concatBottom(result, matrixRow);

        }
        return result;
    }
}
