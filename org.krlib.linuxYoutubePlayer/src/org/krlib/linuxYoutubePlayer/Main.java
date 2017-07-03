package org.krlib.linuxYoutubePlayer;

public class Main {
	private static String DEMO_URL = "https://www.youtube.com/watch?v=1dwu4iVA1yo";
	
	public static void main(String[] args) {
		
		LinuxYoutubePlayer lnx = null;
		try {
			lnx = new LinuxYoutubePlayer(DEMO_URL);
			lnx.start();
			
			Thread.sleep(60 * 1000);
			
			System.out.println("########################################");
			System.out.println("stop now!");
			lnx.stop();
			
		} catch (Exception e) {
			System.out.println("Main Class Exception catched!");
			System.out.println(e);
		}	
	}

}
