package com.emoney.emstockmate.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.view.ViewCompat;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Forcs on 13-12-9.
 */
public class FieldSetView extends View {

    private static final int DEFAULT_TEXT_COLOR = Color.DKGRAY;
    private static final float DEFAULT_TEXT_SIZE = 16.0f;

    private Field[] mFields = null;

    private ColorStateList mTextColor;

    private float mTextSize;

    private TextPaint mPaint = null;

    private float mGap = 0.0f;

    public FieldSetView(Context context) {
        super(context);
        init(context, null);
    }

    public FieldSetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FieldSetView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextSize = DEFAULT_TEXT_SIZE;
    }

    public void setTextColor(int color) {
        ColorStateList newColor = ColorStateList.valueOf(color);
        setTextColor(newColor);
    }

    public void setTextColor(ColorStateList color) {
        if (mTextColor == null || !mTextColor.equals(color)) {
            mTextColor = color;
            if (mTextColor == null) {
                mTextColor = ColorStateList.valueOf(DEFAULT_TEXT_COLOR);
            }

            onTextColorChanged(mTextColor);
        }
    }

    public ColorStateList getTextColor() {
        return mTextColor;
    }

    protected void onTextColorChanged(ColorStateList newColor) {
        ViewCompat.postInvalidateOnAnimation(this);
    }

    public void setGap(float gap) {
        if (mGap != gap) {
            mGap = gap;
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public float getGap() {
        return mGap;
    }

    public void setLabels(CharSequence... labels) {
        if (labels == null || labels.length == 0) {
            mFields = null;
        }

        final int length = labels.length;
        mFields = new Field[length];
        for (int i=0; i<length; i++) {
            mFields[i] = new Field();
        }
    }

    public void setFields(CharSequence... fields) {
        if (fields == null || fields.length == 0
                || mFields == null || mFields.length == 0) {
            return;
        }

        final int fieldLength = fields.length;
        final int maxLength = mFields.length;
        for (int i=0; i<maxLength; i++) {
            if (mFields[i] != null && i<fieldLength) {
            }
        }

        ViewCompat.postInvalidateOnAnimation(this);
    }

    public void setFields(Field... fields) {
        mFields = fields;
        ViewCompat.postInvalidateOnAnimation(this);
    }

    public Field getFieldsAt(int pos) {
        if (mFields != null && pos > -1 && pos < mFields.length) {
            return mFields[pos];
        }

        return null;
    }

    public int getFieldsCount() {
        return mFields != null ? mFields.length : 0;
    }

    public Field[] getFields() {
        return mFields;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        onDrawFields(canvas, 0, 0, width, height, mPaint);
    }

    protected void onDrawFields(Canvas canvas, float left, float top, float right, float bottom, Paint paint) {
        final Field[] fields = getFields();
        if (fields == null || fields.length == 0) {
            return;
        }

        final int length = fields.length;
        float maxWidth = 0;
        final float density = getResources().getDisplayMetrics().density;
        mPaint.setTextSize(mTextSize * density);
        for (int i=0; i<length; i++) {
            if (fields[i] == null || fields[i].field == null || TextUtils.isEmpty(fields[i].field.text)) {
                continue;
            }

            float width = 0.0f;
            StyleableText l = fields[i].label;
            if (l != null && !TextUtils.isEmpty(l.text)) {
                mPaint.setTypeface(Typeface.defaultFromStyle(l.style));
                width += mPaint.measureText(l.text);
                if (mGap > 0) {
                    width += mGap;
                }
            }
            StyleableText f = fields[i].field;
            mPaint.setTypeface(Typeface.defaultFromStyle(f.style));
            width += mPaint.measureText(f.text);

            maxWidth = Math.max(width, maxWidth);

        }

        final int width = (int) (right - left);
        final int height = (int) (bottom - top);

        if (length > 1) {
            final int rowNum = 2;
            final int colNum = (length + 1) / rowNum;
            final int cellWidth = width / colNum;
            final int cellHeight = height / rowNum;
            for (int c=0; c<colNum; c++) {
                for (int r=0; r<rowNum; r++) {
                    final int index = c * rowNum + r;
                    if (index < length) {
                        final float lf = left + cellWidth * c;
                        final float tp = top + cellHeight * r;

                        onDrawFieldAt(canvas, lf, tp, cellWidth, cellHeight, maxWidth, index, paint);
                    }
                }
            }
        } else {

            onDrawFieldAt(canvas, left, top, width, height, maxWidth, 0, paint);
        }
    }

    protected void onDrawFieldAt(Canvas canvas, float left, float top,
                                 float cellWidth, float cellHeight, float textWidth,
                                 int pos, Paint paint) {

        final Field f = getFieldsAt(pos);
        if (f != null) {
            Rect b = new Rect();
            mPaint.getTextBounds(f.field.text, 0, f.field.text.length(), b);
            final int textHeight = b.bottom - b.top;
            float x = left + (cellWidth - textWidth) / 2;
            float y = top + (cellHeight - textHeight) / 2;
            Utils.drawFeild(canvas, x, y, mGap, f.label, f.field, getDrawableState(), mPaint);
        }

    }

    public static class Field {

        public StyleableText label;
        public StyleableText field;

        public Field() {}

        public Field(StyleableText l, StyleableText f) {
            label = l;
            field = f;
        }
    }
}
