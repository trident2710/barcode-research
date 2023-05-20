package com.trident.barcode.model;

import com.trident.math.bch.BCHCode;
import com.trident.math.bch.BCHCodeSyndrome;
import com.trident.math.bch.BCHCodes;
import com.trident.math.field.GFElement;
import com.trident.math.matrix.FieldMatrixUtil;
import org.apache.commons.math3.linear.FieldMatrix;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BASE41BarcodeDictionaryTest {

    @Test
    void testSignCorrectingCodes() {
        for (BarcodeSign sign : BarcodeDictionaries.BASE_41.signs()) {
            var fieldMatrix = toFieldMatrix(BCHCodes.BCH_GF4_9_3, sign.codeRepresentation());
            var syndrome = BCHCodes.BCH_GF4_9_3.syndrome(fieldMatrix);

            assertEquals(syndrome.getCorrectionResult().getCorrectionStatus(), BCHCodeSyndrome.CorrectionStatus.NO_ERROR, sign.toString());
        }
    }

    private <Symbol extends GFElement<Symbol>> FieldMatrix<Symbol> toFieldMatrix(BCHCode<Symbol, ?> bch, Code code) {
        return FieldMatrixUtil.matrixRow(code.data().stream()
                .map(it -> bch.symbolField().getOfValue(it))
                .collect(Collectors.toList()));
    }

}