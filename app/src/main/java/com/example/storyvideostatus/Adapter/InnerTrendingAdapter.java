package com.example.storyvideostatus.Adapter;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.storyvideostatus.Model.ResponseNew;
import com.example.storyvideostatus.Model.ResponseTrending;
import com.example.storyvideostatus.R;
import com.example.storyvideostatus.Utils.Constant;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.File;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class InnerTrendingAdapter extends RecyclerView.Adapter<InnerTrendingAdapter.ViewHolder> {

    private final FirebaseAnalytics mFirebaseAnalytics;
    List<ResponseTrending> responseTrending;
    Activity activity;
    private DownloadClickListener mDownloadClickListener;

    public InnerTrendingAdapter(List<ResponseTrending> responseTrending, Activity activity) {
        this.responseTrending = responseTrending;
        this.activity = activity;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(activity);
    }

    private void fireAnalytics(String arg1, String arg2) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, arg1);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, arg2);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
    }

    public void downloadClickListener(DownloadClickListener listener) {
        mDownloadClickListener = listener;
    }

    @NonNull
    @Override
    public InnerTrendingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_items, parent, false);
        return new InnerTrendingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerTrendingAdapter.ViewHolder holder, int position) {

        Glide.with(activity)
                .load(responseTrending.get(position).image)
                .transition(withCrossFade())
                .transition(new DrawableTransitionOptions().crossFade(500))
                .into(holder.imageView);

        holder.avLoader.setVisibility(View.VISIBLE);

        holder.imageView.setVisibility(View.VISIBLE);

        holder.tvTile.setText(responseTrending.get(position).title);

        Uri uri = Uri.parse(responseTrending.get(position).video);

        holder.videoView.setVideoURI(uri);

        File file = new File(Constant.FOLDERPATH, responseTrending.get(position).thumbnail + ".mp4");
        if (!file.exists()) {
            holder.imgDownload.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_download));
        } else {
            holder.imgDownload.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_download_com));
        }

        holder.imgDownload.setOnClickListener(v -> {
            fireAnalytics("download_video", "NewStatus");
            if (mDownloadClickListener != null) {
                if (!file.exists())
                    mDownloadClickListener.onDownloadClick(position, responseTrending.get(position).image, responseTrending.get(position).title, responseTrending.get(position).video);
                else
                    Toast.makeText(activity, "Already Downloaded.", Toast.LENGTH_SHORT).show();

            }
        });

        holder.imgWhatsApp.setOnClickListener(v -> {
            fireAnalytics("share_to_WhatsApp", "NewStatus");
            Constant.whatsappShareVideo(activity, responseTrending.get(position).video, file);
        });

        holder.imgShare.setOnClickListener(v -> {
            fireAnalytics("share_video_to_other_app", "NewStatus");
            Constant.shareVideo(activity, responseTrending.get(position).video, file);
        });

        holder.videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            float videoRatio = mp.getVideoWidth() / (float) mp.getVideoHeight();
            float screenRatio = holder.videoView.getWidth() / (float)
                    holder.videoView.getHeight();
            float scaleX = videoRatio / screenRatio;
            if (scaleX >= 1f) {
                holder.videoView.setScaleX(scaleX);
            } else {
                holder.videoView.setScaleY(1f / scaleX);
            }

            holder.imageView.setVisibility(View.GONE);
            holder.avLoader.setVisibility(View.GONE);
            holder.videoView.start();

        });

        holder.imgBack.setOnClickListener(v -> {
            if (mDownloadClickListener != null) {
                mDownloadClickListener.backPress();
            }
        });

    }

    @Override
    public int getItemCount() {
        return responseTrending.size();
    }

    public void addAll(List<ResponseTrending> responseTrendings) {
        this.responseTrending.addAll(responseTrendings);
        notifyDataSetChanged();
    }

    public void clearAll() {
        responseTrending.clear();
        notifyDataSetChanged();
    }

    public interface DownloadClickListener {
        void onDownloadClick(int position, String imageUrl, String title, String url);

        void backPress();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        VideoView videoView;
        ImageView imgBack, imageView, imgDownload, imgShare, imgWhatsApp;
        TextView tvTile;
        LinearLayout llRight;
        LottieAnimationView avLoader;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            videoView = itemView.findViewById(R.id.videoView);
            imgBack = itemView.findViewById(R.id.imgBack);
            imageView = itemView.findViewById(R.id.image_View);
            imgDownload = itemView.findViewById(R.id.imgDownload);
            imgShare = itemView.findViewById(R.id.imgShare);
            imgWhatsApp = itemView.findViewById(R.id.imgWhatsApp);
            tvTile = itemView.findViewById(R.id.tvTile);
            llRight = itemView.findViewById(R.id.llRight);
            avLoader = itemView.findViewById(R.id.avLoader);

        }
    }
}
