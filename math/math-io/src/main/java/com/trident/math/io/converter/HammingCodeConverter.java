package com.trident.math.io.converter;

import com.trident.math.field.GF;
import com.trident.math.field.GFElement;
import com.trident.math.field.GFP;
import com.trident.math.field.GFPMSimple;
import com.trident.math.hamming.HammingCode;
import com.trident.math.io.dto.GaloisFieldDto;
import com.trident.math.io.dto.HammingCodeDto;
import com.trident.math.io.dto.ImmutableGaloisFieldDto;
import com.trident.math.io.dto.ImmutableHammingCodeDto;

public class HammingCodeConverter {

    public static <FE extends GFElement<FE>, F extends GF<FE>> HammingCodeDto toDto(HammingCode<FE, F> hammingCode) {
        var generatorMatrixDto = GaloisFieldMatrixConverter.toDto(hammingCode.getGenerator());
        return ImmutableHammingCodeDto.builder()
                .field(toGFDto(hammingCode.getField()))
                .generatorMatrix(generatorMatrixDto)
                .build();
    }

    public static <FE extends GFElement<FE>, F extends GF<FE>> HammingCode<FE, F> fromDto(HammingCodeDto dto, Class<F> fieldClass, FE[] arrayRef) {
        var field = fieldClass.cast(fromGFDto(dto.field()));
        var generatorMatrix = GaloisFieldMatrixConverter.fromDto(field, dto.generatorMatrix(), arrayRef);
        return new HammingCode<>(generatorMatrix);
    }

    @SuppressWarnings("ConstantConditions")
    private static GF<?> fromGFDto(GaloisFieldDto dto) {
        switch (dto.type()) {
            case GFP:
                return GFP.of(dto.prime());
            case GFPM:
                return GFPMSimple.of(dto.prime(), dto.exponent(), dto.irreduciblePoly());
            default:
                throw new IllegalArgumentException("Unsupported: " + dto);
        }
    }

    private static GaloisFieldDto toGFDto(GF<?> field) {
        if (field instanceof GFP) {
            return ImmutableGaloisFieldDto.builder()
                    .type(GaloisFieldDto.Type.GFP)
                    .prime(field.prime())
                    .build();
        }

        if (field instanceof GFPMSimple) {
            var gfpm = (GFPMSimple) field;
            return ImmutableGaloisFieldDto.builder()
                    .type(GaloisFieldDto.Type.GFPM)
                    .prime(field.prime())
                    .exponent(gfpm.exponent())
                    .irreduciblePoly(gfpm.irreduciblePoly())
                    .build();
        }

        throw new IllegalArgumentException("Unsupported: " + field);
    }
}
