package com.python4d.hitcurl.game;

public interface IGoogleServices {
	public void signIn();

	public void signOut();

	public void rateGame();

	public void submitScore(long score, int nbclue);

	public void showScores(int nbclue);

	public boolean isSignedIn();
}