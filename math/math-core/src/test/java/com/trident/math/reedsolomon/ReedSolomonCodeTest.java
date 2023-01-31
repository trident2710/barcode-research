package com.trident.math.reedsolomon;

import org.apache.commons.math3.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

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

    @Test
    void testErasureLocators() {
        var code = new ReedSolomonCode(GF_11_R6);
        var expectedLocators = List.of(GF11.getOfValue(5), GF11.getOfValue(7));
        assertEquals(expectedLocators, code.findErasureLocators(List.of(4, 7)));
    }

    @Test
    void testCalculateErasureLocatorsPolynomial() {
        var code = new ReedSolomonCode(GF_11_R6);
        var locators = List.of(GF11.getOfValue(5), GF11.getOfValue(7));
        var expectedLocatorPolynomial = toFieldMatrixRow(new long[]{1, 10, 2}, GF11);
        assertEquals(Optional.of(expectedLocatorPolynomial), code.calculateErasureLocatorsPolynomial(locators));
    }

    @Test
    void testCorrectErrorsCount() {
        var code = new ReedSolomonCode(GF_11_R6);
        assertEquals(2, code.correctErrorsCount(2));
    }

    @Test
    void testCalculateSyndromeElement() {
        var code = new ReedSolomonCode(GF_11_R6);
        var encoded = toFieldMatrixRow(new long[]{2, 7, 9, 8, 0, 3, 2, 0, 3}, GF11);
        assertEquals(GF11.getOfValue(8), code.calculateSyndromeElement(1, encoded));
    }

    @Test
    void testCalculateSyndrome() {
        var code = new ReedSolomonCode(GF_11_R6);
        var encoded = toFieldMatrixRow(new long[]{2, 7, 9, 8, 0, 3, 2, 0, 3}, GF11);

        var expected = List.of(GF11.getOfValue(8), GF11.getOfValue(9),
                GF11.getOfValue(7), GF11.getOfValue(0), GF11.getOfValue(9), GF11.getOfValue(1));

        assertEquals(expected, code.calculateSyndrome(encoded));
    }

    @Test
    void testSyndromePoly() {
        var code = new ReedSolomonCode(GF_11_R6);

        var syndrome = List.of(GF11.getOfValue(8), GF11.getOfValue(9),
                GF11.getOfValue(7), GF11.getOfValue(0), GF11.getOfValue(9), GF11.getOfValue(1));

        var expected = toFieldMatrixRow(new long[]{1, 8, 9, 7, 0, 9, 1}, GF11);

        assertEquals(expected, code.createSyndromePolynomial(syndrome));

    }

    @Test
    void testCalculateModifiedSyndromePolynomial() {
        var code = new ReedSolomonCode(GF_11_R6);

        var syndromePoly = toFieldMatrixRow(new long[]{1, 8, 9, 7, 0, 9, 1}, GF11);

        var erasureLocatorsPoly = toFieldMatrixRow(new long[]{1, 10, 2}, GF11);

        var expected = toFieldMatrixRow(new long[]{0, 7, 3, 3, 0, 1, 3}, GF11);

        assertEquals(expected, code.calculateModifiedSyndromePolynomial(Optional.of(erasureLocatorsPoly), syndromePoly));
    }

    @Test
    void testCalculateErrorSyndrome() {
        var code = new ReedSolomonCode(GF_11_R6);

        var modifiedSyndromePoly = toFieldMatrixRow(new long[]{0, 7, 3, 3, 0, 1, 3}, GF11);

        var expected = toFieldMatrixRow(new long[]{3, 0, 1, 3}, GF11);

        assertEquals(expected, code.calculateErrorSyndrome(modifiedSyndromePoly, 2));
    }

    @Test
    void testErrorLocatorsPoly() {
        var code = new ReedSolomonCode(GF_11_R6);

        var errorsSyndrome = toFieldMatrixRow(new long[]{3, 0, 1, 3}, GF11);

        var expected = toFieldMatrixRow(new long[]{1, 8, 7}, GF11);

        assertEquals(expected, code.calculateErrorLocatorsPolynomial(errorsSyndrome, 2).orElseThrow());
    }

    @Test
    void testErrorLocators() {
        var code = new ReedSolomonCode(GF_11_R6);

        var errorLocatorsPoly = toFieldMatrixRow(new long[]{1, 8, 7}, GF11);

        assertEquals(List.of(GF11.getOfValue(4), GF11.getOfValue(10)), code.calculateErrorLocators(errorLocatorsPoly));
    }

    @Test
    void testCalculateMutationValuesPoly() {
        var code = new ReedSolomonCode(GF_11_R6);

        var errorLocatorsPoly = toFieldMatrixRow(new long[]{1, 8, 7}, GF11);
        var modifiedSyndromePoly = toFieldMatrixRow(new long[]{0, 7, 3, 3, 0, 1, 3}, GF11);

        var expected = toFieldMatrixRow(new long[]{1, 4, 0, 10, 1}, GF11);


        assertEquals(expected, code.calculateMutationValuesPoly(errorLocatorsPoly, modifiedSyndromePoly));
    }

    @Test
    void testMutationLocators() {
        var code = new ReedSolomonCode(GF_11_R6);

        var errorLocators = List.of(GF11.getOfValue(4), GF11.getOfValue(10));
        var erasureLocators = List.of(GF11.getOfValue(5), GF11.getOfValue(7));

        var expected = List.of(GF11.getOfValue(4), GF11.getOfValue(5), GF11.getOfValue(7), GF11.getOfValue(10));
        assertEquals(expected, code.mutationLocators(erasureLocators, errorLocators));
    }

    @Test
    void testCalculateMutationValues() {
        var code = new ReedSolomonCode(GF_11_R6);
        var mutationLocators = List.of(GF11.getOfValue(4), GF11.getOfValue(5), GF11.getOfValue(7), GF11.getOfValue(10));
        var mutationValuesPoly = toFieldMatrixRow(new long[]{1, 4, 0, 10, 1}, GF11);


        var expected = List.of(GF11.getOfValue(5), GF11.getOfValue(9), GF11.getOfValue(10), GF11.getOfValue(6));
        assertEquals(expected, code.calculateMutationValues(mutationValuesPoly, mutationLocators));
    }

    @Test
    void testCalculateCorrectionValues() {
        var code = new ReedSolomonCode(GF_11_R6);
        var mutationLocators = List.of(GF11.getOfValue(4), GF11.getOfValue(5), GF11.getOfValue(7), GF11.getOfValue(10));
        var mutationValues = List.of(GF11.getOfValue(5), GF11.getOfValue(9), GF11.getOfValue(10), GF11.getOfValue(6));

        var expected = List.of(
                Pair.create(2, GF11.getOfValue(5)),
                Pair.create(4, GF11.getOfValue(9)),
                Pair.create(7, GF11.getOfValue(10)),
                Pair.create(5, GF11.getOfValue(6)));

        assertEquals(expected, code.calculateCorrectionValues(mutationValues, mutationLocators));
    }

}