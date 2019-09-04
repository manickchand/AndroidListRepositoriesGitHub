package com.manickchand.androidlistrepositoriesgithub.interfaces;

import com.manickchand.androidlistrepositoriesgithub.model.GitHubSearchRepository;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IserviceRetrofit {

    @GET("/search/repositories?q=android+language:java&sort=stars&order=desc")
    Call<GitHubSearchRepository> getTrendingRepositoriesAndroid();
}
