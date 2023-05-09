package com.trident.barcode.research.image;

import java.awt.*;

public final class ColorSchemes {
    private ColorSchemes() {
    }

    public static ColorScheme DEFAULT_5_COLORS = value -> {
        switch (value) {
            case 0:
                return Color.BLACK;
            case 1:
                return Color.WHITE;
            case 2:
                return Color.RED;
            case 3:
                return Color.GREEN;
            case 4:
                return Color.BLUE;
            default:
                return Color.CYAN;
        }
    };
}
