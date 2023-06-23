package com.example.aplikasikita.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasikita.MainActivity;
import com.example.aplikasikita.R;
import com.example.aplikasikita.model.ApiResponse;
import com.example.aplikasikita.util.ApiClient;
import com.example.aplikasikita.util.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etUserName, etPassword;
    Button btnLogin;
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName = findViewById(R.id.et_user_name);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tv_register);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = etUserName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (userName.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Silakan lengkapi semua field", Toast.LENGTH_SHORT).show();
                } else {
                    performUserLogin(userName, password);
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void performUserLogin(String userName, String password) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ApiResponse> call = apiInterface.performUserSignIn(userName, password);

        Log.d("Debug", "Objek: " + userName + " " + password);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();



                    if (apiResponse != null) {
                        String status = apiResponse.getStatus();
                        int statusCode = apiResponse.getResultCode();
                        String name = apiResponse.getName();

                        Log.d("Debug", "Objek: " + name + statusCode);


                        if (status.equals("OK")) {
                            if (name != null) {
                                Toast.makeText(LoginActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("name", name);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Login gagal", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Terjadi kesalahan: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Terjadi kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
