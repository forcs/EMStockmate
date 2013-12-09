package com.emoney.emstockmate.view;

import android.content.res.ColorStateList;
import android.graphics.Typeface;

/**
 * Created by Forcs on 13-12-10.
 */
public class StyleableText {

    public static final int NORMAL = Typeface.NORMAL;
    public static final int BOLD = Typeface.BOLD;
    public static final int ITALIC = Typeface.ITALIC;
    public static final int BOLD_ITALIC = Typeface.BOLD_ITALIC;

    public String text;
    public ColorStateList color;
    public int style = NORMAL;

    public StyleableText() {}

    public StyleableText(String t, ColorStateList c, int tf) {
        text = t;
        color = c;
        style = tf;
    }

    public StyleableText(String t, int c, int tf) {
        this(t, ColorStateList.valueOf(c), tf);
    }
}
