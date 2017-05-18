package com.fac.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class Jumper extends Enemy{
	int x;
	int y;
	int sizeX = 50;
	int sizeY;
	int yVelocity;
	int xVelocity;
	int yAccel = 3;
	boolean falling;
	int platCollide;
	int playerCollide;
	
	Platform plat = new Platform();
	
	public Jumper(){
		
	}
	
	public Jumper(int posX, int posY, int sizX, int sizY){
		x = posX;
		y = posY;
		sizeX = sizX;
		sizeY = sizY;
	}
	
	public void setSize(){
		
	}
	public void paint(Graphics g, Image texture){
		g.drawImage(texture, x, y, null);
		if(texture == null){
			g.setColor(Color.CYAN);
			g.fillRect(x, y, sizeX, sizeY);
		}
	}
	public void paint(Graphics g){
		g.setColor(Color.CYAN);
		g.fillRect(x, y, sizeX, sizeY);
	}
	public int intersectWPlatform(int x, int y, ArrayList plats){
		
		for(int i = 0; i <plats.size();i++){
			if(x+sizeX>= ((Platform) plats.get(i)).getX((Platform) plats.get(i))
				&&x<=(((Platform) plats.get(i)).getX((Platform)plats.get(i)))+sizeX
				&&y+sizeY>=(((Platform) plats.get(i)).getY((Platform)plats.get(i)))
				&&y<=(((Platform) plats.get(i)).getY((Platform)plats.get(i))+sizeY)){
				platCollide = i;					
				return platCollide;
			}
		}
		return -1;
	}
	public int intersect(int playerx, int playery, ArrayList jumpers, int playersize){
		for(int i = 0; i <jumpers.size();i++){
			if(playerx+playersize>= getJX((Jumper) jumpers.get(i))
					&&playerx<=getJX((Jumper)jumpers.get(i))+sizeX
					&&playery+playersize>=getJY((Jumper)jumpers.get(i))
					&&playery<=getJY((Jumper)jumpers.get(i))+playersize){
					playerCollide = i;
				return playerCollide;
			}
		}
		return -1;
	}
	public void move(ArrayList plats, ArrayList jumpers, int time){
		if(yVelocity <=0){
			falling = false;
		}
		else if(yVelocity>0){
			falling = true;
		}
		if(time == 1 || time  == 100 || time == 200){
			attack();
		}
		y += yVelocity;
		x += xVelocity;
		yVelocity += yAccel;
		for(int j = 0; j < jumpers.size(); j++){
			if(intersectWPlatform(x,y,plats) != -1 && falling){
				if(y<((Platform) plats.get(platCollide)).getY((Platform) plats.get(platCollide))){
					yVelocity = 0;
					xVelocity = -5;
					for(int i = 0; i < jumpers.size(); i++){
						y = ((Platform)plats.get(intersectWPlatform(x,y,plats))).getY((Platform)plats.get(intersectWPlatform(x,y,plats)));
						y -= sizeY;
					}
				}
				
			}
			else{
				if(y+sizeY>571){
					yVelocity = 0;
					xVelocity = 0;
					y = 571 - sizeY;
				}
				
				if(yVelocity>40){
					yVelocity = 40;
				}
			}
		}
		
		
		
		
	}
	public int getJX(Jumper jump){
		x = jump.x;
		return x;
	}
	public int getJY(Jumper jump){
		y = jump.y;
		return y;
	}
	public void attack(){
		yVelocity = -40;
		xVelocity = -10;
	}
}
