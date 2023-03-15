package com.synergy.synergydhrishtiplus.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncServerSocket;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.callback.ListenCallback;
import com.synergy.synergydhrishtiplus.R;
import com.synergy.synergydhrishtiplus.data_model.NozzleModel;
import com.synergy.synergydhrishtiplus.dialogs.ScannerDialogFragment;
import com.synergy.synergydhrishtiplus.errorlistener.ExceptionHandler;
import com.synergy.synergydhrishtiplus.listners.CheckConnectedAndDisconnectedListner;
import com.synergy.synergydhrishtiplus.server_pack.SocketCreate;
import com.synergy.synergydhrishtiplus.socket.Server484;
import com.synergy.synergydhrishtiplus.socket.Server485;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class DispenserDetail extends Fragment {
    NozzleModel nozzleModel;
    TextView volume_txt,Amt_txt,status_txt,price_txt,prod_txt,pump_txt;
    TextView tv_payment,tv_vehicle_no,tv_totalizer,tv_amount,tv_volume,tv_product,tv_status,tv_date_time;

        @Override
       public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      }
       @Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
           Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
           View v= inflater.inflate(R.layout.fragment_dispenser_detail, container, false);
           Bundle b = getArguments();
           if(b!=null){
               nozzleModel =(NozzleModel) b.getSerializable("UserValidateObject");
           }
//           new Server484("192.168.1.206",54306, DispenserDetail.this);


           initView(v);
           setValue();
           return v;
       }
      private void initView(View v) {
      tv_payment=v.findViewById(R.id.tv_payment);
      tv_vehicle_no=v.findViewById(R.id.tv_vehicle_no);
      tv_totalizer=v.findViewById(R.id.tv_totalizer);
      tv_amount=v.findViewById(R.id.tv_amount);
      tv_volume=v.findViewById(R.id.tv_volume);
      tv_product=v.findViewById(R.id.tv_product);
      tv_status=v.findViewById(R.id.tv_status);
      tv_date_time=v.findViewById(R.id.tv_date_time);
      volume_txt=v.findViewById(R.id.volume_txt);
      Amt_txt=v.findViewById(R.id.Amt_txt);
      status_txt=v.findViewById(R.id.status_txt);
      price_txt=v.findViewById(R.id.price_txt);
      prod_txt=v.findViewById(R.id.prod_txt);
      pump_txt=v.findViewById(R.id.pump_txt);



    }
    private void setValue() {
        if(nozzleModel!=null) {
            try {
                tv_payment.setText(nozzleModel.getAmount());
                tv_amount.setText(nozzleModel.getPrice());
                tv_volume.setText(nozzleModel.getVolume());
                tv_product.setText(nozzleModel.getProduct());
                tv_status.setText(nozzleModel.getStatus());
                volume_txt.setText("Volume- "+nozzleModel.getVolume());
                Amt_txt.setText("Amount- "+nozzleModel.getAmount());
                status_txt.setText("Status- "+nozzleModel.getStatus());
                price_txt.setText("Price- "+nozzleModel.getPrice());
                prod_txt.setText("Product- "+nozzleModel.getProduct());
                pump_txt.setText("PUMP "+nozzleModel.getPumpId() +" "  +nozzleModel.getMakeOfDispenser()+"  "+nozzleModel.getPumpId()+"-"+nozzleModel.getNozzleId());
            }
            catch (Exception e){
                Log.e("Exception",e.getLocalizedMessage());
            }

        }

    }


}