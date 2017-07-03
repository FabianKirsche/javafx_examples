package org.krlib.linuxYoutubePlayer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinuxYoutubePlayer {
	private ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
	private Thread execThread;
	private Thread stopThread;
	
	/***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
	 * @throws Exception 
     **************************************************************************/
	public LinuxYoutubePlayer(String pUrl) throws Exception {
		checkIfLinuxSystem();
		queue.add(pUrl);
	}
	
	public LinuxYoutubePlayer(String[] pUrls) throws Exception {
		checkIfLinuxSystem();
		for (String s : pUrls) {
			queue.add(s);
		}
	}
	
	
	/***************************************************************************
     *                                                                         *
     * Properties                                                              *
     *                                                                         *
     **************************************************************************/
	private Boolean running = false;
	public Boolean getRunning() {return running;}
	private void setRunning(Boolean pValue) {running = pValue;}
	
	
	/***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
	 * @throws Exception 
     **************************************************************************/
	private void checkIfLinuxSystem() throws Exception {
		if (!isLinuxSystem()) {
			System.out.println("Not a linux system!");
			throw new Exception("Not a linux system! This class can only be used on Linux systems!");
		}
		
	}
	
	public void start() {
		if (!queue.isEmpty() && !getRunning()) {
			execThread = new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println("Thread started");
					Process proc = null;
					BufferedReader in = null;
					
					while(getRunning()) {
						String url = queue.poll();
						String resourceUrl = "";
						
						if (url != null) {
							try {
								System.out.println("Trying to start the following stream: " + url);
								/*
								 * Get resource url through youtube-dl
								 */
								String command = "youtube-dl -g " + url;
								System.out.println("Command to excecute: " + command);
								
								proc = Runtime.getRuntime().exec(command);
								Reader r = new InputStreamReader(proc.getInputStream());
								in = new BufferedReader(r);
								
								String line;
								while((line = in.readLine()) != null) {
									resourceUrl = resourceUrl + line; 
								}
								in.close();
								Integer exitCode = proc.waitFor();
								System.out.println("Exit code of '" + command + "': " + exitCode);
								
								System.out.println("ResourceURL: " + resourceUrl);
								
								/*
								 * Start stream
								 */									
								command = "omxplayer '" + resourceUrl + "'";
								System.out.println("Command to excecute: " + command);
								
								//proc = Runtime.getRuntime().exec(command);
								ProcessBuilder pb = new ProcessBuilder("bash", "-c", "omxplayer " + "'" + resourceUrl + "'");
								proc = pb.start();
								
								while(getRunning()) {
									if (!proc.isAlive()) {
										break;
									}
									Thread.sleep(100);
									System.out.println("getRunningCheck: " + getRunning());
								}
								
								/*
								 * Kill the omxplayer process
								 */
								proc.destroyForcibly();
								exitCode = proc.waitFor();
								System.out.println("Exit code of '" + command + "': " + exitCode);
								
								// Now kill it again cuz above didnt work
								// First get the pid
								pb = new ProcessBuilder("bash", "-c", "ps -ef |grep /usr/bin/omxplayer.bin");
								proc = pb.start();
								r = new InputStreamReader(proc.getInputStream());
								in = new BufferedReader(r);
								
								String processes = "";
								while((line = in.readLine()) != null) {
									processes = processes + "\n" + line; 
								}
								in.close();
								System.out.println("Processes: " + processes);
								
								//Get the processes again to compare the pids now and determine which is the correct one
								pb = new ProcessBuilder("bash", "-c", "ps -ef |grep /usr/bin/omxplayer.bin");
								proc = pb.start();
								r = new InputStreamReader(proc.getInputStream());
								in = new BufferedReader(r);
								
								String processes2 ="";
								while((line = in.readLine()) != null) {
									processes2 = processes2 + "\n" + line; 
								}
								in.close();
								System.out.println("Processes2: " + processes2);
								
								//Get process to kill
								String pidRegex = System.getProperty("user.name") + "\\s+(?<pid>[\\d]+)[\\v]*";
								System.out.println("pidRegex: " + pidRegex);
								Pattern pidPatt = Pattern.compile(pidRegex);
								Matcher m;
								
								String processToKill = "";
								Integer pid = 0;
								for (String s : processes.split("\\n")) {
									System.out.println("Iteration - s: " + s);
									m = pidPatt.matcher(s);
									if (m.lookingAt()) {
										System.out.println("m.group(pid): " + m.group("pid"));
										if (processes2.contains(m.group("pid"))) {
											processToKill = s;
											pid = Integer.parseInt(m.group("pid"));
										}
									}
								}
								System.out.println("processToKill: " + processToKill);
								System.out.println("pid: " + pid);
								
								m = pidPatt.matcher(processToKill);
								if (m.matches()) {
									pid = Integer.parseInt(m.group("pid"));
								}
								System.out.println("PID to kill: " + pid);
								if (pid != 0) {
									
									pb = new ProcessBuilder("bash", "-c", "kill " + pid);
									proc = pb.start();
									exitCode = proc.waitFor();
									System.out.println("exit code of kill command for pid " + pid + "is: " + exitCode);
									System.out.println("done.");
								}
								
									
							} catch (Exception e) {
								System.out.println("An error occured while trying to play back the current video: " + url);
								e.printStackTrace();
							}
						}
						else {
							break;
						}
					}
				}
			});
			execThread.setDaemon(true);
			execThread.start();
			setRunning(true);
		}
	}
	
	public void stop() {
		if (getRunning()) {
			System.out.println("in stop in if; interrupting execThread now");
			
			
			
			setRunning(false);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
		}
	}
	
	public void addToQueue(String pUrl) {
		queue.add(pUrl);
	}
	
	public ConcurrentLinkedQueue<String> getQueue(){
		return queue;
	}
	
	public void removeFromQueue(String pUrl) {
		if (queue.contains(pUrl)) {
			queue.remove(pUrl);
		}
	}
	
	/***************************************************************************
     *                                                                         *
     * Static Methods                                                          *
     *                                                                         *
     **************************************************************************/
	static boolean isLinuxSystem()
	  { String osName = System.getProperty("os.name").toLowerCase();
	    return osName.indexOf("linux") >= 0;
	  }
}
