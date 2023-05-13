package com.trident.barcode.image;

import java.awt.*;

public interface ColorScheme {

    ColorScheme DEFAULT = new ColorScheme() {
        @Override
        public Color color(int value) {
            return value == 0 ? Color.WHITE : Color.BLACK;
        }
    };
    Color color(int value);
}
