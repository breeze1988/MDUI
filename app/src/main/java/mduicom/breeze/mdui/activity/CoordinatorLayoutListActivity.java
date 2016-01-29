package mduicom.breeze.mdui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import mduicom.breeze.mdui.R;

public class CoordinatorLayoutListActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.main_coordinator_first_textview)
    TextView mainCoordinatorFirstTextview;
    @Bind(R.id.main_coordinator_textview)
    TextView mainCoordinatorTextview;
    @Bind(R.id.main_ioexample_textview)
    TextView mainIoexampleTextview;
    @Bind(R.id.main_materialup_textview)
    TextView mainMaterialupTextview;
    @Bind(R.id.main_space_textview)
    TextView mainSpaceTextview;
    @Bind(R.id.main_swipebehavior_textview)
    TextView mainSwipebehaviorTextview;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout_list);
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

        mainCoordinatorFirstTextview.setOnClickListener(this);
        mainCoordinatorTextview.setOnClickListener(this);
        mainIoexampleTextview.setOnClickListener(this);
        mainMaterialupTextview.setOnClickListener(this);
        mainSpaceTextview.setOnClickListener(this);
        mainSwipebehaviorTextview.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v == mainCoordinatorFirstTextview){
            startActivity(new Intent(this,CoordinatorLayoutDemoActivity.class));
        }else if( v == mainCoordinatorTextview){
            startActivity(new Intent(this,SimpleCoordinatorActivity.class));
        }else if( v == mainIoexampleTextview){
            startActivity(new Intent(this,IOActivityExample.class));
        }else if( v == mainMaterialupTextview ){

        }else if( v == mainSpaceTextview){
            startActivity(new Intent(this,FlexibleSpaceExampleActivity.class));
        }
    }
}
