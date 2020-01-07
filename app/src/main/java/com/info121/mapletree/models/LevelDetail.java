package com.info121.mapletree.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LevelDetail {

    @SerializedName("level")
    @Expose
    public String level;
    @SerializedName("levelsort")
    @Expose
    public String levelsort;


    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevelsort() {
        return levelsort;
    }

    public void setLevelsort(String levelsort) {
        this.levelsort = levelsort;
    }
}
