package com.manickchand.androidlistrepositoriesgithub.interfaces;

import com.manickchand.androidlistrepositoriesgithub.model.GitHubSearchRepository;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IserviceRetrofit {

    @GET("/search/repositories")
    Call<GitHubSearchRepository> getTrendingRepositoriesAndroid(@Query("q")String filter, @Query("sort")String sort, @Query("order")String order);
}
