package com.example.rentify;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CategoryList extends ArrayAdapter<CategoryEx> {
    private Activity context;
    List<CategoryEx> categories;

    public CategoryList(Activity context, List<CategoryEx> categories) {
        super(context, R.layout.layout_category_list, categories);
        this.context = context;
        this.categories = categories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_category_list, null, true);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textViewName = (TextView) listViewItem.findViewById(R.id.textName);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.textDescription);

        CategoryEx category = categories.get(position);
        textViewName.setText(category.getCategoryName());
        textViewPrice.setText(String.valueOf(category.getDescription()));
        return listViewItem;
    }
}

