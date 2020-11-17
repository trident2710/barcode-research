package com.trident.math.io.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@Value.Immutable
@JsonSerialize(as = ImmutableGaloisFieldDto.class)
@JsonDeserialize(as = ImmutableGaloisFieldDto.class)
public interface GaloisFieldDto {

    Type type();

    long prime();

    @Nullable
    Integer exponent();

    // x^2 + x + 2 -> [2, 1, 1]
    @Nullable
    long[] irreduciblePoly();

    enum Type {
        GFP,
        GFPM
    }
}
