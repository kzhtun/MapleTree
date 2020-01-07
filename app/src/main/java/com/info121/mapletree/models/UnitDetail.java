package com.info121.mapletree.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnitDetail {
    @SerializedName("block")
    @Expose
    public String block;
    @SerializedName("cnt")
    @Expose
    public String cnt;
    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("level")
    @Expose
    public String level;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("unit")
    @Expose
    public String unit;

    public String progress;

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
}
