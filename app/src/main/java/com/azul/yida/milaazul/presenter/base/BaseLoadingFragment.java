package com.azul.yida.milaazul.presenter.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

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
        circleProgressBar=dialog.findViewById(R.id.progress_bar);
        circleProgressBar.setProgress(50);
        dialog.setCancelable(false);
        Mlog.t("onCreateDialog");
        return dialog;
    }

//    public void show(FragmentManager fm) {
//        this.show(fm, "LoadingDialogFragment");
//    }


}
