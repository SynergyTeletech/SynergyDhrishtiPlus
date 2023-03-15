package com.synergy.synergydhrishtiplus.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.synergy.synergydhrishtiplus.data_model.TransactionModel;

import java.util.List;
@Dao
public interface TransactionDao {

    @Query("SELECT * FROM transactions")
    List<TransactionModel> getAll();
    @Insert
    void insert(TransactionModel transactionModel);
    @Query("DELETE FROM transactions")
    public void deleteData();
    @Delete
    void delete(TransactionModel transactionModel);
    @Update
    void update(TransactionModel transactionModel);
    @Query("UPDATE transactions SET status = :status  WHERE transaction_id LIKE :transaction_id")
    void  setStatus(String transaction_id, boolean status);


}
