package util.retrofit_oauth;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/1/29.
 */
public class AccessToken extends BaseResponse {
    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

//    @SerializedName("expires_in")
//    private Long expiresIn;
//
//    @SerializedName("refresh_token")
//    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        // OAuth requires uppercase Authorization HTTP header value for token type
        if(!Character.isUpperCase(tokenType.charAt(0))){
            tokenType = Character.toString(tokenType.charAt(0)).toUpperCase() + tokenType.substring(1);
        }
        return tokenType;
    }

//    public Long getExpiresIn() {
//        return expiresIn;
//    }
//
//    public String getRefreshToken() {
//        return refreshToken;
//    }

    @Override
    public String toString() {

        if (super.getError() != null) {
            return "AccessToken{error='" + super.getError() + "'}";
        }

        return "AccessToken{" +
                "accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
//                ", expiresIn=" + expiresIn +
//                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
