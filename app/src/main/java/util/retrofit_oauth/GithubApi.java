package util.retrofit_oauth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by breeze on 2016/2/5.
 */
public class GithubApi {
    public static final String API_GITHUP = "https://api.github.com";

    public static void main(String... agrs) throws IOException{
        //获得github 用户信息
        GithubUserInfo githubUserInfo = ServiceGenerator.createService(GithubUserInfo.class, API_GITHUP);
        Call<UserInfo> userInfoCall = githubUserInfo.userInfo("breeze1988");

        UserInfo userInfo = userInfoCall.execute().body();
        System.out.println(userInfo.toString());

        //
        ArrayList<String> scopes = new ArrayList<String>();
        scopes.add("repo");
        scopes.add("user");
        String note = "MDUI";
        RequestBodyCreateAuthorization requestBodyCreateAuthorization = new RequestBodyCreateAuthorization(scopes,note);
        GithubCreateAuthorizations createAuthorizationsService = ServiceGenerator.createServiceAuthourization(GithubCreateAuthorizations.class, "breeze1988", "wxh13788957954");
//        ErrorHandlingCallAdapter.MyCall<ResponseCreateAuthorization> authorizationCall = createAuthorizationsService.createAuthorization(requestBodyCreateAuthorization);
        Call<ResponseCreateAuthorization> authorizationCall = createAuthorizationsService.createAuthorization(requestBodyCreateAuthorization);
        ResponseCreateAuthorization ResponseCreateAuthorizationInfo = authorizationCall.execute().body();
//        authorizationCall.enqueue(new ErrorHandlingCallAdapter.MyCallback<ResponseCreateAuthorization>() {
//            @Override
//            public void success(Response<ResponseCreateAuthorization> response) {
//                System.out.println("SUCCESS! " + response.body().toString());
//            }
//
//            @Override
//            public void unauthenticated(Response<?> response) {
//                System.out.println("UNAUTHENTICATED");
//            }
//
//            @Override
//            public void clientError(Response<?> response) {
//                System.out.println("CLIENT ERROR " + response.code() + " " + response.message());
//            }
//
//            @Override
//            public void serverError(Response<?> response) {
//                System.out.println("SERVER ERROR " + response.code() + " " + response.message());
//            }
//
//            @Override
//            public void networkError(IOException e) {
//                System.err.println("NETOWRK ERROR " + e.getMessage());
//            }
//
//            @Override
//            public void unexpectedError(Throwable t) {
//                System.err.println("FATAL ERROR " + t.getMessage());
//            }
//        });
        System.out.println(ResponseCreateAuthorizationInfo.toString());
    }

//    public static String getAuthorization() throws IOException{
//        ArrayList<String> scopes = new ArrayList<String>();
//        scopes.add("repo");
//        scopes.add("user");
//        String note = "MDUI";
//        RequestBodyCreateAuthorization requestBodyCreateAuthorization = new RequestBodyCreateAuthorization(scopes,note);
//        GithubCreateAuthorizations createAuthorizationsService = ServiceGenerator.createServiceAuthourization(GithubCreateAuthorizations.class,"breeze1988","wxh13788957954");
//        Call<ResponseCreateAuthorization> authorizationCall = createAuthorizationsService.createAuthorization(requestBodyCreateAuthorization);
//        ResponseCreateAuthorization ResponseCreateAuthorizationInfo = authorizationCall.execute().body();
//        System.out.println(ResponseCreateAuthorizationInfo.toString());
//        return ResponseCreateAuthorizationInfo.toString();
//    }

    public static class UserInfo {
        public final String login;
        public final int id;
        public final String url;
        public final String name;
        public final String company;
        public final String location;
        public final String email;

        public UserInfo(String login, int id, String url, String name, String company, String location, String email) {
            this.login = login;
            this.id = id;
            this.url = url;
            this.name = name;
            this.company = company;
            this.location = location;
            this.email = email;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "login='" + login + '\'' +
                    ", id=" + id +
                    ", url='" + url + '\'' +
                    ", name='" + name + '\'' +
                    ", company='" + company + '\'' +
                    ", location='" + location + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }

        public interface GithubUserInfo{
            @GET("/users/{usersname}")
            Call<UserInfo> userInfo(
                    @Path("usersname") String usersname
            );
        }



     //根据用户名密码生成auth
       public static class RequestBodyCreateAuthorization{
        private ArrayList<String> scopes = new ArrayList<String>();
        private String note;

        public RequestBodyCreateAuthorization(ArrayList<String> scopes,String note){
            this.scopes = scopes;
            this.note = note;
        }

    }

    public static class ResponseCreateAuthorization{
        public List<String> scopes = new ArrayList<String>();
        public String token;
        public String updatedAt;
        public String url;
        public App app;
        public String createdAt;
        public Object noteUrl;
        public Integer id;
        public String note;

        public class App {
            public String url;
            public String name;
        }

        @Override
        public String toString() {
            return "ResponseCreateAuthorization{" +
                    "scopes=" + scopes +
                    ", token='" + token + '\'' +
                    ", updatedAt='" + updatedAt + '\'' +
                    ", url='" + url + '\'' +
                    ", app=" + app +
                    ", createdAt='" + createdAt + '\'' +
                    ", noteUrl=" + noteUrl +
                    ", id=" + id +
                    ", note='" + note + '\'' +
                    '}';
        }
    }
            public interface GithubCreateAuthorizations{
//               @POST("/authorizations")
//               ErrorHandlingCallAdapter.MyCall<ResponseCreateAuthorization> createAuthorization(@Body RequestBodyCreateAuthorization body);
                 @POST("/authorizations")
                 Call<ResponseCreateAuthorization> createAuthorization(@Body RequestBodyCreateAuthorization body);
           }


}
