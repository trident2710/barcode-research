package com.trident.barcode.reedsolomon;

import com.trident.barcode.codec.BarcodeDecoder;
import com.trident.barcode.correction.CorrectionStrategy;
import com.trident.barcode.model.*;
import com.trident.math.field.GFPElement;
import com.trident.math.matrix.FieldMatrixUtil;
import com.trident.math.reedsolomon.ReedSolomonCode;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReedSolomonDecoder implements BarcodeDecoder {

    private final BarcodeDictionary barcodeDictionary;
    private final ReedSolomonCode reedSolomonCode;

    private final CorrectionStrategy signCorrectionStrategy;

    public ReedSolomonDecoder(BarcodeDictionary barcodeDictionary,
                              ReedSolomonCode reedSolomonCode,
                              CorrectionStrategy signCorrectionStrategy) {
        this.barcodeDictionary = barcodeDictionary;
        this.reedSolomonCode = reedSolomonCode;
        this.signCorrectionStrategy = signCorrectionStrategy;
    }

    @Override
    public String decode(Code code) {
        int signLength = barcodeDictionary.signCodeLength();
        int signsCount = code.data().size() / signLength;

        var signCodes = new ArrayList<Code>();
        var erasures = new ArrayList<Integer>();

        for (int i = 0; i < signsCount; i++) {
            var signData = code.data().subList(i * signLength, (i + 1) * signLength);
            var signCode = ImmutableCode.of(signData);
            var correctedSign = correctSign(signCode);
            if (correctedSign.getSecond()) {
                erasures.add(i);
            }
            signCodes.add(correctedSign.getFirst());
        }

        var decoded = reedSolomonDecode(signCodes, erasures);

        var signsRaw = toBarcodeSigns(decoded);

        var signsPure = purify(signsRaw);

        return signsPure.stream()
                .map(BarcodeSign::sign)
                .reduce("", String::concat);
    }

    private List<BarcodeSign> toBarcodeSigns(List<Code> signCodes) {
        var currentCharset = barcodeDictionary.defaultCharset();

        var signs = new ArrayList<BarcodeSign>();

        for (Code signCode : signCodes) {
            var sign = barcodeDictionary.findSignInCharset(currentCharset, signCode)
                    .orElseThrow(() -> new RuntimeException("Sign not found"));
            signs.add(sign);

            if (sign.type() == BarcodeSignType.SWITCHER) {
                currentCharset = sign.switchesTo().orElseThrow();
            }
        }

        return signs;
    }

    private List<BarcodeSign> purify(List<BarcodeSign> signs) {
        return signs.stream()
                .filter(it -> it.type() == BarcodeSignType.REGULAR)
                .collect(Collectors.toList());
    }


    private Pair<Code, Boolean> correctSign(Code sign) {
        var correction = signCorrectionStrategy.correct(sign);
        switch (correction.status()) {
            case NO_ERROR:
                return Pair.create(sign, false);
            case ERROR_DETECTED:
                return Pair.create(sign, true);
            case ERROR_CORRECTED:
                return Pair.create(correction.correctedCode().orElseThrow(), false);
            default:
                throw new IllegalStateException("Unknown correction status: " + correction.status());
        }
    }

    private List<Code> reedSolomonDecode(List<Code> signs, List<Integer> erasures) {
        var reedSolomonEncoded = toFieldRepresentation(signs.stream()
                .map(it -> barcodeDictionary.findSigns(it).stream()
                        .findFirst()
                        .map(BarcodeSign::digitalRepresentation)
                        .orElseThrow(() -> new RuntimeException("Sign not found")))
                .collect(Collectors.toList()));

        var corrected = fromFieldRepresentation(correctReedSolomon(reedSolomonEncoded, erasures));
        return corrected.stream()
                .map(it -> barcodeDictionary.findSigns(it).stream()
                        .findFirst()
                        .map(BarcodeSign::codeRepresentation)
                        .orElseThrow())
                .collect(Collectors.toList());
    }

    private FieldMatrix<GFPElement> toFieldRepresentation(List<Integer> digitalRepresentation) {
        return FieldMatrixUtil.matrixRow(digitalRepresentation.stream()
                .map(it -> reedSolomonCode.getGeneratorField().getOfValue(it))
                .collect(Collectors.toList()));
    }

    private List<Integer> fromFieldRepresentation(FieldMatrix<GFPElement> fieldRepresentation) {
        return Arrays.stream(fieldRepresentation.getRow(0))
                .map(GFPElement::digitalRepresentation)
                .map(Long::intValue)
                .collect(Collectors.toList());
    }

    private FieldMatrix<GFPElement> correctReedSolomon(FieldMatrix<GFPElement> encoded, List<Integer> erasures) {
        var correctionResult = reedSolomonCode.correct(encoded, erasures);
        switch (correctionResult.status()) {
            case NO_ERROR:
                return reedSolomonCode.decode(correctionResult.message());
            case ERROR_CORRECTED:
                return reedSolomonCode.decode(correctionResult.correctedMessage().orElseThrow());
            default:
                throw new IllegalStateException("Unable to correct: ");
        }
    }
}
