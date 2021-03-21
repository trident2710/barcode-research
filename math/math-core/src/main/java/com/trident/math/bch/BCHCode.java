package com.trident.math.bch;

import com.google.common.base.Preconditions;
import com.trident.math.field.GF;
import com.trident.math.field.GFElement;
import com.trident.math.field.GFPM;
import com.trident.math.field.GFPMElement;
import com.trident.math.field.GFPMPowerRepresentationMapper;
import org.apache.commons.math3.linear.Array2DRowFieldMatrix;
import org.apache.commons.math3.linear.FieldMatrix;

public class BCHCode<Symbol extends GFElement<Symbol>, Locator extends GFPMElement<Locator>> {
    private final FieldMatrix<Symbol> symbolsMatrix;
    private final FieldMatrix<Locator> locatorsMatrixT;
    private final GFPMPowerRepresentationMapper<Locator> powerRepresentationMapper;

    private final GF<Symbol> symbolField;
    private final GF<Locator> locatorField;

    public BCHCode(FieldMatrix<Symbol> symbolsMatrix,
                   Class<? extends GF<Symbol>> symbolsFieldClass,
                   FieldMatrix<Locator> locatorsMatrix,
                   Class<? extends GF<Locator>> locatorsFieldClass) {
        Preconditions.checkArgument(symbolsMatrix.getColumnDimension() == locatorsMatrix.getColumnDimension());
        var locatorsMatrixT = locatorsMatrix.transpose();
        checkCorrect(symbolsMatrix, locatorsMatrixT);

        this.symbolsMatrix = symbolsMatrix;
        this.locatorsMatrixT = locatorsMatrixT;
        this.symbolField = symbolsFieldClass.cast(symbolsMatrix.getField());
        this.locatorField = locatorsFieldClass.cast(locatorsMatrix.getField());
        this.powerRepresentationMapper = GFPMPowerRepresentationMapper.create((GFPM<Locator>) locatorsMatrix.getField());
    }

    public GF<Symbol> symbolField() {
        return symbolField;
    }

    public GF<Locator> locatorField() {
        return locatorField;
    }

    public int totalLength() {
        return symbolsMatrix.getColumnDimension();
    }

    public int informationalLength() {
        return symbolsMatrix.getRowDimension();
    }

    public FieldMatrix<Symbol> encode(FieldMatrix<Symbol> message) {
        Preconditions.checkArgument(message.getRowDimension() == 1);
        Preconditions.checkArgument(message.getColumnDimension() == symbolsMatrix.getRowDimension());
        Preconditions.checkArgument(message.getField().equals(symbolsMatrix.getField()));
        return message.multiply(symbolsMatrix);
    }

    public BCHCodeSyndrome<Symbol, Locator> syndrome(FieldMatrix<Symbol> encoded) {
        Preconditions.checkArgument(encoded.getRowDimension() == 1);
        Preconditions.checkArgument(encoded.getColumnDimension() == locatorsMatrixT.getRowDimension());
        var syndrome = calculateSyndrome(encoded);
        return new BCHCodeSyndrome<>(this, encoded, syndrome);
    }

    GFPMPowerRepresentationMapper<Locator> getPowerRepresentationMapper() {
        return powerRepresentationMapper;
    }

    private FieldMatrix<Locator> calculateSyndrome(FieldMatrix<Symbol> encoded) {
        return multiply(encoded, locatorsMatrixT);
    }

    private void checkCorrect(FieldMatrix<Symbol> symbolsMatrix, FieldMatrix<Locator> locatorsMatrix) {
        var multiplication = multiply(symbolsMatrix, locatorsMatrix);
        Preconditions.checkArgument(multiplication.equals(new Array2DRowFieldMatrix<>(locatorsMatrix.getField(),
                        symbolsMatrix.getRowDimension(), locatorsMatrix.getColumnDimension())),
                "symbols matrix (G)  and locators matrix (H)  do not meet G * H^t = 0 condition");
    }

    private FieldMatrix<Locator> multiply(FieldMatrix<Symbol> symbolsMatrix, FieldMatrix<Locator> locatorsMatrix) {
        var field = (GF<Locator>) locatorsMatrix.getField();
        var result = new Array2DRowFieldMatrix<>(field, symbolsMatrix.getRowDimension(), locatorsMatrix.getColumnDimension());

        for (int i = 0; i < symbolsMatrix.getRowDimension(); i++) {
            for (int j = 0; j < locatorsMatrix.getColumnDimension(); j++) {
                for (int k = 0; k < symbolsMatrix.getColumnDimension(); k++) {
                    var l = locatorsMatrix.getEntry(k, j);
                    var s = field.getOfValue(symbolsMatrix.getEntry(i, k).digitalRepresentation());
                    var value = field.mul(l, s);
                    result.addToEntry(i, j, value);
                }
            }
        }

        return result;
    }
}
