package com.example.storyvideostatus.InnerModel;

import com.example.storyvideostatus.Model.ResponseCategory;
import com.example.storyvideostatus.Model.ResponseNew;
import com.example.storyvideostatus.Model.ResponseTrending;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Model {

    @GET("video/all/{page}/created/{language}/8cve98hty47h2uf0dfg4re7fg0wdhn24ffr3er3reg67yu20/no/")
    Call<List<ResponseNew>> getVideo(
            @Path("page") String page,
            @Path("language") String language
    );
    @GET("video/all/{pager}/downloads/0/8cve98hty47h2uf0dfg4re7fg0wdhn24ffr3er3reg67yu20/no/")
    Call<List<ResponseTrending>> getTrending(
            @Path("pager") String pager
    );
    @GET("video/by/category/{page}/created/0/{status}%2F8cve98hty47h2uf0dfg4re7fg0wdhn24ffr3er3reg67yu20%2Fno")
    Call<List<ResponseCategory>> getCategory(
            @Path("page") String page,
            @Path("status") String status
    );
}
