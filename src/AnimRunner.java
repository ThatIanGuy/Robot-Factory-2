import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

public class AnimRunner extends JComponent implements ActionListener, MouseListener, KeyListener{

	int mouseX = 0;
	int mouseY = 0;
	int mouseButton = 0;
	int playerSize = 40;
	boolean up = true;
	int yVelocity = 0;
	double accel = 3;
	int keyPress = 0;
	int lastKeyPress = 0;
	boolean isPressed = false;
	boolean left = false;
	boolean right = false;
	boolean glide = false;
	boolean space = false;
	int u =10;
	int boxX = 0;
	int boxY = 0;
	int objects = -1;
	int jumpsLeft = 2;
	int platNum = -1;
	int randPlatX = 500;
	int randPlatY = 300;
	int platSizeX = 100;
	int platSizeY = 40;
	
	int vX = 10;
	int vY = 10; 
	
	ArrayList<Spawn> s = new ArrayList<Spawn>();
	ArrayList<Platform> p = new ArrayList<Platform>();
	Platform plats = new Platform();

	
	public static void main(String[] args) {
		JFrame window = new JFrame();
		AnimRunner animation = new AnimRunner();
		//Platform pls = new Platform();
		
		window.add(animation);
		window.setSize(800, 600);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Timer t = new Timer(20,animation);
		t.start();
		
		
		window.addMouseListener(animation);
		window.addKeyListener(animation);
	}
	
	public void paint(Graphics g){
		
		if(objects>=0){
			for(int i = 0; i<=objects; i++){
				s.get(i).paintRect(g);
				s.get(i).move();
			}
		}
		if(p.size()>=0){
			for(int i = 0; i<=platNum; i++){
				p.get(i).paintRect(g);
				p.get(i).move();
			}
		}
		//literately
		if(mouseButton == 1){
			g.setColor(Color.GREEN);
			g.fillRect(mouseX-8, mouseY-31, playerSize,playerSize);
		}
		else if(mouseButton == 2){
			g.setColor(Color.RED);
			g.fillRect(mouseX-8, mouseY-31, playerSize,playerSize);
		}
		else if(mouseButton == 3){
			g.setColor(Color.BLUE);
			g.fillRect(mouseX-8, mouseY-31, playerSize,playerSize);
		}
		else{}
		
		g.setColor(Color.CYAN);
		g.fillRect(380, 380, 100,40);
		
	}

	public void actionPerformed(ActionEvent e) {
		
		mouseY+=yVelocity;
		yVelocity += accel;
		if(mouseY>=380 && mouseY<=460 && mouseX> 340 && mouseX<480){
			yVelocity = -yVelocity;
			glide = false;
			space = false;
		}
		if(yVelocity>40){
			yVelocity =40;
		}
		if(mouseY+playerSize >= 600){
			//yVelocity = -yVelocity;
			yVelocity = 0;
			mouseY = 563;
			glide = false;
			space = false;
			jumpsLeft = 2;
		}
		if(right == true){
			mouseX += 10;
		}
		else if(left == true){
			mouseX -= 10;
		}
		if(glide == true){
			if(yVelocity <= -10){
				yVelocity = -10;
			}
			//yVelocity = -2;
			accel = -3;
		}
		if(space == true){
			s.add(new Spawn());
			objects++;
			boxX =mouseX;
			boxY =mouseY;
			s.get(objects).setSize(boxX+10, boxY, 10,10);
			if(objects >= 150){
				s.remove(0);
				objects--;
			}
			else{
				
			}
		}
		if(plats.intersect(mouseX-8,mouseY-31,p)!= -1){
			yVelocity = 0;
			glide = false;
			space = false;
			jumpsLeft = 2;
			mouseX -= 1;
			if(plats.intersect(mouseX-8, mouseY-31, p)!=-1){
				mouseY = plats.getY(p.get(plats.intersect(mouseX-8,mouseY-31,p)))-9;
			}
		}
		
		repaint();
	}
	
	public void mousePressed(MouseEvent e){
		yVelocity = 0;
		mouseButton = e.getButton();
		mouseX = e.getX();
		mouseY = e. getY();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		keyPress = arg0.getKeyCode();
		
		System.out.println(keyPress);
		if(keyPress == arg0.VK_D){
			//mouseX += 7;
			right = true;
		}
		else if(keyPress == arg0.VK_A){
			//mouseX -=7;
			left = true;
		}
		if(keyPress == arg0.VK_W){
			//yVelocity = 0;
			glide = true;
			space = true;
		}
		if(keyPress == arg0.VK_SPACE){
			if(jumpsLeft>0){
				yVelocity = -30;
			}
			jumpsLeft--;
			
		}
		if(keyPress == arg0.VK_0){
			s.remove(s.size()-1);
			objects--;
			boxX-=10;
			boxY-=10;
		}
		if(keyPress == arg0.VK_P){
			randPlatX = (int)(Math.random()*600)+50;
			randPlatY = (int)(Math.random()*400)+50;
			platNum++;
			p.add(new Platform());
			p.get(platNum).setSize(randPlatX, randPlatY, platSizeX, platSizeY);
			if(p.size()>4){
				p.remove(0);
				platNum--;
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		lastKeyPress = arg0.getKeyCode();
		if(lastKeyPress == arg0.VK_D){
			right = false;
		}
		else if(lastKeyPress == arg0.VK_A){
			left = false;
		}
		else if(lastKeyPress == arg0.VK_W){
			glide = false;
			space = false;
			accel = 3;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		mouseButton = e.getButton();
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//mouseX = e.getX();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

}
