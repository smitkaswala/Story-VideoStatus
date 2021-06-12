package com.example.storyvideostatus.CategoryActivitys;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.example.storyvideostatus.Activity.BaseActivity;
import com.example.storyvideostatus.CategoryAdapter.LoveAdapter;
import com.example.storyvideostatus.Model.ResponseCategory;
import com.example.storyvideostatus.R;
import com.example.storyvideostatus.RetrofitClient.RetrofitClient;
import com.example.storyvideostatus.Utils.Constant;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ch.halcyon.squareprogressbar.SquareProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GreetingActivity extends BaseActivity implements LoveAdapter.DownloadClickListener {

    RecyclerView recyclerView;
    RelativeLayout rlProgress;
    SquareProgressBar sProgressBar;
    TextView tvTile1;
    private final List<ResponseCategory> responseNewList = new ArrayList<>();
    private List<ResponseCategory> responseNewList1 = new ArrayList<>();
    LoveAdapter innerNewAdapter;
    FirebaseAnalytics mFirebaseAnalytics;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    private boolean loading = true;
    ResponseCategory responseNew = new ResponseCategory();
    private int page = 0;
    MediaScannerConnection msConn;

    @Override
    public void permissionGranted() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_love);


        recyclerView = findViewById(R.id.rvVideos);
        rlProgress = findViewById(R.id.rlProgress);
        sProgressBar =findViewById(R.id.sProgressBar);
        tvTile1 = findViewById(R.id.tvTile1);

        setAdapter();
    }
    private void setAdapter() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GreetingActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        innerNewAdapter = new LoveAdapter(responseNewList, GreetingActivity.this);
        recyclerView.setAdapter(innerNewAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        recyclerView.setOnFlingListener(null);
        snapHelper.attachToRecyclerView(recyclerView);
        innerNewAdapter.downloadClickListener(this);
        innerNewAdapter.clearAll();
        LoveVideos(page);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy > 0) { //check for scroll down
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            page++;
                            LoveVideos(page);
                            loading = true;
                        }
                    }
                }

            }
        });

    }
    private void LoveVideos(int page){
        Call<List<ResponseCategory>> call = RetrofitClient.getInstance().getModel().getCategory(
                String.valueOf(page),"13"
        );
        call.enqueue(new Callback<List<ResponseCategory>>() {
            @Override
            public void onResponse(Call<List<ResponseCategory>> call, Response<List<ResponseCategory>> response) {

                List<ResponseCategory> responseNews = response.body();

                innerNewAdapter.addAll(responseNews);

            }

            @Override
            public void onFailure(Call<List<ResponseCategory>> call, Throwable t) {

            }
        });
    }
    public void scanPhoto(final String imageFileName) {
        msConn = new MediaScannerConnection(GreetingActivity.this, new MediaScannerConnection.MediaScannerConnectionClient() {
            public void onMediaScannerConnected() {
                msConn.scanFile(imageFileName, null);
                Log.i("msClient obj", "connection established");
            }

            public void onScanCompleted(String path, Uri uri) {
                msConn.disconnect();
                Log.i("msClient obj", "scan completed");
            }
        });
        this.msConn.connect();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(HindiActivity.this, CategoryFragment.class);
//        startActivity(intent);
        finish();
    }

    @Override
    public void onDownloadClick(int position, String imageUrl, String title, String url) {
        new LongOperation(imageUrl).execute();
        new downloadTask(responseNewList.get(position).title, responseNewList.get(position).video, position).execute();
    }

    @Override
    public void backPress() {
        onBackPressed();
    }


    private final class LongOperation extends AsyncTask<Void, Void, Bitmap> {

        String downloadUrl;

        public LongOperation(String url) {
            this.downloadUrl = url;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(downloadUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                sProgressBar.setImageBitmap(result);
            }
        }
    }

    private final class downloadTask extends AsyncTask<Void, Void, String> {

        private final int TIMEOUT_CONNECTION = 5000;//5sec
        private final int TIMEOUT_SOCKET = 30000;//30sec

        String fileName;
        String downloadUrl;
        int position = 0;

        public downloadTask(String fileName, String downloadUrl, int position) {
            this.fileName = fileName;
            this.downloadUrl = downloadUrl;
            this.position = position;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    sProgressBar.setRoundedCorners(true);
                    Constant.expand(rlProgress);
                }
            });
        }

        @Override
        protected String doInBackground(Void... params) {
            AndroidNetworking.download(downloadUrl, Constant.FOLDERPATH, fileName + ".mp4")
                    .setTag("downloadTest")
                    .setPriority(Priority.IMMEDIATE)
                    .build()
                    .setAnalyticsListener(new AnalyticsListener() {
                        @Override
                        public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                            Log.e("LLLLL_Progress: ", (timeTakenInMillis / 1000) + " Received: " + (bytesReceived / 1000) + "  Sent: " + (bytesSent / 1000));
                        }
                    })
                    .setDownloadProgressListener(new DownloadProgressListener() {
                        @Override
                        public void onProgress(long bytesDownloaded, long totalBytes) {
                            // do anything with progress
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    long progress = (bytesDownloaded * 100) / totalBytes;
                                    sProgressBar.setProgress(progress);
                                    recyclerView.setOnTouchListener(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            return true;
                                        }
                                    });
                                    tvTile1.setText(responseNewList.get(position).title);
                                    Log.e("LLLLL_Progress: ", ((bytesDownloaded * 100) / totalBytes) + " Total: " + (totalBytes / 1000));
                                }
                            });

                        }
                    })
                    .startDownload(new DownloadListener() {
                        @Override
                        public void onDownloadComplete() {
                            // do anything after completion
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("LLLLL_Progress: ", "Download Completed.");

                                    File file = new File(Constant.FOLDERPATH, fileName + ".mp4");
                                    scanPhoto(file.toString());
                                    tvTile1.setText("Download Completed.");
                                    recyclerView.setOnTouchListener(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            return false;
                                        }
                                    });
                                    innerNewAdapter.notifyItemChanged(position);
                                    final Handler handler = new Handler(Looper.getMainLooper());
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Constant.collapse(rlProgress);
                                        }
                                    }, 2000);

                                }
                            });
                        }

                        @Override
                        public void onError(ANError error) {
                            // handle error
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("LLLLL_Progress_Err: ", error.getErrorDetail());
                                }
                            });
                        }
                    });
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }
}