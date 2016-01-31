package util.retrofit_oauth;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by breeze on 2016/1/31.
 */
public class Oauth {
    public interface LoginService {
        @FormUrlEncoded
        @POST("/token")
        Call<AccessToken> getAccessToken(
                @Field("code") String code,
                @Field("grant_type") String grantType);
    }

}
