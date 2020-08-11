package com.trident.math.io.converter;

import com.trident.math.hamming.HammingCode;
import com.trident.math.io.dto.HammingCodeDto;
import com.trident.math.io.dto.ImmutableHammingCodeDto;

public class HammingCodeConverter {

    public static HammingCodeDto toDto(HammingCode hammingCode) {
        var gfp = hammingCode.getField().modulus();
        var generatorMatrixDto = GaloisFieldOverPrimeMatrixConverter.toDto(hammingCode.getGenerator());
        return ImmutableHammingCodeDto.builder()
                .gfp(gfp)
                .generatorMatrix(generatorMatrixDto)
                .build();
    }

    public static HammingCode fromDto(HammingCodeDto dto) {
        var generatorMatrix = GaloisFieldOverPrimeMatrixConverter.fromDto(dto.gfp(), dto.generatorMatrix());
        return new HammingCode(generatorMatrix);
    }
}
