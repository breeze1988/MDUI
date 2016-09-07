package com.breeze.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.breeze.myapplication.data.NetworkService;
import com.breeze.myapplication.data.subscribers.ProgressSubscriber;
import com.breeze.myapplication.data.subscribers.SubscriberOnNextListener;
import com.breeze.myapplication.model.LoginInfoResponse;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.click_me_BN)
    Button mClickMeBN;
    @Bind(R.id.result_TV)
    TextView mResultTV;
    @Bind(R.id.login_result_layout)
    LinearLayout mLoginResultLayout;

    private SubscriberOnNextListener getLoginOnNext;

    private Realm realm;
    private RealmConfiguration realmConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // Create the Realm configuration
        realmConfig = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        // Open the Realm for the UI thread.
        realm = Realm.getInstance(realmConfig);
        getLoginOnNext = new SubscriberOnNextListener<LoginInfoResponse>() {
            @Override
            public void onNext(LoginInfoResponse info) {
                try {
                    if (null != info) {
                        mResultTV.setText(info.getUserInfo().getUserName());
                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(info);
                        realm.commitTransaction();

                        LoginInfoResponse loginInfoResponse = realm.where(LoginInfoResponse.class).findFirst();
                        Log.e("userName", loginInfoResponse.getUserInfo().getUserName());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @OnClick(R.id.click_me_BN)
    public void onClick() {
        getMovie();
    }

    private void getMovie() {
        NetworkService.getInstance().login(new ProgressSubscriber(getLoginOnNext, MainActivity.this), "17711072855", "wxh123456");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
