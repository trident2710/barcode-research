package com.trident.math.reedsolomon;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.trident.math.field.GaloisFields.GF11;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;
import static com.trident.math.reedsolomon.ReedSolomonGeneratorPolynomials.GF_11_R6;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReedSolomonCodeTest {

    @Test
    void testEncode() {
        var message = toFieldMatrixRow(new long[]{1, 5, 3}, GF11);
        var expected = toFieldMatrixRow(new long[]{2, 7, 4, 8, 2, 8, 2, 1, 3}, GF11);
        var code = new ReedSolomonCode(GF_11_R6);

        var encoded = code.encode(message);
        assertEquals(expected, encoded);

        var corrected = code.correct(encoded, List.of());
        assertEquals(CorrectionResult.CorrectionStatus.NO_ERROR, corrected.status());

        assertEquals(message, code.decode(corrected.message()));
    }

    @Test
    void testNoErasure() {
        var message = toFieldMatrixRow(new long[]{2, 7, 9, 8, 2, 3, 2, 1, 3}, GF11);
        var code = new ReedSolomonCode(GF_11_R6);
        var expectedDecoded = toFieldMatrixRow(new long[]{2, 7, 4, 8, 2, 8, 2, 1, 3}, GF11);


        var correctionResult = code.correct(message, List.of());
        var expectedCorrection = toFieldMatrixRow(new long[]{0, 0, 5, 0, 0, 6, 0, 0, 0}, GF11);
        assertEquals(expectedCorrection, correctionResult.correctionVector().orElseThrow());
        assertEquals(expectedDecoded, correctionResult.correctedMessage().orElseThrow());

    }

    @Test
    void testDecode() {
        var code = new ReedSolomonCode(GF_11_R6);
        var message = toFieldMatrixRow(new long[]{2, 7, 9, 8, 0, 3, 2, 0, 3}, GF11);
        var expectedCorrected = toFieldMatrixRow(new long[]{2, 7, 4, 8, 2, 8, 2, 1, 3}, GF11);

        var correctionResult = code.correct(message, List.of(4, 7));
        var expectedCorrection = toFieldMatrixRow(new long[]{0, 0, 5, 0, 9, 6, 0, 10, 0}, GF11);
        assertEquals(expectedCorrection, correctionResult.correctionVector().orElseThrow());
        assertEquals(expectedCorrected, correctionResult.correctedMessage().orElseThrow());


        var expectedDecoded = toFieldMatrixRow(new long[]{1, 5, 3}, GF11);
        assertEquals(expectedDecoded, code.decode(correctionResult.correctedMessage().orElseThrow()));
    }
}
