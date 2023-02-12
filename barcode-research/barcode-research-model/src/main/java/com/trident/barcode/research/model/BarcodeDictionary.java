package com.trident.barcode.research.model;

import org.immutables.value.Value;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Value.Immutable
public interface BarcodeDictionary {

    @Value.Parameter
    List<BarcodeSign> signs();

    default List<BarcodeSign> specialSigns() {
        return signs().stream()
                .filter(it -> it.type() == BarcodeSignType.SWITCHER)
                .collect(Collectors.toList());
    }

    default Optional<BarcodeSign> findSignInCharset(BarcodeCharsetType charsetType, String sign) {
        return findSign(sign).filter(it -> it.charset().equals(charsetType));
    }

    default Optional<BarcodeSign> findSign(String sign) {
        return signs().stream()
                .filter(it -> it.sign().equals(sign))
                .findFirst();
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
        return specialSigns().stream()
                .filter(it -> it.charset().equals(from))
                .filter(it -> it.switchesTo().orElseThrow().equals(to))
                .findFirst();
    }
}
