package com.synergy.synergydhrishtiplus.server_pack.writable;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.koushikdutta.async.AsyncSocket;
import com.synergy.synergydhrishtiplus.data_model.PumpHardwareInfoResponse;
import com.synergy.synergydhrishtiplus.server_pack.readable.PollStatus;
import com.synergy.synergydhrishtiplus.utils.VolumeCalculation;

import java.net.Socket;
import java.util.ArrayList;

public class VolumeLiveData  extends ViewModel {
    private MutableLiveData<ArrayList<Float>> atgData;
    ArrayList<Float> volumes;


    public MutableLiveData<ArrayList<Float>> getAtgDataObserver() {
        if (atgData == null) {
            atgData = new MutableLiveData<ArrayList<Float>>();
            volumes=new ArrayList<>();


        }

        return atgData;
    }

    public void getVolume(AsyncSocket socket, PumpHardwareInfoResponse.Data.TankDatum data){
        String dipchart=data.getDipChartData();
        final String[] presetCompletedStateCommand2 = new String[]{Commands.READ_ATGLEVEL_PROGAGE.toString()};
        new CommandQueue(presetCompletedStateCommand2,socket, new OnAllCommandCompleted() {
            @Override
            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                Log.d("CommandInQueue11",""+isEmpty);
                Log.d("CommandInQueue12",""+lastResponse);


            }

            @Override
            public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                try {
                    Log.d("CommandInQueue13",""+currentCommand);
                    Log.d("CommandInQueue14",""+response);
//                    Float volume=VolumeCalculation.getAtgState("597",data);
//                    Log.d("volumes",""+volume);
//                    volumes.add(volume);
//                    atgData.postValue(volumes);



                } catch (Exception e) {
                }
            }


        }).doCommandChaining();


    }

}
