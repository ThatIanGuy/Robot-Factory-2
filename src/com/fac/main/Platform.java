package com.fac.main;
import java.awt.Color;
	import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.util.ArrayList;

	import javax.swing.Timer;
	
public class Platform implements ActionListener {
	public int sizeX = 100;
	public int sizeY = 0;
	public int posX = 0;
	public int posY = 0;
	public int vX = 0;
	public int vY = 0;
	public int platCollide = -1;
	int yVelocity = 0;
	int xVelocity = -5;
	int accelY = 0;
	int accelX = 0;
	static Image platformTexture;
	//wAnimRunner b = new AnimRunner();
		
	/*public Platform(int x, int y, int sizX, int sizY){
		posX = x;
		posY = y;
		sizeX = sizX;
		sizeY = sizY;
	}*/
	public int intersect(int playerx, int playery, ArrayList plat, int playersize){
		for(int i = 0; i <plat.size();i++){
			if(playerx+playersize/2>= getX((Platform) plat.get(i))
					&&playerx<=getX((Platform)plat.get(i))+sizeX
					&&playery+playersize>=getY((Platform)plat.get(i))
					&&playery<=getY((Platform)plat.get(i))+playersize){
					platCollide = i;
				return platCollide;
			}
		}
		return -1;
	}
	
	public void setSize(int x, int y, int sizX, int sizY){
		posX = x;
		posY = y;
		sizeX = sizX;
		sizeY = sizY;		
		}
	public int getX(Platform p){
		posX = p.posX;
		return posX;
	}
	public int getY(Platform p){
		posY= p.posY;
		return posY;
	}
	public void paintRect(Graphics g, Image platTex){
		g.drawImage(platTex, posX, posY, null);
		if(platTex == null){
			g.setColor(Color.BLACK);
			g.fillRect(posX,  posY,  sizeX,  sizeY);
		}
	}
	
	/*public void animation(){
		Platform animation = new Platform();
		Timer l = new Timer(20,animation);
		l.start();
	}*/
	public void move(){
		posX += xVelocity;
		/*if(posX == 0){
			xVelocity = 10;
		}
		if(posX == 800){
			xVelocity = -10;
		}*/
	}
	
	public void actionPerformed(ActionEvent e){
		
	}
}