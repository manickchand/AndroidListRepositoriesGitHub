package com.manickchand.androidlistrepositoriesgithub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.manickchand.androidlistrepositoriesgithub.adapters.AdapterRV;
import com.manickchand.androidlistrepositoriesgithub.interfaces.IserviceRetrofit;
import com.manickchand.androidlistrepositoriesgithub.interfaces.RecyclerViewOnClickListenerHack;
import com.manickchand.androidlistrepositoriesgithub.model.GitHubSearchRepository;
import com.manickchand.androidlistrepositoriesgithub.model.Item;
import com.manickchand.androidlistrepositoriesgithub.util.Constants;
import com.manickchand.androidlistrepositoriesgithub.util.HasConnection;
import com.manickchand.androidlistrepositoriesgithub.util.RetrofitInit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack {

    private Toolbar toolbar;
    private IserviceRetrofit iserviceRetrofit; //interface com metodos de requiziicoes
    private Retrofit retrofit;
    private RecyclerView rv;
    private List<Item> mlist;
    private String filter;
    private String language;
    private String sort;
    private String order;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //chama funcao de iniar variaveis
        init();

        setSupportActionBar(toolbar);
        setTitle(R.string.title_main_activity);

        initRecyclerView();

        checkNetworkState();
    }

    private void checkNetworkState() {
        HasConnection connection = new HasConnection();
        if(connection.networkStatus(this)){
            requestRepositories();
        }
        else{
            Toast.makeText(MainActivity.this, R.string.no_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void requestRepositories() {

        String repositoryFilter = this.filter+"+language:"+this.language;

        progressBar.setVisibility(View.VISIBLE);

        iserviceRetrofit.getTrendingRepositoriesAndroid(repositoryFilter, this.sort, this.order).enqueue(new Callback<GitHubSearchRepository>() {
            @Override
            public void onResponse(Call<GitHubSearchRepository> call, Response<GitHubSearchRepository> response) {

                progressBar.setVisibility(View.GONE);

                if(response.isSuccessful()) {
                    try {
                        mlist = response.body().getItems();
                        setAdapter();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {

                    Log.d(Constants.TAG_DEBUG, "error loading repositories, response.code: "+ response.code());
                    Toast.makeText(MainActivity.this, R.string.error_get_repositories, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GitHubSearchRepository> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d(Constants.TAG_DEBUG, "error loading repositories: "+t.getMessage());
                Toast.makeText(MainActivity.this, R.string.error_get_repositories, Toast.LENGTH_SHORT).show();
            }
        });
    }


    //inicializa atributos
    private void init() {
        this.toolbar =findViewById(R.id.toolbar);
        this.rv = findViewById(R.id.rv);
        this.progressBar = findViewById(R.id.progressBar);
        this.filter = "android";
        this.language = "java";
        this.sort = "stars";
        this.order = "desc";
        this.mlist = new ArrayList<>();
        this.retrofit = RetrofitInit.getClient(Constants.BASE_URL);
        this.iserviceRetrofit = this.retrofit.create(IserviceRetrofit.class);
    }

    // seta layout linear no recyclerview
    private void initRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_update) {
            checkNetworkState();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setAdapter(){
        AdapterRV adapter = new AdapterRV( this, mlist);
        adapter.setReciclerViewOnClickListenerHack(this);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        rv.setVisibility(View.VISIBLE);
    }

    //Vai para tela de detalhes ao clicar
    @Override
    public void onClickListener(View v, int position) {
        Intent intent = new Intent(this,DetailsActivity.class);
        intent.putExtra("item", mlist.get(position));
        startActivity(intent);
    }
}
