import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


public class Platform {

	// 1. Declaration of Variables
	
	private double x;			//x-coordinate of the center of the ball
	private double y;			//y-coordinate of the center of the ball
	private double width;	//diameter of the ball
	private double height;		//radius of the ball
	private Color color;		//color of the ball

	
	// 2. Create a default constructor
	/**
	 * Default Constructor
	 * Creates a red ball at (0, 0) with a diameter of 25.  
	 * The default speed is 0.
	 */
	
	 
	public Platform() {
		x = 0;
		y = 0;
		color = color.black;
		width = 50;

	}

	public Platform(int ax, int ay, int width, int height, Color color) {
		x = ax;
		y = ay;
		this.width = width;
		this.height = height;
		this.color = color;

	}


	// 4. Create getters and setters for all private variables
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
	public Color getColor() {
		return color;
	}
	
	public double getRX(int num) {
		double[] rx = new double[4];
		rx[0] = x;
		rx[1] = x + width;
		rx[2] = x + width;
		rx[3] = x;
		return rx[num];
	}
	
	public double getRY(int num) {
		double[] ry = new double[4];
		ry[0] = y;
		ry[1] = y;
		ry[2] = y + height;
		ry[3] = y + height;
		return ry[num];
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		int ax = (int) x;
		int ay = (int) y;
		int aWidth = (int) width;
		int aHeight = (int) height;
		g.fillRect(ax, ay, aWidth, aHeight);
		
	}
	
	
	
}