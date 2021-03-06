package com.example.popstar;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.Bmob;

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
	private int rrank;
	private int rscore;
	private String ss;
	private String rr;
	private String cc;
	
	public String  score;
	public String  rank;
	private  long clickTime;
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
		
		Bmob.initialize(this, "aaf472e2b51fd08faa7c37e71a7e715d");
		
		// 隐去Android顶部状态栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 隐去程序标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置显示GameView界面
		setContentView(R.layout.ac_start);
				
		// 从raw文件夹中获取一个音乐资源文件
		player = MediaPlayer.create(this, R.raw.start);
	   
	
       
          
		
		
	
		
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
			//	if (state != null&&rrank!=0&&rscore!=0)
				if (state != null)
				{
					Intent intent = new Intent();
					intent.putExtra("matrix", state);//传送额外信息
					/*intent.putExtra("rrank", rrank);//传送额外信息
					intent.putExtra("rscore", rscore);//传送额外信息
*/					intent.setClass(BeginPage.this, MainActivity.class);
					
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
			/*String rank = settings.getString("RRANK", "abc");
			String score = settings.getString("RSCORE", "abc");*/
			if (!progress.equals("abc")) {
				state = new int[10][10];
				state = Utils.str2array(progress);		
			}
		/*	if (!rank.equals("abc")) {			
				rrank = Utils.rank;
		
			}
			if (!score.equals("abc")) {
				rscore = Utils.score;
			}
			*/
			
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
		/*String rank =rr;
		String score =cc;*/
		// 将数据保存
		editor.putString("PROGRESS", progress);
	/*	editor.putString("RRANK", rank);
		editor.putString("RSCORE", score);*/
		editor.commit();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 设置无限循环，然后启动播放
		player.setLooping(true);
		player.start();
		
		
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
	
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 1) {
			ss = data.getStringExtra("PROGRESS");
			/*rr = data.getStringExtra("RRANK");
			cc = data.getStringExtra("RSCORE");*/
			saveGameProgress();
			
			srank=data.getStringExtra("SRANK");
			rank=data.getStringExtra("RANK");
			score=data.getStringExtra("SCORE");
			
						
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


	public boolean onKeyDown(int keyCode,KeyEvent event)
	{
		switch(keyCode)
		{
			case KeyEvent.KEYCODE_BACK:
				
			if ((System.currentTimeMillis() - clickTime) > 2000) {  
				        Toast.makeText(getApplicationContext(), "再按一次后退键退出程序",Toast.LENGTH_SHORT).show();  
				         clickTime = System.currentTimeMillis();  
			}else{
					this.finish();
			}
			
				return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	
	
}
