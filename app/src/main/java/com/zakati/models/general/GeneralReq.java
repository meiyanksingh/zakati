package com.zakati.models.general;

/**
 * Created by priya on 12/5/16.
 */
public class GeneralReq {

    String userId, phone,otp;

    float totalAmount;

    String isAvailable;

    public String getIsAvailable() {
        return isAvailable;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public GeneralReq(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
