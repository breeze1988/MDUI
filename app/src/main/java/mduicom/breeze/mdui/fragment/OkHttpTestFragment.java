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

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.Bind;
import butterknife.ButterKnife;
import mduicom.breeze.mdui.R;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import util.HttpUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OkHttpTestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OkHttpTestFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Bind(R.id.btn_okHttpGet)
    Button mOkHttpGetBtn;
    @Bind(R.id.btn_okHttpPost)
    Button mOkHttpPostBtn;
    @Bind(R.id.btn_okHttpHeader)
    Button mOkHttpHeaderBtn;

    @Bind(R.id.btn_okHttpPostString)
    Button mOkHttpPostStringbtn;

    @Bind(R.id.btn_okHttpPostStream)
    Button mOkHttpPostStreambtn;

    @Bind(R.id.btn_okHttpPostForm)
    Button mOkHttpPostformBtn;

    @Bind(R.id.btn_okHttpPostFile)
    Button mOkHttpPostFileBtn;

    @Bind(R.id.btn_okHttpPostMultipart)
    Button mOkHttpPostMultipartBtn;

    @Bind(R.id.btn_okHttpCacheResponse)
    Button mOkHttpCacheReponseBtn;

    @Bind(R.id.btn_okHttGsonResponse)
    Button mOkHttpGsonReponseBtn;

    @Bind(R.id.btn_okHttTimeout)
    Button mOkHttpTimeoutBtn;
    @Bind(R.id.btn_okHttCallCancle)
    Button mOkHttpCallCancleBtn;
    @Bind(R.id.btn_okHttConfigCall)
    Button mOkHttpConfigCall;
    @Bind(R.id.btn_okHttHandleAuth)
    Button mOkHttpHandleAuthBtn;

    @Bind(R.id.tx_okHttpResponse)
    TextView mOkHttpResponse;

    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    private static final int HANDLER_WHAT_OKHTTP_RESPONSE = 10;

    private MyHandler mHandler = new MyHandler();

    private class MyHandler extends Handler {

        public MyHandler() {
        }

        @Override
        public void handleMessage(Message msg) {
            String content = (String) msg.obj;
            if (HANDLER_WHAT_OKHTTP_RESPONSE == msg.what) {
                mOkHttpResponse.setText(content);
            }
        }
    }

    public OkHttpTestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OkHttpTestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OkHttpTestFragment newInstance(String param1, String param2) {
        OkHttpTestFragment fragment = new OkHttpTestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ok_http_test, container, false);
        ButterKnife.bind(this,view);
        mOkHttpGetBtn.setOnClickListener(this);
        mOkHttpPostBtn.setOnClickListener(this);
        mOkHttpHeaderBtn.setOnClickListener(this);
        mOkHttpPostStringbtn.setOnClickListener(this);
        mOkHttpPostStreambtn.setOnClickListener(this);
        mOkHttpPostformBtn.setOnClickListener(this);
        mOkHttpPostFileBtn.setOnClickListener(this);
        mOkHttpPostMultipartBtn.setOnClickListener(this);
        mOkHttpCacheReponseBtn.setOnClickListener(this);
        mOkHttpGsonReponseBtn.setOnClickListener(this);
        mOkHttpTimeoutBtn.setOnClickListener(this);
        mOkHttpCallCancleBtn.setOnClickListener(this);
        mOkHttpConfigCall.setOnClickListener(this);
        mOkHttpHandleAuthBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == mOkHttpGetBtn) {
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendMessage(mHandler.obtainMessage(HANDLER_WHAT_OKHTTP_RESPONSE, HttpUtil.getRun("https://raw.github.com/square/okhttp/master/README.md")));
                }
            });
        } else if (v == mOkHttpPostBtn) {
            final String str = "{'winCondition':'HIGH_SCORE',"
                    + "'name':'Bowling',"
                    + "'round':4,"
                    + "'lastSaved':1367702411696,"
                    + "'dateStarted':1367702378785,"
                    + "'players':["
                    + "{'name':'" + "breeze" + "','history':[10,8,6,7,8],'color':-13388315,'total':39},"
                    + "{'name':'" + "zyz" + "','history':[6,10,5,10,10],'color':-48060,'total':41}"
                    + "]}";
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendMessage(mHandler.obtainMessage(HANDLER_WHAT_OKHTTP_RESPONSE, HttpUtil.postRun("http://www.roundsapp.com/post", str)));
                }
            });
        } else if (v == mOkHttpHeaderBtn) {
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendMessage(mHandler.obtainMessage(HANDLER_WHAT_OKHTTP_RESPONSE, HttpUtil.getHeader("https://api.github.com/repos/square/okhttp/issues")));
                }
            });
        } else if (v == mOkHttpPostStringbtn) {
            final String postBody = ""
                    + "Releases\n"
                    + "--------\n"
                    + "\n"
                    + " * _1.0_ May 6, 2013\n"
                    + " * _1.1_ June 15, 2013\n"
                    + " * _1.2_ August 11, 2013\n";
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendMessage(mHandler.obtainMessage(HANDLER_WHAT_OKHTTP_RESPONSE, HttpUtil.postString("https://api.github.com/markdown/raw",postBody)));
                }
            });
        }else if (v == mOkHttpPostStreambtn) {
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendMessage(mHandler.obtainMessage(HANDLER_WHAT_OKHTTP_RESPONSE, HttpUtil.postStream("https://api.github.com/markdown/raw")));
                }
            });
        }else if (v == mOkHttpPostformBtn) {
            final RequestBody formBody = new FormBody.Builder()
                    .add("search", "Jurassic Park")
                    .build();
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendMessage(mHandler.obtainMessage(HANDLER_WHAT_OKHTTP_RESPONSE, HttpUtil.postForm("https://en.wikipedia.org/w/index.php",formBody)));
                }
            });
        }else if (v == mOkHttpPostFileBtn) {
            final File file = new File("README.md");
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendMessage(mHandler.obtainMessage(HANDLER_WHAT_OKHTTP_RESPONSE, HttpUtil.postFile("https://api.github.com/markdown/raw",file)));
                }
            });
        }else if (v == mOkHttpPostMultipartBtn) {
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendMessage(mHandler.obtainMessage(HANDLER_WHAT_OKHTTP_RESPONSE, HttpUtil.postMulti("https://api.imgur.com/3/image")));
                }
            });
        }else if (v == mOkHttpCacheReponseBtn) {
            HttpUtil httpUtil = new HttpUtil(getActivity().getExternalCacheDir());
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendMessage(mHandler.obtainMessage(HANDLER_WHAT_OKHTTP_RESPONSE, HttpUtil.testCache("http://publicobject.com/helloworld.txt")));
                }
            });
        }else if (v == mOkHttpGsonReponseBtn) {
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendMessage(mHandler.obtainMessage(HANDLER_WHAT_OKHTTP_RESPONSE, HttpUtil.getGsonData("https://api.github.com/gists/c2a7c39532239ff261be")));
                }
            });
        }else if (v == mOkHttpTimeoutBtn) {
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendMessage(mHandler.obtainMessage(HANDLER_WHAT_OKHTTP_RESPONSE, HttpUtil.testTimeout("http://httpbin.org/delay/2")));
                }
            });
        }else if (v == mOkHttpCallCancleBtn) {
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendMessage(mHandler.obtainMessage(HANDLER_WHAT_OKHTTP_RESPONSE, HttpUtil.getGsonData("https://api.github.com/repos/square/okhttp/issues")));
                }
            });
        }else if (v == mOkHttpConfigCall) {
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendMessage(mHandler.obtainMessage(HANDLER_WHAT_OKHTTP_RESPONSE, HttpUtil.getGsonData("https://api.github.com/repos/square/okhttp/issues")));
                }
            });
        }else if (v == mOkHttpHandleAuthBtn) {
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendMessage(mHandler.obtainMessage(HANDLER_WHAT_OKHTTP_RESPONSE, HttpUtil.getGsonData("https://api.github.com/repos/square/okhttp/issues")));
                }
            });
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
