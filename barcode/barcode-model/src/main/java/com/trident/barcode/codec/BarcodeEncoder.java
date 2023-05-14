package com.trident.barcode.codec;

import com.trident.barcode.model.Code;

public interface BarcodeEncoder {
    Code encode(String message);
}
