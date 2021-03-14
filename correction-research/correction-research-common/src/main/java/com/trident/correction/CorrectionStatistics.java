package com.trident.correction;

import java.util.Map;

public interface CorrectionStatistics {
    int iterations();

    int errorLevel();

    Map<CorrectionStatus, Long> statistics();
}
