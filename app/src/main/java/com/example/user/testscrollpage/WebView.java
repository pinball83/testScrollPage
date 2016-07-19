package com.example.user.testscrollpage;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Evgeny Safronov on 15.07.16.
 */
public class WebView extends View {



    public WebView(Context context) {
        super(context);
        this.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        setLayoutParams(new ViewGroup.LayoutParams(displayMetrics.widthPixels, ViewGroup.LayoutParams.MATCH_PARENT));
    }


}
