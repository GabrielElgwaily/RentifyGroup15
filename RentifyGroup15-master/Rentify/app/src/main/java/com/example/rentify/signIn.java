package com.example.rentify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class signIn extends AppCompatActivity {
    private EditText usernameInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



    public void OnSetSigninButton(View view) {
        View button3 = findViewById(R.id.button4);
        usernameInput = findViewById(R.id.usernameInputLI);
        passwordInput = findViewById(R.id.passwordInputLI);
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
//Application Context and Activity
        if (username.equals("admin") && password.equals("XPI76SZUqyCjVxgnUjm0")) {
            Intent intent = new Intent(getApplicationContext(), adminWelcome.class);
            startActivity (intent);}
        if (MainActivity.verifyRentor(username, password)){
            Intent intent = new Intent(getApplicationContext(), WelcomePageRenter.class);
            startActivity (intent);
        }
        if (MainActivity.verifyLessor(username, password)){
            Intent intent = new Intent(getApplicationContext(), welcomePage.class);
            startActivity (intent);
        }
    }


    public void OnSetLoginButton(View view) {
        View button5 = findViewById(R.id.button5);
//Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity (intent);}
    }
