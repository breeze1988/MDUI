package mduicom.breeze.mdui.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wealdtech.hawk.HawkCredentials;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.Bind;
import butterknife.ButterKnife;
import mduicom.breeze.mdui.R;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import util.retrofit_oauth.HawkCredentialsService;
import util.retrofit_oauth.RetrofitAuthentication;
import util.retrofit_oauth.RetrofitUploadFile;
import util.retrofit_oauth.ServiceGenerator;
import util.retrofit_oauth.SimpleService;

/**
 * A simple {@link Fragment} subclass.
 */
public class RetrofitFragment extends Fragment implements View.OnClickListener {


    @Bind(R.id.SimpleService)
    Button SimpleServicebtn;
    @Bind(R.id.SimpleMockService)
    Button SimpleMockServicebtn;
    @Bind(R.id.JsonQueryParameters)
    Button JsonQueryParametersbtn;
    @Bind(R.id.JsonAndXmlConverters)
    Button JsonAndXmlConvertersbtn;
    @Bind(R.id.ErrorHandlingCallAdapter)
    Button ErrorHandlingCallAdapterbtn;
    @Bind(R.id.DeserializeErrorBody)
    Button DeserializeErrorBodybtn;
    @Bind(R.id.ChunkingConverter)
    Button ChunkingConverterbtn;
    @Bind(R.id.tx_RetrofitResponse)
    TextView txRetrofitResponse;
    @Bind(R.id.BasicAuthentication)
    Button BasicAuthentication;
    @Bind(R.id.oauth)
    Button oauth;
    @Bind(R.id.uploadFile)
    Button uploadFile;
    @Bind(R.id.HawkCredentials)
    Button HawkCredentialsbtn;

    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    private static final int HANDLER_WHAT_NETWORK_RESPONSE = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            txRetrofitResponse.setText((String) msg.obj);
        }
    };


    public RetrofitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_retrofit, container, false);
        ButterKnife.bind(this, view);
        SimpleServicebtn.setOnClickListener(this);
        SimpleMockServicebtn.setOnClickListener(this);
        JsonQueryParametersbtn.setOnClickListener(this);
        JsonAndXmlConvertersbtn.setOnClickListener(this);
        ErrorHandlingCallAdapterbtn.setOnClickListener(this);
        DeserializeErrorBodybtn.setOnClickListener(this);
        ChunkingConverterbtn.setOnClickListener(this);
        BasicAuthentication.setOnClickListener(this);
        oauth.setOnClickListener(this);
        uploadFile.setOnClickListener(this);
        HawkCredentialsbtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        if (v == SimpleServicebtn) {
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    Message msg = new Message();
                    msg.obj = SimpleService.getData();
                    mHandler.sendMessage(msg);
                }
            });
        } else if (v == SimpleMockServicebtn) {

        } else if (v == JsonQueryParametersbtn) {

        } else if (v == JsonAndXmlConvertersbtn) {

        } else if (v == ErrorHandlingCallAdapterbtn) {

        } else if (v == DeserializeErrorBodybtn) {

        } else if (v == ChunkingConverterbtn) {

        } else if (v == BasicAuthentication) {

        } else if (v == oauth) {

            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/" + "login")
            );
        } else if (v == uploadFile) {
            uploadFile2server();
        } else if (v == HawkCredentialsbtn){
            hawkCredentials();
        }
    }

    private void hawkCredentials() {
        HawkCredentials hawkCredentials =
                new HawkCredentials.Builder()
                        .keyId("dh37fgj492je")
                        .key("werxhqb98rpaxn39848xrunpaw3489ruxnpa98w4rxn")
                        .algorithm(HawkCredentials.Algorithm.SHA256)
                        .build();

        HawkCredentialsService.UserService userService = ServiceGenerator.createServiceHawkCredentials(HawkCredentialsService.UserService.class, hawkCredentials);
        Call<HawkCredentialsService.User> call = userService.me();
        try {
            HawkCredentialsService.User user = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadFile2server() {
        RetrofitUploadFile.FileUploadService service = ServiceGenerator.createService(RetrofitUploadFile.FileUploadService.class);
        String descriptionString = "hello, this is description speaking";
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
        File file = new File("");
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        Call<String> call = service.upload(requestBody, description);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response) {
                Log.v("Upload", "success");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("Upload", t.getMessage());
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
