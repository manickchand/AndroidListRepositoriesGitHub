package com.manickchand.androidlistrepositoriesgithub.interfaces;

import com.google.gson.JsonElement;
import com.manickchand.androidlistrepositoriesgithub.DAO.GitHubSearchRepositoryDAO;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IserviceRetrofit {

    @GET("/search/repositories?q=android+language:java&sort=stars&order=desc")
    Call<GitHubSearchRepositoryDAO> getTrendingRepositoriesAndroid();
}
