package com.example.popstar;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public class Animation {
	final Bitmap[] keyFrames; // ����ͼ��֡
	final float frameDuration; // ÿ֡ͼ����ʾ��ʱ��
	final float playDuration; // ����������������ʱ��
	float tick0; // �������ſ�ʼ��ʱ�ӽ���
	boolean stopped;
	float x, y; //�������ŵ�λ��
	int acolor;

	public boolean isStopped() {
		return stopped;
	}

	/**
	 * @param frameDuration �������ŵĳ���ʱ�䣬��λΪ��
	 * @param x �������ŵ�λ��
	 * @param y
	 * @param keyFrames ֡ͼ��
	 */
	public Animation (float playDuration, float x, float y,int acolor, Bitmap... keyFrames) {
		this.playDuration = playDuration;
		this.keyFrames = keyFrames;
		this.stopped = false;
		this.frameDuration = playDuration / keyFrames.length;
		this.x = x;
		this.y = y;
		this.acolor = acolor;

		tick0 = -1F;
	}

	
	/**
	 * ������Ϸ���е�ʱ���ȡ�����ؼ�֡
	 * @param stateTime ��Ϸ�ӿ�ʼ���е��ۻ�ʱ�䣬�������Ϊʱ���ᣬ��λΪ��
	 * @param looping �Ƿ���Ҫ�ظ����Ŷ���
	 * @return
	 */
	public Bitmap getKeyFrame (float stateTime, boolean looping) {
		// ��¼�������ŵ���ʼʱ���
		if (tick0 == -1F) {
			tick0 = stateTime;
		}
		
		// ���ݾ�����ʱ�䣬��ȡĳ��ͼ��֡
		int frameNumber = (int)((stateTime - tick0) / frameDuration);

		if (!looping) {
			frameNumber = Math.min(keyFrames.length - 1, frameNumber);
			if (stateTime - tick0 >= playDuration) {
				stopped = true;
			}
		} else {
			frameNumber = frameNumber % keyFrames.length;
		}
		
		return keyFrames[frameNumber];
	}


	
	public void draw(float stateTime, Canvas canvas) {
		Bitmap bmp = getKeyFrame(stateTime, false);
		canvas.drawBitmap(bmp, x, y, null);
	}
}
