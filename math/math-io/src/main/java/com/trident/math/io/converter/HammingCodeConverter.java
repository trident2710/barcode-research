package com.trident.math.io.converter;

import com.trident.math.field.GaloisFieldOverPrimeType;
import com.trident.math.hamming.HammingCode;
import com.trident.math.io.dto.HammingCodeDto;
import com.trident.math.io.dto.ImmutableHammingCodeDto;

public class HammingCodeConverter {

    public static HammingCodeDto toDto(HammingCode hammingCode) {
        var fieldType = GaloisFieldOverPrimeType.getForField(hammingCode.getGenerator().getField());
        var generatorMatrixDto = GaloisFieldOverPrimeMatrixConverter.toDto(hammingCode.getGenerator());
        return ImmutableHammingCodeDto.builder()
                .fieldType(fieldType)
                .generatorMatrix(generatorMatrixDto)
                .build();
    }

    public static HammingCode fromDto(HammingCodeDto dto) {
        var generatorMatrix = GaloisFieldOverPrimeMatrixConverter.fromDto(dto.fieldType(), dto.generatorMatrix());
        return new HammingCode(generatorMatrix);
    }
}
