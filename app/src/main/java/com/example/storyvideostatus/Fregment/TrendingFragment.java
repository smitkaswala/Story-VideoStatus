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
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.storyvideostatus.Adapter.LandscapeAdapter;
import com.example.storyvideostatus.Model.ResponseTrending;
import com.example.storyvideostatus.R;
import com.example.storyvideostatus.RetrofitClient.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendingFragment extends Fragment {

    LandscapeAdapter landscapeAdapter;
    RecyclerView rvVideos;
    RelativeLayout rlLoading;
    LottieAnimationView avLoader;
    Boolean isScrolling = false;
    private int page = 0;

    public TrendingFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trending, container, false);

        rlLoading = view.findViewById(R.id.rlLoading);
        rvVideos = view.findViewById(R.id.rvVideos);
        avLoader = view.findViewById(R.id.avLoader);

        TrendingStatus();
        rvVideos.addOnScrollListener(new RecyclerView.OnScrollListener() {

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

    private void TrendingStatus(){
        avLoaderVisible();
        Call<List<ResponseTrending>> call = RetrofitClient.getInstance().getModel().getTrending(
          "0"
        );
        call.enqueue(new Callback<List<ResponseTrending>>() {
            @Override
            public void onResponse(Call<List<ResponseTrending>> call, Response<List<ResponseTrending>> response) {

                Log.d("TAG", "onResponse:**** "+ response.body().size());

                List<ResponseTrending> responseTrendings = response.body();

                landscapeAdapter = new LandscapeAdapter(responseTrendings,getActivity(),page);
                rvVideos.setAdapter(landscapeAdapter);

                avLoaderGone();
            }

            @Override
            public void onFailure(Call<List<ResponseTrending>> call, Throwable t) {

            }
        });
    }

    private void PerformPagination(int page){
        avLoaderVisible();
        Call<List<ResponseTrending>> call = RetrofitClient.getInstance().getModel().getTrending(
                String.valueOf(page)
        );
        call.enqueue(new Callback<List<ResponseTrending>>() {
            @Override
            public void onResponse(Call<List<ResponseTrending>> call, Response<List<ResponseTrending>> response) {

                Log.d("TAG", "onResponse:**** "+ response.body().size());

                List<ResponseTrending> responseTrendings = response.body();


                    landscapeAdapter.addVideos(responseTrendings,page);

                avLoaderGone();
            }

            @Override
            public void onFailure(Call<List<ResponseTrending>> call, Throwable t) {

            }
        });
    }

}