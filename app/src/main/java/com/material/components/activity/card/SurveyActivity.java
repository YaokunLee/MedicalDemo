package com.material.components.activity.card;

import android.content.Context;
import android.os.Bundle;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.material.components.R;
import com.material.components.mine.CustomViewPager;
import com.material.components.mine.healthdata.HealthDataManager;
import com.material.components.mine.healthdata.SelfAssessmentHealthData;
import com.material.components.mine.healthdata.SurveyQuestionData;
import com.material.components.mine.healthdata.SurveyQuestionProvider;
import com.material.components.utils.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SurveyActivity extends AppCompatActivity {

    private static final int MAX_STEP = 16;

    private CustomViewPager viewPager;
    private Button btnNext;
    private MyViewPagerAdapter myViewPagerAdapter;

    private String hint =
            "On a scale of 1-5, with 5 being strongly agree and 1 being strongly disagree:";

    private ArrayList<SurveyQuestionData> surveyQuestionDataArrayList = new ArrayList<>();


    String[] rate = {"1 - strongly disagree", "2", "3", "4", "5 - strongly agree"};
    private final Map<String, Integer> rateMap = getRatingMap();

    public Map<String, Integer> getRatingMap() {
        Map<String, Integer> ratingMap = new HashMap<>();

        for (String rating : rate) {
            if (rating.contains("1")) {
                ratingMap.put(rating, 1);
            } else if (rating.contains("2")) {
                ratingMap.put(rating, 2);
            } else if (rating.contains("3")) {
                ratingMap.put(rating, 3);
            } else if (rating.contains("4")) {
                ratingMap.put(rating, 4);
            } else if (rating.contains("5")) {
                ratingMap.put(rating, 5);
            }
        }

        return ratingMap;
    }

    private void initStatusBar() {
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_wizard_overlap);

        setFinalQuestions();

        viewPager = (CustomViewPager) findViewById(R.id.view_pager);
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


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = viewPager.getCurrentItem() + 1;
                if (current < MAX_STEP) {
                    // move to next screen
                    if (surveyQuestionDataArrayList.get(viewPager.getCurrentItem()).isAnswered()) {
                        viewPager.setCurrentItem(current);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Please answer the question", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    HealthDataManager.getInstance().setSelfAssessmentHealthData(getFinalScore());
                    finish();
                }
            }
        });

        initStatusBar();
    }


    private SelfAssessmentHealthData getFinalScore() {
        return HealthDataManager.calculateAverageScores(surveyQuestionDataArrayList);
    }

    private void setFinalQuestions() {
        surveyQuestionDataArrayList = SurveyQuestionProvider.getSampleQuestions();
    }


    private void bottomProgressDots(int current_index) {
        ProgressBar progressView = findViewById(R.id.progress_view);
        progressView.setMax(MAX_STEP);
        progressView.setProgress(current_index + 1);
    }

    private int maxIndex = -1;


    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {
            bottomProgressDots(position);

            if (maxIndex > position) {
                viewPager.setCanSwipeToLeft(true);
            } else if (maxIndex == position && isCurrentAnswered()) {
                viewPager.setCanSwipeToLeft(true);
            } else if (maxIndex == position && !isCurrentAnswered()) {
                viewPager.setCanSwipeToLeft(false);
            } else {
                viewPager.setCanSwipeToLeft(false);
            }

            maxIndex = Math.max(maxIndex, position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            if (viewPager.getCurrentItem() == surveyQuestionDataArrayList.size() - 1) {
                btnNext.setText("Report");
            } else {
                btnNext.setText("Next");
            }

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };


    private boolean isCurrentAnswered() {
        return surveyQuestionDataArrayList.get(viewPager.getCurrentItem()).isAnswered();
    }


    private void setCurrentAnswer(int score) {
        surveyQuestionDataArrayList.get(viewPager.getCurrentItem()).setScore(score);
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.item_card_wizard, container, false);
            ((TextView) view.findViewById(R.id.instruction)).setText(hint);
            ((TextView) view.findViewById(R.id.question)).setText(surveyQuestionDataArrayList.get(position).getQuestion());

            Button button1 = (Button) view.findViewById(R.id.btn_choice1);
            Button button2 = (Button) view.findViewById(R.id.btn_choice2);
            Button button3 = (Button) view.findViewById(R.id.btn_choice3);

            Button button4 = (Button) view.findViewById(R.id.btn_choice4);
            Button button5 = (Button) view.findViewById(R.id.btn_choice5);

            if (surveyQuestionDataArrayList.get(position).isAnswered()) {
                int score = surveyQuestionDataArrayList.get(position).getScore();
                switch (score) {
                    case 1:
                        button1.setBackgroundResource(R.drawable.option_button_bg_choosed);
                        break;
                    case 2:
                        button2.setBackgroundResource(R.drawable.option_button_bg_choosed);
                        break;
                    case 3:
                        button3.setBackgroundResource(R.drawable.option_button_bg_choosed);
                        break;
                    case 4:
                        button4.setBackgroundResource(R.drawable.option_button_bg_choosed);
                        break;
                    case 5:
                        button5.setBackgroundResource(R.drawable.option_button_bg_choosed);
                        break;
                }
            }

            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setCurrentAnswer(1);
                    viewPager.setCanSwipeToLeft(true);
                    button1.setBackgroundResource(R.drawable.option_button_bg_choosed);
                    button2.setBackgroundResource(R.drawable.option_button_bg);
                    button3.setBackgroundResource(R.drawable.option_button_bg);
                    button4.setBackgroundResource(R.drawable.option_button_bg);
                    button5.setBackgroundResource(R.drawable.option_button_bg);
                }
            });


            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setCurrentAnswer(2);
                    viewPager.setCanSwipeToLeft(true);
                    button2.setBackgroundResource(R.drawable.option_button_bg_choosed);
                    button1.setBackgroundResource(R.drawable.option_button_bg);
                    button3.setBackgroundResource(R.drawable.option_button_bg);
                    button4.setBackgroundResource(R.drawable.option_button_bg);
                    button5.setBackgroundResource(R.drawable.option_button_bg);
                }
            });


            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setCurrentAnswer(3);
                    viewPager.setCanSwipeToLeft(true);
                    button3.setBackgroundResource(R.drawable.option_button_bg_choosed);
                    button2.setBackgroundResource(R.drawable.option_button_bg);
                    button1.setBackgroundResource(R.drawable.option_button_bg);
                    button4.setBackgroundResource(R.drawable.option_button_bg);
                    button5.setBackgroundResource(R.drawable.option_button_bg);
                }
            });


            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setCurrentAnswer(4);
                    viewPager.setCanSwipeToLeft(true);
                    button4.setBackgroundResource(R.drawable.option_button_bg_choosed);
                    button2.setBackgroundResource(R.drawable.option_button_bg);
                    button3.setBackgroundResource(R.drawable.option_button_bg);
                    button1.setBackgroundResource(R.drawable.option_button_bg);
                    button5.setBackgroundResource(R.drawable.option_button_bg);
                }
            });


            button5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setCurrentAnswer(5);
                    viewPager.setCanSwipeToLeft(true);
                    button5.setBackgroundResource(R.drawable.option_button_bg_choosed);
                    button2.setBackgroundResource(R.drawable.option_button_bg);
                    button3.setBackgroundResource(R.drawable.option_button_bg);
                    button4.setBackgroundResource(R.drawable.option_button_bg);
                    button1.setBackgroundResource(R.drawable.option_button_bg);
                }
            });


            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return surveyQuestionDataArrayList.size();
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