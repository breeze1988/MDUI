package mduicom.breeze.mdui.application;

import android.app.Application;

/**
 * Created by Administrator on 2016/2/17.
 */
public class MduiApplication extends Application{
    private static MduiApplication mInstance ;

    public static MduiApplication getmInstance(){
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
