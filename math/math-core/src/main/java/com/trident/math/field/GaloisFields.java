package com.trident.math.field;

public final class GaloisFields {

    public static final GFP GF3 = GFP.of(3);

    public static final GFP GF5 = GFP.of(5);

    public static final GFP GF7 = GFP.of(7);

    public static final GFP GF11 = GFP.of(11);

    public static GFPMSimple GF_2_2 = GFPMSimple.of(2, 2, new long[]{1, 1, 1});

    public static GFPMSimple GF_2_3 = GFPMSimple.of(2, 3, new long[]{1, 1, 0, 1});

    public static GFPMSimple GF_3_2 = GFPMSimple.of(3, 2, new long[]{2, 1, 1});

    public static GFPMSimple GF_3_3 = GFPMSimple.of(3, 3, new long[]{1, 2, 0, 1});

    public static GFPMSimple GF_5_2 = GFPMSimple.of(5, 2, new long[]{2, 1, 1});

    public static GFPMSimple GF_7_2 = GFPMSimple.of(7, 2, new long[]{3, 1, 1});

    public static GFPMExtension GF_4_2 = GFPMExtension.of(GF_2_2, 2, new long[]{2, 1, 1});

    public static GFPMExtension GF_8_2 = GFPMExtension.of(GF_2_3, 2, new long[]{3, 1, 1});

    private GaloisFields() {
    }


}
