package com.example.popstar;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.popstar.db.scoreSQLiteOpenHelper;

public class ScoreActivity extends Activity {
	
	private scoreSQLiteOpenHelper helper;
	private TextView srank;
	private TextView sscore;
	
	private int new_score=0 ;//新分数
	private int new_rank=0 ;//新分数
	
	private Cursor cursor;
	private ListView scoreList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score);
		
		scoreList = (ListView)findViewById(R.id.listView);
		srank =(TextView)findViewById(R.id.srank);
		sscore =(TextView)findViewById(R.id.sscore);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		//积分的数据库类
		helper = new scoreSQLiteOpenHelper(ScoreActivity.this,null,null,0);
		
		if(bundle!=null)
		{
			String str;
			String strs;
			
			if(((str = bundle.getString("RANK")) != null)&&
					((strs = bundle.getString("SCORE")) != null)){
				new_rank = Integer.parseInt(str);
				new_score = Integer.parseInt(strs);
			
			}
			
			
			int flag;
			flag=	helper.updateScore(str, new_score);
			//Log.i("flag",String.valueOf(flag));
			
		}
		
		
		
//		cursor.requery();
		scoreList.invalidateViews();
		cursor = helper.selectAllScore();
		UserAdapter adapter = new UserAdapter(ScoreActivity.this,cursor);
		scoreList.setAdapter(adapter);
		ImageButton button = (ImageButton)findViewById(R.id.back);
        button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ScoreActivity.this, BeginPage.class);
				startActivity(intent);
				finish();
				
			}

			
		});
		
		
		
	}//creat
	
	
}

class UserAdapter extends BaseAdapter{
	Context context ;
	Cursor cursor ;
	
	public UserAdapter(Context context,Cursor cursor) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.cursor = cursor;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cursor.getCount();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub		
		LinearLayout linear = new LinearLayout(context);
		TextView text1 = new TextView(context);
		TextView text3 = new TextView(context);
		TextView text2 = new TextView(context);
		
		cursor.moveToPosition(position);
		
		text1.setText("\t\t\t\t\t\t\t\t\t\t\t\t"+cursor.getString(1));
		text3.setText("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
		text2.setText(cursor.getString(2));
		text1.setTextColor(android.graphics.Color.WHITE);
		text2.setTextColor(android.graphics.Color.WHITE);
		text1.setTextSize(20);
		text2.setTextSize(20);
		
		linear.setOrientation(LinearLayout.HORIZONTAL);
		linear.addView(text1);
		linear.addView(text3);
		linear.addView(text2);
		return linear;
	}
}


