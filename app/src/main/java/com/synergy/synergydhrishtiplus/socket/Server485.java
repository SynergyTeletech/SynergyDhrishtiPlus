package com.synergy.synergydhrishtiplus.socket;

import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncServerSocket;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.callback.ListenCallback;
import com.synergy.synergydhrishtiplus.data_model.CustomMessageEvent;
import com.synergy.synergydhrishtiplus.dialogs.ScannerDialogFragment;
import com.synergy.synergydhrishtiplus.listners.CheckConnectedAndDisconnectedListner;
import com.synergy.synergydhrishtiplus.utils.DataConversion;
import com.synergy.synergydhrishtiplus.utils.Utils;


import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Server485 {
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private InetAddress host;
    private  int ports;

    private static   AsyncSocket socket1;
    private static  AsyncSocket socket2;

    private static AsyncServer asyncServer;
   public static CheckConnectedAndDisconnectedListner checkConnectedAndDisconnectedListner;

    public Server485(String host, int port, CheckConnectedAndDisconnectedListner checkConnectedAndDisconnectedListner) {
        try {
            this.host = InetAddress.getByName(host);

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        this.ports = port;
        this.checkConnectedAndDisconnectedListner=checkConnectedAndDisconnectedListner;

        setup();
    }
    public static void setOnDataListener(CheckConnectedAndDisconnectedListner ch){
        checkConnectedAndDisconnectedListner=ch;
    }

    public  AsyncSocket getSocket() {
        return socket1;
    }


    public static AsyncServer getAsyncServer() {
        return asyncServer;
    }


    private void setup() {

        asyncServer = new AsyncServer("Queue");
        asyncServer.listen(host, ports, new ListenCallback() {

            @Override
            public void onAccepted(final AsyncSocket socket) {
                int port2 =ports;
                socket1 = socket;
                handleAccept(socket);
                checkConnectedAndDisconnectedListner.updateSocketGlobal(port2,socket1,true);
            }

            @Override
            public void onListening(AsyncServerSocket socket) {
                 int port2=ports;
                checkConnectedAndDisconnectedListner.updateSocketGlobal(port2,null,false);
                System.out.println("[Server Queue] Server started listening for connections"+socket.getLocalPort());
            }

            @Override
            public void onCompleted(Exception ex) {
                System.out.println("[Server Queue] Received Message  " + ex.getLocalizedMessage());
                if (ex != null) {
                    if (ex instanceof IOException) {
                        Log.e("exception",ex.getLocalizedMessage());
                       checkConnectedAndDisconnectedListner.updateSocketGlobal(ports,socket1,false);
                        if (AsyncServer.getCurrentThreadServer().isRunning()) {
                            AsyncServer.getCurrentThreadServer().stop();
                        }
                    } else {
                         Log.d("serverPortError", ex.getLocalizedMessage());
                    }
                }
                System.out.println("[Server Queue] Successfully shutdown server");
            }
        });
    }

    private void handleAccept(final AsyncSocket socket) {
        System.out.println("[Server Queue] New Connection " + socket.toString());

        socket.setDataCallback(new DataCallback() {
            @Override
            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                byte[] bytesData = bb.getAllByteArray();
                String server=  encodeHexString(bytesData);
                String recive = new String(bytesData);
                Log.d("Server",server);



            }
        });

            socket.setClosedCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
//                Log.d("exap1",ex.getLocalizedMessage());
                if (ex != null) {
                    if (ex instanceof IOException) {

                    }
                    else {

                    }
                }
                System.out.println("[Server Queue] Successfully closed connection");
            }
        });

        socket.setEndCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
//                Log.d("exap",ex.getLocalizedMessage());
                if (ex != null) {
                    if (ex instanceof IOException) {
                        try {

                        }
                        catch (Exception e){
//                            e.fillInStackTrace();
                        }


                    }
                    else {

                    }
                }
//                System.out.println("[Server Queue] Successfully end connection" + ex.getLocalizedMessage());
            }
        });

    }

    private String  converttoAscii(String hex) {


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
