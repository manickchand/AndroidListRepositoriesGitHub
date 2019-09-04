package com.manickchand.androidlistrepositoriesgithub;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.manickchand.androidlistrepositoriesgithub.adapters.AdapterRV;
import com.manickchand.androidlistrepositoriesgithub.interfaces.IserviceRetrofit;
import com.manickchand.androidlistrepositoriesgithub.interfaces.RecyclerViewOnClickListenerHack;
import com.manickchand.androidlistrepositoriesgithub.model.GitHubSearchRepository;
import com.manickchand.androidlistrepositoriesgithub.model.Item;
import com.manickchand.androidlistrepositoriesgithub.util.Constants;
import com.manickchand.androidlistrepositoriesgithub.util.RetrofitInit;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack {

    private IserviceRetrofit iserviceRetrofit;
    private Retrofit retrofit;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.retrofit = RetrofitInit.getClient(Constants.BASE_URL);
        this.iserviceRetrofit = this.retrofit.create(IserviceRetrofit.class);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv = findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(llm);

        iserviceRetrofit.getTrendingRepositoriesAndroid().enqueue(new Callback<GitHubSearchRepository>() {
            @Override
            public void onResponse(Call<GitHubSearchRepository> call, Response<GitHubSearchRepository> response) {

                Log.i(Constants.TAG_DEBUG, "response: "+response.body());

                if(response.isSuccessful()) {
                    try {
                        setAdapter(response.body().getItems());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.d(Constants.TAG_DEBUG, "posts loaded from API");
                }else {
                    int statusCode  = response.code();
                }
            }

            @Override
            public void onFailure(Call<GitHubSearchRepository> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");
            }
        });
    }

    private void setAdapter(List<Item> mlist){
        if(mlist.size()>0){
            AdapterRV adapter = new AdapterRV( this, mlist,MainActivity.this);
            adapter.setReciclerViewOnClickListenerHack(this);
            rv.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            //tv.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
        }
        else{
            rv.setVisibility(View.GONE);
            //tv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClickListener(View v, int position) {
        Toast.makeText(this,"Clicou",Toast.LENGTH_LONG).show();
    }
}
