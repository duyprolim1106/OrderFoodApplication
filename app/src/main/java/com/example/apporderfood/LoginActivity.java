package com.example.apporderfood;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apporderfood.database.DatabaseHandler;
import com.example.apporderfood.model.User;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername, edtPass;
    private Context context;
    boolean isPasswordVisible = false;
    ImageButton hidePass;
    Button btnSignin, btnSignup;
    TextView txtForgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        String TAG = null;
        context = this;

        edtUsername = findViewById(R.id.edtUsername);
        edtPass = findViewById(R.id.edtPassword);

        btnSignin = findViewById(R.id.btnSignIn);
        btnSignup = findViewById(R.id.btnSignup);
        hidePass = findViewById(R.id.hidePass);

        txtForgotPass = findViewById(R.id.txtForgotPass);

        hidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle password visibility
                isPasswordVisible = !isPasswordVisible;

                // Update EditText input type based on password visibility
                if (isPasswordVisible) {
                    edtPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    hidePass.setImageResource(R.drawable.view); // Update image to visible state
                } else {
                    edtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    hidePass.setImageResource(R.drawable.hide); // Update image to hidden state
                }

                // Move cursor to the end of EditText
                edtPass.setSelection(edtPass.getText().length());
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler db = new DatabaseHandler(context);
                String usernameInput = edtUsername.getText().toString().trim();
                if (!usernameInput.matches("[a-zA-Z0-9]+")) {
                    edtUsername.setError("Username should contain only letters and numbers");
                } else {
                    edtUsername.setError(null);
                }

                String passwordInput = edtPass.getText().toString().trim();
                if (!passwordInput.matches("[a-zA-Z0-9]+")) {
                    edtPass.setError("Password should contain only letters and numbers");
                } else {
                    edtPass.setError(null);
                }

                if (edtUsername.getError() == null && edtPass.getError() == null) {
                    if (db.checkUser(edtUsername.getText().toString(), edtPass.getText().toString())) {
                        String data = edtUsername.getText().toString();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("userName", data);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, "Wrong username or password", Toast.LENGTH_SHORT).show();

                        // Delay for 2 seconds and then dismiss the Toast
                        new Handler().postDelayed(new Runnable() {
                            @SuppressLint("ShowToast")
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).cancel();
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
                            Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).cancel();
                        }
                    }, 2000);
                }
            }
        });

        txtForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, InputUserForgotActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
