package com.example.aplikasikita.model;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("resultCode")
    private int resultCode;

    @SerializedName("name")
    private String name;

    public String getStatus() {
        return status;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getName() {
        return name;
    }
}
