package com.example.popstar;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


//iv.setAlpha(0.5f)Ч��ͼΪ��public void setAlpha(float alpha)
public class MyToast {
	protected static Toast toast  = null;
	private static long oneTime = 0;
    private static long twoTime = 0;
    private static int oldId;
	public static void myTosat(Context context , int imageId ,String content , int duration){
        //newһ��toast����Ҫ��ʾ��activity��������
        toast = new Toast(context);
        //��ʾ��ʱ��
        toast.setDuration(duration);
        //��ʾ��λ��
        toast.setGravity(Gravity.CENTER,50,-400);
   
        //���¸�toast���в���
        LinearLayout toastLayout = new LinearLayout(context);
        toastLayout.setOrientation(LinearLayout.HORIZONTAL);
        toastLayout.setGravity(Gravity.CENTER_VERTICAL);
       
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(imageId);
        //��imageView��ӵ�toastLayout�Ĳ��ֵ���
        toastLayout.addView(imageView);
       
        TextView textView = new TextView(context);
        textView.setText(content);

        //��textView��ӵ�toastLayout�Ĳ��ֵ���
        toastLayout.addView(textView);
 
        //��toastLayout��ӵ�toast�Ĳ��ֵ���
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

	

