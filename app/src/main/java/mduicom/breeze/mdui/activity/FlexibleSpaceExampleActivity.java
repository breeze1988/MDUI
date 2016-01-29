package mduicom.breeze.mdui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import mduicom.breeze.mdui.R;

public class FlexibleSpaceExampleActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    @Bind(R.id.flexible_example_toolbar)
    Toolbar toolbar;
    @Bind(R.id.flexible_example_collapsing)
    CollapsingToolbarLayout collapsing;
    @Bind(R.id.flexible_example_appbar)
    AppBarLayout appbar;
    @Bind(R.id.flexible_example_cardview)
    CardView cardview;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    private static final int PECENTAGE_TO_SHOW_IMAGE = 20;
    private int mMaxScrollSize;
    private boolean mIsImageHiddden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexible_space_example);
        ButterKnife.bind(this);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        appbar.addOnOffsetChangedListener(this);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if(mMaxScrollSize == 0){
            mMaxScrollSize = appBarLayout.getTotalScrollRange();
        }

        int currentScrollPercentage = (Math.abs(verticalOffset)) * 100 /mMaxScrollSize;

        if(currentScrollPercentage >= PECENTAGE_TO_SHOW_IMAGE){
            if(!mIsImageHiddden){
                mIsImageHiddden = true;
                ViewCompat.animate(fab).scaleY(0).scaleX(0).start();
            }
        }

        if(currentScrollPercentage < PECENTAGE_TO_SHOW_IMAGE){
            if(mIsImageHiddden){
                mIsImageHiddden = false;
                ViewCompat.animate(fab).scaleY(1).scaleX(1).start();
            }
        }

    }
}
