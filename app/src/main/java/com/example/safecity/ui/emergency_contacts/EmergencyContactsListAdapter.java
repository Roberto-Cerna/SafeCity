package com.example.safecity.ui.emergency_contacts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.safecity.R;

import java.util.ArrayList;

public class EmergencyContactsListAdapter extends ArrayAdapter<EmergencyContact> {

    private Context mContext;
    int mResource;

    public EmergencyContactsListAdapter(@NonNull Context context, int resource,
                                        @NonNull ArrayList<EmergencyContact> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).name;
        String phone = getItem(position).phone;

        EmergencyContact emergencyContact = new EmergencyContact(name, phone);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView emergencyContactListItemName = convertView.findViewById(R.id.emergencyContactListItemName);
        TextView emergencyContactListItemPhone = convertView.findViewById(R.id.emergencyContactListItemPhone);

        emergencyContactListItemName.setText(name);
        emergencyContactListItemPhone.setText(phone);

        return convertView;
    }
}
