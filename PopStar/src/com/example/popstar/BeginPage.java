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

//���˵����ĸ���ťѡ��
public class BeginPage extends Activity {

	// �������������Ϣ��TAG
	public static final String TAG = "PopStar";
	
	// �����洢����ͨ�����ֿ��ҵ�SharedPreferences����
	public static final String PREFS_STRING = "Pop_GAME_PROGRESS";//�ļ���
	
	private Button btn_start;
	private Button btn_rate;
	private Button btn_resume;
	private Button btn_exit;
	private int state[][];//���¼��ص���Ϸ��������
	private String ss;
	public String  score;
	public String  rank;

	// �������ֲ���MediaPlayer����
	private MediaPlayer player;
	private Music music;
	private scoreSQLiteOpenHelper helper;
	//����
	final float FRAME_TIME = 1.0F / 60; // �ٶ�ÿ�����60֡
	float stateTime = 0F;
	List<Animation> sprites = Collections
			.synchronizedList(new LinkedList<Animation>());

	//�̻�����
	private ImageView animationIV;
	private ImageView animationIV1;
	private ImageView animationIV2;
	private  AnimationDrawable anim;
	private  AnimationDrawable anim1;
	private  AnimationDrawable anim2;
	//�ƶ���
	private ImageView light_red;
	private ImageView light_yellow;
	private ImageView light_blue;
	private ImageView light_green;
	private ImageView light_pink;
	//�ȼ�
	
	private TextView ranktv ;
	private TextView rankstartv ;
	public  int rankMax=1;
	public String srank ;
		

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ��ȥAndroid����״̬��
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// ��ȥ���������
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ������ʾGameView����
		setContentView(R.layout.ac_start);
				
		// ��raw�ļ����л�ȡһ��������Դ�ļ�
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
					rankstartv.setText(a*2+"��");
			}
			
		}.start();
	  */
       
          
		
		
	
		
		btn_start = (Button) findViewById(R.id.btn_start);
		btn_rate = (Button) findViewById(R.id.btn_rate);
		btn_resume = (Button) findViewById(R.id.btn_resume);
		btn_exit = (Button) findViewById(R.id.btn_exit);
	
		
		//��ʼ��Ϸ
		btn_start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				
				Intent intent = new Intent();
				intent.setClass(BeginPage.this, MainActivity.class);
				//�����requestcodeMainActivity100
				startActivityForResult(intent, 100);
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);//���뵭��Ч��

			}
		});
		
		//���а�
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
						android.R.anim.fade_out);//���뵭��Ч��
			}
		});
		
		//�ָ�
		btn_resume.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				loadGameProgress();
				if (state != null) {
					Intent intent = new Intent();
					intent.putExtra("matrix", state);//���Ͷ�����Ϣ
					intent.setClass(BeginPage.this, MainActivity.class);
					
					startActivityForResult(intent, 101);//���浱ǰ��Ϸ����
					overridePendingTransition(android.R.anim.fade_in,
							android.R.anim.fade_out);
				}
			}
		});
		
		//�˳�
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
		// ���ö���������������״̬
		anims.setFillAfter(true);
		// ���صڶ��ݶ�����Դ
		final Animation reverse = AnimationUtils.loadAnimation(this
						, R.anim.reverse);
		// ���ö���������������״̬
		reverse.setFillAfter(true);
		
		light_red= (ImageView)findViewById(R.id.light_red);
		light_yellow= (ImageView)findViewById(R.id.light_yellow);
		light_blue= (ImageView)findViewById(R.id.light_blue);
		light_green= (ImageView)findViewById(R.id.light_green);
		light_pink= (ImageView)findViewById(R.id.light_pink);
		// ���ص�һ�ݶ�����Դ
		
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
	 * ������ǰ�������Ϸ����
	 */
	private void loadGameProgress() {
		try {
			SharedPreferences settings = getSharedPreferences(PREFS_STRING,
					MODE_PRIVATE);
			String progress = settings.getString("PROGRESS", "abc");//��PROGRESS�����ݣ���abc������
			if (!progress.equals("abc")) {
				state = new int[10][10];
				state = Utils.str2array(progress);
			}

		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	/**
	 * ���浱ǰ��Ϸ����
	 */
	private void saveGameProgress() {
		SharedPreferences settings = getSharedPreferences(PREFS_STRING,
				MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();//д����ss
		String progress = ss;//ss==ҳ�洫����10*10����string����
		// �����ݱ���
		editor.putString("PROGRESS", progress);
		editor.commit();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// ��������ѭ����Ȼ����������
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
		// ��ͣ����
		if (player.isPlaying()) {
			player.pause();			
		}	
			
		
						
			
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// ֹͣ����,�ͷ���Դ
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
		//		ranktv.setText(rankMax+"��");
		//		rankstartv.setText(rankMax*2+"��");
			}
			
						
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


}
