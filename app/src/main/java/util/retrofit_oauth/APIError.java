package util.retrofit_oauth;

/**
 * Created by breeze on 2016/2/6.
 */
public class APIError {
    private int statusCode;
    private String message;

    public APIError(){}

    public int getStatusCode(){
        return statusCode;
    }

    public String getMessage(){
        return message;
    }
}
