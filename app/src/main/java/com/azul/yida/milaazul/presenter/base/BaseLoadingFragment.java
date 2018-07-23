package com.azul.yida.milaazul.presenter.base;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.azul.yida.milaazul.AzulApplication.AzulApp;
import com.azul.yida.milaazul.R;
import com.azul.yida.milaazul.common.Mlog;
import com.azul.yida.milaazul.presenter.LifeCycleBasePresenter.BaseDialogFragment;
import com.azul.yida.milaazul.view.myView.CircleProgressBar;
import com.azul.yida.milaazul.view.myView.ColorfulProgressBar;
import com.azul.yida.milaazul.view.viewHelper.AnimUtil;
import com.example2.lrudemo.toast.ToastUtil;
import com.trello.rxlifecycle2.components.RxDialogFragment;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class BaseLoadingFragment extends BaseDialogFragment {
    ColorfulProgressBar circleProgressBar;
    //int[] color={ R.color.colorAccent,R.color.colorYellow,R.color.a,R.color.b,R.color.c,R.color.d,R.color.e,R.color.f};
    int[] color={AzulApp.getInstance().getResources().getColor(R.color.e), AzulApp.getInstance().getResources().getColor(R.color.f)};
    ArrayList<Integer>list=new ArrayList<>();



    private int backClicktimes=1;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        list.add(AzulApp.getInstance().getResources().getColor(R.color.f));
        list.add(AzulApp.getInstance().getResources().getColor(R.color.colorPrimary));
        list.add(AzulApp.getInstance().getResources().getColor(R.color.colorAccent));
        list.add(AzulApp.getInstance().getResources().getColor(R.color.d));
        list.add(AzulApp.getInstance().getResources().getColor(R.color.e));
        list.add(AzulApp.getInstance().getResources().getColor(R.color.c));
        list.add(AzulApp.getInstance().getResources().getColor(R.color.a));
        list.add(AzulApp.getInstance().getResources().getColor(R.color.colorYellow));
        list.add(AzulApp.getInstance().getResources().getColor(R.color.f));

        Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.loading_dialog);
        circleProgressBar=dialog.findViewById(R.id.progress_bar);
        dialog.setCanceledOnTouchOutside(false);
        //Mlog.t("onCreateDialog");
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0f;
        window.setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_transparent));
        changeColor();
        dialog.setOnKeyListener((a,b,c)->{
            if(b == KeyEvent.KEYCODE_BACK){
                backClicktimes++;
                ToastUtil toastUtil=new ToastUtil();
                ToastUtil.showToast(getContext(),"加载中。。。少侠留步，再按一次退出");
                if(backClicktimes>1){
                    return false;
                }
                return true;
            }
            else return false;

            }
        );
        return dialog;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeColor(){
       // Mlog.t("tintColor@"+"111");
//        ObjectAnimator.ofInt(circleProgressBar,"TintColor",AzulApp.getInstance().getResources().getColor(R.color.colorPrimary),AzulApp.getInstance().getResources().getColor(R.color.colorAccent))
//        .setDuration(list.size()*1000).start();
//        ValueAnimator valueAnimator = ValueAnimator.ofInt(circleProgressBar.getTintColor(),list.get(0),list.get(2),list.get(1),list.get(3),list.get(4),list.get(5),list.get(6));
//        //ValueAnimator anim = ValueAnimator.ofObject(new ColorStateListEvaluator(), ColorStateList.valueOf(list.get(1)), ColorStateList.valueOf(list.get(2)));
//        valueAnimator.setDuration(3000);
//        valueAnimator.start();

        Observable.interval(0,400, TimeUnit.MILLISECONDS)
                .compose(applyIOSchedulersAndLifecycle())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(n->{
                    ColorStateList colorStateList = ColorStateList.valueOf(list.get((int) (n%list.size())));
                  circleProgressBar.setIndeterminateTintList(colorStateList);

                });
    }

   class ColorStateListEvaluator implements TypeEvaluator {
       @Override
       public Object evaluate(float v, Object o, Object t1) {
           return ColorStateList.valueOf(AzulApp.getInstance().getResources().getColor(R.color.colorAccent));
       }
   }






}
