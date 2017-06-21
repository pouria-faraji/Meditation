package com.blacksite.meditation.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by pouria on 1/12/2017.
 */
public class MyTextView extends TextView {
    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Museo Sans W01 Rounded 300.ttf"));
    }
}