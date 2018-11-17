package com.lo52.codep25.utils.customsviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class IntegraleCoustumViewEdit extends android.support.v7.widget.AppCompatEditText {
    public IntegraleCoustumViewEdit(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public IntegraleCoustumViewEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IntegraleCoustumViewEdit(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "product_sans_font/Product_Sans_Regular.ttf");
            setTypeface(tf);
        }
    }
}
