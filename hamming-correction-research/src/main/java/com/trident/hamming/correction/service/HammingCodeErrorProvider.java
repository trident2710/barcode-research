package com.trident.hamming.correction.service;

import com.trident.math.field.GaloisFieldOverPrimeElement;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.Iterator;

public interface HammingCodeErrorProvider extends Iterator<FieldMatrix<GaloisFieldOverPrimeElement>> {
    int errorLevel();
}
