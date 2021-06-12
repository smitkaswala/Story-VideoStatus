package com.example.storyvideostatus.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.storyvideostatus.R;
import com.squareup.picasso.BuildConfig;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class AboutUs extends AppCompatActivity {

    ImageView imgLogo;
    TextView tvAppName;
    TextView tvVersion;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        imgLogo = findViewById(R.id.imgLogo);
        tvAppName = findViewById(R.id.tvAppName);
        tvVersion = findViewById(R.id.tvVersion);
        imgBack = findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Glide
                .with(AboutUs.this)
                .load(R.drawable.second_src)
                .transition(withCrossFade())
                .transition(new DrawableTransitionOptions().crossFade(500))
                .into(imgLogo);

        tvAppName.setText(getResources().getString(R.string.app_name));
        tvVersion.setText("Version: " + BuildConfig.VERSION_NAME);

    }
}