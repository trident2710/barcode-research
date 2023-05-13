package com.trident.barcode.correction;

import com.trident.math.bch.BCHCodes;

public final class CorrectionStrategies {

    public static final CorrectionStrategy BCH_9_3 = new BCHCorrectionStrategy<>(BCHCodes.BCH_GF4_9_3);

    private CorrectionStrategies() {
    }
}
