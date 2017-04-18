package com.dizzwave.remindful;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.ArrayList;

/**
 * Created by Dave on 4/17/2017.
 */

public class TrashListItemAdapter extends ArrayAdapter<String> {

    public TrashListItemAdapter(Context context, String[] messages) {
            super(context, 0, messages);
        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String message = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trashable_list_item, parent, false);
        }
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup row = (ViewGroup) v.getParent();
                BootstrapButton view = (BootstrapButton) row.findViewById(R.id.message);
                Log.d("bb",view.getText().toString());
            }
        };
        View.OnClickListener listener2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup row = (ViewGroup) v.getParent();
                BootstrapButton view = (BootstrapButton) row.findViewById(R.id.message);
                Log.d("trash",view.getText().toString());
            }
        };
        // Lookup view for data population
        BootstrapButton bb = (BootstrapButton) convertView.findViewById(R.id.message);
        // Populate the data into the template view using the data object
        bb.setText(message.toString());
        bb.setOnClickListener(listener);

        BootstrapButton cc = (BootstrapButton) convertView.findViewById(R.id.check);
        cc.setOnClickListener(listener);

        BootstrapButton tt = (BootstrapButton) convertView.findViewById(R.id.trash);
        tt.setOnClickListener(listener2);
        // Return the completed view to render on screen
        return convertView;
    }
}

