package com.azul.yida.milaazul.view;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azul.yida.milaazul.R;
import com.azul.yida.milaazul.common.AndroidUtils;
import com.azul.yida.milaazul.common.Base64;
import com.azul.yida.milaazul.common.ImgLoader;
import com.azul.yida.milaazul.common.Mlog;
import com.azul.yida.milaazul.common.RxUtil;
import com.azul.yida.milaazul.net.Entity.DateModel;
import com.azul.yida.milaazul.net.Entity.Gank;
import com.azul.yida.milaazul.presenter.MilaActivity;
import com.azul.yida.milaazul.presenter.WebActivity;
import com.azul.yida.milaazul.view.adapter.myViewHolder;
import com.azul.yida.milaazul.view.base.MvpView;
import com.azul.yida.milaazul.weidget.SmartToolbar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example2.lrudemo.toast.ToastUtil;
import com.zhy.base.fileprovider.FileProvider7;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class DailyView extends MvpView {
    @BindView(R.id.iv_background)
    ImageView imageView;
    @BindView(R.id.tv_paper)
    TextView tvPaper;
    @BindView(R.id.scroll_view)
    RecyclerView nestedScrollView;
    DateModel model;
    ArrayList<Gank> arrayList;
    Bitmap bitmap;
    String image;

    private BaseQuickAdapter<Gank, myViewHolder> baseQuickAdapter;

    @Override
    public void regist(@NonNull LayoutInflater inflater) {
        super.regist(inflater);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            initRv();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initRv() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        baseQuickAdapter = new BaseQuickAdapter<Gank, myViewHolder>(R.layout.item1) {


            @Override
            public void onBindViewHolder(myViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

            }

            @Override
            protected void convert(myViewHolder helper, Gank item) {
                if(item.getImages()!=null&&item.getImages().size()>0) {
                    helper.setIamgeUrl(mContext,R.id.imageView,item.getImages().get(0).replace("https","http"));
                }else helper.setIamgeUrl(mContext,R.id.imageView,"http://www.github.com/favicon.ico");
                helper.setText(R.id.tv_auth,item.getWho())
                        .setText(R.id.tv_title,item.getDesc())
                        .addOnClickListener(R.id.lay_content)
                        .setText(R.id.tv_time,item.getCreatedAt());

            }
        };
        nestedScrollView.setLayoutManager(linearLayoutManager);
        nestedScrollView.setAdapter(baseQuickAdapter);
        baseQuickAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            WebActivity.startWebActivity(view.getContext(),((Gank)adapter.getData().get(position)).getDesc(),arrayList.get(position).getUrl());
        });



    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_daily;
    }

    public void setImage(String image){
        this.image=image;
        getBitmap(image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(b->{
                    tvPaper.setOnClickListener((v)->setPaper(bitmap));
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



    public void setData(DateModel model){
        this.model= model;
        arrayList=new ArrayList<Gank>();
        arrayList.addAll(model.getAndroid());

        arrayList.addAll(model.getiOS());

        arrayList.addAll(model.getFront());

        arrayList.addAll(model.getVedio());

        baseQuickAdapter.addData(arrayList);


        baseQuickAdapter.notifyDataSetChanged();

    }

    private void setPaper(Bitmap bitmap){
        AndroidUtils.saveImageAndGetPathObservable(getActivity(),image, Base64.encode(image.getBytes()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uri ->
                        {
                            try {
                                WallpaperManager wpm = (WallpaperManager) getActivity().getSystemService(
                                        Context.WALLPAPER_SERVICE);
                                wpm.setBitmap(bitmap);
                                showToast("设置成功");
                            } catch (IOException e) {
                                Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                intent.putExtra("mimeType", "image/*");
                                intent.setData(FileProvider7.getUriForFile(getActivity(), new File(uri.getPath())));
                                getActivity().startActivity(Intent.createChooser(intent, "选择壁纸"));
                            }
                            }
                        ,consumer::accept);

    }



}
