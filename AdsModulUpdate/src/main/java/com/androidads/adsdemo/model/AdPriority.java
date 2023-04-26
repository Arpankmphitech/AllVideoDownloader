package com.androidads.adsdemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdPriority {
    @SerializedName("priority")
    @Expose
    String priority;

    @SerializedName("F")
    @Expose
    F f;

    @SerializedName("G")
    @Expose
    G g;

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public F getF() {
        return f;
    }

    public void setF(F f) {
        this.f = f;
    }

    public G getG() {
        return g;
    }

    public void setG(G g) {
        this.g = g;
    }
}