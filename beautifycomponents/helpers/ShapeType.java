package com.bosonshiggs.beautifycomponents.helpers;

import com.google.appinventor.components.common.OptionList;
import java.util.HashMap;
import java.util.Map;

public enum ShapeType implements OptionList<String> {
    CIRCLE("circle"),
    TRIANGLE("triangle"),
    PENTAGON("pentagon"),
    HEXAGON("hexagon"),
	STAR("star"),
	HEART("heart"),
    RECTANGLE("rectangle");
    // Adicione mais formas conforme necessário

    private String shapeType;

    ShapeType(String shapeType) {
        this.shapeType = shapeType;
    }

    public String toUnderlyingValue() {
        return shapeType;
    }

    private static final Map<String, ShapeType> lookup = new HashMap<>();

    static {
        for (ShapeType st : ShapeType.values()) {
            lookup.put(st.toUnderlyingValue(), st);
        }
    }

    public static ShapeType fromUnderlyingValue(String shapeType) {
        return lookup.getOrDefault(shapeType, RECTANGLE);
    }
}