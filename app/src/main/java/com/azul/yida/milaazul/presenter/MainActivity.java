package com.azul.yida.milaazul.presenter;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.PaintDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.azul.yida.milaazul.R;
import com.azul.yida.milaazul.common.Mlog;
import com.azul.yida.milaazul.model.Azul;
import com.azul.yida.milaazul.presenter.base.BasePresentActivity;
import com.azul.yida.milaazul.view.MainActivityView;
import com.azul.yida.milaazul.view.base.MvpView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import butterknife.OnClick;

public class MainActivity extends BasePresentActivity<MainActivityView> {

    private RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView=(RecyclerView) findViewById(R.id.recycle_view);
        initRecycleView();
    }

    @Override
    public Class<MainActivityView> getPresentClass() {
        return MainActivityView.class;
    }

    private void initRecycleView(){
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        ArrayList<Azul> list=new ArrayList<>();
        for(int a=0;a<20;a++){
            list.add(new Azul());
        }
        BaseQuickAdapter adapter=new BaseQuickAdapter<Azul,BaseViewHolder>(R.layout.item){


            @Override
            protected void convert(BaseViewHolder helper, Azul item) {
             //helper.setImageDrawable(R.id.imageView,new BitmapDrawable());
            }
        };
        adapter.addData(list);
        recyclerView.setAdapter(adapter);
    }
}
