package com.synergy.synergydhrishtiplus.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.race604.drawable.wave.WaveDrawable;
import com.synergy.synergydhrishtiplus.R;
import com.synergy.synergydhrishtiplus.data_model.PumpHardwareInfoResponse;
import java.util.List;

public class TankAdapter extends RecyclerView.Adapter<TankAdapter.TankViewHolder> {
    private Context context;
    private List<PumpHardwareInfoResponse.Data.TankDatum> tankModels;

    public TankAdapter(Context context, List<PumpHardwareInfoResponse.Data.TankDatum> tankModels) {
        this.context = context;
        this.tankModels = tankModels;
    }

    @NonNull
    @Override
    public TankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.tank_list_single_layout, null);
        return new TankViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TankViewHolder holder, int position) {

        PumpHardwareInfoResponse.Data.TankDatum tankModel = tankModels.get(position);
        if(tankModel.getVolume()!=0){
         float capicity=Float.parseFloat(tankModel.getCapacity());
         float fraction=tankModel.getVolume()/capicity*10000;
         holder.mWaveDrawable.setLevel(Math.round(fraction));

        }

        holder.mWaveDrawable.setWaveAmplitude(0);
        holder.tankHeading.setText("Tank "+ (position+1)+"-");
        if(tankModel.getProductName().equalsIgnoreCase("SYNERGY GREEN DIESEL")){
            holder.tankProduct.setText("SGD");
        }
        else {
            holder.tankProduct.setText(tankModel.getProductName());
        }
             String s = String.format("%.2f", tankModel.getVolume());
            holder.tankvolume.setText("Volume-"+s);




    }

    @Override
    public int getItemCount() {
        return tankModels.size();
    }

    class TankViewHolder extends RecyclerView.ViewHolder{
        TextView tankHeading, tankProduct,tankvolume;
        ImageView imageView;
        private WaveDrawable mWaveDrawable;

        public TankViewHolder(@NonNull View itemView) {
            super(itemView);
            tankHeading = itemView.findViewById(R.id.tankHeading);
            tankProduct = itemView.findViewById(R.id.tankProduct);
            tankvolume=itemView.findViewById(R.id.tankVolume);
            imageView= itemView.findViewById(R.id.tankImage);
            mWaveDrawable = new WaveDrawable(context, R.drawable.blue_tank);
            imageView.setImageDrawable(mWaveDrawable);
        }

    }
}
