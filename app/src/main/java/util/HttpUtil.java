package util;


import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.net.CacheResponse;
import java.util.Map;

import okhttp3.Cache;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by breeze on 2016/1/15.
 */
public class HttpUtil {

    private final static OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public HttpUtil(File cacheDirectory){
        int cacheSize = 10 * 1024 *1024;
        Cache cache = new Cache(cacheDirectory,cacheSize);
        client.newBuilder().cache(cache).build();
    }


//    https://raw.github.com/square/okhttp/master/README.md
    public static  String getRun(String url){
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()){
                return null;
            }
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String postRun(String url,String json){
        RequestBody body = RequestBody.create(JSON,json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //访问header
    public static String getHeader(String url){
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build();
        Response response = null;
        try {
           response = client.newCall(request).execute();
            if(!response.isSuccessful()){
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            }
            StringBuffer str = new StringBuffer();
            str.append("Server:" + response.header("Server"))
                    .append("\n")
                    .append("Date:" + response.header("Date"))
                    .append("\n")
                    .append("Vary: " + response.headers("Vary"));
            return str.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response +"";
    }

    /*Post方式提交String
    使用HTTP POST提交请求到服务。这个例子提交了一个markdown文档到web服务，以HTML方式渲染markdown。因为整个请求体都在内存中，因此避免使用此api提交大文档（大于1MB）*/
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");
    public static String postString(String url,String postBody){
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN,postBody))
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()){
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String postStream(String url){
        RequestBody requestBody = new RequestBody() {
            @Override public MediaType contentType() {
                return MEDIA_TYPE_MARKDOWN;
            }

            @Override public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8("Numbers\n");
                sink.writeUtf8("-------\n");
                for (int i = 2; i <= 997; i++) {
                    sink.writeUtf8(String.format(" * %s = %s\n", i, factor(i)));
                }
            }

            private String factor(int n) {
                for (int i = 2; i < n; i++) {
                    int x = n / i;
                    if (x * i == n) return factor(x) + " × " + i;
                }
                return Integer.toString(n);
            }
        };

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String postFile(String url,File file) {
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String postForm(String url,RequestBody requestBody){
//        RequestBody formBody = new FormBody.Builder()
//                .add("search", "Jurassic Park")
//                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
          return null;
    }

    private static final String IMGUR_CLIENT_ID = "...";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
   /* MultipartBuilder可以构建复杂的请求体，与HTML文件上传形式兼容。多块请求体中每块请求都是一个请求体，可以定义自己的请求头。这些请求头可以用来描述这块请求，
    例如他的Content-Disposition。如果Content-Length和Content-Type可用的话，他们会被自动添加到请求头中。*/
    public static String postMulti(String url){
//        / Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"title\""),
                        RequestBody.create(null, "Square Logo"))
                .addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"image\""),
                        RequestBody.create(MEDIA_TYPE_PNG, new File("website/static/logo-square.png")))
                .build();

        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                .url("https://api.imgur.com/3/image")
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()){
                throw new IOException("Unexpected code " + response);
            }
           return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String testCache(String url){
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response1 = client.newCall(request).execute();
            if (response1.isSuccessful()){
                throw new IOException("Unexpected code " + response1);
            }
            String response1Body = response1.body().string();
            StringBuffer result = new StringBuffer();
            result.append("Response1 response : " + response1 +"/n")
                    .append("Response1 cache response : " + response1.cacheResponse() + "/n")
                    .append("Response1 network response :" + response1.networkResponse() + "/n");

            Response response2 = client.newCall(request).execute();
            if(!response2.isSuccessful()){
                throw new IOException("Unexpected code " + response2);
            }
            String response2Body = response2.body().string();
            result.append("Response2 response : " + response2 +"/n")
                    .append("Response2 cache response : " + response2.cacheResponse() + "/n")
                    .append("Response2 network response :" + response2.networkResponse() + "/n")
                    .append("Response2 equals Response1 :" +response1Body.equals(response2Body));
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String  getGsonData(String url){
        Request request = new Request.Builder()
                .url("https://api.github.com/gists/c2a7c39532239ff261be")
                .build();
        Gson gson = new Gson();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Gist gist = gson.fromJson(response.body().charStream(), Gist.class);
            StringBuffer sb = new StringBuffer();
            for (Map.Entry<String, GistFile> entry : gist.files.entrySet()) {
                sb.append(entry.getKey() + " : " + entry.getValue().content + "/n");
            }
            return  sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static class Gist {
        Map<String, GistFile> files;
    }

    static class GistFile {
        String content;
    }
}
