package com.synergy.synergydhrishtiplus.data_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PresetModal{
    @SerializedName("data")
    @Expose
    private PresetData data;

    public PresetData getData() {
        return data;
    }
    public void setData(PresetData data) {
        this.data = data;
    }
}
