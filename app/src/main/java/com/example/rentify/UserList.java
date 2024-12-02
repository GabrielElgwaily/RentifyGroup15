package com.example.rentify;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import java.util.List;

public class UserList extends ArrayAdapter<String> {
    private Activity context;
    private List<String> items;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable saveRunnable;

    public UserList(Activity context, List<String> items) {
        super(context, R.layout.users_to_manage, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.users_to_manage, null, true);

        EditText editTextName = listViewItem.findViewById(R.id.user_name);
        ImageView deleteIcon = listViewItem.findViewById(R.id.user_delete_icon);

        String originalUsername = items.get(position);
        editTextName.setHint(originalUsername);

        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String newUsername = editable.toString().trim();

                if (saveRunnable != null) {
                    handler.removeCallbacks(saveRunnable);
                }

                saveRunnable = () -> {
                if (!newUsername.isEmpty() && !newUsername.equals(originalUsername)) {
                    if (MainActivity.rentorDatabase.containsKey(originalUsername)) {
                        String value = MainActivity.rentorDatabase.remove(originalUsername);
                        MainActivity.rentorDatabase.put(newUsername, value);
                    } else if (MainActivity.lessorDatabase.containsKey(originalUsername)) {
                        String value = MainActivity.lessorDatabase.remove(originalUsername);
                        MainActivity.lessorDatabase.put(newUsername, value);
                    }
                    items.set(position, newUsername);
                    notifyDataSetChanged();
                }
                };
                handler.postDelayed(saveRunnable, 500);
            }
        });

        deleteIcon.setOnClickListener(v -> {
            items.remove(position);
            MainActivity.rentorDatabase.remove(originalUsername);
            MainActivity.lessorDatabase.remove(originalUsername);
            notifyDataSetChanged();
        });

        return listViewItem;
    }
}
