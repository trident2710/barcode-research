package com.trident.math.field;

public final class GaloisFields {

    public static final GFP GF3 = GFP.of(3);

    public static final GFP GF5 = GFP.of(5);

    public static final GFP GF7 = GFP.of(7);

    public static GFPM GF_2_2 = GFPM.of(2, 2, new long[]{1, 1, 1});

    public static GFPM GF_2_3 = GFPM.of(2, 3, new long[]{1, 1, 0, 1});

    public static GFPM GF_3_2 = GFPM.of(3, 2, new long[]{2, 1, 1});

    public static GFPMExtension GF_4_2 = GFPMExtension.of(GF_2_2, 2, new long[]{2, 1, 1});

    public static GFPMExtension GF_8_2 = GFPMExtension.of(GF_2_3, 2, new long[]{3, 1, 1});

    private GaloisFields() {
    }


}
