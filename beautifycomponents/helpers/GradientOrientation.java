package com.bosonshiggs.beautifycomponents.helpers;

import com.google.appinventor.components.common.OptionList;
import java.util.HashMap;
import java.util.Map;

public enum GradientOrientation implements OptionList<String> {
    TOP_BOTTOM("TOP_BOTTOM"),
    TR_BL("TR_BL"),
    RIGHT_LEFT("RIGHT_LEFT"),
    BR_TL("BR_TL"),
    BOTTOM_TOP("BOTTOM_TOP"),
    BL_TR("BL_TR"),
    LEFT_RIGHT("LEFT_RIGHT"),
    TL_BR("TL_BR");

	private String filterType;

    GradientOrientation(String filterType) {
        this.filterType = filterType;

    }

    public String toUnderlyingValue() {
        return filterType;
    }

    private static final Map<String, GradientOrientation> lookup = new HashMap<>();

    static {
        for (GradientOrientation cft : GradientOrientation.values()) {
            lookup.put(cft.toUnderlyingValue(), cft);
        }
    }

    public static GradientOrientation fromUnderlyingValue(String filterType) {
        return lookup.getOrDefault(filterType, TOP_BOTTOM);
    }
}
