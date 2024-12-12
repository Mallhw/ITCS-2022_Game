//required import statements
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.awt.geom.AffineTransform;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import java.util.ArrayList; // import the ArrayList class
import java.util.HashSet;


@SuppressWarnings("serial")
//TODO: Change the name of the class from AnimationShell to the name of your class
public class ProtoGame extends JPanel {

	//TODO: set the initial width and height of your image
	private static final int WIDTH = 1400;
	private static final int HEIGHT = 800;

	//required global variables
	private BufferedImage image;
	private Graphics g;
	private Timer timer;
	private double deltaTime;
	private double t;
	private Random rand = new Random();
	private Color[] colors;
	private Object player1;
	private Object player2;
	private Platform[] platform;
	private Ground ground;
	private HashSet<Integer> keysPressed = new HashSet<>();
	private HashSet<Integer> keysReleased = new HashSet<>();




	//Constructor required by BufferedImage
	public ProtoGame() {
		//set up Buffered Image and Graphics objects
		image =  new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = image.getGraphics();		
		deltaTime = 1/600.0;
		t = 0;
		platform = new Platform[3];
		player1 = new Object(400, 200, 40, 20, 25, 1, 1, new Color(225, 204, 229));
		player2 = new Object(1000, 200, 40, 20, 25, 1, -1, Color.orange);
		platform[0] = new Platform(WIDTH/4, HEIGHT/3, 80, 10, Color.gray);
		platform[1] = new Platform(WIDTH/2, HEIGHT/3, 80, 10, Color.gray);
		platform[2] = new Platform(WIDTH/3, HEIGHT/4, 80, 10, Color.gray);

		
		ground = new Ground(400, HEIGHT/2, 600, HEIGHT, Color.black);
		
		//set up and start the Timer
		timer = new Timer(5, new TimerListener());
		timer.start();
		addKeyListener(new Keyboard()); //new code
		setFocusable(true); //new code

	}
	
	private class Keyboard implements KeyListener {
		
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			keysPressed.add(e.getKeyCode());
			char c = e.getKeyChar();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			keysPressed.remove(e.getKeyCode());
			keysReleased.add(e.getKeyCode());
			

		}
		
	}
	
	//TimerListener class that is called repeatedly by the timer
	private class TimerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			deltaTime = 0.1;

			
			
			//Hashmap were with collabration with Taha 
			// Player 1 Controls
			// ****************************
			
			if(keysPressed.contains(KeyEvent.VK_E)) {
				player1.playerAngleChange(7);
				player1.powerSetter(1);
			}
			if(keysReleased.contains(KeyEvent.VK_E)) {
				player1.jumpMechanic(ground, 1);
				player1.setPower(0);
				player1.setColor(Color.pink);
				keysReleased.remove(KeyEvent.VK_E);
			}

			
			
			if(keysPressed.contains(KeyEvent.VK_R)) {
				player1.shoot(g);
			}

			// Player 2 controls
			
			//*********************************
			
			if(keysPressed.contains(KeyEvent.VK_I)) {
				player2.playerAngleChange(7);
				player2.powerSetter(1);
			}
			
			if(keysReleased.contains(KeyEvent.VK_I)) {
				player2.jumpMechanic(ground, 1);
				player2.setPower(0);
				player2.setColor(Color.orange);
				keysReleased.remove(KeyEvent.VK_I);
			}
			if(keysPressed.contains(KeyEvent.VK_O)) {
				player2.playerAngleChange(-7);
				player2.powerSetter(1);
			}
			
			if(keysReleased.contains(KeyEvent.VK_O)) {
				player2.jumpMechanic(ground, 1);
				player2.setPower(0);
				player2.setColor(Color.orange);
				keysReleased.remove(KeyEvent.VK_O);
			}


			
			if(keysPressed.contains(KeyEvent.VK_W)) {
				player1.setGunAngle(player1.getGunAngle() + 1.5);
			}
			if(keysReleased.contains(KeyEvent.VK_W)) {
				player1.shoot(g);
				keysReleased.remove(KeyEvent.VK_W);
				player1.setGunAngle(30);
			}
			
			g.setColor(Color.white);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			
			player1.getRotatedCoords();
			player2.getRotatedCoords();

			// Caclulate the Physics
			player1.resolveCollision(player2); // only need one object to calculate since bothare set

			if(player1.isJumping()) {
				System.out.println("Ground hit");
			}
			if(player2.isJumping()) {
			}
			player1.updatePosition(deltaTime);
			player2.updatePosition(deltaTime);
			




			// Apply constraints
			player1.applyGravity();
			player2.applyGravity();
			/*
			for(int i = 0; i < platform.length; i++) {
				if(player1.checkSATPlatform(platform[i])) {
					if(player1.getLowestPoint() < platform[i].getY() + platform[i].getHeight()) {
						player1.setYAcceleration(0);
						player1.applyPlatformConstraints(platform[i]);
					}
				}
			}
				
			for(int i = 0; i < platform.length; i++) {
				if(player2.checkSATPlatform(platform[i])) {
					player2.setYAcceleration(0);
					player2.applyPlatformConstraints(platform[i]);
				}
			}
			*/
			if(player1.checkSATGround(ground)) {
				player1.setYAcceleration(0);
				player1.applyConstraints2(ground);

				//player1.setXVel(player1.getXVel()/2);
			}
			if(player2.checkSATGround(ground) ) {
				player2.setYAcceleration(0);
				player2.setYAcceleration(0);

				player2.applyConstraints2(ground);

				//player2.setXVel(player2.getXVel()/2);

			}
			
			
			
			player1.jumpBool(false);
			player2.jumpBool(false);


			// Out of bounds rule
			if(player1.outOfBounds(WIDTH, HEIGHT)) {
				player1.setLives(player1.getLives() - 1);
				player1.setX(200);
				player1.setY(200);
				player1.setXVel(0);
				player1.setYVel(0);

			}
			if(player2.outOfBounds(WIDTH, HEIGHT)) {
				player2.setLives(player2.getLives() - 1);
				player2.setX(400);
				player2.setY(200);
				player2.setXVel(0);
				player2.setYVel(0);

			}
			
			
			
			
			// Drawing
			player1.drawLives(200, g);
			player2.drawLives(400, g);
			ground.draw(g);
			player1.draw2(g);
			player2.draw2(g);
			for(int i = 0; i < platform.length; i++) {
				//platform[i].draw(g);
			}


			
			repaint(); //leave this alone, it MUST  be the last thing in this method
		}
		
	}

	//do not modify this
	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	}

	//main method with standard graphics code
	public static void main(String[] args) {
		JFrame frame = new JFrame("Animation Shell");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocation(0, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new ProtoGame()); 
		frame.setVisible(true);
	}

}