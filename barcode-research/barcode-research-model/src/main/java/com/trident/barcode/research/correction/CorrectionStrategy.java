package com.trident.barcode.research.correction;

import com.trident.barcode.research.model.Code;

public interface CorrectionStrategy {

    Correction correct(Code code);
}
