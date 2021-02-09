package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText username = (EditText) findViewById(R.id.signUpUsernameText);
        EditText password = (EditText) findViewById(R.id.signUpPasswordText);
        Button signUp = (Button) findViewById(R.id.signUpBtn);
    }
}