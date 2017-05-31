package com.fac.main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

public class AnimRunner extends JComponent implements ActionListener, MouseListener, KeyListener{

	//DONE Fix platform spawning
	//DONE Add jetpack fuel
	//DONE Add death
	//DONE Program the game
	//DONE Add enemies?
	//NOPE Make the player shoot?
	//DONE Make the game start automagically
	//NOPE Add gradient to jetpack bar
	//NOPE Make the platforms speed up
	//DONE Fix the bug where the player flies up in the air after the jetpack runs out.
	//NOPE Instead turn it into a feature. The jetpack breaks if you over heat it, and then you can't use it.
	//DONE Add textures for player, background, platforms, etc.
	//DONE Add restarting/new game
	//NOPE Add color choice
	//DONE Add Start Menu
	//DONE Add Buttons
	//NOPE Add Settings
	//DONE Add Title
	//DONE Fix bullet bug
	//DONE Make background work
	//DONE Jumper enemy?
	//DONE Highscores?
	//NOPE Maybe acceleration based horizontal movement?
	
	//RELEASE DATE: April 27th, 2017
	
	//POST RELEASE TODO LIST
	//DONE Add jumper enemy
	//DONE Add jumper texture
	//TODO Add waves of jumpers
	//NOPE Add shooters from other directions?
	//TODO Add shooting, but it takes up your fuel
	//NOPE Add new obstacles, such as walls
	//TODO Add a boss
	//TODO Add sound
	//TODO Add scaling for full screen/different screen sizes
	//DONE Fix platform collision
	//TODO Finish adding sound effects
	//TODO Get music to loop
	//TODO Add separate sound effect mute button
	
	static int winHeight;
	static int winWidth;
	int mousePosX;
	int mousePosY;
	int playerX = 150;
	int playerY = -70;
	boolean playerFalling;
	int colorChoice = 1;
	static int playerSize = 40;
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
	int scoreTimer, attackTimer, globalTimer = 0;
	int score = 0;
	int jetFuel =100;
	int startPlatColor = 255;
	boolean startSpawnDone = false;
	int newPlatX;
	int newPlatY;
	boolean dead = false;
	int deathAnimationCount = 0;
	int buttNum = -1;
	boolean inMenu = true;
	int enemyNum = -1;
	int bulletCount = -1;
	int jumperCount = -1;
	int backgroundPosX = 0;
	int backgroundPosY = -10;
	int backgroundPosX2 = 1529;
	int backgroundPosY2 = -10;
	int bulletSpawnPos;
	boolean startMenu = true;
	boolean writtenOnce = false;
	boolean madeFile = false;
	boolean jumperWarning = false;
	int jumperWarningTimer = 0;
	boolean musicStart;
	int numOfLoops = 1;
	boolean musicMuted = true;
	
	static Image playerTexture;
	static Image explosionTexture;
	static Image platformTexture;
	static Image shooterTexture;
	static Image background1;
	static Image background2;
	static Image titleText;
	static Image jumperTexture;
	static Image musicTexture;
	static Image musicMutedTexture;
	
	ArrayList<Spawn> smoke = new ArrayList<Spawn>();
	ArrayList<Platform> platforms = new ArrayList<Platform>();
	ArrayList<Button> buttons = new ArrayList<Button>();
	ArrayList<Enemy> enemy = new ArrayList<Enemy>();
	ArrayList<Shooter> shooters = new ArrayList<Shooter>();
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	ArrayList<Jumper> jumpers = new ArrayList<Jumper>();
	
	Button button = new Button();
	Platform platform = new Platform();
	Shooter shooter = new Shooter();
	Bullet bullet = new Bullet();
	Jumper jumper = new Jumper();
	
	static Sound music = new Sound();
	static Sound laser = new Sound();
	static Sound explosion = new Sound();
	static Sound jetpack = new Sound();
	
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
		window.setTitle("Robot Factory 2");
		
		
		Timer t = new Timer(20,animation);
		t.start();
		
		music.setClip("8-bit-music-loop.wav");
		//laser.setClip("");
		explosion.setClip("Explosion+1.wav");
		//jetpack.setClip("");
		
		try{
			background1 = ImageIO.read(AnimRunner.class.getResource("/imgs/factorybackground.png"));
			background2 = ImageIO.read(AnimRunner.class.getResource("/imgs/factorybackgroundbackwards.png"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		try{
			playerTexture = ImageIO.read(AnimRunner.class.getResource("/imgs/Transparentrobot.gif"));
			playerSize = playerTexture.getHeight(null);
			window.setIconImage(playerTexture);
		}
		catch(IOException e){
			e.printStackTrace();
			playerSize = 61;
		}
		try{
			musicMutedTexture = ImageIO.read(AnimRunner.class.getResource("/imgs/soundIconWCrossNoBackground.png"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		try{
			musicTexture = ImageIO.read(AnimRunner.class.getResource("/imgs/soundIconNoBackground.png"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		try{
				explosionTexture = ImageIO.read(AnimRunner.class.getResource("/imgs/explosion.png"));
			}
		catch(IOException e){
				e.printStackTrace();
			}
		
		try{
			platformTexture = ImageIO.read(AnimRunner.class.getResource("/imgs/newplatformnobackground.png"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		try{
			jumperTexture = ImageIO.read(AnimRunner.class.getResource("/imgs/jumpertexturenobackground.png"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		try{
			shooterTexture = ImageIO.read(AnimRunner.class.getResource("/imgs/shooternobackground.png"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		try{
			titleText = ImageIO.read(AnimRunner.class.getResource("/imgs/FinishedTitle.png"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		window.addMouseListener(animation);
		window.addKeyListener(animation);
	}
	
	public void paint(Graphics g){
		
		g.drawImage(background1,backgroundPosX,backgroundPosY, null);
		g.drawImage(background2,backgroundPosX2,backgroundPosY2, null);
		if(startMenu == true){
			g.drawImage(titleText, 200, 100, null);
		}
		
		if(inMenu == false){
			g.setColor(Color.BLACK);
			g.setFont(new Font("Calibri", Font.BOLD, 20));
			g.drawString("Score: " + score, 15, 30);
			g.fillRect(15,40,110,20);
			g.setColor(Color.RED);
			g.fillRect(20,45,jetFuel,10);
			
			g.setColor(new Color(150,0,0,jumperWarningTimer));
			g.setFont(new Font("Calibri", Font.BOLD, jumperWarningTimer/5));
			g.drawString("JUMPERS INCOMING!", 360 - (jumperWarningTimer/2), 70 - (jumperWarningTimer/20));
			if(jumperWarning){
				if(jumperWarningTimer < 254)
					jumperWarningTimer += 2;
				if(jumperWarningTimer >=254){
					jumperWarning = false;
				}
			}
			else if(jumperWarning == false){
				if(jumperWarningTimer >0)
					jumperWarningTimer -= 2;
			}	
		}
		else{
			g.setColor(Color.darkGray);
			g.fillRect(0, 457, 147, 122);
			g.setColor(Color.gray);
			g.fillRect(0, 459, 145, 120);
			g.setColor(Color.black);
			g.setFont(new Font("Calibri", Font.BOLD, 20));
			int controlX = 10;
			int controlY = 480;
			int increment = 21;
			g.drawString("Controls:", controlX, controlY);
			g.drawString("A/D: Left/Right", controlX, controlY + increment);
			g.drawString("Space: Jump", controlX, controlY + 2*increment);
			g.drawString("W: Jetpack", controlX, controlY + 3*increment);
			g.drawString("P: Pause", controlX, controlY + 4*increment);
			g.setColor(new Color(140,140,140));
			g.setFont(new Font("Calibri",Font.PLAIN,9));
			g.drawString("R.I.P. Level Select", 0, 570);
		}
		
		if(startPlatColor<255){
			g.setColor(new Color(startPlatColor, startPlatColor, startPlatColor));
			g.setColor(new Color(startPlatColor, startPlatColor, startPlatColor, 255-startPlatColor));
			g.fillRect(100, 400, 120, 30);
		}
		//DRAWING SMOKE
		if(objects>=0){
			for(int i = 0; i<=objects; i++){
				smoke.get(i).paintRect(g);
				if(inMenu == false){
					smoke.get(i).move();
				}
				
			}
		}
		//DRAWING THE PLAYER
		if(colorChoice == 1){
			
			g.drawImage(playerTexture, playerX, playerY, null);
			if(playerTexture == null){
				g.setColor(Color.GREEN);
				g.fillRect(playerX, playerY, playerSize,playerSize);
			}
		}
		else if(colorChoice == 2){
			
			if(explosionTexture == null){
				g.setColor(Color.RED);
				g.fillRect(playerX, playerY, playerSize,playerSize);
			}
			else{
				g.drawImage(explosionTexture, playerX-explosionTexture.getWidth(null)/2, playerY-explosionTexture.getHeight(null)+explosionTexture.getHeight(null)/4, null);
			}
		}
		else if(colorChoice == 3){
			g.setColor(Color.BLUE);
			g.fillRect(playerX, playerY, playerSize,playerSize);
		}
		if(deathAnimationCount>300){
			g.setColor(Color.BLACK);
			g.fillRect(0,0,800,600);
		}
		//DRAWING PLATFORMS
		if(platforms.size()>=0){
			for(int i = 0; i<=platNum; i++){
				platforms.get(i).paintRect(g, platformTexture);
				if(inMenu == false){
					platforms.get(i).move();
				}
			}
		}
		
		//DRAWING ENEMIES
		if(enemy.size()>=0){
			for(int i = 0; i <= enemyNum; i++){
				enemy.get(i).paint(g);
				if(inMenu == false){
					enemy.get(i).move();
				}
			}
		}
		//DRAWING SHOOTERS
		if(shooters.size() >= 0){
			for(int i = 0; i <= shooters.size()-1; i++){
				shooters.get(i).paint(g, shooterTexture);
				if(inMenu == false){
					shooters.get(i).move();
				}
			}
		}
		//DRAWING BULLETS
		if(bullets.size()>=0){
			for(int i = 0; i <= bulletCount; i++){
				 bullets.get(i).paint(g);
				 if(inMenu == false){
					 bullets.get(i).move();
				 }
			}
		}
		//DRAWING JUMPERS
		if(jumpers.size()>=0){
			for(int i = 0; i<= jumperCount; i++){
				jumpers.get(i).paint(g, jumperTexture);
				if(inMenu == false){
					jumpers.get(i).move(platforms, jumpers, attackTimer);
				}
			}
		}
		//DRAWING BUTTONS
		if(buttons.size()>0 && inMenu){
			if(startMenu == false && inMenu == true){
				g.setColor(Color.white);
				g.setFont(new Font("Impact", Font.PLAIN, 100));
				g.drawString("Paused", 245, 200);
			}
			
			buttons.get(0).paintButton(g);
			buttons.get(1).paintButton(g);
			
			if(musicMuted){
				buttons.get(2).paintButton(g,musicMutedTexture);
			}
			else{
				buttons.get(2).paintButton(g,musicTexture);
			}			
		}
		if(dead==true){
			g.setColor(Color.white);
			g.setFont(new Font("Impact", Font.PLAIN, 100));
			g.drawString("GAME OVER", 175, 300);
			g.setFont(new Font("Impact", Font.PLAIN, 30));
			g.drawString("Score: " + score, 350, 350);
			g.setFont(new Font("Impact", Font.PLAIN, 20));
			g.drawString("Restart? Press \"R\"", 325, 410);
			try {
				g.setFont(new Font("Impact", Font.PLAIN, 20));
				g.drawString("Highscore: " + getHighScore(), 350, 375);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		globalTimer++;
		
		if(musicMuted == false){
			if(musicStart == false){
				music.play(true);
				musicStart = true;
			}
		}
		if(globalTimer/200 == numOfLoops){
			if(!musicMuted){
				music.stop();
				musicStart = false;
				numOfLoops++;
			}
		}
		if(inMenu == false){
			
			//BACKGROUND MOVEMENT
			backgroundPosX -=1;
			backgroundPosX2 -=1;
			if(backgroundPosX <= -1529){
				backgroundPosX = 1529;
			}
			if(backgroundPosX2 <= -1529){
				backgroundPosX2 = 1529;
			}
			//THE GAME
			if(dead == false){		
				//MAKING SURE THE HIGHSCORE IS ONLY WRITTEN ONCE
				writtenOnce = false;
				
				//REMOVING SMOKE
				if(startPlatColor == 0){
					if(smoke.size() >=0){
						for(int i = smoke.size()-1; i >0; i--){
							smoke.remove(i);
							objects--;
						}
					}
				}
				
				//ENEMY MOVEMENT AND ATTACK
				attackTimer++;
				if(attackTimer == 300){
					if(shooters.size()>=0){
						for(int i = 0; i < shooters.size(); i++){
							
							bulletSpawnPos = shooters.get(i).getY();
							bullets.add(new Bullet(shooters.get(i).getX()+(int)((Math.random()*10)-5), bulletSpawnPos, 1));
							bulletCount++;
							
						}
						attackTimer = 0;
					}
				}
				if(score == 10){
					jumperWarning = true;
				}
				if(score>9){
					if(attackTimer == 1 || attackTimer == 100 || attackTimer == 200){
						jumpers.add(new Jumper(800,600,50,25));
						jumperCount++;
					}
				}
				
				if(attackTimer == 100){
					enemy.add(new Shooter(750,50,20,20));
					shooters.add(new Shooter(750,50,20,20));
					enemyNum++;
				}
				
				//STARTING PLATFORM
				if(startPlatColor<255)
					startPlatColor+=2;
				
				//SCORE TIMER
				scoreTimer++;
				score = scoreTimer/100;
				
				
				//PLAYER MOVEMENT
				playerY+=yVelocity;
				yVelocity += accel;
				if(playerY < -600){
					playerY = -599;
					glide = false;
					yVelocity = 0;
					accel = 2; 
				}
				if(yVelocity < 0){
					playerFalling = false;
				}
				else if(yVelocity >= 0){
					playerFalling = true;
				}
				if(yVelocity>40){
					yVelocity =40;
				}
				if(playerY+playerSize > winHeight){
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
				if(space == true){
					/*s.add(new Spawn());
					objects++;
					boxX =playerX;
					boxY =playerY;
					s.get(objects).setSize(boxX+playerSize/2-(smokeSize/2), boxY, smokeSize, smokeSize);
					if(objects >= 150){
						s.remove(0);
						objects--;
					}
					else{
						
					}*/
				}
				if(platform.intersect(playerX,playerY,platforms, playerSize)!= -1 && playerFalling){
					glide = false;
					space = false;
					yVelocity = 0;
					accel = 3;
					jumpsLeft = 1;
					playerX-=5;
					jetFuel++;
					if(jetFuel>=100){
						jetFuel = 100;
					}
					if(platform.intersect(playerX, playerY, platforms, playerSize)!=-1){
						playerY = platform.getY(platforms.get(platform.intersect(playerX,playerY,platforms, playerSize)))-playerSize;
					}
				}
				
				//PLATFORM PLAYER COLLISION
				for(int i = 0; i<= yVelocity; i++){
					for(int o = 0; o<platform.sizeX; o++){
						if(platform.intersect(playerX, playerY, platforms, playerSize)!=-1){
							yVelocity = 0;
						}
					}
					if(startPlatColor<255){
						if(playerX+playerSize/2>=100 && playerX + playerSize/4<=220 && playerY+playerSize>= 400 && playerY<430){
							glide = false;
							space = false;
							accel = 3;
							playerY = 400-playerSize;
							yVelocity = 0;
							jumpsLeft = 1;
						}
					}
				}
				//JETPACK
				if(glide == true){
					jetpack.play(true);
					if(jetFuel >0){
						if(yVelocity <= -10){
							yVelocity = -10;
						}
						jetFuel--;
						if(jetFuel <= 0){
							jetFuel =0;
							glide = false;
						}
						accel = -3;
					}
					if(jetFuel == 0){
						accel = 2;
					}
					smoke.add(new Spawn());
					objects++;
					smoke.get(objects).setSize(playerX+(playerSize/2)+ (int)(Math.random()*10-5), playerY+playerSize, smokeSize, smokeSize);
					if(objects >= 150){
						smoke.remove(0);
						objects--;
					}
					else{
						
					}
				}
				//KILLING THE PLAYER
				if(bullet.intersect(playerX, playerY, bullets, playerSize) != -1){
					dead = true;
				}
				if(jumper.intersect(playerX,playerY,jumpers,playerSize) != -1){
					dead = true;
				}
				
				//PLATFORM, LASER AND JUMPER DELETION
				for(int i = 0; i < platforms.size(); i++){
					if(platform.getX(platforms.get(i))<=-100){
						platforms.remove(i);
						platNum--;
						
						randPlatX = (int)(Math.random()*600)+50;
						randPlatY = (int)(Math.random()*420)+50;
						platNum++;
						platforms.add(new Platform());
						platforms.get(platNum).setSize(startPlatX+randPlatX, startPlatY+randPlatY, platSizeX, platSizeY);
						
					}
				}
				
				for(int i = 0; i< jumpers.size(); i++){
					if(jumpers.get(i).getJX(jumpers.get(i))< -50){
						jumpers.remove(i);
						jumperCount--;
					}
				}
				
				for(int i = 0; i < bullets.size(); i++){
					if(bullets.get(i).gettingX(bullets.get(i))< -50){
						bullets.remove(i);
						bulletCount--;
					}
				}
				
				//STARTING SPAWNING PLATFORMS
				if(startSpawnDone == false){
					for(int i =0; i<6;i++){
						randPlatX = (int)(Math.random()*600)+50;
						randPlatY = (int)(Math.random()*560)+0;
						platNum++;
						platforms.add(new Platform());
						platforms.get(platNum).setSize(startPlatX+randPlatX, startPlatY+randPlatY, platSizeX, platSizeY);
					}
					startSpawnDone = true;
				}
			}
			
			//DEATH
			if(dead == true){
				if(writtenOnce == false){
					explosion.play(false);
					try {
						if(score>getHighScore()){
							setHighScore(score);
						}
					} 
					catch (Exception e1) {
						e1.printStackTrace();
					}
					writtenOnce = true;
				}
				
				colorChoice = 2;	
				if(platNum>0){
					for(int i =0; i<8;i++){
						randPlatX = (int)(Math.random()*600)+50;
						randPlatY = (int)(Math.random()*590)-10;
						platNum++;
						platforms.add(new Platform());
						platforms.get(platNum).setSize(startPlatX+randPlatX, startPlatY+randPlatY, platSizeX, platSizeY);
					}
					deathAnimationCount++;
				}
				for(int i = bullets.size()-1; i>=0; i--){
					bullets.remove(i);
					bulletCount--;
				}
				for(int i = enemy.size()-1; i >=0; i--){
					enemy.remove(i);
					enemyNum--;
				}
				for(int i = shooters.size()-1; i >=0; i--){
					shooters.remove(i);
				}
				for(int i = jumpers.size()-1; i>=0; i--){
					jumpers.remove(i);
					jumperCount--;
				}
			}
			
			}
		
		//MENUS
		else if(inMenu == true){
			buttNum++;
			buttons.add(new Button());
			buttons.get(buttNum).setButton(150, 350, 200, 60,"Play");
			
			buttNum++;
			buttons.add(new Button());
			buttons.get(buttNum).setButton(450, 350, 200, 60,"Exit");	
			
			buttNum++;
			buttons.add(new Button());
			buttons.get(buttNum).setButton(769, 546, 25, 25, "");
		}
			
			repaint();
		}
	
	public void setHighScore(int sc) throws IOException{
		int highscore = sc;
		String location = System.getProperty("user.home");
		location += "\\Documents\\My Games\\Robot Factory 2";
		Boolean success;
		success = (new File(location)).mkdirs();
		location += "\\highscores.dat";
		//System.out.println(location);
		
		FileWriter file = new FileWriter(location);
		BufferedWriter writer = new BufferedWriter(file);
		String high = "" + highscore;
		writer.write(high);
		//System.out.println("new: " + highscore);
		writer.close();
		file.close();
	}
	
	public int getHighScore() throws Exception {
		String location = System.getProperty("user.home");
		location += "\\Documents\\My Games\\Robot Factory 2";
		Boolean success;
		success = (new File(location)).mkdirs();
		location += "\\highscores.dat";
		String score = "0";
		try{
			BufferedReader pls = new BufferedReader(new FileReader(location));
			score = pls.readLine();
			pls.close();
		}catch (FileNotFoundException e){
			makeHighscoreFile();
		}
		int oldHigh = 0;
		oldHigh = Integer.parseInt(score);

		return oldHigh;
	}
	
	public void makeHighscoreFile() throws IOException {
		String loc = System.getProperty("user.home");
		loc += "\\Documents\\My Games\\Robot Factory 2";
		Boolean success;
		success = (new File(loc)).mkdirs();
		loc += "\\highscores.dat";
		if(!success){
			System.out.println("pls");
		}
		FileWriter file = new FileWriter(loc);
		BufferedWriter write = new BufferedWriter(file);
		write.write("0");
		write.close();
		
	}
	
	public void mousePressed(MouseEvent e){
		//yVelocity = 0;
		//colorChoice = e.getButton();
		//playerX = e.getX();
		//playerY = e. getY();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		keyPress = arg0.getKeyCode();
		
		//System.out.println(keyPress);
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
			if(playerY > -600){
				glide = true;
				space = true;
			}
		}
		if(keyPress == arg0.VK_SPACE){
			if(jumpsLeft>0){
				yVelocity = -30;
			}
			jumpsLeft--;
			
		}
		/*if(keyPress == arg0.VK_0){
			s.remove(s.size()-1);
			objects--;
			boxX-=10;
			boxY-=10;
		}*/
		
		
		/*if(keyPress == arg0.VK_P){
			randPlatX = (int)(Math.random()*600)+50;
			randPlatY = (int)(Math.random()*580)+0;
			platNum++;
			platforms.add(new Platform());
			platforms.get(platNum).setSize(startPlatX+randPlatX, startPlatY+randPlatY, platSizeX, platSizeY);
			if(platforms.size()>10){
				platforms.remove(0);
				platNum--;
			}
		}*/
		if(keyPress == arg0.VK_R){
			if(inMenu == false){
				if(explosion.running()){
					explosion.stop();
				}
				//if(laser.running()){
				//	laser.stop();
				//}
				//if(jetpack.running()){
				//	jetpack.stop();
				//}
				
			
				
				playerX = 150;
				playerY = -20;
				for(int i = platforms.size(); i > 0; i--){
					platforms.remove(i-1);
					platNum--;	
				}
				for(int i = enemy.size(); i > 0; i--){
					enemy.remove(i-1);
					enemyNum--;	
				}
				dead = false;
				colorChoice = 1;
				startPlatColor=0;
				startSpawnDone = false;
				deathAnimationCount=0;
				score = 0;
				scoreTimer = 0;
				jetFuel = 100;
				attackTimer = 0;
			}
		}
		if(keyPress == arg0.VK_P){
			if(dead == false){
				if(inMenu){
					inMenu = false;
					for(int i = buttons.size()-1; i>-1;i--){
						buttons.remove(i);
						buttNum--;
					}
				}
				else{
					inMenu = true;
					startMenu = false;
				}
				
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
			jetpack.stop();
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
		//mouseButton = e.getButton();
		mousePosX = e.getX();
		mousePosY = e.getY();
		if(button.clicked(buttons, mousePosX, mousePosY) == 0){
			if(startMenu){
				inMenu = false;
				startMenu = false;
				startPlatColor = 0;
			}
			else{
				inMenu = false;
				startMenu = false;
			}
			
			for(int i = buttons.size()-1; i>-1;i--){
				buttons.remove(i);
				buttNum--;
			}
		}
		else if(button.clicked(buttons, mousePosX, mousePosY) == 1){
			System.exit(0);
		}
		else if(button.clicked(buttons, mousePosX, mousePosY) == 2){
			if(musicMuted){
				musicMuted = false;
				musicStart = false;
				music.play(true);
			}
			else if(musicMuted == false){
				musicMuted = true;
				music.stop();
			}
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
