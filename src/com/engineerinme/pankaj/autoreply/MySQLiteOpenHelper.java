package com.engineerinme.pankaj.autoreply;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper{
	
	protected final static String DB_NAME = "automessages";
	public final static String TABLE_NAME = "replies";
	public static final String COL_ID="id";
	public static final String COL_KEYWORDS="keywords";
	public static final String COL_MESSAGE="message";
	public static final String COL_EXP_DATE_TIME="expdatetime";
	public static final String COL_STATUS="status";
	protected static final String CREATE_TABLE_SQL = "CREATE TABLE "+TABLE_NAME+"("+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
			+COL_KEYWORDS+" TEXT, "+COL_MESSAGE+" TEXT, "+COL_EXP_DATE_TIME+" DATETIME, "+COL_STATUS+" INTEGER DEFAULT 0)";
	
	public static void insertIntoDB(SQLiteDatabase db, String keywords, String message, String expDateTime){
		db.execSQL("INSERT INTO "+TABLE_NAME+"("+COL_KEYWORDS+","+COL_MESSAGE+","+COL_EXP_DATE_TIME+") VALUES('"+keywords+"','"+message+"','"+expDateTime+"')");
	}
	
	public static Cursor getAllEntries(SQLiteDatabase db ){
		return db.rawQuery("SELECT "+COL_KEYWORDS+", "+COL_MESSAGE+" FROM "+TABLE_NAME+" WHERE "+COL_EXP_DATE_TIME+" > date('now')", null);
	}
	
	public MySQLiteOpenHelper(Context context) {
		super(context, DB_NAME, null, 1);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}
	
}