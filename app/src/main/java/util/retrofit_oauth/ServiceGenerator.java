package util.retrofit_oauth;


import android.net.Uri;
import android.util.Base64;

import com.wealdtech.hawk.HawkClient;
import com.wealdtech.hawk.HawkCredentials;

import java.io.IOException;
import java.net.URI;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2016/1/29.
 */
public final class ServiceGenerator {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

//    private static Retrofit.Builder builder = null;

    public static final String API_BASE_URL = "https://api.github.com";

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private ServiceGenerator(){}

    public static <S> S createService(Class<S> serviceClass,String baseUrl){
        return createService(serviceClass,baseUrl,null);
    }

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    public static <S> S createService(Class<S> serviceClass,String baseUrl,final AccessToken token){
        builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());

        if(token != null){
                httpClient.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request orignal = chain.request();

                        Request.Builder requestBuilder = orignal.newBuilder()
                                .header("Accept","application/json")
                                .header("Authorization",token.getTokenType()+" " +token.getAccessToken())
                                .method(orignal.method(), orignal.body());
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                });
        }
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

   /* 在请求中加入验证*/
    public static <S> S createServiceAuthourization(Class<S> serviceClass,String username,String password){
        if(username != null && password != null){
            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(),Base64.NO_WRAP);

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", basic)
                            .header("Accept", "applicaton/json")
                            .method(original.method(), original.body());
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

   /* Integrate Token Authentication*/
    public static <S> S createSerViceAuthToken(Class<S> serviceClass,final String authToken){
        if(authToken != null){
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    //Request customization:add request header
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization",authToken)
                            .method(original.method(),original.body());
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    //
    public static <S> S createServiceHawkCredentials(
            Class<S> serviceClass, final HawkCredentials credentials) {
        if (credentials != null) {
            final HawkClient hawkClient = new HawkClient.Builder()
                    .credentials(credentials)
                    .build();

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();
                    URI uri = Uri.parse(original.url());
                    String method = original.method();

                    String header =
                            hawkClient.generateAuthorizationHeader(
                                    uri, method, null, null, null, null);

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Accept", "application/json")
                            .header("Content-Type", "application/json")
                            .header("Authorization", header)  // this is the important line
                            .method(method, original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }


//    //token
//    public static <S> S createService(Class<S> serviceClass, final AccessToken token){
//        if(token != null){
//            httpClient.addInterceptor(new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request orignal = chain.request();
//
//                    Request.Builder requestBuilder = orignal.newBuilder()
//                            .header("Accept","application/json")
//                            .header("Authorization",token.getTokenType()+" " +token.getAccessToken())
//                            .method(orignal.method(), orignal.body());
//                    Request request = requestBuilder.build();
//                    return chain.proceed(request;
//                }
//            });
//        }
//        OkHttpClient client = httpClient.build();
//        Retrofit retrofit = builder.client(client).build();
//        return retrofit.create(serviceClass);
//    }
}
