package com.manickchand.androidlistrepositoriesgithub;

import android.os.Bundle;
import android.service.autofill.TextValueSanitizer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.manickchand.androidlistrepositoriesgithub.model.Item;
import com.manickchand.androidlistrepositoriesgithub.util.Constants;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity {

    private Item item;
    private Toolbar toolbar;
    private TextView tv_name, tv_desc, tv_link;
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

        this.item = (Item) getIntent().getSerializableExtra("item");

        setData();

    }

    private void setData() {
        String title = this.item.getOwner().getLogin().length()>15 ? this.item.getOwner().getLogin().substring(0,12)+"..." : this.item.getOwner().getLogin() ;
        setTitle(title);

        this.tv_name.setText(this.item.getName());
        this.tv_desc.setText(this.item.getDescription());
        this.tv_link.setText(this.item.getUrl());

        loadImage();

    }

    private void loadImage() {
        Picasso.get()
                .load(this.item.getOwner().getAvatarUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(iv_user_image);
    }
    private void init() {
        this.toolbar = findViewById(R.id.toolbar);
        this.tv_name = findViewById(R.id.tv_name);
        this.tv_desc = findViewById(R.id.tv_desc);
        this.tv_link = findViewById(R.id.tv_link);
        this.iv_user_image =  findViewById(R.id.iv_user_image);
    }
}
