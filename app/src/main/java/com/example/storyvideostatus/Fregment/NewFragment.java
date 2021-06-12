package com.example.storyvideostatus.Fregment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.storyvideostatus.Adapter.StoryAdapter;
import com.example.storyvideostatus.Model.ResponseNew;
import com.example.storyvideostatus.R;
import com.example.storyvideostatus.RetrofitClient.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewFragment extends Fragment {

    StoryAdapter storyAdapter;
    RecyclerView recyclerView;
    RelativeLayout rlLoading;
    LottieAnimationView avLoader;
    GridLayoutManager gridLayoutManager;
    ProgressBar progressBar;
    Boolean isScrolling = false;
    private int page = 0;

    public NewFragment (){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_new, container, false);

        gridLayoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView = view.findViewById(R.id.rvVideos);
        rlLoading = view.findViewById(R.id.rlLoading);
        avLoader = view.findViewById(R.id.avLoader);
        progressBar = view.findViewById(R.id.progress_bar);

        NewVideos();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager linearLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                int currentItems = linearLayoutManager.getChildCount();
                int totalItems = linearLayoutManager.getItemCount();
                int scrollOutItems = linearLayoutManager.findFirstVisibleItemPosition();

                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {

                    isScrolling = false;
                    page++;
                    Log.d("TAG+++++++++", "onScrolled: " + page);
                    PerformPagination(page);

                }

            }

        });
        return view;
    }

    private void avLoaderVisible() {
        rlLoading.setVisibility(View.VISIBLE);
    }

    private void avLoaderGone() {
        rlLoading.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    private void NewVideos() {
        avLoaderVisible();
        Call<List<ResponseNew>> call = RetrofitClient.getInstance().getModel().getVideo(
                "0","0"
        );
        call.enqueue(new Callback<List<ResponseNew>>() {
            @Override
            public void onResponse(Call<List<ResponseNew>> call, Response<List<ResponseNew>> response) {

                Log.d("TAG", "onResponse:++++ " + response.body().size());

                List<ResponseNew> responseNews = response.body();

                storyAdapter = new StoryAdapter( responseNews,getActivity(),page);
                recyclerView.setAdapter(storyAdapter);

                avLoaderGone();
            }

            @Override
            public void onFailure(Call<List<ResponseNew>> call, Throwable t) {

                Log.d("TAG", "onFailure:---- " + t.toString());

            }
        });
    }

    private void PerformPagination(int page) {
        avLoaderVisible();
        Call<List<ResponseNew>> call = RetrofitClient.getInstance().getModel().getVideo(
                String.valueOf(page),"0"
        );
        call.enqueue(new Callback<List<ResponseNew>>() {
            @Override
            public void onResponse(Call<List<ResponseNew>> call, Response<List<ResponseNew>> response) {

                Log.d("TAG", "onResponse:++++ " + response.body().size());

                List<ResponseNew> responseNews = response.body();

                storyAdapter.addVideos(responseNews,page);

                avLoaderGone();
            }

            @Override
            public void onFailure(Call<List<ResponseNew>> call, Throwable t) {

                Log.d("TAG", "onFailure:---- " + t.toString());

            }
        });
    }

}