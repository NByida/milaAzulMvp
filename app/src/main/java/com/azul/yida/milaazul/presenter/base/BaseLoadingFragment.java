package com.azul.yida.milaazul.presenter.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Window;
import android.view.WindowManager;

import com.azul.yida.milaazul.R;
import com.azul.yida.milaazul.common.Mlog;
import com.azul.yida.milaazul.view.myView.CircleProgressBar;

public class BaseLoadingFragment extends DialogFragment {
    CircleProgressBar circleProgressBar;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.loading_dialog);

        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        Mlog.t("onCreateDialog");
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0f;
        window.setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_transparent));
        return dialog;

    }



}
