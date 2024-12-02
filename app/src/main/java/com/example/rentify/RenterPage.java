package com.example.rentify;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RenterPage extends AppCompatActivity {
    private EditText editTextSearch;
    private Button buttonSearch;
    private ListView listViewSearchResults;

    private DatabaseReference databaseItems;
    private List<Items> allItems;
    private List<Items> filteredItems;
    private ItemRequestList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentor_request);

        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
        listViewSearchResults = findViewById(R.id.listViewSearchResults);
        databaseItems = FirebaseDatabase.getInstance().getReference("items");
        allItems = new ArrayList<>();
        filteredItems = new ArrayList<>();
        loadItemsFromFirebase();

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch(editTextSearch.getText().toString().trim());
            }
        });

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearch(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadItemsFromFirebase() {
        databaseItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allItems.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Items item = itemSnapshot.getValue(Items.class);
                    if (item != null) {
                        allItems.add(item);
                    }
                }

                filteredItems.addAll(allItems);
                adapter = new ItemRequestList(RenterPage.this, filteredItems);
                listViewSearchResults.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RenterPage.this, "Failed to load items: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void performSearch(String query) {
        if (query.isEmpty()) {
            filteredItems.clear();
            filteredItems.addAll(allItems);
        } else {
            filteredItems.clear();
            for (Items item : allItems) {
                if (item.getItemName().toLowerCase().contains(query.toLowerCase()) ||
                        item.getItemCategory().toLowerCase().contains(query.toLowerCase())) {
                    filteredItems.add(item);
                }
            }
        }

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}