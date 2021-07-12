package com.example.safecity.connection.user;

import com.google.gson.annotations.SerializedName;

public class UpdateProfileBody {
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;

    public UpdateProfileBody(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}
