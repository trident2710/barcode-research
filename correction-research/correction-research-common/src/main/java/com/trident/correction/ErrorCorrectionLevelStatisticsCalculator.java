package com.trident.correction;

import com.trident.math.field.GFElement;
import org.apache.commons.math3.linear.FieldMatrix;

public interface ErrorCorrectionLevelStatisticsCalculator<FE extends GFElement<FE>> {
    int errorLevel();

    CorrectionStatistics calculateForMessage(FieldMatrix<FE> message);

    CorrectionStatistics calculateForRandomMessage();
}
