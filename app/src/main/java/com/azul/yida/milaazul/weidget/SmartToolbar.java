package com.azul.yida.milaazul.weidget;

/**
 * Created by xuyimin on 2018/7/24.
 * E-mail codingyida@qq.com
 */

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import com.azul.yida.milaazul.R;



import com.example2.lrudemo.toast.DisplayUtils;



/**
 * Created by tang.rui on 2017/9/1.
 */

public class SmartToolbar extends Toolbar {
    public SmartToolbar(Context context) {
        super(context);
    }

    public SmartToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private boolean isFirst = true;

    @Override
    public void setTitle(@StringRes int resId) {
        String titleString = getContext().getString(resId);
        setTitle(titleString);
    }

    @Override
    public void setTitle(CharSequence title) {
        TextView tvTitle = findViewById(R.id.tv_custom_title);
        tvTitle.setText(title);
        if (isFirst) {
            adjustNavigationIcon();
            isFirst = false;
        }

    }

    public void dismiss(){
        setVisibility(GONE);
    }

    public void show(){
        setVisibility(VISIBLE);
    }

    public void transparent() {
        setBackgroundColor(Color.TRANSPARENT);
        setNavigationIcon(android.R.color.transparent);
        setEnabled(false);
        setClickable(false);
        post(() -> setTitle(""));
    }

    public void transparentWithoutBackInco() {
        setBackgroundColor(Color.TRANSPARENT);
        post(() -> setTitle(""));
    }

    public void transparentWith() {
        setBackgroundColor(Color.TRANSPARENT);
        setTitleTextColor(Color.WHITE);
    }

    public void changeStyle(boolean dark) {
        TextView tvTitle = findViewById(R.id.tv_custom_title);
        if (dark) {
            setNavigationIcon(R.mipmap.ic_back_white);
            tvTitle.setTextColor(Color.WHITE);
            setBackgroundResource(R.color.colorAccent);
        }else{
            setNavigationIcon(R.mipmap.ic_back);
            tvTitle.setTextColor(Color.BLACK);
         //   setBackgroundResource(R.drawable.bg_tool_bar);
        }
    }

    public void changeColor2yellow() {
        //setBackgroundResource(R.color.yellow);
    }

    private void adjustNavigationIcon() {
        try {
            Field navigationButton = this.getClass().getSuperclass().getDeclaredField("mNavButtonView");
            navigationButton.setAccessible(true);
            ImageButton imageButton = (ImageButton) navigationButton.get(this);
            imageButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageButton.setPadding(0, 0, DisplayUtils.dip2px(getContext(), 10), 0);
            ViewGroup.LayoutParams layoutParams = imageButton.getLayoutParams();
            layoutParams.width = DisplayUtils.dip2px(getContext(), 39);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
