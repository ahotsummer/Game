package com.example.popstar;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

//����������ǵ�������
//�����������ɢ�����������
//�̻���״���ӿɲ�������ǰд�ķ��̻�Դ��
public class Particle
{
	//�������ӵ�ͼƬ����
	Bitmap bmp[];
	//���ӵ����꣬�Ѿ����ڵ�ʱ��
	int x,y,t=0;
	//���ӵ��ٶ�
	int speedX,speedY;
	//���ӵ���ɫ
	int color;
	//0��ɫ ��1��ɫ��2��ɫ��3��ɫ��4��ɫ
	//�����
	Random random;
	//�����Ƿ�������ʶ
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
    	//����������������
    	//this.x=x+random.nextInt(GameView.screenW/10)-random.nextInt(GameView.screenW/5);
    	//this.y=y+random.nextInt(GameView.screenW/10)-random.nextInt(GameView.screenW/5);
    	//�����ӵķ��䷽����ٶȽ������
    	speedX=-random.nextInt(GameView.screenW/30)+random.nextInt(GameView.screenW/30);
    	speedY=-random.nextInt(GameView.screenW/5)+random.nextInt(GameView.screenW/30);	
	
	}
	
	
	void logic()
	{		
		//������������
		speedY+=9.8*20/300;
    	x+=speedX;
    	y+=speedY;
		//����һ����ʱ�䣬���ӽ�������
		t++;
		if(t>=15)
		{isDead=true;}
	}
	
	void draw(Canvas canvas,Paint paint)
	{	
		//��������		
		canvas.drawBitmap(bmp[color],x,y,paint);
    }
}
