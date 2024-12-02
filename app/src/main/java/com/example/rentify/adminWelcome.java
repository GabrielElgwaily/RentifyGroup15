package com.example.rentify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class adminWelcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_welcome);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public void OnSetUserButton(View view) {
        View button6 = findViewById(R.id.button6);
        Intent intent = new Intent(getApplicationContext(), ManageUsers.class);
        startActivity (intent);}


    public void OnSetCategoriesButton(View view) {
        View button8 = findViewById(R.id.button8);
        Intent intent = new Intent(getApplicationContext(), Categories.class);
        startActivity (intent);}

}

