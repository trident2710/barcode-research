package com.trident.math.io.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.trident.math.field.GaloisFieldOverPrimeType;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableHammingCodeDto.class)
@JsonDeserialize(as = ImmutableHammingCodeDto.class)
public interface HammingCodeDto {
    GaloisFieldOverPrimeType fieldType();

    NaturalMatrixDto generatorMatrix();
}
