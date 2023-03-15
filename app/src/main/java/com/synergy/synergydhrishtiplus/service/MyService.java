package com.synergy.synergydhrishtiplus.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.koushikdutta.async.AsyncSocket;
import com.synergy.synergydhrishtiplus.SynergyApplicationClass;
import com.synergy.synergydhrishtiplus.data_model.NozzleModel;
import com.synergy.synergydhrishtiplus.server_pack.readable.TokheiumStatus;
import com.synergy.synergydhrishtiplus.server_pack.writable.CommandQueue;
import com.synergy.synergydhrishtiplus.server_pack.writable.Commands;
import com.synergy.synergydhrishtiplus.server_pack.writable.OnAllCommandCompleted;
import com.synergy.synergydhrishtiplus.server_pack.writable.OnSingleCommandCompleted;
//import com.synergy.synergydhrishtiplus.server_pack.writable.WriteCommandUtil3;
//import com.synergy.synergydhrishtiplus.server_pack.writable.WriteCommandUtils;
import com.synergy.synergydhrishtiplus.server_pack.writable.WriteCommandUtil3;
import com.synergy.synergydhrishtiplus.utils.Utils;
import com.synergy.synergydhrishtiplus.utils.VolumeCalculation;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyService extends Service {

    int notify = 1000;
    ScheduledExecutorService scheduledExecutorService;
    HashMap<Integer, AsyncSocket> socketStore;
    String port,pump_id,nozzleid;
    AsyncSocket sockets;

    AsyncSocket socket[];

    @Override
    public void onCreate() {
        super.onCreate();
        socket=new AsyncSocket[20];
        scheduledExecutorService = Executors.newScheduledThreadPool(10);
        Log.d("Service","OnCreate called");
    }

    @Nullable


    @Override
    public boolean onUnbind(Intent intent) {
        //Stop ScheduledExecutorService
//        myschedule_executor.shutdown();
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle b=       intent.getExtras();
        List<NozzleModel> tokheumList= (List<NozzleModel>) b.get("tokheum");
        pump_id=tokheumList.get(3).getPumpId();
        port = tokheumList.get(3).getPort();
        nozzleid=tokheumList.get(3).getNozzleId();
        Log.d("tokheiumList",""+tokheumList.get(2).getPort());
        socketStore=((SynergyApplicationClass)getApplication()).getSocketHashMap();
        sockets = Utils.getValueByKey(socketStore, Integer.parseInt(port));
        scheduledExecutorService.scheduleAtFixedRate(new KStatus(sockets),0,notify, TimeUnit.MILLISECONDS);
        return START_NOT_STICKY;
    }

    class KStatus implements Runnable {
        AsyncSocket socket1;

        public KStatus(AsyncSocket socket) {
            socket1 = socket;
        }

        @Override
        public void run() {
            String code;
            String binaryString=  "010"+Utils.intToBinary(1,5);
            int decimal = Integer.parseInt(binaryString,2);
            String pump_hexStr = Integer.toString(decimal,16);
            Log.d("hex",pump_hexStr);
            code="0"+nozzleid+pump_hexStr+Commands.statusPoll+"7f";
            String checkSumCommand = code + Utils.calculateCheckSum((Utils.convertToAscii(code)).getBytes());
            WriteCommandUtil3 writeCommandUtils = new WriteCommandUtil3(checkSumCommand, socket1, new OnSingleCommandCompleted() {
                @Override
                public void onSingleCommandCompleted(String currentCommand, String response, AsyncSocket socket) {
                    if (checkSumCommand == currentCommand) {
                        if (response != null) {
                            try {
                                String status = TokheiumStatus.getPollState(response);
                                Log.d("value1", status);
                                Intent local = new Intent();
                                local.setAction("ToGetTokheiumStatus3");
                                local.putExtra("Status", status);
                                local.putExtra("Port", Utils.getKeyByValue(socketStore, socket));
                                sendBroadcast(local);
                            } catch (Exception e) {
                                Log.d("exception", e.getLocalizedMessage());
                            }


                        }

                    }

                }
            });
            writeCommandUtils.doCommandChaining();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scheduledExecutorService.shutdown();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
