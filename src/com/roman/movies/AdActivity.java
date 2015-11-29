package com.roman.movies;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class AdActivity extends AppCompatActivity implements OnClickListener{

	private ImageView img;
	private Bitmap intentBitmap;
	private Button skipBtn;
	
	// Splash screen lenght is 5 sec
	private final int SPLASH_LENGHT = 5000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity);
		Intent intent = getIntent();
		if (intent.getExtras() != null)
			intentBitmap = (Bitmap) intent.getParcelableExtra("adImage");
		img = (ImageView)findViewById(R.id.iv_splashLogo);
		img.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		img.setImageBitmap(intentBitmap);
		
		skipBtn = (Button)findViewById(R.id.btn_skip);
		skipBtn.setVisibility(View.VISIBLE);
		skipBtn.setOnClickListener(this);

		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent intent = new Intent();
				setResult(100,intent);
				finish();
			}
		},SPLASH_LENGHT);

	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		setResult(100,intent);
		finish();
	}
	
	@Override
	public void onBackPressed(){}
	
	
	

}
