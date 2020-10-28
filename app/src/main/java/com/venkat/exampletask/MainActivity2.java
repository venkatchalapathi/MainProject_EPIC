package com.venkat.exampletask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    TestModell data = null;
    private static final String TAG = "MainActivity2";
    EditText search_text, epic, name;
    Button searchBtn;
    ProgressBar progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        progressBar2 = findViewById(R.id.progressBar2);
        search_text = findViewById(R.id.search_text);
        epic = findViewById(R.id.epic_edittext);
        name = findViewById(R.id.edit_name);

        searchBtn = findViewById(R.id.search_btn);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            logoutMethod();
        } else {
            searchBtn.setOnClickListener(v -> {
                progressBar2.setVisibility(View.VISIBLE);
                if (search_text.getText().toString().equals("") && epic.getText().toString().equals("")
                        && name.getText().toString().equals("")) {
                    Toast.makeText(this, "Please Enter a Valid key", Toast.LENGTH_SHORT).show();
                    progressBar2.setVisibility(View.GONE);
                } else {
                    if (!search_text.getText().toString().equals("")) {
                        loadData(search_text.getText().toString());
                    } else {
                        if (name.getText().toString().equals("") || epic.getText().toString().equals("")) {
                            Toast.makeText(this, "Please enter epic no and name", Toast.LENGTH_SHORT).show();
                            progressBar2.setVisibility(View.GONE);
                        } else
                            loadData2(name.getText().toString(), epic.getText().toString());
                    }
                }
            });
        }
        /*search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.equals("")){
                    epic.setText("");
                    name.setText("");
                    loadData(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/
    }

    private void loadData2(String name, String epic) {
        Log.d(TAG, "loadData2: name:" + name + " Epic :" + epic);
        try {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://script.googleusercontent.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            GitHubService service = retrofit.create(GitHubService.class);

            service.loadData().enqueue(new Callback<TestModell>() {
                @Override
                public void onResponse(Call<TestModell> call, Response<TestModell> response) {
                    List<Sheet1> tempList = new ArrayList<>();

                    data = response.body();
                    for (Sheet1 s : response.body().getSheet1()) {
                        if (s.getName().equalsIgnoreCase(name) && s.getePICNo().equalsIgnoreCase(epic)) {
                            tempList.add(s);
                        }
                    }
                    if (tempList.size() > 0)
                        sendResponse(tempList);
                    else {
                        progressBar2.setVisibility(View.GONE);
                        Toast.makeText(MainActivity2.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                    Log.d(TAG, "onResponse: " + new Gson().toJson(response.body()));

                   /* if (tempList.size() > 0) {
                        no_data_layout.setVisibility(View.GONE);
                        fixTableLayout.setVisibility(View.VISIBLE);
                        fixTableAdapter = new FixTableAdapter(title, tempList);
                        fixTableLayout.setAdapter(fixTableAdapter);
                    } else {
                        no_data_layout.setVisibility(View.VISIBLE);
                        fixTableLayout.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "NO DATA FOUND!", Toast.LENGTH_SHORT).show();
                    }
                    adapter.notifyDataSetChanged();*/
                }

                @Override
                public void onFailure(Call<TestModell> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                    Toast.makeText(MainActivity2.this, "Failed...", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "loadData: Exception" + e.getMessage());
        }
    }

    private void sendResponse(List<Sheet1> body) {
        progressBar2.setVisibility(View.GONE);
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) body);
        startActivity(intent);
    }

    private void loadData(final String sx) {

        try {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://script.googleusercontent.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            GitHubService service = retrofit.create(GitHubService.class);

            service.loadData().enqueue(new Callback<TestModell>() {
                @Override
                public void onResponse(Call<TestModell> call, Response<TestModell> response) {
                    List<Sheet1> tempList = new ArrayList<>();

                    data = response.body();
                    for (Sheet1 s : response.body().getSheet1()) {
                        if (s.getName().equalsIgnoreCase(sx) || s.getDivision().equalsIgnoreCase(sx) ||
                                s.getBooth().equalsIgnoreCase(sx) || s.getePICNo().equalsIgnoreCase(sx) ||
                                s.getAge().equalsIgnoreCase(sx) || s.getGender().equalsIgnoreCase(sx) ||
                                s.getHouseNo().equalsIgnoreCase(sx) || s.getSpouseOrFatherName().equalsIgnoreCase(sx) ||
                                s.getReferee().equalsIgnoreCase(sx) || s.getReferred().equalsIgnoreCase(sx)) {
                            tempList.add(s);
                        }
                    }
                    if (tempList.size() > 0)
                        sendResponse(tempList);
                    {
                        progressBar2.setVisibility(View.GONE);
                        Toast.makeText(MainActivity2.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
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
                   /* if (tempList.size() > 0) {
                        no_data_layout.setVisibility(View.GONE);
                        fixTableLayout.setVisibility(View.VISIBLE);
                        fixTableAdapter = new FixTableAdapter(title, tempList);
                        fixTableLayout.setAdapter(fixTableAdapter);
                    } else {
                        no_data_layout.setVisibility(View.VISIBLE);
                        fixTableLayout.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "NO DATA FOUND!", Toast.LENGTH_SHORT).show();
                    }
                    adapter.notifyDataSetChanged();*/
                }

                @Override
                public void onFailure(Call<TestModell> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                    Toast.makeText(MainActivity2.this, "Failed...", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "loadData: Exception" + e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            logoutMethod();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logoutMethod() {
        FirebaseAuth.getInstance().signOut();
        finish();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}