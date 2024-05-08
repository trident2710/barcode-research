package com.trident.math.reedsolomon;

import org.apache.commons.math3.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.trident.math.field.GaloisFields.GF11;
import static com.trident.math.matrix.GaloisFieldMatrixUtil.toFieldMatrixRow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReedSolomonUtilTest {

    @Test
    void testErasureLocators() {
        var expectedLocators = List.of(GF11.getOfValue(5), GF11.getOfValue(7));
        assertEquals(expectedLocators, ReedSolomonUtil.findErasureLocators(GF11, GF11.getOfValue(2), List.of(4, 7)));
    }

    @Test
    void testCalculateErasureLocatorsPolynomial() {
        var locators = List.of(GF11.getOfValue(5), GF11.getOfValue(7));
        var expectedLocatorPolynomial = toFieldMatrixRow(new long[]{1, 10, 2}, GF11);
        assertEquals(Optional.of(expectedLocatorPolynomial), ReedSolomonUtil.calculateErasureLocatorsPolynomial(GF11, locators));
    }

    @Test
    void testCalculateSyndromeElement() {
        var encoded = toFieldMatrixRow(new long[]{2, 7, 9, 8, 0, 3, 2, 0, 3}, GF11);
        assertEquals(GF11.getOfValue(8), ReedSolomonUtil.calculateSyndromeElement(GF11, GF11.getOfValue(2), 1, encoded));
    }

    @Test
    void testCalculateSyndrome() {
        var encoded = toFieldMatrixRow(new long[]{2, 7, 9, 8, 0, 3, 2, 0, 3}, GF11);

        var expected = List.of(GF11.getOfValue(8), GF11.getOfValue(9),
                GF11.getOfValue(7), GF11.getOfValue(0), GF11.getOfValue(9), GF11.getOfValue(1));

        assertEquals(expected, ReedSolomonUtil.calculateSyndrome(6, GF11, GF11.getOfValue(2), encoded));
    }

    @Test
    void testSyndromePoly() {
        var syndrome = List.of(GF11.getOfValue(8), GF11.getOfValue(9),
                GF11.getOfValue(7), GF11.getOfValue(0), GF11.getOfValue(9), GF11.getOfValue(1));

        var expected = toFieldMatrixRow(new long[]{1, 8, 9, 7, 0, 9, 1}, GF11);

        assertEquals(expected, ReedSolomonUtil.createSyndromePolynomial(GF11, syndrome));

    }

    @Test
    void testCalculateModifiedSyndromePolynomial() {
        var syndromePoly = toFieldMatrixRow(new long[]{1, 8, 9, 7, 0, 9, 1}, GF11);

        var erasureLocatorsPoly = toFieldMatrixRow(new long[]{1, 10, 2}, GF11);

        var expected = toFieldMatrixRow(new long[]{0, 7, 3, 3, 0, 1, 3}, GF11);

        assertEquals(expected, ReedSolomonUtil.calculateModifiedSyndromePolynomial(6, GF11, Optional.of(erasureLocatorsPoly), syndromePoly));
    }

    @Test
    void testCalculateErrorSyndrome() {
        var modifiedSyndromePoly = toFieldMatrixRow(new long[]{0, 7, 3, 3, 0, 1, 3}, GF11);

        var expected = toFieldMatrixRow(new long[]{3, 0, 1, 3}, GF11);

        assertEquals(expected, ReedSolomonUtil.calculateErrorSyndrome(modifiedSyndromePoly, 2));
    }

    @Test
    void testErrorLocatorsPoly() {
        var errorsSyndrome = toFieldMatrixRow(new long[]{3, 0, 1, 3}, GF11);

        var expected = toFieldMatrixRow(new long[]{1, 8, 7}, GF11);

        assertEquals(expected, ReedSolomonUtil.calculateErrorLocatorsPolynomial(GF11, errorsSyndrome, 2).orElseThrow());
    }

    @Test
    void testErrorLocators() {
        var errorLocatorsPoly = toFieldMatrixRow(new long[]{1, 8, 7}, GF11);

        assertEquals(List.of(GF11.getOfValue(4), GF11.getOfValue(10)), ReedSolomonUtil.calculateErrorLocators(errorLocatorsPoly));
    }

    @Test
    void testCalculateMutationValuesPoly() {
        var errorLocatorsPoly = toFieldMatrixRow(new long[]{1, 8, 7}, GF11);
        var modifiedSyndromePoly = toFieldMatrixRow(new long[]{0, 7, 3, 3, 0, 1, 3}, GF11);

        var expected = toFieldMatrixRow(new long[]{1, 4, 0, 10, 1}, GF11);

        assertEquals(expected, ReedSolomonUtil.calculateMutationValuesPoly(6, GF11, errorLocatorsPoly, modifiedSyndromePoly));
    }

    @Test
    void testMutationLocators() {
        var errorLocators = List.of(GF11.getOfValue(4), GF11.getOfValue(10));
        var erasureLocators = List.of(GF11.getOfValue(5), GF11.getOfValue(7));

        var expected = List.of(GF11.getOfValue(4), GF11.getOfValue(5), GF11.getOfValue(7), GF11.getOfValue(10));
        assertEquals(expected, ReedSolomonUtil.mutationLocators(erasureLocators, errorLocators));
    }

    @Test
    void testCalculateMutationValues() {
        var mutationLocators = List.of(GF11.getOfValue(4), GF11.getOfValue(5), GF11.getOfValue(7), GF11.getOfValue(10));
        var mutationValuesPoly = toFieldMatrixRow(new long[]{1, 4, 0, 10, 1}, GF11);


        var expected = List.of(GF11.getOfValue(5), GF11.getOfValue(9), GF11.getOfValue(10), GF11.getOfValue(6));
        assertEquals(expected, ReedSolomonUtil.calculateMutationValues(GF11, mutationValuesPoly, mutationLocators));
    }

    @Test
    void testCalculateCorrectionValues() {
        var mutationLocators = List.of(GF11.getOfValue(4), GF11.getOfValue(5), GF11.getOfValue(7), GF11.getOfValue(10));
        var mutationValues = List.of(GF11.getOfValue(5), GF11.getOfValue(9), GF11.getOfValue(10), GF11.getOfValue(6));

        var expected = List.of(
                Pair.create(2, GF11.getOfValue(5)),
                Pair.create(4, GF11.getOfValue(9)),
                Pair.create(7, GF11.getOfValue(10)),
                Pair.create(5, GF11.getOfValue(6)));

        assertEquals(expected, ReedSolomonUtil.calculateCorrectionValues(mutationValues, mutationLocators));
    }
}
