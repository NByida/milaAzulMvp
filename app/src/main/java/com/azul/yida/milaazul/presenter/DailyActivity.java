package com.azul.yida.milaazul.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.azul.yida.milaazul.presenter.base.BasePresentActivity;
import com.azul.yida.milaazul.view.DailyView;

public class DailyActivity extends BasePresentActivity<DailyView> {
    @Override
    public Class<DailyView> getPresentClass() {
        return DailyView.class;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String image=getIntent().getStringExtra("image");
        mvpView.setImage(image);
    }
}
