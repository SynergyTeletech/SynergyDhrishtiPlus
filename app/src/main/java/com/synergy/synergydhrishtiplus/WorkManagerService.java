package com.synergy.synergydhrishtiplus;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.synergy.synergydhrishtiplus.data_model.Postmodel;
import com.synergy.synergydhrishtiplus.data_model.TransactionModel;
import com.synergy.synergydhrishtiplus.network.ApiClient;
import com.synergy.synergydhrishtiplus.network.ApiInterface;
import com.synergy.synergydhrishtiplus.room.DatabaseClient;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkManagerService extends Worker {
    public WorkManagerService(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }
    @NonNull
    @Override
    public Result doWork() {
        List<TransactionModel> list=DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                .transactionDao().getAll();
        for (int i=0;i<list.size();i++){
              TransactionModel transactionModel=list.get(i);
              if(!transactionModel.isStatus()==true) {
                  submitFinalData(transactionModel, i);
              }
        }
        return Result.success();
    }

    private void submitFinalData(TransactionModel model,int i) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("dispenser_id",model.getDispenser_id());
        hashMap.put("totalizer",model.getTotaliser());
        hashMap.put("fueling_start_time",model.getStart_time());
        hashMap.put("fueling_end_time",model.getEnd_time());
        hashMap.put("amount",model.getAmount());
        hashMap.put("qty",model.getVolume());
        hashMap.put("price",model.getRate());
        hashMap.put("transaction_number",model.getTransaction_id());
        ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
        apiInterface.postModal(hashMap).enqueue(new Callback<Postmodel>() {
            @Override
            public void onResponse(Call<Postmodel> call, Response<Postmodel> response) {
                if(response.body().getData().getStatusCode()==200){

                    Log.d("response","response");

                    if(i>9) {
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {

                                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                        .transactionDao().setStatus(model.getTransaction_id(),true);
                                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                        .transactionDao().delete(model);


                            }
                        });
                        thread.start();
                    }
                    else {
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
//
                                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                        .transactionDao().setStatus(model.getTransaction_id(),true);


                            }
                        });
                        thread.start();

                    }

                }
            }

            @Override
            public void onFailure(Call<Postmodel> call, Throwable t) {
                Log.e("errorinpost",t.getLocalizedMessage());
            }
        });
    }
}
