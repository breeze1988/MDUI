package mduicom.breeze.mdui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import mduicom.breeze.mdui.R;

public class IOActivityExample extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ioactivity_example);
        Toolbar toolbar = (Toolbar) findViewById(R.id.ioexample_toolbar);
        setSupportActionBar(toolbar);

       toolbar.setNavigationOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
            onBackPressed();
        }
    });
    }

}
