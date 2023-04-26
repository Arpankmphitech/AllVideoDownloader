package com.androidads.adsdemo.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class SplashCommonModel implements Serializable {

    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("F")
    @Expose
    private SplashCommonModel f;
    @SerializedName("G")
    @Expose
    private SplashCommonModel g;

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public SplashCommonModel getF() {
        return f;
    }

    public void setF(SplashCommonModel f) {
        this.f = f;
    }

    public SplashCommonModel getG() {
        return g;
    }

    public void setG(SplashCommonModel g) {
        this.g = g;
    }

    @SerializedName("home")
    @Expose
    private ArrayList<SplashCommonModel> home = null;
    @SerializedName("exit")
    @Expose
    private ArrayList<SplashCommonModel> exit = null;
    @SerializedName("is_rate_dialog")
    @Expose
    private Boolean isRateDialog;
    @SerializedName("is_user_dialog")
    @Expose
    private Boolean isUserDialog;
    @SerializedName("user_dialog_img")
    @Expose
    private String userDialogImg;
    @SerializedName("user_dialog_text")
    @Expose
    private String userDialogText;
    @SerializedName("user_dialog_url")
    @Expose
    private String userDialogUrl;
    @SerializedName("app_version_code")
    @Expose
    private String appVersionCode;
    @SerializedName("developer_name")
    @Expose
    private String developer_name;
    @SerializedName("bitcoin_url")
    @Expose
    private String bitcoinUrl;
    @SerializedName("predchamp_url")
    @Expose
    private String predchampUrl;
    @SerializedName("game_url")
    @Expose
    private String gameUrl;
    @SerializedName("back_press_count")
    @Expose
    private String backPressCount;
    @SerializedName("normal_ad_count")
    @Expose
    private String normalAdCount;
    @SerializedName("info_scr_avail")
    @Expose
    private String infoScrAvail;

    public ArrayList<SplashCommonModel> getHome() {
        return home;
    }

    public void setHome(ArrayList<SplashCommonModel> home) {
        this.home = home;
    }

    public ArrayList<SplashCommonModel> getExit() {
        return exit;
    }

    public void setExit(ArrayList<SplashCommonModel> exit) {
        this.exit = exit;
    }

    public Boolean getIsRateDialog() {
        return isRateDialog;
    }

    public void setIsRateDialog(Boolean isRateDialog) {
        this.isRateDialog = isRateDialog;
    }

    public Boolean getIsUserDialog() {
        return isUserDialog;
    }

    public void setIsUserDialog(Boolean isUserDialog) {
        this.isUserDialog = isUserDialog;
    }

    public String getUserDialogImg() {
        return userDialogImg;
    }

    public void setUserDialogImg(String userDialogImg) {
        this.userDialogImg = userDialogImg;
    }

    public String getUserDialogText() {
        return userDialogText;
    }

    public void setUserDialogText(String userDialogText) {
        this.userDialogText = userDialogText;
    }

    public String getUserDialogUrl() {
        return userDialogUrl;
    }

    public void setUserDialogUrl(String userDialogUrl) {
        this.userDialogUrl = userDialogUrl;
    }

    public String getAppVersionCode() {
        return appVersionCode;
    }

    public void setAppVersionCode(String appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public String getDeveloper_name() {
        return developer_name;
    }

    public void setDeveloper_name(String developer_name) {
        this.developer_name = developer_name;
    }

    public String getBitcoinUrl() {
        return bitcoinUrl;
    }

    public void setBitcoinUrl(String bitcoinUrl) {
        this.bitcoinUrl = bitcoinUrl;
    }

    public String getPredchampUrl() {
        return predchampUrl;
    }

    public void setPredchampUrl(String predchampUrl) {
        this.predchampUrl = predchampUrl;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public void setGameUrl(String gameUrl) {
        this.gameUrl = gameUrl;
    }

    public String getBackPressCount() {
        return backPressCount;
    }

    public void setBackPressCount(String backPressCount) {
        this.backPressCount = backPressCount;
    }

    public String getNormalAdCount() {
        return normalAdCount;
    }

    public void setNormalAdCount(String normalAdCount) {
        this.normalAdCount = normalAdCount;
    }

    public String getInfoScrAvail() {
        return infoScrAvail;
    }

    public void setInfoScrAvail(String infoScrAvail) {
        this.infoScrAvail = infoScrAvail;
    }

    @SerializedName("app_name")
    @Expose
    private String appName;
    @SerializedName("app_url")
    @Expose
    private String appUrl;
    @SerializedName("app_img")
    @Expose
    private String appImg;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getAppImg() {
        return appImg;
    }

    public void setAppImg(String appImg) {
        this.appImg = appImg;
    }

    @SerializedName("B")
    @Expose
    private String b;
    @SerializedName("SNB")
    @Expose
    private String snb;
    @SerializedName("N")
    @Expose
    private String n;

    @SerializedName("N2")
    @Expose
    private String n2;

    public String getN2() {
        return n2;
    }

    public void setN2(String n2) {
        this.n2 = n2;
    }

    @SerializedName("NB")
    @Expose
    private String nb;
    @SerializedName("I")
    @Expose
    private String i;
    @SerializedName("AO")
    @Expose
    private String ao;

    @SerializedName("RV")
    @Expose
    private String rv;

    @SerializedName("RVI")
    @Expose
    private String rvi;

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getSnb() {
        return snb;
    }

    public void setSnb(String snb) {
        this.snb = snb;
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

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public String getAo() {
        return ao;
    }

    public void setAo(String ao) {
        this.ao = ao;
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

    @SerializedName("acc_type")
    @Expose
    private String AccountType;
    @SerializedName("add_type")
    @Expose
    private String addType;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("add_size")
    @Expose
    private String addSize;
    @SerializedName("acc_id")
    @Expose
    private String accountNo;

    public String getAccountType() {
        return AccountType;
    }

    public void setAccountType(String AccountType) {
        this.AccountType = AccountType;
    }

    public String getAddType() {
        return addType;
    }

    public void setAddType(String addType) {
        this.addType = addType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddSize() {
        return addSize;
    }

    public void setAddSize(String addSize) {
        this.addSize = addSize;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

}
