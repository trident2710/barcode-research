package com.trident.math.io.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableHammingCodeDto.class)
@JsonDeserialize(as = ImmutableHammingCodeDto.class)
public interface HammingCodeDto {
    long gfp();

    NaturalMatrixDto generatorMatrix();
}
