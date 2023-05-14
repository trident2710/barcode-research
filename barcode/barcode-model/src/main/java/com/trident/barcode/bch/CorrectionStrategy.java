package com.trident.barcode.bch;

import com.trident.barcode.model.Code;

public interface CorrectionStrategy {

    Correction correct(Code code);
}
