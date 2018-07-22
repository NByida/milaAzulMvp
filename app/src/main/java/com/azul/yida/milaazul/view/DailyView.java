package com.azul.yida.milaazul.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
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

import com.azul.yida.milaazul.R;
import com.azul.yida.milaazul.common.Mlog;
import com.azul.yida.milaazul.net.Entity.Gank;
import com.azul.yida.milaazul.view.adapter.myViewHolder;
import com.azul.yida.milaazul.view.base.MvpView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example2.lrudemo.toast.DisplayUtils;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.schedulers.Schedulers;

import static java.lang.StrictMath.abs;

public class DailyView extends MvpView {
    @BindView(R.id.iv_background)
    ImageView imageView;

    @BindView(R.id.scroll_view)
    RecyclerView nestedScrollView;

//    @BindView(R.id.lay_content)
//    View laycontent;

    private BaseQuickAdapter<String, myViewHolder> baseQuickAdapter;

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
        baseQuickAdapter = new BaseQuickAdapter<String, myViewHolder>(R.layout.item1) {
            @Override
            protected void convert(myViewHolder helper, String item) {
            }

            @Override
            public void onBindViewHolder(myViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

            }
        };
        nestedScrollView.setLayoutManager(linearLayoutManager);
        nestedScrollView.setAdapter(baseQuickAdapter);
        ArrayList list=new ArrayList<String>();
        for(int i=0;i<56;i++){
            list.add("");
        }
        baseQuickAdapter.addData(list);
        baseQuickAdapter.notifyDataSetChanged();
        ImageView imageView1=new ImageView(getActivity());
        imageView.setImageDrawable(getActivity().getDrawable(R.drawable.bg_pink));
        float aa=0.5563f;

        nestedScrollView.setOnScrollChangeListener((View.OnScrollChangeListener) (v, x, y, ox, oy)->{
            float a=Math.abs((float)(0.1123-((float) 2083-getDistance(nestedScrollView))/(((float)(2083+281)))));
            //laycontent.setAlpha( Math.abs(1-((float) 2083-getDistance(nestedScrollView))/(((float)(2083+281)))));
            //laycontent.setBackgroundColor(Color.argb((float) a*255,0,139,0));
          //  laycontent.setBackground(getActivity().getDrawable(R.drawable.bg_pink));
            float b=aa+aa*a;

           // nestedScrollView.getBackground().setAlpha((int)(b*(float)255));

            Mlog.t("alpha"+(int)(a*(float)255)+"a" +
                    ""+a);
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_daily;
    }

    public void setImage(String image){

        //Glide.with(getActivity()).load(image).asBitmap()
        Observable.create(e -> {

            e.onNext(getBitmap(image));
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(b->{
//                    CollapsingToolbarLayout.LayoutParams layoutParams=new CollapsingToolbarLayout.LayoutParams(1080,getBitmapHeight((Bitmap) b));
//                    imageView.setLayoutParams(layoutParams);
                    Class<? extends ViewGroup.LayoutParams> LayoutParamsClass=imageView.getLayoutParams().getClass();
                    imageView.setLayoutParams( LayoutParamsClass.getDeclaredConstructor(int.class,int.class).newInstance(((WindowManager) getActivity()
                            .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth(),300+getBitmapHeight((Bitmap) b)));

                    imageView.setImageBitmap((Bitmap) b);
                    imageView.requestLayout();
//                    imageView.setLayoutParams(imageView.getLayoutParams());
                    int height=imageView.getLayoutParams().height;
                    int width=imageView.getLayoutParams().width;
                    Mlog.t("w:"+width+"h:"+height);
                    Mlog.t(""+imageView.getLayoutParams().getClass());
                });



    }
    private Bitmap getBitmap(String image){
        Bitmap  myBitmap=null;
        try {
              myBitmap = Glide.with(getActivity())
                    .load(image)
                    .asBitmap()
                    .centerCrop()
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            Mlog.t("get bitmap success");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return    myBitmap;
    }
    /**
     * 还能向下滑动多少
     */

    public int  getBitmapHeight(Bitmap bitmap){
        WindowManager wm = (WindowManager) getActivity()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        float rate=((float) bitmap.getHeight())/((float) bitmap.getWidth());
        Mlog.t("bitmap.getHeight():"+bitmap.getHeight()+"bitmap.getWidth():"+bitmap.getWidth());
        return (int) ((float)width*rate);
    }

    private int getDistance(RecyclerView mRecyclerView){
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        View firstVisibItem = mRecyclerView.getChildAt(0);
        int firstItemPosition = layoutManager.findFirstVisibleItemPosition();
        int itemCount = layoutManager.getItemCount();
        int recycleViewHeight = mRecyclerView.getHeight();
        int itemHeight = firstVisibItem.getHeight();
        int firstItemBottom = layoutManager.getDecoratedBottom(firstVisibItem);
        return (itemCount - firstItemPosition - 1)* itemHeight - recycleViewHeight;

    }

}
