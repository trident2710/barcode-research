package com.trident.correction;

import com.trident.math.field.GFElement;
import org.apache.commons.math3.linear.FieldMatrix;

public interface ErrorCorrectionAnalyzer<FE extends GFElement<FE>> {
    CorrectionStatus analyze(FieldMatrix<FE> message, FieldMatrix<FE> errorVector);
}
