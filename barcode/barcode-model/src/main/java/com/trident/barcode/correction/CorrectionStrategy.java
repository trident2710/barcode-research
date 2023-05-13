package com.trident.barcode.correction;

import com.trident.barcode.model.Code;

public interface CorrectionStrategy {

    Correction correct(Code code);
}
