package com.trident.barcode.codec;

import com.trident.barcode.model.Code;

public interface BarcodeDecoder {
    String decode(Code code);
}
