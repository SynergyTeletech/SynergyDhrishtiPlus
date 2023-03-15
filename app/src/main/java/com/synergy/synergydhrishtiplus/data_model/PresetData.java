package com.synergy.synergydhrishtiplus.data_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PresetData {

    @SerializedName("status_code")
    @Expose
    private Integer statusCode;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("error_messages")
    @Expose
    private List<Object> errorMessages = null;
    @SerializedName("preset_quantity")
    @Expose
    private Integer presetQuantity;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Object> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<Object> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public Integer getPresetQuantity() {
        return presetQuantity;
    }

    public void setPresetQuantity(Integer presetQuantity) {
        this.presetQuantity = presetQuantity;
    }
}
