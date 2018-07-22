package com.azul.yida.milaazul.view.myView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.azul.yida.milaazul.common.Mlog;
import com.azul.yida.milaazul.view.viewHelper.AnimUtil;

public class ColorfulProgressBar extends ProgressBar {


    public int TintColor;

    public ColorfulProgressBar(Context context) {
        super(context);
    }

    public ColorfulProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorfulProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ColorfulProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public int getTintColor() {
        return TintColor;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setTintColor(int tintColor) {
        TintColor = tintColor;
        setIndeterminateTintList(ColorStateList.valueOf(TintColor));
    }




}
