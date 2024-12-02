package com.example.rentify;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class ItemList extends ArrayAdapter<Items> {
    private Activity context;
    List<Items> items;


    public ItemList(Activity context, List<Items> items) {
        super(context, R.layout.layout_item_list, items);
        this.context = context;
        this.items = items;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_item_list, null, true);

        TextView textViewName = listViewItem.findViewById(R.id.textView8);
        Button buttonDelete = listViewItem.findViewById(R.id.buttonDeleteItem);
        Items item = items.get(position);
        textViewName.setText(item.getItemName());
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseItems = FirebaseDatabase.getInstance().getReference("items").child(item.getId());
                databaseItems.removeValue().addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();


                    items.remove(position);
                    notifyDataSetChanged();
                }).addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to delete item", Toast.LENGTH_SHORT).show();
                });
            }
        });

        return listViewItem;
    }
}



