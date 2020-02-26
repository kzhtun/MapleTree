package com.info121.mapletree.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoundsDetails {
    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("duration")
    @Expose
    public String duration;
    @SerializedName("starttime")
    @Expose
    public String starttime;

    @SerializedName("roundinfo")
    @Expose
    public String roundinfo;


    @SerializedName("status")
    @Expose
    public String status;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoundinfo() {
        return roundinfo;
    }

    public void setRoundinfo(String roundinfo) {
        this.roundinfo = roundinfo;
    }
}
