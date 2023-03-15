package com.synergy.synergydhrishtiplus.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.synergy.synergydhrishtiplus.data_model.TransactionModel;

@Database(entities = {TransactionModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TransactionDao transactionDao();
}
