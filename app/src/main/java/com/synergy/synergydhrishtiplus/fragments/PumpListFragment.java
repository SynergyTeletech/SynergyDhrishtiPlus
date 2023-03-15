package com.synergy.synergydhrishtiplus.fragments;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bugfender.sdk.Bugfender;
import com.google.gson.Gson;
import com.koushikdutta.async.AsyncSocket;
import com.synergy.synergydhrishtiplus.R;
import com.synergy.synergydhrishtiplus.SynergyApplicationClass;
import com.synergy.synergydhrishtiplus.adapters.NozzelsAdapter;
import com.synergy.synergydhrishtiplus.adapters.TankAdapter;
import com.synergy.synergydhrishtiplus.adapters.TransactionsAdapter;
import com.synergy.synergydhrishtiplus.broadcast.NetworkReceiver;
import com.synergy.synergydhrishtiplus.data_model.NozzleModel;
import com.synergy.synergydhrishtiplus.data_model.Postmodel;
import com.synergy.synergydhrishtiplus.data_model.PresetModal;
import com.synergy.synergydhrishtiplus.data_model.PumpHardwareInfoResponse;
import com.synergy.synergydhrishtiplus.data_model.TransactionModel;
import com.synergy.synergydhrishtiplus.errorlistener.ExceptionHandler;
import com.synergy.synergydhrishtiplus.listners.RecyclerviewListener;
import com.synergy.synergydhrishtiplus.network.ApiClient;
import com.synergy.synergydhrishtiplus.network.ApiInterface;
import com.synergy.synergydhrishtiplus.room.DatabaseClient;
import com.synergy.synergydhrishtiplus.server_pack.writable.AsciiCommandQueue;
import com.synergy.synergydhrishtiplus.server_pack.writable.CommandQueue;
import com.synergy.synergydhrishtiplus.server_pack.writable.CommandQueue1;
import com.synergy.synergydhrishtiplus.server_pack.writable.CommandQueue2;
import com.synergy.synergydhrishtiplus.server_pack.writable.CommandQueue3;
import com.synergy.synergydhrishtiplus.server_pack.writable.CommandQueue4;
import com.synergy.synergydhrishtiplus.server_pack.writable.CommandQueue5;
import com.synergy.synergydhrishtiplus.server_pack.writable.CommandQueue6;
import com.synergy.synergydhrishtiplus.server_pack.writable.CommandQueue7;
import com.synergy.synergydhrishtiplus.server_pack.writable.CommandQueue8;
import com.synergy.synergydhrishtiplus.server_pack.writable.Commands;
import com.synergy.synergydhrishtiplus.server_pack.writable.FinalResponse;
import com.synergy.synergydhrishtiplus.server_pack.writable.FinalResponse1;
import com.synergy.synergydhrishtiplus.server_pack.writable.FinalResponse2;
import com.synergy.synergydhrishtiplus.server_pack.writable.FinalResponse3;
import com.synergy.synergydhrishtiplus.server_pack.writable.FinalResponse4;
import com.synergy.synergydhrishtiplus.server_pack.writable.FinalResponse5;
import com.synergy.synergydhrishtiplus.server_pack.writable.FinalResponse6;
import com.synergy.synergydhrishtiplus.server_pack.writable.FinalResponse7;
import com.synergy.synergydhrishtiplus.server_pack.writable.FinalResponse8;
import com.synergy.synergydhrishtiplus.server_pack.writable.OnAllCommandCompleted;
import com.synergy.synergydhrishtiplus.server_pack.writable.OnSingleCommandCompleted;
import com.synergy.synergydhrishtiplus.server_pack.writable.TxnCommand;
import com.synergy.synergydhrishtiplus.server_pack.writable.TxnCommand1;
import com.synergy.synergydhrishtiplus.server_pack.writable.TxnCommand2;
import com.synergy.synergydhrishtiplus.server_pack.writable.TxnCommand3;
import com.synergy.synergydhrishtiplus.server_pack.writable.TxnCommand4;
import com.synergy.synergydhrishtiplus.server_pack.writable.TxnCommand5;
import com.synergy.synergydhrishtiplus.server_pack.writable.TxnCommand6;
import com.synergy.synergydhrishtiplus.server_pack.writable.TxnCommand7;
import com.synergy.synergydhrishtiplus.server_pack.writable.TxnCommand8;
import com.synergy.synergydhrishtiplus.server_pack.writable.WriteCommandUtils;
import com.synergy.synergydhrishtiplus.service.DispenserServices;
import com.synergy.synergydhrishtiplus.service.MyService;
import com.synergy.synergydhrishtiplus.service.TokheumService;
import com.synergy.synergydhrishtiplus.service.TokheumService1;
import com.synergy.synergydhrishtiplus.service.TokheumService2;
import com.synergy.synergydhrishtiplus.service.TokheumService4;
import com.synergy.synergydhrishtiplus.service.TokheumService5;
import com.synergy.synergydhrishtiplus.service.TokheumService6;
import com.synergy.synergydhrishtiplus.service.TokheumService7;
import com.synergy.synergydhrishtiplus.service.TokheumService8;
import com.synergy.synergydhrishtiplus.utils.DataConversion;
import com.synergy.synergydhrishtiplus.utils.SharedPref;
import com.synergy.synergydhrishtiplus.utils.Utils;
import com.synergy.synergydhrishtiplus.utils.VolumeCalculation;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class PumpListFragment extends Fragment implements RecyclerviewListener {
    RecyclerView  tankRecyclerView, nozzleRecyclerView, transactionRecyclerView;
    LinearLayout dispenserListRecyclerView;
    LinearLayoutManager layoutManager;
    GridLayoutManager mlinearLayoutManager;
    ImageButton scrollbutton,scrollbutton2,scrolltank,scrolltank0;
    PumpHardwareInfoResponse.Data data;
    TankAdapter tankAdapter;
    List<NozzleModel> chinese=new ArrayList<>();
    List<NozzleModel> gvr=new ArrayList<>();
    List<NozzleModel> tokheium=new ArrayList<>();
    List<NozzleModel> synergy=new ArrayList<>();
    List<PumpHardwareInfoResponse.Data.TankDatum> tankData=new ArrayList<>();
    List<PumpHardwareInfoResponse.Data.DispenserId> dispenserIdList=new ArrayList<>();
    List<PumpHardwareInfoResponse.Data.DispenserNozzle> dispenserNozzles=new ArrayList<>();
    List<NozzleModel> dispenserModels;
    private BroadcastReceiver activityReceiver;
    private BroadcastReceiver connectivityReceiver;
    private BroadcastReceiver tokheiumReceiver,tokheiumReceiver1,tokheiumReceiver2,tokheiumReceiver3,tokheiumReceiver4,tokheiumReceiver5,tokheiumReceiver6,tokheiumReceiver7,tokheiumReceiver8;
    NozzelsAdapter dispensersAdapter;
    int presetvolumes=0;
    String tot_volume="0.0",tot_volume1="0.0",tot_volume2="0.0",tot_volume3="0.0",tot_volume4="0.0",tot_volume5="0.0",tot_volume6="0.0",tot_volume7="0.0",tot_volume8="0.0";
    String start_time="", start_time1="", start_time2="", start_time3="", start_time4="", start_time5="", start_time6="", start_time7="", start_time8="";
    String end_time="",end_time1="",end_time2="",end_time3="",end_time4="",end_time5="",end_time6="",end_time7="",end_time8="";
    boolean state=false;
    int count=0,count1=0,count2=0,count3=0,count4=0,count5=0,count6=0,count7=0,count8=0;
    int[] ports=new int[10];
    float volume=0.0f,volume1=0.0f,volume2=0.0f;

    AsyncSocket socket[]=new AsyncSocket[10];
    HashMap<Integer, AsyncSocket> socketStore;
    String hardwaredetail;
    String hardwareStatus;
    public static String TAG="PumpListFragment";

    public String volume_preset="0.0",volume_preset1="0.0",volume_preset2="0.0",volume_preset3="0.0",volume_preset4="0.0",volume_preset5="0.0",volume_preset6="0.0",volume_preset7="0.0",volume_preset8="0.0";
    String volumeSt="0.0",volumeSt1="0.0",volumeSt2="0.0",volumeSt3="0.0",volumeSt4="0.0",volumeSt5="0.0",volumeSt6="0.0",volumeSt7="0.0",volumeSt8="0.0";
    public String pump_id="",pump_id1="",pump_id2="",pump_id3="",pump_id4="",pump_id5="",pump_id6="",pump_id7="",pump_id8="";
    public  String nozzle_id="", nozzle_id1="",nozzle_id2="",nozzle_id3="",nozzle_id4="",nozzle_id5="",nozzle_id6="",nozzle_id7="",nozzle_id8="";
    ScheduledExecutorService scheduledExecutorService;
    String ammountSt = "0.0", priceSt = "0.0";
    Intent intent,intent1,intent2,intent3,intent4,intent5,intent6,intent7,intent8;
    Intent gintent,gintent1,gintent2,gintent3,gintent4,gintent5,gintent6,gintent7,gintent8;

    private ScheduledExecutorService executorAtg1;
    private Runnable getATG1Runnable;
    ArrayList<PumpHardwareInfoResponse.Data.TankDatum> connectedATg=new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onStop() {
        super.onStop();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pump_list, container, false);
        connectedATg.clear();
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
        Bundle bundle = getArguments();
        if(bundle!=null){
            ArrayList<PumpHardwareInfoResponse.Data.DispenserId> dispenserIds= (ArrayList<PumpHardwareInfoResponse.Data.DispenserId>) bundle.get("key");
        }

        Gson gson = new Gson();
        dispenserModels=new ArrayList<>();
        hardwaredetail= SharedPref.getHardwareDetail();
        hardwareStatus=SharedPref.getSocketConnnectionDetail();
        data=gson.fromJson(hardwaredetail, PumpHardwareInfoResponse.Data.class);
        tankData=data.getTankData();
        dispenserIdList=data.getDispenserId();
        dispenserNozzles=data.getDispenserNozzle();
        tokheium.clear();
        dispenserModels=  setDispenserList();
        scheduledExecutorService= Executors.newScheduledThreadPool(1);
        connectivityReceiver=new NetworkReceiver();
        socketStore=((SynergyApplicationClass)getActivity().getApplication()).getSocketHashMap();
        Bugfender.d(TAG,""+data);
        for (PumpHardwareInfoResponse.Data.TankDatum item : data.getTankData()) {
            if (item.isConnected_Status()==true) {
                connectedATg.add(item);
            }
            else {

            }
        }
        getATG1Runnable = new Runnable() {
            public void run() {
                atgVolume();
            }
        };
        if(connectedATg.size()>0){
            executorAtg1 = Executors.newScheduledThreadPool(1);
            executorAtg1.scheduleAtFixedRate(getATG1Runnable, 0, 15, TimeUnit.MINUTES);
        }
        if(gvr.size()>0){
         for(int i=0;i<gvr.size();i++){
             if (i==0){
                 gintent = new Intent(getActivity(), DispenserServices.class);
                 Bundle bundle1 = new Bundle();
                 bundle1.putSerializable("gvr", (Serializable) gvr);
                 gintent.putExtras(bundle1);
                 getActivity().startService(gintent);


             }


         }
        }
        if(tokheium.size()>0) {
            for (int j=0;j<tokheium.size();j++){
                if(j==0){
                    Bugfender.d(TAG,""+j);
                    intent = new Intent(getActivity(), TokheumService.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("tokheum", (Serializable) tokheium);
                    intent.putExtras(bundle1);
                    getActivity().startService(intent);
                }
                if(j==1){
                    Bugfender.d(TAG,""+j);
                    intent1 = new Intent(getActivity(), TokheumService1.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("tokheum", (Serializable) tokheium);
                    intent1.putExtras(bundle1);
                    getActivity().startService(intent1);
                }
                if(j==2){
                    Bugfender.d(TAG,""+j);
                    intent2 = new Intent(getActivity(), TokheumService2.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("tokheum", (Serializable) tokheium);
                    intent2.putExtras(bundle1);
                    getActivity().startService(intent2);
                }
                if(j==3){
                    Bugfender.d(TAG,""+j);
                    intent3 = new Intent(getActivity(), MyService.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("tokheum", (Serializable) tokheium);
                    intent3.putExtras(bundle1);
                    getActivity().startService(intent3);
                }
                if(j==4){
                    Bugfender.d(TAG,""+j);
                    intent4 = new Intent(getActivity(), TokheumService4.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("tokheum", (Serializable) tokheium);
                    intent4.putExtras(bundle1);
                    getActivity().startService(intent4);
                }
                if(j==5){
                    Bugfender.d(TAG,""+j);
                    intent5 = new Intent(getActivity(), TokheumService5.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("tokheum", (Serializable) tokheium);
                    intent5.putExtras(bundle1);
                    getActivity().startService(intent5);
                }
                if(j==6){
                    Bugfender.d(TAG,""+j);
                    intent6 = new Intent(getActivity(), TokheumService6.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("tokheum", (Serializable) tokheium);
                    intent6.putExtras(bundle1);
                    getActivity().startService(intent6);
                }
                if(j==7){
                    Bugfender.d(TAG,""+j);
                    intent7 = new Intent(getActivity(), TokheumService7.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("tokheum", (Serializable) tokheium);
                    intent7.putExtras(bundle1);
                    getActivity().startService(intent7);
                }
                if(j==8){
                    Bugfender.d(TAG,""+j);
                    intent8 = new Intent(getActivity(), TokheumService8.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("tokheum", (Serializable) tokheium);
                    intent8.putExtras(bundle1);
                    getActivity().startService(intent8);
                }
            }
        }
        else if(chinese.size()>0)
        {
        }
        else if(synergy.size()>0)
        {
        }

        ScheduledThreadPoolExecutor executor_ =
                new ScheduledThreadPoolExecutor(1);
        ScheduledFuture<?> schedulerFuture= executor_.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                getTransaction();
            }
        }, 0L, 60000,  TimeUnit.MILLISECONDS);

        activityReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String status = intent.getStringExtra("Status");
                int port= intent.getIntExtra("Port",0);
                PumpHardwareInfoResponse.Data.DispenserId  dispenserId=null;
                PumpHardwareInfoResponse.Data.TankDatum tankDatum=null;
                NozzleModel nozzleModel;
                nozzleModel=null;
                if(port!=0) {
                    dispenserId=Utils.getDispenser(data.getDispenserId(), port);
                    Log.i("findd",""+dispenserId);
                    if(dispenserId==null){
                        tankDatum=Utils.getTank(data.getTankData(),port) ;
                        Log.i("findt",""+tankDatum);
                    }
                    else {
                        nozzleModel= Utils.getNozzle(dispenserModels,port)  ;
                        nozzleModel.setStatus(status);
                    }
                }
                if(status.contains("CALLSTATE")){
                    nozzleModel.setStatus(status);
                    dispensersAdapter.notifyDataSetChanged();
                    Log.d("getStatus1",status);
                    CommandQueue.TerminateCommandChain();
                    final String[] authorize;
                    authorize = new String[]{"11"};
                    CommandQueue commandQueue = new CommandQueue(authorize, socket[0], new OnAllCommandCompleted() {
                        @Override
                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {


                        }

                        @Override
                        public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                            if (response != null) {
                                Log.d("gvr1",response);


                            }

                        }
                    });
                    commandQueue.doCommandChaining();

                }
                else if (status.contains("IDLE")){
                    nozzleModel.setStatus(status);
                    retrancation(nozzleModel);
                }

                else if(status.contains("FUELINGSTATE")){
                    nozzleModel.setStatus(status);

                    dispensersAdapter.notifyDataSetChanged();
                    NozzleModel finalNozzleModel = nozzleModel;
                    final String[] retranactionAmount;
                    retranactionAmount = new String[]{"61"};
                    CommandQueue commandQueue = new CommandQueue(retranactionAmount, socket[0], new OnAllCommandCompleted() {
                        @Override
                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {


                        }
                        @Override
                        public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                            if (response != null) {
                                try {
                                    if(response.startsWith("e")) {
                                        Log.d("gvr2", response);
                                        Log.d("responseG", response);
                                        String ammount = response.replaceAll("[^\\d.]", "");
                                        StringBuilder ammountS = new StringBuilder();
                                        ammountS.append(ammount);
                                        ammountS.reverse();
                                        double rate=Double.parseDouble(priceSt);
                                        double amount=Double.parseDouble(ammountS.toString());
                                        double volume=amount/rate;
                                        String volumes=    String.format("%.2f", volume);
                                        Log.e("ammount", ammountS.toString());
                                        String finalammountS = ammountS.toString();
                                        String pAmout=finalammountS.replaceFirst("^0+(?!$)", "");
                                        finalNozzleModel.setAmount(pAmout);
                                        finalNozzleModel.setPrice(String.valueOf(rate));
                                        finalNozzleModel.setVolume( volumes);
                                    }

                                }
                                catch (Exception e){
                                }

                            }

                        }
                    });
                    commandQueue.doCommandChaining();

                }
                else if(status.contains("PRESETDONE")){
                    nozzleModel.setStatus(status);
                    dispensersAdapter.notifyDataSetChanged();
                    final String[] reTransaction;
                    reTransaction = new String[]{"41"};
                    CommandQueue commandQueue = new CommandQueue(reTransaction, socket[0], new OnAllCommandCompleted() {
                        @Override
                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

                        }

                        @Override
                        public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                            if (response != null) {
                                Log.d("gvr3",response);
                                Log.d("final volume", response);
                            }
                        }
                    });
                    commandQueue.doCommandChaining();
                }
            }
        };
        // for first tokheium
        tokheiumReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String status = intent.getStringExtra("Status");
                int port= intent.getIntExtra("Port",0);
                AsyncSocket   sockets = Utils.getValueByKey(socketStore, port);
                PumpHardwareInfoResponse.Data.DispenserId  dispenserId=null;
                PumpHardwareInfoResponse.Data.TankDatum tankDatum=null;
                NozzleModel nozzleModel;
                nozzleModel=null;
                if(port!=0) {
                    dispenserId=Utils.getDispenser(data.getDispenserId(), port);
                    if(dispenserId==null){
                        tankDatum=Utils.getTank(data.getTankData(),port) ;
                    }
                    else {
                        nozzleModel= Utils.getNozzle(dispenserModels,port)  ;
//                        nozzleModel.setStatus(status);
//                        dispensersAdapter.notifyDataSetChanged();
                    }
                }
                pump_id=nozzleModel.getPumpId();
                String binaryString=  "010"+Utils.intToBinary(1,5);
                int decimal = Integer.parseInt(binaryString,2);
                String pump_hexStr = Integer.toString(decimal,16);

                nozzle_id=nozzleModel.getNozzleId();
                String authrisationcommand="0"+nozzle_id+pump_hexStr+Commands.authrization_command+"7f";
                String read_transaction="0"+nozzle_id+pump_hexStr+Commands.read_transaction+"7f";
                String last_transaction="0"+nozzle_id+pump_hexStr+Commands.read_lasttransaction+"7f";
                String pump_startCommand="0"+nozzle_id+pump_hexStr+Commands.pump_startcommands+"7f";
                String pump_stopCommand="0"+nozzle_id+pump_hexStr+Commands.pumps_stopcommands+"7f";
                String totliser="0"+nozzle_id+pump_hexStr+Commands.totaliser_command+"7f";
                String clears_sale="0"+nozzle_id+pump_hexStr+Commands.clear_salecommands+"7f";
                String command=  "0"+nozzle_id+pump_hexStr+Commands.read_preset+"7f";
                String auth_code = authrisationcommand + Utils.calculateCheckSum((Utils.convertToAscii(authrisationcommand).getBytes()));
                String readtransaction_code = read_transaction + Utils.calculateCheckSum((Utils.convertToAscii(read_transaction).getBytes()));
                String readlasttransaction_code=last_transaction+Utils.calculateCheckSum((Utils.convertToAscii(last_transaction).getBytes()));
                String pumpstart_code=pump_startCommand+Utils.calculateCheckSum((Utils.convertToAscii(pump_startCommand).getBytes()));
                String pumpstop_code=pump_stopCommand+Utils.calculateCheckSum((Utils.convertToAscii(pump_stopCommand).getBytes()));
                String totaliser_code=totliser+Utils.calculateCheckSum((Utils.convertToAscii(totliser).getBytes()));
                String clearsale_code=clears_sale+Utils.calculateCheckSum((Utils.convertToAscii(clears_sale).getBytes()));
                String checkSumCommand = command + Utils.calculateCheckSum((Utils.convertToAscii(command)).getBytes());
                if(status.contains("STOPPED STATE")){
                    nozzleModel.setStatus(status);
                    count=0;
                    Bugfender.d(TAG,"Pump1 Stopped state");
                    final String[] retansaction;
                    CommandQueue.TerminateCommandChain();
                    retansaction = new String[]{pumpstop_code,pumpstart_code};
                    CommandQueue commandQueue1 = new CommandQueue(retansaction,
                            sockets,
                            new OnAllCommandCompleted() {
                                @Override
                                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                                }
                                @Override
                                public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                                    if (response != null) {

                                    }

                                }
                            });
                    commandQueue1.doCommandChaining();

                }
                else if(status.contains("IDLE")){
                    nozzleModel.setStatus(status);
                    Log.d("pump","pump0");
                       tot_volume="0.0";
                       volumeSt="0.0";
                       start_time="";
                       end_time="";
//                       if(count==0) {
//                           volume_preset = String.valueOf(checkpreset(dispenserId));
                           volume_preset = String.valueOf(10.00);
                           Bugfender.d(TAG,"Volume Preset1"+volume_preset);
                           Bugfender.d(TAG,"Dispenser1"+dispenserId);
                           Bugfender.d(TAG,"pumpid1"+pump_id);
                           Bugfender.d(TAG,"Port1"+port);
//                       }
//                       count++;
                }
                else if(status.contains("PRESET READY STATE")){
                    nozzleModel.setStatus(status);
                    Bugfender.d(TAG,"Pump1 preset ready state");
                    count=0;
                    state=false;
                    Bugfender.d(TAG,"Preset1"+volume_preset);
//                    if(!volumeSt.contains("0.0") || !volumeSt.contains(".0")) {
                        Bugfender.d(TAG,"Preset Varify1"+volumeSt);
                        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                        start_time = df.format(Calendar.getInstance().getTime());
                        sendAuthcode(auth_code,sockets);
//                    }
                }
                          else if(status.contains("CALL STATE")){
                            count=0;
                            Bugfender.d(TAG,"Pump1 call state");
                            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
//                            start_time = df.format(Calendar.getInstance().getTime());
                            nozzleModel.setStatus(status);
                            Bugfender.d(TAG,"Preset1"+volume_preset);
                             sendAuthcode(auth_code, sockets);


//                                String b = String.format(Locale.US, "%07d", (int) (Double.parseDouble(volume_preset) * 100));
//
//                                Log.e("Pravin",b);
//                                Log.e("PravinR",stringToHexWithoutSpace(b));
//                                String commands = "0" + nozzle_id + pump_hexStr + Commands.set_presetCommand + "31" +  stringToHexWithoutSpace(b) + "7F";
//                                String checkSumCommands = commands + Utils.calculateCheckSum((Utils.convertToAscii(commands)).getBytes());
//                                Log.d("volumescommand1", checkSumCommands + "," + checkSumCommand);
//                                final String[] d = new String[]{checkSumCommands, checkSumCommand};
//                                CommandQueue commandQueue = new CommandQueue(d, sockets, new OnAllCommandCompleted() {
//                                    @Override
//                                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                                    }
//
//                                    @Override
//                                    public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
//                                        try {
//                                            if (response.length() == 36) {
//                                                String respon = response.substring(12, 32);
//                                                volumeSt = Utils.convertToAscii(respon).replaceAll("^0*", "");
//                                                Bugfender.d(TAG,"VolumeSt1"+volumeSt);
//                                                if (!volumeSt.contains(".0")|| !volumeSt.contains(".00")) {
//                                                    state=true;
//                                                    Bugfender.d(TAG,"VolumeStforAuth1"+volumeSt);
//                                                    sendAuthcode(auth_code, sockets);
//
//                                                }
//
////
//                                            }
//
//                                        } catch (IndexOutOfBoundsException e) {
//
//                                        }
//                                        catch (Exception e) {
//
//                                        }
//
//
//                                    }
//                                });
//                                commandQueue.doCommandChaining();


                    dispensersAdapter.notifyDataSetChanged();
                }
                else if(status.contains("FUELING STATE")){
                    nozzleModel.setStatus(status);

                    dispensersAdapter.notifyDataSetChanged();
                    NozzleModel finalNozzleModel = nozzleModel;
                    final String[] retansaction;
                    retansaction = new String[]{readtransaction_code};
                    CommandQueue commandQueue = new CommandQueue(retansaction, sockets, new OnAllCommandCompleted() {
                        @Override
                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

                        }
                        @Override
                        public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                            try {
                                if (response != null) {
                                    if (response != null && Utils.countMatches(response, ".") == 3) {
                                        String value = Utils.encodeHexString(response.getBytes());
                                        String respomse = response;
                                        Log.e("txnreskam", "" + (Utils.findNextOccurance(respomse, ".", 1) > 4) + "" + (Utils.findNextOccurance(respomse, ".", 2) > 13) + "&&" + (Utils.findNextOccurance(respomse, ".", 3) > 22));
                                        final String txnFuelRate = respomse.substring(4, respomse.indexOf(".") + 3);
                                        final String txnDispense = respomse.substring(respomse.indexOf(".") + 3, Utils.findNextOccurance(respomse, ".", 2) + 3);
                                        final String txnCharges = respomse.substring(Utils.findNextOccurance(respomse, ".", 2) + 3, Utils.findNextOccurance(respomse, ".", 3) + 3);
                                        finalNozzleModel.setVolume(txnDispense);
                                        finalNozzleModel.setAmount(txnCharges);
                                        finalNozzleModel.setPrice(txnFuelRate);

                                    }
                                }
                            }
                            catch (Exception e){
                                Log.d("d", e.getLocalizedMessage());
                                Bugfender.d(TAG, "571"+e.getMessage());
                            }
                        }
                    });
                    commandQueue.doCommandChaining();
                }
                else if(status.contains("PAYABLE STATE")){
                    Bugfender.d(TAG,"Pump1 payable state");
                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                    end_time= df.format(Calendar.getInstance().getTime());
                    nozzleModel.setStatus(status);

//                    dispensersAdapter.notifyDataSetChanged();
                    CommandQueue.TerminateCommandChain();
                    final String[] totaliser;
                    totaliser = new String[]{totaliser_code};
                    PumpHardwareInfoResponse.Data.DispenserId finalDispenserId = dispenserId;
                    NozzleModel finalNozzleModel2 = nozzleModel;
//                    TxnCommand txnCommand =new TxnCommand(totaliser, sockets, new OnAllCommandCompleted() {
//                        @Override
//                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//
//                        }
//                        @Override
//                        public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
//                            try {
//
//                                String volumeresponse = response.substring(8, response.indexOf("7f"));
//                                Log.d("responsehello",volumeresponse);
//                                String sb=Utils.convertToAscii(volumeresponse);
//                                tot_volume = sb.replaceFirst("^0+(?!$)", "");
//                                finalNozzleModel2.setVolume_totaliser(tot_volume);
//                                Bugfender.d(TAG,"first totaliser1"+ tot_volume);
//                            }
//                            catch (ArrayIndexOutOfBoundsException e){
//                                Bugfender.d(TAG,"first totaliser exception"+ e.getMessage());
//                            }
//                            catch (Exception e){
//                                Bugfender.d(TAG,"first totaliser exception1"+ e.getMessage());
//                            }
//
//
//                        }
//                    });
//                    txnCommand.doCommandChaining();
                    if(!tot_volume.contains("0.0")) {
                        final String[] refresh;
                        Bugfender.d(TAG,"readLasttran"+tot_volume);
                        refresh = new String[]{readlasttransaction_code};
                        NozzleModel finalNozzleModel1 = nozzleModel;
                        FinalResponse finalResponse = new FinalResponse(refresh[0], sockets, new OnSingleCommandCompleted() {
                            @Override
                            public void onSingleCommandCompleted(String currentCommand, String response, AsyncSocket socket) {
                                try {
                                    if (response.length() == 152 || response.length() == 120) {
                                        idealState(clearsale_code, sockets);
                                        String ammount = response.substring(30, 38);
                                        ammount = "" + Integer.parseInt("" + rev_U(ammount), 16);
                                        ammount = String.valueOf(Double.parseDouble(ammount) / 1000);
                                        String volume = response.substring(38, 46);
                                        volume = "" + Integer.parseInt("" + rev_U(volume), 16);
                                        volume = String.valueOf((Double.parseDouble(volume) / 1000));
                                        String price = response.substring(46, 54);
                                        price = "" + Integer.parseInt("" + rev_U(price), 16);
                                        price = String.valueOf(Double.parseDouble(price) / 1000);
                                        Log.d("final result", ammount + ", " + volume + "," + price +","+response);
                                        finalNozzleModel1.setVolume(volume);
                                        finalNozzleModel1.setAmount(ammount);
                                        finalNozzleModel1.setPrice(price);
                                        String reciptNo = response.substring(60, 64);
                                        reciptNo = "" + Integer.parseInt("" + rev_U(reciptNo), 16);
                                        Log.d("recipt", reciptNo);
                                        String localID = response.substring(64, 72);
                                        localID = "" + Integer.parseInt("" + rev_U(localID), 16);
                                        Log.d("local", localID);
                                        String date = response.substring(74, 80);
                                        String day = date.substring(0, 2);
                                        String month1 = date.substring(2, 4);
                                        String year = date.substring(4, 6);
                                        date = Integer.parseInt("" + rev_U(day), 16) + "/" + Integer.parseInt("" + rev_U(month1), 16) + "/" + Integer.parseInt("" + rev_U(year), 16);
                                        Log.d("date", date);
                                        String time = response.substring(80, 84);
                                        String hour = response.substring(0, 2);
                                        String min = response.substring(2, 4);
                                        time = Integer.parseInt("" + rev_U(hour), 16) + ":" + Integer.parseInt("" + rev_U(min), 16);
                                        Long tsLong = System.currentTimeMillis() / 1000;
                                        String ts = tsLong.toString();
                                        TransactionModel transactionModel = new TransactionModel();
                                        transactionModel.setAmount(ammount);
                                        transactionModel.setTransaction_id(ts);
                                        transactionModel.setStatus(false);
                                        transactionModel.setVolume(volume);
                                        transactionModel.setRate(price);
                                        transactionModel.setDispenser_id(pump_id);
                                        transactionModel.setTotaliser(tot_volume);
                                        transactionModel.setStart_time(start_time);
                                        transactionModel.setEnd_time(end_time);
                                        finalNozzleModel1.setEnd_time(end_time);
                                        Bugfender.d(TAG,"first last transaction1"+ pump_id+","+volume+","+tot_volume+","+ammount+","+price+","+ts);
                                        if (!volume.contains("0.0") || !volume.contains("0")) {
                                            Bugfender.d(TAG,"first last transactionsave1"+ start_time+","+volume+","+tot_volume+","+ammount+","+price+","+end_time);
                                            new SaveTask(transactionModel).execute();
                                        }

                                    }
                                }
                                catch (Exception e){
                                    Bugfender.d("exception first complete1",e.getMessage());
                                }
                            }
                        });
                        finalResponse.doCommandChaining();
                    }
                }

            }




        };
        // for second tokheium
        tokheiumReceiver1=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String status = intent.getStringExtra("Status");
                Log.d("StatusPump",status);
                int port= intent.getIntExtra("Port",0);
                AsyncSocket   sockets = Utils.getValueByKey(socketStore, port);
                PumpHardwareInfoResponse.Data.DispenserId  dispenserId=null;
                PumpHardwareInfoResponse.Data.TankDatum tankDatum=null;
                NozzleModel nozzleModel;
                nozzleModel=null;
                if(port!=0) {
                    dispenserId=Utils.getDispenser(data.getDispenserId(), port);

                    if(dispenserId==null){
                        tankDatum=Utils.getTank(data.getTankData(),port) ;
                    }
                    else {
                        nozzleModel= Utils.getNozzle(dispenserModels,port)  ;

//                        dispensersAdapter.notifyDataSetChanged();
                    }
                }
                pump_id1=nozzleModel.getPumpId();
                String binaryString=  "010"+Utils.intToBinary(1,5);
                int decimal = Integer.parseInt(binaryString,2);
                String pump_hexStr = Integer.toString(decimal,16);
                nozzle_id1=nozzleModel.getNozzleId();
                String authrisationcommand="0"+nozzle_id1+pump_hexStr+Commands.authrization_command+"7f";
                String read_transaction="0"+nozzle_id1+pump_hexStr+Commands.read_transaction+"7f";
                String last_transaction="0"+nozzle_id1+pump_hexStr+Commands.read_lasttransaction+"7f";
                String pump_startCommand="0"+nozzle_id1+pump_hexStr+Commands.pump_startcommands+"7f";
                String pump_stopCommand="0"+nozzle_id1+pump_hexStr+Commands.pumps_stopcommands+"7f";
                String totliser="0"+nozzle_id1+pump_hexStr+Commands.totaliser_command+"7f";
                String clears_sale="0"+nozzle_id1+pump_hexStr+Commands.clear_salecommands+"7f";
                String command=  "0"+nozzle_id1+pump_hexStr+Commands.read_preset+"7f";
                String auth_code = authrisationcommand + Utils.calculateCheckSum((Utils.convertToAscii(authrisationcommand).getBytes()));
                String readtransaction_code = read_transaction + Utils.calculateCheckSum((Utils.convertToAscii(read_transaction).getBytes()));
                String readlasttransaction_code=last_transaction+Utils.calculateCheckSum((Utils.convertToAscii(last_transaction).getBytes()));
                String pumpstart_code=pump_startCommand+Utils.calculateCheckSum((Utils.convertToAscii(pump_startCommand).getBytes()));
                String pumpstop_code=pump_stopCommand+Utils.calculateCheckSum((Utils.convertToAscii(pump_stopCommand).getBytes()));
                String totaliser_code=totliser+Utils.calculateCheckSum((Utils.convertToAscii(totliser).getBytes()));
                String clearsale_code=clears_sale+Utils.calculateCheckSum((Utils.convertToAscii(clears_sale).getBytes()));
                String checkSumCommand = command + Utils.calculateCheckSum((Utils.convertToAscii(command)).getBytes());
                if(status.contains("STOPPED STATE")){
                    nozzleModel.setStatus(status);
                    Bugfender.d(TAG,"Pump2 Stopped state");
                    final String[] retansaction;
                    CommandQueue.TerminateCommandChain();
//                    retansaction = new String[]{pumpstop_code,pumpstart_code};
//                    CommandQueue1 commandQueue1 = new CommandQueue1(retansaction,
//                            sockets,
//                            new OnAllCommandCompleted() {
//                                @Override
//                                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                                }
//                                @Override
//                                public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
//                                    if (response != null) {
//
//                                    }
//
//                                }
//                            });
//                    commandQueue1.doCommandChaining();

                }
                else if(status.contains("IDLE")){
                    count1=0;
                    nozzleModel.setStatus(status);
                    Log.d("pump","pump1");
//                        tot_volume1="0.0";
//                        start_time1="0.0";
//                        end_time1="0.0";
//                        volumeSt1="0.0";
//                    if(count1==0) {
////                        volume_preset1 = String.valueOf(checkpreset(dispenserId));
//                        volume_preset1 = String.valueOf(10.00);
//
//                        Bugfender.d(TAG,"Volume Preset2"+volume_preset1);
//                        Bugfender.d(TAG,"Dispenser2"+nozzle_id1);
//                        Bugfender.d(TAG,"pumpid2"+pump_id1);
//                        Bugfender.d(TAG,"Port2"+port);
//                    }
//                    count1++;

                }
                else if(status.contains("PRESET READY STATE")){
                    nozzleModel.setStatus(status);
                    Bugfender.d(TAG,"Pump2 preset ready state");
                    state=false;

//                    if(!volumeSt1.contains("0.0")) {
                        Bugfender.d(TAG,"Preset Varify2"+volumeSt1);
                        if(count1==0) {
                            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                            start_time1 = df.format(Calendar.getInstance().getTime());
                            Log.d("fueling",""+start_time1);
                            count1++;
                        }
                        sendAuthcode(auth_code,sockets);
//                    }
                }
                else if(status.contains("CALL STATE")){
                    Bugfender.d(TAG,"Pump2 call state");

                    if(count1==0) {
                        DateFormat df = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
                        start_time1 = df.format(Calendar.getInstance().getTime());
                        Log.d("fueling",""+start_time1);
                        count1++;
                    }
                    nozzleModel.setStatus(status);
                    sendAuthcode(auth_code,sockets);
//                        if(!volume_preset1.contains("0.0") || !volume_preset1.contains("0")){
//                            String b = String.format(Locale.US, "%07d", (int) (Double.parseDouble(volume_preset1) * 100));
//                            String commands = "0" + nozzle_id1 + pump_hexStr + Commands.set_presetCommand + "31" + stringToHexWithoutSpace(b) + "7F";
//                            String checkSumCommands = commands + Utils.calculateCheckSum((Utils.convertToAscii(commands)).getBytes());
//                            Log.d("volumescommand2", checkSumCommands + "," + checkSumCommand);
//                            final String[] d = new String[]{checkSumCommands, checkSumCommand};
//                            CommandQueue commandQueue = new CommandQueue(d, sockets, new OnAllCommandCompleted() {
//                                @Override
//                                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                                }
//
//                                @Override
//                                public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
//                                    Log.d("CheckPresetResponse2", response);
//                                    try {
//                                        if (response.length() == 36) {
//                                            String respon = response.substring(12, 32);
//                                            volumeSt1 = Utils.convertToAscii(respon).replaceAll("^0*", "");
//                                            Bugfender.d(TAG,"VolumeSt2"+volumeSt1);
//                                            if (!volumeSt1.contains(".0")|| !volumeSt1.contains(".00")) {
//                                                state=true;
//                                                Bugfender.d(TAG,"VolumeStforAuth2"+volumeSt1);
//                                                sendAuthcode(auth_code, sockets);
//
//                                            }
//                                            Bugfender.d("volumespreset2", volumeSt1);
//                                        }
//
//                                    } catch (IndexOutOfBoundsException e) {
//
//                                    } catch (Exception e) {
//
//                                    }
//
//
//                                }
//                            });
//                            commandQueue.doCommandChaining();
////                        }
////                        else {
////
////                        }
//
//
//                    dispensersAdapter.notifyDataSetChanged();
                }
                else if(status.contains("FUELING STATE")){
                    count1=0;
                    nozzleModel.setStatus(status);
                    dispensersAdapter.notifyDataSetChanged();
                    NozzleModel finalNozzleModel = nozzleModel;
                    final String[] retansaction;
                    retansaction = new String[]{readtransaction_code};
                    CommandQueue1 commandQueue = new CommandQueue1(retansaction, sockets, new OnAllCommandCompleted() {
                        @Override
                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

                        }
                        @Override
                        public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                            try {
                                if (response != null) {
                                    if(response.length()==62) {
                                        int count=0;

                                        String rate_hex = response.substring(8, 22);
                                        String rate_ascii = Utils.hexToAscii(rate_hex).replaceFirst("^0+(?!$)", "");
                                        String volume_hex = response.substring(22, 40);
                                        String volume_ascii = Utils.hexToAscii(volume_hex).replaceFirst("^0+(?!$)", "");
                                        String amount_hex = response.substring(40, 58);
                                        String amount_ascii = Utils.hexToAscii(amount_hex).replaceFirst("^0+(?!$)", "");

                                        finalNozzleModel.setVolume(volume_ascii);
                                        finalNozzleModel.setAmount(amount_ascii);
                                       if(count==0) {
                                           finalNozzleModel.setPrice(rate_ascii);
                                           count++;
                                       }

                                    }

//                                    if (response != null && Utils.countMatches(response, ".") == 3) {
//                                        String value = Utils.encodeHexString(response.getBytes());
//                                        Log.d("fueling", value);
//                                        String respomse = response;
//                                        Log.e("txnreskam", "" + (Utils.findNextOccurance(respomse, ".", 1) > 4) + "" + (Utils.findNextOccurance(respomse, ".", 2) > 13) + "&&" + (Utils.findNextOccurance(respomse, ".", 3) > 22));
//                                        final String txnFuelRate = respomse.substring(4, respomse.indexOf(".") + 3);
//                                        final String txnDispense = respomse.substring(respomse.indexOf(".") + 3, Utils.findNextOccurance(respomse, ".", 2) + 3);
//                                        final String txnCharges = respomse.substring(Utils.findNextOccurance(respomse, ".", 2) + 3, Utils.findNextOccurance(respomse, ".", 3) + 3);
//                                        finalNozzleModel.setVolume(txnDispense);
//                                        finalNozzleModel.setAmount(txnCharges);
//                                        finalNozzleModel.setPrice(txnFuelRate);
//                                    }
                                }
                            }
                            catch (Exception e){

                            }
                        }
                    });
                    commandQueue.doCommandChaining();
                }
                else if(status.contains("PAYABLE STATE")){
                    Bugfender.d(TAG,"Pump2 payable state");
                    Log.d("PravinRai10cs45",tot_volume1+","+start_time1);
                    DateFormat df = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
                    end_time1= df.format(Calendar.getInstance().getTime());
                    nozzleModel.setStatus(status);
//                    dispensersAdapter.notifyDataSetChanged();
//                    CommandQueue.TerminateCommandChain();
//                    final String[] totaliser;

//                    totaliser = new String[]{totaliser_code};
//                    PumpHardwareInfoResponse.Data.DispenserId finalDispenserId = dispenserId;
//                    NozzleModel finalNozzleModel2 = nozzleModel;
//                    TxnCommand1 txnCommand =new TxnCommand1(totaliser, sockets, new OnAllCommandCompleted() {
//                        @Override
//                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//
//                        }
//
//                        @Override
//                        public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
//                            try {
//                                String volumeresponse = response.substring(8, response.indexOf("7f"));
//
//                                String sb=Utils.convertToAscii(volumeresponse);
//                                tot_volume1 = sb.replaceFirst("^0+(?!$)", "");
//                                Bugfender.d(TAG,"second totaliser1"+ tot_volume1);
//                                finalNozzleModel2.setVolume_totaliser(tot_volume1);
//
//
//                            }
//                            catch (ArrayIndexOutOfBoundsException e){
//
//                            }
//                            catch (Exception e){
//
//                            }
//                        }
//                    });
//                    txnCommand.doCommandChaining();

//                    if(!tot_volume1.contains("0.0")) {
                        final String[] refresh;
                        refresh = new String[]{readlasttransaction_code};
                        NozzleModel finalNozzleModel1 = nozzleModel;
                        FinalResponse1 finalResponse = new FinalResponse1(refresh[0], sockets, new OnSingleCommandCompleted() {
                            @Override
                            public void onSingleCommandCompleted(String currentCommand, String response, AsyncSocket socket) {
                                try {

                                    if (response.length() == 152 || response.length() == 120) {
                                        Log.d("PravinFInResponnse",response);

                                        String ammount = response.substring(30, 38);
                                        ammount = "" + Integer.parseInt("" + rev_U(ammount), 16);
                                        ammount = String.valueOf(Double.parseDouble(ammount) / 1000);
                                        String volume = response.substring(38, 46);
                                        volume = "" + Integer.parseInt("" + rev_U(volume), 16);
                                        volume = String.valueOf((Double.parseDouble(volume) / 1000));
                                        String price = response.substring(46, 54);
                                        price = "" + Integer.parseInt("" + rev_U(price), 16);
                                        price = String.valueOf(Double.parseDouble(price) / 1000);
                                        String volumeTotalizer = response.substring(132, 148);
                                        tot_volume1 = "" + Integer.parseInt("" + Utils.rev_U(volumeTotalizer), 16);
                                        tot_volume1 = String.valueOf((Double.parseDouble(volumeTotalizer) / 1000));
                                        Log.d("final result", ammount + ", " + volume + "," + price +","+response);
                                        finalNozzleModel1.setVolume(volume);
                                        finalNozzleModel1.setAmount(ammount);
                                        finalNozzleModel1.setPrice(price);
                                        finalNozzleModel1.setVolume_totaliser(tot_volume1);
//                                        String reciptNo = response.substring(60, 64);
//                                        reciptNo = "" + Integer.parseInt("" + rev_U(reciptNo), 16);
//                                        Log.d("recipt", reciptNo);
//                                        String localID = response.substring(64, 72);
//                                        localID = "" + Integer.parseInt("" + rev_U(localID), 16);
//                                        Log.d("local", localID);
//                                        String date = response.substring(74, 80);
//                                        String day = date.substring(0, 2);
//                                        String month1 = date.substring(2, 4);
//                                        String year = date.substring(4, 6);
//                                        date = Integer.parseInt("" + rev_U(day), 16) + "/" + Integer.parseInt("" + rev_U(month1), 16) + "/" + Integer.parseInt("" + rev_U(year), 16);
//                                        Log.d("date", date);
//                                        String time = response.substring(80, 84);
//                                        String hour = response.substring(0, 2);
//                                        String min = response.substring(2, 4);
//                                        time = Integer.parseInt("" + rev_U(hour), 16) + ":" + Integer.parseInt("" + rev_U(min), 16);

                                        Long tsLong = System.currentTimeMillis() / 1000;
                                        String ts = tsLong.toString();
                                        TransactionModel transactionModel = new TransactionModel();
                                        transactionModel.setAmount(ammount);
                                        transactionModel.setTransaction_id(ts);
                                        transactionModel.setStatus(false);
                                        transactionModel.setVolume(volume);
                                        transactionModel.setRate(price);
                                        transactionModel.setDispenser_id(pump_id1);
                                        transactionModel.setTotaliser(tot_volume1);

                                        if(!start_time1.equalsIgnoreCase("")){
                                            transactionModel.setStart_time(start_time1);
                                        }
                                        else {
                                            transactionModel.setStart_time(end_time1);
                                        }
                                        transactionModel.setEnd_time(end_time1);
                                        finalNozzleModel1.setEnd_time(end_time1);

                                        if (!volume.contains("0.0") || !volume.contains("0")) {
                                            new SaveTask(transactionModel).execute();
                                            idealState(clearsale_code, sockets);
                                        }

                                    }
                                }
                                catch (Exception e){
                                    Log.d("message",e.getMessage());
                                }
                            }
                        });
                        finalResponse.doCommandChaining();
                    }
                }

//            }



        };
        // for third tokheium
        tokheiumReceiver2=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String status = intent.getStringExtra("Status");
                int port= intent.getIntExtra("Port",0);
                AsyncSocket   sockets = Utils.getValueByKey(socketStore, port);
                PumpHardwareInfoResponse.Data.DispenserId  dispenserId=null;
                PumpHardwareInfoResponse.Data.TankDatum tankDatum=null;
                NozzleModel nozzleModel;
                nozzleModel=null;
                if(port!=0) {
                    dispenserId=Utils.getDispenser(data.getDispenserId(), port);
                    if(dispenserId==null){
                        tankDatum=Utils.getTank(data.getTankData(),port) ;
                    }
                    else {
                        nozzleModel= Utils.getNozzle(dispenserModels,port)  ;
                        nozzleModel.setStatus(status);
                        dispensersAdapter.notifyDataSetChanged();
                    }
                }
                pump_id2=nozzleModel.getPumpId();
                String binaryString=  "010"+Utils.intToBinary(1,5);
                int decimal = Integer.parseInt(binaryString,2);
                String pump_hexStr = Integer.toString(decimal,16);
                nozzle_id2=nozzleModel.getNozzleId();
                String authrisationcommand="0"+nozzle_id2+pump_hexStr+Commands.authrization_command+"7f";
                String read_transaction="0"+nozzle_id2+pump_hexStr+Commands.read_transaction+"7f";
                String last_transaction="0"+nozzle_id2+pump_hexStr+Commands.read_lasttransaction+"7f";
                String pump_startCommand="0"+nozzle_id2+pump_hexStr+Commands.pump_startcommands+"7f";
                String pump_stopCommand="0"+nozzle_id2+pump_hexStr+Commands.pumps_stopcommands+"7f";
                String totliser="0"+nozzle_id2+pump_hexStr+Commands.totaliser_command+"7f";
                String clears_sale="0"+nozzle_id2+pump_hexStr+Commands.clear_salecommands+"7f";
                String command=  "0"+nozzle_id2+pump_hexStr+Commands.read_preset+"7f";
                String auth_code = authrisationcommand + Utils.calculateCheckSum((Utils.convertToAscii(authrisationcommand).getBytes()));
                String readtransaction_code = read_transaction + Utils.calculateCheckSum((Utils.convertToAscii(read_transaction).getBytes()));
                String readlasttransaction_code=last_transaction+Utils.calculateCheckSum((Utils.convertToAscii(last_transaction).getBytes()));
                String pumpstart_code=pump_startCommand+Utils.calculateCheckSum((Utils.convertToAscii(pump_startCommand).getBytes()));
                String pumpstop_code=pump_stopCommand+Utils.calculateCheckSum((Utils.convertToAscii(pump_stopCommand).getBytes()));
                String totaliser_code=totliser+Utils.calculateCheckSum((Utils.convertToAscii(totliser).getBytes()));
                String clearsale_code=clears_sale+Utils.calculateCheckSum((Utils.convertToAscii(clears_sale).getBytes()));
                String checkSumCommand = command + Utils.calculateCheckSum((Utils.convertToAscii(command)).getBytes());
                if(status.contains("STOPPED STATE")){
                    Bugfender.d(TAG,"Pump3 Stopped state");
                    final String[] retansaction;
                    CommandQueue2.TerminateCommandChain();
                    retansaction = new String[]{pumpstop_code,pumpstart_code};
                    CommandQueue2 commandQueue1 = new CommandQueue2(retansaction,
                            sockets,
                            new OnAllCommandCompleted() {
                                @Override
                                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                                }
                                @Override
                                public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                                    if (response != null) {

                                    }

                                }
                            });
                    commandQueue1.doCommandChaining();

                }
                else if(status.contains("IDLE")){
                    Log.d("pump","pump2");
                    tot_volume2="0.0";
                    start_time2="0.0";
                    volumeSt2="0.0";
                     end_time2="0.0";
                    if(count2==0) {

                        volume_preset2 = String.valueOf(20.00);
                        Bugfender.d(TAG,"Volume Preset3"+volume_preset2);
                        Bugfender.d(TAG,"Dispenser3"+nozzle_id2+","+dispenserId);
                        Bugfender.d(TAG,"pumpid3"+pump_id2);
                        Bugfender.d(TAG,"Port3"+port);

                    }
                    count2++;


                }
                else if(status.contains("PRESET READY STATE")) {
                    Bugfender.d(TAG,"Pump3 preset ready state");
                    count2 = 0;
                    state = false;
                    if (!volumeSt2.contains("0.0")) {
                        Bugfender.d(TAG,"Preset Varify3"+volumeSt2);
                        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                        start_time2 = df.format(Calendar.getInstance().getTime());
                        sendAuthcode(auth_code, sockets);
                    }
                }
                else if(status.contains("CALL STATE")){
                    count2=0;
                    Bugfender.d(TAG,"Pump3 call state");
                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                    start_time2 = df.format(Calendar.getInstance().getTime());
                    nozzleModel.setStatus(status);

                        Log.d("volume_find",volume_preset2);
//                        if(!volume_preset2.contains("0.0")|| !volume_preset2.contains("0")) {
                            String b = String.format(Locale.US, "%07d", (int) (Double.parseDouble(volume_preset2) * 100));
                            String commands = "0" + nozzle_id2 + pump_hexStr + Commands.set_presetCommand + "31" + stringToHexWithoutSpace(b) + "7F";
                            String checkSumCommands = commands + Utils.calculateCheckSum((Utils.convertToAscii(commands)).getBytes());
                            Log.d("volumescommand", checkSumCommands + "," + checkSumCommand);
                            final String[] d = new String[]{checkSumCommands, checkSumCommand};
                            CommandQueue commandQueue = new CommandQueue(d, sockets, new OnAllCommandCompleted() {
                                @Override
                                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                                }

                                @Override
                                public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                                    Log.d("CheckPresetResponse", response);
                                    try {
                                        if (response.length() == 36) {
                                            String respon = response.substring(12, 32);
                                            volumeSt2 = Utils.convertToAscii(respon).replaceAll("^0*", "");
                                            Bugfender.d(TAG,"VolumeSt3"+volumeSt2);
                                            if (!volumeSt2.contains(".0")|| !volumeSt2.contains(".00")) {
                                                state=true;
                                                Bugfender.d(TAG,"VolumeStforAuth3"+volumeSt2);
                                                sendAuthcode(auth_code, sockets);
                                            }
                                        }

                                    } catch (IndexOutOfBoundsException e) {


                                    } catch (Exception e) {


                                    }


                                }
                            });
                            commandQueue.doCommandChaining();
//                        }
//                        else {
//
//                        }

                    dispensersAdapter.notifyDataSetChanged();
                }
                else if(status.contains("FUELING STATE")){
                    nozzleModel.setStatus(status);
                    dispensersAdapter.notifyDataSetChanged();
                    NozzleModel finalNozzleModel = nozzleModel;
                    final String[] retansaction;
                    retansaction = new String[]{readtransaction_code};
                    CommandQueue2 commandQueue = new CommandQueue2(retansaction, sockets, new OnAllCommandCompleted() {
                        @Override
                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

                        }
                        @Override
                        public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                            try {
                                if (response != null) {
                                    if (response != null && Utils.countMatches(response, ".") == 3) {
                                        String value = Utils.encodeHexString(response.getBytes());
                                        Log.d("fueling", value);
                                        String respomse = response;
                                        Log.e("txnreskam", "" + (Utils.findNextOccurance(respomse, ".", 1) > 4) + "" + (Utils.findNextOccurance(respomse, ".", 2) > 13) + "&&" + (Utils.findNextOccurance(respomse, ".", 3) > 22));
                                        final String txnFuelRate = respomse.substring(4, respomse.indexOf(".") + 3);
                                        final String txnDispense = respomse.substring(respomse.indexOf(".") + 3, Utils.findNextOccurance(respomse, ".", 2) + 3);
                                        final String txnCharges = respomse.substring(Utils.findNextOccurance(respomse, ".", 2) + 3, Utils.findNextOccurance(respomse, ".", 3) + 3);
                                        finalNozzleModel.setVolume(txnDispense);
                                        finalNozzleModel.setAmount(txnCharges);
                                        finalNozzleModel.setPrice(txnFuelRate);
                                    }
                                }
                            }
                            catch (Exception e){
                                Log.d("Exception",e.getMessage());
                            }
                        }
                    });
                    commandQueue.doCommandChaining();
                }
                else if(status.contains("PAYABLE STATE")){
                    Bugfender.d(TAG,"Pump3 payable state");
                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                    end_time2= df.format(Calendar.getInstance().getTime());
                    nozzleModel.setStatus(status);
                    dispensersAdapter.notifyDataSetChanged();
                    CommandQueue.TerminateCommandChain();
                    final String[] totaliser;

                    totaliser = new String[]{totaliser_code};
                    PumpHardwareInfoResponse.Data.DispenserId finalDispenserId = dispenserId;
                    NozzleModel finalNozzleModel2 = nozzleModel;
                    TxnCommand2 txnCommand =new TxnCommand2(totaliser, sockets, new OnAllCommandCompleted() {
                        @Override
                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

                        }

                        @Override
                        public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                            try {
                                String volumeresponse = response.substring(8, response.indexOf("7f"));
                                Log.d("responsehello",volumeresponse);
                                String sb=Utils.convertToAscii(volumeresponse);
                                tot_volume2 = sb.replaceFirst("^0+(?!$)", "");
                                finalNozzleModel2.setVolume_totaliser(tot_volume2);
                                Bugfender.d(TAG,"third totaliser1"+ tot_volume2);

                            }
                            catch (ArrayIndexOutOfBoundsException e){
                                Log.d("derrr", e.getLocalizedMessage());
                            }
                            catch (NullPointerException e){
                                Log.d("derrr", e.getLocalizedMessage());
                            }
                            catch (NumberFormatException e){
                                Log.d("derrr", e.getLocalizedMessage());
                            }
                            catch (Exception e){
                                Log.d("derrr", e.getLocalizedMessage());
                            }
                        }
                    });
                    txnCommand.doCommandChaining();

                    if(!tot_volume2.contains("0.0") || !tot_volume2.contains("0")) {
                        final String[] refresh;
                        refresh = new String[]{readlasttransaction_code};
                        NozzleModel finalNozzleModel1 = nozzleModel;
                        FinalResponse2 finalResponse = new FinalResponse2(refresh[0], sockets, new OnSingleCommandCompleted() {
                            @Override
                            public void onSingleCommandCompleted(String currentCommand, String response, AsyncSocket socket) {
                                try {

                                    if (response.length() == 152 || response.length() == 120) {
                                        idealState(clearsale_code, sockets);
                                        String ammount = response.substring(30, 38);
                                        ammount = "" + Integer.parseInt("" + rev_U(ammount), 16);
                                        ammount = String.valueOf(Double.parseDouble(ammount) / 1000);
                                        String volume = response.substring(38, 46);
                                        volume = "" + Integer.parseInt("" + rev_U(volume), 16);
                                        volume = String.valueOf((Double.parseDouble(volume) / 1000));
                                        String price = response.substring(46, 54);
                                        price = "" + Integer.parseInt("" + rev_U(price), 16);
                                        price = String.valueOf(Double.parseDouble(price) / 1000);
                                        Log.d("final result", ammount + ", " + volume + "," + price +","+response);
                                        finalNozzleModel1.setVolume(volume);
                                        finalNozzleModel1.setAmount(ammount);
                                        finalNozzleModel1.setPrice(price);
                                        String reciptNo = response.substring(60, 64);
                                        reciptNo = "" + Integer.parseInt("" + rev_U(reciptNo), 16);
                                        Log.d("recipt", reciptNo);
                                        String localID = response.substring(64, 72);
                                        localID = "" + Integer.parseInt("" + rev_U(localID), 16);
                                        Log.d("local", localID);
                                        String date = response.substring(74, 80);
                                        String day = date.substring(0, 2);
                                        String month1 = date.substring(2, 4);
                                        String year = date.substring(4, 6);
                                        date = Integer.parseInt("" + rev_U(day), 16) + "/" + Integer.parseInt("" + rev_U(month1), 16) + "/" + Integer.parseInt("" + rev_U(year), 16);
                                        Log.d("date", date);
                                        String time = response.substring(80, 84);
                                        String hour = response.substring(0, 2);
                                        String min = response.substring(2, 4);
                                        time = Integer.parseInt("" + rev_U(hour), 16) + ":" + Integer.parseInt("" + rev_U(min), 16);
                                        Long tsLong = System.currentTimeMillis() / 1000;
                                        String ts = tsLong.toString();
                                        TransactionModel transactionModel = new TransactionModel();
                                        transactionModel.setAmount(ammount);
                                        transactionModel.setTransaction_id(ts);
                                        transactionModel.setStatus(false);
                                        transactionModel.setVolume(volume);
                                        transactionModel.setRate(price);
                                        transactionModel.setDispenser_id(pump_id2);
                                        transactionModel.setTotaliser(tot_volume2);
                                        transactionModel.setStart_time(start_time2);
                                        transactionModel.setEnd_time(end_time2);
                                        finalNozzleModel1.setEnd_time(end_time2);
                                        if (!volume.contains("0.0") || !volume.contains("0")) {
                                            new SaveTask(transactionModel).execute();
                                        }
                                    }
                                }
                                catch (Exception e){
                                    Log.d("d", e.getLocalizedMessage());
                                }
                            }
                        });
                        finalResponse.doCommandChaining();
                    }
                }

            }
        };



        // for fourth  tokheium
        tokheiumReceiver3=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String status = intent.getStringExtra("Status");
                int port= intent.getIntExtra("Port",0);
                AsyncSocket   sockets = Utils.getValueByKey(socketStore, port);
                PumpHardwareInfoResponse.Data.DispenserId  dispenserId=null;
                PumpHardwareInfoResponse.Data.TankDatum tankDatum=null;
                NozzleModel nozzleModel;
                nozzleModel=null;
                if(port!=0) {
                    dispenserId=Utils.getDispenser(data.getDispenserId(), port);
                    Log.i("findd",""+dispenserId);
                    if(dispenserId==null){
                        tankDatum=Utils.getTank(data.getTankData(),port) ;
                        Log.i("findt",""+tankDatum);
                    }
                    else {
                        nozzleModel= Utils.getNozzle(dispenserModels,port)  ;
                        nozzleModel.setStatus(status);
                        dispensersAdapter.notifyDataSetChanged();
                    }
                }
                pump_id3=nozzleModel.getPumpId();
                String binaryString=  "010"+Utils.intToBinary(1,5);
                int decimal = Integer.parseInt(binaryString,2);
                String pump_hexStr = Integer.toString(decimal,16);
                Log.d("hex",pump_hexStr);



                nozzle_id3=nozzleModel.getNozzleId();
                Log.d("getS",status);
                String authrisationcommand="0"+nozzle_id3+pump_hexStr+Commands.authrization_command+"7f";
                String read_transaction="0"+nozzle_id3+pump_hexStr+Commands.read_transaction+"7f";
                String last_transaction="0"+nozzle_id3+pump_hexStr+Commands.read_lasttransaction+"7f";
                String pump_startCommand="0"+nozzle_id3+pump_hexStr+Commands.pump_startcommands+"7f";
                String pump_stopCommand="0"+nozzle_id3+pump_hexStr+Commands.pumps_stopcommands+"7f";
                String totliser="0"+nozzle_id3+pump_hexStr+Commands.totaliser_command+"7f";
                String clears_sale="0"+nozzle_id3+pump_hexStr+Commands.clear_salecommands+"7f";
                String command=  "0"+nozzle_id3+pump_hexStr+Commands.read_preset+"7f";

                String auth_code = authrisationcommand + Utils.calculateCheckSum((Utils.convertToAscii(authrisationcommand).getBytes()));
                String readtransaction_code = read_transaction + Utils.calculateCheckSum((Utils.convertToAscii(read_transaction).getBytes()));
                String readlasttransaction_code=last_transaction+Utils.calculateCheckSum((Utils.convertToAscii(last_transaction).getBytes()));
                String pumpstart_code=pump_startCommand+Utils.calculateCheckSum((Utils.convertToAscii(pump_startCommand).getBytes()));
                String pumpstop_code=pump_stopCommand+Utils.calculateCheckSum((Utils.convertToAscii(pump_stopCommand).getBytes()));
                String totaliser_code=totliser+Utils.calculateCheckSum((Utils.convertToAscii(totliser).getBytes()));
                String clearsale_code=clears_sale+Utils.calculateCheckSum((Utils.convertToAscii(clears_sale).getBytes()));
                String checkSumCommand = command + Utils.calculateCheckSum((Utils.convertToAscii(command)).getBytes());
                if(status.contains("STOPPED STATE")){
                    Bugfender.d(TAG,"Pump4 Stopped state");
                    final String[] retansaction;
                    CommandQueue3.TerminateCommandChain();
                    retansaction = new String[]{pumpstop_code,pumpstart_code};
                    CommandQueue3 commandQueue1 = new CommandQueue3(retansaction,
                            sockets,
                            new OnAllCommandCompleted() {
                                @Override
                                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                                }
                                @Override
                                public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                                    if (response != null) {
                                        Log.d("responsettt", response);
                                    }

                                }
                            });
                    commandQueue1.doCommandChaining();

                }
                else if(status.contains("IDLE")){
                    tot_volume3="0.0";
                    start_time3="";
                    end_time3="";
                    volumeSt3="0.0";
                    Log.d("pump","pump3");
                    if(count3==0) {
//                        volume_preset3 = String.valueOf(checkpreset(dispenserId));
                        volume_preset3 = String.valueOf(15.00);
                        Bugfender.d(TAG,"Volume Preset4"+volume_preset3);
                        Bugfender.d(TAG,"Dispenser4"+dispenserId);
                        Bugfender.d(TAG,"pumpid4"+pump_id3);
                        Bugfender.d(TAG,"Port4"+port);
                    }
                    count3++;

                }
                else if(status.contains("PRESET READY STATE")){
                    Bugfender.d(TAG,"Pump4 preset ready state");
                    count3=0;
                    state=false;
                    if(!volumeSt3.contains("0.0")) {
                        Bugfender.d(TAG,"Preset Varify4"+volumeSt3);
                        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                        start_time3 = df.format(Calendar.getInstance().getTime());
                        sendAuthcode(auth_code, sockets);
                    }
                }
                else if(status.contains("CALL STATE")){
                    count3=0;
                    Bugfender.d(TAG,"Pump4 call state");
                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                    start_time3 = df.format(Calendar.getInstance().getTime());
                    nozzleModel.setStatus(status);

                        Log.d("volume_find",volume_preset);
//                        if(!volume_preset3.contains("0.0") || !volume_preset3.contains("0")) {
                            String b = String.format(Locale.US, "%07d", (int) (Double.parseDouble(volume_preset3) * 100));
                            String commands = "0" + nozzle_id3 + pump_hexStr + Commands.set_presetCommand + "31" + stringToHexWithoutSpace(b) + "7F";
                            String checkSumCommands = commands + Utils.calculateCheckSum((Utils.convertToAscii(commands)).getBytes());
                            Log.d("volumescommand", checkSumCommands + "," + checkSumCommand);
                            final String[] d = new String[]{checkSumCommands, checkSumCommand};
                            CommandQueue commandQueue = new CommandQueue(d, sockets, new OnAllCommandCompleted() {
                                @Override
                                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                                }

                                @Override
                                public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                                    Log.d("CheckPresetResponse", response);
                                    try {
                                        if (response.length() == 36) {
                                            String respon = response.substring(12, 32);
                                            volumeSt3 = Utils.convertToAscii(respon).replaceAll("^0*", "");
                                            Bugfender.d(TAG,"VolumeSt4"+volumeSt3);
                                            if (!volumeSt3.contains(".0")|| !volumeSt3.contains(".00")) {
                                                state=true;
                                                Bugfender.d(TAG,"VolumeStforAuth4"+volumeSt3);
                                                sendAuthcode(auth_code, sockets);

                                            }
                                            Log.d("volumespreset", volumeSt);
//
                                        }

                                    } catch (IndexOutOfBoundsException e) {

                                    } catch (Exception e) {

                                    }


                                }
                            });
                            commandQueue.doCommandChaining();
//                        }
//                        else {
//                            Toast.makeText(getActivity(),"Preset Volume not found",Toast.LENGTH_LONG).show();
//                        }

                    dispensersAdapter.notifyDataSetChanged();
                }
                else if(status.contains("FUELING STATE")){
                    nozzleModel.setStatus(status);
                    dispensersAdapter.notifyDataSetChanged();
                    NozzleModel finalNozzleModel = nozzleModel;
                    final String[] retansaction;
                    retansaction = new String[]{readtransaction_code};
                    CommandQueue3 commandQueue = new CommandQueue3(retansaction, sockets, new OnAllCommandCompleted() {
                        @Override
                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

                        }
                        @Override
                        public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                            try {
                                if (response != null) {
                                    if (response != null && Utils.countMatches(response, ".") == 3) {
                                        String value = Utils.encodeHexString(response.getBytes());
                                        Log.d("fueling", value);
                                        String respomse = response;
                                        Log.e("txnreskam", "" + (Utils.findNextOccurance(respomse, ".", 1) > 4) + "" + (Utils.findNextOccurance(respomse, ".", 2) > 13) + "&&" + (Utils.findNextOccurance(respomse, ".", 3) > 22));
                                        final String txnFuelRate = respomse.substring(4, respomse.indexOf(".") + 3);
                                        final String txnDispense = respomse.substring(respomse.indexOf(".") + 3, Utils.findNextOccurance(respomse, ".", 2) + 3);
                                        final String txnCharges = respomse.substring(Utils.findNextOccurance(respomse, ".", 2) + 3, Utils.findNextOccurance(respomse, ".", 3) + 3);
                                        finalNozzleModel.setVolume(txnDispense);
                                        finalNozzleModel.setAmount(txnCharges);
                                        finalNozzleModel.setPrice(txnFuelRate);
                                    }
                                }
                            }
                            catch (Exception e){
                                Log.d("d", e.getLocalizedMessage());
                            }
                        }
                    });
                    commandQueue.doCommandChaining();
                }
                else if(status.contains("PAYABLE STATE")){
                    Bugfender.d(TAG,"Pump4 payable state");
                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                    end_time3= df.format(Calendar.getInstance().getTime());
                    nozzleModel.setStatus(status);
                    dispensersAdapter.notifyDataSetChanged();
                    CommandQueue3.TerminateCommandChain();
                    final String[] totaliser;

                    totaliser = new String[]{totaliser_code};
                    PumpHardwareInfoResponse.Data.DispenserId finalDispenserId = dispenserId;
                    NozzleModel finalNozzleModel2 = nozzleModel;
                    TxnCommand3 txnCommand =new TxnCommand3(totaliser, sockets, new OnAllCommandCompleted() {
                        @Override
                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

                        }

                        @Override
                        public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                            try {
                                String volumeresponse = response.substring(8, response.indexOf("7f"));
                                Log.d("response",volumeresponse);
                                String sb=Utils.convertToAscii(volumeresponse);
                                tot_volume3 = sb.replaceFirst("^0+(?!$)", "");
                                finalNozzleModel2.setVolume_totaliser(tot_volume3);
                                Bugfender.d(TAG,"fourth totaliser1"+ tot_volume3);


                            }
                            catch (ArrayIndexOutOfBoundsException e){
                                Log.d("derr", e.getLocalizedMessage());
                            }
                            catch (Exception e){
                                Log.d("derrr", e.getLocalizedMessage());
                            }
                        }
                    });
                    txnCommand.doCommandChaining();

                    if(!tot_volume3.contains("0.0")) {
                        final String[] refresh;
                        refresh = new String[]{readlasttransaction_code};
                        NozzleModel finalNozzleModel1 = nozzleModel;
                        FinalResponse3 finalResponse = new FinalResponse3(refresh[0], sockets, new OnSingleCommandCompleted() {
                            @Override
                            public void onSingleCommandCompleted(String currentCommand, String response, AsyncSocket socket) {
                                try {
                                    if (response.length() == 152 || response.length() == 120) {
                                        idealState(clearsale_code, sockets);
                                        String ammount = response.substring(30, 38);
                                        ammount = "" + Integer.parseInt("" + rev_U(ammount), 16);
                                        ammount = String.valueOf(Double.parseDouble(ammount) / 1000);
                                        String volume = response.substring(38, 46);
                                        volume = "" + Integer.parseInt("" + rev_U(volume), 16);
                                        volume = String.valueOf((Double.parseDouble(volume) / 1000));
                                        String price = response.substring(46, 54);
                                        price = "" + Integer.parseInt("" + rev_U(price), 16);
                                        price = String.valueOf(Double.parseDouble(price) / 1000);
                                        Log.d("final result", ammount + ", " + volume + "," + price +","+response);
                                        finalNozzleModel1.setVolume(volume);
                                        finalNozzleModel1.setAmount(ammount);
                                        finalNozzleModel1.setPrice(price);
                                        String reciptNo = response.substring(60, 64);
                                        reciptNo = "" + Integer.parseInt("" + rev_U(reciptNo), 16);
                                        Log.d("recipt", reciptNo);
                                        String localID = response.substring(64, 72);
                                        localID = "" + Integer.parseInt("" + rev_U(localID), 16);
                                        Log.d("local", localID);
                                        String date = response.substring(74, 80);
                                        String day = date.substring(0, 2);
                                        String month1 = date.substring(2, 4);
                                        String year = date.substring(4, 6);
                                        date = Integer.parseInt("" + rev_U(day), 16) + "/" + Integer.parseInt("" + rev_U(month1), 16) + "/" + Integer.parseInt("" + rev_U(year), 16);
                                        Log.d("date", date);
                                        String time = response.substring(80, 84);
                                        String hour = response.substring(0, 2);
                                        String min = response.substring(2, 4);
                                        time = Integer.parseInt("" + rev_U(hour), 16) + ":" + Integer.parseInt("" + rev_U(min), 16);
                                        Long tsLong = System.currentTimeMillis() / 1000;
                                        String ts = tsLong.toString();
                                        TransactionModel transactionModel = new TransactionModel();
                                        transactionModel.setAmount(ammount);
                                        transactionModel.setTransaction_id(ts);
                                        transactionModel.setStatus(false);
                                        transactionModel.setVolume(volume);
                                        transactionModel.setRate(price);
                                        transactionModel.setDispenser_id(pump_id3);
                                        transactionModel.setTotaliser(tot_volume3);
                                        transactionModel.setStart_time(start_time3);
                                        transactionModel.setEnd_time(end_time3);
                                        finalNozzleModel1.setEnd_time(end_time3);
                                        if (!volume.contains("0.0") || !volume.contains("0")) {
                                            new SaveTask(transactionModel).execute();
                                        }

                                    }
                                }
                                catch (Exception e){
                                    Log.d("d", e.getLocalizedMessage());
                                }
                            }
                        });
                        finalResponse.doCommandChaining();
                    }
                }

            }
        };

        // for fifth tokheium
        tokheiumReceiver4=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String status = intent.getStringExtra("Status");
                int port= intent.getIntExtra("Port",0);
                AsyncSocket   sockets = Utils.getValueByKey(socketStore, port);
                PumpHardwareInfoResponse.Data.DispenserId  dispenserId=null;
                PumpHardwareInfoResponse.Data.TankDatum tankDatum=null;
                NozzleModel nozzleModel;
                nozzleModel=null;
                if(port!=0) {
                    dispenserId=Utils.getDispenser(data.getDispenserId(), port);
                    Log.i("findd",""+dispenserId);
                    if(dispenserId==null){
                        tankDatum=Utils.getTank(data.getTankData(),port) ;
                        Log.i("findt",""+tankDatum);
                    }
                    else {
                        nozzleModel= Utils.getNozzle(dispenserModels,port)  ;
                        nozzleModel.setStatus(status);
                        dispensersAdapter.notifyDataSetChanged();
                    }
                }
                pump_id4=nozzleModel.getPumpId();
                String binaryString=  "010"+Utils.intToBinary(1,5);
                int decimal = Integer.parseInt(binaryString,2);
                String pump_hexStr = Integer.toString(decimal,16);
                Log.d("hex",pump_hexStr);
                nozzle_id4=nozzleModel.getNozzleId();
                Log.d("getS",status);
                String authrisationcommand="0"+nozzle_id4+pump_hexStr+Commands.authrization_command+"7f";
                String read_transaction="0"+nozzle_id4+pump_hexStr+Commands.read_transaction+"7f";
                String last_transaction="0"+nozzle_id4+pump_hexStr+Commands.read_lasttransaction+"7f";
                String pump_startCommand="0"+nozzle_id4+pump_hexStr+Commands.pump_startcommands+"7f";
                String pump_stopCommand="0"+nozzle_id4+pump_hexStr+Commands.pumps_stopcommands+"7f";
                String totliser="0"+nozzle_id4+pump_hexStr+Commands.totaliser_command+"7f";
                String clears_sale="0"+nozzle_id4+pump_hexStr+Commands.clear_salecommands+"7f";
                String command=  "0"+nozzle_id4+pump_hexStr+Commands.read_preset+"7f";

                String auth_code = authrisationcommand + Utils.calculateCheckSum((Utils.convertToAscii(authrisationcommand).getBytes()));
                String readtransaction_code = read_transaction + Utils.calculateCheckSum((Utils.convertToAscii(read_transaction).getBytes()));
                String readlasttransaction_code=last_transaction+Utils.calculateCheckSum((Utils.convertToAscii(last_transaction).getBytes()));
                String pumpstart_code=pump_startCommand+Utils.calculateCheckSum((Utils.convertToAscii(pump_startCommand).getBytes()));
                String pumpstop_code=pump_stopCommand+Utils.calculateCheckSum((Utils.convertToAscii(pump_stopCommand).getBytes()));
                String totaliser_code=totliser+Utils.calculateCheckSum((Utils.convertToAscii(totliser).getBytes()));
                String clearsale_code=clears_sale+Utils.calculateCheckSum((Utils.convertToAscii(clears_sale).getBytes()));
                String checkSumCommand = command + Utils.calculateCheckSum((Utils.convertToAscii(command)).getBytes());
                if(status.contains("STOPPED STATE")){
                    Bugfender.d(TAG,"Pump5 Stopped state");
                    final String[] retansaction;
                    CommandQueue.TerminateCommandChain();
                    retansaction = new String[]{pumpstop_code ,pumpstart_code};
                    CommandQueue4 commandQueue1 = new CommandQueue4(retansaction,
                            sockets,
                            new OnAllCommandCompleted() {
                                @Override
                                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                                }
                                @Override
                                public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                                    if (response != null) {
                                        Log.d("responsettt", response);
                                    }

                                }
                            });
                    commandQueue1.doCommandChaining();

                }
                else if(status.contains("IDLE")){
                    Log.d("pump","pump4");
                    tot_volume4="0.0";
                    start_time4="0.0";
                    end_time4="0.0";
                    volumeSt4="0.0";
                    if(count4==0) {
                        volume_preset4 = String.valueOf(10.00);
                        Bugfender.d(TAG,"Volume Preset5"+volume_preset4);
                        Bugfender.d(TAG,"Dispenser5"+dispenserId);
                        Bugfender.d(TAG,"pumpid5"+pump_id4);
                        Bugfender.d(TAG,"Port5"+port);
                    }
                    count4++;
//

                }
                else if(status.contains("PRESET READY STATE")){
                    Bugfender.d(TAG,"Pump5 preset ready state");
                    state=false;
                    count4=0;
                    if(!volumeSt4.contains("0.0")) {
                        Bugfender.d(TAG,"Preset Varify5"+volumeSt4);
                        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                        start_time4 = df.format(Calendar.getInstance().getTime());
                        final String[] retansaction;
                        retansaction = new String[]{auth_code};
                        CommandQueue4 commandQueue = new CommandQueue4(retansaction, sockets, new OnAllCommandCompleted() {
                            @Override
                            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {


                            }

                            @Override
                            public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                                if (response != null) {
                                    Log.d("respons3", response);
                                }
                            }
                        });
                        commandQueue.doCommandChaining();
                    }
                }
                else if(status.contains("CALL STATE")){
                    Bugfender.d(TAG,"Pump5 call state");
                    nozzleModel.setStatus(status);
                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                    start_time4 = df.format(Calendar.getInstance().getTime());
                    count4=0;
                        Log.d("volume_find",volume_preset);

                            String b = String.format(Locale.US, "%07d", (int) (Double.parseDouble(volume_preset4) * 100));
                            String commands = "0" + nozzle_id4 + pump_hexStr + Commands.set_presetCommand + "31" + stringToHexWithoutSpace(b) + "7F";
                            String checkSumCommands = commands + Utils.calculateCheckSum((Utils.convertToAscii(commands)).getBytes());
                            Log.d("volumescommand", checkSumCommands + "," + checkSumCommand);
                            final String[] d = new String[]{checkSumCommands, checkSumCommand};
                            CommandQueue commandQueue = new CommandQueue(d, sockets, new OnAllCommandCompleted() {
                                @Override
                                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                                }

                                @Override
                                public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                                    Log.d("CheckPresetResponse", response);
                                    try {
                                        if (response.length() == 36) {
                                            String respon = response.substring(12, 32);
                                            volumeSt4 = Utils.convertToAscii(respon).replaceAll("^0*", "");
                                            Bugfender.d(TAG,"VolumeSt5"+volumeSt4);
                                            if (!volumeSt4.contains(".0")|| !volumeSt4.contains(".00")) {
                                                state=true;
                                                Bugfender.d(TAG,"VolumeStforAuth5"+volumeSt4);
                                                sendAuthcode(auth_code, sockets);

                                            }
                                            Log.d("volumespreset", volumeSt);
//
                                        }

                                    } catch (IndexOutOfBoundsException e) {

                                    } catch (Exception e) {

                                    }


                                }
                            });
                            commandQueue.doCommandChaining();

//                        Toast.makeText(getActivity(),"Preset Volume not found",Toast.LENGTH_LONG).show();
                    dispensersAdapter.notifyDataSetChanged();
                }
                else if(status.contains("FUELING STATE")){
                    nozzleModel.setStatus(status);
                    dispensersAdapter.notifyDataSetChanged();
                    NozzleModel finalNozzleModel = nozzleModel;
                    final String[] retansaction;
                    retansaction = new String[]{readtransaction_code};
                    CommandQueue4 commandQueue = new CommandQueue4(retansaction, sockets, new OnAllCommandCompleted() {
                        @Override
                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

                        }
                        @Override
                        public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                            try {
                                if (response != null) {
                                    if (response != null && Utils.countMatches(response, ".") == 3) {
                                        String value = Utils.encodeHexString(response.getBytes());
                                        Log.d("fueling", value);
                                        String respomse = response;
                                        Log.e("txnreskam", "" + (Utils.findNextOccurance(respomse, ".", 1) > 4) + "" + (Utils.findNextOccurance(respomse, ".", 2) > 13) + "&&" + (Utils.findNextOccurance(respomse, ".", 3) > 22));
                                        final String txnFuelRate = respomse.substring(4, respomse.indexOf(".") + 3);
                                        final String txnDispense = respomse.substring(respomse.indexOf(".") + 3, Utils.findNextOccurance(respomse, ".", 2) + 3);
                                        final String txnCharges = respomse.substring(Utils.findNextOccurance(respomse, ".", 2) + 3, Utils.findNextOccurance(respomse, ".", 3) + 3);
                                        finalNozzleModel.setVolume(txnDispense);
                                        finalNozzleModel.setAmount(txnCharges);
                                        finalNozzleModel.setPrice(txnFuelRate);
                                    }
                                }
                            }
                            catch (Exception e){
                                Log.d("d", e.getLocalizedMessage());
                            }
                        }
                    });
                    commandQueue.doCommandChaining();
                }
                else if(status.contains("PAYABLE STATE")){
                    Bugfender.d(TAG,"Pump5 payable state");
                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                    end_time4= df.format(Calendar.getInstance().getTime());
                    nozzleModel.setStatus(status);
                    dispensersAdapter.notifyDataSetChanged();
                    CommandQueue.TerminateCommandChain();
                    final String[] totaliser;

                    totaliser = new String[]{totaliser_code};
                    PumpHardwareInfoResponse.Data.DispenserId finalDispenserId = dispenserId;
                    NozzleModel finalNozzleModel2 = nozzleModel;
                    TxnCommand4 txnCommand =new TxnCommand4(totaliser, sockets, new OnAllCommandCompleted() {
                        @Override
                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

                        }
                        @Override
                        public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                            try {
                                String volumeresponse = response.substring(8, response.indexOf("7f"));
                                Log.d("totaliseroftokheium", volumeresponse);
                                String sb=Utils.convertToAscii(volumeresponse);
                                tot_volume4 = sb.replaceFirst("^0+(?!$)", "");
                                finalNozzleModel2.setVolume_totaliser(tot_volume4);
                                Bugfender.d(TAG,"fifth totaliser1"+ tot_volume4);

                            }
                            catch (ArrayIndexOutOfBoundsException e){
                                Log.d("d", e.getLocalizedMessage());
                            }
                            catch (Exception e){
                                Log.d("d", e.getLocalizedMessage());
                            }
                        }
                    });
                    txnCommand.doCommandChaining();

                    if(!tot_volume4.contains("0.0")) {
                        final String[] refresh;
                        refresh = new String[]{readlasttransaction_code};
                        NozzleModel finalNozzleModel1 = nozzleModel;
                        FinalResponse4 finalResponse = new FinalResponse4(refresh[0], sockets, new OnSingleCommandCompleted() {
                            @Override
                            public void onSingleCommandCompleted(String currentCommand, String response, AsyncSocket socket) {
                                try {
                                    if (response.length() == 152 || response.length() == 120) {
                                        idealState(clearsale_code, sockets);
                                        String ammount = response.substring(30, 38);
                                        ammount = "" + Integer.parseInt("" + rev_U(ammount), 16);
                                        ammount = String.valueOf(Double.parseDouble(ammount) / 1000);
                                        String volume = response.substring(38, 46);
                                        volume = "" + Integer.parseInt("" + rev_U(volume), 16);
                                        volume = String.valueOf((Double.parseDouble(volume) / 1000));
                                        String price = response.substring(46, 54);
                                        price = "" + Integer.parseInt("" + rev_U(price), 16);
                                        price = String.valueOf(Double.parseDouble(price) / 1000);
                                        Log.d("final result", ammount + ", " + volume + "," + price +","+response);
                                        finalNozzleModel1.setVolume(volume);
                                        finalNozzleModel1.setAmount(ammount);
                                        finalNozzleModel1.setPrice(price);
                                        String reciptNo = response.substring(60, 64);
                                        reciptNo = "" + Integer.parseInt("" + rev_U(reciptNo), 16);
                                        Log.d("recipt", reciptNo);
                                        String localID = response.substring(64, 72);
                                        localID = "" + Integer.parseInt("" + rev_U(localID), 16);
                                        Log.d("local", localID);
                                        String date = response.substring(74, 80);
                                        String day = date.substring(0, 2);
                                        String month1 = date.substring(2, 4);
                                        String year = date.substring(4, 6);
                                        date = Integer.parseInt("" + rev_U(day), 16) + "/" + Integer.parseInt("" + rev_U(month1), 16) + "/" + Integer.parseInt("" + rev_U(year), 16);
                                        Log.d("date", date);
                                        String time = response.substring(80, 84);
                                        String hour = response.substring(0, 2);
                                        String min = response.substring(2, 4);
                                        time = Integer.parseInt("" + rev_U(hour), 16) + ":" + Integer.parseInt("" + rev_U(min), 16);

                                        Long tsLong = System.currentTimeMillis() / 1000;
                                        String ts = tsLong.toString();
                                        TransactionModel transactionModel = new TransactionModel();
                                        transactionModel.setAmount(ammount);
                                        transactionModel.setTransaction_id(ts);
                                        transactionModel.setStatus(false);
                                        transactionModel.setVolume(volume);
                                        transactionModel.setRate(price);
                                        transactionModel.setDispenser_id(pump_id4);
                                        transactionModel.setTotaliser(tot_volume4);
                                        transactionModel.setStart_time(start_time4);
                                        transactionModel.setEnd_time(end_time4);
                                        finalNozzleModel1.setEnd_time(end_time4);
                                        if (!volume.contains("0.0") || !volume.contains("0")) {
                                            new SaveTask(transactionModel).execute();
                                        }

                                    }
                                }
                                catch (Exception e){
                                    Log.d("Exception",e.getMessage());
                                }
                            }
                        });
                        finalResponse.doCommandChaining();
                    }
                }

            }
        };

        // for sixth tokheium
        tokheiumReceiver5=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String status = intent.getStringExtra("Status");
                int port= intent.getIntExtra("Port",0);
                AsyncSocket   sockets = Utils.getValueByKey(socketStore, port);
                PumpHardwareInfoResponse.Data.DispenserId  dispenserId=null;
                PumpHardwareInfoResponse.Data.TankDatum tankDatum=null;
                NozzleModel nozzleModel;
                nozzleModel=null;
                if(port!=0) {
                    dispenserId=Utils.getDispenser(data.getDispenserId(), port);
                    Log.i("findd",""+dispenserId);
                    if(dispenserId==null){
                        tankDatum=Utils.getTank(data.getTankData(),port) ;
                        Log.i("findt",""+tankDatum);
                    }
                    else {
                        nozzleModel= Utils.getNozzle(dispenserModels,port)  ;
                        nozzleModel.setStatus(status);
                        dispensersAdapter.notifyDataSetChanged();
                    }
                }

                pump_id5=nozzleModel.getPumpId();
                String binaryString=  "010"+Utils.intToBinary(1,5);
                int decimal = Integer.parseInt(binaryString,2);
                String pump_hexStr = Integer.toString(decimal,16);
                Log.d("hex",pump_hexStr);
                nozzle_id5=nozzleModel.getNozzleId();
                Log.d("getS",status);
                String authrisationcommand="0"+nozzle_id5+pump_hexStr+Commands.authrization_command+"7f";
                String read_transaction="0"+nozzle_id5+pump_hexStr+Commands.read_transaction+"7f";
                String last_transaction="0"+nozzle_id5+pump_hexStr+Commands.read_lasttransaction+"7f";
                String pump_startCommand="0"+nozzle_id5+pump_hexStr+Commands.pump_startcommands+"7f";
                String pump_stopCommand="0"+nozzle_id5+pump_hexStr+Commands.pumps_stopcommands+"7f";
                String totliser="0"+nozzle_id5+pump_hexStr+Commands.totaliser_command+"7f";
                String clears_sale="0"+nozzle_id5+pump_hexStr+Commands.clear_salecommands+"7f";
                String command=  "0"+nozzle_id5+pump_hexStr+Commands.read_preset+"7f";

                String auth_code = authrisationcommand + Utils.calculateCheckSum((Utils.convertToAscii(authrisationcommand).getBytes()));
                String readtransaction_code = read_transaction + Utils.calculateCheckSum((Utils.convertToAscii(read_transaction).getBytes()));
                String readlasttransaction_code=last_transaction+Utils.calculateCheckSum((Utils.convertToAscii(last_transaction).getBytes()));
                String pumpstart_code=pump_startCommand+Utils.calculateCheckSum((Utils.convertToAscii(pump_startCommand).getBytes()));
                String pumpstop_code=pump_stopCommand+Utils.calculateCheckSum((Utils.convertToAscii(pump_stopCommand).getBytes()));
                String totaliser_code=totliser+Utils.calculateCheckSum((Utils.convertToAscii(totliser).getBytes()));
                String clearsale_code=clears_sale+Utils.calculateCheckSum((Utils.convertToAscii(clears_sale).getBytes()));
                String checkSumCommand = command + Utils.calculateCheckSum((Utils.convertToAscii(command)).getBytes());
                if(status.contains("STOPPED STATE")){
                    Bugfender.d(TAG,"Pump6 Stopped state");
                    final String[] retansaction;
                    CommandQueue5.TerminateCommandChain();
                    retansaction = new String[]{pumpstop_code,pumpstart_code};
                    CommandQueue5 commandQueue1 = new CommandQueue5(retansaction,
                            sockets,
                            new OnAllCommandCompleted() {
                                @Override
                                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                                }
                                @Override
                                public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                                    if (response != null) {
                                        Log.d("responsettt", response);
                                    }

                                }
                            });
                    commandQueue1.doCommandChaining();

                }
                else if(status.contains("IDLE")){
                    Log.d("pump","pump5");
                    tot_volume5="0.0";
                    start_time5="0.0";
                     end_time5="0.0";
                     volumeSt5="0.0";
                    if(count5==0) {
                        volume_preset5 = String.valueOf(20.00);
                        Bugfender.d(TAG,"Volume Preset6"+volume_preset5);
                        Bugfender.d(TAG,"Dispenser6"+dispenserId);
                        Bugfender.d(TAG,"pumpid6"+pump_id5);
                        Bugfender.d(TAG,"Port6"+port);
                    }
                    count5++;
//                         nozzleModel.setVolume("0.0");
//                         nozzleModel.setAmount("0.0");
//                         nozzleModel.setPrice("0.0");

                }
                else if(status.contains("PRESET READY STATE")){
                    Bugfender.d(TAG,"Pump6 preset ready state");
                    state=false;
                    count5=0;
                    if(!volumeSt5.contains("0.0")) {
                        Bugfender.d(TAG,"Preset Varify6"+volumeSt6);
                        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                        start_time5 = df.format(Calendar.getInstance().getTime());
                        sendAuthcode(auth_code, sockets);
                    }
                }
                else if(status.contains("CALL STATE")){
                    count5=0;
                    Bugfender.d(TAG,"Pump6 call state");
                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                    start_time5 = df.format(Calendar.getInstance().getTime());
                    nozzleModel.setStatus(status);
                        Log.d("volume_find",volume_preset);
//                        if(!volume_preset5.contains("0.0")||!volume_preset5.contains("0")) {
                            String b = String.format(Locale.US, "%07d", (int) (Double.parseDouble(volume_preset5) * 100));
                            String commands = "0" + nozzle_id5 + pump_hexStr + Commands.set_presetCommand + "31" + stringToHexWithoutSpace(b) + "7F";
                            String checkSumCommands = commands + Utils.calculateCheckSum((Utils.convertToAscii(commands)).getBytes());
                            Log.d("volumescommand", checkSumCommands + "," + checkSumCommand);
                            final String[] d = new String[]{checkSumCommands, checkSumCommand};
                            CommandQueue commandQueue = new CommandQueue(d, sockets, new OnAllCommandCompleted() {
                                @Override
                                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                                }

                                @Override
                                public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                                    Log.d("CheckPresetResponse", response);
                                    try {
                                        if (response.length() == 36) {
                                            String respon = response.substring(12, 32);
                                            volumeSt5 = Utils.convertToAscii(respon).replaceAll("^0*", "");
                                            Bugfender.d(TAG,"VolumeSt6"+volumeSt5);
                                            if (!volumeSt5.contains(".0")|| !volumeSt5.contains(".00")) {
                                                state=true;
                                                Bugfender.d(TAG,"VolumeStforAuth6"+volumeSt5);
                                                sendAuthcode(auth_code, sockets);

                                            }
                                            Log.d("volumespreset", volumeSt);
//
                                        }

                                    } catch (IndexOutOfBoundsException e) {

                                    } catch (Exception e) {

                                    }


                                }
                            });
                            commandQueue.doCommandChaining();
//                        }
//                        else {
//                            Toast.makeText(getActivity(),"Preset Volume not found",Toast.LENGTH_LONG).show();
//                        }

                    dispensersAdapter.notifyDataSetChanged();
                }
                else if(status.contains("FUELING STATE")){
                    nozzleModel.setStatus(status);
                    dispensersAdapter.notifyDataSetChanged();
                    NozzleModel finalNozzleModel = nozzleModel;
                    final String[] retansaction;
                    retansaction = new String[]{readtransaction_code};
                    CommandQueue5 commandQueue = new CommandQueue5(retansaction, sockets, new OnAllCommandCompleted() {
                        @Override
                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

                        }
                        @Override
                        public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                            try {
                                if (response != null) {
                                    if (response != null && Utils.countMatches(response, ".") == 3) {
                                        String value = Utils.encodeHexString(response.getBytes());
                                        Log.d("fueling", value);
                                        String respomse = response;
                                        Log.e("txnreskam", "" + (Utils.findNextOccurance(respomse, ".", 1) > 4) + "" + (Utils.findNextOccurance(respomse, ".", 2) > 13) + "&&" + (Utils.findNextOccurance(respomse, ".", 3) > 22));
                                        final String txnFuelRate = respomse.substring(4, respomse.indexOf(".") + 3);
                                        final String txnDispense = respomse.substring(respomse.indexOf(".") + 3, Utils.findNextOccurance(respomse, ".", 2) + 3);
                                        final String txnCharges = respomse.substring(Utils.findNextOccurance(respomse, ".", 2) + 3, Utils.findNextOccurance(respomse, ".", 3) + 3);
                                        finalNozzleModel.setVolume(txnDispense);
                                        finalNozzleModel.setAmount(txnCharges);
                                        finalNozzleModel.setPrice(txnFuelRate);
                                    }
                                }
                            }
                            catch (Exception e){
                                Log.d("Exception",e.getMessage());
                            }
                        }
                    });
                    commandQueue.doCommandChaining();
                }
                else if(status.contains("PAYABLE STATE")){
                    Bugfender.d(TAG,"Pump6 payable state");
                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                    end_time5= df.format(Calendar.getInstance().getTime());
                    nozzleModel.setStatus(status);
                    dispensersAdapter.notifyDataSetChanged();
                    CommandQueue5.TerminateCommandChain();
                    final String[] totaliser;
                    totaliser = new String[]{totaliser_code};
                    PumpHardwareInfoResponse.Data.DispenserId finalDispenserId = dispenserId;
                    NozzleModel finalNozzleModel2 = nozzleModel;
                    TxnCommand5 txnCommand =new TxnCommand5(totaliser, sockets, new OnAllCommandCompleted() {
                        @Override
                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {


                        }
                        @Override
                        public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                            try {
                                String volumeresponse = response.substring(8, response.indexOf("7f"));
                                Log.d("totaliseroftokheium", volumeresponse);
                                String sb=Utils.convertToAscii(volumeresponse);

                                tot_volume5 = sb.replaceFirst("^0+(?!$)", "");
                                finalNozzleModel2.setVolume_totaliser(tot_volume5);
                                Bugfender.d(TAG,"sixth totaliser1"+ tot_volume5);
                            }
                            catch (ArrayIndexOutOfBoundsException e){
                                Log.d("d", e.getLocalizedMessage());
                            }
                            catch (Exception e){
                                Log.d("d", e.getLocalizedMessage());
                            }
                        }
                    });
                    txnCommand.doCommandChaining();

                    if(!tot_volume5.contains("0.0")) {
                        final String[] refresh;
                        refresh = new String[]{readlasttransaction_code};
                        NozzleModel finalNozzleModel1 = nozzleModel;
                        FinalResponse5 finalResponse = new FinalResponse5(refresh[0], sockets, new OnSingleCommandCompleted() {
                            @Override
                            public void onSingleCommandCompleted(String currentCommand, String response, AsyncSocket socket) {
                                if (response.length() == 152 || response.length() == 120) {
                                    idealState(clearsale_code,sockets);
                                    String ammount = response.substring(30, 38);
                                    ammount = "" + Integer.parseInt("" + rev_U(ammount), 16);
                                    ammount = String.valueOf(Double.parseDouble(ammount) / 1000);
                                    String volume = response.substring(38, 46);
                                    volume = "" + Integer.parseInt("" + rev_U(volume), 16);
                                    volume = String.valueOf((Double.parseDouble(volume) / 1000));
                                    String price = response.substring(46, 54);
                                    price = "" + Integer.parseInt("" + rev_U(price), 16);
                                    price = String.valueOf(Double.parseDouble(price) / 1000);
                                    Log.d("final result", ammount + ", " + volume + "," + price +","+response);
                                    finalNozzleModel1.setVolume(volume);
                                    finalNozzleModel1.setAmount(ammount);
                                    finalNozzleModel1.setPrice(price);
                                    String reciptNo = response.substring(60, 64);
                                    reciptNo = "" + Integer.parseInt("" + rev_U(reciptNo), 16);
                                    Log.d("recipt", reciptNo);
                                    String localID = response.substring(64, 72);
                                    localID = "" + Integer.parseInt("" + rev_U(localID), 16);
                                    Log.d("local", localID);
                                    String date = response.substring(74, 80);
                                    String day = date.substring(0, 2);
                                    String month1 = date.substring(2, 4);
                                    String year = date.substring(4, 6);
                                    date = Integer.parseInt("" + rev_U(day), 16) + "/" + Integer.parseInt("" + rev_U(month1), 16) + "/" + Integer.parseInt("" + rev_U(year), 16);
                                    Log.d("date", date);
                                    String time = response.substring(80, 84);
                                    String hour = response.substring(0, 2);
                                    String min = response.substring(2, 4);
                                    time = Integer.parseInt("" + rev_U(hour), 16) + ":" + Integer.parseInt("" + rev_U(min), 16);


                                    Long tsLong = System.currentTimeMillis()/1000;
                                    String ts = tsLong.toString();
                                    TransactionModel transactionModel=new TransactionModel();
                                    transactionModel.setAmount(ammount);
                                    transactionModel.setTransaction_id(ts);
                                    transactionModel.setStatus(false);
                                    transactionModel.setVolume(volume);
                                    transactionModel.setRate(price);
                                    transactionModel.setDispenser_id(pump_id5);
                                    transactionModel.setTotaliser(tot_volume5);
                                    transactionModel.setStart_time(start_time5);
                                    transactionModel.setEnd_time(end_time5);
                                    finalNozzleModel1.setEnd_time(end_time5);
                                    if (!volume.contains("0.0") || !volume.contains("0")) {
                                        new SaveTask(transactionModel).execute();
                                    }

                                }
                            }
                        });
                        finalResponse.doCommandChaining();
                    }
                }

            }
        };
        // for seventh tokheium
        tokheiumReceiver6=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String status = intent.getStringExtra("Status");
                int port= intent.getIntExtra("Port",0);
                AsyncSocket   sockets = Utils.getValueByKey(socketStore, port);
                PumpHardwareInfoResponse.Data.DispenserId  dispenserId=null;
                PumpHardwareInfoResponse.Data.TankDatum tankDatum=null;
                NozzleModel nozzleModel;
                nozzleModel=null;
                if(port!=0) {
                    dispenserId=Utils.getDispenser(data.getDispenserId(), port);
                    Log.i("findd",""+dispenserId);
                    if(dispenserId==null){
                        tankDatum=Utils.getTank(data.getTankData(),port) ;
                        Log.i("findt",""+tankDatum);
                    }
                    else {
                        nozzleModel= Utils.getNozzle(dispenserModels,port)  ;
                        nozzleModel.setStatus(status);
                        dispensersAdapter.notifyDataSetChanged();
                    }
                }
                pump_id6=nozzleModel.getPumpId();
                String binaryString=  "010"+Utils.intToBinary(1,5);
                int decimal = Integer.parseInt(binaryString,2);
                String pump_hexStr = Integer.toString(decimal,16);
                Log.d("hex",pump_hexStr);
                nozzle_id6=nozzleModel.getNozzleId();
                Log.d("getS",status);
                String authrisationcommand="0"+nozzle_id6+pump_hexStr+Commands.authrization_command+"7f";
                String read_transaction="0"+nozzle_id6+pump_hexStr+Commands.read_transaction+"7f";
                String last_transaction="0"+nozzle_id6+pump_hexStr+Commands.read_lasttransaction+"7f";
                String pump_startCommand="0"+nozzle_id6+pump_hexStr+Commands.pump_startcommands+"7f";
                String pump_stopCommand="0"+nozzle_id6+pump_hexStr+Commands.pumps_stopcommands+"7f";
                String totliser="0"+nozzle_id6+pump_hexStr+Commands.totaliser_command+"7f";
                String clears_sale="0"+nozzle_id6+pump_hexStr+Commands.clear_salecommands+"7f";
                String command=  "0"+nozzle_id6+pump_hexStr+Commands.read_preset+"7f";

                String auth_code = authrisationcommand + Utils.calculateCheckSum((Utils.convertToAscii(authrisationcommand).getBytes()));
                String readtransaction_code = read_transaction + Utils.calculateCheckSum((Utils.convertToAscii(read_transaction).getBytes()));
                String readlasttransaction_code=last_transaction+Utils.calculateCheckSum((Utils.convertToAscii(last_transaction).getBytes()));
                String pumpstart_code=pump_startCommand+Utils.calculateCheckSum((Utils.convertToAscii(pump_startCommand).getBytes()));
                String pumpstop_code=pump_stopCommand+Utils.calculateCheckSum((Utils.convertToAscii(pump_stopCommand).getBytes()));
                String totaliser_code=totliser+Utils.calculateCheckSum((Utils.convertToAscii(totliser).getBytes()));
                String clearsale_code=clears_sale+Utils.calculateCheckSum((Utils.convertToAscii(clears_sale).getBytes()));
                String checkSumCommand = command + Utils.calculateCheckSum((Utils.convertToAscii(command)).getBytes());
                if(status.contains("STOPPED STATE")){
                    Bugfender.d(TAG,"Pump7 Stopped state");
                    final String[] retansaction;
                    CommandQueue.TerminateCommandChain();
                    retansaction = new String[]{pumpstop_code,pumpstart_code};
                    CommandQueue6 commandQueue1 = new CommandQueue6(retansaction,
                            sockets,
                            new OnAllCommandCompleted() {
                                @Override
                                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                                }
                                @Override
                                public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                                    if (response != null) {
                                        Log.d("responsettt", response);
                                    }

                                }
                            });
                    commandQueue1.doCommandChaining();

                }
                else if(status.contains("IDLE")){
                    tot_volume6="0.0";
                    start_time6="";
                     end_time6="";
                     volumeSt6="0.0";
                    if(count6==0) {
                        volume_preset6 = String.valueOf(20.00);
                        Bugfender.d(TAG,"Volume Preset7"+volume_preset6);
                        Bugfender.d(TAG,"Dispenser7"+dispenserId);
                        Bugfender.d(TAG,"pumpid7"+pump_id6);
                        Bugfender.d(TAG,"Port7"+port);
                    }
                    count6++;
//                         nozzleModel.setVolume("0.0");
//                         nozzleModel.setAmount("0.0");
//                         nozzleModel.setPrice("0.0");

                }
                else if(status.contains("PRESET READY STATE")){
                    Bugfender.d(TAG,"Pump7 preset ready state");

                    state=false;
                    count6=0;
                    if(!volumeSt6.contains("0.0")) {
                        Bugfender.d(TAG,"Preset Varify7"+volumeSt6);
                        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                        start_time6 = df.format(Calendar.getInstance().getTime());
                      sendAuthcode(auth_code,sockets);
                    }
                }
                else if(status.contains("CALL STATE")){
                    Bugfender.d(TAG,"Pump7 call state");
                    count6=0;
                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                    start_time6 = df.format(Calendar.getInstance().getTime());
                    nozzleModel.setStatus(status);
//                        if (!volume_preset6.contains("0.0") || !volume_preset6.contains("0")) {
                            String b = String.format(Locale.US, "%07d", (int) (Double.parseDouble(volume_preset6) * 100));
                            String commands = "0" + nozzle_id6 + pump_hexStr + Commands.set_presetCommand + "31" + stringToHexWithoutSpace(b) + "7F";
                            String checkSumCommands = commands + Utils.calculateCheckSum((Utils.convertToAscii(commands)).getBytes());
                            Log.d("volumescommand", checkSumCommands + "," + checkSumCommand);
                            final String[] d = new String[]{checkSumCommands, checkSumCommand};
                            CommandQueue commandQueue = new CommandQueue(d, sockets, new OnAllCommandCompleted() {
                                @Override
                                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                                }

                                @Override
                                public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                                    Log.d("CheckPresetResponse", response);
                                    try {
                                        if (response.length() == 36) {
                                            String respon = response.substring(12, 32);
                                            volumeSt6 = Utils.convertToAscii(respon).replaceAll("^0*", "");
                                            Bugfender.d(TAG,"VolumeSt7"+volumeSt6);
                                            if (!volumeSt6.contains(".0")|| !volumeSt6.contains(".00")) {
                                                state=true;
                                                Bugfender.d(TAG,"VolumeStforAuth7"+volumeSt6);
                                                sendAuthcode(auth_code, sockets);

                                            }
                                            Log.d("volumespreset", volumeSt);
//
                                        }

                                    } catch (IndexOutOfBoundsException e) {

                                    } catch (Exception e) {

                                    }


                                }
                            });
                            commandQueue.doCommandChaining();
//                        }
//                        else {
//                            Toast.makeText(getActivity(),"Preset Volume not found",Toast.LENGTH_LONG).show();
//                        }
                    dispensersAdapter.notifyDataSetChanged();
                }
                else if(status.contains("FUELING STATE")){
                    nozzleModel.setStatus(status);
                    dispensersAdapter.notifyDataSetChanged();
                    NozzleModel finalNozzleModel = nozzleModel;
                    final String[] retansaction;
                    retansaction = new String[]{readtransaction_code};
                    CommandQueue6 commandQueue = new CommandQueue6(retansaction, sockets, new OnAllCommandCompleted() {
                        @Override
                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

                        }
                        @Override
                        public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                            try {
                                if (response != null) {
                                    if (response != null && Utils.countMatches(response, ".") == 3) {
                                        String value = Utils.encodeHexString(response.getBytes());
                                        Log.d("fueling", value);
                                        String respomse = response;
                                        Log.e("txnreskam", "" + (Utils.findNextOccurance(respomse, ".", 1) > 4) + "" + (Utils.findNextOccurance(respomse, ".", 2) > 13) + "&&" + (Utils.findNextOccurance(respomse, ".", 3) > 22));
                                        final String txnFuelRate = respomse.substring(4, respomse.indexOf(".") + 3);
                                        final String txnDispense = respomse.substring(respomse.indexOf(".") + 3, Utils.findNextOccurance(respomse, ".", 2) + 3);
                                        final String txnCharges = respomse.substring(Utils.findNextOccurance(respomse, ".", 2) + 3, Utils.findNextOccurance(respomse, ".", 3) + 3);
                                        finalNozzleModel.setVolume(txnDispense);
                                        finalNozzleModel.setAmount(txnCharges);
                                        finalNozzleModel.setPrice(txnFuelRate);
                                    }
                                }
                            }
                            catch (Exception e){
                                Log.d("Exception",e.getMessage());
                            }
                        }
                    });
                    commandQueue.doCommandChaining();
                }
                else if(status.contains("PAYABLE STATE")){
                    Bugfender.d(TAG,"Pump8 payable state");
                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                    end_time6= df.format(Calendar.getInstance().getTime());
                    nozzleModel.setStatus(status);
                    dispensersAdapter.notifyDataSetChanged();
                    CommandQueue6.TerminateCommandChain();
                    final String[] totaliser;

                    totaliser = new String[]{totaliser_code};
                    PumpHardwareInfoResponse.Data.DispenserId finalDispenserId = dispenserId;
                    NozzleModel finalNozzleModel2 = nozzleModel;
                    TxnCommand6 txnCommand =new TxnCommand6(totaliser, sockets, new OnAllCommandCompleted() {
                        @Override
                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

                        }

                        @Override
                        public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                            try {
                                String volumeresponse = response.substring(8, response.indexOf("7f"));
                                Log.d("totaliseroftokheium", volumeresponse);
                                String sb=Utils.convertToAscii(volumeresponse);
                                tot_volume6 = sb.replaceFirst("^0+(?!$)", "");
                                finalNozzleModel2.setVolume_totaliser(tot_volume6);
                                Bugfender.d(TAG,"seventh totaliser1"+ tot_volume6);
                            }
                            catch (ArrayIndexOutOfBoundsException e){
                                Log.d("d", e.getLocalizedMessage());
                            }
                            catch (Exception e){
                                Log.d("d", e.getLocalizedMessage());
                            }
                        }
                    });
                    txnCommand.doCommandChaining();

                    if(!tot_volume6.contains("0.0")) {
                        final String[] refresh;
                        refresh = new String[]{readlasttransaction_code};
                        NozzleModel finalNozzleModel1 = nozzleModel;
                        FinalResponse6 finalResponse = new FinalResponse6(refresh[0], sockets, new OnSingleCommandCompleted() {
                            @Override
                            public void onSingleCommandCompleted(String currentCommand, String response, AsyncSocket socket) {
                                try {
                                    if (response.length() == 152 || response.length() == 120) {
                                        idealState(clearsale_code, sockets);
                                        String ammount = response.substring(30, 38);
                                        ammount = "" + Integer.parseInt("" + rev_U(ammount), 16);
                                        ammount = String.valueOf(Double.parseDouble(ammount) / 1000);
                                        String volume = response.substring(38, 46);
                                        volume = "" + Integer.parseInt("" + rev_U(volume), 16);
                                        volume = String.valueOf((Double.parseDouble(volume) / 1000));
                                        String price = response.substring(46, 54);
                                        price = "" + Integer.parseInt("" + rev_U(price), 16);
                                        price = String.valueOf(Double.parseDouble(price) / 1000);
                                        Log.d("final result", ammount + ", " + volume + "," + price +","+response);
                                        finalNozzleModel1.setVolume(volume);
                                        finalNozzleModel1.setAmount(ammount);
                                        finalNozzleModel1.setPrice(price);
                                        String reciptNo = response.substring(60, 64);
                                        reciptNo = "" + Integer.parseInt("" + rev_U(reciptNo), 16);
                                        Log.d("recipt", reciptNo);
                                        String localID = response.substring(64, 72);
                                        localID = "" + Integer.parseInt("" + rev_U(localID), 16);
                                        Log.d("local", localID);
                                        String date = response.substring(74, 80);
                                        String day = date.substring(0, 2);
                                        String month1 = date.substring(2, 4);
                                        String year = date.substring(4, 6);
                                        date = Integer.parseInt("" + rev_U(day), 16) + "/" + Integer.parseInt("" + rev_U(month1), 16) + "/" + Integer.parseInt("" + rev_U(year), 16);
                                        Log.d("date", date);
                                        String time = response.substring(80, 84);
                                        String hour = response.substring(0, 2);
                                        String min = response.substring(2, 4);
                                        time = Integer.parseInt("" + rev_U(hour), 16) + ":" + Integer.parseInt("" + rev_U(min), 16);


                                        Long tsLong = System.currentTimeMillis() / 1000;
                                        String ts = tsLong.toString();
                                        TransactionModel transactionModel = new TransactionModel();
                                        transactionModel.setAmount(ammount);
                                        transactionModel.setTransaction_id(ts);
                                        transactionModel.setStatus(false);
                                        transactionModel.setVolume(volume);
                                        transactionModel.setRate(price);
                                        transactionModel.setDispenser_id(pump_id6);
                                        transactionModel.setTotaliser(tot_volume6);
                                        transactionModel.setStart_time(start_time6);
                                        transactionModel.setEnd_time(end_time6);
                                        finalNozzleModel1.setEnd_time(end_time6);
                                        if (!volume.contains("0.0") || !volume.contains("0")) {
                                            new SaveTask(transactionModel).execute();
                                        }

                                    }


                                }
                                catch (Exception e){
                                    Log.d("Exception",e.getMessage());
                                }
                            }
                        });
                        finalResponse.doCommandChaining();
                    }
                }

            }
        };

        // for eight tokheium
        tokheiumReceiver7=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String status = intent.getStringExtra("Status");
                int port= intent.getIntExtra("Port",0);
                AsyncSocket   sockets = Utils.getValueByKey(socketStore, port);
                PumpHardwareInfoResponse.Data.DispenserId  dispenserId=null;
                PumpHardwareInfoResponse.Data.TankDatum tankDatum=null;
                NozzleModel nozzleModel;
                nozzleModel=null;
                if(port!=0) {
                    dispenserId=Utils.getDispenser(data.getDispenserId(), port);
                    Log.i("findd",""+dispenserId);
                    if(dispenserId==null){
                        tankDatum=Utils.getTank(data.getTankData(),port) ;
                        Log.i("findt",""+tankDatum);
                    }
                    else {
                        nozzleModel= Utils.getNozzle(dispenserModels,port)  ;
                        nozzleModel.setStatus(status);
                        dispensersAdapter.notifyDataSetChanged();
                    }
                }
                pump_id7=nozzleModel.getPumpId();
                String binaryString=  "010"+Utils.intToBinary(1,5);
                int decimal = Integer.parseInt(binaryString,2);
                String pump_hexStr = Integer.toString(decimal,16);
                Log.d("hex",pump_hexStr);
                nozzle_id7=nozzleModel.getNozzleId();
                Log.d("getS",status);
                String authrisationcommand="0"+nozzle_id7+pump_hexStr+Commands.authrization_command+"7f";
                String read_transaction="0"+nozzle_id7+pump_hexStr+Commands.read_transaction+"7f";
                String last_transaction="0"+nozzle_id7+pump_hexStr+Commands.read_lasttransaction+"7f";
                String pump_startCommand="0"+nozzle_id7+pump_hexStr+Commands.pump_startcommands+"7f";
                String pump_stopCommand="0"+nozzle_id7+pump_hexStr+Commands.pumps_stopcommands+"7f";
                String totliser="0"+nozzle_id7+pump_hexStr+Commands.totaliser_command+"7f";
                String clears_sale="0"+nozzle_id7+pump_hexStr+Commands.clear_salecommands+"7f";
                String command=  "0"+nozzle_id7+pump_hexStr+Commands.read_preset+"7f";
                String auth_code = authrisationcommand + Utils.calculateCheckSum((Utils.convertToAscii(authrisationcommand).getBytes()));
                String readtransaction_code = read_transaction + Utils.calculateCheckSum((Utils.convertToAscii(read_transaction).getBytes()));
                String readlasttransaction_code=last_transaction+Utils.calculateCheckSum((Utils.convertToAscii(last_transaction).getBytes()));
                String pumpstart_code=pump_startCommand+Utils.calculateCheckSum((Utils.convertToAscii(pump_startCommand).getBytes()));
                String pumpstop_code=pump_stopCommand+Utils.calculateCheckSum((Utils.convertToAscii(pump_stopCommand).getBytes()));
                String totaliser_code=totliser+Utils.calculateCheckSum((Utils.convertToAscii(totliser).getBytes()));
                String clearsale_code=clears_sale+Utils.calculateCheckSum((Utils.convertToAscii(clears_sale).getBytes()));
                String checkSumCommand = command + Utils.calculateCheckSum((Utils.convertToAscii(command)).getBytes());
                if(status.contains("STOPPED STATE")){
                    Bugfender.d(TAG,"Pump8 Stopped state");
                    final String[] retansaction;
                    CommandQueue7.TerminateCommandChain();
                    retansaction = new String[]{pumpstop_code,pumpstart_code};
                    CommandQueue7 commandQueue1 = new CommandQueue7(retansaction,
                            sockets,
                            new OnAllCommandCompleted() {
                                @Override
                                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                                }
                                @Override
                                public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                                    if (response != null) {
                                        Log.d("responsettt", response);
                                    }

                                }
                            });
                    commandQueue1.doCommandChaining();

                }
                else if(status.contains("IDLE")){
                    tot_volume7="0.0";
                    start_time7="0.0";
                    end_time7="0.0";
                    volumeSt7="0.0";
                    if(count7==0) {
                        volume_preset7 = String.valueOf(20.00);
                        Bugfender.d(TAG,"Volume Preset8"+volume_preset7);
                        Bugfender.d(TAG,"Dispenser8"+dispenserId);
                        Bugfender.d(TAG,"pumpid8"+pump_id7);
                        Bugfender.d(TAG,"Port8"+port);
                    }
                     count7++;
//                         nozzleModel.setVolume("0.0");
//                         nozzleModel.setAmount("0.0");
//                         nozzleModel.setPrice("0.0");

                }
                else if(status.contains("PRESET READY STATE")){
                    Bugfender.d(TAG,"Pump8 preset ready state");
                    count7=0;
                    state=false;
                    if(!volumeSt7.contains("0.0")) {
                        Bugfender.d(TAG,"Preset Varify8"+volumeSt7);
                        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                        start_time7 = df.format(Calendar.getInstance().getTime());
                        sendAuthcode(auth_code,sockets);
                    }
                }
                else if(status.contains("CALL STATE")){
                    Bugfender.d(TAG,"pump8 call state");
                    count7=0;
                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                    start_time7 = df.format(Calendar.getInstance().getTime());
                    nozzleModel.setStatus(status);
                        Log.d("volume_find", volume_preset);
//                        if (!volume_preset7.contains("0.0") || !volume_preset7.contains("0")) {
                            String b = String.format(Locale.US, "%07d", (int) (Double.parseDouble(volume_preset7) * 100));
                            String commands = "0" + nozzle_id7 + pump_hexStr + Commands.set_presetCommand + "31" + stringToHexWithoutSpace(b) + "7F";
                            String checkSumCommands = commands + Utils.calculateCheckSum((Utils.convertToAscii(commands)).getBytes());
                            Log.d("volumescommand", checkSumCommands + "," + checkSumCommand);
                            final String[] d = new String[]{checkSumCommands, checkSumCommand};
                            CommandQueue commandQueue = new CommandQueue(d, sockets, new OnAllCommandCompleted() {
                                @Override
                                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                                }

                                @Override
                                public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                                    Log.d("CheckPresetResponse", response);
                                    try {
                                        if (response.length() == 36) {
                                            String respon = response.substring(12, 32);
                                            volumeSt7 = Utils.convertToAscii(respon).replaceAll("^0*", "");
                                            Bugfender.d(TAG,"VolumeSt8"+volumeSt7);
                                            if (!volumeSt7.contains(".0")|| !volumeSt7.contains(".00")) {
                                                state=true;
                                                Bugfender.d(TAG,"VolumeStforAuth8"+volumeSt7);
                                                sendAuthcode(auth_code, sockets);
                                            }
                                            Log.d("volumespreset", volumeSt);
//
                                        }

                                    } catch (IndexOutOfBoundsException e) {

                                    } catch (Exception e) {

                                    }


                                }
                            });
                            commandQueue.doCommandChaining();
//                        }
//                        else {
//                            Toast.makeText(getActivity(),"Preset Volume does not found",Toast.LENGTH_LONG).show();
//                        }



                    dispensersAdapter.notifyDataSetChanged();
                }
                else if(status.contains("FUELING STATE")){
                    nozzleModel.setStatus(status);
                    dispensersAdapter.notifyDataSetChanged();
                    NozzleModel finalNozzleModel = nozzleModel;
                    final String[] retansaction;
                    retansaction = new String[]{readtransaction_code};
                    CommandQueue7 commandQueue = new CommandQueue7(retansaction, sockets, new OnAllCommandCompleted() {
                        @Override
                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

                        }
                        @Override
                        public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                            try {
                                if (response != null) {
                                    if (response != null && Utils.countMatches(response, ".") == 3) {
                                        String value = Utils.encodeHexString(response.getBytes());
                                        Log.d("fueling", value);
                                        String respomse = response;
                                        Log.e("txnreskam", "" + (Utils.findNextOccurance(respomse, ".", 1) > 4) + "" + (Utils.findNextOccurance(respomse, ".", 2) > 13) + "&&" + (Utils.findNextOccurance(respomse, ".", 3) > 22));
                                        final String txnFuelRate = respomse.substring(4, respomse.indexOf(".") + 3);
                                        final String txnDispense = respomse.substring(respomse.indexOf(".") + 3, Utils.findNextOccurance(respomse, ".", 2) + 3);
                                        final String txnCharges = respomse.substring(Utils.findNextOccurance(respomse, ".", 2) + 3, Utils.findNextOccurance(respomse, ".", 3) + 3);
                                        finalNozzleModel.setVolume(txnDispense);
                                        finalNozzleModel.setAmount(txnCharges);
                                        finalNozzleModel.setPrice(txnFuelRate);
                                    }
                                }
                            }
                            catch (Exception e){
                                Log.d("exception",e.getMessage());
                            }
                        }
                    });
                    commandQueue.doCommandChaining();
                }
                else if(status.contains("PAYABLE STATE")){
                    Bugfender.d(TAG,"Pump8 payable state");
                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                    end_time7= df.format(Calendar.getInstance().getTime());
                    nozzleModel.setStatus(status);
                    dispensersAdapter.notifyDataSetChanged();
                    CommandQueue7.TerminateCommandChain();
                    final String[] totaliser;

                    totaliser = new String[]{totaliser_code};
                    PumpHardwareInfoResponse.Data.DispenserId finalDispenserId = dispenserId;
                    NozzleModel finalNozzleModel2 = nozzleModel;
                    TxnCommand7 txnCommand =new TxnCommand7(totaliser, sockets, new OnAllCommandCompleted() {
                        @Override
                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

                        }

                        @Override
                        public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                            try {
                                String volumeresponse = response.substring(8, response.indexOf("7f"));
                                Log.d("totaliseroftokheium", volumeresponse);
                                String sb=Utils.convertToAscii(volumeresponse);
                                tot_volume7 = sb.replaceFirst("^0+(?!$)", "");
                                finalNozzleModel2.setVolume_totaliser(tot_volume7);

                                Bugfender.d(TAG,"eight totaliser1"+ tot_volume7);

                            }
                            catch (ArrayIndexOutOfBoundsException e){
                                Log.d("d", e.getLocalizedMessage());
                            }
                            catch (Exception e){
                                Log.d("d", e.getLocalizedMessage());
                            }
                        }
                    });
                    txnCommand.doCommandChaining();

                    if(!tot_volume7.contains("0.0")) {
                        final String[] refresh;
                        refresh = new String[]{readlasttransaction_code};
                        NozzleModel finalNozzleModel1 = nozzleModel;
                        FinalResponse7 finalResponse = new FinalResponse7(refresh[0], sockets, new OnSingleCommandCompleted() {
                            @Override
                            public void onSingleCommandCompleted(String currentCommand, String response, AsyncSocket socket) {
                                try {
                                    if (response.length() == 152 || response.length() == 120) {
                                        idealState(clearsale_code, sockets);
                                        String ammount = response.substring(30, 38);
                                        ammount = "" + Integer.parseInt("" + rev_U(ammount), 16);
                                        ammount = String.valueOf(Double.parseDouble(ammount) / 1000);
                                        String volume = response.substring(38, 46);
                                        volume = "" + Integer.parseInt("" + rev_U(volume), 16);
                                        volume = String.valueOf((Double.parseDouble(volume) / 1000));
                                        String price = response.substring(46, 54);
                                        price = "" + Integer.parseInt("" + rev_U(price), 16);
                                        price = String.valueOf(Double.parseDouble(price) / 1000);
                                        Log.d("final result", ammount + ", " + volume + "," + price +","+response);
                                        finalNozzleModel1.setVolume(volume);
                                        finalNozzleModel1.setAmount(ammount);
                                        finalNozzleModel1.setPrice(price);
                                        String reciptNo = response.substring(60, 64);
                                        reciptNo = "" + Integer.parseInt("" + rev_U(reciptNo), 16);
                                        Log.d("recipt", reciptNo);
                                        String localID = response.substring(64, 72);
                                        localID = "" + Integer.parseInt("" + rev_U(localID), 16);
                                        Log.d("local", localID);
                                        String date = response.substring(74, 80);
                                        String day = date.substring(0, 2);
                                        String month1 = date.substring(2, 4);
                                        String year = date.substring(4, 6);
                                        date = Integer.parseInt("" + rev_U(day), 16) + "/" + Integer.parseInt("" + rev_U(month1), 16) + "/" + Integer.parseInt("" + rev_U(year), 16);
                                        Log.d("date", date);
                                        String time = response.substring(80, 84);
                                        String hour = response.substring(0, 2);
                                        String min = response.substring(2, 4);
                                        time = Integer.parseInt("" + rev_U(hour), 16) + ":" + Integer.parseInt("" + rev_U(min), 16);
                                        Long tsLong = System.currentTimeMillis() / 1000;
                                        String ts = tsLong.toString();
                                        TransactionModel transactionModel = new TransactionModel();
                                        transactionModel.setAmount(ammount);
                                        transactionModel.setTransaction_id(ts);
                                        transactionModel.setStatus(false);
                                        transactionModel.setVolume(volume);
                                        transactionModel.setRate(price);
                                        transactionModel.setDispenser_id(pump_id7);
                                        transactionModel.setTotaliser(tot_volume7);
                                        transactionModel.setStart_time(start_time7);
                                        transactionModel.setEnd_time(end_time7);
                                        finalNozzleModel1.setEnd_time(end_time7);
                                        if (!volume.contains("0.0") || !volume.contains("0")) {
                                            new SaveTask(transactionModel).execute();
                                        }
                                    }
                                }
                                catch (Exception e){
                                    Log.d("exception",e.getMessage());
                                }
                            }
                        });
                        finalResponse.doCommandChaining();
                    }
                }

            }
        };
// for nineth tokheium
        tokheiumReceiver8=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String status = intent.getStringExtra("Status");
                int port= intent.getIntExtra("Port",0);
                AsyncSocket   sockets = Utils.getValueByKey(socketStore, port);
                PumpHardwareInfoResponse.Data.DispenserId  dispenserId=null;
                PumpHardwareInfoResponse.Data.TankDatum tankDatum=null;
                NozzleModel nozzleModel;
                nozzleModel=null;
                if(port!=0) {
                    dispenserId=Utils.getDispenser(data.getDispenserId(), port);
                    Log.i("findd",""+dispenserId);
                    if(dispenserId==null){
                        tankDatum=Utils.getTank(data.getTankData(),port) ;
                        Log.i("findt",""+tankDatum);
                    }
                    else {
                        nozzleModel= Utils.getNozzle(dispenserModels,port)  ;
                        nozzleModel.setStatus(status);
                        dispensersAdapter.notifyDataSetChanged();
                    }
                }
                pump_id8=nozzleModel.getPumpId();
                String binaryString=  "010"+Utils.intToBinary(1,5);
                int decimal = Integer.parseInt(binaryString,2);
                String pump_hexStr = Integer.toString(decimal,16);
                Log.d("hex",pump_hexStr);
                nozzle_id8=nozzleModel.getNozzleId();
                Log.d("getS",status);
                String authrisationcommand="0"+nozzle_id8+pump_hexStr+Commands.authrization_command+"7f";
                String read_transaction="0"+nozzle_id8+pump_hexStr+Commands.read_transaction+"7f";
                String last_transaction="0"+nozzle_id8+pump_hexStr+Commands.read_lasttransaction+"7f";
                String pump_startCommand="0"+nozzle_id8+pump_hexStr+Commands.pump_startcommands+"7f";
                String pump_stopCommand="0"+nozzle_id8+pump_hexStr+Commands.pumps_stopcommands+"7f";
                String totliser="0"+nozzle_id8+pump_hexStr+Commands.totaliser_command+"7f";
                String clears_sale="0"+nozzle_id8+pump_hexStr+Commands.clear_salecommands+"7f";
                String command=  "0"+nozzle_id8+pump_hexStr+Commands.read_preset+"7f";
                String auth_code = authrisationcommand + Utils.calculateCheckSum((Utils.convertToAscii(authrisationcommand).getBytes()));
                String readtransaction_code = read_transaction + Utils.calculateCheckSum((Utils.convertToAscii(read_transaction).getBytes()));
                String readlasttransaction_code=last_transaction+Utils.calculateCheckSum((Utils.convertToAscii(last_transaction).getBytes()));
                String pumpstart_code=pump_startCommand+Utils.calculateCheckSum((Utils.convertToAscii(pump_startCommand).getBytes()));
                String pumpstop_code=pump_stopCommand+Utils.calculateCheckSum((Utils.convertToAscii(pump_stopCommand).getBytes()));
                String totaliser_code=totliser+Utils.calculateCheckSum((Utils.convertToAscii(totliser).getBytes()));
                String clearsale_code=clears_sale+Utils.calculateCheckSum((Utils.convertToAscii(clears_sale).getBytes()));
                String checkSumCommand = command + Utils.calculateCheckSum((Utils.convertToAscii(command)).getBytes());
                if(status.contains("STOPPED STATE")){
                    Bugfender.d(TAG,"Pump9 Stopped state");
                    final String[] retansaction;
                    CommandQueue8.TerminateCommandChain();
                    retansaction = new String[]{pumpstop_code,pumpstart_code};
                    CommandQueue8 commandQueue1 = new CommandQueue8(retansaction,
                            sockets,
                            new OnAllCommandCompleted() {
                                @Override
                                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                                }
                                @Override
                                public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                                    if (response != null) {
                                        Log.d("responsettt", response);
                                    }

                                }
                            });
                    commandQueue1.doCommandChaining();

                }
                else if(status.contains("IDLE")){
                     tot_volume8="0.0";
                     start_time8="";
                     end_time8="";
                     volumeSt8="0.0";
                    if(count8==0) {
                        volume_preset8 = String.valueOf(20.00);
                        Bugfender.d(TAG,"Volume Preset9"+volume_preset8);
                        Bugfender.d(TAG,"Dispenser9"+dispenserId);
                        Bugfender.d(TAG,"pumpid9"+pump_id8);
                        Bugfender.d(TAG,"Port9"+port);
                    }
                    count8++;
//                         nozzleModel.setVolume("0.0");
//                         nozzleModel.setAmount("0.0");
//                         nozzleModel.setPrice("0.0");

                }
                else if(status.contains("PRESET READY STATE")){
                    Bugfender.d(TAG,"Pump9 presetready state");
                    state=false;
                    count8=0;
                    if(!volumeSt8.contains("0.0")) {
                        Bugfender.d(TAG,"Preset Varify9"+volumeSt8);
                        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                        start_time8 = df.format(Calendar.getInstance().getTime());
                        sendAuthcode(auth_code,sockets);
                    }
                }
                else if(status.contains("CALL STATE")){
                    Bugfender.d(TAG,"Pump9 call state");
                    nozzleModel.setStatus(status);
                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                    start_time8 = df.format(Calendar.getInstance().getTime());
                    count8=0;
                        Log.d("volume_find",volume_preset);
//                        if(!volume_preset8.contains("0.0")|| !volume_preset8.contains("0")) {
                            String b = String.format(Locale.US, "%07d", (int) (Double.parseDouble(volume_preset8) * 100));
                            String commands = "0" + nozzle_id8 + pump_hexStr + Commands.set_presetCommand + "31" + stringToHexWithoutSpace(b) + "7F";
                            String checkSumCommands = commands + Utils.calculateCheckSum((Utils.convertToAscii(commands)).getBytes());
                            Log.d("volumescommand", checkSumCommands + "," + checkSumCommand);
                            final String[] d = new String[]{checkSumCommands, checkSumCommand};
                            CommandQueue commandQueue = new CommandQueue(d, sockets, new OnAllCommandCompleted() {
                                @Override
                                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                                }

                                @Override
                                public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                                    Log.d("CheckPresetResponse", response);
                                    try {
                                        if (response.length() == 36) {
                                            String respon = response.substring(12, 32);
                                            volumeSt8 = Utils.convertToAscii(respon).replaceAll("^0*", "");
                                            Bugfender.d(TAG,"VolumeSt9 "+volumeSt8);
                                            if (!volumeSt8.contains(".0")|| !volumeSt8.contains(".00")) {
                                                state=true;
                                                Bugfender.d(TAG,"VolumeStforAuth9"+volumeSt8);
                                                sendAuthcode(auth_code, sockets);

                                            }
                                            Log.d("volumespreset", volumeSt);
//
                                        }

                                    } catch (IndexOutOfBoundsException e) {

                                    } catch (Exception e) {

                                    }


                                }
                            });
                            commandQueue.doCommandChaining();
//                        }
//                        else {
//                            Toast.makeText(getActivity(),"Volume Preset not found",Toast.LENGTH_LONG).show();
//                        }


                    dispensersAdapter.notifyDataSetChanged();
                }
                else if(status.contains("FUELING STATE")){
                    nozzleModel.setStatus(status);
                    dispensersAdapter.notifyDataSetChanged();
                    NozzleModel finalNozzleModel = nozzleModel;
                    final String[] retansaction;
                    retansaction = new String[]{readtransaction_code};
                    CommandQueue8 commandQueue = new CommandQueue8(retansaction, sockets, new OnAllCommandCompleted() {
                        @Override
                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

                        }
                        @Override
                        public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                            try {
                                if (response != null) {
                                    if (response != null && Utils.countMatches(response, ".") == 3) {
                                        String value = Utils.encodeHexString(response.getBytes());
                                        Log.d("fueling", value);
                                        String respomse = response;
                                        Log.e("txnreskam", "" + (Utils.findNextOccurance(respomse, ".", 1) > 4) + "" + (Utils.findNextOccurance(respomse, ".", 2) > 13) + "&&" + (Utils.findNextOccurance(respomse, ".", 3) > 22));
                                        final String txnFuelRate = respomse.substring(4, respomse.indexOf(".") + 3);
                                        final String txnDispense = respomse.substring(respomse.indexOf(".") + 3, Utils.findNextOccurance(respomse, ".", 2) + 3);
                                        final String txnCharges = respomse.substring(Utils.findNextOccurance(respomse, ".", 2) + 3, Utils.findNextOccurance(respomse, ".", 3) + 3);
                                        finalNozzleModel.setVolume(txnDispense);
                                        finalNozzleModel.setAmount(txnCharges);
                                        finalNozzleModel.setPrice(txnFuelRate);
                                    }
                                }
                            }
                            catch (Exception e){
                                Log.d("error",e.getMessage());
                            }
                        }
                    });
                    commandQueue.doCommandChaining();
                }
                else if(status.contains("PAYABLE STATE")){
                    Bugfender.d(TAG,"Pump9 payable  state");
                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                    end_time= df.format(Calendar.getInstance().getTime());
                    nozzleModel.setStatus(status);
                    dispensersAdapter.notifyDataSetChanged();
                    CommandQueue.TerminateCommandChain();
                    final String[] totaliser;

                    totaliser = new String[]{totaliser_code};
                    PumpHardwareInfoResponse.Data.DispenserId finalDispenserId = dispenserId;
                    NozzleModel finalNozzleModel2 = nozzleModel;
                    TxnCommand8 txnCommand =new TxnCommand8(totaliser, sockets, new OnAllCommandCompleted() {
                        @Override
                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

                        }

                        @Override
                        public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                            try {
                                String volumeresponse = response.substring(8, response.indexOf("7f"));
                                String sb=Utils.convertToAscii(volumeresponse);
                                tot_volume8 = sb.replaceFirst("^0+(?!$)", "");
                                finalNozzleModel2.setVolume_totaliser(tot_volume8);
                                Bugfender.d(TAG,"nineth totaliser"+ tot_volume8);
                            }
                            catch (ArrayIndexOutOfBoundsException e){
                                Log.d("d", e.getLocalizedMessage());
                            }

                        }
                    });
                    txnCommand.doCommandChaining();

                    if(!tot_volume8.contains("0.0")) {
                        final String[] refresh;
                        refresh = new String[]{readlasttransaction_code};
                        NozzleModel finalNozzleModel1 = nozzleModel;
                        FinalResponse8 finalResponse = new FinalResponse8(refresh[0], sockets, new OnSingleCommandCompleted() {
                            @Override
                            public void onSingleCommandCompleted(String currentCommand, String response, AsyncSocket socket) {
                                try {
                                    if (response.length() == 152 || response.length() == 120) {
                                        idealState(clearsale_code, sockets);
                                        String ammount = response.substring(30, 38);
                                        ammount = "" + Integer.parseInt("" + rev_U(ammount), 16);
                                        ammount = String.valueOf(Double.parseDouble(ammount) / 1000);
                                        String volume = response.substring(38, 46);
                                        volume = "" + Integer.parseInt("" + rev_U(volume), 16);
                                        volume = String.valueOf((Double.parseDouble(volume) / 1000));
                                        String price = response.substring(46, 54);
                                        price = "" + Integer.parseInt("" + rev_U(price), 16);
                                        price = String.valueOf(Double.parseDouble(price) / 1000);
                                        Log.d("final result", ammount + ", " + volume + "," + price +","+response);
                                        finalNozzleModel1.setVolume(volume);
                                        finalNozzleModel1.setAmount(ammount);
                                        finalNozzleModel1.setPrice(price);
                                        String reciptNo = response.substring(60, 64);
                                        reciptNo = "" + Integer.parseInt("" + rev_U(reciptNo), 16);
                                        Log.d("recipt", reciptNo);
                                        String localID = response.substring(64, 72);
                                        localID = "" + Integer.parseInt("" + rev_U(localID), 16);
                                        Log.d("local", localID);
                                        String date = response.substring(74, 80);
                                        String day = date.substring(0, 2);
                                        String month1 = date.substring(2, 4);
                                        String year = date.substring(4, 6);
                                        date = Integer.parseInt("" + rev_U(day), 16) + "/" + Integer.parseInt("" + rev_U(month1), 16) + "/" + Integer.parseInt("" + rev_U(year), 16);
                                        Log.d("date", date);
                                        String time = response.substring(80, 84);
                                        String hour = response.substring(0, 2);
                                        String min = response.substring(2, 4);
                                        time = Integer.parseInt("" + rev_U(hour), 16) + ":" + Integer.parseInt("" + rev_U(min), 16);
                                        String endTIme = String.valueOf(Calendar.getInstance().getTime());
                                        Log.d("time", time + " ," + endTIme);

                                        Long tsLong = System.currentTimeMillis() / 1000;
                                        String ts = tsLong.toString();
                                        TransactionModel transactionModel = new TransactionModel();
                                        transactionModel.setAmount(ammount);
                                        transactionModel.setTransaction_id(ts);
                                        transactionModel.setStatus(false);
                                        transactionModel.setVolume(volume);
                                        transactionModel.setRate(price);
                                        transactionModel.setDispenser_id(pump_id8);
                                        transactionModel.setTotaliser(tot_volume8);
                                        transactionModel.setStart_time(start_time8);
                                        transactionModel.setEnd_time(end_time8);
                                        finalNozzleModel1.setEnd_time(end_time8);
                                        if (!volume.contains("0.0") || !volume.contains("0")) {
                                            new SaveTask(transactionModel).execute();
                                        }
//
                                    }
                                }
                                catch (Exception e){
                                    Log.d("TAG",e.getMessage());
                                }
                            }
                        });
                        finalResponse.doCommandChaining();
                    }
                }
            }
        };
        init(v);
        return v;
    }

    private void atgVolume() {

            if(connectedATg.size()>0) {
                for (int i = 0; i < connectedATg.size(); i++) {
                    if (i == 0)
                    {
                        String port = connectedATg.get(0).getPort_no();
                        String serial = "M" + connectedATg.get(0).getSerialNo();
                        String hex_serial_num = Utils.convertStringToHex(serial);
                        String hex_code = hex_serial_num + "0D0A";
                        AsyncSocket sockets = Utils.getValueByKey(socketStore, Integer.parseInt(port));
                        Log.d("ATGcomand",hex_code);

                        final String[] presetCompletedStateCommands = new String[]{hex_code};
                        new CommandQueue(presetCompletedStateCommands, sockets, new OnAllCommandCompleted() {
                            @Override
                            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                            }

                            @Override
                            public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                                try {
                                    Bugfender.d(TAG,response);
                                    Log.d("ATGcomandM",response);
                                    String asciivalue = DataConversion.convertToAscii(response);
                                    String[] separated = asciivalue.split("=");
                                    float d = Float.parseFloat(separated[2]);
                                    volume = VolumeCalculation.getAtgState(d, data.getTankData().get(0));
                                    data.getTankData().get(0).setVolume(volume);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(tankAdapter!=null) {
                                                tankAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    });
//
                                    float capicity=Float.parseFloat(data.getTankData().get(0).getCapacity());
                                    float alege=capicity-volume;
                                    if(alege>50){
                                        relayoff(sockets);

                                    }else {
                                        relayon(sockets);
                                    }

                                    Bugfender.d(TAG,"height="+d+","+"volume"+volume);


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
                                    data.getTankData().get(1).setVolume(volume1);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(tankAdapter!=null) {
                                                tankAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    });
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
                                    data.getTankData().get(2).setVolume(volume2);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(tankAdapter!=null) {
                                                tankAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    });
                                    float capicity=Float.parseFloat(data.getTankData().get(2).getCapacity());
                                    float alege=capicity-volume2;
                                    if(alege>50){
                                        relayoff(sockets);
                                    }else {
                                        relayon(sockets);
                                    }
                                    Bugfender.d(TAG,"height="+d+","+"volume"+volume);

                                }
                                catch (Exception e) {
                                    Bugfender.d(TAG,e.getMessage());

                                }
                            }
                        }).doCommandChaining();
                    }
                }
            }
    }

    private void relayon(AsyncSocket sockets) {
        final String[] presetCompletedStateCommands = new String[]{"#*RL11"};
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

    private void relayoff(AsyncSocket sockets) {
        final String[] presetCompletedStateCommands = new String[]{"#*RL10"};
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

    private void sendAuthcode(String auth_code,AsyncSocket socket) {
        final String[] retansaction;
        retansaction = new String[]{auth_code};
        CommandQueue commandQueue = new CommandQueue(retansaction, socket, new OnAllCommandCompleted() {
            @Override
            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse)
            {

            }
            @Override
            public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                if (response != null) {
                    Log.d("respons3",response);
                }
            }
        });
        commandQueue.doCommandChaining();




    }


    private int checkpreset(PumpHardwareInfoResponse.Data.DispenserId dispenserId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("dispenser_id",dispenserId.getId());
        ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
        apiInterface.getPresetData(hashMap).enqueue(new Callback<PresetModal>() {
            @Override
            public void onResponse(Call<PresetModal> call, Response<PresetModal> response) {
                if(response.body().getData().getStatusCode()==200){
                    presetvolumes=  response.body().getData().getPresetQuantity();
                    Log.d("volume_preset",""+presetvolumes);
                }
                else{
                    Toast.makeText(getActivity(),"Dispenser id null",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PresetModal> call, Throwable t) {
                t.getMessage();
                Log.d("error",t.getLocalizedMessage());
            }
        });
        return  presetvolumes;
    }
    private String stringToHexWithoutSpace(String string) {
        StringBuilder buf = new StringBuilder(200);
        for (char ch : string.toCharArray()) {
            buf.append(String.format("%02X", (int) ch));
        }
        return buf.toString();
    }

    private void retrancation(NozzleModel nozzleModel) {

        WriteCommandUtils writeCommandUtil=new WriteCommandUtils("41", socket[0], new OnSingleCommandCompleted() {
            @Override
            public void onSingleCommandCompleted(String currentCommand, String response, AsyncSocket socket) {
                if (response != null) {
                    if(response.startsWith("ff") && response.endsWith("f0")) {
                        try {
                            Log.d("gvr3", response);
                            Log.d("final volume", response);
                            String rate = response.substring((response.indexOf("f7") + 2), response.indexOf("f9")).replaceAll("[^\\d.]", "");
                            Log.e("rate41 cmd", rate);
                            StringBuilder rateS = new StringBuilder();
                            rate = rate.substring(0, 2) + "." + rate.substring(2, rate.length());
                            rateS.append(rate);
                            rateS.reverse();
                            Log.e("rateRe41 cmd", rateS.toString());
                            priceSt = rateS.toString();
                            String ammount = response.substring((response.indexOf("fa") + 2), response.indexOf("fb")).replaceAll("[^\\d.]", "");
                            StringBuilder ammountS = new StringBuilder();
                            ammount = ammount.substring(0, 3) + "." + ammount.substring(3, ammount.length());
                            ammountS.append(ammount);
                            ammountS.reverse();
                            Log.e("ammount41 cmd", ammountS.toString());
                            ammountSt = ammountS.toString();
//                            nozzleModel.setAmount(ammountSt);

                            String volume = response.substring((response.indexOf("f9") + 2), response.indexOf("fa")).replaceAll("[^\\d.]", "");
                            StringBuilder volumeS = new StringBuilder();
                            volume = volume.substring(0, 3) + "." + volume.substring(3, volume.length());
                            volumeS.append(volume);
                            volumeS.reverse();
                        }
                        catch (Exception e){

                        }
                    }
                }

            }
        });
        writeCommandUtil.doCommandChaining();
    }

    private void idealState(String code,AsyncSocket socket) {

        final String[] retansaction;
        CommandQueue.TerminateCommandChain();
        Log.d("clearsale",code);
        retansaction = new String[]{code};
        CommandQueue commandQueue1 = new CommandQueue(retansaction, socket, new OnAllCommandCompleted() {
            @Override
            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

            }

            @Override
            public void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket) {
                if (response != null) {
                    Log.d("responsettt", response);
                }
            }
        });
        commandQueue1.doCommandChaining();

    }

    public String rev_U(String n_U) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i <= n_U.length() - 2; i = i + 2) {
            result.append(new StringBuilder(n_U.substring(i, i + 2)).reverse());
        }
        return result.reverse().toString();
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("ToGetGvrStatus");
        getActivity().registerReceiver(activityReceiver,intentFilter);
        //first tokheium receive
        IntentFilter intentFilter1 = new IntentFilter("ToGetTokheiumStatus");
        getActivity().registerReceiver(tokheiumReceiver,intentFilter1);
        // second tokheium
        IntentFilter intentFilter2 = new IntentFilter("ToGetTokheiumStatus1");
        getActivity().registerReceiver(tokheiumReceiver1,intentFilter2);
        // third tokheium
        IntentFilter intentFilter3 = new IntentFilter("ToGetTokheiumStatus2");
        getActivity().registerReceiver(tokheiumReceiver2,intentFilter3);
        // fourth tokheium
        IntentFilter intentFilter4 = new IntentFilter("ToGetTokheiumStatus3");
        getActivity().registerReceiver(tokheiumReceiver3,intentFilter4);
        // fifth tokheium
        IntentFilter intentFilter5 = new IntentFilter("ToGetTokheiumStatus4");
        getActivity().registerReceiver(tokheiumReceiver4,intentFilter5);
        // six tokheium
        IntentFilter intentFilter6 = new IntentFilter("ToGetTokheiumStatus5");
        getActivity().registerReceiver(tokheiumReceiver5,intentFilter6);
        // seven
        IntentFilter intentFilter7 = new IntentFilter("ToGetTokheiumStatus6");
        getActivity().registerReceiver(tokheiumReceiver6,intentFilter7);
        // eight
        IntentFilter intentFilter8 = new IntentFilter("ToGetTokheiumStatus7");
        getActivity().registerReceiver(tokheiumReceiver7,intentFilter8);
        // nine
        IntentFilter intentFilter9 = new IntentFilter("ToGetTokheiumStatus8");
        getActivity().registerReceiver(tokheiumReceiver8,intentFilter9);


        IntentFilter intentFilterconection = new IntentFilter();
        intentFilterconection.addAction("android.net.conn.CONNECTIVITY_CHANGE");


        getActivity().registerReceiver(connectivityReceiver, intentFilterconection);



        setDispenserDatas();
        setTankData();

    }

    private void getTransaction() {
        class GetTasks extends AsyncTask<Void, Void, List<TransactionModel>> {

            @Override
            protected List<TransactionModel> doInBackground(Void... voids) {
                List<TransactionModel> taskList = DatabaseClient
                        .getInstance(getActivity())
                        .getAppDatabase()
                        .transactionDao()
                        .getAll();
                Collections.reverse(taskList);
                return taskList;
            }

            @Override
            protected void onPostExecute(List<TransactionModel> tasks) {
                super.onPostExecute(tasks);
                if(tasks.size()>10) {
                    TransactionsAdapter adapter = new TransactionsAdapter(getActivity(), tasks.subList(0, 9));
                    transactionRecyclerView.setAdapter(adapter);
                }
                else {
                    TransactionsAdapter adapter = new TransactionsAdapter(getActivity(), tasks);
                    transactionRecyclerView.setAdapter(adapter);
                }
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }



    private void setDispenserDatas() {
        dispensersAdapter = new NozzelsAdapter(requireContext(), dispenserModels,this);

        nozzleRecyclerView.setAdapter(dispensersAdapter);

    }

    private void init(View v) {
        dispenserListRecyclerView   = v.findViewById(R.id.dispenserListRecyclerView);
        tankRecyclerView            = v.findViewById(R.id.tankRecyclerView);
        nozzleRecyclerView          = v.findViewById(R.id.nozzleRecyclerView);
        transactionRecyclerView     = v.findViewById(R.id.transactionRecyclerView);
        scrollbutton                = v.findViewById(R.id.scroll);
        scrollbutton2               =v.findViewById(R.id.scrollt);
        scrolltank                  =v.findViewById(R.id.scrolltank);
        scrolltank0                 =v.findViewById(R.id.scrolltanko);
        layoutManager=new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(requireContext(), 4, LinearLayoutManager.VERTICAL, false);
        mlinearLayoutManager=new GridLayoutManager(requireContext(), 1,LinearLayoutManager.VERTICAL, false);
        nozzleRecyclerView.setHasFixedSize(true);
        nozzleRecyclerView.setLayoutManager(linearLayoutManager);
        transactionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        transactionRecyclerView.addItemDecoration(itemDecorator);
        scrollbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int totalItemCount = nozzleRecyclerView.getAdapter().getItemCount();
                Log.d("totalitemcount",""+totalItemCount);
                if (totalItemCount <= 0) return;
                int lastVisibleItemIndex = linearLayoutManager.findLastVisibleItemPosition();
                Log.d("totalitemvisible",""+lastVisibleItemIndex);

                if (lastVisibleItemIndex >= totalItemCount) return;
                linearLayoutManager.smoothScrollToPosition(nozzleRecyclerView,null,lastVisibleItemIndex+1);

            }
        });
        scrollbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int firstVisibleItemIndex = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                if (firstVisibleItemIndex > 0) {
                    linearLayoutManager.smoothScrollToPosition(nozzleRecyclerView,null,firstVisibleItemIndex-1);
                }

            }
        });
        scrolltank0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int totalItemCount = tankRecyclerView.getAdapter().getItemCount();
                Log.d("totalitemcount",""+totalItemCount);
                if (totalItemCount <= 0) return;
                int lastVisibleItemIndex = mlinearLayoutManager.findLastVisibleItemPosition();
                Log.d("totalitemvisible",""+lastVisibleItemIndex);

                if (lastVisibleItemIndex >= totalItemCount) return;
                mlinearLayoutManager.smoothScrollToPosition(tankRecyclerView,null,lastVisibleItemIndex+1);

            }
        });
        scrolltank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int firstVisibleItemIndex = mlinearLayoutManager.findFirstCompletelyVisibleItemPosition();
                if (firstVisibleItemIndex > 0) {
                    mlinearLayoutManager.smoothScrollToPosition(tankRecyclerView,null,firstVisibleItemIndex-1);
                }


            }
        });

    }
    private void setTankData(){
        tankAdapter= new TankAdapter(requireContext(), tankData);
        tankRecyclerView.setHasFixedSize(true);
        tankRecyclerView.setLayoutManager(mlinearLayoutManager);
        tankRecyclerView.setAdapter(tankAdapter);
    }



    //TODO remove after implement api
    private List<NozzleModel> setDispenserList(){
        Map<String, Integer> hm = new HashMap<String, Integer>();
        Map <String,Integer> hm1= new HashMap<String,Integer>();
        for(PumpHardwareInfoResponse.Data.DispenserNozzle dispenserNozzle :dispenserNozzles){
            NozzleModel dispenserModel = new NozzleModel();
            String dispenserid=dispenserNozzle.getDispenserId();
            String pump_id=dispenserNozzle.getPumpId();
            Integer j = hm.get(dispenserid);
            Integer k=hm1.get(pump_id);
            hm.put(dispenserid, (j == null) ? 1 : j + 1);
            hm1.put(pump_id,(k==null) ? 1: k+1);
            PumpHardwareInfoResponse.Data.DispenserId hardware= findTypeofHardware(dispenserid);
            if(hardware!=null){
                dispenserModel.setPort(hardware.getPort_number());
                if(hardware.isConnected_Status())
                {
                    dispenserModel.setStatus("Ideal");
                }
                else{
                    dispenserModel.setStatus("No Connection");
                }
            }
            dispenserModel.setDispenser_id(dispenserNozzle.getDispenserId());
            dispenserModel.setPumpId(dispenserNozzle.getPumpId());
            dispenserModel.setNozzleId(dispenserNozzle.getNozzel());
            dispenserModel.setPreset_type(dispenserNozzle.getTypeOfPreset());
            if (j==null){
                dispenserModel.setPump_num("1");
            }
            else {
                dispenserModel.setPump_num(String.valueOf(j+1));
            }
            if(k==null){
                dispenserModel.setNozzle_num("1");
            }
            else{
                dispenserModel.setNozzle_num(String.valueOf(k+1));
            }
            dispenserModel.setPrice("0");
            dispenserModel.setVolume("0");
            dispenserModel.setAmount("0");
            dispenserModel.setProduct(dispenserNozzle.getProductName());
            if(hardware.getDispencer().equals("1")){
                dispenserModel.setMakeOfDispenser("Tokheim");
                tokheium.add(dispenserModel);
            }
            else if(hardware.getDispencer().equals("2")){
                dispenserModel.setMakeOfDispenser("Synergy");
                synergy.add(dispenserModel);
            }
            else if(hardware.getDispencer().equals("3")){
                dispenserModel.setMakeOfDispenser("GVR");
                gvr.add(dispenserModel);
            }
            else {
                dispenserModel.setMakeOfDispenser("Chinese");
                chinese.add(dispenserModel);
            }
            dispenserModels.add(dispenserModel);
        }
        return  dispenserModels;
    }

    private PumpHardwareInfoResponse.Data.DispenserId findTypeofHardware(String dispenserid) {
        for(PumpHardwareInfoResponse.Data.DispenserId carnet : dispenserIdList) {
            if(carnet.getId().equals(dispenserid)) {
                return carnet;
            }
        }
        return null;
    }

    @Override
    public void onElementclick(int position) {
        Log.i("position",""+position);
        String backStackname=PumpListFragment.class.getName();
        DispenserDetail dispenserDetail=new DispenserDetail();
        FragmentTransaction tx = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("UserValidateObject",dispenserModels.get(position) );
        dispenserDetail.setArguments(bundle);
        tx.addToBackStack(backStackname);
        tx.replace(R.id.container, dispenserDetail, "Dispenser Detail");
        tx.commit();
    }
    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(activityReceiver);
        getActivity().unregisterReceiver(tokheiumReceiver);
        getActivity().unregisterReceiver(tokheiumReceiver1);
        getActivity().unregisterReceiver(tokheiumReceiver2);
        getActivity().unregisterReceiver(tokheiumReceiver3);
        getActivity().unregisterReceiver(tokheiumReceiver4);
        getActivity().unregisterReceiver(tokheiumReceiver5);
        getActivity().unregisterReceiver(tokheiumReceiver6);
        getActivity().unregisterReceiver(tokheiumReceiver7);
        getActivity().unregisterReceiver(tokheiumReceiver8);
        getActivity().stopService(new Intent(getActivity(),TokheumService.class));
        getActivity().stopService(new Intent(getActivity(),TokheumService1.class));
        getActivity().stopService(new Intent(getActivity(),TokheumService2.class));
        getActivity().stopService(new Intent(getActivity(),TokheumService4.class));
        getActivity().stopService(new Intent(getActivity(),TokheumService5.class));
        getActivity().stopService(new Intent(getActivity(),TokheumService6.class));
        getActivity().stopService(new Intent(getActivity(),TokheumService7.class));
        getActivity().stopService(new Intent(getActivity(),TokheumService8.class));
        getActivity().stopService(new Intent(getActivity(),MyService.class));

    }
    public class SaveTask  extends AsyncTask<Void, Void, Void > {
        TransactionModel ts;
        public SaveTask(TransactionModel transactionModel) {
            this.ts=transactionModel;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            DatabaseClient.getInstance(getActivity()).getAppDatabase()
                    .transactionDao()
                    .insert(ts);
            return null;
        }
    }
}