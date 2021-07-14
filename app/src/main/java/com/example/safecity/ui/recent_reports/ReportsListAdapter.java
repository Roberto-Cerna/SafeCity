package com.example.safecity.ui.recent_reports;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.safecity.R;
import com.example.safecity.data.reports_list.Report;

import java.util.ArrayList;

public class ReportsListAdapter extends ArrayAdapter<Report> {

    private Context mContext;
    int mResource;

    public ReportsListAdapter(@NonNull Context context, int resource,
                                        @NonNull ArrayList<Report> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).victim;
        String type = getItem(position).incident;
        //boolean isSeen = getItem(position).isSeen;
        String time = Double.toString(getItem(position).daysAgo);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView reportListItemName = convertView.findViewById(R.id.reportListItemName);
        TextView reportListItemType = convertView.findViewById(R.id.reportListItemType);
        ImageView reportListItemIcon = convertView.findViewById(R.id.reportListItemIcon);
        TextView reportListItemTime = convertView.findViewById(R.id.reportListItemTime);

        reportListItemName.setText(name);
        reportListItemType.setText(type);
        //reportListItemIcon.setVisibility((isSeen) ? View.INVISIBLE : View.VISIBLE);
        reportListItemTime.setText(time);

        return convertView;
    }
}
