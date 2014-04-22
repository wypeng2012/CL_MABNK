package com.matrix.mbank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;


public class MainActivity extends Activity {
private ImageView image;
public static MainActivity instance=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.activity_main);
		instance=this;
		image=(ImageView)findViewById(R.id.startImage);
		AlphaAnimation animation=new AlphaAnimation(0.1f, 1.0f);
		animation.setDuration(3000);
		image.setAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				//Toast.makeText(MainActivity.this, "animation start", Toast.LENGTH_SHORT).show();
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				//Toast.makeText(MainActivity.this, "animation end", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(MainActivity.this, LoginActivity.class));
				
			}
		});
	}
}
