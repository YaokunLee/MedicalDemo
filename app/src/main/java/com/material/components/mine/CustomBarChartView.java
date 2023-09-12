package com.material.components.mine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;

public class CustomBarChartView extends View {

    private ArrayList<Float> scores;
    private Paint paint;
    private int maxHeightInDp = 60;
    private int spacingInPx = 2; // 间隔距离
    private int columnWidthInPx; // 柱的宽度，基于视图宽度和分数长度计算

    public CustomBarChartView(Context context) {
        super(context);
        init();
    }

    public CustomBarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomBarChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.parseColor("#03DAC5"));
        spacingInPx = (int) (spacingInPx * getResources().getDisplayMetrics().density);
    }

    public void setScores(ArrayList<Float> scores) {
        this.scores = scores;

        invalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int heightInPx = MeasureSpec.getSize(heightMeasureSpec);
        maxHeightInDp = (int) (heightInPx / getResources().getDisplayMetrics().density);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (scores == null || scores.size() == 0) {
            return;
        }

        float maxScore = getMax(scores);
        float dpToPxRatio = getResources().getDisplayMetrics().density;
        float maxBarHeight = maxHeightInDp * dpToPxRatio;

        float cornerRadius = 2 * dpToPxRatio; // 设置为2dp，可以根据需要进行调整

        float barWidth = getWidth() / (scores.size() + 1);
        float spacing = 3 * dpToPxRatio; // 3dp的间距

        for (int i = 0; i < scores.size(); i++) {
            float relativeHeight = (scores.get(i) / maxScore) * maxBarHeight;
            float left = i * (barWidth + spacing);
            float top = getHeight() - relativeHeight;
            float right = left + barWidth;
            float bottom = getHeight();
            canvas.drawRoundRect(left, top, right, bottom, cornerRadius, cornerRadius, paint);
        }
    }


    private float getMax(ArrayList<Float> list) {
        float max = list.get(0);
        for (float v : list) {
            if (v > max) {
                max = v;
            }
        }
        return max;
    }
}
