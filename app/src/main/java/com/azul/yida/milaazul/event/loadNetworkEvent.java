package com.azul.yida.milaazul.event;

import com.azul.yida.milaazul.view.base.MvpView;

/**
 * Created by xuyimin on 2018/7/24.
 * E-mail codingyida@qq.com
 */

public class loadNetworkEvent {
    private MvpView mvpView;
    public loadNetworkEvent(MvpView mvpView) {
        this.mvpView=mvpView;
    }

    public MvpView getMvpView() {
        return mvpView;
    }

    public void setMvpView(MvpView mvpView) {
        this.mvpView = mvpView;
    }
}
