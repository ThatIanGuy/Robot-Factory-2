package com.fac.main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound{
	
	Thread thread;
	Clip clip;
	String path;
	int LOOP_CONTINUOUSLY;
	
	public Sound(){
		
	}
	
	public void setClip(String path){
		try {
			clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(AnimRunner.class.getResource("/imgs/" + path));
			clip.open(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play(boolean loop){
		thread = new Thread(new Runnable(){
			public void run() {
				try {
			        clip.start();
			        if(loop){
			        	clip.loop(LOOP_CONTINUOUSLY);
			        }
					
			        
			      }catch (Exception e) {
			    	  System.err.println(e.getMessage());
			      }
			}
		});
		thread.start();
	}
	
	public void stop(){
		try {
			thread.join();
			clip.isRunning();
			clip.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean running(){
		return clip.isRunning();
	}
}
