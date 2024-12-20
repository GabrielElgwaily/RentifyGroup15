package com.example.rentify;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ManageUsers extends AppCompatActivity {

    private ListView listView;
    private UserList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        ArrayList<String> userKeys = new ArrayList<>(MainActivity.rentorDatabase.keySet());
        userKeys.addAll(MainActivity.lessorDatabase.keySet());

        listView = findViewById(R.id.listView);
        adapter = new UserList(this, userKeys);
        listView.setAdapter(adapter);
    }
}
