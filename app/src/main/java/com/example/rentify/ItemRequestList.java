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

public class ItemRequestList extends ArrayAdapter<Items> {
    private Activity context;
    List<Items> items;

    public ItemRequestList(Activity context, List<Items> items) {
        super(context, R.layout.layout_item_request, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_item_request, null, true);

        TextView textViewName = listViewItem.findViewById(R.id.nameItem);
        TextView textViewTime = listViewItem.findViewById(R.id.timeItem);
        TextView textViewFee = listViewItem.findViewById(R.id.feeItem);
        Button buttonRequest = listViewItem.findViewById(R.id.button14);

        // Get the item at the current position
        Items item = items.get(position);
        textViewName.setText(item.getItemName());
        textViewTime.setText(item.getItemTime() + " hours");
        textViewFee.setText("$" + item.getItemFee());

        buttonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseRequests = FirebaseDatabase.getInstance().getReference("lessor_requests");


                databaseRequests.child(item.getId()).setValue(item).addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Item Requested", Toast.LENGTH_SHORT).show();

                    DatabaseReference databaseItems = FirebaseDatabase.getInstance().getReference("items").child(item.getId());
                    databaseItems.removeValue().addOnSuccessListener(aVoid1 -> {
                        items.remove(position);
                        notifyDataSetChanged();
                    }).addOnFailureListener(e -> {
                        Toast.makeText(context, "Failed to remove item from items: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }).addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to request item: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });

        return listViewItem;
    }
}
