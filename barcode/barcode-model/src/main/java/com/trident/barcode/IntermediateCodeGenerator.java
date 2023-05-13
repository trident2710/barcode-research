package com.trident.barcode;

import com.trident.barcode.model.BarcodeSign;

import java.util.List;

public interface IntermediateCodeGenerator {
    List<BarcodeSign> buildIntermediateCode(String message);
}
