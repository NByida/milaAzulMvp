package com.example2.lrudemo.toast;

/**
 * Created by xuyimin on 2018/6/26.
 * E-mail codingyida@qq.com
 */

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;






/**
 * Created by xuyimin on 2018/6/21.
 * E-mail codingyida@qq.com
 */

public class LoadingLayout extends RelativeLayout {
    View loadView;
    public LoadingLayout(Context context) {
        super(context);
    }

    public LoadingLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attributeName = attrs.getAttributeName(i);

            if (attributeName.equals("style")) {
                String attributeValue = attrs.getAttributeValue(i);
            }
        }
    }


     @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr,defStyleRes);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void startLoading(){
        if(getTag(R.id.loadingview)!=null)return;
        ViewGroup layContent=this;
        for(int a=0;a<layContent.getChildCount();a++){
            layContent.getChildAt(a).setVisibility(View.GONE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            loadView=new ProgressBar(getContext(),null,0,R.style.MyProgressBar);
        }else {
             loadView=new ProgressBar(getContext(),null,R.attr.defaultStyle);
        }


        LayoutParams p = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        p.addRule(RelativeLayout.CENTER_IN_PARENT);
        layContent.addView(loadView,p);
        RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams)loadView.getLayoutParams();
        int width=getWidth()>getHeight()?getHeight()/3:getWidth()/3;
        params.height=width;
        params.width=width;
        loadView.setLayoutParams(params);
        setTag(R.id.loadingview,loadView);
    }

    public void stoptLoading(){
        if(getTag(R.id.loadingview)==null)return;
        removeView((View) getTag(R.id.loadingview));
        setTag(R.id.loadingview,null);
        for(int a=0;a<getChildCount();a++){
            getChildAt(a).setVisibility(View.VISIBLE);
        }
    }

}
