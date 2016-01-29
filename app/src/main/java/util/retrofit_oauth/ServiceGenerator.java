package util.retrofit_oauth;


import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2016/1/29.
 */
public final class ServiceGenerator {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder = null;

    private ServiceGenerator(){}

    public static <S> S createService(Class<S> serviceClass,String baseUrl){
        return createService(serviceClass,baseUrl,null);
    }

    public static <S> S createService(Class<S> serviceClass,String baseUrl,final AccessToken accessToken){
        builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());

        if(accessToken != null){

        }
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}
