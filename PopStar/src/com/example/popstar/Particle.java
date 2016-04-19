package com.example.popstar;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

//这个类是星星的粒子类
//当星星消灭后散射出星星粒子
//烟花形状粒子可参照我以前写的放烟花源码
public class Particle
{
	//星星粒子的图片数组
	Bitmap bmp[];
	//粒子的坐标，已经存在的时间
	int x,y,t=0;
	//粒子的速度
	int speedX,speedY;
	//粒子的颜色
	int color;
	//0蓝色 ，1绿色，2紫色，3红色，4黄色
	//随机库
	Random random;
	//粒子是否消亡标识
	boolean isDead;
	Particle(Bitmap[]bmp,int x,int y,int color)
	{
		random=new Random();
		
		this.color=color;
		this.bmp=bmp;
	
		this.x=x;
		this.y=y;
		//speedX=-random.nextInt(50)+3;
		//speedY=random.nextInt(80)-4;
    	//对粒子坐标进行随机
    	//this.x=x+random.nextInt(GameView.screenW/10)-random.nextInt(GameView.screenW/5);
    	//this.y=y+random.nextInt(GameView.screenW/10)-random.nextInt(GameView.screenW/5);
    	//对粒子的发射方向和速度进行随机
    	speedX=-random.nextInt(GameView.screenW/30)+random.nextInt(GameView.screenW/30);
    	speedY=-random.nextInt(GameView.screenW/5)+random.nextInt(GameView.screenW/30);	
	
	}
	
	
	void logic()
	{		
		//更新粒子坐标
		speedY+=9.8*20/300;
    	x+=speedX;
    	y+=speedY;
		//存在一定的时间，粒子进行消亡
		t++;
		if(t>=15)
		{isDead=true;}
	}
	
	void draw(Canvas canvas,Paint paint)
	{	
		//绘制粒子		
		canvas.drawBitmap(bmp[color],x,y,paint);
    }
}
