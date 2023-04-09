package com.example.apporderfood;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apporderfood.database.DatabaseHandler;
import com.example.apporderfood.model.User;

public class SignupActivity extends AppCompatActivity {

    private Context context;
    EditText edtUsername, edtPass, edtConfirmPass;

    ImageButton hidePass, hideConfirmPass;

    Button btnSignUp, btnSignin;
    boolean isPasswordVisible = false;
    boolean isConfirmPasswordVisible = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);
        String TAG = null;
        context = this;

        edtUsername = findViewById(R.id.edtUsername);
        edtPass = findViewById(R.id.edtPassword);
        edtConfirmPass = findViewById(R.id.edtConfirmPass);

        btnSignUp = findViewById(R.id.btnSignup);
        btnSignin = findViewById(R.id.btnSignIn);
        hidePass = findViewById(R.id.hidePass);
        hideConfirmPass = findViewById(R.id.hideConfirmPass);

        edtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkUsername(edtUsername);
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkUsername(edtUsername);
            }
        });

        edtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkPass(edtPass);
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkPass(edtPass);
            }
        });

        edtConfirmPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkConfirmPass(edtPass, edtConfirmPass);
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkConfirmPass(edtPass, edtConfirmPass);
            }
        });

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

        hideConfirmPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle password visibility
                isConfirmPasswordVisible = !isConfirmPasswordVisible;

                // Update EditText input type based on password visibility
                if (isConfirmPasswordVisible) {
                    edtConfirmPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    hideConfirmPass.setImageResource(R.drawable.view); // Update image to visible state
                } else {
                    edtConfirmPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    hideConfirmPass.setImageResource(R.drawable.hide); // Update image to hidden state
                }

                // Move cursor to the end of EditText
                edtConfirmPass.setSelection(edtConfirmPass.getText().length());
            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUsername(edtUsername);
                checkPass(edtPass);
                checkConfirmPass(edtPass, edtConfirmPass);
                if (edtUsername.getError() == null && edtPass.getError() == null && edtConfirmPass.getError() == null) {
                    User user = new User( edtUsername.getText().toString(), edtPass.getText().toString());
                    try {
                        DatabaseHandler db = new DatabaseHandler(context);
                        db.addUser(user);
                        db.close();
                        // Show success message using Toast
                        Toast.makeText(context, "Account registration successful", Toast.LENGTH_SHORT).show();

                        // Delay for 2 seconds and then dismiss the Toast
                        new Handler().postDelayed(new Runnable() {
                            @SuppressLint("ShowToast")
                            @Override
                            public void run() {
                                Toast.makeText(SignupActivity.this, "", Toast.LENGTH_SHORT).cancel();
                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        }, 2000);

                    } catch (SQLiteException e) {
                        Log.e(TAG, "Error adding user to database: " + e.getMessage());
                    }
                } else {
                    Toast.makeText(context, "Please complete all information", Toast.LENGTH_SHORT).show();

                    // Delay for 2 seconds and then dismiss the Toast
                    new Handler().postDelayed(new Runnable() {
                        @SuppressLint("ShowToast")
                        @Override
                        public void run() {
                            Toast.makeText(SignupActivity.this, "", Toast.LENGTH_SHORT).cancel();
                        }
                    }, 2000);
                }
            }
        });


    }

    public void checkUsername(EditText edtUsername) {
        String usernameInput = edtUsername.getText().toString().trim();
        if (!usernameInput.matches("[a-zA-Z0-9]+")) {
            edtUsername.setError("Username should contain only letters and numbers");
        } else {
            edtUsername.setError(null);
        }
    }
    public void checkPass(EditText edtPass) {
        String passwordInput = edtPass.getText().toString().trim();
        if (!passwordInput.matches("[a-zA-Z0-9]+")) {
            edtPass.setError("Password should contain only letters and numbers");
        } else {
            edtPass.setError(null);
        }
    }
    public void checkConfirmPass(EditText edtPass, EditText edtConfirmPass) {
        String passwordInput = edtPass.getText().toString().trim();
        if (!passwordInput.matches("[a-zA-Z0-9]+")) {
            edtPass.setError("Password should contain only letters and numbers");
        } else {
            edtPass.setError(null);
        }

        String confirmPassword = edtConfirmPass.getText().toString();
        if (!passwordInput.equals(confirmPassword)) {
            edtConfirmPass.setError("Passwords do not match");
        } else {
            edtConfirmPass.setError(null);
        }
    }
}
