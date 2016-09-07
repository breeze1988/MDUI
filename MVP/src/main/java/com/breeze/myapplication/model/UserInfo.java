package com.breeze.myapplication.model;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;

public class UserInfo extends RealmObject implements RealmModel{

    public String badgeNo;

    public String depId;

    public String email;

    public String gender;

    public String jobsId;

    public int orgId;

    public String phoneNo;

    public int userStatus;

    public int userType;

    public String yn;

    public String userId;

    public String userName;

    public String userImg;

    public RealmList<StoreInfo> stores;

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getJobsId() {
        return jobsId;
    }

    public void setJobsId(String jobsId) {
        this.jobsId = jobsId;
    }

    public String getBadgeNo() {
        return badgeNo;
    }

    public void setBadgeNo(String badgeNo) {
        this.badgeNo = badgeNo;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getYn() {
        return yn;
    }

    public void setYn(String yn) {
        this.yn = yn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RealmList<StoreInfo> getStores() {
        return stores;
    }

    public void setStores(RealmList<StoreInfo> stores) {
        this.stores = stores;
    }

    public String getUserImg() {
        return userImg;

    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

}
