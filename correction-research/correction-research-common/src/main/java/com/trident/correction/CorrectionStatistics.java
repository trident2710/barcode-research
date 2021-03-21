package com.trident.correction;

import org.immutables.value.Value;

import java.util.Map;

@Value.Immutable
public interface CorrectionStatistics {
    int iterations();

    int errorLevel();

    Map<CorrectionStatus, Long> statistics();
}
