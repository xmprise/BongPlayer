package com.google.code.androidsmb;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bongplayer2.R;
public class MyHomeActivity extends ListActivity implements OnItemClickListener {
  
 private TextView text1;
 private String hostname, host, ipaddrstr, valueS, valueS2;
 private InetAddress ipaddr2;
 private DBAdapter database;
 private ListView lv;
 private Cursor cursor;
 private String str;
 private Editable value, value2;
  
 //adds a menu item from the res/menu/menu.xml
 @Override
 public boolean onCreateOptionsMenu(Menu menu) {
 MenuInflater inflater = getMenuInflater();
 inflater.inflate(R.menu.menu, menu);
 return true;
 }
  
 //adds an action to the button click
 @Override
 public boolean onOptionsItemSelected(MenuItem item) {
 switch (item.getItemId()) {
 
 case R.id.item1:
 finish();
 break;
 
 case R.id.item2:
  AlertDialog.Builder alert = new AlertDialog.Builder(this);
     alert.setTitle("Add IP...");
     alert.setMessage("I want to add this IP to list");
        //Setup text area for user input
     final EditText input = new EditText(this);
     alert.setView(input);
        //Setup what happens when the user clicks either buttons
     alert.setPositiveButton("Add this IP Address", new DialogInterface.OnClickListener() {
    
   @Override
   public void onClick(DialogInterface dialog, int whichbutton) {
    // TODO Auto-generated method stub
    value = input.getText();
    valueS = value.toString();
    database.insertTitle("add", valueS, "added");
    cursor.requery();
   }
  });
  alert.show();
  break;
  
 case R.id.item3:
	 Intent intent = new Intent(MyHomeActivity.this, WiFiDirectActivity.class);
	 //intent.putExtra("IPaddress" , str);
	 //intent.putExtra("username" , valueS);
	 //intent.putExtra("password" , valueS2);
	 startActivity(intent);
	   
 }
 return true;
 }
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        database = new DBAdapter(getApplicationContext());
        database.open();
        database.deleteAll();
        lv = getListView();
       // text1 = (TextView)findViewById(R.id.infoTV);
        
        SocketThread socket = new SocketThread();
        socket.execute();
       //Log.i("My Home", ipaddrstr);
       //checkHosts("192.168.1");
       cursor = database.getAllTitles();
       //cursor.moveToFirst();
       //lv = (ListView)findViewById(R.id.listView1);
        
       ListAdapter adapter=new SimpleCursorAdapter(
         this,
         android.R.layout.simple_list_item_1,
         cursor,
         new String[] {cursor.getColumnName(cursor.getColumnIndex("title"))},
         new int[] {android.R.id.text1});
       setListAdapter(adapter);
        
       lv.setOnItemClickListener(this);
       lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
       lv.setTextFilterEnabled(true);
       lv.isFocusable();
    }
 private void checkHosts(String subnet) {
  // TODO Auto-generated method stub
  int timeout = 100;
  for (int i = 1; i < 254; i++) {
   String host = subnet + "." + i;
   try {
    if (InetAddress.getByName(host).isReachable(timeout)) {
     System.out.println(host + " is reachable");
     //text1.append("\n" + host + " is reachable");
     database.insertTitle(host, host, subnet);
    }
   } catch (UnknownHostException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }
   System.out.println("Count is on IP : " + host);
  }
 }
 @Override
 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
  // TODO Auto-generated method stub
  id = lv.getItemIdAtPosition(position);
   
  str = cursor.getString(cursor.getColumnIndexOrThrow("title"));
   
  //------------the "str" is the value of the selected item from the database, in this case IP Address----
  //------------the id is the position in the listview that we selected from------------------------------
  System.out.println("Your clicked on item : " + id + " " + str);
  Toast toast = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT);
  toast.show();
   
  View bodyView=getLayoutInflater().inflate(R.layout.alert, (ViewGroup)findViewById(R.id.toastView));
  final EditText input = (EditText) bodyView.findViewById(R.id.editText1);
  final EditText input2 = (EditText) bodyView.findViewById(R.id.editText2);
  Button button1 = (Button)bodyView.findViewById(R.id.button1);
   
  AlertDialog.Builder alert = new AlertDialog.Builder(this);
     alert.setTitle("Credentials...");
     alert.setMessage("Login Information");
        //Setup text area for user input
    // final EditText input = new EditText(this);
    // final EditText input2 = new EditText(this);
     alert.setView(bodyView);
     //alert.setView(input2);
        //Setup what happens when the user clicks either buttons
     button1.setOnClickListener(new View.OnClickListener() {
    
   @Override
   public void onClick(View arg0) {
    // TODO Auto-generated method stub
    value = input.getText();
    value2 = input2.getText();
    valueS = value.toString();
    valueS2 = value2.toString();
    Intent intent = new Intent(MyHomeActivity.this, ListFiles.class);
    intent.putExtra("IPaddress" , str);
    intent.putExtra("username" , valueS);
    intent.putExtra("password" , valueS2);
    startActivity(intent);
   }
  });
  alert.show();
   
 }
  
 	public class SocketThread extends AsyncTask<Void, Byte[], Boolean> {
 		
 		boolean result = false;
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
	        try {
	        	   InetAddress ipaddr = InetAddress.getLocalHost();
	        	   byte[] addr = ipaddr.getAddress();
	        	   hostname = addr.toString();
	        	   host = ipaddr.getHostName();
	        	  } catch (UnknownHostException e) {
	        	   // TODO Auto-generated catch block
	        	   e.printStackTrace();
	        	  }
	        	         
	        	       //text1.setText(host);
	        	        
	        try {
	        	  ipaddr2 = InetAddress.getByName(host);
	        	  byte[] addr2 = ipaddr2.getAddress();
	        	  ipaddrstr = "";
	        	  for (int i = 0; i < addr2.length; i++){
	        	   if (i > 0){
	        	    ipaddrstr += ".";
	        	   }
	        	   ipaddrstr += addr2[i];
	        	  }
	        	 } catch (UnknownHostException e) {
	        	  // TODO Auto-generated catch block
	        	  e.printStackTrace();
	        	 }
			
			return result;
		}
 		
 	}
}