package util.retrofit_oauth;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by breeze on 2016/1/31.
 */
public class RetrofitUploadFile {

    public interface FileUploadService{
        @Multipart
        @POST("/upload")
        Call<String> upload(
                @Part("myfile\"; filename=\"image.png\" ") RequestBody file,
                @Part("description") RequestBody description
        );
    }
}
