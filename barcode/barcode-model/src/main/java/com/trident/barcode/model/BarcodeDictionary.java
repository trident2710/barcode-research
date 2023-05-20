package com.trident.barcode.model;

import org.immutables.value.Value;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Value.Immutable
public interface BarcodeDictionary {

    @Value.Parameter
    int signCodeLength();

    @Value.Parameter
    List<BarcodeSign> signs();

    default List<BarcodeSign> switchers() {
        return signs().stream()
                .filter(it -> it.type() == BarcodeSignType.SWITCHER)
                .collect(Collectors.toList());
    }

    default Optional<BarcodeSign> findSignInCharset(BarcodeCharsetType charsetType, String sign) {
        return findSign(sign).filter(it -> it.charset().equals(charsetType));
    }

    default Optional<BarcodeSign> findSignInCharset(BarcodeCharsetType charsetType, Code signCode) {
        return signs().stream()
                .filter(it -> it.charset().equals(charsetType))
                .filter(it -> it.codeRepresentation().equals(signCode))
                .findFirst();
    }

    default Optional<BarcodeSign> findSign(String sign) {
        return signs().stream()
                .filter(it -> it.sign().equals(sign))
                .findFirst();
    }

    default List<BarcodeSign> findSigns(int digitalRepresentation) {
        return signs().stream()
                .filter(it -> it.digitalRepresentation() == digitalRepresentation)
                .collect(Collectors.toList());
    }

    default List<BarcodeSign> findSigns(Code codeRepresentation) {
        return signs().stream()
                .filter(it -> it.codeRepresentation().equals(codeRepresentation))
                .collect(Collectors.toList());
    }

    default BarcodeCharsetType defaultCharset() {
        return signs().stream()
                .map(BarcodeSign::charset)
                .distinct()
                .min(Comparator.comparingInt(BarcodeCharsetType::index))
                .orElseThrow();
    }

    default BarcodeCharset charset(BarcodeCharsetType charsetType) {
        return ImmutableBarcodeCharset.of(charsetType,
                signs().stream()
                        .filter(it -> it.charset().equals(charsetType))
                        .collect(Collectors.toList()));
    }

    default Optional<BarcodeSign> switcher(BarcodeCharsetType from, BarcodeCharsetType to) {
        return switchers().stream()
                .filter(it -> it.charset().equals(from))
                .filter(it -> it.switchesTo().orElseThrow().equals(to))
                .findFirst();
    }

    default Optional<BarcodeSign> padding(BarcodeCharsetType charset) {
        return charset(charset).signs().stream()
                .filter(it -> it.type() == BarcodeSignType.PADDING)
                .findFirst();
    }
}
