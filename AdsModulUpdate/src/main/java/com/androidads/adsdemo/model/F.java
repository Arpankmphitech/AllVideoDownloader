package com.androidads.adsdemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class F {
    @SerializedName("native_ad2")
    @Expose
    String n2;

    @SerializedName("B")
    @Expose
    String b;

    public String getN2() {
        return n2;
    }

    public void setN2(String n2) {
        this.n2 = n2;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getNb() {
        return nb;
    }

    public void setNb(String nb) {
        this.nb = nb;
    }

    public String getSnb() {
        return snb;
    }

    public void setSnb(String snb) {
        this.snb = snb;
    }

    public String getRv() {
        return rv;
    }

    public void setRv(String rv) {
        this.rv = rv;
    }

    public String getRvi() {
        return rvi;
    }

    public void setRvi(String rvi) {
        this.rvi = rvi;
    }

    public String getIg() {
        return ig;
    }

    public void setIg(String ig) {
        this.ig = ig;
    }

    @SerializedName("interstitial_ad")
    @Expose
    String i;

    @SerializedName("native_ad")
    @Expose
    String n;

    @SerializedName("native_banner_ad")
    @Expose
    String nb;

    @SerializedName("native_banner_ad2")
    @Expose
    String nb2;

    public String getNb2() {
        return nb2;
    }

    public void setNb2(String nb2) {
        this.nb2 = nb2;
    }

    @SerializedName("small_native_banner_ad")
    @Expose
    String snb;

    @SerializedName("RV")
    @Expose
    String rv;

    @SerializedName("RVI")
    @Expose
    String rvi;

    @SerializedName("IG")
    @Expose
    String ig;
}