package com.manickchand.androidlistrepositoriesgithub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.manickchand.androidlistrepositoriesgithub.DAO.GitHubSearchRepositoryDAO;
import com.manickchand.androidlistrepositoriesgithub.DAO.Item;
import com.manickchand.androidlistrepositoriesgithub.interfaces.IserviceRetrofit;
import com.manickchand.androidlistrepositoriesgithub.util.Constants;
import com.manickchand.androidlistrepositoriesgithub.util.RetrofitInit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private IserviceRetrofit iserviceRetrofit;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.retrofit = RetrofitInit.getClient(Constants.BASE_URL);
        this.iserviceRetrofit = this.retrofit.create(IserviceRetrofit.class);

        iserviceRetrofit.getTrendingRepositoriesAndroid().enqueue(new Callback<GitHubSearchRepositoryDAO>() {
            @Override
            public void onResponse(Call<GitHubSearchRepositoryDAO> call, Response<GitHubSearchRepositoryDAO> response) {

                Log.i(Constants.TAG_DEBUG, "response: "+response.body());
                if(response.isSuccessful()) {
                    //mAdapter.updateAnswers(response.body().getItems());
                    try {
                        for(Item item : response.body().getItems()){
                            Log.i(Constants.TAG_DEBUG, "item nome: "+item.getFullName());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.d(Constants.TAG_DEBUG, "posts loaded from API");
                }else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<GitHubSearchRepositoryDAO> call, Throwable t) {
                //showErrorMessage();
                Log.d("MainActivity", "error loading from API");

            }
        });
    }
}
