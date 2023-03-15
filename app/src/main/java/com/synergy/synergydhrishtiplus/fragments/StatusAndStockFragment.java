package com.synergy.synergydhrishtiplus.fragments;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bugfender.sdk.Bugfender;
import com.google.gson.Gson;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.synergy.synergydhrishtiplus.R;
import com.synergy.synergydhrishtiplus.SynergyApplicationClass;
import com.synergy.synergydhrishtiplus.WorkManagerService;
import com.synergy.synergydhrishtiplus.data_model.MapWrapper;
import com.synergy.synergydhrishtiplus.data_model.PumpHardwareInfoResponse;
import com.synergy.synergydhrishtiplus.errorlistener.ExceptionHandler;
import com.synergy.synergydhrishtiplus.listners.CheckConnectedAndDisconnectedListner;

import com.synergy.synergydhrishtiplus.server_pack.writable.AsciiCommandQueue;
import com.synergy.synergydhrishtiplus.server_pack.writable.CommandQueue;
import com.synergy.synergydhrishtiplus.server_pack.writable.OnAllCommandCompleted;
import com.synergy.synergydhrishtiplus.socket.Server485;
import com.synergy.synergydhrishtiplus.utils.DataConversion;
import com.synergy.synergydhrishtiplus.utils.SharedPref;
import com.synergy.synergydhrishtiplus.utils.Utils;
import com.synergy.synergydhrishtiplus.utils.VolumeCalculation;
import com.synergy.synergydhrishtiplus.viewmodel.HardwareResponse;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import pl.pawelkleczkowski.customgauge.CustomGauge;

public class StatusAndStockFragment extends Fragment implements View.OnClickListener, CheckConnectedAndDisconnectedListner {
    String hardwaredetail;
    String hardwareStatus;
    PumpHardwareInfoResponse.Data data;
    MapWrapper mapWrapper;
    HardwareResponse hardwareViewModel;
    boolean state = false;
    LinearLayout pump_detail,mis,sale_recept;
    ArrayList<PumpHardwareInfoResponse.Data.DispenserId> connectedDispenser=new ArrayList<>();
    ArrayList<PumpHardwareInfoResponse.Data.DispenserId> disconnetedDispenser=new ArrayList<>();
    ArrayList<PumpHardwareInfoResponse.Data.TankDatum> connectedATg=new ArrayList<>();
    ArrayList<PumpHardwareInfoResponse.Data.TankDatum> disconnetedAtg=new ArrayList<>();
    List<PumpHardwareInfoResponse.Data.DispenserId> dispenserIds;
    List<PumpHardwareInfoResponse.Data.TankDatum> tankDatas;

    HashMap<Integer, Boolean> statusHashmap = new HashMap<>();
    HashMap<Integer,Boolean> status;
    FragmentManager fragmentManager;
    Runnable getVolumeStatus;
    int[] ports=new int[20];
    private int mInterval = 1000; // 5 seconds by default, can be changed later
    private Handler mHandler;
    float volume=0.0f,volume1=0.0f,volume2=0.0f;

    AsyncSocket socket[]=new AsyncSocket[20];
    HashMap<Integer, AsyncSocket> socketStore;
    TextView dispenserNo;
    TextView atgNo;
    TextView rfidno;
    boolean isBuzzer=false;
    private CustomGauge gauge1,gauge2,gauge3,gauge4;
    private TextView product1,product2,product3,product4,volumes,volumes1,volumes2;
    private static final String TAG = "StatusandStock";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View v = inflater.inflate(R.layout.fragment_status_and_stock, container, false);
//
         Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
         Server485.setOnDataListener(this);

         WorkManager mWorkManager = WorkManager.getInstance();
        PeriodicWorkRequest refreshWork =
                new PeriodicWorkRequest.Builder(WorkManagerService.class, 15, TimeUnit.MINUTES)
                        .build();
         mWorkManager.enqueue(refreshWork);
         fragmentManager = getActivity().getSupportFragmentManager();
         connectedDispenser.clear();
         disconnetedDispenser.clear();
         disconnetedAtg.clear();
         connectedATg.clear();
         Gson gson = new Gson();
         hardwareViewModel= ViewModelProviders.of(getActivity()).get(HardwareResponse.class);
         hardwaredetail=SharedPref.getHardwareDetail();
         hardwareStatus=SharedPref.getSocketConnnectionDetail();
         socketStore=((SynergyApplicationClass)getActivity().getApplication()).getSocketHashMap();
        int i=0;
        for (Map.Entry<Integer,AsyncSocket> entry : socketStore.entrySet()){
            if(entry.getValue()!=null){
                ports[i] = entry.getKey();
                socket[i]=entry.getValue();
                i++;
            }
        }
         data = gson.fromJson(hardwaredetail, PumpHardwareInfoResponse.Data.class);
         dispenserIds = data.getDispenserId();
         tankDatas = data.getTankData();
    for (PumpHardwareInfoResponse.Data.DispenserId item : data.getDispenserId()) {
        if (item.isConnected_Status() == true) {
            connectedDispenser.add(item);
        } else {
            disconnetedDispenser.add(item);
        }
    }
    for (PumpHardwareInfoResponse.Data.TankDatum item : data.getTankData()) {
        if (item.isConnected_Status() == true) {
            connectedATg.add(item);
        } else {
            disconnetedAtg.add(item);
        }
    }


        mHandler = new Handler();
        startRepeatingTask();
         mapWrapper=gson.fromJson(hardwareStatus, MapWrapper.class);
         init(v);
        hardwareViewModel.getDataObserver().observe(getActivity(), new Observer<PumpHardwareInfoResponse.Data>() {
            @Override
            public void onChanged(PumpHardwareInfoResponse.Data datas) {
                Log.d("mutatable",""+datas);
                connectedDispenser.clear();
                disconnetedDispenser.clear();
                connectedATg.clear();
                disconnetedAtg.clear();
                data=datas;
                for (PumpHardwareInfoResponse.Data.DispenserId item : data.getDispenserId()) {
                    if (item.isConnected_Status()==true) {
                        connectedDispenser.add(item);
                    }
                    else {
                        disconnetedDispenser.add(item);
                    }
                }
                for (PumpHardwareInfoResponse.Data.TankDatum item : data.getTankData()) {
                    if (item.isConnected_Status()==true) {
                        connectedATg.add(item);
                    }
                    else {
                        disconnetedAtg.add(item);
                    }
                }
                dispenserNo.setText(String.valueOf(connectedDispenser.size())+"/"+ String.valueOf(data.getDispenserId().size()));
                atgNo.setText(String.valueOf(connectedATg.size())+"/"+ String.valueOf(data.getTankData().size()));

            }
        });
         return v;
    }

    private void buzzeroff(AsyncSocket socket) {
        String port = connectedATg.get(0).getPort_no();
        AsyncSocket sockets = Utils.getValueByKey(socketStore, Integer.parseInt(port));
        final String[] presetCompletedStateCommands = new String[]{"#*BZ10"};
        new AsciiCommandQueue(presetCompletedStateCommands, sockets, new OnAllCommandCompleted() {
            @Override
            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

            }

            @Override
            public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                try {

                } catch (Exception e) {

                }
            }
        }).doCommandChaining();

    }

    private void buzzeron(AsyncSocket socket) {
        String port = connectedATg.get(0).getPort_no();
        AsyncSocket sockets = Utils.getValueByKey(socketStore, Integer.parseInt(port));
        final String[] presetCompletedStateCommands = new String[]{"#*BZ11"};
        new AsciiCommandQueue(presetCompletedStateCommands, sockets, new OnAllCommandCompleted() {
            @Override
            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

            }

            @Override
            public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                try {

                } catch (Exception e) {

                }
            }
        }).doCommandChaining();

    }

    private void relayoff(AsyncSocket socket) {
        String port = connectedATg.get(0).getPort_no();
        AsyncSocket sockets = Utils.getValueByKey(socketStore, Integer.parseInt(port));
        final String[] presetCompletedStateCommands = new String[]{"#*RL10"};
        new AsciiCommandQueue(presetCompletedStateCommands, socket, new OnAllCommandCompleted() {
            @Override
            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

            }

            @Override
            public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                try {

                } catch (Exception e) {

                }
            }
        }).doCommandChaining();


    }

    private void relayon(AsyncSocket socket) {
        String port = connectedATg.get(0).getPort_no();
        AsyncSocket sockets = Utils.getValueByKey(socketStore, Integer.parseInt(port));
        final String[] presetCompletedStateCommands = new String[]{"#*RL11"};
        new AsciiCommandQueue(presetCompletedStateCommands, socket, new OnAllCommandCompleted() {
            @Override
            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

            }

            @Override
            public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                try {

                } catch (Exception e) {

                }
            }
        }).doCommandChaining();

    }

    private void startRepeatingTask() {
        mStatusChecker.run();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }

    private void stopRepeatingTask() {
        if(mHandler!=null) {
            mHandler.removeCallbacks(mStatusChecker);
        }
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {

              atgVolume();

            }
            finally {
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    private void atgVolume()  {
        if(connectedATg.size()>0) {
            for (int i = 0; i < connectedATg.size(); i++) {
                if (i == 0)
                {
                    String port = connectedATg.get(0).getPort_no();
                    String serial = "M" + connectedATg.get(0).getSerialNo();
                    String hex_serial_num = Utils.convertStringToHex(serial);
                    String hex_code = hex_serial_num + "0D0A";
                    AsyncSocket sockets = Utils.getValueByKey(socketStore, Integer.parseInt(port));

                    final String[] presetCompletedStateCommands = new String[]{hex_code};
                    new CommandQueue(presetCompletedStateCommands, sockets, new OnAllCommandCompleted() {
                        @Override
                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                        }

                        @Override
                        public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                            try {

                                Bugfender.d(TAG,response);
                                Log.d("ATGRESPONSE",response);
                                String asciivalue = DataConversion.convertToAscii(response);
                                String[] separated = asciivalue.split("=");
                                float d = Float.parseFloat(separated[2]);
                                 volume = VolumeCalculation.getAtgState(d, data.getTankData().get(0));
                                Log.d("ATGRESPONSEV",""+volume);

                                Bugfender.d(TAG,"height="+d+","+"volume"+volume);
                                gauge1.setEndValue(Integer.parseInt(data.getTankData().get(0).getCapacity()));
                                gauge1.setValue((int)volume);
                                if(getActivity()!=null){
                                  getActivity().runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          String s = String.format("%.2f", volume);
                                          volumes.setText(s);
                                      }
                                  });
                                }
                                float capicity=Float.parseFloat(data.getTankData().get(0).getCapacity());
                                float alege=capicity-volume;
                                if(alege>50){
                                    relayoff(sockets);

                                }else {
                                    relayon(sockets);
                                }

                            } catch (Exception e) {
                                Bugfender.d(TAG,e.getMessage());
                            }
                        }
                    }).doCommandChaining();


                }
                if(i==1){
                    String port = connectedATg.get(1).getPort_no();
                    String serial = "M" + connectedATg.get(1).getSerialNo();
                    String hex_serial_num = Utils.convertStringToHex(serial);
                    String hex_code = hex_serial_num + "0D0A";

                    AsyncSocket sockets = Utils.getValueByKey(socketStore, Integer.parseInt(port));
                    final String[] presetCompletedStateCommands = new String[]{hex_code};
                    new CommandQueue(presetCompletedStateCommands, sockets, new OnAllCommandCompleted() {
                        @Override
                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

                        }

                        @Override
                        public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                            try {
                                String asciivalue = DataConversion.convertToAscii(response);
                                String[] separated = asciivalue.split("=");
                                float d = Float.parseFloat(separated[2]);
                                 volume1 = VolumeCalculation.getAtgState(d, data.getTankData().get(1));
                                gauge2.setEndValue(Integer.parseInt(data.getTankData().get(1).getCapacity()));
                                gauge2.setValue((int)volume1);
                                float capicity=Float.parseFloat(data.getTankData().get(1).getCapacity());
                                float alege=capicity-volume1;
                                if(alege>50){
                                    relayoff(sockets);

                                }else {
                                    relayon(sockets);
                                }
                                Bugfender.d(TAG,"height="+d+","+"volume"+volume1);
                            } catch (Exception e) {
                                Bugfender.d(TAG,e.getMessage());
                            }
                        }
                    }).doCommandChaining();



                }
                if(i==2){
                    String port = connectedATg.get(2).getPort_no();
                    String serial = "M" + connectedATg.get(2).getSerialNo();
                    String hex_serial_num = Utils.convertStringToHex(serial);
                    String hex_code = hex_serial_num + "0D0A";
                    AsyncSocket sockets = Utils.getValueByKey(socketStore, Integer.parseInt(port));
                    final String[] presetCompletedStateCommands = new String[]{hex_code};
                    new CommandQueue(presetCompletedStateCommands, sockets, new OnAllCommandCompleted() {
                        @Override
                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                        }

                        @Override
                        public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                            try {
                                String asciivalue = DataConversion.convertToAscii(response);
                                String[] separated = asciivalue.split("=");
                                float d = Float.parseFloat(separated[2]);
                                 volume2 = VolumeCalculation.getAtgState(d, data.getTankData().get(2));
                                 Bugfender.d(TAG,"height="+d+","+"volume"+volume);
                                gauge3.setEndValue(Integer.parseInt(data.getTankData().get(2).getCapacity()));
                                gauge3.setValue((int)volume2);
                                float capicity=Float.parseFloat(data.getTankData().get(2).getCapacity());
                                float alege=capicity-volume2;
                                if(alege>50){
                                    relayoff(sockets);

                                }else {
                                    relayon(sockets);
                                }

                            } catch (Exception e) {
                                Bugfender.d(TAG,e.getMessage());

                            }
                        }
                    }).doCommandChaining();


                }

            }
        }

    }


    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void init(View v) {
        dispenserNo=v.findViewById(R.id.dispense_no);

        gauge1= (CustomGauge) v.findViewById(R.id.first_gg);
        gauge2= (CustomGauge)v.findViewById(R.id.second_gg);
        gauge3= (CustomGauge)v.findViewById(R.id.third_gg);
        gauge4= (CustomGauge)v.findViewById(R.id.fourth_gg);
        product1= v.findViewById(R.id.first_gp);
        product2=v.findViewById(R.id.second_gp);
        product3=v.findViewById(R.id.third_gp);
        product4=v.findViewById(R.id.fourth_gp);
        volumes=v.findViewById(R.id.first_volume);
        atgNo=v.findViewById(R.id.atg_num);
        rfidno=v.findViewById(R.id.rfid_no);
        pump_detail=v.findViewById(R.id.pump_detail);
        mis=v.findViewById(R.id.mis_b);
        sale_recept=v.findViewById(R.id.sale_receipt);
        dispenserNo.setText(String.valueOf(connectedDispenser.size())+"/"+ String.valueOf(data.getDispenserId().size()));
        atgNo.setText(String.valueOf(connectedATg.size())+"/"+ String.valueOf(data.getTankData().size()));
        pump_detail.setOnClickListener(this);
        mis.setOnClickListener(this);
        sale_recept.setOnClickListener(this);
        showProductStock(data) ;

    }

    @Override
    public void onResume() {
        super.onResume();
//        new Thread() {
//            public void run() {
//                gauge2.setEndValue(2000);
//                gauge2.setValue(400);
//                gauge3.setEndValue(2000);
//                gauge3.setValue(1000);
//
//            }
//        }.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopRepeatingTask();
    }

    private void showProductStock(PumpHardwareInfoResponse.Data data) {
        switch (data.getTankData().size()){
            case 1:
                gauge1.setVisibility(View.VISIBLE);
                product1.setVisibility(View.VISIBLE);
                volumes.setVisibility(View.VISIBLE);
                product1.setText(data.getTankData().get(0).getProductName());
              break;
            case 2:
                gauge1.setVisibility(View.VISIBLE);
                product1.setVisibility(View.VISIBLE);
                gauge2.setVisibility(View.VISIBLE);
                product2.setVisibility(View.VISIBLE);
                volumes.setVisibility(View.VISIBLE);
                product1.setText(data.getTankData().get(0).getProductName());
                product2.setText(data.getTankData().get(1).getProductName());
                break;
            case 3:
                gauge1.setVisibility(View.VISIBLE);
                product1.setVisibility(View.VISIBLE);
                gauge2.setVisibility(View.VISIBLE);
                volumes.setVisibility(View.VISIBLE);
                volumes.setVisibility(View.VISIBLE);
                product2.setVisibility(View.VISIBLE);
                gauge3.setVisibility(View.VISIBLE);
                product3.setVisibility(View.VISIBLE);
                product1.setText(data.getTankData().get(0).getProductName());
                product2.setText(data.getTankData().get(1).getProductName());
                product3.setText(data.getTankData().get(2).getProductName());
                break;
            case 4:
                gauge1.setVisibility(View.VISIBLE);
                volumes.setVisibility(View.VISIBLE);
                product1.setVisibility(View.VISIBLE);
                gauge2.setVisibility(View.VISIBLE);
                product2.setVisibility(View.VISIBLE);
                gauge3.setVisibility(View.VISIBLE);
                product3.setVisibility(View.VISIBLE);
                gauge4.setVisibility(View.VISIBLE);
                product4.setVisibility(View.VISIBLE);
                product1.setText(data.getTankData().get(0).getProductName());
                product2.setText(data.getTankData().get(1).getProductName());
                product3.setText(data.getTankData().get(2).getProductName());
                product4.setText(data.getTankData().get(3).getProductName());
                break;

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pump_detail:
                String backStateName;
                PumpListFragment pumpfragment= new PumpListFragment();
                Bundle bundle=new Bundle();
                bundle.putSerializable("key",disconnetedDispenser);
                pumpfragment.setArguments(bundle);
                backStateName = StatusAndStockFragment.class.getName();
                FragmentTransaction tx = getActivity().getSupportFragmentManager().beginTransaction();
                tx.addToBackStack(backStateName);
                tx.replace(R.id.container,  pumpfragment,"PumpList");
                tx.commit();

                break;
            case R.id.mis_b:


                break;
            case R.id.sale_receipt:
                String backStateName1;
                Transaction_fragment    transactionfragment= new Transaction_fragment();
                Bundle bundle1=new Bundle();
                bundle1.putSerializable("key",disconnetedDispenser);
                transactionfragment.setArguments(bundle1);
                backStateName1 = StatusAndStockFragment.class.getName();
                FragmentTransaction tx1 = getActivity().getSupportFragmentManager().beginTransaction();
                tx1.addToBackStack(backStateName1);
                tx1.replace(R.id.container,  transactionfragment,"PumpList");
                tx1.commit();

                break;


        }
    }

    @Override
    public void updateSocketGlobal(int portNo, AsyncSocket socket, boolean status) {
        socketStore.put(portNo,socket);
        statusHashmap.put(portNo, status);
        Bugfender.d(TAG,""+portNo+","+socket+","+status);

        PumpHardwareInfoResponse.Data.DispenserId dispenser = Utils.getDispenser(dispenserIds, portNo);
        PumpHardwareInfoResponse.Data.TankDatum tankDatum = Utils.getTank(tankDatas, portNo);
        if(dispenser!=null){
            dispenser.setConnected_Status(status);
        }
        else {
            tankDatum.setConnected_Status(status);

        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hardwareViewModel.setResponse(data);
                Gson gson = new Gson();
                MapWrapper wrapper = new MapWrapper();
                wrapper.setStatusHashMap(statusHashmap);
                String serializedMap = gson.toJson(wrapper);
                SharedPref.setSocketConnnectionDetail(serializedMap);
                ((SynergyApplicationClass) getActivity().getApplication()).setSocketData(socketStore);
                ((SynergyApplicationClass) getActivity().getApplication()).setResponse(data);
                Bugfender.d(TAG,""+data);
//                Bugfender.d(TAG,""+new Gson().toJson(socketStore));
//                Bugfender.d(TAG,""+new Gson().toJson(statusHashmap));
            }
        });



    }
}