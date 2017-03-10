import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Button{
	int x;
	int y;
	int sizeX=200;
	int sizeY=60;
	int mouseX;
	int mouseY;
	int indent = 2;
	String buttonText;
	public void setButton(int posX, int posY, int sizX, int sizY, String text){
		x = posX;
		y = posY;
		sizeX = sizX;
		sizeY = sizY;
		buttonText = text;
	}
	public void paintButton(Graphics g){
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, sizeX, sizeY);
		g.setColor(Color.GRAY);
		g.fillRect(x+indent, y+indent, sizeX-2*indent, sizeY - 2*indent);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Impact", Font.BOLD, 45));
		g.drawString(buttonText, x+sizeX/4, y+sizeY-10);
	}
	public int clicked(ArrayList buttons, int mousePosX, int mousePosY){
		for(int i = 0; i<buttons.size();i++){
			if(mousePosX-4 >= getButtonX((Button) buttons.get(i)) 
					&& mousePosX-4 <= getButtonX((Button) buttons.get(i))+sizeX 
					&& mousePosY-28 >= getButtonY((Button) buttons.get(i)) 
					&& mousePosY-28 <= getButtonY((Button) buttons.get(i))+sizeY){
				System.out.println("YAY I DID IT");
				return i;
			}
		}
		System.out.println("No button clicked");
		return -1;
	}
	public int getButtonX(Button b){
		x = b.x;
		return x;
	}
	public int getButtonY(Button b){
		y = b.y;
		return y;
	}
}
