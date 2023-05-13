package com.trident.barcode;

import com.trident.barcode.model.Code;

public interface BarcodeEncoder {
    Code encode(String message);
}
