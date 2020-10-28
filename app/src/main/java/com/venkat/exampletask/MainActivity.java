package com.venkat.exampletask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.feng.fixtablelayout.FixTableLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button search;
    TestModell data = null;
    TextView textView;
    RecyclerView recyclerView;
    private static final String TAG = "MainActivity";
    List<Sheet1> list;
    DataAdapter adapter;
    public String[] title = {"Name", "EPIC No", "Father Name", "Address", "Booth", "Gender", "Age"};
    FixTableLayout fixTableLayout;
    FixTableAdapter fixTableAdapter;
    FrameLayout no_data_layout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

        fixTableLayout = findViewById(R.id.fixTableLayout);
        no_data_layout = findViewById(R.id.no_data_layout);
        editText = findViewById(R.id.edittext);
        search = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        list = new ArrayList<>();

        adapter = new DataAdapter(this, list);
        recyclerView.setAdapter(adapter);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = editText.getText().toString();
                if (TextUtils.isEmpty(s)){
                    Toast.makeText(MainActivity.this, "Please enter a valid key", Toast.LENGTH_SHORT).show();
                }else{
                    loadData(s);
                }
            }
        });
    }

    private void loadData(final String s) {

        try {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://script.googleusercontent.com/")
//                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            GitHubService service = retrofit.create(GitHubService.class);

            service.loadData().enqueue(new Callback<TestModell>() {
                @Override
                public void onResponse(Call<TestModell> call, Response<TestModell> response) {
                    List<Sheet1> tempList = new ArrayList<>();

                    data = response.body();
                    Log.d(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    /*for (Sheet1 d : data.getSheet1()) {
                        if (s.equalsIgnoreCase(d.getName()) || s.equalsIgnoreCase(d.getFatherName()) ||
                                s.equals(d.getAddress()) || s.equals(d.getGender()) || s.equals(d.getAge()) ||
                                s.equals(d.getBooth()) || s.equals(d.getEPICNo())) {
                            tempList.add(d);
                       *//* textView.setText("Name:" + d.getName() + "\n"
                                + "EPIC No: " + d.getEPICNo() + "\n"
                                + "Address:" + d.getFatherName() + "\n"
                                + "Phone Number:" + d.getAddress() + "\n"
                                + "Booth:" + d.getBooth() + "\n"
                                + "Gender" + d.getGender() + "\n"
                                + "Address:" + d.getAddress() + "\n"
                                + "Age:" + d.getAge());*//*
//                        Toast.makeText(MainActivity.this, "" + new Gson().toJson(d), Toast.LENGTH_SHORT).show();
                        }
                    }*/
                    if (tempList.size() > 0) {
                        no_data_layout.setVisibility(View.GONE);
                        fixTableLayout.setVisibility(View.VISIBLE);
                        fixTableAdapter = new FixTableAdapter(title, tempList);
                        fixTableLayout.setAdapter(fixTableAdapter);
                    } else {
                        no_data_layout.setVisibility(View.VISIBLE);
                        fixTableLayout.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "NO DATA FOUND!", Toast.LENGTH_SHORT).show();
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<TestModell> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                    Toast.makeText(MainActivity.this, "Failed...", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Log.d(TAG, "loadData: Exception"+e.getMessage());
        }
    }
}