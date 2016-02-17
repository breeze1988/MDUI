package util.retrofit_oauth;

import okhttp3.Response;

/**
 * Created by Administrator on 2016/2/1.
 */
public class ErrorUtils {

    public static APIError parseError(Response response){
//        Converter<ResponseBody,APIError> converter =  ServiceGenerator.retrofit()
//                .responseBodyConverter(APIError.class, new Annotation[0]);
        APIError error = null;
//
//        try {
//            error = converter.convert(response.errorBody());
//        } catch (IOException e) {
//            return new APIError();
//        }

        return error;
    }
}
