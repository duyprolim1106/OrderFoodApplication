package com.example.apporderfood;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apporderfood.database.DatabaseHandler;

public class InputUserForgotActivity extends AppCompatActivity {

    Button btnBack, btnNext;

    EditText edtUsername;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_user_forgot);
        context = this;

        edtUsername = findViewById(R.id.edtUsernameForgot);

        btnBack = findViewById(R.id.btnBackLogin);
        btnNext = findViewById(R.id.btnNextPass);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputUserForgotActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler db = new DatabaseHandler(context);
                String usernameInput = edtUsername.getText().toString().trim();
                if (!usernameInput.matches("[a-zA-Z0-9]+")) {
                    edtUsername.setError("Username should contain only letters and numbers");
                } else {
                    edtUsername.setError(null);
                }

                if (edtUsername.getError() == null) {
                    if (db.checkUsername(edtUsername.getText().toString())) {
                        String data = edtUsername.getText().toString();
                        Intent intent = new Intent(InputUserForgotActivity.this, InputPassForgotActivity.class);
                        intent.putExtra("userName", data);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, "Wrong username", Toast.LENGTH_SHORT).show();

                        // Delay for 2 seconds and then dismiss the Toast
                        new Handler().postDelayed(new Runnable() {
                            @SuppressLint("ShowToast")
                            @Override
                            public void run() {
                                Toast.makeText(InputUserForgotActivity.this, "", Toast.LENGTH_SHORT).cancel();
                            }
                        }, 2000);
                    }
                } else {
                    Toast.makeText(context, "Please complete all information", Toast.LENGTH_SHORT).show();

                    // Delay for 2 seconds and then dismiss the Toast
                    new Handler().postDelayed(new Runnable() {
                        @SuppressLint("ShowToast")
                        @Override
                        public void run() {
                            Toast.makeText(InputUserForgotActivity.this, "", Toast.LENGTH_SHORT).cancel();
                        }
                    }, 2000);
                }
            }
        });
    }
}
