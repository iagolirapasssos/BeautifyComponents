package com.bosonshiggs.beautifycomponents.helpers;

import com.google.appinventor.components.common.OptionList;
import java.util.HashMap;
import java.util.Map;

public enum ColorFilterType implements OptionList<String> {
    NONE("none"),
    GRAYSCALE("grayscale"),
    SEPIA("sepia"),
    INVERT("invert");

    private String filterType;

    ColorFilterType(String filterType) {
        this.filterType = filterType;

    }

    public String toUnderlyingValue() {
        return filterType;
    }

    private static final Map<String, ColorFilterType> lookup = new HashMap<>();

    static {
        for (ColorFilterType cft : ColorFilterType.values()) {
            lookup.put(cft.toUnderlyingValue(), cft);
        }
    }

    public static ColorFilterType fromUnderlyingValue(String filterType) {
        return lookup.getOrDefault(filterType, NONE);
    }
}
