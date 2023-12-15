package com.bosonshiggs.beautifycomponents.helpers;

import com.google.appinventor.components.common.OptionList;
import java.util.HashMap;
import java.util.Map;

public enum AnimationEffectType implements OptionList<String> {
    FADE_IN("fadeIn"),
    FADE_OUT("fadeOut"),
    SLIDE_LEFT("slideLeft"),
    SLIDE_RIGHT("slideRight"),
    SLIDE_UP("slideUp"),
    SLIDE_DOWN("slideDown"),
    ZOOM_IN("zoomIn"),
    ZOOM_OUT("zoomOut"),
    ROTATE("rotate");

    private String effectType;

    AnimationEffectType(String effectType) {
        this.effectType = effectType;
    }

    public String toUnderlyingValue() {
        return effectType;
    }

    private static final Map<String, AnimationEffectType> lookup = new HashMap<>();

    static {
        for (AnimationEffectType aet : AnimationEffectType.values()) {
            lookup.put(aet.toUnderlyingValue(), aet);
        }
    }

    public static AnimationEffectType fromUnderlyingValue(String effectType) {
        return lookup.getOrDefault(effectType, FADE_IN);
    }
}
