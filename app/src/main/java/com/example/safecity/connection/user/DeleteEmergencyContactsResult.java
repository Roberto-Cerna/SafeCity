package com.example.safecity.connection.user;

import com.example.safecity.ui.emergency_contacts.EmergencyContact;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DeleteEmergencyContactsResult {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("emergencyContacts")
    @Expose
    private ArrayList<EmergencyContact> emergencyContacts;
    @SerializedName("err")
    @Expose
    private String err;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<EmergencyContact> getEmergencyContacts() {
        return emergencyContacts;
    }

    public void setEmergencyContacts(ArrayList<EmergencyContact> emergencyContacts) {
        this.emergencyContacts = emergencyContacts;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }
}
