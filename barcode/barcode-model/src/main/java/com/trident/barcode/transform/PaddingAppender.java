package com.trident.barcode.transform;

import com.trident.barcode.model.Barcode;
import com.trident.barcode.model.ImmutableBarcode;
import com.trident.barcode.padding.PaddingStrategy;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PaddingAppender implements BarcodeTransformer {

    private final PaddingStrategy paddingStrategy;

    public PaddingAppender(PaddingStrategy paddingStrategy) {
        this.paddingStrategy = paddingStrategy;
    }

    @Override
    public Barcode transform(Barcode barcode) {
        int paddingCount = paddingStrategy.getPaddingCount(barcode.signs().size());
        var lastSymbol = barcode.signs().get(barcode.signs().size() - 1);
        var paddingSymbol = barcode.dictionary().padding(lastSymbol.charset()).orElseThrow();
        var paddingSymbols = Stream.generate(() -> paddingSymbol).limit(paddingCount).collect(Collectors.toList());
        return ImmutableBarcode.copyOf(barcode)
                .withSigns(Stream.of(barcode.signs(), paddingSymbols)
                        .flatMap(List::stream)
                        .collect(Collectors.toList()));
    }
}
