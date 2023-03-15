package com.synergy.synergydhrishtiplus.dialogs;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bugfender.sdk.Bugfender;
import com.google.gson.Gson;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncSocket;
import com.synergy.synergydhrishtiplus.CommonActivity;
import com.synergy.synergydhrishtiplus.R;
import com.synergy.synergydhrishtiplus.SynergyApplicationClass;
import com.synergy.synergydhrishtiplus.adapters.DispenserList;
import com.synergy.synergydhrishtiplus.data_model.MapWrapper;
import com.synergy.synergydhrishtiplus.data_model.PumpHardwareInfoResponse;
import com.synergy.synergydhrishtiplus.listners.CheckConnectedAndDisconnectedListner;
import com.synergy.synergydhrishtiplus.network.ApiClient;
import com.synergy.synergydhrishtiplus.network.ApiInterface;
import com.synergy.synergydhrishtiplus.socket.Server485;
import com.synergy.synergydhrishtiplus.utils.SharedPref;
import com.synergy.synergydhrishtiplus.utils.Utils;
import com.synergy.synergydhrishtiplus.utils.UtilsNetwork;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScannerDialogFragment extends DialogFragment   {
    View scanning_bar_view;
    RecyclerView rc;
    DispenserList adapter;
    public static final String Tag = "ScannerDialogFragment";
    public String login_id;
    LinearLayout content;
    boolean state = false;
    RelativeLayout rl;
    ScrollView sl;
    PumpHardwareInfoResponse.Data obj;
    private static final String TAG = "ScannerDialog";
    List<Integer> portNo = new ArrayList<>();
    List<PumpHardwareInfoResponse.Data.DispenserId> dispenserIds;
    List<PumpHardwareInfoResponse.Data.TankDatum> tankDatas;
    HashMap<Integer, AsyncSocket> socketHashMap = new HashMap<>();
    HashMap<Integer, Boolean> statusHashmap = new HashMap<>();
    LinearLayout placeHolderLinearLayout;
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scanner_dialog, container, false);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        login_id=  SharedPref.getLoginUserId();
        dispenserIds=new ArrayList<>();
        tankDatas=new ArrayList<>();
        socketHashMap.clear();
        statusHashmap.clear();
        dispenserIds.clear();
        tankDatas.clear();
        init(view);
        getListofPumps();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getListofPumps() {
        if(UtilsNetwork.isNetworkConnected(getActivity())){
            HashMap<String, String> hashMap = new HashMap<>();
            Log.d("Login",login_id);
            hashMap.put("login_id",login_id);
            ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
            apiInterface.getPumpHardware(hashMap).enqueue(new Callback<PumpHardwareInfoResponse>() {
                @Override
                public void onResponse(Call<PumpHardwareInfoResponse> call, Response<PumpHardwareInfoResponse> response) {
                    if(response.isSuccessful()){
                        obj= response.body().getData();
                        Bugfender.d(TAG,"response"+response.body().getData());
                        Log.d(TAG,"response"+response.body().getData().getDispenserId());
                        if(response.body().getData().getDispenserId().size()>0 && response.body().getData().getDispenserId()!=null){
                            dispenserIds.addAll( response.body().getData().getDispenserId());
                        }
                        else{

//                            Bugfender.d(TAG, "dispenserlistEmpty");

                        }
                        if(response.body().getData().getTankData().size()>0 && response.body().getData().getTankData()!=null){
                            tankDatas.addAll(response.body().getData().getTankData());
                        }
                        else{
//                            Bugfender.d(TAG, "tanklistEmpty");

                        }

                        startScanning();
                    }
                    else {
                        Toast.makeText(getActivity(),response.message(),Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<PumpHardwareInfoResponse> call, Throwable t) {
                    Bugfender.d(TAG, t.getMessage());

                }
            });
        }

    }
    private void init(View view) {
        scanning_bar_view = view.findViewById(R.id.scanning_bar_view);
        sl=view.findViewById(R.id.list);
        placeHolderLinearLayout=(LinearLayout)view.findViewById(R.id.placeholder_lay) ;
        adapter=new DispenserList(getActivity(),dispenserIds,tankDatas);
    }
    private void  startScanning() {

        class ExampleAsyncTask extends AsyncTask{
            Context context;

            public ExampleAsyncTask(Context context) {
                this.context=context;
                if(getActivity()!=null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Bugfender.d(TAG,"Asyntask Constructor");
                            placeHolderLinearLayout.removeAllViews();
                        }
                    });
                }

            }

            @Override
            protected Object doInBackground(Object[] objects) {

                for(int i=0; i<dispenserIds.size()+tankDatas.size();i++){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(i<dispenserIds.size()) {
                        new Server485(dispenserIds.get(i).getIp_address(), Integer.parseInt(dispenserIds.get(i).getPort_number()), new CheckConnectedAndDisconnectedListner() {
                            @Override
                            public void updateSocketGlobal(int portNo, AsyncSocket socket, boolean status) {
                                socketHashMap.put(portNo, socket);
                                statusHashmap.put(portNo, status);
                                Bugfender.d(TAG, ""+portNo+","+socket+","+status);

                                PumpHardwareInfoResponse.Data.DispenserId dispenser = Utils.getDispenser(dispenserIds, portNo);
                                PumpHardwareInfoResponse.Data.TankDatum tankDatum = Utils.getTank(tankDatas, portNo);
                                if(dispenser!=null){
                                    dispenser.setConnected_Status(status);

                                }
                                else {
                                    tankDatum.setConnected_Status(status);

                                }
                                try {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            LayoutInflater vi = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                            View l = vi.inflate(R.layout.hardware_list, null);
                                            ScrollView scrollView=(ScrollView)l.findViewById(R.id.scroll_up) ;
                                            TextView leftTextView = (TextView) l.findViewById(R.id.hrd_name);
                                            TextView rightTextView = (TextView) l.findViewById(R.id.totalConnected);
                                            Bugfender.d("Dispenserto", dispenser.getId());
                                            if (dispenser != null) {
                                                leftTextView.setText("Dispenser" + dispenser.getId() + "" + "(" + String.valueOf(portNo) + ")");
                                            }
//                                    else {
//                                        leftTextView.setText("ATG"+""+tankDatum.getId()+"("+String.valueOf(key)+")");
//                                    }

                                            if (status == true) {
                                                rightTextView.setText("Connected");
                                            } else {
                                                rightTextView.setText("Disconnected");
                                            }

                                           for (int i=0;i<placeHolderLinearLayout.getChildCount();i++){
                                               View v=placeHolderLinearLayout.getChildAt(i);
                                               if(v.getId()==portNo){
                                                 placeHolderLinearLayout.removeView(v);
                                               }

                                           }

                                               l.setId(portNo);
                                            placeHolderLinearLayout.addView(l);
                                             int height=l.getHeight();





                                        }
                                    });

                                }
                                catch (Exception e){
                                    Bugfender.d(TAG, e.getMessage());

                                }
                            }
                        });
                    }
                    else if(i-dispenserIds.size()<tankDatas.size()){
                        new Server485(tankDatas.get(i-dispenserIds.size()).getIp_address(),Integer.parseInt(tankDatas.get(i-dispenserIds.size()).getPort_no()), new CheckConnectedAndDisconnectedListner() {
                            @Override
                            public void updateSocketGlobal(int portNo, AsyncSocket socket, boolean status) {
                                socketHashMap.put(portNo,socket);
                                statusHashmap.put(portNo,status);
                                Bugfender.d(TAG, ""+portNo+","+socket+","+status);
                                PumpHardwareInfoResponse.Data.DispenserId dispenser=Utils.getDispenser(dispenserIds,portNo);
                                PumpHardwareInfoResponse.Data.TankDatum  tankDatum  =Utils.getTank(tankDatas,portNo);
                                if(dispenser!=null){
                                    dispenser.setConnected_Status(status);

                                }
                                else {
                                    tankDatum.setConnected_Status(status);

                                }
                                try {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            LayoutInflater vi = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                            View l = vi.inflate(R.layout.hardware_list, null);
                                            TextView leftTextView = (TextView) l.findViewById(R.id.hrd_name);
                                            TextView rightTextView = (TextView) l.findViewById(R.id.totalConnected);
                                            if (dispenser != null) {
                                                leftTextView.setText("Dispenser" + dispenser.getId() + "" + "(" + String.valueOf(portNo) + ")");
                                            } else {
                                                leftTextView.setText("ATG" + "" + tankDatum.getId() + "(" + String.valueOf(portNo) + ")");
                                            }

                                            if (status == true) {
                                                rightTextView.setText("Connected");
                                            } else {
                                                rightTextView.setText("Disconnected");
                                            }
                                            placeHolderLinearLayout.addView(l);


                                        }
                                    });
                                }
                                catch (Exception e){
                                    Bugfender.d(TAG, e.getMessage());

                                }

                            }
                        });
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Gson gson = new Gson();
                MapWrapper wrapper = new MapWrapper();
                wrapper.setStatusHashMap(statusHashmap);
                String serializedMap = gson.toJson(wrapper);
                SharedPref.setSocketConnnectionDetail(serializedMap);
                String json = gson.toJson(obj);
                SharedPref.setHardwareDetail(json);
                Bugfender.d(TAG, ""+json+","+serializedMap);
                if(getActivity()!=null) {
                    ((SynergyApplicationClass) getActivity().getApplication()).setSocketData(socketHashMap);
                    ((SynergyApplicationClass) getActivity().getApplication()).setResponse(obj);
                     Bugfender.d(TAG,""+json);
                     startActivity(new Intent(getActivity(), CommonActivity.class));
                     getActivity().finish();
                }
            }
        }
        new ExampleAsyncTask(getActivity()).execute();

        Animation animation = AnimationUtils.loadAnimation(requireContext(), R.anim.scanner_anim_file);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        scanning_bar_view.startAnimation(animation);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        if (getFragmentManager() != null)
            super.onDismiss(dialog);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }




}
