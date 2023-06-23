package com.example.aplikasikita.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasikita.R;
import com.example.aplikasikita.model.ApiResponse;
import com.example.aplikasikita.util.ApiClient;
import com.example.aplikasikita.util.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    Button btnRegister;
    TextView login, etName, etUsername, etPassword;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        login = findViewById(R.id.login);
        btnRegister = findViewById(R.id.btnRegister);
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        etUsername = findViewById(R.id.etUsername);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void signUp(){
        String name = etName.getText().toString();
        String password = etPassword.getText().toString();
        String username = etUsername.getText().toString();

        if(name.isEmpty()){
            etName.setError("Please fill in Name");
            etName.requestFocus();
        } else if(username.isEmpty()){
            etUsername.setError("Please fill in Username");
            etUsername.requestFocus();
        } else if(password.isEmpty() || password.length() < 6){
            etPassword.setError("Please fill in Password (min 6 characters)");
            etPassword.requestFocus();
        } else {
            progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<ApiResponse> call = apiInterface.performUserRegistration(name, username, password);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    progressDialog.dismiss();
                    if(response.isSuccessful()){
                        if(response.body().getStatus().equals("OK")){
                            if(response.body().getResultCode() == 1){
                                Toast.makeText(RegisterActivity.this, response.body().getStatus(),
                                        Toast.LENGTH_SHORT).show();
                                onBackPressed();
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Registration Successfully.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "Something went wrong.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Something went wrong.",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}