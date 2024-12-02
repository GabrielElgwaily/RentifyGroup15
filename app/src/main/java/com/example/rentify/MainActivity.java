package com.example.rentify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText usernameInput;
    private EditText passwordInput;
    public static HashMap<String, String> rentorDatabase = new HashMap<>();
    public static HashMap<String, String> lessorDatabase = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void OnSetLogin(View view) {
        View button5 = findViewById(R.id.button5);
//Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), signIn.class);
        startActivity (intent);
    }

    // store usernames and passwords in a hashmap
    public void addRentor(String username, String password) {
        //this implementation will override any other users with the same username
        //fix this by showing the user a warning
            rentorDatabase.put(username, password);
    }
    public void addLessor(String username, String password) {
        //this implementation will override any other users with the same username
        //fix this by showing the user a warning
        lessorDatabase.put(username, password);
    }
    public static boolean verifyRentor(String username, String password) {
        if (rentorDatabase.containsKey(username)) {
            return rentorDatabase.get(username).equals(password);
        }
        return false;
    }
    public static boolean verifyLessor(String username, String password) {
        if (lessorDatabase.containsKey(username)) {
            return lessorDatabase.get(username).equals(password);
        }
        return false;
    }


    public void OnSetRenterButton(View view) {
        View button2 = findViewById(R.id.button2);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        if (!username.isEmpty() && !password.isEmpty()) {
            addRentor(username, password);
        Intent intent = new Intent(getApplicationContext(), WelcomePageRenter.class);
        startActivity (intent);}
    }

    public void OnSetLessorButton(View view) {
        View button3 = findViewById(R.id.button3);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        if (!username.isEmpty() && !password.isEmpty()) {
            addLessor(username, password);
        Intent intent = new Intent(getApplicationContext(), welcomePage.class);
        startActivity (intent);}
    }




}