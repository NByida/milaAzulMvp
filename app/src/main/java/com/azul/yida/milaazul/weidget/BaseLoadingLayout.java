package com.azul.yida.milaazul.weidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.azul.yida.milaazul.R;

/**
 * Created by xuyimin on 2018/6/21.
 * E-mail codingyida@qq.com
 */

public class BaseLoadingLayout extends RelativeLayout {
    View loadView;
    public BaseLoadingLayout(Context context) {
        super(context);
    }

    public BaseLoadingLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseLoadingLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseLoadingLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr,defStyleRes);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void startLoading(){
        if(getTag(R.id.always)!=null)return;
        ViewGroup layContent=this;
        for(int a=0;a<layContent.getChildCount();a++){
            layContent.getChildAt(a).setVisibility(View.GONE);
        }
        LayoutInflater layoutInflater=(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //loadView=layoutInflater.inflate(R.layout.loading_dialog,null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            loadView=new ProgressBar(getContext(),null,0,R.style.MyProgressBar);
        }

        LayoutParams p = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        p.addRule(RelativeLayout.CENTER_IN_PARENT);
        layContent.addView(loadView,p);
        RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams)loadView.getLayoutParams();
        int width=getWidth()>getHeight()?getHeight()/3:getWidth()/3;
        params.height=width;
        params.width=width;
        loadView.setLayoutParams(params);
        setTag(R.id.always,loadView);
    }

    public void stoptLoading(){
        if(getTag(R.id.always)==null)return;
        removeView((View) getTag(R.id.always));
        setTag(R.id.always,null);
        for(int a=0;a<getChildCount();a++){
            getChildAt(a).setVisibility(View.VISIBLE);
        }
    }

}
