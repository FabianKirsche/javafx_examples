package org.krlib.musicPlayer.localFilePlayer;

import org.krlib.musicPlayer.api.*;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


/***************************************************************************
 *                                                                         *
 * Class declaration & global variables                                    *
 *                                                                         *
 **************************************************************************/
public class LocalFilePlayer implements IMusicPlayer {
	MediaPlayer mediaPlayer;
	
	/***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/
	public LocalFilePlayer(String pSongPath) {
		setSong(pSongPath);
		setSongName(FileSystems.getDefault().getPath(pSongPath).getFileName().toString());
	}
	
	
	/***************************************************************************
     *                                                                         *
     * Properties                                                              *
     *                                                                         *
     **************************************************************************/
	private StringProperty songName = new SimpleStringProperty();
	public String getSongName() { return songName.get(); }
	public void setSongName(String pSongName) { songName.set(pSongName); }
	public StringProperty getSongNameProperty() { return songName; }
	
	private Path songPath;
	public Path getSongPath() { return songPath; }
	public void setSongPath(Path pPath) { songPath = pPath; }
	
	
	/***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/
	@Override
	public void setSong(String pSong) {
		setSongPath(FileSystems.getDefault().getPath(pSong));
	}

	@Override
	public void play() {
		Media song = new Media(getSongPath().toUri().toString());
		mediaPlayer = new MediaPlayer(song);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); //repeat
		mediaPlayer.play();
	}

	@Override
	public void stop() {
		mediaPlayer.stop();
	}

}
