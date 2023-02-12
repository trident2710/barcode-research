package com.trident.barcode.research;

import com.trident.barcode.research.model.Code;

public interface BarcodeEncoder {
    Code encode(String message);
}
