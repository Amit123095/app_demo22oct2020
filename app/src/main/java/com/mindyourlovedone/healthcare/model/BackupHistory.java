package com.mindyourlovedone.healthcare.model;

public class BackupHistory {
    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    String profile="";
    public String getFileMetaData() {
        return FileMetaData;
    }

    public void setFileMetaData(String fileMetaData) {
        FileMetaData = fileMetaData;
    }

    String FileMetaData="";
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;
    String fileName="";

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    String date="";
    String type="";
    String status="";
    String reason="";
}
