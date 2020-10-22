package com.mindyourlovedone.healthcare.Appointment.Model;

public class AppointmentListPojo {

    private String month;
    private String day;
    private String year;
    private String doctorName;
    private String status;
    private String diagnosisType;
    private String visitingStatus;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDiagnosisType() {
        return diagnosisType;
    }

    public void setDiagnosisType(String diagnosisType) {
        this.diagnosisType = diagnosisType;
    }

    public String getVisitingStatus() {
        return visitingStatus;
    }

    public void setVisitingStatus(String visitingStatus) {
        this.visitingStatus = visitingStatus;
    }
}
