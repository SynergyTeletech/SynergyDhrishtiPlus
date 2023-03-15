package com.synergy.synergydhrishtiplus.utils;

import static android.text.TextUtils.isEmpty;

import android.util.Log;

import com.koushikdutta.async.AsyncSocket;
import com.synergy.synergydhrishtiplus.data_model.NozzleModel;
import com.synergy.synergydhrishtiplus.data_model.PumpHardwareInfoResponse;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
public class Utils {

    public static final int INDEX_NOT_FOUND = -1;
    public static   byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    public static String rev_U(String n_U) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i <= n_U.length() - 2; i = i + 2) {
            result.append(new StringBuilder(n_U.substring(i, i + 2)).reverse());
        }
        return result.reverse().toString();
    }

    public static String hexToAscii(String res){
        StringBuilder output = new StringBuilder("");

        for (int i = 0; i < res.length(); i += 2) {
            String str = res.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }

        return output.toString();


    }

    private static short charToByte(char hexChar) {
        return (byte)"0123456789ABCDEF".indexOf(hexChar);
    }
    public static Integer getKeyByValue(Map<Integer, AsyncSocket> map, AsyncSocket value) {
        for (Map.Entry<Integer, AsyncSocket> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
    public static  AsyncSocket getValueByKey(Map<Integer, AsyncSocket> map,int port){
        if(map.containsKey(port)){
           return map.get(port);

        }
        return null;

    }
    public static String encodeHexString(byte[] byteArray) {
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
    public  static PumpHardwareInfoResponse.Data.DispenserId getDispenser(List< PumpHardwareInfoResponse.Data.DispenserId> dispenser, int port){
        for(PumpHardwareInfoResponse.Data.DispenserId dispenserId : dispenser) {
            if(dispenserId.getPort_number().equals(String.valueOf(port))) {
                return dispenserId;
            }
        }
        return null;



    }
    public  static PumpHardwareInfoResponse.Data.TankDatum getTank(List< PumpHardwareInfoResponse.Data.TankDatum> tankDatas,int port){
        for(PumpHardwareInfoResponse.Data.TankDatum tankDatum : tankDatas) {
            if(tankDatum.getPort_no().equals(String.valueOf(port))) {
                return tankDatum;
            }
        }
        return null;

    }
    public  static NozzleModel getNozzle(List<NozzleModel> dispenserModels , int port){

        for(NozzleModel tankDatum : dispenserModels) {
            Log.d("modal",""+tankDatum);
            if(tankDatum.getPort()!=null) {
                if (tankDatum.getPort().equals(String.valueOf(port))) {
                    return tankDatum;
                }
            }
        }
        return null;

    }

    public static String convertToAscii(String hex) {
        try {
            StringBuilder sb = new StringBuilder();
            StringBuilder temp = new StringBuilder();

            for (int i = 0; i < hex.length() - 1; i += 2) {
                String output = hex.substring(i, (i + 2));
                int decimal = Integer.parseInt(output.trim(), 16);
                sb.append((char) decimal);
                temp.append(decimal);
            }
            return sb.toString();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return "";
    }
    public static String calculateCheckSum(byte[] byteData) {
        byte chkSumByte = 0x00;
        for (int i = 0; i < byteData.length; i++) {
            chkSumByte ^= byteData[i];
        }
        // Log.d("createdCheckSum", convertToAscii(String.valueOf(chkSumByte)));
        return String.format("%02x", chkSumByte);
    }



    public static int findNextOccurance(String source, String findElement, int n) {
        int index = source.indexOf(findElement);
        if (index == -1) return -1;

        for (int i = 1; i < n; i++) {
            index = source.indexOf(findElement, index + 1);
            if (index == -1) return -1;
        }
        return index;
    }

    public static String convertStringToHex(String str) {

        StringBuffer hex = new StringBuffer();

        // loop chars one by one
        for (char temp : str.toCharArray()) {

            // convert char to int, for char `a` decimal 97
            int decimal = (int) temp;

            // convert int to hex, for decimal 97 hex 61
            hex.append(Integer.toHexString(decimal));
        }

        return hex.toString();

    }



        public static int countMatches(final CharSequence str, final CharSequence sub) {
            if (isEmpty(str) || isEmpty(sub)) {
                return 0;
            }
            int count = 0;
            int idx = 0;
            while ((idx = CharSequenceUtils.indexOf(str, sub, idx)) != INDEX_NOT_FOUND) {
                count++;
                idx += sub.length();
            }
            return count;
        }

    public static boolean isInternetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
            // Log error
        }
        return false;
    }

    public static String intToBinary (int n, int numOfBits) {
        String binary = "";
        for(int i = 0; i < numOfBits; ++i, n/=2) {
            switch (n % 2) {
                case 0:
                    binary = "0" + binary;
                    break;
                case 1:
                    binary = "1" + binary;
                    break;
            }
        }

        return binary;
    }


}


