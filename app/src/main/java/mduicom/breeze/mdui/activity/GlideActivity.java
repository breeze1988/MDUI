package mduicom.breeze.mdui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import mduicom.breeze.mdui.R;
import mduicom.breeze.mdui.customview.FreeRockViewPager;
import mduicom.breeze.mdui.customview.PagerSlidingTabStrip;
import mduicom.breeze.mdui.fragment.GlideRecycleviewFragment;
import mduicom.breeze.mdui.fragment.GlideSigleImageFragment;

public class GlideActivity extends AppCompatActivity implements View.OnClickListener,FreeRockViewPager.OnPrePageChangeListener, ViewPager.OnPageChangeListener{

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.sliding_tabs)
    PagerSlidingTabStrip slidingTabs;
    @Bind(R.id.pager)
    FreeRockViewPager pager;


    public static final int TAB_GLIDE_SINGLE_IMAGE = 0;
    public static final int TAB_GLIDE_SINGLE_RECYCLEVIEW = 2;

    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        ButterKnife.bind(this);
//       toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        pager.setAdapter(getPagerAdapter());
        pager.setOffscreenPageLimit(3);
        pager.setCurrentItem(0);
        pager.setBackgroundColor(getResources().getColor(android.R.color.white));

        slidingTabs.setTextSize(16);
        slidingTabs.setTextColorResource(R.color.orange);
        slidingTabs.setSelectTextColorResource(R.color.green);
        slidingTabs.setUnderlineHeight(0);
        slidingTabs.setTabPaddingLeftRight(dip2px(20));
        slidingTabs.setIndicatorHeight(dip2px(4));
        slidingTabs.setAllCaps(false);

        slidingTabs.setOnPageChangeListener(this);
        slidingTabs.setOnPrePageChangeListener(this);

        slidingTabs.setViewPager(pager);

    }

    public  int dip2px(float dp) {
        float mDensity = getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dp * mDensity + 0.5f);
    }

    private PagerAdapter getPagerAdapter() {
        String[] content = {"单个图片","recycle view"};
        mFragments.add(new GlideSigleImageFragment());
        mFragments.add(new GlideRecycleviewFragment());
        return new FragmentAdapter(getSupportFragmentManager(),mFragments,content);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onCanChange(int currentPosition) {
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class FragmentAdapter extends FragmentStatePagerAdapter{

        private List<? extends Fragment> mFragments;

        private String[] mContent;

        @Override
        public CharSequence getPageTitle(int position) {
            return mContent[position % mContent.length];
        }

        public FragmentAdapter(FragmentManager fm,ArrayList<? extends Fragment> mFragments,String[] mContent) {
            this(fm,mFragments);
            this.mContent = mContent;
        }

        public FragmentAdapter(FragmentManager fm, List<? extends Fragment> mFragments) {
            super(fm);
            this.mFragments = mFragments;
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;// 使用notifyDataSetChanged才会生效
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
