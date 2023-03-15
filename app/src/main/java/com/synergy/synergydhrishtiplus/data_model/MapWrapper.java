package com.synergy.synergydhrishtiplus.data_model;

import com.koushikdutta.async.AsyncSocket;

import java.io.Serializable;
import java.util.HashMap;

public class MapWrapper implements Serializable {

    private HashMap<Integer,Boolean>  statusHashMap;




    public HashMap<Integer, Boolean> getStatusHashMap() {
        return statusHashMap;
    }

    public void setStatusHashMap(HashMap<Integer, Boolean> statusHashMap) {
        this.statusHashMap = statusHashMap;
    }
}
