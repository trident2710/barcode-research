package com.trident.barcode.codec;

import com.trident.barcode.correction.Correction;
import com.trident.barcode.correction.CorrectionStrategy;
import com.trident.barcode.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SignCorrectingDecoder implements BarcodeDecoder {

    private final BarcodeDictionary barcodeDictionary;
    private final CorrectionStrategy signCorrectionStrategy;

    public SignCorrectingDecoder(BarcodeDictionary barcodeDictionary,
                                 CorrectionStrategy signCorrectionStrategy) {
        this.barcodeDictionary = barcodeDictionary;
        this.signCorrectionStrategy = signCorrectionStrategy;
    }

    @Override
    public String decode(Code code) {
        return Optional.of(code)
                .map(this::extractSignCodes)
                .map(this::toBarcodeSigns)
                .map(this::toMessage)
                .orElse("");
    }

    private List<Code> extractSignCodes(Code code) {
        int signsCount = code.data().size() / barcodeDictionary.signCodeLength();

        var signsCodes = new ArrayList<Code>();

        for (int i = 0; i < signsCount; i++) {
            var signData = code.data().subList(i * barcodeDictionary.signCodeLength(), (i + 1) * barcodeDictionary.signCodeLength());
            var signCode = ImmutableCode.of(signData);
            var correction = correctSign(signCode);

            var codeAfterCorrection = tryCorrect(signCode, correction);

            signsCodes.add(codeAfterCorrection);
        }

        return signsCodes;
    }

    private List<BarcodeSign> toBarcodeSigns(List<Code> signCodes) {
        BarcodeCharsetType currentCharset = barcodeDictionary.defaultCharset();

        var barcodeSigns = new ArrayList<BarcodeSign>();

        for (Code code : signCodes) {
            var sign = barcodeDictionary.findSignInCharset(currentCharset, code);

            if (sign.isPresent()) {
                var it = sign.get();
                switch (it.type()) {
                    case REGULAR:
                        barcodeSigns.add(it);
                        break;
                    case PADDING:
                        break;
                    case SWITCHER:
                        currentCharset = it.switchesTo().orElseThrow();
                        break;
                }
            }
        }

        return barcodeSigns;
    }

    private String toMessage(List<BarcodeSign> signs) {
        return signs.stream()
                .map(BarcodeSign::sign)
                .reduce("", String::concat);
    }

    private static Code tryCorrect(ImmutableCode signCode, Correction correction) {
        switch (correction.status()) {
            case ERROR_CORRECTED:
                return correction.correctedCode().orElseThrow();
            default:
                return signCode;
        }
    }

    private Correction correctSign(Code sign) {
        return signCorrectionStrategy.correct(sign);
    }
}
