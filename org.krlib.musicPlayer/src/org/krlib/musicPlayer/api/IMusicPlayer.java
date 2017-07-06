package org.krlib.musicPlayer.api;

public interface IMusicPlayer {
	public void setSong(String pSong);
	public String getSongName();
	public void play();
	public void stop();
}
