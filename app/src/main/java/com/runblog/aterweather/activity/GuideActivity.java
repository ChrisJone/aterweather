package com.runblog.aterweather.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.runblog.aterweather.R;
import com.runblog.aterweather.activity.base.BaseActivity;
import com.runblog.aterweather.activity.transformer.DepthPageTransformer;

import java.util.ArrayList;

public class GuideActivity extends BaseActivity {
    private static final String TAG = GuideActivity.class.getSimpleName();

    private ViewPager mViewPage;
    private ArrayList<ImageView> mImageViewList;
    private int[] mImageIds = new int[]{R.drawable.start_1,R.drawable.start_2,R.drawable.start_3};
    private LinearLayout pointContainer;
    private ImageView pointCurrent;
    private int mPaintDis;
    private Button startBtn;
    private int currentPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

    }

    @Override
    public int getLayoutView() {
        return R.layout.guide_layout;
    }

    @Override
    public void initData() {
        mImageViewList = new ArrayList<>();
        for(int i=0;i<mImageIds.length;i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(mImageIds[i]);
            mImageViewList.add(imageView);

            ImageView pointView = new ImageView(this);
            pointView.setImageResource(R.drawable.shape_point_one);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

            if(i > 0){
                params.leftMargin = 50;
            }
            pointView.setLayoutParams(params);
            pointContainer.addView(pointView);
        }
    }

    @Override
    public void initView() {
        mViewPage = (ViewPager) findViewById(R.id.rc_guide);
        startBtn = (Button) findViewById(R.id.start_btn);
        pointCurrent = (ImageView) findViewById(R.id.point_current);
        pointContainer = (LinearLayout) findViewById(R.id.point_container);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(act,MainActivity.class));

                //启动之后写入共享
                sharedPreferencesUtil.put(IS_FIRST_STARTUP,false);
                finish();
            }
        });

        initData();

        GuideAdapter adapter = new GuideAdapter();
        mViewPage.setPageTransformer(true,new DepthPageTransformer());
        mViewPage.setAdapter(adapter);


        pointCurrent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                pointContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mPaintDis = pointContainer.getChildAt(1).getLeft() - pointContainer.getChildAt(0).getLeft();
                Log.d(TAG, "onGlobalLayout: "+mPaintDis);
            }
        });
        mViewPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //当滑到第二个Pager的时候，positionOffset百分比会变成0，position会变成1，所以后面要加上position*mPaintDis
                int letfMargin = (int) (mPaintDis * positionOffset) + position * mPaintDis;
                //在父布局控件中设置他的leftMargin边距
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) pointCurrent.getLayoutParams();
                params.leftMargin = letfMargin;
                pointCurrent.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: "+position);
                currentPosition = position;
                if (position == mImageViewList.size() - 1) {
                    startBtn.setVisibility(View.VISIBLE);
                } else {
                    startBtn.setVisibility(View.GONE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                Log.d(TAG, "onPageScrollStateChanged: "+i);
            }
        });
    }

    class GuideAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mImageViewList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView view = mImageViewList.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentPosition == mImageViewList.size() - 1) {
            startBtn.setVisibility(View.VISIBLE);
        } else {
            startBtn.setVisibility(View.GONE);
        }
    }
}
