package com.material.components.activity.card;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.material.components.R;
import com.material.components.utils.Tools;
import com.material.components.view.ProgressView;

import java.util.ArrayList;

public class CardWizardOverlap extends AppCompatActivity {

    private static final int MAX_STEP = 4;

    private ViewPager viewPager;
    private Button btnNext;
    private MyViewPagerAdapter myViewPagerAdapter;
    private String about_title_array[] = {
            "On a scale of 1-5, with 5 being strongly agree and 1 being strongly disagree:",
            "On a scale of 1-5, with 5 being strongly agree and 1 being strongly disagree:",
            "On a scale of 1-5, with 5 being strongly agree and 1 being strongly disagree:",
            "On a scale of 1-5, with 5 being strongly agree and 1 being strongly disagree:"
    };
    private String about_description_array[] = {
            "I didn't sleep well.",
            "I don't feel like eating anything.",
            "I feel physically fatigued and weak, with no energy or strength.",
            "I often feel like crying.",
    };

    static String[] B = {"1 - strongly disagree", "2", "3", "4", "5 - strongly agree"};

    // 假设你想要A数组包含3个B数组的元素
    static String[][] A = {B, B, B, B};


    private void initStatusBar() {
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_wizard_overlap);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        btnNext = (Button) findViewById(R.id.btn_next);

        // adding bottom dots
        bottomProgressDots(0);

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        viewPager.setClipToPadding(false);
        viewPager.setPadding(0, 0, 0, 0);
//        viewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin_overlap));
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (viewPager.getCurrentItem() == about_title_array.length - 1) {
                    btnNext.setText("Report");
                } else {
                    btnNext.setText("Next");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = viewPager.getCurrentItem() + 1;
                if (current < MAX_STEP) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    finish();
                }
            }
        });

        initStatusBar();

    }


    private void bottomProgressDots(int current_index) {
        ProgressBar progressView = findViewById(R.id.progress_view);
        progressView.setMax(MAX_STEP);
        progressView.setProgress(current_index + 1);
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {
            bottomProgressDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        private int dpToPx(int dp) {
            float density = getResources().getDisplayMetrics().density;
            return (int) (dp * density + 0.5f);
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.item_card_wizard, container, false);
            ((TextView) view.findViewById(R.id.instruction)).setText(about_title_array[position]);
            ((TextView) view.findViewById(R.id.question)).setText(about_description_array[position]);

            LinearLayoutCompat choiceContainer = view.findViewById(R.id.choices_container);
            for (int i = 0; i < A[position].length; i++) {
                AppCompatButton btn = new AppCompatButton(CardWizardOverlap.this);

                // 设置按钮属性
                btn.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                        LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                        LinearLayoutCompat.LayoutParams.WRAP_CONTENT
                ));
                btn.setGravity(Gravity.CENTER);
                btn.setTextColor(ContextCompat.getColor(CardWizardOverlap.this, R.color.grey_60));
                btn.setAllCaps(false);
                btn.setText(A[position][i]);

                // 3. 将每个按钮添加到LinearLayoutCompat中
                choiceContainer.addView(btn);
            }

//
//            for (int i = 0; i < A[position].length; i++) {
//
//                LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(
//                        LinearLayout.LayoutParams.MATCH_PARENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT
//                );
//
//                int marginInPx = dpToPx(10);
//                params.setMargins(0, i == 0 ? 0 : marginInPx, 0, marginInPx); // 如果是第一个按钮则上边距为0，否则为10dp
//
//                Button button = new Button(CardWizardOverlap.this);
//                button.setText(A[position][i]);
//                button.setTextColor(getResources().getColor(R.color.button_test_color));
//                button.setBackgroundColor(getResources().getColor(R.color.button_test_background_color));
////                button.setBackgroundColor(getResources().getColor(R.color.button_test_background_color));
//                choiceContainer.addView(button, params);
//            }

            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return about_title_array.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}