package com.example.rentify;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LessorRequests extends AppCompatActivity {

    private ListView listViewRequests;
    private List<Items> requestedItems;
    private DatabaseReference databaseRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessor_requests);

        // Initialize components
        listViewRequests = findViewById(R.id.listViewSearchResults);
        requestedItems = new ArrayList<>();
        databaseRequests = FirebaseDatabase.getInstance().getReference("lessor_requests");

        // Load requested items
        loadRequestedItems();
    }

    private void loadRequestedItems() {
        databaseRequests.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                requestedItems.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Items item = itemSnapshot.getValue(Items.class);
                    if (item != null) {
                        requestedItems.add(item);
                    }
                }
                LessorRequestList adapter = new LessorRequestList(LessorRequests.this, requestedItems);
                listViewRequests.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
    }
}
