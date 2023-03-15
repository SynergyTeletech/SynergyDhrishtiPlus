package com.synergy.synergydhrishtiplus.network;

import com.synergy.synergydhrishtiplus.data_model.LoginResponse;
import com.synergy.synergydhrishtiplus.data_model.Postmodel;
import com.synergy.synergydhrishtiplus.data_model.PresetModal;
import com.synergy.synergydhrishtiplus.data_model.PumpHardwareInfoResponse;

import java.util.HashMap;

import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("userLogin")
    Call<LoginResponse> getLogin(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("pumpHardwareInfo")
    Call<PumpHardwareInfoResponse>getPumpHardware(@FieldMap HashMap<String,String>hashMap);
     @FormUrlEncoded
    @POST("getPreset")
    Call<PresetModal> getPresetData(@FieldMap HashMap<String,String> hashMap);

    @FormUrlEncoded
    @POST("postDispenseData")
    Call<Postmodel> postModal(@FieldMap HashMap<String,String> hashMap);
    /*  @FormUrlEncoded
    @POST("orderList")
     Call<LoginResponse> getOrder(@Field(value = AppConstants.Vehicle_id, encoded = true) String vehicleId, @Field(value = AppConstants.Status, encoded = true) String status);
      */




}
