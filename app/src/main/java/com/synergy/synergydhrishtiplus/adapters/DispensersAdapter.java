package com.synergy.synergydhrishtiplus.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.synergy.synergydhrishtiplus.R;
import com.synergy.synergydhrishtiplus.data_model.DispenserModel;
import com.synergy.synergydhrishtiplus.listners.DispenserSelectListner;

import java.util.List;

public class DispensersAdapter extends RecyclerView.Adapter<DispensersAdapter.DispenserViewHolder> {
    private Context context;
    int lastSelectedPosition = 0;
    private int countDispenser = 1;
    private List<DispenserModel> dispenserModels;
    private DispenserSelectListner dispenserSelectListner;

    public DispensersAdapter(Context context, List<DispenserModel> dispenserModels) {
        this.context = context;
        this.dispenserModels = dispenserModels;
        this.dispenserSelectListner = dispenserSelectListner;
    }

    @NonNull
    @Override
    public DispensersAdapter.DispenserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dispenser_single_layout, null);
        return new DispenserViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DispenserViewHolder holder, int position) {
        DispenserModel dispenserModel = dispenserModels.get(position);
        countDispenser = position + 1;
        holder.dispenserText.setText("Page "+countDispenser);
        holder.dispenserText.setTag(dispenserModel.getDispenserId());

        holder.dispenserText.setChecked(lastSelectedPosition == position);

//        holder.dispenserText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    dispenserSelectListner.onSelect(buttonView.getTag().toString());
//                    buttonView.setFocusable(true);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return dispenserModels.size();
    }

    class DispenserViewHolder extends RecyclerView.ViewHolder{
        RadioButton dispenserText;

        public DispenserViewHolder(@NonNull View itemView) {
            super(itemView);
            dispenserText = itemView.findViewById(R.id.dispenserText);

            dispenserText.setOnClickListener(v ->{
                lastSelectedPosition = getAdapterPosition();
                notifyDataSetChanged();
            });
        }
    }
}
