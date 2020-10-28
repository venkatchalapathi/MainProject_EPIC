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
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    SignInButton sign_in_button;
    GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "LoginActivity";
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progressbar);
        mAuth = FirebaseAuth.getInstance();
        sign_in_button = findViewById(R.id.sign_in_button);
        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGoogle();
            }
        });
    }

    private void signInWithGoogle() {
        progressBar.setVisibility(View.VISIBLE);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 123);
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 123) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + new Gson().toJson(account) + "dfdsfL:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(mBinding.mainLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user!= null) {
            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://script.googleusercontent.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                GitHubService service = retrofit.create(GitHubService.class);

                service.validateUsers().enqueue(new Callback<ValidateUser>() {
                    @Override
                    public void onResponse(Call<ValidateUser> call, Response<ValidateUser> response) {
                        List<Sheet1> tempList = new ArrayList<>();
                        checkAuthentication(response.body(), user);

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
                    public void onFailure(Call<ValidateUser> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                        Toast.makeText(LoginActivity.this, "Failed...", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                Log.d(TAG, "loadData: Exception" + e.getMessage());
            }
        }
    }

    private void checkAuthentication(ValidateUser body, FirebaseUser user) {
        if (new Gson().toJson(body).contains(user.getEmail())){
            progressBar.setVisibility(View.GONE);
            finish();
            startActivity(new Intent(this,MainActivity2.class));
        }else{
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "User not Authorized", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}