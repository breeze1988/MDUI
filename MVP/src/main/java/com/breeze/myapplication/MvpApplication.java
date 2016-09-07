package com.breeze.myapplication;

import android.app.Application;

/**
 * Created by xiaohong.wang@dmall.com on 2016/4/12.
 * description:
 */
public class MvpApplication extends Application{
    private static MvpApplication mInstance;

    public static MvpApplication getInstance(){
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
