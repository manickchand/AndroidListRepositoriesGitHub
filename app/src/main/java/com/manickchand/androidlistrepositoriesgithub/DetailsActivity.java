package com.manickchand.androidlistrepositoriesgithub;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.manickchand.androidlistrepositoriesgithub.model.Item;
import com.manickchand.androidlistrepositoriesgithub.util.Constants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

public class DetailsActivity extends AppCompatActivity {

    private Item item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.item = (Item) getIntent().getSerializableExtra("item");

        Log.i(Constants.TAG_DEBUG,"item: "+this.item.getName());

    }
}
