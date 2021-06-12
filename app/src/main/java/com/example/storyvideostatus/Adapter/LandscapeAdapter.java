package com.example.storyvideostatus.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storyvideostatus.Activity.NewActivity;
import com.example.storyvideostatus.Activity.TrendingActivity;
import com.example.storyvideostatus.Model.ResponseTrending;
import com.example.storyvideostatus.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LandscapeAdapter extends RecyclerView.Adapter<LandscapeAdapter.ViewHolder> {
    List<ResponseTrending> responseTrendings;
    public List<Object> objects = new ArrayList<>();
    Activity activity;
    int page;

    public LandscapeAdapter(List<ResponseTrending> responseTrendings,Activity activity ,int page){
        this.responseTrendings = responseTrendings;
        this.activity = activity;
        this.objects.addAll(responseTrendings);
        this.page = page;
    }
    @NonNull
    @Override
    public LandscapeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LandscapeAdapter.ViewHolder holder, int position) {

        ResponseTrending Response = (ResponseTrending) objects.get(position);

        Picasso.get().load(responseTrendings.get(position).image).into(holder.mImage);
        holder.textView.setText(responseTrendings.get(position).title);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("VideoList", (Serializable) responseTrendings);
                Intent intent = new Intent(activity, TrendingActivity.class);
                intent.putExtras(bundle);
                intent.putExtra("position", position);
                intent.putExtra("page",page);
                activity.startActivity(intent);
//                activity.finish();

            }
        });
    }

    @Override
    public int getItemCount() {
        return responseTrendings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImage;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mImage = itemView.findViewById(R.id.mImage);
            textView = itemView.findViewById(R.id.text_view);

        }
    }
    public void addVideos(List<ResponseTrending> Videos, int page){

        this.responseTrendings.addAll(Videos);
        this.page = page;
        objects.addAll(responseTrendings);
        notifyDataSetChanged();
    }
}
