package com.trident.math.io.converter;

import com.trident.math.field.GaloisFieldOverPrimeElement;
import com.trident.math.io.dto.ImmutableNaturalMatrixDto;
import com.trident.math.io.dto.NaturalMatrixDto;
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
}
