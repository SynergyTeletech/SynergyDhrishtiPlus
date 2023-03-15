package com.synergy.synergydhrishtiplus.server_pack.writable;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.synergy.synergydhrishtiplus.SynergyApplicationClass;

import java.util.concurrent.ExecutionException;

public class WriteCommandUtil6 {
    private static OnSingleCommandCompleted onAllCommandCompleted;
    public  static AsyncSocket socket;
    private static boolean isReceived;
    private static int currentCommand;
    private static String Command;

    public WriteCommandUtil6(String command,AsyncSocket socket,  OnSingleCommandCompleted onAllCommandCompleted) {

        this.Command = command;
        this.onAllCommandCompleted = onAllCommandCompleted;
        this.socket=socket;
        Log.d("GV",command +","+ socket);


    }

    public void doCommandChaining() {



        executeCommandOneByOne(Command,socket);





    }

    private synchronized boolean executeCommandOneByOne(String command,AsyncSocket socket) {
        if (sendCommand(command,socket))
        {
            return true;
        }
        return false;
    }

    private synchronized boolean sendCommand(String command,AsyncSocket socket) {
        Log.d("d",""+command);
        socket.getClosedCallback();
        Util.writeAll(socket, hexStringToBytes(command), new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                if(ex!=null){
                    ex.getLocalizedMessage();
                }

            }
        });
        try {

            return new MyTask(socket).execute().get();
        } catch (ExecutionException e)
        {
            Log.d("CommandInQueue7", e.getLocalizedMessage());
            e.printStackTrace();

        } catch (InterruptedException e)
        {
            Log.d("CommandInQueue8", e.getLocalizedMessage());
            e.printStackTrace();
        }


        return false;
    }

    private byte[] hexStringToBytes(String bytes) {
        if (bytes == null || bytes.equals("")) {
            return null;
        }
        bytes = bytes.toUpperCase();
        int length = bytes.length() / 2;
        char[] hexChars = bytes.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;


    }

    private static short charToByte(char hexChar) {
        return (byte)"0123456789ABCDEF".indexOf(hexChar);
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
            Toast.makeText(SynergyApplicationClass.getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return "";
    }

    private static class MyTask extends AsyncTask<Void, Void, Boolean> {
        AsyncSocket socket1;
        public MyTask(AsyncSocket socket) {
            this.socket1=socket;
            Log.d("CommandInQueue9",""+socket1);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            socket1.setDataCallback(new DataCallback() {
                @Override
                public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                    byte[] byteData = bb.getAllByteArray();
                    onAllCommandCompleted.onSingleCommandCompleted(Command, encodeHexString(byteData),socket1);


                    Log.d("CommandInQueue10",""+encodeHexString(byteData));
                    isReceived=true;

                }
            });
            return isReceived;

        }

        @Override
        protected void onPostExecute(Boolean result) {


        }
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


}
