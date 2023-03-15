package com.synergy.synergydhrishtiplus.server_pack.writable;

import com.koushikdutta.async.AsyncSocket;

public interface OnAllCommandCompleted {
    void commandsAllQueueEmpty(boolean isEmpty, String lastResponse);
    void onAllCommandCompleted(int currentCommand, String response, AsyncSocket socket);

}
