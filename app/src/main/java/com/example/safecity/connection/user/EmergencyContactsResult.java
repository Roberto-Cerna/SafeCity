package com.example.safecity.connection.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.example.safecity.ui.emergency_contacts.EmergencyContact;

import java.util.ArrayList;

public class EmergencyContactsResult {
    @SerializedName("emergencyContacts")
    @Expose
    private ArrayList<EmergencyContact> emergencyContacts;

    public ArrayList<EmergencyContact> getEmergencyContacts() {
        return emergencyContacts;
    }

    public void setEmergencyContacts(ArrayList<EmergencyContact> emergencyContacts) {
        this.emergencyContacts = emergencyContacts;
    }
}
