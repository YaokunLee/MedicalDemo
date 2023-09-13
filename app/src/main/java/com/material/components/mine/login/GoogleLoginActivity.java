package com.material.components.mine.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.google.android.gms.auth.api.identity.SignInCredential;
import com.material.components.R;
import com.material.components.activity.menu.DataVisualizationActivity;
import com.material.components.utils.Tools;

public class GoogleLoginActivity extends AppCompatActivity {

    private static final String TAG = "GoogleLoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_google);

        initLoginButton();
        initStatusBar();
        setWelcomeText();
    }


    private void initLoginButton() {
        loginButton = findViewById(R.id.google_login_btn_layout);
        GoogleSignInManager.getInstance().initLoginButton(loginButton, this, new GoogleSignInManager.OnLoginResult() {
            @Override
            public void onSuccess(SignInCredential credential) {
                startActivity(new Intent(GoogleLoginActivity.this, DataVisualizationActivity.class));
                finish();
            }

            @Override
            public void onFail() {

            }
        });
    }

    private void setWelcomeText() {
        TextView textView = findViewById(R.id.title_view2);
        String text = "Together for brighter days.";
        SpannableString spannable = new SpannableString(text);
        // 设置"brighter"这部分文本的颜色
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#0568f8"));
        int start = text.indexOf("brighter");
        int end = start + "brighter".length();
        spannable.setSpan(colorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannable);

    }


    LinearLayoutCompat loginButton;

    private void initStatusBar() {
        Tools.setSystemBarColor(this, R.color.login_bg);
        Tools.setSystemBarLight(this);
    }



}
