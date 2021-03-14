package com.trident.correction;

import com.trident.math.field.GFElement;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.Map;

public interface ErrorCorrectionTotalStatisticsCalculator<FE extends GFElement<FE>> {

    Map<Integer, CorrectionStatistics> calculateForMessageByErrorLevel(FieldMatrix<FE> message);

    Map<Integer, CorrectionStatistics> calculateForRandomMessageByErrorLevel();
}
