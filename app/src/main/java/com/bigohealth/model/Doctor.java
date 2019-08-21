package com.bigohealth.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "doctor_data")
public class Doctor {

    @SerializedName("doc_id")
    @Expose
    @NonNull
    @PrimaryKey
    private String docId;

    @SerializedName("doc_img_url")
    @Expose
    private String docImgUrl;

    @SerializedName("experience")
    @Expose
    private Integer experience;

    @SerializedName("qualification")
    @Expose
    private String qualification;

    @SerializedName("pincode")
    @Expose
    private Integer pincode;

    @SerializedName("doc_fee")
    @Expose
    private Integer docFee;

    @SerializedName("from_hospital")
    @Expose
    private Integer fromHospital;

    @SerializedName("hospital_id")
    @Expose
    private String hospitalId;

    @SerializedName("rank")
    @Expose
    private Integer rank;

    @SerializedName("book_appt")
    @Expose
    private Integer bookAppt;

    @SerializedName("doc_discounted_fee")
    @Expose
    private String docDiscountedFee;

    @SerializedName("doc_online_pay_fee")
    @Expose
    private String docOnlinePayFee;

    @SerializedName("language_id")
    @Expose
    private Integer languageId;

    @SerializedName("doc_firstname")
    @Expose
    private String docFirstname;

    @SerializedName("doc_middlename")
    @Expose
    private String docMiddlename;

    @SerializedName("doc_lastname")
    @Expose
    private String docLastname;

    @SerializedName("specialisation")
    @Expose
    private String specialisation;

    @SerializedName("address_line1")
    @Expose
    private String addressLine1;

    @SerializedName("address_line2")
    @Expose
    private String addressLine2;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("state")
    @Expose
    private String state;

    @SerializedName("landmark")
    @Expose
    private String landmark;

    @SerializedName("general_slot")
    @Expose
    private String generalSlot;

    @NonNull
    public String getDocId() {
        return docId;
    }

    public void setDocId(@NonNull String docId) {
        this.docId = docId;
    }

    public String getDocImgUrl() {
        return docImgUrl;
    }

    public void setDocImgUrl(String docImgUrl) {
        this.docImgUrl = docImgUrl;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public Integer getDocFee() {
        return docFee;
    }

    public void setDocFee(Integer docFee) {
        this.docFee = docFee;
    }

    public Integer getFromHospital() {
        return fromHospital;
    }

    public void setFromHospital(Integer fromHospital) {
        this.fromHospital = fromHospital;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getBookAppt() {
        return bookAppt;
    }

    public void setBookAppt(Integer bookAppt) {
        this.bookAppt = bookAppt;
    }

    public String getDocDiscountedFee() {
        return docDiscountedFee;
    }

    public void setDocDiscountedFee(String docDiscountedFee) {
        this.docDiscountedFee = docDiscountedFee;
    }

    public String getDocOnlinePayFee() {
        return docOnlinePayFee;
    }

    public void setDocOnlinePayFee(String docOnlinePayFee) {
        this.docOnlinePayFee = docOnlinePayFee;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public String getDocFirstname() {
        return docFirstname;
    }

    public void setDocFirstname(String docFirstname) {
        this.docFirstname = docFirstname;
    }

    public String getDocMiddlename() {
        return docMiddlename;
    }

    public void setDocMiddlename(String docMiddlename) {
        this.docMiddlename = docMiddlename;
    }

    public String getDocLastname() {
        return docLastname;
    }

    public void setDocLastname(String docLastname) {
        this.docLastname = docLastname;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getGeneralSlot() {
        return generalSlot;
    }

    public void setGeneralSlot(String generalSlot) {
        this.generalSlot = generalSlot;
    }
}
