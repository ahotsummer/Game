package com.example.popstar.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class scoreSQLiteOpenHelper extends SQLiteOpenHelper {
	
	private static final String ID="id";			//序号
	private static final String RANK="rank";		//关卡
	private static final String SCORE="score";		//分数
	private static final int DATABASE_VERSION = 1;	//数据库版本
    private static final String DICTIONARY_TABLE_NAME = "scorerank";	//表名
    public  static int  rank;
   

	//创建表的语句
    private static final String DICTIONARY_TABLE_CREATE =
                "CREATE TABLE " + DICTIONARY_TABLE_NAME + " (" +
                		ID + " integer PRIMARY KEY AUTOINCREMENT, " +
                RANK + " TEXT,"+
                SCORE +" integer );";
    
    
    private static final String DATEBASE_NAME="rank";

    //构造函数
	public scoreSQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, DATEBASE_NAME, factory, DATABASE_VERSION);
		 getRank();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//创建表
		db.execSQL(DICTIONARY_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	
	//加入新用户
	public int addNewRank(String rank, int score){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(DICTIONARY_TABLE_NAME, new String[] { ID, SCORE },  "rank ='" + rank+"'", null, null, null, null);
		if(cursor.getCount() == 0){	//用户不存在
			db = this.getWritableDatabase();//执行这一句话的时候，数据库文件才会被创建
			ContentValues values = new ContentValues();
	        values.put(RANK, rank);
	        values.put(SCORE, score);
	        db.insert(DICTIONARY_TABLE_NAME, null, values);
	        getRank();
	        return 0;
		}else{
			return -1;	// 已有该用户
		}
	}
	
	
	//修改分数
	public int updateScore(String rank, int score){
		//获取 sqlitedatabase
		//查找是否有该关卡
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(DICTIONARY_TABLE_NAME, new String[] { ID, SCORE },  "rank ='" + rank+"'", null, null, null, null);
		if(cursor.getCount() > 0)//关卡存在
		{	
			cursor.moveToFirst();
			String str = cursor.getString(1);
			int old = Integer.parseInt(str);
			if(old < score){
				//更新SCORE
				db = this.getWritableDatabase();//执行这一句话的时候，数据库文件才会被创建	
				db.execSQL("update "+DICTIONARY_TABLE_NAME+" set "+ SCORE+"="+score+" where " + RANK+"='"+rank+"'");
			
				return 0;
			}else{
				return -2;	//本局分数比最高分低
			}		
		}else 
		{
			if(cursor.getCount() == 0){	//用户不存在
				db = this.getWritableDatabase();//执行这一句话的时候，数据库文件才会被创建
				ContentValues values = new ContentValues();
				values.put(RANK, rank);
				values.put(SCORE, score);
				db.insert(DICTIONARY_TABLE_NAME, null, values);
				getRank();
				return 1;
			}else
			{
				return -1;	// 已有该用户
			}
		}
	}
	
	//修改分数
		public int getRank(){
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.query(DICTIONARY_TABLE_NAME, null, null, null, null, null, "ID DESC");
			if(cursor.moveToNext())
			{
			   int id = cursor.getInt(cursor.getColumnIndex(ID));
			   
			   rank=id;
			} 
			return rank;
		}
		
		
	
	
	//查询方法
	public Cursor selectAllScore(){
		//获取 sqlitedatabase
		SQLiteDatabase db = this.getReadableDatabase();//执行这一句话的时候，数据库文件才会被创建
		Cursor cursor= db.query(DICTIONARY_TABLE_NAME, null, null, null, null, null, null);
		return cursor;
	}

}
