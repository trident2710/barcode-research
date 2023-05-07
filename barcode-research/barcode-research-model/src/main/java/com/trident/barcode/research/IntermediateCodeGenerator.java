package com.trident.barcode.research;

import com.trident.barcode.research.model.BarcodeSign;

import java.util.List;

public interface IntermediateCodeGenerator {
    List<BarcodeSign> buildIntermediateCode(String message);
}
