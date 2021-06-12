package com.example.storyvideostatus.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.storyvideostatus.Adapter.ViewPagerAdapter;
import com.example.storyvideostatus.R;
import com.example.storyvideostatus.Utils.Constant;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class IntroActivity extends BaseActivity implements View.OnClickListener{

    ImageView settingBar,navigation_exit;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    Dialog dial;
    TextView tvTitle;
    LinearLayout story_status,landscape_status,social_status,about_us,privacy_policy,rate_us;
    TrendingActivity trendingActivity;

    @Override
    public void permissionGranted() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startApp();

    }

    private void startApp(){

        settingBar = findViewById(R.id.settingbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigation_exit =findViewById(R.id.navigation_exit);
        navigationView = findViewById(R.id.navigationView);
        about_us = findViewById(R.id.about_us);
        privacy_policy = findViewById(R.id.privacy_policy);
        rate_us = findViewById(R.id.rate_us);
        tvTitle = findViewById(R.id.tvTitle);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.view_pager);
        story_status = findViewById(R.id.story_status);
        landscape_status = findViewById(R.id.landscape_status);
        social_status = findViewById(R.id.social_status);

        story_status.setOnClickListener(this);
        landscape_status.setOnClickListener(this);
        social_status.setOnClickListener(this);



        settingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigation_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationView.clearFocus();
            }
        });

        rate_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Uri uri = Uri.parse("market://details?id=" + getPackageName() + "");
                    Intent goMarket = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(goMarket);
                } catch (ActivityNotFoundException e) {
                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName() + "");
                    Intent goMarket = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(goMarket);
                }
            }
        });

        setUpViewPager();

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.home:
                                viewPager.setCurrentItem(0);
                                break;

                            case R.id.trending:
                                viewPager.setCurrentItem(1);
                                break;

                            case R.id.category:
                                viewPager.setCurrentItem(2);
                                break;
                        }
                        return true;
                    }
                });

        about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(IntroActivity.this, AboutUs.class);
                startActivity(intent);
            }
        });

        privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, PrivacyPolicy.class);
                startActivity(intent);
            }
        });

        dial = new Dialog(IntroActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dial.requestWindowFeature(1);
        dial.setContentView(R.layout.dialog_exit);
        dial.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dial.setCanceledOnTouchOutside(true);

        dial.findViewById(R.id.delete_yes).setOnClickListener(view -> {
            dial.dismiss();
            finishAffinity();
        });

        dial.findViewById(R.id.rate_us_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Uri uri = Uri.parse("market://details?id=" + getPackageName() + "");
                    Intent goMarket = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(goMarket);
                } catch (ActivityNotFoundException e) {
                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName() + "");
                    Intent goMarket = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(goMarket);
                }
            }
        });

        dial.findViewById(R.id.delete_no).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dial.dismiss();
            }
        });
    }

    private void checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isConnected()) {
            dial.dismiss();
            startApp();

        } else if (mobile.isConnected()) {
//            apiVideoRequest();
            dial.dismiss();
            startApp();

        } else {


            dial.setContentView(R.layout.alert_dailog);

            dial.setCanceledOnTouchOutside(false);

            dial.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT);

            dial.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            dial.getWindow().getAttributes().windowAnimations =
                    android.R.style.Animation_Dialog;

            Button bt_try = dial.findViewById(R.id.bt_try);

            bt_try.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    checkConnection();
                }
            });

            dial.show();

        }
    }

    @Override
    public void onBackPressed() {
        dial.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void setUpViewPager(){

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.setCurrentItem(Constant.pagerPosition);
        bottomNavigationView.setSelectedItemId(Constant.pagerPosition);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvTitle.setText(viewPagerAdapter.getPageTitle(position));
                bottomNavigationView.setSelectedItemId(position);
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.trending).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.category).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.story_status:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                viewPager.setCurrentItem(0);
                break;

            case R.id.landscape_status:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                viewPager.setCurrentItem(1);
                break;

            case R.id.social_status:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                viewPager.setCurrentItem(2);
                break;
        }
    }

}