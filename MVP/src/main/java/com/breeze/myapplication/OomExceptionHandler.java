package com.breeze.myapplication;

import android.content.Context;
import android.os.Debug;

import java.io.File;

/**
 * Created by xiaohong.wang@dmall.com on 2016/5/4.
 * description:内存溢出捕获
 *
 * https://gist.github.com/pyricau/4726389fd64f3b7c6f32
 */
public class OomExceptionHandler implements Thread.UncaughtExceptionHandler{
    private static final String FILE_NAME = "out-of-memory.hprof";
    private final Thread.UncaughtExceptionHandler defaultHandler;
    private final Context mContext;

    public OomExceptionHandler(Thread.UncaughtExceptionHandler defaultHandler,Context context){
        this.defaultHandler = defaultHandler;
        this.mContext = context;
    }

    public static void install(Context context){
        Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        if(defaultHandler instanceof OomExceptionHandler){
            return;
        }
        OomExceptionHandler oomExceptionHandler = new OomExceptionHandler(defaultHandler,context);
        Thread.setDefaultUncaughtExceptionHandler(oomExceptionHandler);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (containsOom(ex)){
            File headDumpFile = new File(mContext.getFilesDir(),FILE_NAME);
            try{
                Debug.dumpHprofData(headDumpFile.getAbsolutePath());
            }catch (Throwable ignored){

            }
        }
         defaultHandler.uncaughtException(thread,ex);
    }

    private boolean containsOom(Throwable ex){
        if(ex instanceof OutOfMemoryError){
            return true;
        }
        while ((ex = ex.getCause()) != null){
            if(ex instanceof OutOfMemoryError){
                return true;
            }
        }
        return false;
    }
}
