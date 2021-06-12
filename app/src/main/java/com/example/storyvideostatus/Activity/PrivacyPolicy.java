package com.example.storyvideostatus.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.storyvideostatus.R;

public class PrivacyPolicy extends AppCompatActivity {

    ImageView imgBack;
    WebView ivWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        imgBack = findViewById(R.id.imgBack);
        ivWebView = findViewById(R.id.ivWebView);


        ivWebView.setWebViewClient(new MyWebViewClient());
        openURL();

        imgBack.setOnClickListener(v -> onBackPressed());

    }

    private void openURL() {
        ivWebView.loadUrl(getResources().getString(R.string.privacy_policy));
        ivWebView.requestFocus();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}