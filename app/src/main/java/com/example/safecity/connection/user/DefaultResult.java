package com.example.safecity.connection.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DefaultResult {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("err")
    @Expose
    private String err;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }
}
