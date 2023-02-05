package com.trident.barcode.research;

import com.trident.barcode.research.model.Code;

public interface BarcodeCodec {

    Code encode(String message);

    String decode(Code code);
}
