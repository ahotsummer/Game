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
	private int rrank;
	private int rscore;
	private String ss;
	private String rr;
	private String cc;
	
	public String  score;
	public String  rank;
	private  long clickTime;
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
		
		Bmob.initialize(this, "aaf472e2b51fd08faa7c37e71a7e715d");
		
		// ��ȥAndroid����״̬��
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// ��ȥ���������
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ������ʾGameView����
		setContentView(R.layout.ac_start);
				
		// ��raw�ļ����л�ȡһ��������Դ�ļ�
		player = MediaPlayer.create(this, R.raw.start);
	   
	
       
          
		
		
	
		
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
			//	if (state != null&&rrank!=0&&rscore!=0)
				if (state != null)
				{
					Intent intent = new Intent();
					intent.putExtra("matrix", state);//���Ͷ�����Ϣ
					/*intent.putExtra("rrank", rrank);//���Ͷ�����Ϣ
					intent.putExtra("rscore", rscore);//���Ͷ�����Ϣ
*/					intent.setClass(BeginPage.this, MainActivity.class);
					
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
	 * ���浱ǰ��Ϸ����
	 */
	private void saveGameProgress() {
		SharedPreferences settings = getSharedPreferences(PREFS_STRING,
				MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();//д����ss
		String progress = ss;//ss==ҳ�洫����10*10����string����
		/*String rank =rr;
		String score =cc;*/
		// �����ݱ���
		editor.putString("PROGRESS", progress);
	/*	editor.putString("RRANK", rank);
		editor.putString("RSCORE", score);*/
		editor.commit();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// ��������ѭ����Ȼ����������
		player.setLooping(true);
		player.start();
		
		
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
				        Toast.makeText(getApplicationContext(), "�ٰ�һ�κ��˼��˳�����",Toast.LENGTH_SHORT).show();  
				         clickTime = System.currentTimeMillis();  
			}else{
					this.finish();
			}
			
				return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	
	
}
