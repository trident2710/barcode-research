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
        try {
            return decoder.decode(code);
        } catch (Exception ex) {
            throw new RuntimeException("Unable to decode: " + code, ex);
        }
    }

    @Override
    public Code encode(String message) {
        try {
            return encoder.encode(message);
        } catch (Exception ex) {
            throw new RuntimeException("Unable to encode message: " + message, ex);
        }
    }
}
