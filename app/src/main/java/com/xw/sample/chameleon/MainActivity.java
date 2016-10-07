package com.xw.sample.chameleon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.xw.repo.chameleon.Chameleon;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int[] IMAGES = {R.mipmap.landscape_1, R.mipmap.landscape_2,
            R.mipmap.landscape_3, R.mipmap.landscape_4};

    private FrameLayout mFrameLayout;

    private Chameleon mChameleon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFrameLayout = (FrameLayout) findViewById(R.id.layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        findViewById(R.id.button0).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);

        mChameleon = new Chameleon()
                .fromColor(((ColorDrawable) mFrameLayout.getBackground()).getColor())
                .toColor(Color.parseColor("#f9bb05"))
                .duration(2000)
                .interpolator(new AccelerateInterpolator())
                .setOnColorChangedListener(new Chameleon.OnColorChangedListener() {
                    @Override
                    public void onColorChanged(int color, boolean isChanging) {
                        mFrameLayout.setBackgroundColor(color);
                    }
                });
        mChameleon.startChangingColor();

        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

            @Override
            public void onPageSelected(int position) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), IMAGES[position]);
                if (bitmap != null) {
                    Palette palette = Palette.from(bitmap).generate();

                    if (palette.getLightVibrantSwatch() != null) {
                        int rgb = palette.getLightVibrantSwatch().getRgb();

                        mChameleon.toColor(rgb);
                        mChameleon.startChangingColor();
                    }

                    bitmap.recycle();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button0:
                mChameleon.toColor(ContextCompat.getColor(this, R.color.blue));
                break;
            case R.id.button1:
                mChameleon.toColor(ContextCompat.getColor(this, R.color.red));
                break;
            case R.id.button2:
                mChameleon.toColor(ContextCompat.getColor(this, R.color.yellow));
                break;
            case R.id.button3:
                mChameleon.toColor(ContextCompat.getColor(this, R.color.colorPrimary));
                break;
        }

        mChameleon.startChangingColor();
    }

    private class MyPagerAdapter extends PagerAdapter {

        private ImageView[] mImageViews = new ImageView[4];

        MyPagerAdapter() {
            for (int i = 0; i < mImageViews.length; i++) {
                mImageViews[i] = new ImageView(MainActivity.this);
                mImageViews[i].setImageResource(IMAGES[i]);
                mImageViews[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        }

        @Override
        public int getCount() {
            return IMAGES.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViews[position]);

            return mImageViews[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImageViews[position]);
        }
    }
}
