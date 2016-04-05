
package com.google.code.androidsmb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bongplayer2.R;
import com.google.code.androidsmb.DeviceListFragment.DeviceActionListener;


public class DeviceDetailFragment extends Fragment implements ConnectionInfoListener {

    protected static final int CHOOSE_FILE_RESULT_CODE = 20;
    private View mContentView = null;
    private WifiP2pDevice device;
    private WifiP2pInfo info;
    ProgressDialog progressDialog = null;
    public static boolean clientSide = false;
    
    public static boolean mIsRunning = false;
    private AndroidSMBService mService;
    public static String Addr;

    public static final String DEVICE_NAME = "device_name";
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_TOAST = 5;

    public static final int MESSAGE_WRITE = 3;
    
    boolean groupOwner = true;
    
    //new FileServerAsyncTask(getActivity(), mContentView.findViewById(R.id.status_text)).execute();
    FileServerAsyncTask fileServerAsyncTask;
    ServerThread serverThread;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
    }
    
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            
            mService = ((AndroidSMBService.LocalBinder)service).getService();

        }

        public void onServiceDisconnected(ComponentName className) {
            
                mService = null;
           
        }
    };
    
    private byte[] getLocalIPAddress() {
        try { 
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) { 
                NetworkInterface intf = en.nextElement(); 
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) { 
                    InetAddress inetAddress = enumIpAddr.nextElement(); 
                    if (!inetAddress.isLoopbackAddress()) { 
                        if (inetAddress instanceof Inet4Address) { // fix for Galaxy Nexus. IPv4 is easy to use :-) 
                            return inetAddress.getAddress(); 
                        } 
                        //return inetAddress.getHostAddress().toString(); // Galaxy Nexus returns IPv6 
                    } 
                } 
            } 
        } catch (SocketException ex) { 
            //Log.e("AndroidNetworkAddressFactory", "getLocalIPAddress()", ex); 
        } catch (NullPointerException ex) { 
            //Log.e("AndroidNetworkAddressFactory", "getLocalIPAddress()", ex); 
        } 
        return null; 
    }

    private String getDottedDecimalIP(byte[] ipAddr) {
        //convert to dotted decimal notation:
        String ipAddrStr = "";
        for (int i=0; i<ipAddr.length; i++) {
            if (i > 0) {
                ipAddrStr += ".";
            }
            ipAddrStr += ipAddr[i]&0xFF;
        }
        return ipAddrStr;
    }

    //ip = getDottedDecimalIP(getLocalIPAddress());
    
    public static String getIPFromMac(String MAC) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;
            while ((line = br.readLine()) != null) {

                String[] splitted = line.split(" +");
                if (splitted != null && splitted.length >= 4) {
                    // Basic sanity check
                    String device = splitted[5];
                    if (device.matches(".*p2p-p2p0.*")){
                        String mac = splitted[3];
                        if (mac.matches(MAC)) {
                            return splitted[0];
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContentView = inflater.inflate(R.layout.device_detail, null);
        mContentView.findViewById(R.id.btn_connect).setOnClickListener(new View.OnClickListener() {
        	
            @Override
            public void onClick(View v) {
                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = device.deviceAddress;
                config.wps.setup = WpsInfo.PBC;
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                progressDialog = ProgressDialog.show(getActivity(), "Press back to cancel",
                        "Connecting to :" + device.deviceAddress, true, true );
                ((DeviceActionListener) getActivity()).connect(config);
                //List Item Click, SMB Client
                clientSide = true;
            }
        });

        mContentView.findViewById(R.id.btn_disconnect).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        ((DeviceActionListener) getActivity()).disconnect();
                        
                        //if SMB Server open 
                        if(mIsRunning == true) {
                        	// Detach our existing connection.
                            getActivity().unbindService(mConnection); // Not Registed service
                        	
                        	mService.shutDown();
                            getActivity().stopService(new Intent(getActivity(), AndroidSMBService.class));
                            mIsRunning=false;
                        }
                        clientSide = false; // init
                        
                    }
                });

        mContentView.findViewById(R.id.btn_start_client).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                    	if(DeviceDetailFragment.mIsRunning == false && DeviceDetailFragment.clientSide == true){
                        	//SMB Explore
                        	Log.d(WiFiDirectActivity.TAG, "SMB Client Start");
                        	Intent intents = new Intent();
                        	intents.setClass(getActivity(), ListFiles.class);
                            if(groupOwner == false){
                            	intents.putExtra("IPaddress", "192.168.49.1:7771");
                            }else{
                            	intents.putExtra("IPaddress" , Addr + ":7771");
                            }
                        	
                            intents.putExtra("username" , "jlansrv");
                            intents.putExtra("password" , "jlan");
                           
                            startActivity(intents);
                        	
                        	
                        }
                    }
                });

        return mContentView;
    }

    @Override
    public void onConnectionInfoAvailable(final WifiP2pInfo info) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        this.info = info;
        this.getView().setVisibility(View.VISIBLE);
        //fileServerAsyncTask = new FileServerAsyncTask(getActivity(), mContentView.findViewById(R.id.status_text));
        serverThread = new ServerThread();
        String ip = getDottedDecimalIP(getLocalIPAddress());
        //String ip = getIPFromMac(device.deviceAddress);
        // The owner IP is now known.
        TextView view = (TextView) mContentView.findViewById(R.id.group_owner);
        view.setText(getResources().getString(R.string.group_owner_text)
                + ((info.isGroupOwner == true) ? getResources().getString(R.string.yes)
                        : getResources().getString(R.string.no)));
        //TEST_CODE
        
        ///////////
        // InetAddress from WifiP2pInfo struct.
        view = (TextView) mContentView.findViewById(R.id.device_info);
        view.setText("Group Owner IP - " + info.groupOwnerAddress.getHostAddress() + "My IP Address : " + ip);
        
        // peer group access in the point then task SMB protocol
        if(info.groupFormed && info.isGroupOwner && mIsRunning == false &&clientSide == false) {
//        	new FileServerAsyncTask(getActivity(), mContentView.findViewById(R.id.status_text))
//            .execute();
        	//fileServerAsyncTask.execute();
        	serverThread.start();
        	//SMB Protocol
        	getActivity().bindService(new Intent(getActivity(), 
                    AndroidSMBService.class), mConnection, Context.BIND_AUTO_CREATE);
        	
        	getActivity().startService(new Intent(getActivity(), AndroidSMBService.class));
            //getActivity().startService(new Intent(getActivity(),myPlayService.class));
        	mIsRunning=true;
        	groupOwner = true;
        	
        }
        // Client Side
        else if (info.groupFormed && info.isGroupOwner) {
            mContentView.findViewById(R.id.btn_start_client).setVisibility(View.VISIBLE);
            ((TextView) mContentView.findViewById(R.id.status_text)).setText(getResources()
                    .getString(R.string.client_text));
            groupOwner = true;
//        	new FileServerAsyncTask(getActivity(), mContentView.findViewById(R.id.status_text))
//                    .execute();
        	//fileServerAsyncTask.execute();
        	serverThread.start();
        } else if (info.groupFormed && mIsRunning == false &&clientSide == false) {
            // The other device acts as the client. In this case, we enable the
            // get file button.
            
            Intent serviceIntent = new Intent(getActivity(), FileTransferService.class);
            serviceIntent.setAction(FileTransferService.ACTION_SEND_FILE);
            //serviceIntent.putExtra(FileTransferService.EXTRAS_FILE_PATH, uri.toString());
            serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_ADDRESS,
                    info.groupOwnerAddress.getHostAddress());
            serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_PORT, 8988);
            getActivity().startService(serviceIntent);
            
            //SMB Protocol
            getActivity().bindService(new Intent(getActivity(), 
                    AndroidSMBService.class), mConnection, Context.BIND_AUTO_CREATE);
            getActivity().startService(new Intent(getActivity(), AndroidSMBService.class));
            mIsRunning=true;
            groupOwner = false;
           
        } 
        // Client Side
        else if(info.groupFormed) {
        	// The other device acts as the client. In this case, we enable the
            // get file button.
            mContentView.findViewById(R.id.btn_start_client).setVisibility(View.VISIBLE);
            ((TextView) mContentView.findViewById(R.id.status_text)).setText(getResources()
                    .getString(R.string.client_text));
            
            // GroupOwner None;
            groupOwner = false;
            
            Intent serviceIntent = new Intent(getActivity(), FileTransferService.class);
            serviceIntent.setAction(FileTransferService.ACTION_SEND_FILE);
            //serviceIntent.putExtra(FileTransferService.EXTRAS_FILE_PATH, uri.toString());
            serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_ADDRESS,
                    info.groupOwnerAddress.getHostAddress());
            serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_PORT, 8988);
            getActivity().startService(serviceIntent);
            
        }

        // hide the connect button
        mContentView.findViewById(R.id.btn_connect).setVisibility(View.GONE);
        
    }
    
    /**
     * Updates the UI with device data
     * 
     * @param device the device to be displayed
     */
    public void showDetails(WifiP2pDevice device) {
        this.device = device;
        this.getView().setVisibility(View.VISIBLE);
        TextView view = (TextView) mContentView.findViewById(R.id.device_address);
        view.setText(device.deviceAddress);
        view = (TextView) mContentView.findViewById(R.id.device_info);
        view.setText(device.toString());
        
    }

    /**
     * Clears the UI fields after a disconnect or direct mode disable operation.
     */
    @SuppressWarnings("deprecation")
	public void resetViews() {
        mContentView.findViewById(R.id.btn_connect).setVisibility(View.VISIBLE);
        clientSide = false;
        info = new WifiP2pInfo();
        //fileServerAsyncTask = new FileServerAsyncTask(getActivity(), mContentView.findViewById(R.id.status_text));
        serverThread = new ServerThread();
        TextView view = (TextView) mContentView.findViewById(R.id.device_address);
        view.setText(R.string.empty);
        view = (TextView) mContentView.findViewById(R.id.device_info);
        view.setText(R.string.empty);
        view = (TextView) mContentView.findViewById(R.id.group_owner);
        view.setText(R.string.empty);
        view = (TextView) mContentView.findViewById(R.id.status_text);
        view.setText(R.string.empty);
        mContentView.findViewById(R.id.btn_start_client).setVisibility(View.GONE);
        this.getView().setVisibility(View.GONE);
       
        if(info.groupFormed == false && mIsRunning == true){
        	// Detach our existing connection.
            getActivity().unbindService(mConnection); // Not Registed service
        	
        	mService.shutDown();
            getActivity().stopService(new Intent(getActivity(), AndroidSMBService.class));
            
            mIsRunning=false;
        }
        
        getActivity().stopService(new Intent(getActivity(), FileTransferService.class));
        
        //fileServerAsyncTask.cancel(true);
        
    }
    
    @Override
    public void onDetach() {
    	super.onDetach();
    	
    }
    
    public class ServerThread extends Thread {
    	Socket socket;
        public void run() {      
            try {
            	Log.d(WiFiDirectActivity.TAG, "doInBackground..." );
            	
            	ServerSocket serverSocket = new ServerSocket(8988);
                Log.d(WiFiDirectActivity.TAG, "Server: Socket opened" + "IP Address : " + serverSocket.getInetAddress().getHostAddress());
                
                Socket client = serverSocket.accept();
                Log.d(WiFiDirectActivity.TAG, "Server: connection done" + client.getInetAddress().getHostAddress());
                
                String clientIP = client.getInetAddress().getHostAddress() + "@";
                OutputStream stream = client.getOutputStream();
                stream.write(clientIP.getBytes());
                
                String addr = client.getInetAddress().getHostAddress();
                DeviceDetailFragment.Addr = addr;
                
                stream.close();
                serverSocket.close();
                
            } catch (IOException e) {
                Log.e(WiFiDirectActivity.TAG, e.getMessage());
                
            }
    }
   
    }
    
    /**
     * A simple server socket that accepts connection and writes some data on
     * the stream.
     */
    public static class FileServerAsyncTask extends AsyncTask<Void, Void, String> {

        private Context context;
        private TextView statusText;

        /**
         * @param context
         * @param statusText
         */
        public FileServerAsyncTask(Context context, View statusText) {
            this.context = context;
            this.statusText = (TextView) statusText;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
            	Log.d(WiFiDirectActivity.TAG, "doInBackground..." );
            	ServerSocket serverSocket = new ServerSocket(8988);
                Log.d(WiFiDirectActivity.TAG, "Server: Socket opened" + "IP Address : " + serverSocket.getInetAddress().getHostAddress());
                Socket client = serverSocket.accept();
                Log.d(WiFiDirectActivity.TAG, "Server: connection done" + client.getInetAddress().getHostAddress());
                String clientIP = client.getInetAddress().getHostAddress() + "@";
                OutputStream stream = client.getOutputStream();
                stream.write(clientIP.getBytes());
                String addr = client.getInetAddress().getHostAddress();
                
                stream.close();
                serverSocket.close();
                return addr;
            } catch (IOException e) {
                Log.e(WiFiDirectActivity.TAG, e.getMessage());
                return null;
            }
        }

       
        @Override
        protected void onPostExecute(String addr) {

            DeviceDetailFragment.Addr = addr;
        }

       
        @Override
        protected void onPreExecute() {
            statusText.setText("Opening a server socket");
        }
        
        @Override
        protected void onCancelled() 
        { 
         Log.i("DeviceDetailFragment", "AsyncTask Cancel");
         
         super.onCancelled(); 
        }
    }


}
