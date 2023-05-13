package com.trident.barcode.transform;

import com.trident.barcode.model.Barcode;

public interface BarcodeTransformer {
    Barcode transform(Barcode barcode);
}
