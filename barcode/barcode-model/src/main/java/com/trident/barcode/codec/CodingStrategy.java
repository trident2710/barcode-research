package com.trident.barcode.codec;

import com.trident.barcode.model.Barcode;

public interface CodingStrategy {

    Barcode apply(Barcode barcode);
}
