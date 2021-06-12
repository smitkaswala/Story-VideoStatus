package com.example.storyvideostatus.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.example.storyvideostatus.Adapter.InnerNewAdapter;
import com.example.storyvideostatus.Adapter.InnerTrendingAdapter;
import com.example.storyvideostatus.Fregment.TrendingFragment;
import com.example.storyvideostatus.Model.ResponseNew;
import com.example.storyvideostatus.Model.ResponseTrending;
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

public class TrendingActivity extends BaseActivity implements InnerTrendingAdapter.DownloadClickListener {

    RecyclerView recyclerView;
    RelativeLayout rlProgress;
    SquareProgressBar sProgressBar;
    TextView tvTile1;
    private final List<ResponseTrending> responseTrending = new ArrayList<>();
    private List<ResponseTrending> responseTrendings1 = new ArrayList<>();
    InnerTrendingAdapter innerTrendingAdapter;
    FirebaseAnalytics mFirebaseAnalytics;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    private boolean loading = true;
    ResponseTrending responseTrendingList = new ResponseTrending();
    private String pageCount = "1";
    MediaScannerConnection msConn;
    private int position;
    private int page;

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
        setContentView(R.layout.activity_trending);

        recyclerView = findViewById(R.id.rvVideos);
        rlProgress = findViewById(R.id.rlProgress);
        sProgressBar =findViewById(R.id.sProgressBar);
        tvTile1 = findViewById(R.id.tvTile1);


        AndroidNetworking.initialize(TrendingActivity.this);
        page = getIntent().getIntExtra("page",0);
        position = getIntent().getIntExtra("position",0);

        setAdapter();

    }
    @Override
    protected void onResume() {
        super.onResume();


    }


    private void setAdapter() {
        //get the bundle
        Bundle b = getIntent().getExtras();
        responseTrendings1 = (ArrayList<ResponseTrending>) b.getSerializable("VideoList");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TrendingActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        innerTrendingAdapter = new InnerTrendingAdapter(responseTrendings1, TrendingActivity.this);
        recyclerView.setAdapter(innerTrendingAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        recyclerView.setOnFlingListener(null);
        snapHelper.attachToRecyclerView(recyclerView);
        innerTrendingAdapter.downloadClickListener(this);
        recyclerView.scrollToPosition(position);


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
                            // Do pagination.. i.e. fetch new data
//                            getStoryStatusVideos(pageCount);
                            TrendingVideos();
                            loading = true;
                        }
                    }
                }
            }
        });
    }


    private void TrendingVideos(){
        Call<List<ResponseTrending>> call = RetrofitClient.getInstance().getModel().getTrending("2");

        call.enqueue(new Callback<List<ResponseTrending>>() {
            @Override
            public void onResponse(Call<List<ResponseTrending>> call, Response<List<ResponseTrending>> response) {
                List<ResponseTrending> responseTrend = response.body();

//                if (pageCount.equals("1")) {
//
//                    responseTrending.add(responseTrendingList);
//                }
//
//                responseTrending.addAll(responseTrend);

                innerTrendingAdapter.addAll(responseTrend);
            }

            @Override
            public void onFailure(Call<List<ResponseTrending>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();

    }

    @Override
    public void onDownloadClick(int position, String imageUrl, String title, String url) {
        new LongOperation(imageUrl).execute();
        new downloadTask(responseTrendings1.get(position).title, responseTrendings1.get(position).video, position).execute();
    }

    @Override
    public void backPress() {
        onBackPressed();
    }

    public void scanPhoto(final String imageFileName) {
        msConn = new MediaScannerConnection(TrendingActivity.this, new MediaScannerConnection.MediaScannerConnectionClient() {
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
                                    tvTile1.setText(responseTrendings1.get(position).title);
                                    recyclerView.setOnTouchListener(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            return true;
                                        }
                                    });
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
                                    innerTrendingAdapter.notifyItemChanged(position);
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