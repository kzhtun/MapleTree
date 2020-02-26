package com.info121.mapletree.models;

import android.provider.ContactsContract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ObjectRes {
	@SerializedName("LevelNotCheckedDetails")
	@Expose
	public List<LevelDetail> LevelNotCheckedDetails;

	@SerializedName("LevelDetails")
	@Expose
	public List<LevelDetail> levelDetails;
	@SerializedName("ProfileDetails")
	@Expose
	public ProfileDetails profileDetails;
	@SerializedName("RoundsDetails")
	@Expose
	public List<RoundsDetails> roundsDetails = null;
	@SerializedName("UnitDetails")
	@Expose
	public List<UnitDetail> unitDetails;
	@SerializedName("jobcount")
	@Expose
	public Object jobcount;
	@SerializedName("jobcountlist")
	@Expose
	public Object jobcountlist;
	@SerializedName("jobdatelist")
	@Expose
	public Object jobdatelist;
	@SerializedName("jobdetails")
	@Expose
	public Object jobdetails;
	@SerializedName("jobs")
	@Expose
	public Object jobs;
	@SerializedName("responsemessage")
	@Expose
	public String responsemessage;
	@SerializedName("status")
	@Expose
	public String status;
	@SerializedName("token")
	@Expose
	public String token;

	@SerializedName("lastlogin")
	@Expose
	public String lastlogin;



	public List<LevelDetail> getLevelDetails() {
		return levelDetails;
	}

	public void setLevelDetails(List<LevelDetail> levelDetails) {
		this.levelDetails = levelDetails;
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

	public Object getJobcount() {
		return jobcount;
	}

	public void setJobcount(Object jobcount) {
		this.jobcount = jobcount;
	}

	public Object getJobcountlist() {
		return jobcountlist;
	}

	public void setJobcountlist(Object jobcountlist) {
		this.jobcountlist = jobcountlist;
	}

	public Object getJobdatelist() {
		return jobdatelist;
	}

	public void setJobdatelist(Object jobdatelist) {
		this.jobdatelist = jobdatelist;
	}

	public Object getJobdetails() {
		return jobdetails;
	}

	public void setJobdetails(Object jobdetails) {
		this.jobdetails = jobdetails;
	}

	public Object getJobs() {
		return jobs;
	}

	public void setJobs(Object jobs) {
		this.jobs = jobs;
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

	public String getLastlogin() {
		return lastlogin;
	}

	public void setLastlogin(String lastlogin) {
		this.lastlogin = lastlogin;
	}

	public List<LevelDetail> getLevelNotCheckedDetails() {
		return LevelNotCheckedDetails;
	}

	public void setLevelNotCheckedDetails(List<LevelDetail> levelNotCheckedDetails) {
		LevelNotCheckedDetails = levelNotCheckedDetails;
	}
}
