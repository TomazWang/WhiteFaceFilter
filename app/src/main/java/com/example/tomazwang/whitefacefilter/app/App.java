package com.example.tomazwang.whitefacefilter.app;

import android.app.Application;

/**
 * Created by TomazWang on 2018/06/07.
 *
 * @author Tomaz Wang
 * @since 2018/06/07
 **/

public class App extends Application {

    private static Application sInstance;

    public static Application get() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
