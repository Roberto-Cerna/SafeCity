package com.example.safecity.ui.emergency_contacts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.safecity.R;
import com.example.safecity.data.user.User;

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

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView emergencyContactListItemName = convertView.findViewById(R.id.reportListItemName);
        TextView emergencyContactListItemPhone = convertView.findViewById(R.id.reportListItemType);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
        checkBox.setTag(position);

        emergencyContactListItemName.setText(name);
        emergencyContactListItemPhone.setText(phone);

        if(EmergencyContactsFragment.isActionMode) {
            checkBox.setVisibility(View.VISIBLE);
        }
        else {
            checkBox.setVisibility(View.GONE);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position = (int) buttonView.getTag();

                if(EmergencyContactsFragment.contactPhonesSelection.contains(User.emergencyContacts.get(position).phone)) {
                    EmergencyContactsFragment.contactPhonesSelection.remove(User.emergencyContacts.get(position).phone);
                }
                else {
                    EmergencyContactsFragment.contactPhonesSelection.add(User.emergencyContacts.get(position).phone);
                }
                EmergencyContactsFragment.actionMode.setTitle(EmergencyContactsFragment.contactPhonesSelection.size() + " contacto(s) seleccionado(s)");

            }
        });

        return convertView;
    }
}
