package com.synergy.synergydhrishtiplus.data_model;

import com.koushikdutta.async.AsyncSocket;

import java.io.Serializable;

public class CustomMessageEvent implements Serializable {
 private String port;
 private boolean status;
 private AsyncSocket socket;

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public AsyncSocket getSocket() {
        return socket;
    }

    public void setSocket(AsyncSocket socket) {
        this.socket = socket;
    }
}
