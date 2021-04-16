package com.trident.math.bch;

import com.google.common.base.Preconditions;
import com.trident.math.field.GF;
import com.trident.math.field.GFElement;
import com.trident.math.field.GFPMElement;
import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.linear.FieldMatrix;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.trident.math.bch.BCHCodeSyndrome.CorrectionStatus.CORRECTED_1;
import static com.trident.math.bch.BCHCodeSyndrome.CorrectionStatus.CORRECTED_2;
import static com.trident.math.bch.BCHCodeSyndrome.CorrectionStatus.ERR_ERROR_COUNT;
import static com.trident.math.bch.BCHCodeSyndrome.CorrectionStatus.ERR_ERROR_VALUE_0;
import static com.trident.math.bch.BCHCodeSyndrome.CorrectionStatus.ERR_NO_ROOTS;
import static com.trident.math.bch.BCHCodeSyndrome.CorrectionStatus.ERR_POSITION_OUT_OF_RANGE;
import static com.trident.math.bch.BCHCodeSyndrome.CorrectionStatus.ERR_S1_0;
import static com.trident.math.bch.BCHCodeSyndrome.CorrectionStatus.ERR_SIGMA_ZERO;
import static com.trident.math.bch.BCHCodeSyndrome.CorrectionStatus.NO_ERROR;
import static com.trident.math.field.FiniteFieldEquation.solveLinearEquation;
import static com.trident.math.field.FiniteFieldEquation.solveSquaredEquation;
import static com.trident.math.matrix.FieldMatrixUtil.createMatrixOfRows;
import static com.trident.math.matrix.FieldMatrixUtil.isZero;
import static com.trident.math.matrix.FieldMatrixUtil.matrixColumn;
import static com.trident.math.matrix.FieldMatrixUtil.matrixRow;
import static com.trident.math.matrix.FieldMatrixUtil.matrixRowOfValue;

public class BCHCodeSyndrome<Symbol extends GFElement<Symbol>, Locator extends GFPMElement<Locator>> {
    private final BCHCode<Symbol, Locator> bch;
    private final FieldMatrix<Symbol> message;
    private final FieldMatrix<Locator> syndrome;
    private final CorrectionResult<Symbol> correctionResult;

    BCHCodeSyndrome(BCHCode<Symbol, Locator> bch,
                    FieldMatrix<Symbol> message,
                    FieldMatrix<Locator> syndrome) {
        this.bch = bch;
        this.message = message;
        this.syndrome = syndrome;
        this.correctionResult = findCorrection(bch, message, syndrome);
    }

    public boolean hasError() {
        return getCorrectionResult().getErrorCount() > 0;
    }

    public CorrectionResult<Symbol> getCorrectionResult() {
        return correctionResult;
    }

    public FieldMatrix<Symbol> getMessage() {
        return message;
    }

    public FieldMatrix<Locator> getSyndrome() {
        return syndrome;
    }

    private CorrectionResult<Symbol> findCorrection(BCHCode<Symbol, Locator> bch, FieldMatrix<Symbol> message, FieldMatrix<Locator> syndrome) {
        if (isZero(syndrome)) {
            return new CorrectionResult<>(NO_ERROR, null);
        }

        // M = (S1 S2)
        //     (S2 S3)
        var syndromeMatrix = createSyndromeMatrix(syndrome);
        // detM = S1*S3 - S2^2
        var determinant = calculateDeterminant2x2Matrix(syndromeMatrix);

        if (determinant.equals(syndromeMatrix.getField().getZero())) {
            return findSingleError(message, syndrome);
        } else {
            return findDoubleError(bch, message, syndrome);
        }
    }

    private FieldMatrix<Locator> createSyndromeMatrix(FieldMatrix<Locator> syndrome) {
        return createMatrixOfRows(
                matrixRow(syndrome.getEntry(0, 0), syndrome.getEntry(0, 1)),
                matrixRow(syndrome.getEntry(0, 1), syndrome.getEntry(0, 2)));
    }

    private Locator calculateDeterminant2x2Matrix(FieldMatrix<Locator> matrix2x2) {
        Preconditions.checkArgument(matrix2x2.getColumnDimension() == 2);
        Preconditions.checkArgument(matrix2x2.getRowDimension() == 2);
        return (matrix2x2.getEntry(0, 0).multiply(matrix2x2.getEntry(1, 1)))
                .subtract(matrix2x2.getEntry(0, 1).multiply(matrix2x2.getEntry(1, 0)));
    }

    private FieldMatrix<Locator> calculateInverse2x2Matrix(FieldMatrix<Locator> matrix2x2) {
        Preconditions.checkArgument(matrix2x2.getColumnDimension() == 2);
        Preconditions.checkArgument(matrix2x2.getRowDimension() == 2);
        var determinant = calculateDeterminant2x2Matrix(matrix2x2);
        var inverse = createMatrixOfRows(
                matrixRow(matrix2x2.getEntry(1, 1), matrix2x2.getEntry(0, 1).negate()),
                matrixRow(matrix2x2.getEntry(1, 0).negate(), matrix2x2.getEntry(0, 0)));
        return inverse.scalarMultiply(determinant.reciprocal());
    }

    private CorrectionResult<Symbol> findSingleError(FieldMatrix<Symbol> message, FieldMatrix<Locator> syndrome) {
        var S1 = syndrome.getEntry(0, 0);
        var S2 = syndrome.getEntry(0, 1);
        if (S1.equals(syndrome.getField().getZero())) {
            return new CorrectionResult<>(ERR_S1_0, null);
        }
        // sigma = - S2 / S1
        var sigma = S2.divide(S1).negate();
        var field = (GF<Locator>) syndrome.getField();
        // sigma * x + 1 = 0
        // x = a^j
        if (sigma.equals(syndrome.getField().getZero())) {
            return new CorrectionResult<>(ERR_SIGMA_ZERO, null);
        }
        var x = solveLinearEquation(field, sigma, field.getOne());
        Preconditions.checkNotNull(x, "x should not be null");
        var X = x.reciprocal();
        int j = bch.getPowerRepresentationMapper().getPower(X);
        var symbolField = (GF<Symbol>) message.getField();
        var errorValue = symbolField.getOfValue(x.multiply(syndrome.getEntry(0, 0)).digitalRepresentation());
        var correction = matrixRowOfValue(symbolField.getZero(), message.getColumnDimension());
        if (j >= message.getColumnDimension()) {
            return new CorrectionResult<>(ERR_POSITION_OUT_OF_RANGE, null);
        }
        correction.setEntry(0, j, errorValue);
        return new CorrectionResult<>(CORRECTED_1, correction);
    }

    private CorrectionResult<Symbol> findDoubleError(BCHCode<Symbol, Locator> bch, FieldMatrix<Symbol> message, FieldMatrix<Locator> syndrome) {
        var M = createSyndromeMatrix(syndrome);
        // M^-1 = 1/det * (S3 -S2)
        //                (-S2 S1)
        var M_inv = calculateInverse2x2Matrix(M);
        // S_3_4 = (-S3)
        //         (-S4)
        var S_3_4 = matrixColumn(syndrome.getEntry(0, 2).negate(), syndrome.getEntry(0, 3).negate());
        // (sig1)  = M^-1 * S_3_4
        // (sig2)
        var sigma = M_inv.multiply(S_3_4);

        var field = (GF<Locator>) syndrome.getField();
        // x1,x2 -> sig2*x^2 + sig1*x + 1 = 0
        var x = solveSquaredEquation(field, sigma.getEntry(0, 0), sigma.getEntry(1, 0), field.getOne());

        if (x.size() != 2) {
            return new CorrectionResult<>(ERR_NO_ROOTS, null);
        }

        // X1, X2 = x1^-1, x2^-1
        var X = x.stream().map(FieldElement::reciprocal).collect(Collectors.toList());

        // X = (X1 X2)
        //     (X1^2 X2^2)
        var Xmatrix = createMatrixOfRows(
                matrixRow(X.get(0), X.get(1)),
                matrixRow(X.get(0).multiply(X.get(0)), X.get(1).multiply(X.get(1))));

        var X_inv = calculateInverse2x2Matrix(Xmatrix);

        var S_1_2 = matrixColumn(syndrome.getEntry(0, 0), syndrome.getEntry(0, 1));
        // (Y1) = (X1, X2)     *     (S1)
        // (Y2)   (X1^2, X2^2)       (S2)
        var Y = X_inv.multiply(S_1_2);

        var errorPositions = X.stream().map(e -> bch.getPowerRepresentationMapper().getPower(e)).collect(Collectors.toList());

        if (new HashSet<>(errorPositions).size() != 2) {
            return new CorrectionResult<>(ERR_ERROR_COUNT, null);
        }

        if (errorPositions.stream().anyMatch(p -> p >= message.getColumnDimension())) {
            return new CorrectionResult<>(ERR_POSITION_OUT_OF_RANGE, null);
        }

        var symbolField = (GF<Symbol>) message.getField();
        var errorValues = List.of(Y.getEntry(0, 0), Y.getEntry(1, 0)).stream()
                .map(e -> symbolField.getOfValue(e.digitalRepresentation()))
                .collect(Collectors.toList());

        if (errorValues.stream().anyMatch(v -> symbolField.getZero().equals(v))) {
            return new CorrectionResult<>(ERR_ERROR_VALUE_0, null);
        }

        var correction = matrixRowOfValue(symbolField.getZero(), message.getColumnDimension());
        correction.setEntry(0, errorPositions.get(0), errorValues.get(0));
        correction.setEntry(0, errorPositions.get(1), errorValues.get(1));

        return new CorrectionResult<>(CORRECTED_2, correction);
    }

    public enum CorrectionStatus {
        CORRECTED_1,
        CORRECTED_2,
        ERR_S1_0,
        ERR_SIGMA_ZERO,
        ERR_NO_ROOTS,
        ERR_POSITION_OUT_OF_RANGE,
        ERR_ERROR_COUNT,
        ERR_ERROR_VALUE_0,
        NO_ERROR;
    }

    public static final class CorrectionResult<Symbol extends GFElement<Symbol>> {
        private final CorrectionStatus correctionStatus;
        private final FieldMatrix<Symbol> correction;
        private final int errorCount;

        private CorrectionResult(CorrectionStatus correctionStatus,
                                 FieldMatrix<Symbol> correction) {
            this.correctionStatus = correctionStatus;
            this.correction = correction;
            this.errorCount = errorCount(correction);
        }

        public CorrectionStatus getCorrectionStatus() {
            return correctionStatus;
        }

        @Nullable
        public FieldMatrix<Symbol> getCorrection() {
            return correction;
        }

        public int getErrorCount() {
            return errorCount;
        }

        private static <Symbol extends GFElement<Symbol>> int errorCount(FieldMatrix<Symbol> correction) {
            if (correction == null) {
                return 0;
            }
            int count = 0;
            for (int i = 0; i < correction.getColumnDimension(); i++) {
                if (!correction.getEntry(0, i).equals(correction.getField().getZero())) {
                    count++;
                }
            }
            return count;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CorrectionResult<?> that = (CorrectionResult<?>) o;
            return correctionStatus == that.correctionStatus &&
                    Objects.equals(correction, that.correction)
                    && Objects.equals(errorCount, that.errorCount);
        }

        @Override
        public int hashCode() {
            return Objects.hash(correctionStatus, correction, errorCount);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BCHCodeSyndrome<?, ?> that = (BCHCodeSyndrome<?, ?>) o;
        return Objects.equals(message, that.message) &&
                Objects.equals(syndrome, that.syndrome)
                && Objects.equals(correctionResult, that.correctionResult);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, syndrome);
    }

    @Override
    public String toString() {
        return "BCHCodeSyndrome{" +
                "message=" + message +
                ", syndrome=" + syndrome +
                '}';
    }
}
