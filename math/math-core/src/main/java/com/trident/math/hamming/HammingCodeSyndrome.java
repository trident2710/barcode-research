package com.trident.math.hamming;

import com.google.common.base.Preconditions;
import com.trident.math.field.GaloisField;
import com.trident.math.field.GaloisFieldElement;
import com.trident.math.field.GaloisFieldElementUtil;
import com.trident.math.matrix.FieldMatrixUtil;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.Objects;
import java.util.Optional;

public class HammingCodeSyndrome<E extends GaloisFieldElement<E>> {
    private final FieldMatrix<E> syndrome;
    private final Optional<E> errorValue;
    private final Optional<FieldMatrix<E>> errorPosition;
    private final boolean canCorrectError;

    HammingCodeSyndrome(FieldMatrix<E> syndrome, HammingCode<E, ? extends GaloisField<E>> hammingCode) {
        this.syndrome = syndrome;
        this.errorValue = calculateErrorValue(syndrome);
        this.errorPosition = errorValue.map(value -> calculateErrorPosition(syndrome, value));
        this.canCorrectError = errorPosition
                .map(position -> canCorrectError(position, hammingCode.getFullMatrix()))
                .orElse(false);
    }

    public FieldMatrix<E> getSyndromeRow() {
        return syndrome;
    }

    public boolean hasError() {
        return getErrorValue().isPresent();
    }

    public boolean canCorrectError() {
        return canCorrectError;
    }

    public Optional<E> getErrorValue() {
        return errorValue;
    }

    public Optional<FieldMatrix<E>> getErrorPosition() {
        return errorPosition;
    }

    private Optional<E> calculateErrorValue(FieldMatrix<E> syndrome) {
        Preconditions.checkArgument(syndrome.getRowDimension() == 1);
        return GaloisFieldElementUtil.getFirstNonZero(syndrome);
    }

    private FieldMatrix<E> calculateErrorPosition(FieldMatrix<E> syndrome, E errorValue) {
        Preconditions.checkArgument(!errorValue.equals(errorValue.getField().getZero()));
        return syndrome.scalarMultiply(errorValue.reciprocal());
    }

    private boolean canCorrectError(FieldMatrix<E> errorPosition, FieldMatrix<E> hammingFullMatrix) {
        return FieldMatrixUtil.hasColumn(hammingFullMatrix, errorPosition.transpose());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HammingCodeSyndrome<?> that = (HammingCodeSyndrome<?>) o;
        return canCorrectError == that.canCorrectError &&
                Objects.equals(syndrome, that.syndrome) &&
                Objects.equals(errorValue, that.errorValue) &&
                Objects.equals(errorPosition, that.errorPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(syndrome, errorValue, errorPosition, canCorrectError);
    }

    @Override
    public String toString() {
        return "HammingCodeSyndrome{" +
                "syndrome=" + syndrome +
                ", errorValue=" + errorValue +
                ", errorPosition=" + errorPosition +
                ", canCorrectError=" + canCorrectError +
                '}';
    }
}
