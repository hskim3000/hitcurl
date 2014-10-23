package com.python4d.hitcurl.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;
import com.python4d.hitcurl.HitcurL;
import com.python4d.hitcurl.game.Constants;
import com.python4d.hitcurl.game.IGoogleServices;

//
//admob
//https://github.com/libgdx/libgdx/wiki/Admob-in-libgdx
//https://github.com/libgdx/libgdx/wiki/Google-Mobile-Ads-in-Libgdx-(replaces-deprecated-AdMob)

public class AndroidLauncher extends AndroidApplication implements IActivityRequestHandler, IGoogleServices {

	protected AdView adView;
	private GameHelper _gameHelper;
	private final static AdRequest adr = new AdRequest.Builder().build();
	private static final int REQUEST_CODE_UNUSED = 9002;
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
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		// Create the GameHelper.
		// @see
		// http://fortheloss.org/tutorial-set-up-google-services-with-libgdx/
		_gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		_gameHelper.enableDebugLog(true);

		GameHelperListener gameHelperListener = new GameHelper.GameHelperListener()
		{
			@Override
			public void onSignInSucceeded()
			{
			}

			@Override
			public void onSignInFailed()
			{
			}
		};

		_gameHelper.setup(gameHelperListener);
		// Create the layout
		RelativeLayout layout = new RelativeLayout(this);

		// Do the stuff that initialize() would do for you
		// http://stackoverflow.com/questions/16939814/android-util-androidruntimeexception-requestfeature-must-be-called-before-add
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		// Create the libgdx View
		View gameView = initializeForView(new HitcurL((IGoogleServices) this, getString(R.string.app_version)));

		// Create and setup the AdMob view
		adView = new AdView(this);
		// Put in your secret key here
		adView.setAdUnitId("ca-app-pub-1008481061910472/6901250516");
		// Size (WxH) Description Availability AdSize Constant
		// 320x50 Standard Banner Phones and Tablets BANNER
		// 320x100 Large Banner Phones and Tablets LARGE_BANNER
		// 300x250 IAB Medium Rectangle Phones and Tablets MEDIUM_RECTANGLE
		// 468x60 IAB Full-Size Banner Tablets FULL_BANNER
		// 728x90 IAB Leaderboard Tablets LEADERBOARD
		adView.setAdSize(AdSize.BANNER);
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
		showAds(false);
		// Hook it all up
		setContentView(layout);
	}

	// This is the callback that posts a message for the handler
	@Override
	public void showAds(boolean show) {
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		_gameHelper.onStart(this);
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		_gameHelper.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		_gameHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void signIn()
	{
		try
		{
			runOnUiThread(new Runnable()
			{
				// @Override
				public void run()
				{
					_gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void signOut()
	{
		try
		{
			runOnUiThread(new Runnable()
			{
				// @Override
				public void run()
				{
					_gameHelper.signOut();
				}
			});
		} catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void rateGame()
	{
		// Replace the end of the URL with the package of your game
		String str = "https://play.google.com/store/apps/details?id=org.fortheloss.plunderperil";
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
	}

	@Override
	public void submitScore(long score, int nbclue)
	{
		if (isSignedIn() == true)
		{
			if (nbclue == Constants.EASY)
				Games.Leaderboards.submitScore(_gameHelper.getApiClient(), getString(R.string.leaderboard_highscoreeasy), score);
			if (nbclue == Constants.NORMAL)
				Games.Leaderboards.submitScore(_gameHelper.getApiClient(), getString(R.string.leaderboard_highscorenormal), score);
			if (nbclue == Constants.EXPERT)
				Games.Leaderboards.submitScore(_gameHelper.getApiClient(), getString(R.string.leaderboard_highscoreexpert), score);
			// startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(),
			// getString(R.string.leaderboard_highscreen)),
			// REQUEST_CODE_UNUSED);

		}
		else
		{
			// Maybe sign in here then redirect to submitting score?
		}
	}

	@Override
	public void showScores(int nbclue)
	{
		if (isSignedIn() == true)
			switch (nbclue) {
			case Constants.EASY:
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(), getString(R.string.leaderboard_highscoreeasy)),
						REQUEST_CODE_UNUSED);
				break;
			case Constants.NORMAL:
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(), getString(R.string.leaderboard_highscorenormal)),
						REQUEST_CODE_UNUSED);
				break;
			case Constants.EXPERT:
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(), getString(R.string.leaderboard_highscoreexpert)),
						REQUEST_CODE_UNUSED);
				break;
			default:
				break;
			}
		else
		{
			// Maybe sign in here then redirect to showing scores?
		}
	}

	@Override
	public boolean isSignedIn()
	{
		return _gameHelper.isSignedIn();
	}
}
