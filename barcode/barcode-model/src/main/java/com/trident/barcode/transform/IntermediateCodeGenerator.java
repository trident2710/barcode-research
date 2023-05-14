package com.trident.barcode.transform;

import com.trident.barcode.model.BarcodeSign;

import java.util.List;

@Deprecated
public interface IntermediateCodeGenerator {
    List<BarcodeSign> buildIntermediateCode(String message);
}
