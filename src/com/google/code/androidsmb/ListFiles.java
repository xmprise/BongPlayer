package com.google.code.androidsmb;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bongplayer2.R;
import com.example.bongplayer2.VideoActivity;
import com.test.smbstreamer.variant1.Streamer;
public class ListFiles extends Activity {

 private String ipaddress, user, pass, url;
 private String[] files;
 private String[] shares;
 private TextView text1;
 private boolean intit;
 private String url2;
 private String post, back;
 //adds a menu item from the res/menu/menu.xml
 @Override
 public boolean onCreateOptionsMenu(Menu menu) {
 MenuInflater inflater = getMenuInflater();
 inflater.inflate(R.menu.menu2, menu);
 return true;
 }
  
 //adds an action to the button click
 @Override
 public boolean onOptionsItemSelected(MenuItem item) {
 switch (item.getItemId()) {
 case R.id.item1:
  finish();
  break;
 }
 return true;
 }
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        intit = false; 
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
         ipaddress = extras.getString("IPaddress");
         user = extras.getString("username");
         pass = extras.getString("password");
        }
         
        text1 = (TextView)findViewById(R.id.infoTV);
        
         
        Log.i("MyHome", user + " " + pass + " " + ipaddress);
             
                  
        ServerThread srvThread = new ServerThread(this);
        srvThread.execute("init");

    }
    
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getRepeatCount() == 0) {

                // Tell the framework to start tracking this event.
                Log.i("Back ", "Press Back Button url2: " + url2);
                Log.i("Back ", "Press Back Button url: " + url);
                // Check URL
                ServerThread srvThread = new ServerThread(this);
                try{
                	if(url2.equals(url)){ // if url reference null point, occur null point exception 
                		srvThread.cancel(true);
                		finish();
                    }else{
                    	 srvThread.execute("back");
                         return true;
                    }
                }catch(Exception e){
                	Log.d("ListFiles.Activity", "url2 is NULL");
                	finish(); 
                }
                
               

            } else if (event.getAction() == KeyEvent.ACTION_UP) {
                
            	if (event.isTracking() && !event.isCanceled()) {
            		
                    // DO BACK ACTION HERE
            		Log.i("Back ", "2");
                    return true;

                }
            }
            return super.dispatchKeyEvent(event);
        } else {
            return super.dispatchKeyEvent(event);
        }
    }
    
    @Override
    protected void onStop() {
    
    	super.onStop();
    	ServerThread srvThread = new ServerThread(this);
    	Log.d("ListFiles", "onStop");
    	srvThread.cancel(true);
    }
       
    public class ServerThread extends AsyncTask<String, Integer, Integer> {
    	
    	ListView lv = (ListView)findViewById(R.id.listView1);
    	ArrayList<String> mListContents = new ArrayList<String>();
    	int curListID = 0;
		ArrayList<String> mListStrings = new ArrayList<String>();
		ArrayList<String> reFiles;
		
		private String[] extension = {".WMV",".SWF",".AVI",".MKV",".MP4"};
		private Context context;
		public ServerThread(Context context)
		{
			this.context = context;
		}
		@Override
		
		protected Integer doInBackground(String... args) {
			// TODO Auto-generated method stub
			//---------START SMB WORKS-------------------------
	        
			url = "smb://" + ipaddress + "/";
			Log.i("url AsyncTask Start", url);
			NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, user, pass);
			SmbFile dir = null;
			int value = 0;
			if(args[0].equals("init")){
				try {
				 		dir = new SmbFile(url, auth);
				} catch (MalformedURLException e) {
				 		// TODO Auto-generated catch block
				 		e.printStackTrace();
				 		Log.i("MyHome", "Authenication problem");
				}
				try {
					 for (SmbFile f : dir.listFiles()) {
						 System.out.println(f.getName());
						 shares = dir.list();
					 }
					 value = 1;
				} catch (SmbException e) {
					 // TODO Auto-generated catch block
					 e.printStackTrace();
					 Log.i("MyHome", "Directory listing problem");
				}
				//-------END SMB WORKS---------------------
				   
				
			}
			else if(args[0].equals("back")) {
				value = 2;
			}
			return value;
			
		}
    	
		protected void onPostExecute(Integer result) {
			
			Log.i("TAG", "HEY");
			String add = "smb://" + ipaddress + "/";
			
			if(result == 1 )
			{
				SmbFile server = null;
				try {
					 server = new SmbFile("smb://" + ipaddress + "/");
					 
				} catch (MalformedURLException e) {
					 // TODO Auto-generated catch block
					 e.printStackTrace();
				}
				try {
					 //files = server.list();
					files = AuthListFiles(add);
				} catch (SmbException e) {
					 // TODO Auto-generated catch block
					 e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
							 
				lv.setAdapter(new ArrayAdapter<String>(ListFiles.this, android.R.layout.simple_list_item_1 , files ));
			}
			else if(result == 2){
				Log.i("ListView Item Click", "Back Button Press");
				try {
					//back = ((String) text1.getText()).substring(0, ((String) text1.getText()).lastIndexOf("/"));
					String temp = url2.substring(0, url2.lastIndexOf("/"));
					back = temp.substring(0, temp.lastIndexOf("/"));
					Log.i("ListView Item Click", "Back : " + back);
					reFiles = getList(back + "/");
					url2 = back + "/";
					text1.setText(url2);
					lv.setAdapter(new ArrayAdapter<String>(ListFiles.this, android.R.layout.simple_list_item_1, reFiles ));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			lv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					
					// TODO Auto-generated method stub
					//Log.i("FileList", "Event Handler" + " : " + url + " : " + files[arg2]); //files dosen't accepted new file list.
					//String share = mListContents[arg2];
					String lastItem;
					Streamer s;
					if(intit == false){
						url2 = url + files[arg2];
					}
					
					try {
						if(intit == false){
							reFiles = getList(url2);
							intit = true;
							text1.setText(url2);
						}else{
							post = reFiles.get(arg2);
							lastItem = post.substring(post.length()-1, post.length());
							//Selected Item Check that Directory or File
							Log.i("Check List" , url2 + " : " + post + " : " + lastItem);
							if(lastItem.equals("/")) {
								url2 = url2 + post;
								text1.setText(url2);
								reFiles = getList(url2);
							}else
							{
								Toast.makeText(ListFiles.this, "Selected Item : " + url2+ "/" + post, Toast.LENGTH_SHORT).show();
								
								s = Streamer.getInstance();
								SmbFile file = new SmbFile(url2+ "/" + post, new NtlmPasswordAuthentication(null, user, pass));
								s.setStreamSrc(file, null);//the second argument can be a list of subtitle files
								
								Uri uri = Uri.parse(Streamer.URL + Uri.fromFile(new File(Uri.parse(url2+ "/" + post).getPath())).getEncodedPath());
								Intent i = new Intent(context, VideoActivity.class);
								i.setDataAndType(uri, "video/*");
								i.putExtra("smburl", url2+"/"+post);
								Bundle extra = new Bundle();
								extra.putParcelable("uri", uri);
								
								i.putExtras(extra);
								startActivity(i);
							}
							
						}
						
						//Log.i("FileList : onItemClick ", reFiles.get(arg2));
						lv.setAdapter(new ArrayAdapter<String>(ListFiles.this, android.R.layout.simple_list_item_1, reFiles ));
						//Press Back Button and return value 2
					
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			});
		}
		
		//Get File List via SMB Protocol
		public ArrayList<String> getList(String path) throws Exception {
			
			SmbFile file = new SmbFile(path, new NtlmPasswordAuthentication(null, user, pass));
			ArrayList<String> fileList = new ArrayList<String>();
			
			SmbFile[] fArr = file.listFiles();
			
			for(int a=0;a<fArr.length;a++) {
				if(accept(fArr[a]) == true) {
					fileList.add(fArr[a].getName());
				}
			}
			return fileList;
		}
		
		public String[] AuthListFiles( String path ) throws Exception {
	        //NtlmAuthenticator.setDefault( this );

			SmbFile file = new SmbFile(path, new NtlmPasswordAuthentication(null, user, pass));
	        //System.out.println("argv[0] = " + data[0]);
	        //SmbFile[] files = file.listFiles();
			SmbFile[] fArr = file.listFiles();
			String[] fileList = new String[fArr.length];
			for( int i = 0; i < fArr.length; i++ ) {
				if(accept(fArr[i]) == true) {
					fileList[i] = fArr[i].getName();
				}
	        }
//	        System.out.println();
			
	        return fileList;
	    }
		
		//File Filter method that only using video file.
		public boolean accept(SmbFile pathname) throws SmbException {
	    	if(pathname.isDirectory()){
	    		return true;
	    	}
	    	
	    	String name = pathname.getName().toUpperCase();
	    	for(String anExt : extension) {
	    		if(name.endsWith(anExt)) {
	    			return true;
	    		}
	    	}
	    	
			return false;
	    }
		

    }
}