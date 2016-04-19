package com.example.popstar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;


import com.example.popstar.db.scoreSQLiteOpenHelper;
//import com.example.popstar.utils.FriworkAnimation;





public class GameView extends SurfaceView implements SurfaceHolder.Callback {
//private Resources res;
	private Bitmap background; // 游戏背景图
	private Bitmap words;// 文字图
	private Bitmap word1;// 文字图
	private Bitmap word2;// 文字图
	private Bitmap word3;// 文字图
	private Bitmap ui1;// 分数图
	private Bitmap ui2;// 分数图
	private Bitmap ui3;// 分数图
	private Bitmap gameover; // 游戏结束图
	private Bitmap mplay; // 音效图
	private Bitmap mstop; // 音效图
	private Bitmap bpass; // 通关图
	private Bitmap lustar; // 通关图
	private Bitmap bcol; // 通关图
	private Bitmap brow; // 通关图
	//change
	private boolean one = false;
	private boolean two=false;
	private boolean click = true;
	private boolean colorchange=false;
	private Bitmap color; // 改变图
	private Bitmap again; // 改变图
	private int starcolor;
	private boolean change =false;
	private int starcol;
	private int starrow;
	
	
	private int newcol;
	private int newrow;

	private Bitmap[] starpic = new Bitmap[5]; // 存储星星图片
	public scoreSQLiteOpenHelper helper;
	private double p; // 星星的边长
	public static int screenW,screenH;
	private int mark;
	

	public  boolean counts = false;
	public  boolean pass = true;
	public static boolean play =true;
	public  int score = 0; // 分数
	public  int rank = 1; // 关卡
	public static  int srank = 1;  
	public  int  starcounts = 0;
	
	private int asking = 1000; // 每关要求
	private boolean game = true; // 游戏是否继续


	private Music music;

	private SurfaceHolder holder;
	private boolean finished;
	private boolean tpass=true;
	Bitmap[] bmps = null;
	
	final float FRAME_TIME = 1.0F / 60; // 假定每秒绘制60帧
	float stateTime = 0F;
	List<Animation> sprites = Collections
			.synchronizedList(new LinkedList<Animation>());


	public int[][] matrix = null;//星星数组

	private List<Node> list = new ArrayList<Node>();//要消灭的星星

	private Paint paint; // 绘制几何图形的画笔
	private Paint p_text; // 绘制几何图形的画笔
	
	Random random;
	
	//星星粒子图片的数组
	Bitmap bmpParticle[];

	//星星消灭后散射出来的粒子容器
	Vector<Particle> vcParticle;
	Context mycontext;
	
	public GameView(Context context) {
		super(context);
		mycontext= context;
		// 设置画笔属性：颜色、无锯齿平滑、实心线
		paint = new Paint();
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);

		p_text = new Paint();
		p_text.setColor(Color.WHITE);
		p_text.setTextSize(25);
		p_text.setAntiAlias(true);
		p_text.setStyle(Paint.Style.STROKE);

		helper = new scoreSQLiteOpenHelper(context,null,null,0);
		starcounts = helper.getRank()*2;
		// 音效初始化
		music  = new Music(context);
		random=new Random();
		
		// 获取holder，以便我们控制游戏界面的刷新工作
		holder = this.getHolder();
		holder.addCallback(this);
		// 设置游戏界面可触摸
		setFocusable(true);
		setFocusableInTouchMode(true);
		requestFocus();
		
		//初始化10*10星星数组
		if (matrix == null) 
		{
			matrix = new int[10][10];
			for (int i = 0; i < 10; i++) 
			{
				for (int j = 0; j < 10; j++) 
				{
					matrix[i][j] = 1 + (int) (Math.random() * 5);
				}
			}
		}

	}//ofGameView(Context context)
	
	
	
	public Animation makeAnimation(float playDuration, float x, float y,int acolor) {
	//	if (bmps == null) {
			// 加载动画资源，创建动画对象
		if(acolor==0){
			bmps = new Bitmap[10];
			//bmps[0] = BitmapFactory.decodeResource(getResources(), id)
			bmps[0] = getRes(R.raw.p1);
				//	= getRes(R.raw.p1);
			bmps[1] = getRes(R.raw.p2);
			bmps[2] = getRes(R.raw.p3);
			bmps[3] = getRes(R.raw.p4);
			bmps[4] = getRes(R.raw.p5);
			bmps[5] = getRes(R.raw.p6);
			bmps[6] = getRes(R.raw.p7);
			bmps[7] = getRes(R.raw.p8);
			bmps[8] = getRes(R.raw.p9);
			bmps[9] = getRes(R.raw.p10);
		}
		if(acolor==1){
			bmps = new Bitmap[10];
			bmps[0] = getRes(R.raw.r1);
			bmps[1] = getRes(R.raw.r2);
			bmps[2] = getRes(R.raw.r3);
			bmps[3] = getRes(R.raw.r4);
			bmps[4] = getRes(R.raw.r5);
			bmps[5] = getRes(R.raw.r6);
			bmps[6] = getRes(R.raw.r7);
			bmps[7] = getRes(R.raw.r8);
			bmps[8] = getRes(R.raw.r9);
			bmps[9] = getRes(R.raw.r10);
		}
		if(acolor==2){
			bmps = new Bitmap[10];
			bmps[0] = getRes(R.raw.y1);
			bmps[1] = getRes(R.raw.y2);
			bmps[2] = getRes(R.raw.y3);
			bmps[3] = getRes(R.raw.y4);
			bmps[4] = getRes(R.raw.y5);
			bmps[5] = getRes(R.raw.y6);
			bmps[6] = getRes(R.raw.y7);
			bmps[7] = getRes(R.raw.y8);
			bmps[8] = getRes(R.raw.y9);
			bmps[9] = getRes(R.raw.y10);
		}
		if(acolor==3){
			bmps = new Bitmap[14];
			bmps[0] = getRes(R.raw.fw3_01);
			bmps[1] = getRes(R.raw.fw3_02);
			bmps[2] = getRes(R.raw.fw3_03);
			bmps[3] = getRes(R.raw.fw3_04);
			bmps[4] = getRes(R.raw.fw3_05);
			bmps[5] = getRes(R.raw.fw3_06);
			bmps[6] = getRes(R.raw.fw3_07);
			bmps[7] = getRes(R.raw.fw3_08);
			bmps[8] = getRes(R.raw.fw3_09);
			bmps[9] = getRes(R.raw.fw3_10);
			bmps[10] = getRes(R.raw.fw3_11);
			bmps[11] = getRes(R.raw.fw3_12);
			bmps[12] = getRes(R.raw.fw3_13);
			bmps[13] = getRes(R.raw.fw3_14);
			
		}
		if(acolor==4){
			bmps = new Bitmap[13];
			bmps[0] = getRes(R.raw.fw4_01);
			bmps[1] = getRes(R.raw.fw4_02);
			bmps[2] = getRes(R.raw.fw4_03);
			bmps[3] = getRes(R.raw.fw4_04);
			bmps[4] = getRes(R.raw.fw4_05);
			bmps[5] = getRes(R.raw.fw4_06);
			bmps[6] = getRes(R.raw.fw4_07);
		    bmps[7] = getRes(R.raw.fw4_08);
			bmps[8] = getRes(R.raw.fw4_09);
			bmps[9] = getRes(R.raw.fw4_10);
			bmps[10] = getRes(R.raw.fw4_11);				
			bmps[11] = getRes(R.raw.fw4_12);
			bmps[12] = getRes(R.raw.fw4_13);
				
		}
		if(acolor==5){
			bmps = new Bitmap[13];
			bmps[0] = getRes(R.raw.fw5_01);
			bmps[1] = getRes(R.raw.fw5_02);
			bmps[2] = getRes(R.raw.fw5_03);
			bmps[3] = getRes(R.raw.fw5_04);
			bmps[4] = getRes(R.raw.fw5_05);
			bmps[5] = getRes(R.raw.fw5_06);
			bmps[6] = getRes(R.raw.fw5_07);
		    bmps[7] = getRes(R.raw.fw5_08);
			bmps[8] = getRes(R.raw.fw5_09);
			bmps[9] = getRes(R.raw.fw5_10);
			bmps[10] = getRes(R.raw.fw5_11);				
			bmps[11] = getRes(R.raw.fw4_12);
			bmps[12] = getRes(R.raw.fw5_13);
				
		}
		return new Animation(playDuration, x, y,acolor ,bmps);
	}

	
	

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// 计算屏幕大小，并使宽和高符合竖屏要求
		screenW = (w > h) ? h : w;
		screenH = (w > h) ? w : h;
		// ----------------
		// 水平方向：10p = screenW
		p = screenW / 10;
		// 加载背景图片，并按照当前屏幕大小缩放
		//0蓝色 ，1绿色，2紫色，3红色，4黄色
		background = Bitmap.createScaledBitmap(getRes(R.drawable.bg_main), screenW, screenH, false);
		gameover = Bitmap.createScaledBitmap(getRes(R.drawable.gameover), 300, 300, false);
		starpic[0] = Bitmap
				.createScaledBitmap(getRes(R.drawable.star_b), (int) p, (int) p,
						false);
		starpic[1] = Bitmap
				.createScaledBitmap(getRes(R.drawable.star_g), (int) p, (int) p,
						false);
		starpic[2] = Bitmap
				.createScaledBitmap(getRes(R.drawable.star_p), (int) p, (int) p,
						false);
		starpic[3] = Bitmap
				.createScaledBitmap(getRes(R.drawable.star_r), (int) p, (int) p,
						false);
		starpic[4] = Bitmap
				.createScaledBitmap(getRes(R.drawable.star_y), (int) p, (int) p,
						false);
		words = Bitmap.createScaledBitmap(getRes(R.drawable.charactr), (int) (4.2 * p),
				(int) (2.5 * p), false);
		
		bcol = Bitmap
				.createScaledBitmap(getRes(R.drawable.ccol), (int) p, (int) p,
						false);
		brow = Bitmap
				.createScaledBitmap(getRes(R.drawable.rrow), (int) p, (int) p,
						false);
		initGame(screenW,screenH);
		
		double ww = words.getWidth();
		double hh = words.getHeight() / 4;
		//对原位图进行缩放和放大成新的w,h
		//长的
		ui3 = Bitmap.createScaledBitmap(getRes(R.drawable.ui3),
				(int) (3.3 * p), (int) hh, false);
		//2长
		ui2 = Bitmap.createScaledBitmap(getRes(R.drawable.ui2),
				(int) (1.7 * p), (int) hh, false);
		//2短
		ui1 = Bitmap.createScaledBitmap(getRes(R.drawable.ui1),
				(int) (1 * p), (int) hh, false);
		
		mplay = Bitmap.createScaledBitmap(getRes(R.drawable.play),
				(int) (0.8* p), (int) (0.8 * p), false);
		mstop = Bitmap.createScaledBitmap(getRes(R.drawable.stop),
				(int) (0.8 * p), (int) (0.8 * p), false);
		
		bpass = Bitmap.createScaledBitmap(getRes(R.drawable.stage_clear),
				(int) (2.2 * p), (int) (0.8 * p), false);
		
		lustar= Bitmap.createScaledBitmap(getRes(R.drawable.stars),
			    (int) (hh*1.8), (int) (hh*1.8), false);
		//改变
		color= Bitmap.createScaledBitmap(getRes(R.drawable.item_color),
			    (int) (hh*1.6), (int) (hh*1.6), false);
		again=Bitmap.createScaledBitmap(getRes(R.drawable.item_shuffle),
				(int) (hh*1.6), (int) (hh*1.6), false);
		 
		 
		//从souce的指定坐标x,y开始挖一个w,h的对象
		word1 = Bitmap.createBitmap(words, 0, 0, (int) ww, (int) hh);
		word2 = Bitmap.createBitmap(words, 0, (int) (hh), (int) (ww * 0.5),
				(int) hh);
		word3 = Bitmap.createBitmap(words, (int) (ww * 0.5), (int) (hh),
				(int) (ww * 0.5), (int) hh);
		

		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	public  void initGame(int screenW,int screenH)
	{
		//散射出来的星星粒子颜色数组，用于渲染粒子图片
		int colors[]={0xffff00ff,0xff00ec00,0xffffff00,0xffff0000,0xff00ffff};
													
		//粒子的图片
		Bitmap bmpTemp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
				getResources(), R.drawable.particle), screenW/30, screenW/30, false);
		//粒子图片的数组
		bmpParticle=new Bitmap[colors.length];
	
		//粒子图片数组实例化
		//由于该粒子图片只有一张，将它渲染成不同的颜色
		for(int i=0;i<colors.length;i++) 
		{
			bmpParticle[i]=changBitmapColor(bmpTemp,colors[i]);
		}
	
		//粒子容器
		vcParticle=new Vector<Particle>();	
	}
	//返回真实屏幕宽度
		public	int gWidth()
		{
			return this.screenW;
		}

		
		//返回真实屏幕高度
		 public  int gHeight()
		{
			return screenH;
		}

	protected void drawCanvas(Canvas canvas) {
		// 绘制背景图
		canvas.drawBitmap(background, 0, 0, null);
		
		canvas.drawBitmap(word1, 5, 70, null);
		canvas.drawBitmap(ui3, (int) (5 + 4 * p), 65, null);
		
		canvas.drawBitmap(word2, 10, (int) (70 + 0.6 * p), null);
		canvas.drawBitmap(ui1, (int) (10 + 2 * p), (int) (70 + 0.6 * p), null);
		
		canvas.drawBitmap(word3, (int) (20 + 3.2 * p), (int) (70 + 0.6 * p),
				null);
		canvas.drawBitmap(ui2, (int) (40 + 5.2 * p), (int) (70 + 0.6 * p), null);
		
		canvas.drawBitmap(lustar, 40, (int) (190 + 0.3 * p), null);
		canvas.drawBitmap(ui1,(int)(20+1.5*p), (int) (190 + 0.6 * p), null);
		
		canvas.drawBitmap(color, (int)(7.3*p), (int) (170 + 0.6 * p), null);
		canvas.drawBitmap(again, (int)(8.5*p), (int) (170 + 0.6 * p), null);
		
		if(!play)
		{	
			canvas.drawBitmap(mstop, (int) (10 + 8.1 * p), (int) (70 + 0.3 * p), null);
					
			
		}else
		{
			canvas.drawBitmap(mplay, (int) (10 + 8.1 * p), (int) (70 + 0.3 * p), null);
		}
		if(!pass)
		{
			canvas.drawBitmap(bpass, (int)(7*p), (int) (200 + 2 * p), null);
			//canvas.drawBitmap(bpass, (int)(7*p), (int) (200 + 0.6 * p), null);
		}
	     
		if(colorchange&&starcolor>0&&starcolor<6)
		{
			if(starcolor==1){
			canvas.drawBitmap(starpic[1], (int)(2.8*p), (int) (200 + 2 * p), null);
			canvas.drawBitmap(starpic[2], (int)(4*p), (int) (200 + 2 * p), null);
			canvas.drawBitmap(starpic[3], (int)(5.2*p), (int) (200 + 2 * p), null);
			canvas.drawBitmap(starpic[4], (int)(6.4*p), (int) (200 + 2 * p), null);}
			if(starcolor==2){
			canvas.drawBitmap(starpic[0], (int)(2.8*p), (int) (200 + 2 * p), null);
			canvas.drawBitmap(starpic[2], (int)(4*p), (int) (200 + 2 * p), null);
			canvas.drawBitmap(starpic[3], (int)(5.2*p), (int) (200 + 2 * p), null);
			canvas.drawBitmap(starpic[4], (int)(6.4*p), (int) (200 + 2 * p), null);}
			if(starcolor==3){
			canvas.drawBitmap(starpic[0], (int)(2.8*p), (int) (200 + 2 * p), null);
			canvas.drawBitmap(starpic[1], (int)(4*p), (int) (200 + 2 * p), null);
			canvas.drawBitmap(starpic[3], (int)(5.2*p), (int) (200 + 2 * p), null);
			canvas.drawBitmap(starpic[4], (int)(6.4*p), (int) (200 + 2 * p), null);}
			if(starcolor==4){
			canvas.drawBitmap(starpic[0], (int)(2.8*p), (int) (200 + 2 * p), null);
			canvas.drawBitmap(starpic[1], (int)(4*p), (int) (200 + 2 * p), null);
			canvas.drawBitmap(starpic[2], (int)(5.2*p), (int) (200 + 2 * p), null);
			canvas.drawBitmap(starpic[4], (int)(6.4*p), (int) (200 + 2 * p), null);}
			if(starcolor==5){
			canvas.drawBitmap(starpic[0], (int)(2.8*p), (int) (200 + 2 * p), null);
			canvas.drawBitmap(starpic[1], (int)(4*p), (int) (200 + 2 * p), null);
			canvas.drawBitmap(starpic[2], (int)(5.2*p), (int) (200 + 2 * p), null);
			canvas.drawBitmap(starpic[3], (int)(6.4*p), (int) (200 + 2 * p), null);}
		}
		if(change)
		{
			canvas.drawBitmap(bcol, (int)(3.8*p), (int) (200 + 2 * p), null);
			canvas.drawBitmap(brow, (int)(5.4*p), (int) (200 + 2 * p), null);
		}
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 获取触摸动作类型和触摸位置的坐标
		int act = event.getAction();
		int x = (int) event.getX();
		int y = (int) event.getY();
		int col = (int) (x / p);
		int row = (int) ((y - (screenH - 10 * p)) / p);
		
		if (x<900&&x>750&&y<350&&y>200)
		{
			//不能散
			click = false;
			one =true;
			two =false;
			Toast.makeText(mycontext, "点击需要换颜色的星星", Toast.LENGTH_SHORT).show();
		}
		if (x<1200&&x>900&&y<350&&y>200)
		{//	x900-1000
			
			click = false;
			two =true;
			one = false;
			Toast.makeText(mycontext, "点击需要重新排列的星星", Toast.LENGTH_SHORT).show();	
		}
		

		switch (act)
		{
		case MotionEvent.ACTION_DOWN:	
			if (col >= 0 && row >= 0&&click) 
			{
				if (matrix[row][col] != 0)
				{					
					isSelect(col,row);
				
					list.clear();
					// 增加动画效果
					sortStar();
					// invalidate();
					ifClear(col,row);
				}
			}
		case MotionEvent.ACTION_UP:
		//	Log.i("x",""+x);
		//	Log.i("y",""+y);
		//	Log.i("col剪辑时间",""+col);
		//	Log.i("row",""+row);
			if(col >= 0 && row >= 0&&!click)
			{				
				change(col,row);		
			}	
			
			if (x<900&&x>250&&y<600&&y>350&&colorchange==true)
			{
		//		Log.i("info","zoul1");
				changecolor(x,starcol,starrow);
				one=false;
				click = true;
			}
			if (x<900&&x>250&&y<600&&y>350&&change==true)
			{
		//		Log.i("info","zoul2");
				changecolrow(x,newcol,newrow);
				two=false;
				click = true;
			}
			
			if (x<1000&&x>800&&y<200&&y>100)
			{
				setMusic();
			}
		}

		return super.onTouchEvent(event);
	}
	
	
	public void change(int col,int row)
	{
		if (col >= 0 && row >= 0)
		{//x800-900
		 //y245- 300	
			if(one&&!two){
			colorchange=true;
		
			starcolor = matrix[row][col];
			starcol =col;
			starrow=row;
			}
			if(two&&!one)
			{
				change=true;
			
				newcol =col;
				newrow=row;
		//		Log.i("newcol",""+newcol);
		//		Log.i("newrow",""+newrow);
			}
		}
	
	}
	public void changecolrow(int x,int col,int row)
	{
		int ncol =(int)((x-3.8*p)/(1.4*p))+1;
	//	Log.i("ncol",""+ncol);
		if(ncol==1)
		{
			for (int j = 0; j < 10; j++) 
			{
				if(matrix[row][j]!=0)
				{
				matrix[row][j] = 1 + (int) (Math.random() * 5);
				}
			}
		}
		if(ncol==2)
		{		
				
			for (int i = 0;i < 10; i++) 
			{
				if(matrix[i][col]!=0)
				{
				matrix[i][col] = 1 + (int) (Math.random() * 5);
				}
			}
				
		}
		click = true;
		change = false;
	}
	public void changecolor(int x,int col,int row)
	{
		int ncol =(int)((x-2.8*p)/(1.2*p))+1;
		//int nrow =
	//	Log.i("ncol",""+ncol);
	//	Log.i("starcolor",""+starcolor);
	//	Log.i("col",""+col);
	//	Log.i("row",""+row);
	//	Log.i("matrix",""+matrix[row][col]);
		if(starcolor==1){
			matrix[row][col]= ncol +1;
			}
		if(starcolor==2){
				if(ncol==1){matrix[row][col]=ncol;}
				else{matrix[row][col]=ncol+1;}
				}
		if(starcolor==3){
				if(ncol==1||ncol==2){matrix[row][col]=ncol;}
				else{matrix[row][col]=ncol+1;}	
				}
		if(starcolor==4){
				if(ncol==4){matrix[row][col]=ncol+1;}
				else{matrix[row][col]=ncol;}	
				}
		if(starcolor==5){
				matrix[row][col]=ncol;
				}
		
		click = true;
		colorchange = false;
	}
	public void setMusic()
	{
		play=!play;
		if (music!=null)
		{
			if (play==false)
			{
				music.soundST=false;
			}else
			{
				music.soundST=true;
			}
		}
	}
	public void isSelect(int col,int row)
	{
		 //判断点中的是否为0
			mark = matrix[row][col];
			
			list.add(new Node(row, col,(mark-1)));
			List<Node> nodepoint = getRoundNode(list.get(0));//附近是否存在形同的点
			if (nodepoint.size() > 0) 
			{ // 如果附近的存在相同点，则
				matrix[row][col] = 0;
				for (int i = 0; i < list.size(); i++)
				{
					List<Node> nodes = getRoundNode(list.get(i));

					for (int j = 0; j < nodes.size(); j++) 
					{
						if (matrix[nodes.get(j).row][nodes.get(j).col] == mark)
						{
							matrix[nodes.get(j).row][nodes.get(j).col] = 0;
							if (!list.contains(nodes.get(j))) 
							{
								list.add(nodes.get(j));
								
								for(int z =0;z<10;z++)
								{
									vcParticle.addElement(new Particle
				(bmpParticle,(int)(col*p),(int)(row*p+(screenH - 10 * p)),nodes.get(j).color));
									//Log.i("color",""+nodes.get(j).color);
							
								}
								//Log.i("info","到了vcParticle.addElement");
							}
						}
					}
				}
				
				//f.playfw(sprites, list.size());
				playfw(list.size());
				score = list.size() * list.size() * 5 + score;
				
			
				if(score>asking&&pass)
				{
					pass=false;
					//MyToast.myTosat(mycontext, R.drawable.stage_clear, "", Toast.LENGTH_SHORT);
				}
			}		
	}

	public void playfw(int size)
	{  //2,3,4,5
		if(size>1&&size<6)
		{
			sprites.add(makeAnimation(1F, (float) screenW/8,
					(float) ((screenH - 10 * p)*2/3),0) );
			
			sprites.add(makeAnimation(0.5F, (float) screenW*3/8,
					(float) ((screenH - 10 * p)*1/5),2) );
			
			sprites.add(makeAnimation(0.3F, (float) screenW*4/6,
					(float) ((screenH - 10 * p)*1/2),1) );
			music.play(2);
		//	music.play(5);
			//2,3
			if(size>1&&size<4)
			{
				MyToast.myTosat(mycontext, R.drawable.combo_cool, "", 600);
			}
			//4
			if(size>3&&size<6)
			{
				MyToast.myTosat(mycontext, R.drawable.combo_good, "", 700);
			}
		}
		//6,6
		if(size>5)
		{
			sprites.add(makeAnimation(0.5F, (float) screenW/8,
					(float) ((screenH - 10 * p)*4/6),3) );
			
			sprites.add(makeAnimation(1F, (float) screenW*3/8,
					(float) ((screenH - 10 * p)*1/5),4) );
			
			sprites.add(makeAnimation(0.3F, (float) screenW*4/6,
					(float) ((screenH - 10 * p)*3/6),5) );
			music.play(1);
			music.play(2);
			music.play(6);
			if(size<7)
			{
				MyToast.myTosat
			(mycontext, R.drawable.combo_fantastic, "", Toast.LENGTH_SHORT);
			}
			else
			{   MyToast.myTosat
				(mycontext, R.drawable.combo_awesome, "", Toast.LENGTH_SHORT);}
		}
		
	}
	
	private List<Node> getRoundNode(Node sameNode) {// 寻找上下左右四个星星
		List<Node> nodes = new ArrayList<Node>();

		int col = sameNode.col;
		int row = sameNode.row;
		int color = sameNode.color;

		if ((row - 1) >= 0 && matrix[row - 1][col] == mark) {
			nodes.add(new Node(row - 1, col,color));// 上
		}

		if ((row + 1) <= 9 && matrix[row + 1][col] == mark) {
			nodes.add(new Node(row + 1, col,color));// 下
		}

		if ((col - 1) >= 0 && matrix[row][col - 1] == mark) {
			nodes.add(new Node(row, col - 1,color));// 左
		}

		if ((col + 1) <= 9 && matrix[row][col + 1] == mark) {
			nodes.add(new Node(row, col + 1,color));// 右
		}

		return nodes;
	}

	/**
	 * 对改动后的数组进行排序处理
	 */
	private void sortStar() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (matrix[i][j] == 0) {
					for (int k = i; k > 0; k--) {
						matrix[k][j] = matrix[k - 1][j];
					}
					matrix[0][j] = 0;
				}
			}
		}// 0的上浮操作

		for (int i = 8; i >= 0; i--) {
			if (matrix[9][i] == 0) {
				for (int local = i; local <= 8; local++) {
					for (int j = 0; j < 10; j++) {
						matrix[j][local] = matrix[j][local + 1];
					}
				}
				for (int j = 0; j < 10; j++) {
					matrix[j][9] = 0;
				}
			}
		}// 数组的左靠紧处理
	}

	/**
	 * 判断是否已经消完或者游戏over
	 */
	private void ifClear(int col,int row) {
		
		 int  count = 0;
		 counts = false;
		
		for (int i = 0; i < 10; i++) 
		{
			for (int j = 0; j < 10; j++) 
			{
				boolean flag1 = false, flag2 = false, flag3 = false, flag4 = false;
				if (matrix[i][j] == 0) 
				{
					count++;
				} else 
				{
					if (i - 1 >= 0) 
					{
						if (matrix[i - 1][j] != matrix[i][j])
						{
							flag1 = true;
						}
					} else {
						flag1 = true;
					}
					if (i + 1 <= 9) {
						if (matrix[i + 1][j] != matrix[i][j]) {
							flag2 = true;
						}
					} else {
						flag2 = true;
					}
					if (j - 1 >= 0) {
						if (matrix[i][j - 1] != matrix[i][j]) {
							flag3 = true;
						}
					} else {
						flag3 = true;
					}
					if (j + 1 <= 9) {
						if (matrix[i][j + 1] != matrix[i][j]) {
							flag4 = true;
						}
					} else {
						flag4 = true;
					}
					if (flag1 == true && flag2 == true && flag3 == true
							&& flag4 == true) {
						count++;
					}
				}//else
			}//for
		}//for
		if (count == 100) 
		{
			
			for(int s =0;s<5;s++)
			{
				for(int z =0;z<6;z++)
				{
					vcParticle.addElement(new Particle
						(bmpParticle,(int)(col*p)+random.nextInt(3),(int)(row*p+(screenH - 10 * p)+random.nextInt(3)),s));
				}
			}
			
		
			for (int i = 0; i < 10; i++) 
			{
				for (int j = 0; j < 10; j++) 
				{
					matrix[i][j] = 0;
				}
				
			}
			
			if (score >= asking) 
			{
				//通关动画
				pass=true;
				//音乐
				music.play(5);
			
				tpass=false;
				if(!tpass)
				{
					try
					{ 
					   Thread.sleep(3000);
					}
					catch(Exception e){}
					
				}
				for (int i = 0; i < 10; i++) 
				{
					for (int j = 0; j < 10; j++) 
					{
						matrix[i][j] = 1 + (int) (Math.random() * 5);
					}
					
				}
				counts = true;				
				helper.updateScore(String.valueOf(rank), score);
				starcounts = helper.getRank()*2;
				rank++;
				if(srank<rank)
				{
					srank =rank;
				}
			//	starcounts =starcounts+(score-asking)/500;
				asking = ((rank + 1) % 2 + 1) * 1000 + asking;
				tpass =true;
				
			} else 
			{
				game = false;
				music.play(3);
				//	MyToast.myTosat(mycontext, R.drawable.star5, "", Toast.LENGTH_SHORT);
				
				
			}

		}
	}
	public  void logic()
	{				
		//星星粒子运动
		for(int i=0;i<vcParticle.size();i++)
		{
			Particle particle=vcParticle.elementAt(i);
			//粒子消亡，移除粒子
			if(particle.isDead)
			{
				vcParticle.removeElementAt(i);
			}
			else
			{
				particle.logic();
			}
		}			
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// 启动界面刷新线程
		Thread render = new Thread(new GameRender());
		finished = false;
		render.start();
		if(!tpass)
		{
			try
			{ 
			Thread.sleep(9000);
			}
			catch(Exception e){}
			
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// 通知界面刷新线程终止退出
		finished = true;
        music.release();
	}

	/**
	 * 内部类GameRender，用于刷新游戏界面
	 */
	private class GameRender implements Runnable {

		@Override
		public void run() {
			long tick0, tick1;
			float deltaTime;
			List<Animation> stoppedAnims = new ArrayList<Animation>();
			Animation sprite;

			while (!finished) {
				// 获取界面刷新代码执行之前的时间，单位毫秒
				tick0 = System.currentTimeMillis();
				
				// 刷新游戏界面
				try {
					// 锁定画布
					Canvas canvas = holder.lockCanvas(null);
					if (canvas != null) {
						drawCanvas(canvas);
						//holder.
						//画板上画数字
						canvas.drawText(asking + "", (int) (5.3 * p), 90, p_text);
						canvas.drawText(score + "", (int) (90 + 5.2 * p),
								(int) (100 + 0.6 * p), p_text);
						canvas.drawText(rank + "", (int) (50 + 2 * p),
								(int) (110 + 0.6 * p), p_text);
						canvas.drawText(starcounts + "", (int)(16+2*p), 
								(int) (182 + 1 * p), p_text);
						//画板上画星星
						for (int i = 0; i < 10; i++) 
						{
							for (int j = 0; j < 10; j++) 
							{
								if (matrix[i][j] != 0) 
								{
						//将星星画在view上
									canvas.drawBitmap(
											starpic[matrix[i][j] - 1],
											(float) (j * p), (float) (screenH
													- 10 * p + i * p), paint);
								}
							}
						}
						for(int i=0;i<vcParticle.size();i++)
						{
							int a =vcParticle.elementAt(0).color;
							canvas.drawBitmap(bmpParticle[a], vcParticle.elementAt(i).x, vcParticle.elementAt(i).y, null);	
						}
						if (!game) 
						{
							canvas.drawBitmap(gameover, 90, 500, paint);
							//music.play(3);
						}
						// ==================================
						// 绘制动画帧
						stoppedAnims.clear();
					//	Log.i("info后来",""+sprites.size());
						// 注：这里不能使用for-each循环，因为循环过程中可能会有触摸事件发生，动态数组的元素个数会变
						for (int i = 0; i < sprites.size(); i++) {
							sprite = sprites.get(i);
							sprite.draw(stateTime, canvas);
							// 如果某动画对象播放完，则将其放入删除队列
							if (sprite.isStopped()) {
								stoppedAnims.add(sprite);
							}
						}
						sprites.removeAll(stoppedAnims);
						logic();
						// =======================================
						// 获取界面刷新代码执行之后的时间，单位毫秒
						tick1 = System.currentTimeMillis();
						// 计算绘图所花时间(秒)，确保帧率不大于60
						deltaTime = (tick1 - tick0) / 1000F;
						if (deltaTime < FRAME_TIME) {
							Thread.sleep((long) (1000 * (FRAME_TIME - deltaTime)));
							deltaTime = FRAME_TIME;
						}
						// 解锁画布，以便画面在屏幕上显示
						holder.unlockCanvasAndPost(canvas);

						// 计算游戏运行的累积时间
						tick1 = System.currentTimeMillis();
						stateTime = stateTime + (tick1 - tick0) / 1000F;
					}
				} catch (Exception e2) {

				}
			} // of while
		}// of run()
	}// of class GameRender
	
	//返回图片资源/
	 
	public Bitmap getRes(int resID) {
		return BitmapFactory.decodeResource(getResources(),resID);
	}
	

	//将一张图片填充指定颜色
		public Bitmap changBitmapColor(Bitmap bmp,int endColor)
		{Bitmap bmpTemp=Bitmap.createBitmap(bmp.getWidth(),bmp.getHeight(),Bitmap.Config.ARGB_8888);
		Canvas canvas=new Canvas(bmpTemp);
		Paint paint=new Paint();
		paint.setAntiAlias(true);
		paint.setColor(endColor);
			for(int j=0;j<bmpTemp.getWidth();j++)
			{
				for(int k=0;k<bmpTemp.getHeight();k++)
				{
			
					if(bmp.getPixel(j,k)!=0)
					{
						canvas.drawPoint(j,k,paint);			
					}								
				}						
			}		
			return bmpTemp;
		}
		 
		
		
		
}
