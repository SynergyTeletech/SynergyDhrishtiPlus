package com.synergy.synergydhrishtiplus.service;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.koushikdutta.async.AsyncSocket;
import com.synergy.synergydhrishtiplus.SynergyApplicationClass;
import com.synergy.synergydhrishtiplus.data_model.NozzleModel;
import com.synergy.synergydhrishtiplus.server_pack.readable.TokheiumStatus;
import com.synergy.synergydhrishtiplus.server_pack.writable.Commands;
import com.synergy.synergydhrishtiplus.server_pack.writable.OnSingleCommandCompleted;
import com.synergy.synergydhrishtiplus.server_pack.writable.WriteCommandUtils;
import com.synergy.synergydhrishtiplus.utils.Utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class TokheumService extends Service {
    int notify = 1000;  //interval between two services(Here Service run every 5 Minute)
    ScheduledExecutorService scheduledExecutorService;
    String pump_d;

    HashMap<Integer, AsyncSocket> socketStore;
    String nozzle_id, port_no;
    AsyncSocket socket[];
    AsyncSocket sockets;

    public TokheumService() {
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
        socket = new AsyncSocket[20];
        scheduledExecutorService = Executors.newScheduledThreadPool(10);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle b = intent.getExtras();
        List<NozzleModel> tokheumList = (List<NozzleModel>) b.get("tokheum");
        Log.d("tokheiumList", "" + tokheumList.get(0).getPort());
        nozzle_id = tokheumList.get(0).getNozzleId();
        pump_d=tokheumList.get(0).getPumpId();
        port_no = tokheumList.get(0).getPort();

        socketStore = ((SynergyApplicationClass) getApplication()).getSocketHashMap();
        sockets = Utils.getValueByKey(socketStore, Integer.parseInt(port_no));
        scheduledExecutorService.scheduleAtFixedRate(new TokeamDuStatus(sockets), 0, notify, TimeUnit.MILLISECONDS);
        return START_NOT_STICKY;
    }

    class TokeamDuStatus implements Runnable {
        AsyncSocket socket1;

        public TokeamDuStatus(AsyncSocket socket) {
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


            WriteCommandUtils writeCommandUtils = new WriteCommandUtils(checkSumCommand, socket1, new OnSingleCommandCompleted() {
                @Override
                public void onSingleCommandCompleted(String currentCommand, String response, AsyncSocket socket) {
                    if (checkSumCommand == currentCommand) {
                        if (response != null) {
                            try {
                                String status = TokheiumStatus.getPollState(response);
                                Log.d("value1", status);
                                Intent local = new Intent();
                                local.setAction("ToGetTokheiumStatus");
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

}


