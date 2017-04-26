import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class Shooter extends Enemy{
	int x;
	int y;
	int sizeX;
	int sizeY;
	int newY;
	boolean goingUp;
	//ArrayList<Bullet> bullets = new ArrayList<Bullet>(); 
	
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
	public void paint(Graphics g, Image texture){
		g.drawImage(texture, x, y, null);
		if(texture == null){
			g.setColor(Color.ORANGE);
			g.fillRect(x, y, sizeX, sizeY);
		}
		
		//for(int i = 0; i < bullets.size(); i++){
		//	bullets.get(i).paint(g);
		//}
	}
	public void move(){
		if(goingUp == true){
			y = this.setNewY(-2);
			newY = y;
		}
		else if(goingUp == false){
			y = this.setNewY(2);
			newY = y;
		}
		if(y<50){
			goingUp = false;
		}
		if(y>500){
			goingUp = true;
		}
	}
	public int setNewY(int increment){
		newY = y + increment;
		return newY;
	}
	public void attack(){
		//bullets.add(new Bullet(x, y, 1));
	}
	public int getX(){
		return x;
	}
	public int getY(){
		System.out.println(newY);
		return newY;
	}
}
