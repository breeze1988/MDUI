package mduicom.breeze.mdui.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.Bind;
import butterknife.ButterKnife;
import mduicom.breeze.mdui.R;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import util.HttpUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private FragmentTransaction ft = null;

    @Bind(R.id.btn_okHttpGet)
    Button mOkHttpGetBtn;
    @Bind(R.id.btn_okHttpPost)
    Button mOkHttpPostBtn;
    @Bind(R.id.btn_okHttpHeader)
    Button mOkHttpHeaderBtn;
    @Bind(R.id.tx_okHttpGetResponse)
    EditText mOkHttpGetResponseTxt;
    @Bind(R.id.tx_okHttpPosteResponse)
    EditText mOkHttpPostResponseTxt;
    @Bind(R.id.tx_okHttpHeaderResponse)
    EditText mOkHttpHeaderResponseTxt;

    @Bind(R.id.btn_okHttpPostString)
    Button mOkHttpPostStringbtn;
    @Bind(R.id.tx_okHttpPostString)
    EditText mOkHttpPostStringEdit;

    @Bind(R.id.btn_okHttpPostStream)
    Button mOkHttpPostStreambtn;
    @Bind(R.id.tx_okHttpPostStream)
    EditText mOkHttpPostStreamTxt;

    @Bind(R.id.btn_okHttpPostForm)
    Button mOkHttpPostformBtn;
    @Bind(R.id.tx_okHttpPostForm)
    EditText mOkHttpPostFormTxt;

    @Bind(R.id.btn_okHttpPostFile)
    Button mOkHttpPostFileBtn;
    @Bind(R.id.tx_okHttpPostFile)
    EditText mOkHttpPostFileEdit;

    @Bind(R.id.btn_okHttpPostMultipart)
    Button mOkHttpPostMultipartBtn;
    @Bind(R.id.tx_okHttpPostMultipart)
    EditText mOkHttpPostMultipartEdit;

    @Bind(R.id.btn_okHttpCacheResponse)
    Button mOkHttpCacheReponseBtn;
    @Bind(R.id.tx_okHttpCacheReponse)
    EditText mOkHttpCacheEdit;

    @Bind(R.id.btn_okHttGsonResponse)
    Button mOkHttpGsonReponseBtn;
    @Bind(R.id.tx_okHttpGsonReponse)
    EditText mOkHttpGsonEdit;


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

    private static final int HANDLER_WHAT_OKHTTP_GET_RESOPONSE = 0;
    private static final int HANDLER_WHAT_OKHTTP_POST_RESOPONSE = 1;
    private static final int HANDLER_WHAT_OKHTTP_HEADER_RESOPONSE = 2;
    private static final int HANDLER_WHAT_OKHTTP_POST_STRING_RESPONSE = 3;
    private static final int HANDLER_WHAT_OKHTTP_POST_STREAM_RESPONSE = 4;
    private static final int HANDLER_WHAT_OKHTTP_POST_FORM_RESPONSE = 5;
    private static final int HANDLER_WHAT_OKHTTP_POST_FILE_RESPONSE = 6;
    private static final int HANDLER_WHAT_OKHTTP_POST_MULTIPART_RESPONSE = 7;
    private static final int HANDLER_WHAT_OKHTTP_CACHE_RESPONSE = 8;
    private static final int HANDLER_WHAT_OKHTTP_GSON_RESPONSE = 9;

    private static final int HANDLER_WHAT_OKHTTP_RESPONSE = 10;


    private MyHandler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        public MyHandler(MainActivity activity) {
            this.mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivity.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

//        try {
//            ft = getFragmentManager().beginTransaction();
//            OkHttpTestFragment okHttpTestFragment = new OkHttpTestFragment();
//            ft.add(R.id.main_content,okHttpTestFragment).show(okHttpTestFragment).commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    protected void handleMessage(Message msg) {
        String content = (String) msg.obj;
        if (HANDLER_WHAT_OKHTTP_GET_RESOPONSE == msg.what) {
            mOkHttpGetResponseTxt.setText(content);
        } else if (HANDLER_WHAT_OKHTTP_POST_RESOPONSE == msg.what) {
            mOkHttpPostResponseTxt.setText(content);
        }else if (HANDLER_WHAT_OKHTTP_HEADER_RESOPONSE == msg.what) {
            mOkHttpHeaderResponseTxt.setText(content);
        }else if (HANDLER_WHAT_OKHTTP_POST_STRING_RESPONSE == msg.what) {
            mOkHttpPostStringEdit.setText(content);
        }else if (HANDLER_WHAT_OKHTTP_POST_STREAM_RESPONSE == msg.what) {
            mOkHttpPostStreamTxt.setText(content);
        }else if (HANDLER_WHAT_OKHTTP_POST_FORM_RESPONSE == msg.what) {
            mOkHttpPostFormTxt.setText(content);
        }else if (HANDLER_WHAT_OKHTTP_POST_FILE_RESPONSE == msg.what) {
            mOkHttpPostFileEdit.setText(content);
        }else if (HANDLER_WHAT_OKHTTP_POST_MULTIPART_RESPONSE == msg.what) {
            mOkHttpPostMultipartEdit.setText(content);
        }else if (HANDLER_WHAT_OKHTTP_CACHE_RESPONSE == msg.what) {
            mOkHttpCacheEdit.setText(content);
        }else if (HANDLER_WHAT_OKHTTP_GSON_RESPONSE == msg.what) {
            mOkHttpGsonEdit.setText(content);
        }else if (HANDLER_WHAT_OKHTTP_RESPONSE == msg.what) {
            mOkHttpResponse.setText(content);
        }
    }


    @Override
    public void onClick(View v) {
        if (v == mOkHttpGetBtn) {
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendMessage(mHandler.obtainMessage(HANDLER_WHAT_OKHTTP_GET_RESOPONSE, HttpUtil.getRun("https://raw.github.com/square/okhttp/master/README.md")));
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
                    mHandler.sendMessage(mHandler.obtainMessage(HANDLER_WHAT_OKHTTP_POST_RESOPONSE, HttpUtil.postRun("http://www.roundsapp.com/post", str)));
                }
            });
        } else if (v == mOkHttpHeaderBtn) {
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendMessage(mHandler.obtainMessage(HANDLER_WHAT_OKHTTP_HEADER_RESOPONSE, HttpUtil.getHeader("https://api.github.com/repos/square/okhttp/issues")));
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
                    mHandler.sendMessage(mHandler.obtainMessage(HANDLER_WHAT_OKHTTP_POST_STRING_RESPONSE, HttpUtil.postString("https://api.github.com/markdown/raw",postBody)));
                }
            });
        }else if (v == mOkHttpPostStreambtn) {
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendMessage(mHandler.obtainMessage(HANDLER_WHAT_OKHTTP_POST_STREAM_RESPONSE, HttpUtil.postStream("https://api.github.com/markdown/raw")));
                }
            });
        }else if (v == mOkHttpPostformBtn) {
            final RequestBody formBody = new FormBody.Builder()
                    .add("search", "Jurassic Park")
                    .build();
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendMessage(mHandler.obtainMessage(HANDLER_WHAT_OKHTTP_POST_FORM_RESPONSE, HttpUtil.postForm("https://en.wikipedia.org/w/index.php",formBody)));
                }
            });
        }else if (v == mOkHttpPostFileBtn) {
            final File file = new File("README.md");
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendMessage(mHandler.obtainMessage(HANDLER_WHAT_OKHTTP_POST_FILE_RESPONSE, HttpUtil.postFile("https://api.github.com/markdown/raw",file)));
                }
            });
        }else if (v == mOkHttpPostMultipartBtn) {
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendMessage(mHandler.obtainMessage(HANDLER_WHAT_OKHTTP_POST_MULTIPART_RESPONSE, HttpUtil.postMulti("https://api.imgur.com/3/image")));
                }
            });
        }else if (v == mOkHttpCacheReponseBtn) {
            HttpUtil httpUtil = new HttpUtil(getExternalCacheDir());
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendMessage(mHandler.obtainMessage(HANDLER_WHAT_OKHTTP_CACHE_RESPONSE, HttpUtil.testCache("http://publicobject.com/helloworld.txt")));
                }
            });
        }else if (v == mOkHttpGsonReponseBtn) {
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendMessage(mHandler.obtainMessage(HANDLER_WHAT_OKHTTP_GSON_RESPONSE, HttpUtil.getGsonData("https://api.github.com/gists/c2a7c39532239ff261be")));
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
