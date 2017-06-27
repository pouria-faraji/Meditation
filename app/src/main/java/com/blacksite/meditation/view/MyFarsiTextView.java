package com.blacksite.meditation.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by pouria on 1/12/2017.
 */
public class MyFarsiTextView extends TextView {
    public MyFarsiTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/B Yekan_p30download.com.ttf"));
    }
}