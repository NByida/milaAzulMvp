package com.azul.yida.milaazul.view.viewHelper;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.content.res.AppCompatResources;
import android.util.TypedValue;

import com.azul.yida.milaazul.AzulApplication.AzulApp;

public class AnimUtil {
    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};
    private static final int[] DISABLED_STATE_SET = {-android.R.attr.state_enabled};
    static final int[] FOCUSED_ENABLED_STATE_SET = {android.R.attr.state_focused,
            android.R.attr.state_enabled};


    static final int[] PRESSED_ENABLED_STATE_SET = {android.R.attr.state_pressed,
            android.R.attr.state_enabled};
    public static ColorStateList createColorStateList(int selectedColor) {
        final int[][] states = new int[3][];
        final int[] colors = new int[3];
        int i = 0;

        states[i] = FOCUSED_ENABLED_STATE_SET;
        colors[i] = selectedColor;
        i++;

        states[i] = PRESSED_ENABLED_STATE_SET;
        colors[i] = selectedColor;
        i++;

        // Default enabled state
        states[i] = new int[0];
        colors[i] = Color.TRANSPARENT;
        i++;

        return new ColorStateList(states, colors);
    }

    public static ColorStateList createDefaultColorStateList(int baseColorThemeAttr) {
        final TypedValue value = new TypedValue();
        if (!AzulApp.getInstance().getTheme().resolveAttribute(baseColorThemeAttr, value, true)) {
            return null;
        }
        ColorStateList baseColor = AppCompatResources.getColorStateList(
                AzulApp.getInstance(), value.resourceId);
        if (!AzulApp.getInstance().getTheme().resolveAttribute(
                android.support.v7.appcompat.R.attr.colorPrimary, value, true)) {
            return null;
        }
        int colorPrimary = value.data;
        int defaultColor = baseColor.getDefaultColor();
        return new ColorStateList(new int[][]{
                DISABLED_STATE_SET,
                CHECKED_STATE_SET,
                null
        }, new int[]{
                baseColor.getColorForState(DISABLED_STATE_SET, defaultColor),
                colorPrimary,
                defaultColor
        });
    }
}
