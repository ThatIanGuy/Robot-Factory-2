import java.awt.Color;
import java.awt.Graphics;

public class Shooter extends Enemy{
	int x;
	int y;
	int sizeX;
	int sizeY;
	int newY;
	boolean goingUp;
	
	public Shooter(){
		
	}
	public Shooter(int posX, int posY, int sizX, int sizY){
		x = posX;
		y = posY;
		sizeX = sizX;
		sizeY = sizY;
	}
	public void setSize(){
		
	}
	public void paint(Graphics g){
		g.setColor(Color.ORANGE);
		g.fillRect(x, y, sizeX, sizeY);
	}
	public void move(){
		if(goingUp == true){
			y-=2;
		}
		else if(goingUp == false){
			y+=2;
		}
		if(y<50){
			goingUp = false;
		}
		if(y>500){
			goingUp = true;
		}
	}
	public void attack(){
		
	}
	public void setY(){
		y = newY;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
}
