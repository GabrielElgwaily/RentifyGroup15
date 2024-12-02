package com.example.rentify;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddItem extends AppCompatActivity {
    EditText editTextName;
    EditText editTextFee;
    EditText editTextTime;
    EditText editTextDescription;
    AutoCompleteTextView autoTextCategory;
    Button buttonAddItem;
    ListView listViewItems;

    List<Items> items;

    DatabaseReference databaseItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);

        setContentView(R.layout.activity_add_item);

        // Initialize UI components
        editTextName = findViewById(R.id.editTextName4);
        editTextDescription = findViewById(R.id.textItemDescription2);
        autoTextCategory = findViewById(R.id.autoCompleteTextView2);
        editTextFee = findViewById(R.id.editTextFee2);
        editTextTime = findViewById(R.id.editTextTime2);
        listViewItems = findViewById(R.id.ItemListView);
        buttonAddItem = findViewById(R.id.buttonAdd);

        items = new ArrayList<>();
        databaseItems = FirebaseDatabase.getInstance().getReference("items");

        // Set click listener to add item
        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });

        // Set long click listener for updating/deleting item
        listViewItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Items item = items.get(i);
                showUpdateDeleteDialog(item.getId(), item.getItemName());
                return true;
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                items.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Items item = postSnapshot.getValue(Items.class);
                    items.add(item);
                }

                ItemList adapter = new ItemList(AddItem.this, items);
                listViewItems.setAdapter(adapter);
                listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        Items selectedItem = items.get(position);

                        editTextName.setText(selectedItem.getItemName());
                        editTextDescription.setText(selectedItem.getItemDescription());
                        autoTextCategory.setText(selectedItem.getItemCategory());
                        editTextFee.setText(String.valueOf(selectedItem.getItemFee()));
                        editTextTime.setText(String.valueOf(selectedItem.getItemTime()));
                        buttonAddItem.setText("Save Changes");
                        buttonAddItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                updateItem(selectedItem.getId(),
                                        editTextName.getText().toString().trim(),
                                        editTextDescription.getText().toString().trim(),
                                        autoTextCategory.getText().toString().trim(),
                                        Float.parseFloat(editTextFee.getText().toString().trim()),
                                        Float.parseFloat(editTextTime.getText().toString().trim()));

                                buttonAddItem.setText("Add Item");
                                resetFields();
                            }
                        });
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddItem.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



        // Fetch categories for the AutoCompleteTextView
        DatabaseReference databaseCategories = FirebaseDatabase.getInstance().getReference("categories");
        databaseCategories.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> categoryNames = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    CategoryEx category = postSnapshot.getValue(CategoryEx.class);
                    categoryNames.add(category.getCategoryName());
                }

                // Set up AutoCompleteTextView adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddItem.this, android.R.layout.simple_dropdown_item_1line, categoryNames);
                autoTextCategory.setAdapter(adapter);

                // Show dropdown when AutoCompleteTextView is clicked or gains focus
                autoTextCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        autoTextCategory.showDropDown();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddItem.this, "Error fetching categories: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void addItem() {
        String name = editTextName.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String category = autoTextCategory.getText().toString().trim();
        float fee = Float.parseFloat(editTextFee.getText().toString().trim());
        float time = Float.parseFloat(editTextTime.getText().toString().trim());

        if (!TextUtils.isEmpty(name)) {
            String id = databaseItems.push().getKey();
            Items item = new Items(id, name, description, category, fee, time);

            // Save item to Firebase
            databaseItems.child(id).setValue(item);

            // Clear input fields after adding item
            editTextName.setText("");
            editTextDescription.setText("");
            editTextFee.setText("");
            editTextTime.setText("");
            autoTextCategory.setText("");
            Toast.makeText(this, "Item added", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }

    private void showUpdateDeleteDialog(final String itemId, String itemName) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout_update_item, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = dialogView.findViewById(R.id.editTextName4);
        final EditText editTextDescription = dialogView.findViewById(R.id.textItemDescription2);
        final AutoCompleteTextView editTextCategory = dialogView.findViewById(R.id.autoCompleteTextView2);
        final EditText editTextFee = dialogView.findViewById(R.id.editTextFee2);
        final EditText editTextTime = dialogView.findViewById(R.id.editTextTime2);
        final Button buttonUpdate = dialogView.findViewById(R.id.buttonUpdateCategory2);
        final Button buttonDelete = dialogView.findViewById(R.id.buttonDeleteCategory2);

        dialogBuilder.setTitle(itemName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        // Populate fields with the item data
        editTextName.setText(itemName);
        // Set other fields if needed...

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();
                String category = editTextCategory.getText().toString().trim();
                float fee = Float.parseFloat(editTextFee.getText().toString().trim());
                float time = Float.parseFloat(editTextTime.getText().toString().trim());

                if (!TextUtils.isEmpty(name)) {
                    updateItem(itemId, name, description, category, fee, time);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(itemId);
                b.dismiss();
            }
        });
    }

    private void updateItem(String id, String name, String description, String category, float fee, float time) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(category)) {
            Toast.makeText(this, "name and category cant be empty", Toast.LENGTH_LONG).show();
            return;
        }
        Items updatedItem = new Items(id, name, description, category, fee, time);
        databaseItems.child(id).setValue(updatedItem).addOnSuccessListener(aVoid -> {
            Toast.makeText(this, "  item updated successfully", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "failed to update item " + e.getMessage(), Toast.LENGTH_LONG).show();
        });
    }


    private void deleteItem(String id) {
        databaseItems.child(id).removeValue();
    }
    private void resetFields() {
        editTextName.setText("");
        editTextDescription.setText("");
        autoTextCategory.setText("");
        editTextFee.setText("");
        editTextTime.setText("");
    }


}