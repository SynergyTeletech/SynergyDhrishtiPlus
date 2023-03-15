package com.synergy.synergydhrishtiplus.adapters;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.synergy.synergydhrishtiplus.R;
import com.synergy.synergydhrishtiplus.data_model.NozzleModel;
import com.synergy.synergydhrishtiplus.listners.RecyclerviewListener;

import java.util.List;

public class NozzelsAdapter extends RecyclerView.Adapter<NozzelsAdapter.NozzleViewHolder> {
    private Context context;
    private List<NozzleModel> nozzleModels;
    RecyclerviewListener recyclerviewListener;

    public NozzelsAdapter(Context context, List<NozzleModel> nozzleModels,RecyclerviewListener recyclerviewListener) {
        this.context        = context;
        this.nozzleModels   = nozzleModels;
        this.recyclerviewListener=recyclerviewListener;
    }
    @NonNull
    @Override
    public NozzleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.nozzle_list_single_layout, null);
        return new NozzleViewHolder(view,recyclerviewListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NozzleViewHolder holder, int position) {
        NozzleModel nozzleModel = nozzleModels.get(position);
        holder.nozzleHeading.setText(nozzleModel.getMakeOfDispenser()+" "+nozzleModel.getPumpId()+" - "+nozzleModel.getNozzleId());
        holder.nozzleStatus.setText("STATUS-"+nozzleModel.getStatus());
        if(nozzleModel.getStatus().contains("FUELING STATE")){
            holder.nozzleStatus.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.fueling_state, null));
            Glide.with(context).asGif().load(R.drawable.running_fuel3).into(holder.imageView);
        }
        else {
            holder.nozzleStatus.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.white, null));
            Glide.with(context).load(R.drawable.blue_nozzle).into(holder.imageView);
        }
        holder.volumeTv.setText("Vol: "+nozzleModel.getVolume());
        holder.amountTv.setText("Amt: "+nozzleModel.getAmount());
        holder.priceTv.setText("Price: "+nozzleModel.getPrice());
        holder.productTv.setText("Product: "+nozzleModel.getProduct());
    }

    @Override
    public int getItemCount() {
        return nozzleModels.size();
    }

    class NozzleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nozzleHeading, nozzleStatus, volumeTv, amountTv, priceTv, productTv;
        RecyclerviewListener recyclerviewListener;
        ImageView imageView;

        public NozzleViewHolder(@NonNull View itemView,RecyclerviewListener recyclerviewListener ) {
            super(itemView);
            this.recyclerviewListener=recyclerviewListener;
            nozzleHeading   = itemView.findViewById(R.id.nozzleHeading);
            nozzleStatus    = itemView.findViewById(R.id.nozzleStatus);
            volumeTv        = itemView.findViewById(R.id.volumeTv);
            amountTv        = itemView.findViewById(R.id.amountTv);
            priceTv         = itemView.findViewById(R.id.priceTv);
            productTv       = itemView.findViewById(R.id.productTv);
            imageView       =itemView.findViewById(R.id.tankImage);
            itemView.setOnClickListener(this);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(context, CommonStatusActivity.class);
//                  //  intent.putExtra("your_extra","your_class_value");
//                    context.startActivity(intent);
//                }
//            });
        }

        @Override
        public void onClick(View view) {
            recyclerviewListener.onElementclick(getAdapterPosition());
        }
    }


}
