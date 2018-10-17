package com.azul.yida.milaazul.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.azul.yida.milaazul.R;
import com.azul.yida.milaazul.common.AndroidUtils;
import com.azul.yida.milaazul.common.Base64;
import com.azul.yida.milaazul.common.ImgLoader;
import com.azul.yida.milaazul.common.Mlog;
import com.azul.yida.milaazul.presenter.PictureLookupActivity;
import com.azul.yida.milaazul.view.adapter.myViewHolder;
import com.azul.yida.milaazul.view.base.MvpView;
import com.azul.yida.milaazul.weidget.SmartToolbar;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rohitarya.glide.facedetection.transformation.core.GlideFaceDetector;
import com.zhy.base.fileprovider.FileProvider7;

import java.io.File;
import java.util.ArrayList;


import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class milaView extends MvpView {
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.toolbar)
    SmartToolbar toolbar;
    @BindView(R.id.rl_modulename_refresh)
    BGARefreshLayout rlModulenameRefresh;
    @BindView(R.id.tv_custom_title)
    TextView tvTitle;
    Bitmap bitmap;
    String image;
    private BaseQuickAdapter<String, myViewHolder> baseQuickAdapter;

    @Override
    public void regist(@NonNull LayoutInflater inflater) {
        super.regist(inflater);
        GlideFaceDetector.initialize(getActivity());
        initRv();
        toolbar.post(()->{
            toolbar.setTitle("可爱");
        });
        toolbar.setNavigationIcon(R.color.colorPrimary);
    }

    private void initRv() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        baseQuickAdapter = new BaseQuickAdapter<String, myViewHolder>(R.layout.item) {
            @Override
            protected void convert(myViewHolder helper, String item) {
                helper.setVisible(R.id.tv_title,false)
                        .setVisible(R.id.tv_time,false);
                ImageView imageView=helper.getView(R.id.im_beauty);
                //ImgLoader.getInstance().faceCenter(imageView.getContext(),item,imageView);
//                Glide.with(AzulApp.getInstance()).load(R.color.a).error(R.color.c).into(imageView);

                setImage(item,helper.getView(R.id.im_beauty));
            }

            //当item被回收时停止加载图片

            @Override
            public void onViewRecycled(@NonNull myViewHolder holder) {
                super.onViewRecycled(holder);
                ImageView imageView=holder.getView(R.id.im_beauty);
                if(imageView==null)return;
                Glide.clear(imageView);
            }
        };
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(baseQuickAdapter);
        baseQuickAdapter.setOnItemLongClickListener((a,v,p)->{
            setPaper((String)a.getData().get(p));
            return true;
        });
        baseQuickAdapter.setOnItemClickListener((a,v,p)->{
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("images",(ArrayList<String>) a.getData());
            bundle.putInt("index",p);
            Intent intent = new Intent(getActivity(), PictureLookupActivity.class);
            intent.putExtras(bundle);
            getActivity().startActivity(intent);
        });
    }

    public void setData(ArrayList<String> arrayList, int page) {
        Mlog.t("page:"+page+"list.size:"+arrayList.size());
//        if (page == 1) {
            baseQuickAdapter.replaceData(arrayList);
//        } else {
//            baseQuickAdapter.addData(arrayList);
//        }
        baseQuickAdapter.notifyDataSetChanged();
    }

    @Override
    public View getToolbar() {
        return toolbar;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mila;
    }

    private void setPaper(String image){
        AndroidUtils.saveImageAndGetPathObservable(getActivity(),image, Base64.encode(image.getBytes()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uri ->
                        {

                                Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                intent.putExtra("mimeType", "image/*");
                                intent.setData(FileProvider7.getUriForFile(getActivity(), new File(uri.getPath())));
                                getActivity().startActivity(Intent.createChooser(intent, "选择壁纸"));

                        }
                        ,consumer::accept);

    }

    public void setImage(String image,ImageView imageView){
        this.image=image;
        getBitmap(image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(b->{
                    Class<? extends ViewGroup.LayoutParams> LayoutParamsClass=imageView.getLayoutParams().getClass();
                    imageView.setLayoutParams( LayoutParamsClass.getDeclaredConstructor(int.class,int.class).newInstance(((WindowManager) getActivity()
                            .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth(),getBitmapHeight((Bitmap) b)));
                    imageView.setImageBitmap(b);
                },consumer::accept);
    }

    public Observable<Bitmap> getBitmap(String image){
        return Observable.create(e->{
            try {
                bitmap=ImgLoader.getInstance().getBitmap(getActivity(),image);
                e.onNext(bitmap);
            }catch (Throwable throwable){
                throwable.printStackTrace();
                e.onError(throwable);
            }
        });
    }


    public int  getBitmapHeight(Bitmap bitmap){
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        float rate=((float) bitmap.getHeight())/((float) bitmap.getWidth());
        return (int) ((float)width*rate);
    }
}
