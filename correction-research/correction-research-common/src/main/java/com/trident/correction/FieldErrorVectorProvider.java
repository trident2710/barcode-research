package com.trident.correction;

import com.trident.math.field.GFElement;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.Iterator;

public interface FieldErrorVectorProvider<FE extends GFElement<FE>> extends Iterator<FieldMatrix<FE>> {
    int vectorLength();

    int errorLevel();
}
