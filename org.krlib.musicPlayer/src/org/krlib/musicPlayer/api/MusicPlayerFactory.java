package org.krlib.musicPlayer.api;

import org.krlib.musicPlayer.localFilePlayer.LocalFilePlayer;

public class MusicPlayerFactory {
	
	public static IMusicPlayer createMusicPlayer(String pValue) {
		return new LocalFilePlayer(pValue);
	}

}
