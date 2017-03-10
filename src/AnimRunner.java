import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

public class AnimRunner extends JComponent implements ActionListener, MouseListener, KeyListener{
	
	//DONE Fix platform spawning
	//DONE Add jetpack fuel
	//DONE Add death
	//TODO Program the game
	//TODO Add enemies?
	//TODO Make the player shoot?
	//DONE Make the game start automagically
	//TODO Add gradient to jetpack bar
	//TODO Make the platforms speed up
	//TODO Fix the bug where the player flies up in the air after the jetpack runs out.
	//TODO Instead turn it into a feature. The jetpack breaks if you over heat it, and then you can't use it.
	//TODO Add textures for player, background, platforms, etc.
	//DONE Add restarting/new game
	//TODO Add color choice
	//TODO Add Start Menu
	//TODO Add Buttons
	//TODO Add Settings
	
	static int winHeight;
	static int winWidth;
	int mousePosX;
	int mousePosY;
	int playerX = 150;
	int playerY = -70;
	int colorChoice = 1;
	int playerSize = 61;
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
	int boxX = 0;
	int boxY = 0;
	int objects = -1;
	int smokeSize = 10;
	int jumpsLeft = 1;
	int platNum = -1;
	int randPlatX = 500;
	int randPlatY = 300;
	int platSizeX = 100;
	int platSizeY = 40;
	int startPlatX = 800;
	int startPlatY = 0;
	int scoreTimer = 0;
	int score =0;
	int jetFuel =100;
	int startPlatColor = 255;
	boolean startSpawnDone = false;
	int newPlatX;
	int newPlatY;
	boolean dead = false;
	int deathAnimationCount = 0;
	int buttNum = -1;
	boolean inMenu = true;
	
	int vX = 10;
	int vY = 10; 
	Image playerTexture;
	Image explosionTexture;
	
	ArrayList<Spawn> s = new ArrayList<Spawn>();
	ArrayList<Platform> p = new ArrayList<Platform>();
	ArrayList<Button> b = new ArrayList<Button>();
	Button buttons = new Button();
	Platform plats = new Platform();
	
	public static void main(String[] args) {
		JFrame window = new JFrame();
		AnimRunner animation = new AnimRunner();
		
		window.add(animation);
		window.setSize(800, 600);
		winHeight = window.getHeight();
		winWidth = window.getWidth();
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBackground(Color.white);
		
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
		
		//literately
		if(colorChoice == 1){
			try{
				playerTexture = ImageIO.read(new File("C:/Users/imor1471/Downloads/Transparentrobot.gif"));
			}
			catch(IOException e){
				e.printStackTrace();
			}
			g.drawImage(playerTexture, playerX-8, playerY-31, null);
			//g.setColor(Color.GREEN);
			//g.fillRect(playerX-8, playerY-31, playerSize,playerSize);
		}
		else if(colorChoice == 2){
			try{
				explosionTexture = ImageIO.read(new File("C:/Users/imor1471/Downloads/explosion.png"));
			}
			catch(IOException e){
				e.printStackTrace();
			}
			//Doesn't work //g.drawImage(explosionTexture, playerX-explosionTexture.getWidth(null)/2, playerY-explosionTexture.getHeight(null), 50, 50, null);
			g.drawImage(explosionTexture, playerX-explosionTexture.getWidth(null)/2, playerY-explosionTexture.getHeight(null), null);
			//g.setColor(Color.RED);
			//g.fillRect(playerX-8, playerY-31, playerSize,playerSize);
		}
		else if(colorChoice == 3){
			g.setColor(Color.BLUE);
			g.fillRect(playerX-8, playerY-31, playerSize,playerSize);
		}
		else{}
		if(inMenu == false){
		g.setColor(Color.BLACK);
		g.setFont(new Font("Calibri", Font.BOLD, 20));
		g.drawString("Score: " + score, 15, 30);
		g.fillRect(15,40,110,20);
		g.setColor(Color.RED);
		g.fillRect(20,45,jetFuel,10);
		}
		else{}
		if(startPlatColor<255){
			g.setColor(new Color(startPlatColor, startPlatColor, startPlatColor));
			g.fillRect(100, 400, 120, 30);
		}
		if(deathAnimationCount>300){
			g.setColor(Color.BLACK);
			g.fillRect(0,0,800,600);
		}
		if(p.size()>=0){
			for(int i = 0; i<=platNum; i++){
				p.get(i).paintRect(g);
				p.get(i).move();
			}
		}
		if(dead==true){
			g.setColor(Color.white);
			g.setFont(new Font("Impact", Font.PLAIN, 100));
			g.drawString("GAME OVER", 175, 300);
			g.setFont(new Font("Impact", Font.PLAIN, 30));
			g.drawString("Score: " + score, 350, 350);
			g.setFont(new Font("Impact", Font.PLAIN, 20));
			g.drawString("Restart? Press \"R\"", 325, 380);
		}
		if(b.size()>=0){
			for(int i = 0; i<=buttNum; i++){
				b.get(i).paintButton(g);
			}
		}
		
	}

	public void actionPerformed(ActionEvent e) {
		if(inMenu == false){
			if(dead == false){
				for(int i = 0; i<= yVelocity; i++){
					for(int o = 0; o<plats.sizeX; o++){
						if(plats.intersect(playerX-8, playerY-31, p)!=-1){
							yVelocity = 0;
						}
					}
					if(startPlatColor<255){
						if(playerX+20>=100 && playerX<=220 && playerY+40> 400 && playerY<430){
							glide = false;
							space = false;
							yVelocity = 0;
							playerY = 391;
							jumpsLeft = 1;
						}
					}
				}
				
				if(startPlatColor<255)
					startPlatColor+=2;
				
				scoreTimer++;
				score = scoreTimer/100;
				playerY+=yVelocity;
				yVelocity += accel;
				
				if(yVelocity>40){
					yVelocity =40;
				}
				if(playerY+playerSize > winHeight){
					//yVelocity = -yVelocity;
					yVelocity = 0;
					playerY = 563;
					glide = false;
					space = false;
					jumpsLeft = 1;
					dead= true;
				}
				if(right == true){
					playerX += 10;
				}
				else if(left == true){
					playerX -= 10;
				}
				if(glide == true && jetFuel >0){
					if(yVelocity <= -10){
						yVelocity = -10;
					}
			//	yVelocity = -2;
					jetFuel--;
					if(jetFuel <= 0){
						jetFuel =0;
						glide = false;
					}
					accel = -3;
				}
				if(space == true){
					s.add(new Spawn());
					objects++;
					boxX =playerX;
					boxY =playerY;
					s.get(objects).setSize(boxX+playerSize/2-(smokeSize/2), boxY, smokeSize, smokeSize);
					if(objects >= 150){
						s.remove(0);
						objects--;
					}
					else{
						
					}
				}
				if(plats.intersect(playerX-8,playerY-31,p)!= -1){
					yVelocity = 0;
					glide = false;
					space = false;
					jumpsLeft = 1;
					playerX-=5;
					jetFuel++;
					if(jetFuel>=100){
						jetFuel = 100;
					}
					if(plats.intersect(playerX-8, playerY-31, p)!=-1){
						playerY = plats.getY(p.get(plats.intersect(playerX-8,playerY-31,p)))-9;
					}
				}
				for(int i = 0; i < p.size(); i++){
					if(plats.getX(p.get(i))<=-100){
						p.remove(i);
						platNum--;
						
						randPlatX = (int)(Math.random()*600)+50;
						randPlatY = (int)(Math.random()*450)+50;
						platNum++;
						p.add(new Platform());
						p.get(platNum).setSize(startPlatX+randPlatX, startPlatY+randPlatY, platSizeX, platSizeY);
						
					}
				}
				if(startSpawnDone == false){
					for(int i =0; i<6;i++){
						randPlatX = (int)(Math.random()*600)+50;
						randPlatY = (int)(Math.random()*580)+0;
						platNum++;
						p.add(new Platform());
						p.get(platNum).setSize(startPlatX+randPlatX, startPlatY+randPlatY, platSizeX, platSizeY);
					}
					startSpawnDone = true;
				}
			}
			if(dead == true){
				colorChoice = 2;	
				if(platNum>0){
					for(int i =0; i<8;i++){
						randPlatX = (int)(Math.random()*600)+50;
						randPlatY = (int)(Math.random()*590)-10;
						platNum++;
						p.add(new Platform());
						p.get(platNum).setSize(startPlatX+randPlatX, startPlatY+randPlatY, platSizeX, platSizeY);
					}
					deathAnimationCount++;
				}
			}
			
			}
		
		
		else if(inMenu == true){
			buttNum++;
			b.add(new Button());
			b.get(buttNum).setButton(150, 350, 200, 60,"Play");
			
			buttNum++;
			b.add(new Button());
			b.get(buttNum).setButton(450, 350, 200, 60,"Exit");	
		}
			
			repaint();
		}
	
	public void mousePressed(MouseEvent e){
		yVelocity = 0;
		colorChoice = e.getButton();
		//playerX = e.getX();
		//playerY = e. getY();
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
			randPlatY = (int)(Math.random()*580)+0;
			platNum++;
			p.add(new Platform());
			p.get(platNum).setSize(startPlatX+randPlatX, startPlatY+randPlatY, platSizeX, platSizeY);
			if(p.size()>10){
				p.remove(0);
				platNum--;
			}
		}
		if(keyPress == arg0.VK_R){
			for(int i = p.size(); i > 0; i--){
				platNum--;
				p.remove(i-1);
			}
			dead = false;
			playerX = 150;
			playerY = -20;
			colorChoice = 1;
			startPlatColor=0;
			startSpawnDone = false;
			deathAnimationCount=0;
			score = 0;
			scoreTimer = 0;
			jetFuel = 100;
		}
		if(keyPress == arg0.VK_B){
			inMenu = true;
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
			inMenu = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//mouseButton = e.getButton();
		mousePosX = e.getX();
		mousePosY = e.getY();
		if(buttons.clicked(b, mousePosX, mousePosY) == 0){
			inMenu = false;
			startPlatColor = 0;
			for(int i = b.size()-1; i>-1;i--){
				b.remove(i);
				buttNum--;
			}
		}
		else if(buttons.clicked(b, mousePosX, mousePosY) == 1){
			System.exit(0);
		}
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
