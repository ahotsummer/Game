package com.example.popstar;



import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class Music {
	
	private SoundPool soundpool;
	//private MediaPlayer music;
	private HashMap<Integer, Integer> soundmap = new HashMap<Integer, Integer>();
	private Context Context;
	int id ;
	public static boolean soundST = true; //���ֿ���
   // private static boolean soundSt = true; //��Ч����
	
	public Music(Context Context){
		this.Context=Context;
		soundpool = new SoundPool(6, AudioManager.STREAM_SYSTEM,100);	
		soundmap.put(1, soundpool.load(Context, R.raw.fireworks_01, 1));
		soundmap.put(2, soundpool.load(Context, R.raw.pop_star, 1));
		soundmap.put(3, soundpool.load(Context, R.raw.gameover, 1));
		soundmap.put(4, soundpool.load(Context, R.raw.logo, 1));
		soundmap.put(5, soundpool.load(Context, R.raw.level_up, 1));
		soundmap.put(6, soundpool.load(Context, R.raw.applause, 1));
	}
	public int play(int id)
	{
		// ��ȡϵͳ��������
		AudioManager mgr = (AudioManager) Context.getSystemService(
						Context.AUDIO_SERVICE);
		// ��ȡϵͳ��ǰ�������������ֵ
		float currVol = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
		float maxVol = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = currVol / maxVol;
				// ������Ч���ĸ���������Чid�����������������������������ȼ���ѭ�����ط�ֵ
		//		soundPool.play(sound, volume, volume, 1, 0, 1.0f);
	//	soundpool.play(soundID, leftVolume, rightVolume, priority, loop, rate);
		if(soundST)
		{
			soundpool.play(soundmap.get(id), volume/10, volume/10, id, 0, 1);	
		}
	//	else{unload(id);}
		return 0;
				
	}
	public void setLoop(int id)
	{
	    soundpool.setLoop(id, -1); 
	}
	public void pause()
	{
		for(int i=1;i<7;i++){
	    soundpool.pause(i);
		}
	}
	public void stop(int id)
	{
	    soundpool.stop(id);
	}
	public void release()
	{
	    soundpool.release();
	}
	public void unload(int id)
	{
	    soundpool.unload(id);
	}
}
