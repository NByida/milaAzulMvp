package com.azul.yida.milaazul.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.azul.yida.milaazul.presenter.base.BasePresentActivity;
import com.azul.yida.milaazul.view.PictureLookupView;

import java.util.ArrayList;

/**
 * Created by xuyimin on 2018/10/11.
 * E-mail codingyida@qq.com
 */

public class  PictureLookupActivity extends BasePresentActivity<PictureLookupView> {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<String> image = getIntent().getStringArrayListExtra("images");
        int index = getIntent().getIntExtra("index", 0);
        mvpView.showImages(image,index);
    }

    @Override
    public Class<PictureLookupView> getPresentClass() {
        return PictureLookupView.class;
    }

    @Override
    public void onNetWorkErorRetry() {

    }
}

