package com.fac.main;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Bullet {
	int x;
	int y;
	int sizeX = 20;
	int sizeY = 5;
	int direction = 1;
	int speed = 10;
	int bullCollide;
	Shooter shoot = new Shooter();
	public Bullet(){
		
	}
	public Bullet(int posX, int posY, int direct){
		x = posX;
		y = posY;
		direction = direct;
	}
	public void paint(Graphics g){
		g.setColor(Color.RED);
		g.fillOval(x, y, sizeX, sizeY);
	}
	public void move(){
		
		if(direction == 0){
			x+=speed;
		}
		else if(direction ==1){
			x-=speed;
		}
		else if(direction ==2){
			y+=speed;
		}
		else if(direction ==3){
			y-=speed;
		}
	}
	public void setX(int newX){
		x = newX;
	}
	public void setY(int newY){
		y = shoot.getY();
	}
	public int gettingX(Bullet b){
		x = b.x;
		return b.x;
	}
	public int gettingY(Bullet b){
		y = b.y;
		return y;
	}
	public int intersect(int playerx, int playery, ArrayList bull, int playersize){
		for(int i = 0; i <bull.size();i++){
			if(playerx+playersize>= gettingX((Bullet) bull.get(i))
					&&playerx<=gettingX((Bullet)bull.get(i))+sizeX
					&&playery+playersize>=gettingY((Bullet)bull.get(i))
					&&playery<=gettingY((Bullet)bull.get(i))+sizeY){
					bullCollide = i;
					
				return bullCollide;
			}
		}
		return -1;
	}
}
