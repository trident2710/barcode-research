package com.trident.math.io.converter;

import com.trident.math.field.GaloisField;
import com.trident.math.field.GaloisFieldElement;
import com.trident.math.field.GaloisFieldOverPoly;
import com.trident.math.field.GaloisFieldOverPrime;
import com.trident.math.hamming.HammingCode;
import com.trident.math.io.dto.GaloisFieldDto;
import com.trident.math.io.dto.HammingCodeDto;
import com.trident.math.io.dto.ImmutableGaloisFieldDto;
import com.trident.math.io.dto.ImmutableHammingCodeDto;

public class HammingCodeConverter {

    public static <GFElement extends GaloisFieldElement<GFElement>, GF extends GaloisField<GFElement>> HammingCodeDto toDto(HammingCode<GFElement, GF> hammingCode) {
        var generatorMatrixDto = GaloisFieldMatrixConverter.toDto(hammingCode.getGenerator());
        return ImmutableHammingCodeDto.builder()
                .field(toGFDto(hammingCode.getField()))
                .generatorMatrix(generatorMatrixDto)
                .build();
    }

    public static <GFElement extends GaloisFieldElement<GFElement>, GF extends GaloisField<GFElement>> HammingCode<GFElement, GF> fromDto(HammingCodeDto dto, Class<GF> fieldClass, GFElement[] arrayRef) {
        var field = fieldClass.cast(fromGFDto(dto.field()));
        var generatorMatrix = GaloisFieldMatrixConverter.fromDto(field, dto.generatorMatrix(), arrayRef);
        return new HammingCode<>(generatorMatrix);
    }

    @SuppressWarnings("ConstantConditions")
    private static GaloisField<?> fromGFDto(GaloisFieldDto dto) {
        switch (dto.type()) {
            case GFP:
                return new GaloisFieldOverPrime(dto.prime());
            case GFPM:
                return new GaloisFieldOverPoly(dto.prime(), dto.exponent(), dto.irreduciblePoly());
            default:
                throw new IllegalArgumentException("Unsupported: " + dto);

        }
    }

    private static GaloisFieldDto toGFDto(GaloisField<?> field) {
        if (field instanceof GaloisFieldOverPrime) {
            return ImmutableGaloisFieldDto.builder()
                    .type(GaloisFieldDto.Type.GFP)
                    .prime(field.prime())
                    .build();
        }

        if (field instanceof GaloisFieldOverPoly) {
            var gfpm = (GaloisFieldOverPoly) field;
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
