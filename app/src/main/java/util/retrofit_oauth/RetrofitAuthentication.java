package util.retrofit_oauth;

import android.util.Log;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by breeze on 2016/1/30.
 */
public class RetrofitAuthentication {
    public static final String API_URL = "https://api.github.com";

    public interface LoginService{
        @POST("/login")
        Call<User> basicLogin();
    }

    public static class User{
        public final String login;
        public final int contributions;

        public User(String login,int contributions){
            this.login = login;
            this.contributions = contributions;
        }
    }

    public static String getDataTest(){
        LoginService loginService = ServiceGenerator.createServiceAuthourization(LoginService.class, "wangxiaohong0326@gmail.com", "wxh13788957954");
        Call<User> call = loginService.basicLogin();
        StringBuffer sb = new StringBuffer();
        try {
            User user = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

}
