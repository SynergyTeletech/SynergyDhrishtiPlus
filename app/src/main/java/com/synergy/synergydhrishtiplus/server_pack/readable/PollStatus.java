package com.synergy.synergydhrishtiplus.server_pack.readable;

import android.util.Log;

public class PollStatus {

     public static  String  getPollState(String response) {
//        String status=getPollStatusBit(response);
//        String nozle=getNozle(response);
        switch (response) {
            case "61":
                return "IDLE";
            case "71":
                Log.d("pumpState:", "CALL STATE");
                return "CALLSTATE";
            case "32":

                return "PRESETREADYSTATE";
            case "91":
                return "FUELINGSTATE";
            case "34":
                return "PAYABLESTATE";
            case "35":
                return "SUSPENDEDSTATE";
            case "36":
                return "STOPPEDSTATE";
            case "38":
                return "IN-OPERATIVE STATE";
            case "81":
                return "AUTHORIZESTATE";
            case "3d":
                return "Suspend Started State";
            case "3e":
                return "Wait For Preset State";
            case "3b":
                return "STARTED STATE";
            case "a1":
                return "PRESETDONE";

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
    private static String getPollStatusBit1(byte[] byteArray) {
        StringBuffer hexStringBuffer = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            hexStringBuffer.append(byteToHex(byteArray[i]));
        }
        return hexStringBuffer.toString();
    }
    public static String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
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










