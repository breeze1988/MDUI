package mduicom.breeze.mdui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.Bind;
import butterknife.ButterKnife;
import mduicom.breeze.mdui.R;
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

    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    private static final int HANDLER_WHAT_NETWORK_RESPONSE = 0;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            txRetrofitResponse.setText((String)msg.obj);
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

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        if(v == SimpleServicebtn){
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    Message msg = new Message();
                    msg.obj = SimpleService.getData();
                    mHandler.sendMessage(msg);
                }
            });
        }else if( v == SimpleMockServicebtn){

        }else if( v == JsonQueryParametersbtn){

        }else if( v == JsonAndXmlConvertersbtn){

        }else if( v == ErrorHandlingCallAdapterbtn){

        }else if( v == DeserializeErrorBodybtn){

        }else if( v == ChunkingConverterbtn){

        }
    }
}
