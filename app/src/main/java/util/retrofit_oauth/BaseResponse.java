package util.retrofit_oauth;

/**
 * Created by Administrator on 2016/1/29.
 */
public abstract class BaseResponse {

    private String error = null;

    public String getError(){
        return error;
    }
}
