package com.example.storyvideostatus.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storyvideostatus.Activity.NewActivity;
import com.example.storyvideostatus.Model.ResponseNew;
import com.example.storyvideostatus.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {
    List<ResponseNew> responseNew;
    public List<Object> objects = new ArrayList<>();
    Activity activity;
    int page;

    public StoryAdapter(List<ResponseNew> responseNew, Activity activity, int page) {
        this.responseNew = responseNew;
        this.activity = activity;
        this.objects.addAll(responseNew);
        this.page = page;
    }

    @NonNull
    @Override
    public StoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryAdapter.ViewHolder holder, int position) {

        ResponseNew ResponseneW = (ResponseNew) objects.get(position);

        Picasso.get().load(responseNew.get(position).image).into(holder.mImage);
        holder.textView.setText(responseNew.get(position).title);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("VideoList", (Serializable) responseNew);
                Intent intent = new Intent(activity, NewActivity.class);
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
        return responseNew.size();
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

    public void addVideos(List<ResponseNew> video, int page) {
        /*for (ResponseNew vd : video)
        {
            responseNew.add(vd);
        }*/

        this.responseNew.addAll(video);
        this.page = page;
        objects.addAll(responseNew);
        notifyDataSetChanged();
    }


}
