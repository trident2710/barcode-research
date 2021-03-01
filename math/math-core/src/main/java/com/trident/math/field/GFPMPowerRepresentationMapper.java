package com.trident.math.field;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

public final class GFPMPowerRepresentationMapper<FE extends GFElement<FE>> {
    private final GFPM<FE> field;
    private final Map<FE, Integer> powerRepresentationMap;

    private GFPMPowerRepresentationMapper(GFPM<FE> field) {
        this.field = field;
        this.powerRepresentationMap = initMap(field);
    }

    public static <FE extends GFElement<FE>> GFPMPowerRepresentationMapper<FE> create(GFPM<FE> field) {
        return new GFPMPowerRepresentationMapper<>(field);
    }

    private Map<FE, Integer> initMap(GFPM<FE> field) {
        var map = new HashMap<FE, Integer>();
        map.put(field.getOne(), 0);
        var element = field.getOfValue(field.prime());
        int power = 1;
        while (!element.equals(field.getOne())) {
            map.put(element, power);
            element = element.multiply(field.getOfValue(field.prime()));
            power++;
        }
        return map;
    }

    public int getPower(FE element) {
        Preconditions.checkNotNull(element);
        return powerRepresentationMap.getOrDefault(element, -1);
    }
}
