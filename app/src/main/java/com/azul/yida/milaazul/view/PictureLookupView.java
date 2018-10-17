package com.azul.yida.milaazul.view;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azul.yida.milaazul.R;
import com.azul.yida.milaazul.view.base.MvpView;
import com.azul.yida.milaazul.weidget.SmartToolbar;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;

/**
 * Created by xuyimin on 2018/10/10.
 * E-mail codingyida@qq.com
 */

public class PictureLookupView extends MvpView {

    @BindView(R.id.toolbar)
    SmartToolbar toolbar;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private List<String> images;

    @Override
    public int getLayoutId() {
        return R.layout.activity_picture_look;
    }

    public void showImages(List<String> images, int index) {
        this.images = images;
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(index, false);
    }

    @Override
    public void regist(@NonNull LayoutInflater inflater) {
        super.regist(inflater);
        toolbar.transparent();
    }

    @Override
    public SmartToolbar getToolbar() {
        return toolbar;
    }



//    public void tryShowSavePictureDialog(final int position) {
//        if (confirmDialogFragment != null
//                && confirmDialogFragment.getDialog() != null
//                && confirmDialogFragment.getDialog().isShowing()) {
//            confirmDialogFragment.dismiss();
//        }
//        RxPermissions rxPermission = new RxPermissions(getActivity());
//        rxPermission.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .subscribe(obtain -> {
//                    if (obtain) {
//                        showSavePictureDialog(position);
//                    } else {
//                        showErrorMsg("没有存储写入权限");
//                    }
//                });
//    }
//
//    private void showSavePictureDialog(final int position) {
//        FragmentActivity activity = getActivity();
//        confirmDialogFragment = new ConfirmDialogFragment();
//        confirmDialogFragment.setOnClickListener((dialog, v) -> {
//            if(v.getId() != R.id.bt_confirm){
//                dialog.dismiss();
//                return;
//            }
//
//            Observable.create(em -> {
//                File file = Glide.with(getActivity())
//                        .load(images.get(position))
//                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                        .get();
//                File newFile = new File(Environment.getExternalStorageDirectory(),"dayou/");
//                if(!newFile.exists()){
//                    newFile.mkdir();
//                }
//                newFile = new File(newFile,System.currentTimeMillis() + ".png");
//                copyFile(file.getAbsolutePath(),newFile.getAbsolutePath());
//                em.onNext(newFile);
//                em.onComplete();
//            })
//                    .compose(applyIOSchedulersAndLifecycle())
//                    .subscribe(file ->{
//                        showErrorMsg(activity.getString(R.string.picture_has_saved_to) +( (File)file).getCanonicalPath());
//                        getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile((File) file)));
//                    },e -> e.printStackTrace());
//            dialog.dismiss();
//        });
//        confirmDialogFragment.show(activity.getSupportFragmentManager(),activity.getString(R.string.save_picture));
//    }

    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }

    private PagerAdapter pagerAdapter = new PagerAdapter() {

        @Override
        public int getCount() {
            return images == null ? 0 : images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(getActivity());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            photoView.setLayoutParams(layoutParams);
            photoView.setBackgroundColor(Color.BLACK);
            container.addView(photoView);
            photoView.setOnLongClickListener(v -> {
//                tryShowSavePictureDialog(position);
                return true;
            });
            String image = images.get(position);
            Glide.with(getActivity()).load(image).into(photoView);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    };
}

