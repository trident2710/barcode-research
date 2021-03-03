package com.trident.math.bch;

import com.google.common.base.Preconditions;
import com.trident.math.field.GF;
import com.trident.math.field.GFElement;
import com.trident.math.field.GFPMElement;
import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.linear.FieldMatrix;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private final FieldMatrix<Symbol> correction;

    BCHCodeSyndrome(BCHCode<Symbol, Locator> bch,
                    FieldMatrix<Symbol> message,
                    FieldMatrix<Locator> syndrome) {
        this.bch = bch;
        this.message = message;
        this.syndrome = syndrome;
        this.correction = findCorrection(bch, message, syndrome);
    }

    public boolean hasError() {
        return !isZero(syndrome);
    }

    public FieldMatrix<Symbol> getCorrection() {
        return correction;
    }

    public FieldMatrix<Symbol> getMessage() {
        return message;
    }

    public FieldMatrix<Locator> getSyndrome() {
        return syndrome;
    }

    private FieldMatrix<Symbol> findCorrection(BCHCode<Symbol, Locator> bch, FieldMatrix<Symbol> message, FieldMatrix<Locator> syndrome) {
        if (isZero(syndrome)) {
            return matrixRowOfValue(message.getField().getZero(), message.getColumnDimension());
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

    private FieldMatrix<Symbol> findSingleError(FieldMatrix<Symbol> message, FieldMatrix<Locator> syndrome) {
        // sigma = - S2 / S1
        var sigma = syndrome.getEntry(0, 1).divide(syndrome.getEntry(0, 0).negate());
        var field = (GF<Locator>) syndrome.getField();
        // sigma * x + 1 = 0
        // x = a^j
        var x = solveLinearEquation(field, sigma, field.getOne());
        Preconditions.checkNotNull(x, "x should not be null");
        var X = x.reciprocal();
        int j = bch.getPowerRepresentationMapper().getPower(X);
        var symbolField = (GF<Symbol>) message.getField();
        var errorValue = symbolField.getOfValue(x.digitalRepresentation());
        var correction = matrixRowOfValue(symbolField.getZero(), message.getColumnDimension());
        correction.setEntry(0, j, errorValue);
        return correction;
    }

    private FieldMatrix<Symbol> findDoubleError(BCHCode<Symbol, Locator> bch, FieldMatrix<Symbol> message, FieldMatrix<Locator> syndrome) {
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

        Preconditions.checkArgument(x.size() == 2, "sig2*x^2 + sig1*x + 1 = 0 equation should have 2 roots");
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

        var symbolField = (GF<Symbol>) message.getField();
        var errorValues = List.of(Y.getEntry(0, 0), Y.getEntry(1, 0)).stream()
                .map(e -> symbolField.getOfValue(e.digitalRepresentation()))
                .collect(Collectors.toList());

        var correction = matrixRowOfValue(symbolField.getZero(), message.getColumnDimension());
        correction.setEntry(0, errorPositions.get(0), errorValues.get(0));
        correction.setEntry(0, errorPositions.get(1), errorValues.get(1));

        return correction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BCHCodeSyndrome<?, ?> that = (BCHCodeSyndrome<?, ?>) o;
        return Objects.equals(message, that.message) &&
                Objects.equals(syndrome, that.syndrome);
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
