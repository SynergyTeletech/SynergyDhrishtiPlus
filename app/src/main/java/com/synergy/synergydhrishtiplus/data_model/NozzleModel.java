package com.synergy.synergydhrishtiplus.data_model;

import java.io.Serializable;

public class NozzleModel implements Serializable {
    String dispenser_id;
    String pump_num;
    String nozzle_num;
    String port;
    String pumpId;
    String nozzleId;
    String makeOfDispenser;
    String volume;
    String amount;
    String product;
    String status;
    String price;
    String preset_type;
    String volume_totaliser;
    String End_time;




    public String getPumpId() {
        return pumpId;
    }

    public void setPumpId(String pumpId) {
        this.pumpId = pumpId;
    }

    public String getNozzleId() {
        return nozzleId;
    }

    public void setNozzleId(String nozzleId) {
        this.nozzleId = nozzleId;
    }

    public String getMakeOfDispenser() {
        return makeOfDispenser;
    }

    public void setMakeOfDispenser(String makeOfDispenser) {
        this.makeOfDispenser = makeOfDispenser;
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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getVolume_totaliser() {
        return volume_totaliser;
    }

    public void setVolume_totaliser(String volume_totaliser) {
        this.volume_totaliser = volume_totaliser;
    }

    public String getEnd_time() {
        return End_time;
    }

    public void setEnd_time(String end_time) {
        End_time = end_time;
    }

    public String getDispenser_id() {
        return dispenser_id;
    }

    public void setDispenser_id(String dispenser_id) {
        this.dispenser_id = dispenser_id;
    }

    public String getPreset_type() {
        return preset_type;
    }

    public void setPreset_type(String preset_type) {
        this.preset_type = preset_type;
    }

    public String getPump_num() {
        return pump_num;
    }

    public void setPump_num(String pump_num) {
        this.pump_num = pump_num;
    }

    public String getNozzle_num() {
        return nozzle_num;
    }

    public void setNozzle_num(String nozzle_num) {
        this.nozzle_num = nozzle_num;
    }

    public String getStatus() {
        return status;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
