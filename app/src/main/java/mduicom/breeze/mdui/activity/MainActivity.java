package mduicom.breeze.mdui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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
import android.widget.ImageView;

import butterknife.ButterKnife;
import mduicom.breeze.mdui.R;
import mduicom.breeze.mdui.fragment.OkHttpTestFragment;
import mduicom.breeze.mdui.fragment.RetrofitFragment;
import mduicom.breeze.mdui.fragment.SwipToRefrshFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private FragmentTransaction ft = null;

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

        ImageView imageview = (ImageView)findViewById(R.id.main_imageview);

        Bitmap b = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawText("BREEZE DEMO", 20, 20, paint);

        BitmapDrawable bitmapDrawable = new BitmapDrawable(b);
        imageview.setBackground(bitmapDrawable);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


//        try {
//            ft = getFragmentManager().beginTransaction();
//            OkHttpTestFragment okHttpTestFragment = new OkHttpTestFragment();
//            ft.add(R.id.main_content,okHttpTestFragment).show(okHttpTestFragment).commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onClick(View v) {

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
//        Fragment fragment = null;
        FragmentManager fragmentManager = getFragmentManager();
        if (id == R.id.nav_camera) {
            // Handle the camera action

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.SwipeRefreshLayout) {
           Fragment fragment = SwipToRefrshFragment.newInstance("","");
           fragmentManager.beginTransaction()
                    .replace(R.id.main_content,fragment)
                    .commit();

        } else if (id == R.id.CoordinatorLayout) {
            startActivity(new Intent(MainActivity.this,CoordinatorLayoutListActivity.class));
        } else if (id == R.id.okHttp){
            Fragment fragment = OkHttpTestFragment.newInstance("","");
            fragmentManager.beginTransaction()
                    .replace(R.id.main_content,fragment)
                    .commit();
        } else if(id == R.id.retrofit){
            Fragment fragment = new RetrofitFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_content,fragment)
                    .commit();
        } else if (id == R.id.glide){
            startActivity(new Intent(MainActivity.this,GlideActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void selectItem(int position,Fragment fragment){
        fragment = new Fragment();
    }



}
