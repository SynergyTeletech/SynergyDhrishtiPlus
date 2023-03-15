package com.synergy.synergydhrishtiplus.server_pack.readable;

import android.util.Log;

public class TokheiumStatus {
    public static  String getPollState(String response) {
        String op_code = getPollStatusBit(response);

        switch (op_code) {
            case "30":
                return "IDLE";
            case "31":
                return "CALL STATE";
            case "32":
                return "PRESET READY STATE";
            case "33":
                return "FUELING STATE";
            case "34":
                return "PAYABLE STATE";
            case "35":
                return "SUSPENDED STATE";
            case "36":
                return "STOPPED STATE";
            case "38":
                return "IN-OPERATIVE STATE";
            case "39":
                return "AUTHORIZE STATE";
            case "3d":
                return "Suspend Started State";
            case "3e":
                return "Wait For Preset State";
            case "3b":
                return "STARTED STATE";

            default:
                return "";

        }


    }
    public static  String getNozle(String response){
        String nz_code = getNozleType(response);
        switch (nz_code){
            case "01":
                return "1";

            case "02":
                return "2";

            case "03":
                return "3";

            case "04":
                return "4";

            case "05":
                return "5";


            case "06":
                return "6";

            default:
                return "";
        }

    }

    private static String getPollStatusBit(String hexResponse) {
        if (hexResponse != null && hexResponse.toLowerCase().contains("7f")) {
            Log.e("statusResponseKamal",hexResponse);
            try {
                Log.d("responseBit", hexResponse.substring(hexResponse.indexOf("7f") - 2, hexResponse.indexOf("7f")).trim());
                return hexResponse.substring(hexResponse.indexOf("7f") - 2, hexResponse.indexOf("7f")).trim();
            } catch (Exception e) {
                return "";
            }
        }
        return "";
    }



    private static String getNozleType(String hexResponse) {
        if (hexResponse != null && hexResponse.toLowerCase().contains("7f")) {
            Log.e("get",hexResponse);
            try {
                Log.d("responseBit", hexResponse.substring(0, 2).trim());
                return hexResponse.substring(0, 2).trim();
            } catch (Exception e) {
                return "";
            }
        }
        return "";
    }

}
