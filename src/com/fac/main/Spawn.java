package com.fac.main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class Spawn implements ActionListener {
	public int sizeX = 0;
	public int sizeY = 0;
	public int posX = 0;
	public int posY = 0;
	public int vX = 0;
	public int vY = 0;
	int yVelocity = 15;
	int xVelocity = 0;
	int accelY = -1;
	int accelX = 0;
	
	AnimRunner b = new AnimRunner();
	
	/*public Spawn(int x, int y, int sizX, int sizY){
		posX = x;
		posY = y;
		sizeX = sizX;
		sizeY = sizY;
	}*/
	
	public void setSize(int x, int y, int sizX, int sizY){
		posX = x;
		posY = y;
		sizeX = sizX;
		sizeY = sizY;		
		sizeX = (int)(Math.random()*10)+10;

	}
	
	public void paintRect(Graphics g){
		xVelocity = (int)((Math.random()*10)-5);
		g.setColor(Color.GRAY);
		g.fillRect(posX - (sizeX/2),  posY,  sizeX,  sizeX);
	}
	
	public void animation(){
		Spawn animation = new Spawn();
		Timer l = new Timer(20,animation);
		l.start();
		
	}
	public void move(){
		
		posY+=yVelocity;
		yVelocity += accelY;
		posX += xVelocity;
		xVelocity += accelX;
		if(yVelocity>=15){
			yVelocity = 15;
		}
		if(yVelocity <= -5){
			yVelocity = -5;
		}
		/*posY += yVelocity;
		yVelocity += accelY;
		if(yVelocity > 60){
			yVelocity = 60;
		}
		if(posY > 600){
			yVelocity = -yVelocity;
		}*/
		
	}
	
	public void actionPerformed(ActionEvent e){
		
		posY += yVelocity;
		yVelocity += accelY;
		sizeX -=1;
		
	}
}

