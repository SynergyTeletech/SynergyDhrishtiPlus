package com.synergy.synergydhrishtiplus;
import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import androidx.multidex.BuildConfig;
import androidx.multidex.MultiDexApplication;
import com.bugfender.sdk.Bugfender;
import com.koushikdutta.async.AsyncSocket;
import com.synergy.synergydhrishtiplus.data_model.PumpHardwareInfoResponse;

import java.util.HashMap;
public class SynergyApplicationClass extends MultiDexApplication {
    private static Context context;
    HashMap<Integer, AsyncSocket> socketHashMap = new HashMap<>();
    private static Context mContext = null;
    private MutableLiveData<PumpHardwareInfoResponse.Data> responseData=new MutableLiveData<PumpHardwareInfoResponse.Data>();
    private static SynergyApplicationClass mApplicationData = null;

    public static SynergyApplicationClass getInstance(Context context) {
        if (mApplicationData == null) {
            mApplicationData = (SynergyApplicationClass) context.getApplicationContext();
        }
        return mApplicationData;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Bugfender.init(this, "YD7CORgNrK4MztMzEQmYGBI8rzw0In9r", BuildConfig.DEBUG);
        Bugfender.enableCrashReporting();
        Bugfender.enableUIEventLogging(this);
//        Bugfender.enableLogcatLogging();
        context = this;
    }
    public static Context getContext() {
        return context;
    }
    public static Context getApplicationContex()
    {
        return mContext;
    }
    public  void setSocketData(HashMap<Integer, AsyncSocket> socketHashMap){
        this.socketHashMap=socketHashMap;
    }

    public MutableLiveData<PumpHardwareInfoResponse.Data> getDataObserver() {
        if (responseData == null) {
            responseData = new MutableLiveData<PumpHardwareInfoResponse.Data>();
        }
        return responseData;
    }

    public  HashMap<Integer, AsyncSocket> getSocketHashMap() {
        return socketHashMap;
    }

    public void setResponse(PumpHardwareInfoResponse.Data data){
        responseData.setValue(data);

    }
}
