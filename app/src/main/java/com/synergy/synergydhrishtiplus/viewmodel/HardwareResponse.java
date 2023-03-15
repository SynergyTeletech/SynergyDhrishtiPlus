package com.synergy.synergydhrishtiplus.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.synergy.synergydhrishtiplus.data_model.PumpHardwareInfoResponse;

public class HardwareResponse  extends ViewModel {
    private MutableLiveData<PumpHardwareInfoResponse.Data> responseData=new MutableLiveData<PumpHardwareInfoResponse.Data>();

    public MutableLiveData<PumpHardwareInfoResponse.Data> getDataObserver() {
        if (responseData == null) {
            responseData = new MutableLiveData<PumpHardwareInfoResponse.Data>();
        }
        return responseData;
    }
    public void setResponse(PumpHardwareInfoResponse.Data data){
        responseData.setValue(data);
    }

}
