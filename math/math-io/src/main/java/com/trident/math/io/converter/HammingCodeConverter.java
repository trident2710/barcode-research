package com.trident.math.io.converter;

import com.trident.math.field.GaloisFieldOverPrime;
import com.trident.math.field.GaloisFieldOverPrimeElement;
import com.trident.math.hamming.HammingCode;
import com.trident.math.io.dto.HammingCodeDto;
import com.trident.math.io.dto.ImmutableHammingCodeDto;

public class HammingCodeConverter {

    public static HammingCodeDto toDto(HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> hammingCode) {
        var gfp = hammingCode.getField().modulus();
        var generatorMatrixDto = GaloisFieldOverPrimeMatrixConverter.toDto(hammingCode.getGenerator());
        return ImmutableHammingCodeDto.builder()
                .gfp(gfp)
                .generatorMatrix(generatorMatrixDto)
                .build();
    }

    public static HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> fromDto(HammingCodeDto dto) {
        var generatorMatrix = GaloisFieldOverPrimeMatrixConverter.fromDto(dto.gfp(), dto.generatorMatrix());
        return new HammingCode<>(generatorMatrix);
    }
}
