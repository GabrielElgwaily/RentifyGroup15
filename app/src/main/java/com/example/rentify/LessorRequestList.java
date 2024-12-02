package com.example.rentify;

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

public class LessorRequestList extends ArrayAdapter<Items> {
    private Activity context;
    private List<Items> items;

    public LessorRequestList(Activity context, List<Items> items) {
        super(context, R.layout.layout_lessor_request_item, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_lessor_request_item, null, true);

        // Find views
        TextView textViewName = listViewItem.findViewById(R.id.nameItem);
        TextView textViewTime = listViewItem.findViewById(R.id.timeItem);
        TextView textViewFee = listViewItem.findViewById(R.id.feeItem);
        Button buttonAccept = listViewItem.findViewById(R.id.buttonAccept);
        Button buttonDeny = listViewItem.findViewById(R.id.buttonDeny);

        // Get the current item
        Items item = items.get(position);
        textViewName.setText(item.getItemName());
        textViewTime.setText(item.getItemTime() + " hours");
        textViewFee.setText("$" + item.getItemFee());

        // Handle Accept button click
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove the item from Firebase
                DatabaseReference databaseRequests = FirebaseDatabase.getInstance().getReference("lessor_requests").child(item.getId());
                databaseRequests.removeValue().addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Request Accepted", Toast.LENGTH_SHORT).show();
                    items.remove(position);
                    notifyDataSetChanged();
                }).addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to remove request: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });

        // Handle Deny button click
        buttonDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove the item from Firebase
                DatabaseReference databaseRequests = FirebaseDatabase.getInstance().getReference("lessor_requests").child(item.getId());
                databaseRequests.removeValue().addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Request Denied", Toast.LENGTH_SHORT).show();
                    items.remove(position);
                    notifyDataSetChanged();
                }).addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to remove request: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });

        return listViewItem;
    }
}
