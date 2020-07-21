package com.trident.math.io.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutableNaturalMatrixDto.class)
@JsonDeserialize(as = ImmutableNaturalMatrixDto.class)
public interface NaturalMatrixDto {
    List<List<Long>> matrix();
}
