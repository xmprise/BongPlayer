// Copyright 2011 Google Inc. All Rights Reserved.

package com.google.code.androidsmb;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * A service that process each file transfer request i.e Intent by opening a
 * socket connection with the WiFi Direct Group Owner and writing the file
 */
public class FileTransferService extends IntentService {

    private static final int SOCKET_TIMEOUT = 5000;
    public static final String ACTION_SEND_FILE = "com.example.android.wifidirect.SEND_FILE";
    public static final String EXTRAS_FILE_PATH = "file_url";
    public static final String EXTRAS_GROUP_OWNER_ADDRESS = "go_host";
    public static final String EXTRAS_GROUP_OWNER_PORT = "go_port";
    private WiFiDirectActivity wiFiDirectActivity;
    private DeviceDetailFragment deviceDetailFragment;
    public FileTransferService(String name) {
        super(name);
    }

    public FileTransferService() {
        super("FileTransferService");
    }
    
    /*
     * (non-Javadoc)
     * @see android.app.IntentService#onHandleIntent(android.content.Intent)
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        Context context = getApplicationContext();
        if (intent.getAction().equals(ACTION_SEND_FILE)) {
            //String fileUri = intent.getExtras().getString(EXTRAS_FILE_PATH);
            String host = intent.getExtras().getString(EXTRAS_GROUP_OWNER_ADDRESS);
            Socket socket = new Socket();
            int port = intent.getExtras().getInt(EXTRAS_GROUP_OWNER_PORT);

            try {
                Log.d(WiFiDirectActivity.TAG, "Opening client socket - ");
                socket.bind(null);
                socket.connect((new InetSocketAddress(host, port)), SOCKET_TIMEOUT);
                // socket address is owner IP 
                Log.d(WiFiDirectActivity.TAG, "Client socket - " + socket.isConnected() + " Client IP : " + socket.getInetAddress());
                InputStream stream = socket.getInputStream();
                byte arr[] = new byte[100];
                stream.read(arr); 
                
                String ip = new String(arr);
                String ipAddr = ip.substring(0, ip.lastIndexOf("@"));
                
                Log.d(WiFiDirectActivity.TAG, "InputStream : " + ipAddr);
                
                DeviceDetailFragment.Addr = ipAddr;

                Log.d(WiFiDirectActivity.TAG, "Client: Data written");
                
                stream.close();
                socket.close();
            } catch (IOException e) {
                Log.e(WiFiDirectActivity.TAG, e.getMessage());
            } 

        }
    }
}
