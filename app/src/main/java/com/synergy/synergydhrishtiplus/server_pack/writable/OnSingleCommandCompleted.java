package com.synergy.synergydhrishtiplus.server_pack.writable;

import com.koushikdutta.async.AsyncSocket;

public interface OnSingleCommandCompleted {
    void onSingleCommandCompleted(String currentCommand, String response, AsyncSocket socket);
}
