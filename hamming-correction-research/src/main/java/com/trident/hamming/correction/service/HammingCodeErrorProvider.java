package com.trident.hamming.correction.service;

import com.trident.math.field.GaloisFieldElement;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.Iterator;

public interface HammingCodeErrorProvider<GFElement extends GaloisFieldElement<GFElement>> extends Iterator<FieldMatrix<GFElement>> {
    int errorLevel();
}
