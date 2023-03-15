package com.synergy.synergydhrishtiplus.listners;

import com.koushikdutta.async.AsyncSocket;

public interface CheckConnectedAndDisconnectedListner {
    void updateSocketGlobal(int portNo, AsyncSocket socket,boolean status);

}
