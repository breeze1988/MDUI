package util.retrofit_oauth;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2016/1/29.
 */
public final class SimpleService {
    public static final String API_URL = "https://api.github.com";

    public static class Contributor{
        public final String login;
        public final int contributions;

        public Contributor(String login,int contributions){
            this.login = login;
            this.contributions = contributions;
        }
    }

    public interface GitHub {
        @GET("/repos/{owner}/{repo}/contributors")
        Call<List<Contributor>> contributors(
                @Path("owner") String owner,
                @Path("repo") String repo);
    }

    public static String getData(){
        StringBuffer sb = new StringBuffer();
        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our GitHub API interface.
        GitHub github = retrofit.create(GitHub.class);

        // Create a call instance for looking up Retrofit contributors.
        Call<List<Contributor>> call = github.contributors("square", "retrofit");

        try {
            // Fetch and print a list of the contributors to the library.
            List<Contributor> contributors = call.execute().body();
            for (Contributor contributor : contributors) {
                sb.append(contributor.login + " (" + contributor.contributions + ")"+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
