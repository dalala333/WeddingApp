package com.example.wedding_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<String> {

    private final int resourceLayout;
    private final Context mContext;

    public ItemAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(resourceLayout, null);
        }

        String item = getItem(position);

        if (item != null) {
            TextView textView = view.findViewById(R.id.itemTextView); // Ensure this matches your XML
            textView.setText(item);
        }

        return view;
    }
}
