import java.awt.Color;
	import java.awt.Graphics;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.util.ArrayList;

	import javax.swing.Timer;
	
public class Platform implements ActionListener {
	public int sizeX = 0;
	public int sizeY = 0;
	public int posX = 0;
	public int posY = 0;
	public int vX = 0;
	public int vY = 0;
	public int platCollide = -1;
	int yVelocity = 0;
	int xVelocity = -1;
	int accelY = 0;
	int accelX = 0;
	
	//wAnimRunner b = new AnimRunner();
		
	/*public Platform(int x, int y, int sizX, int sizY){
		posX = x;
		posY = y;
		sizeX = sizX;
		sizeY = sizY;
	}*/
	public int intersect(int playerx, int playery, ArrayList plat){
		for(int i = 0; i <plat.size();i++){
			if(playerx+20>= getX((Platform) plat.get(i))&&playerx<=getX((Platform)plat.get(i))+100&&playery+40>=getY((Platform)plat.get(i))&&playery<=getY((Platform)plat.get(i))+40){
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
	public void paintRect(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(posX,  posY,  sizeX,  sizeY);
	}
	
	public void animation(){
		Platform animation = new Platform();
		Timer l = new Timer(20,animation);
		l.start();
	}
	public void move(){
		posX += xVelocity;
		if(posX == 0){
			xVelocity = 10;
		}
		if(posX == 800){
			xVelocity = -10;
		}
	}
	
	public void actionPerformed(ActionEvent e){
		
	}
}