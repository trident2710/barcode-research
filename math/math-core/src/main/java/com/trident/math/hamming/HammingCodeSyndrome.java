package com.trident.math.hamming;

import com.google.common.base.Preconditions;
import com.trident.math.field.GF;
import com.trident.math.field.GFElement;
import com.trident.math.field.GFElementUtil;
import com.trident.math.matrix.FieldMatrixUtil;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.Objects;
import java.util.Optional;

public class HammingCodeSyndrome<FE extends GFElement<FE>> {
    private final FieldMatrix<FE> syndrome;
    private final Optional<FE> errorValue;
    private final Optional<FieldMatrix<FE>> errorPosition;
    private final boolean canCorrectError;

    HammingCodeSyndrome(FieldMatrix<FE> syndrome, HammingCode<FE, ? extends GF<FE>> hammingCode) {
        this.syndrome = syndrome;
        this.errorValue = calculateErrorValue(syndrome);
        this.errorPosition = errorValue.map(value -> calculateErrorPosition(syndrome, value));
        this.canCorrectError = errorPosition
                .map(position -> canCorrectError(position, hammingCode.getFullMatrix()))
                .orElse(false);
    }

    public FieldMatrix<FE> getSyndromeRow() {
        return syndrome;
    }

    public boolean hasError() {
        return getErrorValue().isPresent();
    }

    public boolean canCorrectError() {
        return canCorrectError;
    }

    public Optional<FE> getErrorValue() {
        return errorValue;
    }

    public Optional<FieldMatrix<FE>> getErrorPosition() {
        return errorPosition;
    }

    private Optional<FE> calculateErrorValue(FieldMatrix<FE> syndrome) {
        Preconditions.checkArgument(syndrome.getRowDimension() == 1);
        return GFElementUtil.getFirstNonZero(syndrome);
    }

    private FieldMatrix<FE> calculateErrorPosition(FieldMatrix<FE> syndrome, FE errorValue) {
        Preconditions.checkArgument(!errorValue.equals(errorValue.getField().getZero()));
        return syndrome.scalarMultiply(errorValue.reciprocal());
    }

    private boolean canCorrectError(FieldMatrix<FE> errorPosition, FieldMatrix<FE> hammingFullMatrix) {
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
}
