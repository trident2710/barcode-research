package com.trident.hamming.correction.report;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.trident.math.io.dto.HammingCodeDto;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableHammingCorrectionReport.class)
@JsonDeserialize(as = ImmutableHammingCorrectionReport.class)
public interface HammingCorrectionReport {
    HammingCodeDto hammingCode();

    int iterations();

    int errorLevel();

    int corrected();

    int detected();

    int noErrors();
}
