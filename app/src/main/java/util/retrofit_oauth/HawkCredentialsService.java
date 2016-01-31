package util.retrofit_oauth;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by breeze on 2016/1/31.
 */
public class HawkCredentialsService {

    public interface UserService{
        @POST("/me")
        Call<User> me();
    }

    public static class User{
        public final String login;
        public final int contributions;

        public User(String login,int contributions){
            this.login = login;
            this.contributions = contributions;
        }
    }


}
