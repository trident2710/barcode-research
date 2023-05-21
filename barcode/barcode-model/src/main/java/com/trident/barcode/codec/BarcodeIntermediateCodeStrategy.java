package com.trident.barcode.codec;

import com.trident.barcode.model.Barcode;

public interface BarcodeIntermediateCodeStrategy {

    Barcode buildIntermediateCode(Barcode barcode);
}
