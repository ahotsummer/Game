package com.example.popstar.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class scoreSQLiteOpenHelper extends SQLiteOpenHelper {
	
	private static final String ID="id";			//���
	private static final String RANK="rank";		//�ؿ�
	private static final String SCORE="score";		//����
	private static final int DATABASE_VERSION = 1;	//���ݿ�汾
    private static final String DICTIONARY_TABLE_NAME = "scorerank";	//����
    public  static int  rank;
   

	//����������
    private static final String DICTIONARY_TABLE_CREATE =
                "CREATE TABLE " + DICTIONARY_TABLE_NAME + " (" +
                		ID + " integer PRIMARY KEY AUTOINCREMENT, " +
                RANK + " TEXT,"+
                SCORE +" integer );";
    
    
    private static final String DATEBASE_NAME="rank";

    //���캯��
	public scoreSQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, DATEBASE_NAME, factory, DATABASE_VERSION);
		 getRank();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//������
		db.execSQL(DICTIONARY_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	
	//�������û�
	public int addNewRank(String rank, int score){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(DICTIONARY_TABLE_NAME, new String[] { ID, SCORE },  "rank ='" + rank+"'", null, null, null, null);
		if(cursor.getCount() == 0){	//�û�������
			db = this.getWritableDatabase();//ִ����һ�仰��ʱ�����ݿ��ļ��Żᱻ����
			ContentValues values = new ContentValues();
	        values.put(RANK, rank);
	        values.put(SCORE, score);
	        db.insert(DICTIONARY_TABLE_NAME, null, values);
	        getRank();
	        return 0;
		}else{
			return -1;	// ���и��û�
		}
	}
	
	
	//�޸ķ���
	public int updateScore(String rank, int score){
		//��ȡ sqlitedatabase
		//�����Ƿ��иùؿ�
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(DICTIONARY_TABLE_NAME, new String[] { ID, SCORE },  "rank ='" + rank+"'", null, null, null, null);
		if(cursor.getCount() > 0)//�ؿ�����
		{	
			cursor.moveToFirst();
			String str = cursor.getString(1);
			int old = Integer.parseInt(str);
			if(old < score){
				//����SCORE
				db = this.getWritableDatabase();//ִ����һ�仰��ʱ�����ݿ��ļ��Żᱻ����	
				db.execSQL("update "+DICTIONARY_TABLE_NAME+" set "+ SCORE+"="+score+" where " + RANK+"='"+rank+"'");
			
				return 0;
			}else{
				return -2;	//���ַ�������߷ֵ�
			}		
		}else 
		{
			if(cursor.getCount() == 0){	//�û�������
				db = this.getWritableDatabase();//ִ����һ�仰��ʱ�����ݿ��ļ��Żᱻ����
				ContentValues values = new ContentValues();
				values.put(RANK, rank);
				values.put(SCORE, score);
				db.insert(DICTIONARY_TABLE_NAME, null, values);
				getRank();
				return 1;
			}else
			{
				return -1;	// ���и��û�
			}
		}
	}
	
	//�޸ķ���
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
		
		
	
	
	//��ѯ����
	public Cursor selectAllScore(){
		//��ȡ sqlitedatabase
		SQLiteDatabase db = this.getReadableDatabase();//ִ����һ�仰��ʱ�����ݿ��ļ��Żᱻ����
		Cursor cursor= db.query(DICTIONARY_TABLE_NAME, null, null, null, null, null, null);
		return cursor;
	}

}
