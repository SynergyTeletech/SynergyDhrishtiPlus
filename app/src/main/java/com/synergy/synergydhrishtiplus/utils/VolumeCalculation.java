package com.synergy.synergydhrishtiplus.utils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.synergy.synergydhrishtiplus.data_model.PumpHardwareInfoResponse;
import org.json.JSONObject;
import java.util.LinkedHashMap;

public class VolumeCalculation extends ViewModel {
    public static MutableLiveData<String> atgData=new MutableLiveData<>();
    public static LinkedHashMap<String, Object> atgLinkedHashMapTank;
    private static double tank1MaxVolume = 0f, tankMaxHeight = 0f, totalAvailableVolume = 0f;
    public static float getAtgState(float response, @NonNull PumpHardwareInfoResponse.Data.TankDatum data) {
        try {
           float tank1AtgReading=response ;
//            String atgtype = data.getMakeOfAtg();
//            switch (atgtype) {
//                case "OPW":
//                    tank1AtgReading = findHeight(response);
//                    Log.d("height",""+tank1AtgReading);
//                    break;
//                case "GVR":
//                    tank1AtgReading=findHeight1(response);
//                    Log.d("height",""+tank1AtgReading);
//                    break;
//                case "PROGAUGE":
//                    tank1AtgReading=findHeight2(response);
//                    Log.d("height",""+tank1AtgReading);
//
//
//            }

            float preAtgReading = 0, postAtgReading, preAtgVolume = 0, postAtgVolume, meanAtgVolume;
            String service = data.getDipChartData();
            JSONObject jsonObject1 = null;

            jsonObject1 = new JSONObject(service);
            atgLinkedHashMapTank = new Gson().fromJson(jsonObject1.toString(), LinkedHashMap.class);

            Object obj = atgLinkedHashMapTank.entrySet().toArray()[atgLinkedHashMapTank.size() - 1];
            LinkedHashMap.Entry linkedTreeMaps = (LinkedHashMap.Entry) (obj);
            JsonObject jsonObjects = (new Gson()).toJsonTree(linkedTreeMaps.getValue()).getAsJsonObject();
            if (!jsonObjects.get("A").getAsString().equalsIgnoreCase("ATG")) {
                tank1MaxVolume = Float.parseFloat(jsonObjects.get("B").getAsString());
                tankMaxHeight = Float.parseFloat(jsonObjects.get("A").getAsString());
            }


            for (LinkedHashMap.Entry hashMap : atgLinkedHashMapTank.entrySet()) {


                LinkedTreeMap linkedTreeMap = (LinkedTreeMap) hashMap.getValue();
                JsonObject jsonObject = (new Gson()).toJsonTree(linkedTreeMap).getAsJsonObject();
                if (!jsonObject.get("A").getAsString().equalsIgnoreCase("ATG")) {
                    float tableAtgReading = Float.parseFloat(jsonObject.get("A").getAsString());

                    if (tableAtgReading == tank1AtgReading) {
                        float tank1Volume = Float.parseFloat(jsonObject.get("B").getAsString());
                        atgData.postValue(jsonObject.get("B").getAsString());
                        return tank1Volume;


                    } else if (tableAtgReading < tank1AtgReading) {
                        preAtgReading = tableAtgReading;
                        preAtgVolume = Float.parseFloat(jsonObject.get("B").getAsString());

                    } else if (tableAtgReading > tank1AtgReading) {
                        float prevAtgreading = preAtgReading;
                        float prevAtgVolume = preAtgVolume;
                        postAtgReading = Float.parseFloat(jsonObject.get("A").getAsString());
                        postAtgVolume = Float.parseFloat(jsonObject.get("B").getAsString());
                        meanAtgVolume = (postAtgVolume - prevAtgVolume) / (postAtgReading - prevAtgreading) * (tank1AtgReading - prevAtgreading) + prevAtgVolume;
                        atgData.postValue(String.valueOf(meanAtgVolume));

                        return meanAtgVolume;

                    }


                }
                else {
                 Log.d("test","check");
                }
            }


        }
        catch (Exception e) {
          Log.e("error",e.getMessage());

        }
        return 0.0f;
    }

    private static float findHeight2(String response) {
        String[] separated = response.split("=");
        return Float.parseFloat(separated[3]);
    }

    private static float findHeight1(String response) {
        String[] separated = response.split("=");
        return Float.parseFloat(separated[3]);
    }

    private static float findHeight(String response) {
        String[] separated = response.split("=");
        return Float.parseFloat(separated[3]);
    }
}










