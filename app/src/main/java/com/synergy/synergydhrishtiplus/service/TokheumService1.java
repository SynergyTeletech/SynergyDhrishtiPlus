package com.synergy.synergydhrishtiplus.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.koushikdutta.async.AsyncSocket;
import com.synergy.synergydhrishtiplus.SynergyApplicationClass;
import com.synergy.synergydhrishtiplus.data_model.NozzleModel;
import com.synergy.synergydhrishtiplus.server_pack.readable.TokheiumStatus;
import com.synergy.synergydhrishtiplus.server_pack.writable.Commands;
import com.synergy.synergydhrishtiplus.server_pack.writable.OnSingleCommandCompleted;
import com.synergy.synergydhrishtiplus.server_pack.writable.WriteCommandUtil1;
import com.synergy.synergydhrishtiplus.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TokheumService1 extends Service {
    int notify = 10000;
    ScheduledExecutorService scheduledExecutorService;
    HashMap<Integer, AsyncSocket> socketStore;

    String port_no,pump_id,nozzle_id;
    AsyncSocket sockets;
    Handler handler;
    Runnable runnable;
    AsyncSocket socket[];

    @Override
    public void onCreate() {
        super.onCreate();
        socket=new AsyncSocket[20];

        Log.d("Service","OnCreate called");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle b=       intent.getExtras();
        List<NozzleModel> tokheumList= (List<NozzleModel>) b.get("tokheum");
        Log.d("tokheiumList",""+tokheumList.get(1).getPort());
        port_no = tokheumList.get(1).getPort();
        pump_id=tokheumList.get(1).getPumpId();
        nozzle_id=tokheumList.get(1).getNozzleId();
        socketStore = ((SynergyApplicationClass) getApplication()).getSocketHashMap();
        sockets = Utils.getValueByKey(socketStore, Integer.parseInt(port_no));
        socketStore=((SynergyApplicationClass)getApplication()).getSocketHashMap();

        sendStatus();
//        if(scheduledExecutorService!=null){
//            scheduledExecutorService.shutdown();
//        }
//        else {
//            scheduledExecutorService = Executors.newScheduledThreadPool(1);
//            scheduledExecutorService.scheduleAtFixedRate(new TokeamDu1Status(sockets), 0, notify, TimeUnit.MILLISECONDS);
//        }
        return START_NOT_STICKY;


    }

    private void sendStatus() {
        handler=new Handler();
        runnable =new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(runnable,1000);
                String code;
                String binaryString=  "010"+Utils.intToBinary(1,5);
                int decimal = Integer.parseInt(binaryString,2);
                String pump_hexStr = Integer.toString(decimal,16);
                Log.d("ServiceStart",pump_hexStr);
                code="0"+nozzle_id+pump_hexStr+Commands.statusPoll+"7f";
                String checkSumCommand = code + Utils.calculateCheckSum((Utils.convertToAscii(code)).getBytes());

                WriteCommandUtil1 writeCommandUtils = new WriteCommandUtil1(checkSumCommand, sockets, new OnSingleCommandCompleted() {
                    @Override
                    public void onSingleCommandCompleted(String currentCommand, String response, AsyncSocket socket) {
                        if (checkSumCommand == currentCommand) {
                            if (response != null) {
                                try {
                                    String status = TokheiumStatus.getPollState(response);
                                    Log.d("value1", status);
                                    Intent local = new Intent();
                                    local.setAction("ToGetTokheiumStatus1");
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
        };
        handler.postDelayed(runnable,1000);

    }


    class TokeamDu1Status implements Runnable {
        AsyncSocket socket1;

        public TokeamDu1Status(AsyncSocket socket) {
            socket1 = socket;
        }

        @Override
        public void run() {
            String code;
            String binaryString=  "010"+Utils.intToBinary(1,5);
            int decimal = Integer.parseInt(binaryString,2);
            String pump_hexStr = Integer.toString(decimal,16);
            Log.d("hex",pump_hexStr);
            code="0"+nozzle_id+pump_hexStr+Commands.statusPoll+"7f";
            String checkSumCommand = code + Utils.calculateCheckSum((Utils.convertToAscii(code)).getBytes());

            WriteCommandUtil1 writeCommandUtils = new WriteCommandUtil1(checkSumCommand, socket1, new OnSingleCommandCompleted() {
                @Override
                public void onSingleCommandCompleted(String currentCommand, String response, AsyncSocket socket) {
                    if (checkSumCommand == currentCommand) {
                        if (response != null) {
                            try {
                                String status = TokheiumStatus.getPollState(response);


                                Log.d("value1", status);
                                Intent local = new Intent();
                                local.setAction("ToGetTokheiumStatus1");
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(handler!=null){
            handler.removeCallbacks(runnable);
        }
//        scheduledExecutorService.shutdown();
    }
}
