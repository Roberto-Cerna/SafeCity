package com.example.safecity.data.user;

import com.example.safecity.data.reports_list.Report;
import com.example.safecity.ui.emergency_contacts.EmergencyContact;

import java.util.ArrayList;
import java.util.Arrays;

public class User {
    public static String name = "Piero Violeta";
    public static String email = "piero@bajo.pe";
    public static String phone = "999888777";
    public static String id = "123";
    public static ArrayList<EmergencyContact> emergencyContacts = new ArrayList<>(Arrays.asList(
            new EmergencyContact("Edwin Yauyo", "987654321"),
            new EmergencyContact("Donald Kun", "999999999"),
            new EmergencyContact("Edwin Yauyo", "987654321")));
}
