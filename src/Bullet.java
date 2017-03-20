import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Bullet {
	int x;
	int y;
	int size = 5;
	int direction;
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
		g.fillOval(x, y, size, size);
	}
	public void move(){
		
		if(direction == 0){
			x+=speed;
		}
		else if(direction ==1){
			x-=speed;
		}
		else if(direction ==1){
			y+=speed;
		}
		else if(direction ==1){
			y-=speed;
		}
	}
	public void setX(int newX){
		x = newX;
	}
	public void setY(int newY){
		y = shoot.getY();
	}
	public int getX(Bullet b){
		x = b.x;
		return b.x;
	}
	public int getY(Bullet b){
		y = b.y;
		return y;
	}
	public int intersect(int playerx, int playery, ArrayList bull, int playersize){
		for(int i = 0; i <bull.size();i++){
			if(playerx+playersize>= getX((Bullet) bull.get(i))
					&&playerx<=getX((Bullet)bull.get(i))+size
					&&playery+playersize>=getY((Bullet)bull.get(i))
					&&playery<=getY((Bullet)bull.get(i))+size){
					bullCollide = i;
				return bullCollide;
			}
		}
		return -1;
	}
}
