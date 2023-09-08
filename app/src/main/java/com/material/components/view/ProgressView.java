package com.material.components.view ;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.material.components.R;

public class ProgressView extends LinearLayout {

    private View yellowSection;
    private View greySection;

    public ProgressView(Context context) {
        super(context);
        init(context);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_progress_view, this, true);
        yellowSection = findViewById(R.id.yellowSection);
        greySection = findViewById(R.id.greySection);
    }

    public void setProgressRatio(int a, int b) {
        float ratio = ((float) a / (float) b);
        LinearLayout.LayoutParams yellowParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, ratio);
        LinearLayout.LayoutParams greyParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1 - ratio);
        yellowSection.setLayoutParams(yellowParams);
        greySection.setLayoutParams(greyParams);
    }
}
