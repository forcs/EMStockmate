package com.emoney.emstockmate.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextUtils;

/**
 * Created by Forcs on 13-12-9.
 */
public class Utils {

    static void drawFeild(Canvas canvas, float x, float y, float gap,
                                 StyleableText label, StyleableText field, int[] stateSet, Paint paint) {

        float labelTextWidth = 0.0f;
        float offset = 0.0f;
        if (label != null && !TextUtils.isEmpty(label.text)) {
            paint.setTypeface(Typeface.defaultFromStyle(label.style));
            paint.setColor(label.color.getColorForState(stateSet, Color.DKGRAY));
            canvas.drawText(label.text + ":", x, y, paint);
            labelTextWidth += paint.measureText(label + ":");
            offset = labelTextWidth + gap;
        }

        paint.setTypeface(Typeface.defaultFromStyle(field.style));
        paint.setColor(field.color.getColorForState(stateSet, Color.DKGRAY));
        canvas.drawText(field.text, x + offset, y, paint);
    }
}
