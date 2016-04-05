package com.google.code.androidsmb;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_Open extends SQLiteOpenHelper{
	
	private SQLiteDatabase db;
	
	public DB_Open(Context context) {
		super(context, "db_user", null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		db.execSQL("CREATE TABLE db_user" + "(_user_id integer primary key autoincrement," + 
		"url TEXT NOT NULL, name TEXT NOT NULL, pwd TEXT NOT NULL);");
		
		// Test Query
		db.execSQL("INSERT INTO db_user" + " (url, name, pwd)" 
				+ "VALUES ('210.118.69.125:', 'jung', '2114');");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS db_user");
		
		onCreate(db);
	}
	
	public long insertTitle(String url, String name, String pwd)
    {
		db = this.getWritableDatabase();
		
		ContentValues initialValues = new ContentValues();
        initialValues.put("url", url);
        initialValues.put("name", name);
        initialValues.put("pwd", pwd);
        return db.insert("db_user", null, initialValues);
    }
	
	public boolean deleteTitle(String rowId)
    {
		db = this.getWritableDatabase();
		
        return db.delete("db_user", "url" +
          "=" + "'"+rowId+"'", null) > 0;
    }
}
