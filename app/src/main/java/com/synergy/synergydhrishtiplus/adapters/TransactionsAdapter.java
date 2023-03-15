package com.synergy.synergydhrishtiplus.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.synergy.synergydhrishtiplus.R;
import com.synergy.synergydhrishtiplus.data_model.TransactionModel;
import java.util.List;
public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder> {
    Context mContext;
    List<TransactionModel> mTransactionList;
    public TransactionsAdapter(Context context, List<TransactionModel> model) {
        this.mContext=context;
        this.mTransactionList=model;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view=   LayoutInflater.from(mContext).inflate(R.layout.transactionlist,parent,false);
     return new TransactionViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
              TransactionModel modal= mTransactionList.get(position);
              holder.transaction.setText(modal.getTransaction_id());
              holder.volume.setText(modal.getVolume());
              holder.totaliser.setText(modal.getTotaliser());
              holder.amount.setText(modal.getAmount());
              holder.price.setText(modal.getRate());
              holder.dispenser.setText(modal.getDispenser_id());
              holder.start_time.setText(modal.getStart_time());
              holder.end_time.setText(modal.getEnd_time());
    }

    @Override
    public int getItemCount() {
        return mTransactionList.size();
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder{
        TextView transaction,volume,amount,price,totaliser,dispenser,start_time,end_time;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
          transaction=itemView.findViewById(R.id.tn_nov)  ;
          volume=itemView.findViewById(R.id.vol_txv);
          amount=itemView.findViewById(R.id.Amt_txv);
          price=itemView.findViewById(R.id.price_txtv);
          totaliser=itemView.findViewById(R.id.totaliser_v);
          dispenser=itemView.findViewById(R.id.dispenser_v);
          start_time=itemView.findViewById(R.id.start_time);
          end_time=itemView.findViewById(R.id.end_time);
        }
    }
}
