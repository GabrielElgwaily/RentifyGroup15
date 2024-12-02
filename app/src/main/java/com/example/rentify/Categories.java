package com.example.rentify;

import android.os.Bundle;


import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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

public class Categories extends AppCompatActivity {

    EditText editTextName;
    EditText editTextDescription;
    Button buttonAddCategory;
    ListView listViewCategory;

    List<CategoryEx> categories;

    DatabaseReference databaseCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseApp.initializeApp(this);

        databaseCategories = FirebaseDatabase.getInstance().getReference("categories");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        listViewCategory = (ListView) findViewById(R.id.listViewCategory);
        buttonAddCategory = (Button) findViewById(R.id.addButton);

        categories = new ArrayList<>();

        buttonAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCategory();
            }
        });

        listViewCategory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                CategoryEx categoryEx = categories.get(i);
                showUpdateDeleteDialog(categoryEx.getId(), categoryEx.getCategoryName());
                return true;
            }
        });
    }

protected void onStart() {
    super.onStart();

    databaseCategories.addValueEventListener(new ValueEventListener() {

        public void onDataChange(DataSnapshot dataSnapshot) {
            categories.clear();

            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                CategoryEx categoryEx = postSnapshot.getValue(CategoryEx.class);

                categories.add(categoryEx);
            }

           CategoryList productsAdapter = new CategoryList(Categories.this, categories);

            listViewCategory.setAdapter(productsAdapter);

        }

        public void onCancelled(DatabaseError databaseError) {

            Toast.makeText(Categories.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();

        }
    });


}


private void showUpdateDeleteDialog(final String categoryId, String name) {

    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
    LayoutInflater inflater = getLayoutInflater();
    final View dialogView = inflater.inflate(R.layout.layout_update_category, null);
    dialogBuilder.setView(dialogView);

    final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName2);
    final EditText editTextDescription = (EditText) dialogView.findViewById(R.id.editTextDescription2);
    final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateCategory);
    final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteCategory);

    dialogBuilder.setTitle(name);
    final AlertDialog b = dialogBuilder.create();
    b.show();

    buttonUpdate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String name = editTextName.getText().toString().trim();
            String description = editTextDescription.getText().toString().trim();
            if (!TextUtils.isEmpty(name)) {
                updateCategory(categoryId, name, description);
                b.dismiss();
            }
        }
    });

    buttonDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            deleteCategory(categoryId);
            b.dismiss();
        }
    });
}

private void updateCategory(String id, String name, String description) {

    DatabaseReference dR = FirebaseDatabase.getInstance().getReference("categories").child(id);

    CategoryEx categoryEx = new CategoryEx(id,name,description);

    dR.setValue(categoryEx);

    Toast.makeText(getApplicationContext(), "Category Updated", Toast.LENGTH_LONG).show();
}

private void deleteCategory(String id) {
    DatabaseReference dR = FirebaseDatabase.getInstance().getReference("categories").child(id);
    dR.removeValue();
    Toast.makeText(getApplicationContext(), "Category Deleted", Toast.LENGTH_LONG).show();
}

private void addCategory() {


    String name = editTextName.getText().toString().trim();

    String description = editTextDescription.getText().toString().trim();

    if (!TextUtils.isEmpty(name)) {

        String id = databaseCategories.push().getKey();

        CategoryEx category = new CategoryEx(id, name, description);

        databaseCategories.child(id).setValue(category);

        editTextName.setText("");

        editTextDescription.setText("");

        Toast.makeText(this, "Category added", Toast.LENGTH_LONG).show();

    } else {

        Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
    }
}}
