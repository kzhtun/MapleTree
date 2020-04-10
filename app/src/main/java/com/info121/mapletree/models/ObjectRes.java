package com.info121.mapletree.models;

import android.provider.ContactsContract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ObjectRes {
	@SerializedName("IsUnderMaintenance")
	@Expose
	private String isUnderMaintenance;
	@SerializedName("LevelDetails")
	@Expose
	private List<LevelDetail> levelDetails;
	@SerializedName("LevelNotCheckedDetails")
	@Expose
	private List<LevelDetail> levelNotCheckedDetails;
	@SerializedName("ProfileDetails")
	@Expose
	private ProfileDetails profileDetails;
	@SerializedName("RoundsDetails")
	@Expose
	private List<RoundsDetails> roundsDetails = null;
	@SerializedName("UnitDetails")
	@Expose
	private List<UnitDetail> unitDetails;
	@SerializedName("Version")
	@Expose
	private String version;
	@SerializedName("lastlogin")
	@Expose
	private String lastlogin;
	@SerializedName("responsemessage")
	@Expose
	private String responsemessage;
	@SerializedName("status")
	@Expose
	private String status;
	@SerializedName("token")
	@Expose
	private String token;


	public List<LevelDetail> getLevelDetails() {
		return levelDetails;
	}

	public void setLevelDetails(List<LevelDetail> levelDetails) {
		this.levelDetails = levelDetails;
	}

	public List<LevelDetail> getLevelNotCheckedDetails() {
		return levelNotCheckedDetails;
	}

	public void setLevelNotCheckedDetails(List<LevelDetail> levelNotCheckedDetails) {
		this.levelNotCheckedDetails = levelNotCheckedDetails;
	}

	public ProfileDetails getProfileDetails() {
		return profileDetails;
	}

	public void setProfileDetails(ProfileDetails profileDetails) {
		this.profileDetails = profileDetails;
	}

	public List<RoundsDetails> getRoundsDetails() {
		return roundsDetails;
	}

	public void setRoundsDetails(List<RoundsDetails> roundsDetails) {
		this.roundsDetails = roundsDetails;
	}

	public List<UnitDetail> getUnitDetails() {
		return unitDetails;
	}

	public void setUnitDetails(List<UnitDetail> unitDetails) {
		this.unitDetails = unitDetails;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLastlogin() {
		return lastlogin;
	}

	public void setLastlogin(String lastlogin) {
		this.lastlogin = lastlogin;
	}

	public String getResponsemessage() {
		return responsemessage;
	}

	public void setResponsemessage(String responsemessage) {
		this.responsemessage = responsemessage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

    public String getIsUnderMaintenance() {
        return isUnderMaintenance;
    }

    public void setIsUnderMaintenance(String isUnderMaintenance) {
        this.isUnderMaintenance = isUnderMaintenance;
    }
}
