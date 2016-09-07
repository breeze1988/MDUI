package com.breeze.myapplication.data;

import android.util.Log;
import android.widget.Toast;

import com.breeze.myapplication.MvpApplication;
import com.breeze.myapplication.model.LoginInfoRequest;
import com.breeze.myapplication.model.LoginInfoResponse;
import com.google.gson.Gson;

import java.security.MessageDigest;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xiaohong.wang@dmall.com on 2016/4/26.
 * description:
 */
public final class NetworkService {

    private static final String API_URL = "http://testgw.picking.wms.dmall.com";

//    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private Retrofit mRetrofit;
    private DMLoginService mDMLoginService;


    private NetworkService(){
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(2000, TimeUnit.SECONDS);
        Retrofit.Builder retrofitBuilder  = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
//                .build();
        if(true){
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(logging);
        }
        mRetrofit = retrofitBuilder.client(httpClientBuilder.build()).build();
    }

    private static class SingletonHolder{
        private static final NetworkService INSTANCE = new NetworkService();
    }

    public static NetworkService getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public void login(Subscriber<LoginInfoResponse> subscriber,String userName,String password){
        mDMLoginService = mRetrofit.create(DMLoginService.class);
        LoginInfoRequest loginInfoRequest = new LoginInfoRequest(userName,md5(password),"08067dfc737");
        Observable observable = mDMLoginService.login(new Gson().toJson(loginInfoRequest))
                .map(new HttpResultFunc<LoginInfoResponse>());
        toSubscribe(observable,subscriber);
    }
   public interface DMLoginService{
       @FormUrlEncoded
       @POST("/user/login")
       Observable<HttpResult<LoginInfoResponse>> login(@Field("param") String loginInfoReques);
   }

    private class HttpResultFunc<T> implements Func1<HttpResult<T>,T>{
        @Override
        public T call(HttpResult<T> tHttpResult) {
                if (!tHttpResult.equals("0000")){
                    Log.e("http error response", tHttpResult.getResult());
                    Observable.just(tHttpResult).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<HttpResult<T>>() {
                        @Override
                        public void call(HttpResult<T> tHttpResult) {
                            Toast.makeText(MvpApplication.getInstance().getApplicationContext(), tHttpResult.getResult(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
                return tHttpResult.getData();
        }
    }

    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }


    public static String md5(String origString) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] strTemp = origString.getBytes("utf-8");
            //如果输入“SHA”，就是实现SHA加密。
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

}
