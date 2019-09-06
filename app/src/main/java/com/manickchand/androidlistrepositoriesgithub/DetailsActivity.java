package com.manickchand.androidlistrepositoriesgithub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.manickchand.androidlistrepositoriesgithub.model.Item;
import com.manickchand.androidlistrepositoriesgithub.util.Constants;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailsActivity extends AppCompatActivity {

    private Item item;
    private Toolbar toolbar;
    private TextView tv_name, tv_desc, tv_link, tv_owner,
                     tv_start_count, tv_fork_count, tv_language;
    private ImageView iv_user_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        init();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setTitle(R.string.title_activity_details);

        this.item = (Item) getIntent().getSerializableExtra(Constants.STR_ITEM);

        setData();

    }

    private void setData() {

        this.tv_owner.setText(this.item.getOwner().getLogin());
        this.tv_fork_count.setText(String.valueOf(this.item.getForksCount()));
        this.tv_start_count.setText(String.valueOf(this.item.getStargazersCount()));
        this.tv_language.setText(this.item.getLanguage());
        this.tv_name.setText(this.item.getName());
        this.tv_desc.setText(this.item.getDescription());
        this.tv_link.setText(this.item.getHtmlUrl());

        this.tv_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl(item.getHtmlUrl());
            }
        });

        loadImage();

    }

    //carrega imagem do dono do repo e seta do imageView
    private void loadImage() {
        Picasso.get()
                .load(this.item.getOwner().getAvatarUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(iv_user_image);
    }

    //inicializa atributos
    private void init() {
        this.toolbar = findViewById(R.id.toolbar);
        this.tv_name = findViewById(R.id.tv_name);
        this.tv_desc = findViewById(R.id.tv_desc);
        this.tv_link = findViewById(R.id.tv_link);
        this.tv_owner = findViewById(R.id.tv_owner);
        this.tv_start_count = findViewById(R.id.tv_start_count);
        this.tv_fork_count = findViewById(R.id.tv_fork_count);
        this.tv_language = findViewById(R.id.tv_language);
        this.iv_user_image =  findViewById(R.id.iv_user_image);
    }


    //abre navegador no endereço do repositorio
    private void goToUrl(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
