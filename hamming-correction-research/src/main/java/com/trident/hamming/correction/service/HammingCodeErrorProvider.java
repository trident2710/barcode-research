package com.trident.hamming.correction.service;

import com.trident.math.field.GFElement;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.Iterator;

public interface HammingCodeErrorProvider<FE extends GFElement<FE>> extends Iterator<FieldMatrix<FE>> {
    int errorLevel();
}
