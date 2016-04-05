/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.androidsmb;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.alfresco.jlan.app.XMLServerConfiguration;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.bongplayer2.R;

interface MessageListener {
        
        public void message(String msg);
        
        public void error(String msg);
        
        public void error(String msg, Throwable e);
        
}

/**
 * This class does all the work for setting up and managing Bluetooth
 * connections with other devices. It has a thread that listens for
 * incoming connections, a thread for connecting with a device, and a
 * thread for performing data transmissions when connected.
 */
public class AndroidSMBService extends Service implements AndroidSMBConstants {
        
        final private String TAG="AndroidSMBService";

        private NotificationManager mNM;
        
        private int status = STOPPED;

        protected CifsServer server;
        
        private DebugHandler logHandler;
        
    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        AndroidSMBService getService() {
            return AndroidSMBService.this;
        }
    }
    
    /* (non-Javadoc)
     * @see android.app.Service#onCreate()
     * This will be called automatically when the Activity tries to bind to this service if it isn't already created.
     */
    @Override
    public void onCreate() {
        Toast.makeText(this, "Android SMB Service Created", Toast.LENGTH_LONG).show();
        // Display a notification about us starting.  We put an icon in the status bar.
        this.logHandler = DebugHandler.getInstance();
    }

    public int getStatus(){
        return status;
    }
    
    private int setStatus(int status){
        int temp = this.getStatus();
        this.status = status;
        return temp;
    }
    
    public DebugHandler getLogHandler(){
        return this.logHandler;
    }
    
    /* (non-Javadoc)
     * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
     * 
     * Even though this 'Service' is 'created' automatically, this won't get called until the activity explicitly calls 'startActivity'
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Received start id " + startId + ": " + intent);
        Toast.makeText(this, R.string.smb_started, Toast.LENGTH_LONG).show();
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        showNotification();
        new Thread(new Runnable() {
                
                        public void run() {
                                try {
                                        InputStream stream = AndroidSMBService.this.getResources().openRawResource(R.raw.config);
                                        Log.d("SMB", "Here - 1");
                                        XMLServerConfiguration srvConfig = new XMLServerConfiguration();
                                        Log.d("SMB", "Here - 2");
                                        srvConfig.loadConfiguration(new InputStreamReader(stream));
                                        Log.d("SMB", "Here - 3");
                                        server = new CifsServer(srvConfig);
                                AndroidSMBService.this.setStatus(RUNNING);
                                AndroidSMBService.this.getLogHandler().publish(new LogRecord(Level.INFO,"SMB Started"));
                                server.run();
                                } catch (Exception e) {
                                        server.stop();
                                        AndroidSMBService.this.setStatus(STOPPED);
                                Toast.makeText(AndroidSMBService.this, "Error with server", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                                        Log.e(TAG, "error loading server", e);
                                }
                        }
                }).start();
        return START_STICKY;
    }

        @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Process destroyed", Toast.LENGTH_LONG).show();
    }
    
    public void shutDown() {
        server.stop();
        //AndroidSMBService.this.getLogHandler().publish(new LogRecord(Level.INFO,"SMB Stopped"));
        mNM.cancel(R.string.smb_service_label);
        this.setStatus(STOPPED);
        // Tell the user we stopped.
        Toast.makeText(this, R.string.smb_stopped, Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
        public boolean onUnbind(Intent intent) {
                // TODO Auto-generated method stub
        this.logHandler = null;
                return super.onUnbind(intent);
        }

        // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();

    
        
    /**
     * Show a notification while this service is running.
     */
    private void showNotification() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = getText(R.string.app_name);

        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.ic_launcher, text,
                System.currentTimeMillis());
        notification.flags |= Notification.FLAG_NO_CLEAR|Notification.FLAG_ONGOING_EVENT;

        // The PendingIntent to launch our activity if the user selects this notification
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//                new Intent(this, AndroidSMB.class), 0);

        // Set the info for the views that show in the notification panel.
//        notification.setLatestEventInfo(this, getText(R.string.smb_service_label),
//                       text, contentIntent);

        // Send the notification.
        // We use a layout id because it is a unique number.  We use it later to cancel.
        //mNM.notify(R.string.smb_service_label, notification);
    }

}