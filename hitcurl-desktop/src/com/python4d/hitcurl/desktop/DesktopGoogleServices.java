package com.python4d.hitcurl.desktop;

import com.python4d.hitcurl.game.IGoogleServices;

public class DesktopGoogleServices implements IGoogleServices {

	@Override
	public void signIn()
	{
		System.out.println("DesktopGoogleServies: signIn()");
	}

	@Override
	public void signOut()
	{
		System.out.println("DesktopGoogleServies: signOut()");
	}

	@Override
	public void rateGame()
	{
		System.out.println("DesktopGoogleServices: rateGame()");
	}

	@Override
	public void submitScore(long score, int nbclue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showScores(int nbclue)
	{
		System.out.println("DesktopGoogleServies: showScores(" + nbclue + ")");
	}

	@Override
	public boolean isSignedIn()
	{
		System.out.println("DesktopGoogleServies: isSignedIn()");
		return false;
	}

}
