package com.trident.barcode;

import com.trident.barcode.model.Code;

public interface BarcodeDecoder {
    String decode(Code code);
}
