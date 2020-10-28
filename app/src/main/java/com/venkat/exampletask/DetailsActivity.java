package com.venkat.exampletask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DetailAdapter adapter;
    ArrayList<Sheet1> testModell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ;
        testModell = getIntent().getParcelableArrayListExtra("data");
        adapter = new DetailAdapter(this,testModell);
        recyclerView.setAdapter(adapter);

    }
}