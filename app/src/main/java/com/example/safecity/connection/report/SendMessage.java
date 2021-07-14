package com.example.safecity.connection.report;

import com.google.gson.annotations.SerializedName;

public class SendMessage {
    @SerializedName("message")
    private String message;

    public SendMessage(String message) {
        this.message = message;
    }
}
