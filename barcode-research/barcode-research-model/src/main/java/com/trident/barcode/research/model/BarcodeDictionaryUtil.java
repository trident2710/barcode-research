package com.trident.barcode.research.model;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.trident.barcode.research.model.BarcodeSignType.REGULAR;
import static com.trident.barcode.research.model.BarcodeSignType.SWITCHER;

public final class BarcodeDictionaryUtil {


    public static BarcodeDictionary dictionaryFromCharsets(List<BarcodeCharset> charsets) {
        return ImmutableBarcodeDictionary.of(charsets.stream()
                .flatMap(it -> it.signs().stream())
                .collect(Collectors.toList()));
    }

    public static BarcodeDictionary dictionary(List<BarcodeSign> signs) {
        return ImmutableBarcodeDictionary.of(signs);
    }

    public static BarcodeCharset charset(BarcodeCharsetType charsetType, List<BarcodeSign> signs) {
        return ImmutableBarcodeCharset.of(charsetType, signs);
    }

    public static BarcodeCharsetType charsetType(int index, String label) {
        return ImmutableBarcodeCharsetType.of(index, label);
    }

    public static BarcodeSign regularSymbol(BarcodeCharsetType charset, int digitalRepresentation, String stringRepresentation, List<Integer> codeRepresentation) {
        return ImmutableBarcodeSign.of(charset, REGULAR, Optional.empty(), ImmutableCode.of(codeRepresentation), digitalRepresentation, stringRepresentation);
    }

    public static BarcodeSign regularSymbol(BarcodeCharsetType charset, int digitalRepresentation, String stringRepresentation, String codeStr) {
        return regularSymbol(charset, digitalRepresentation, stringRepresentation, fromCodeString(codeStr));
    }

    public static BarcodeSign specialSymbol(BarcodeCharsetType charset, int digitalRepresentation, String stringRepresentation, List<Integer> codeRepresentation, BarcodeCharsetType switchesTo) {
        return ImmutableBarcodeSign.of(charset, SWITCHER, Optional.of(switchesTo), ImmutableCode.of(codeRepresentation), digitalRepresentation, stringRepresentation);
    }

    public static BarcodeSign specialSymbol(BarcodeCharsetType charset, int digitalRepresentation, String stringRepresentation, String codeStr, BarcodeCharsetType switchesTo) {
        return specialSymbol(charset, digitalRepresentation, stringRepresentation, fromCodeString(codeStr), switchesTo);
    }

    static List<Integer> fromCodeString(String codeStr) {
        return Optional.of(codeStr.chars().boxed().collect(Collectors.toList()))
                .map(Lists::reverse)
                .orElseThrow()
                .stream()
                .map(it -> Integer.parseInt(String.valueOf((char) it.intValue())))
                .collect(Collectors.toList());
    }
}
