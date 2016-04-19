package com.example.popstar;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


//iv.setAlpha(0.5f)效果图为：public void setAlpha(float alpha)
public class MyToast {
	protected static Toast toast  = null;
	private static long oneTime = 0;
    private static long twoTime = 0;
    private static int oldId;
	public static void myTosat(Context context , int imageId ,String content , int duration){
        //new一个toast传入要显示的activity的上下文
        toast = new Toast(context);
        //显示的时间
        toast.setDuration(duration);
        //显示的位置
        toast.setGravity(Gravity.CENTER,50,-400);
   
        //重新给toast进行布局
        LinearLayout toastLayout = new LinearLayout(context);
        toastLayout.setOrientation(LinearLayout.HORIZONTAL);
        toastLayout.setGravity(Gravity.CENTER_VERTICAL);
       
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(imageId);
        //把imageView添加到toastLayout的布局当中
        toastLayout.addView(imageView);
       
        TextView textView = new TextView(context);
        textView.setText(content);

        //把textView添加到toastLayout的布局当中
        toastLayout.addView(textView);
 
        //把toastLayout添加到toast的布局当中
        toast.setView(toastLayout);
        
   //     toast.show();     
        if(toast==null)
        {   
          toast.show();  
          oneTime=System.currentTimeMillis();  
        }else
        {  
               twoTime=System.currentTimeMillis();  
                if(imageId==oldId)
                {  
                    if(twoTime-oneTime>Toast.LENGTH_SHORT)
                    {  toast.show();  }  
                    }else{  
                        oldId =imageId ;   
                        toast.show();  
                    }         
                }  
                oneTime=twoTime;         
    }
}

	

