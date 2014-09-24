package com.python4d.hitcurl.android;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.python4d.hitcurl.HitcurL;

//
//admob
//https://github.com/libgdx/libgdx/wiki/Admob-in-libgdx
//https://github.com/libgdx/libgdx/wiki/Google-Mobile-Ads-in-Libgdx-(replaces-deprecated-AdMob)

public class AndroidLauncher extends AndroidApplication implements IActivityRequestHandler {

	protected AdView adView;

	private final static AdRequest adr = new AdRequest.Builder().build();
	private final int SHOW_ADS = 1;
	private final int HIDE_ADS = 0;

	protected Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_ADS:
			{
				adView.setVisibility(View.VISIBLE);
				break;
			}
			case HIDE_ADS:
			{
				adView.setVisibility(View.GONE);
				break;
			}
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Create the layout
		RelativeLayout layout = new RelativeLayout(this);

		// Do the stuff that initialize() would do for you
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		// Create the libgdx View
		View gameView = initializeForView(new HitcurL());

		// Create and setup the AdMob view
		adView = new AdView(this);
		adView.setAdUnitId("ca-app-pub-1008481061910472/6901250516");
		// Size (WxH) Description Availability AdSize Constant
		// 320x50 Standard Banner Phones and Tablets BANNER
		// 320x100 Large Banner Phones and Tablets LARGE_BANNER
		// 300x250 IAB Medium Rectangle Phones and Tablets MEDIUM_RECTANGLE
		// 468x60 IAB Full-Size Banner Tablets FULL_BANNER
		// 728x90 IAB Leaderboard Tablets LEADERBOARD
		adView.setAdSize(AdSize.BANNER); // Put in your secret key here
		adView.loadAd(adr);

		// Add the libgdx view
		layout.addView(gameView);

		// Add the AdMob view
		RelativeLayout.LayoutParams adParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		adParams.addRule(RelativeLayout.CENTER_IN_PARENT);

		layout.addView(adView, adParams);

		// Hook it all up
		setContentView(layout);
	}

	// This is the callback that posts a message for the handler
	@Override
	public void showAds(boolean show) {
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}
}
