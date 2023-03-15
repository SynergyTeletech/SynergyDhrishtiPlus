package com.synergy.synergydhrishtiplus.server_pack;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncServerSocket;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.callback.ListenCallback;
import com.synergy.synergydhrishtiplus.DPApplication;
import com.synergy.synergydhrishtiplus.dialogs.ScannerDialogFragment;
import com.synergy.synergydhrishtiplus.listners.CheckConnectedAndDisconnectedListner;
import com.synergy.synergydhrishtiplus.utils.DataConversion;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class SocketCreate {
    private InetAddress host;
    private int port;
    private static AsyncSocket socket;
    private static AsyncServer asyncServer;
   static CheckConnectedAndDisconnectedListner checkConnectedAndDisconnectedListner;

    public SocketCreate(String host, int port,CheckConnectedAndDisconnectedListner checkConnectedAndDisconnectedListner) {
        try {
            this.host = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        this.port = port;
        this.checkConnectedAndDisconnectedListner=checkConnectedAndDisconnectedListner;


        setUp();
    }

    public static void updateListner(CheckConnectedAndDisconnectedListner checkConnectedAndDisconnectedListner){
        checkConnectedAndDisconnectedListner = checkConnectedAndDisconnectedListner;
    }

    public static AsyncSocket getSocket() {
        return socket;
    }

    public static AsyncServer getAsyncServer() {
        return asyncServer;
    }

    private void setUp() {
        asyncServer = new AsyncServer("Queue");
        asyncServer.listen(host, port, new ListenCallback() {
            @Override
            public void onAccepted(final AsyncSocket socket) {
                Log.d("TAG", "FragmentManager" + socket.getServer());

                SocketCreate.socket = socket;
                Log.d("Serverport",""+port);
                checkConnectedAndDisconnectedListner.updateSocketGlobal(port,socket,true);
                handleAccept(socket);
            }

            @Override
            public void onListening(AsyncServerSocket socket) {
                Log.d("TAG", "Server Listning start"+socket.getLocalPort());
                checkConnectedAndDisconnectedListner.updateSocketGlobal(port,null,false);

                SocketCreate.socket = null;
            }

            @Override
            public void onCompleted(Exception ex) {
                if(ex !=null){
                    checkConnectedAndDisconnectedListner.updateSocketGlobal(port,socket,false);
                }
                Log.d("TAG", ex.getLocalizedMessage() );

                Log.d(
                        "TAG", "Server complete stop");
            }
        });
    }

    private void handleAccept(AsyncSocket socket) {
        socket.setDataCallback(new DataCallback() {
            @Override
            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                byte[] bytesData = bb.getAllByteArray();
                String recive = new String(bytesData);
                String var=    DataConversion.encodeHexString(bytesData);

                System.out.println("[Server Queue] Received Message 54307 " + var);
            }
        });

        socket.setClosedCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    if (ex instanceof IOException) {

                    } else {

                    }
                }
                System.out.println("[Server Queue] Successfully closed connection");
            }
        });

        socket.setEndCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    if (ex instanceof IOException) {
                        try {

                        }
                        catch (Exception e){
                            e.fillInStackTrace();
                        }


                    } else {

                    }
                }
                System.out.println("[Server Queue] Successfully end connection" + ex.getLocalizedMessage());
            }
        });


    }
}
