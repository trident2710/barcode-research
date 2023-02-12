package com.trident.barcode.research;

import com.trident.barcode.research.model.Code;

public interface BarcodeDecoder {
    String decode(Code code);
}
