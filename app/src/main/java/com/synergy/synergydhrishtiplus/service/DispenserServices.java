package com.synergy.synergydhrishtiplus.service;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.koushikdutta.async.AsyncSocket;
import com.synergy.synergydhrishtiplus.SynergyApplicationClass;
import com.synergy.synergydhrishtiplus.data_model.NozzleModel;
import com.synergy.synergydhrishtiplus.data_model.PumpHardwareInfoResponse;

import com.synergy.synergydhrishtiplus.server_pack.readable.PollStatus;
import com.synergy.synergydhrishtiplus.server_pack.writable.CommandQueue;
import com.synergy.synergydhrishtiplus.server_pack.writable.Commands;
import com.synergy.synergydhrishtiplus.server_pack.writable.OnAllCommandCompleted;
import com.synergy.synergydhrishtiplus.server_pack.writable.OnSingleCommandCompleted;
import com.synergy.synergydhrishtiplus.server_pack.writable.WriteCommandUtil;
//import com.synergy.synergydhrishtiplus.server_pack.writable.WriteCommandUtils;
import com.synergy.synergydhrishtiplus.utils.Utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DispenserServices extends Service {
    int notify = 800;
    ScheduledExecutorService scheduledExecutorService;
    HashMap<Integer, AsyncSocket> socketStore;

    AsyncSocket socket[];
    public DispenserServices() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scheduledExecutorService.shutdownNow();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        socket=new AsyncSocket[20];
        scheduledExecutorService = Executors.newScheduledThreadPool(10);


        Log.d("Service","OnCreate called");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle b=       intent.getExtras();
        List<NozzleModel> gvrList= (List<NozzleModel>) b.get("gvr");
        socketStore=((SynergyApplicationClass)getApplication()).getSocketHashMap();

        int i=0;
        for (Map.Entry<Integer,AsyncSocket> entry : socketStore.entrySet()){
            if(entry.getValue()!=null){
                socket[i]=entry.getValue();
                i++;
            }
        }

        scheduledExecutorService.scheduleAtFixedRate(new GvrDuStatus(socket), 0, notify, TimeUnit.MILLISECONDS);


        return START_NOT_STICKY;


    }

    class GvrDuStatus implements Runnable {
        AsyncSocket[] socket1;
        public GvrDuStatus(AsyncSocket[] socket) {
            socket1=socket;
        }
        @Override
        public void run() {
            final String refresh;
            refresh = Commands.get_status_pollg.toString();
            if(socket1[0]!=null) {
                WriteCommandUtil writeCommandUtils=new WriteCommandUtil(refresh, socket1[0], new OnSingleCommandCompleted() {
                    @Override
                    public void onSingleCommandCompleted(String currentCommand, String response, AsyncSocket socket) {
                        if (response != null) {
                            String status = PollStatus.getPollState(response);
                            Log.d("value", status);
                            Intent local = new Intent();
                            local.setAction("ToGetGvrStatus");
                            local.putExtra("Status", status);
                            local.putExtra("Port", Utils.getKeyByValue(socketStore, socket));
                            sendBroadcast(local);
                        }
                    }
                });
                writeCommandUtils.doCommandChaining();
            }

//               if(socket1[1]!=null) {
//                   WriteCommandUtil writeCommandUtils=new WriteCommandUtil(refresh, socket1[1], new OnSingleCommandCompleted() {
//                       @Override
//                       public void onSingleCommandCompleted(String currentCommand, String response, AsyncSocket socket) {
//                           if (response != null) {
//
//                               String status = PollStatus.getPollState(response);
//                               Log.d("value", status);
//                               Intent local = new Intent();
//                               local.setAction("ToGetGvrStatus");
//                               local.putExtra("Status", status);
//                               sendBroadcast(local);
//                           }
//                       }
//                   });
//                   writeCommandUtils.doCommandChaining();
//               }
//            if(socket1[2]!=null) {
//                WriteCommandUtil writeCommandUtils=new WriteCommandUtil(refresh, socket1[2], new OnSingleCommandCompleted() {
//                    @Override
//                    public void onSingleCommandCompleted(String currentCommand, String response, AsyncSocket socket) {
//                        if (response != null) {
//
//                            String status = PollStatus.getPollState(response);
//                            Log.d("value", status);
//                            Intent local = new Intent();
//                            local.setAction("ToGetGvrStatus");
//                            local.putExtra("Status", status);
//                            sendBroadcast(local);
//                        }
//                    }
//                });
//                writeCommandUtils.doCommandChaining();
//            }
//            if(socket1[3]!=null) {
//                WriteCommandUtil writeCommandUtils=new WriteCommandUtil(refresh, socket1[3], new OnSingleCommandCompleted() {
//                    @Override
//                    public void onSingleCommandCompleted(String currentCommand, String response, AsyncSocket socket) {
//                        if (response != null) {
//
//                            String status = PollStatus.getPollState(response);
//                            Log.d("value", status);
//                            Intent local = new Intent();
//                            local.setAction("ToGetGvrStatus");
//                            local.putExtra("Status", status);
//                            sendBroadcast(local);
//                        }
//                    }
//                });
//                writeCommandUtils.doCommandChaining();
//
//            }
//            if(socket1[4]!=null) {
//                WriteCommandUtil writeCommandUtils=new WriteCommandUtil(refresh, socket1[4], new OnSingleCommandCompleted() {
//                    @Override
//                    public void onSingleCommandCompleted(String currentCommand, String response, AsyncSocket socket) {
//                        if (response != null) {
//
//                            String status = PollStatus.getPollState(response);
//                            Log.d("value", status);
//                            Intent local = new Intent();
//                            local.setAction("ToGetGvrStatus");
//                            local.putExtra("Status", status);
//                            sendBroadcast(local);
//                        }
//                    }
//                });
//                writeCommandUtils.doCommandChaining();
//
//            }



        }
    }
}

