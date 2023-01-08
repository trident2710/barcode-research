package com.trident.math.reedsolomon;

import com.trident.math.field.GFP;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;
import static com.trident.math.reedsolomon.ReedSolomonGeneratorPolynomials.GF_59_R6;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReedSolomone59r6Test {

    @Test
    void testEncode() {

        var message = toFieldMatrixRow(new long[]{4, 58, 12, 0, 28, 31}, GFP.of(59));
        var expectedEncoded = toFieldMatrixRow(new long[]{47, 48, 47, 41, 25, 18, 3, 15, 57, 36, 16, 31}, GFP.of(59));
        var code = new ReedSolomonCode(GF_59_R6);

        assertEquals(expectedEncoded, code.encode(message));
    }

    @Test
    void testDecode() {
        var encoded = toFieldMatrixRow(new long[]{47, 48, 18, 41, 25, 0, 3, 43, 57, 0, 16, 31}, GFP.of(59));
        var code = new ReedSolomonCode(GF_59_R6);

        var decoded = code.decode(encoded, List.of(5, 9));
        assertEquals(CorrectionResult.CorrectionStatus.SUCCESS, decoded.status());

        var expectedCorrected = toFieldMatrixRow(new long[]{47, 48, 47, 41, 25, 18, 3, 15, 57, 36, 16, 31}, GFP.of(59));
        assertEquals(expectedCorrected, decoded.correctedMessage().orElseThrow());
    }
}
