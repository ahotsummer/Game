package com.example.popstar;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popstar.db.scoreSQLiteOpenHelper;
//import com.example.popstar.utils.FriworkAnimation;

//主菜单，四个按钮选项
public class BeginPage extends Activity {

	// 用于输出调试信息的TAG
	public static final String TAG = "PopStar";
	
	// 给定存储名，通过名字可找到SharedPreferences对象
	public static final String PREFS_STRING = "Pop_GAME_PROGRESS";//文件名
	
	private Button btn_start;
	private Button btn_rate;
	private Button btn_resume;
	private Button btn_exit;
	private int state[][];//重新加载的游戏画面数组
	private String ss;
	public String  score;
	public String  rank;

	// 背景音乐播放MediaPlayer对象
	private MediaPlayer player;
	private Music music;
	private scoreSQLiteOpenHelper helper;
	//动画
	final float FRAME_TIME = 1.0F / 60; // 假定每秒绘制60帧
	float stateTime = 0F;
	List<Animation> sprites = Collections
			.synchronizedList(new LinkedList<Animation>());

	//烟花动画
	private ImageView animationIV;
	private ImageView animationIV1;
	private ImageView animationIV2;
	private  AnimationDrawable anim;
	private  AnimationDrawable anim1;
	private  AnimationDrawable anim2;
	//灯动画
	private ImageView light_red;
	private ImageView light_yellow;
	private ImageView light_blue;
	private ImageView light_green;
	private ImageView light_pink;
	//等级
	
	private TextView ranktv ;
	private TextView rankstartv ;
	public  int rankMax=1;
	public String srank ;
		

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐去Android顶部状态栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 隐去程序标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置显示GameView界面
		setContentView(R.layout.ac_start);
				
		// 从raw文件夹中获取一个音乐资源文件
		player = MediaPlayer.create(this, R.raw.start);
	    //tv
	//	ranktv =(TextView)findViewById(R.id.rankTV);
	//	rankstartv =(TextView)findViewById(R.id.rankstarTV);
		
	/*	new Thread()
		{
			public void run()
			{		
				helper = new scoreSQLiteOpenHelper(BeginPage.this,null,null,0);
				//	helper.getRank();
					int a =helper.rank;
					Log.i("rank",""+a);
					ranktv.setText(""+a);
					rankstartv.setText(a*2+"颗");
			}
			
		}.start();
	  */
       
          
		
		
	
		
		btn_start = (Button) findViewById(R.id.btn_start);
		btn_rate = (Button) findViewById(R.id.btn_rate);
		btn_resume = (Button) findViewById(R.id.btn_resume);
		btn_exit = (Button) findViewById(R.id.btn_exit);
	
		
		//开始游戏
		btn_start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				
				Intent intent = new Intent();
				intent.setClass(BeginPage.this, MainActivity.class);
				//对象的requestcodeMainActivity100
				startActivityForResult(intent, 100);
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);//淡入淡出效果

			}
		});
		
		//排行榜
		btn_rate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			
				Intent intent = new Intent();
				intent.setClass(BeginPage.this, ScoreActivity.class);
				
				if(rank!= null&&score!=null)  {
					intent.putExtra("RANK", rank);  
					intent.putExtra("SCORE", score);
				}	      
	            startActivity(intent);
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);//淡入淡出效果
			}
		});
		
		//恢复
		btn_resume.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				loadGameProgress();
				if (state != null) {
					Intent intent = new Intent();
					intent.putExtra("matrix", state);//传送额外信息
					intent.setClass(BeginPage.this, MainActivity.class);
					
					startActivityForResult(intent, 101);//保存当前游戏进度
					overridePendingTransition(android.R.anim.fade_in,
							android.R.anim.fade_out);
				}
			}
		});
		
		//退出
		btn_exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		animationIV = (ImageView) findViewById(R.id.animation);
		animationIV1 = (ImageView) findViewById(R.id.animation1);
		animationIV2 = (ImageView) findViewById(R.id.animation2);
    	
    	animationIV1.setBackgroundResource(R.anim.friwork1);
    	animationIV2.setBackgroundResource(R.anim.friwork2);
    	animationIV.setBackgroundResource(R.anim.friwork);
    	  
		anim = (AnimationDrawable) animationIV.getBackground();
		anim1 = (AnimationDrawable) animationIV1.getBackground();
		anim2 = (AnimationDrawable) animationIV2.getBackground();

		anim.start();	
		anim1.start();	
		anim2.start();
		Animation anims = AnimationUtils.loadAnimation(this, R.anim.anim);
		// 设置动画结束后保留结束状态
		anims.setFillAfter(true);
		// 加载第二份动画资源
		final Animation reverse = AnimationUtils.loadAnimation(this
						, R.anim.reverse);
		// 设置动画结束后保留结束状态
		reverse.setFillAfter(true);
		
		light_red= (ImageView)findViewById(R.id.light_red);
		light_yellow= (ImageView)findViewById(R.id.light_yellow);
		light_blue= (ImageView)findViewById(R.id.light_blue);
		light_green= (ImageView)findViewById(R.id.light_green);
		light_pink= (ImageView)findViewById(R.id.light_pink);
		// 加载第一份动画资源
		
		//light_red.startAnimation(anims);	
	//	light_yellow.startAnimation(anims);
	//	light_blue.startAnimation(anims);
	//	light_green.startAnimation(anims);
	//	light_pink.startAnimation(anims);
	//	light_red.startAnimation(reverse);
		light_red.startAnimation(reverse);
		light_yellow.startAnimation(reverse);
		light_blue.startAnimation(reverse);
		light_green.startAnimation(reverse);
		light_pink.startAnimation(reverse);
		
	
		
		
	}
	/**
	 * 加载以前保存的游戏进度
	 */
	private void loadGameProgress() {
		try {
			SharedPreferences settings = getSharedPreferences(PREFS_STRING,
					MODE_PRIVATE);
			String progress = settings.getString("PROGRESS", "abc");//读PROGRESS的数据，“abc”类型
			if (!progress.equals("abc")) {
				state = new int[10][10];
				state = Utils.str2array(progress);
			}

		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	/**
	 * 保存当前游戏进度
	 */
	private void saveGameProgress() {
		SharedPreferences settings = getSharedPreferences(PREFS_STRING,
				MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();//写数据ss
		String progress = ss;//ss==页面传来的10*10数组string方法
		// 将数据保存
		editor.putString("PROGRESS", progress);
		editor.commit();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 设置无限循环，然后启动播放
		player.setLooping(true);
		player.start();
		/*for(int i=1;i<5;i++){
			music.setLoop(i);
			music.play(i);
			}*/
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 暂停播放
		if (player.isPlaying()) {
			player.pause();			
		}	
			
		
						
			
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 停止播放,释放资源
		player.release();
	/*	for(int i=1;i<5;i++)
		{
			music.unload(i);
		}
		music.release();*/
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 1) {
			ss = data.getStringExtra("PROGRESS");
			saveGameProgress();
			srank=data.getStringExtra("SRANK");
			rank=data.getStringExtra("RANK");
			score=data.getStringExtra("SCORE");
			if(srank!=null)
			{
		//		rankMax = Integer.parseInt(srank);
		//		Log.i("srank",srank);
		//		ranktv.setText(rankMax+"级");
		//		rankstartv.setText(rankMax*2+"颗");
			}
			
						
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


}
