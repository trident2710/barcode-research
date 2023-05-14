package com.trident.barcode.codec;

import com.trident.barcode.model.Code;

public class BarcodeCodecImpl implements BarcodeCodec {

    private final BarcodeEncoder encoder;
    private final BarcodeDecoder decoder;

    public BarcodeCodecImpl(BarcodeEncoder encoder, BarcodeDecoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    @Override
    public String decode(Code code) {
        return decoder.decode(code);
    }

    @Override
    public Code encode(String message) {
        return encoder.encode(message);
    }
}
