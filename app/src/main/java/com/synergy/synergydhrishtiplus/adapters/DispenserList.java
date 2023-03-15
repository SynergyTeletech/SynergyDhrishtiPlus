package com.synergy.synergydhrishtiplus.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.synergy.synergydhrishtiplus.R;
import com.synergy.synergydhrishtiplus.data_model.PumpHardwareInfoResponse;

import java.util.List;

public class DispenserList  extends RecyclerView.Adapter<DispenserList.DispenseHolder> {


    Context context;
    List<PumpHardwareInfoResponse.Data.DispenserId> dispensers;
    List<PumpHardwareInfoResponse.Data.TankDatum> tankData;

    public DispenserList(Context context, List<PumpHardwareInfoResponse.Data.DispenserId> dispensers, List<PumpHardwareInfoResponse.Data.TankDatum> tankData) {
        this.context = context;
        this.dispensers = dispensers;
        this.tankData = tankData;
    }

    @NonNull
    @Override
    public DispenserList.DispenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.hardware_list,parent,false);
        return new  DispenseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DispenserList.DispenseHolder holder, int position) {
        if(position<dispensers.size() && dispensers!=null){
            PumpHardwareInfoResponse.Data.DispenserId dispenserId=dispensers.get(position);
            holder.hardware.setText("Dispenser"+dispenserId.getId());
            if(dispenserId.isConnected_Status()){
                holder.status.setText("Connected");
            }
            else{
                holder.status.setText("Disconnected");
            }


        }
        else if(tankData.size()>0 && tankData!=null){
            if(position-dispensers.size()<tankData.size()){
                PumpHardwareInfoResponse.Data.TankDatum tankDatum=tankData.get(position-dispensers.size());
                holder.hardware.setText("ATG"+ tankDatum.getId());
                if(tankDatum.isConnected_Status()){
                    holder.status.setText("Connected");
                }else {
                    holder.status.setText("Disconnected");
                }


            }


        }

    }

    @Override
    public int getItemCount() {
        return dispensers.size()+tankData.size() ;
    }

    public class DispenseHolder extends RecyclerView.ViewHolder {
        TextView hardware, status;
        public DispenseHolder(@NonNull View itemView) {
            super(itemView);
            hardware=itemView.findViewById(R.id.hrd_name);
            status=itemView.findViewById(R.id.totalConnected);
        }
    }
}
