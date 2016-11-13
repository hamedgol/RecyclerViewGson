package com.bwie.test.utils;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Administrator on 2016/11/11.
 *
 *
 *
 */
public class ImageLoder extends Application{
    @Override
    public void onCreate() {
        ImageLoaderConfiguration configuration=ImageLoaderConfiguration.createDefault(this);
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(configuration);
        super.onCreate();
    }
}
