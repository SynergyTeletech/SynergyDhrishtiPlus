package com.synergy.synergydhrishtiplus.data_model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName = "transactions")
public class TransactionModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "transaction_id")
    private String transaction_id;
    @ColumnInfo(name = "rate")
    private String rate;
    @ColumnInfo(name = "volume")
    private String volume;
    @ColumnInfo(name = "amount")
    private String amount;
    @ColumnInfo(name = "totaliser")
    private String totaliser;
    @ColumnInfo(name="dispenser_id")
    private String dispenser_id;
    @ColumnInfo(name="start_time")
    private String start_time;
    @ColumnInfo(name="end_time")
    private String end_time;
    @ColumnInfo(name = "status")
    private boolean status;
//    @ColumnInfo(name="product")
//    private String product;


    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDispenser_id() {
        return dispenser_id;
    }

    public void setDispenser_id(String dispenser_id) {
        this.dispenser_id = dispenser_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTotaliser() {
        return totaliser;
    }

    public void setTotaliser(String totaliser) {
        this.totaliser = totaliser;
    }

    public boolean isStatus() {
        return status;
    }

//    public String getProduct() {
//        return product;
//    }

//    public void setProduct(String product) {
//        this.product = product;
//    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
