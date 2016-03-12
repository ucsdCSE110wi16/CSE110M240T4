package com.parse.starter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


/**
 * Created by PerryLiu on 1/25/16.
 */
public class SignUpActivity extends AppCompatActivity{
    protected EditText firstName;
    protected EditText lastName;
    protected EditText passwordEditText;
    protected EditText emailEditText;
    protected EditText confirmPassword;
    protected Button signUpButton;
    protected Drawable background;
    protected TextView signUpTextView;
    protected Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.sign_up);

        firstName = (EditText) findViewById(R.id.firstnamefield);
        lastName = (EditText) findViewById(R.id.lastnamefield);
        signUpButton = (Button) findViewById(R.id.signupButton);
        passwordEditText = (EditText) findViewById(R.id.passwordField);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        emailEditText = (EditText) findViewById(R.id.emailField);

        firstName.requestFocus();
        firstName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                firstName.getBackground().setColorFilter(Color.rgb(101, 153, 255), PorterDuff.Mode.SRC_ATOP);
                return false;
            }
        });
        firstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus || !firstName.hasFocus()) {
                    hideKeyboard(v);
                }
            }
        });

        lastName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                lastName.getBackground().setColorFilter(Color.rgb(101, 153, 255), PorterDuff.Mode.SRC_ATOP);
                return false;
            }
        });
        lastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus || !lastName.hasFocus()) {
                    hideKeyboard(v);
                }
            }
        });

        passwordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                passwordEditText.getBackground().setColorFilter(Color.rgb(101, 153, 255), PorterDuff.Mode.SRC_ATOP);
                return false;
            }
        });
        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus || !passwordEditText.hasFocus()) {
                    hideKeyboard(v);
                }
            }
        });

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        emailEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                emailEditText.getBackground().setColorFilter(Color.rgb(101, 153, 255), PorterDuff.Mode.SRC_ATOP);
                return false;
            }
        });
        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus || !emailEditText.hasFocus()) {
                    hideKeyboard(v);
                }
            }
        });

        confirmPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                confirmPassword.getBackground().setColorFilter(Color.rgb(101, 153, 255), PorterDuff.Mode.SRC_ATOP);
                return false;
            }
        });
        confirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus || !confirmPassword.hasFocus()) {
                    hideKeyboard(v);
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPass = confirmPassword.getText().toString();
                String name1 = firstName.getText().toString();
                String name2 = lastName.getText().toString();

                username = username.trim();
                password = password.trim();

                if(name1.isEmpty() || name2.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    if(name1.isEmpty()) {
                        firstName.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                    }
                    if(name2.isEmpty()) {
                        lastName.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                    }
                    if(username.isEmpty()) {
                        emailEditText.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                    }
                    if(password.isEmpty()) {
                        passwordEditText.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                    }
                    if(confirmPass.isEmpty()) {
                        confirmPassword.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setMessage("Please fill in every field.")
                            .setTitle("Please fill in every field.")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    setProgressBarIndeterminateVisibility(true);

                    ParseUser newUser = new ParseUser();
                    newUser.setUsername(username);
                    newUser.setPassword(password);
                    newUser.setEmail(username);
                    newUser.put("Name", name1 + " " + name2);
                    newUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            setProgressBarIndeterminateVisibility(false);

                            if (e == null) {
                                // Success!
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                builder.setMessage(e.getMessage())
                                        .setTitle(R.string.signup_error_title)
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });
                }
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}