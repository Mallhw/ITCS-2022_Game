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
public class TestRigidBodyCollisonAngular extends JPanel {

	//TODO: set the initial width and height of your image
	private static final int WIDTH = 800;
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
	private HashSet<Integer> keysPressed = new HashSet<>();
	private HashSet<Integer> keysReleased = new HashSet<>();




	//Constructor required by BufferedImage
	public TestRigidBodyCollisonAngular() {
		//set up Buffered Image and Graphics objects
		image =  new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = image.getGraphics();		
		deltaTime = 1/600.0;
		t = 0;
		
		player1 = new Object(200, 200, 40, 20, 25, 1, 1, new Color(225, 204, 229));
		player2 = new Object(400, 200, 40, 20, 25, 1, -1, Color.orange);

		
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
				player2.setPower(0);
				player2.setColor(Color.orange);
				keysReleased.remove(KeyEvent.VK_I);
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
			
			player1.updatePosition(deltaTime);
			player2.updatePosition(deltaTime);

			
			
			
			// Drawing
		
			player1.drawLives(200, g);
			player2.drawLives(400, g);
			player1.draw2(g);
			player2.draw2(g);


			
			repaint(); //leave this alone, it MUST  be the last thing in this method
		}
		
	}

	//do not modify this
	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	}

	//main method with standard graphics code
	public static void main(String[] args) {
		JFrame frame = new JFrame("TesRigidBodyCollison");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocation(0, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new TestRigidBodyCollisonAngular()); 
		frame.setVisible(true);
	}

}