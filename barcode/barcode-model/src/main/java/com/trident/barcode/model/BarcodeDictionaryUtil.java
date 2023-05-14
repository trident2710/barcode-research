package com.trident.barcode.model;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class BarcodeDictionaryUtil {


    public static BarcodeDictionary dictionaryFromCharsets(List<BarcodeCharset> charsets, int signCodeLength) {
        return ImmutableBarcodeDictionary.of(signCodeLength, charsets.stream()
                .flatMap(it -> it.signs().stream())
                .collect(Collectors.toList()));
    }

    public static BarcodeDictionary dictionary(List<BarcodeSign> signs, int signCodeLength) {
        return ImmutableBarcodeDictionary.of(signCodeLength, signs);
    }

    public static BarcodeCharset charset(BarcodeCharsetType charsetType, List<BarcodeSign> signs) {
        return ImmutableBarcodeCharset.of(charsetType, signs);
    }

    public static BarcodeCharsetType charsetType(int index, String label) {
        return ImmutableBarcodeCharsetType.of(index, label);
    }

    public static BarcodeSign regularSymbol(BarcodeCharsetType charset, int digitalRepresentation, String stringRepresentation, List<Integer> codeRepresentation) {
        return ImmutableBarcodeSign.of(charset, BarcodeSignType.REGULAR, Optional.empty(), ImmutableCode.of(codeRepresentation), digitalRepresentation, stringRepresentation);
    }

    public static BarcodeSign regularSymbol(BarcodeCharsetType charset, int digitalRepresentation, String stringRepresentation, String codeStr) {
        return regularSymbol(charset, digitalRepresentation, stringRepresentation, fromCodeString(codeStr));
    }

    public static BarcodeSign switcher(BarcodeCharsetType charset, int digitalRepresentation, String stringRepresentation, List<Integer> codeRepresentation, BarcodeCharsetType switchesTo) {
        return ImmutableBarcodeSign.of(charset, BarcodeSignType.SWITCHER, Optional.of(switchesTo), ImmutableCode.of(codeRepresentation), digitalRepresentation, stringRepresentation);
    }

    public static BarcodeSign switcher(BarcodeCharsetType charset, int digitalRepresentation, String stringRepresentation, String codeStr, BarcodeCharsetType switchesTo) {
        return switcher(charset, digitalRepresentation, stringRepresentation, fromCodeString(codeStr), switchesTo);
    }

    public static BarcodeSign padding(BarcodeCharsetType charset, int digitalRepresentation, String stringRepresentation, List<Integer> codeRepresentation) {
        return ImmutableBarcodeSign.of(charset, BarcodeSignType.PADDING, Optional.empty(), ImmutableCode.of(codeRepresentation), digitalRepresentation, stringRepresentation);
    }

    public static BarcodeSign padding(BarcodeCharsetType charset, int digitalRepresentation, String stringRepresentation, String codeStr) {
        return padding(charset, digitalRepresentation, stringRepresentation, fromCodeString(codeStr));
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
