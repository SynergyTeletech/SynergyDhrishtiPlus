package com.synergy.synergydhrishtiplus.listners;

public interface OnStatusQueueListener {
    void onStatusQueueQueueEmpty(boolean isEmpty, String lastResponse);
    void OnStatusQueueCommandCompleted(int currentCommand, String response);
}
