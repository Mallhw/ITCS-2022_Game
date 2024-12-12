import java.awt.Color;
import java.util.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.lang.Math;


public class Object {


    // 1. Declaration of Variables


    // Physics engine
    double x, y; // Current Position
    double rp; //  distance vector from center of mass of body A to point P and distance vector from center of mass of body B to point P
    double wi; // inital pre-collison angular velocity
    double wf; // final post-collison angular velocity
    double[] rx = new double[4]; 
    double[] ry = new double[4]; // Rotated Position of each Verticies
    double nx, ny; // New Position
    double height, width; // height and width
    double px, py; // Previous position
    double ax, ay; // Current Acceleration
    double xVel, yVel; // Velocities
    double mass; // Object Mass
    double damping; // damping factor
    double friction;
    Graphics ga;

    
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>(); // - amount
    Color color; // Color of Object
    double angle, angleInc, cr, pr, nr; // Angle of Objects head
    double power;
    double wobbleTime; // Time of the woblling motion
    double jumpCount; // Amounts of jumps
    double gunAngle;
    double lowestRY;
    int lives;
    boolean jumping;
    boolean touchingGround;
    String currentDrawing;

    


    // Constructors

    public Object(double x, double y, double height, double width, double mass, double damping, double angleInc, Color color, int WIDTH, int HEIGHT, BufferedImage bufferedImage, String art) {
        this.x = x;
        this.y = y;
        this.px = x;
        this.py = y;
        this.height = height;
        this.width = width;
        this.ax = 0;
        this.ay = 0;
        this.mass = mass;
        this.damping = damping;
        this.color = color;
        xVel = 0;
        yVel = 0;
        angle = 90;
        this.angleInc = angleInc;
        gunAngle = 30;
        power = 0;
        rx[0] = 0;
        ry[1] = 0;
        rx[0] = 0;
        ry[1] = 0;
        lives = 3;
        bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        currentDrawing = art;
		//retrieve the graphics object from the bufferedImage object
		ga = bufferedImage.getGraphics();
    }





    // 4. Create getters and setters for all private variables
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }

    public double getXVel() {
        return xVel;
    }

    public double getYVel() {
        return yVel;
    }
    
    public double getWidth() {
        return width;
    }
    
    public double getHeight() {
        return height;
    }
    
    public boolean isJumping() {
    	return jumping;
    }
    
    public Color getColor() {
        return color;
    }
    
    public int getLives() {
    	return lives;
    }
    
    public ArrayList<Bullet> getBullets() {
    	return bullets;
    }
    
    public int getBulletSize() {
    	return bullets.size();
    }
    
    public double getGunAngle() {
    	return gunAngle;
    }
    
    public double getRX(int num) {
    	return rx[num];
    }
    
    public double getRY(int num) {
    	return ry[num];
    }
    
    public double getRightX() {
    	double mostRightX = 0;
    	for(int c = 0; c < 4; c++) {
    		if(rx[c] > mostRightX) {
    			mostRightX = rx[c];
    		}
    	}
    	return mostRightX;
    }
    
    public double getLeftX() {
    	double mostLeftX = 10000;
    	for(int c = 0; c < 4; c++) {
    		if(rx[c] < mostLeftX) {
    			mostLeftX = rx[c];
    		}
    	}
    	return mostLeftX;
    }
    
    public double getLowestPoint() {
    	double lowestPoint = 0;
    	for(int i = 0; i < 4; i++) {
    		if(ry[i] > lowestPoint) {
    			lowestPoint = ry[i];
    		}
    	}
    	return lowestPoint;
    }

    
    public double getAngle() {
        return angle;
    }
    
    public double getMass() {
        return mass;
    }
    
    public void setX(double x) {
        this.x = x;
    }
    
    public void setMass(double mass) {
        this.mass = mass;
    }
    
    public void setLives(int lives) {
    	this.lives = lives;
    }
    
    public void setAngle(double angle) {
        this.angle = angle;
    }
    
    public void setPower(double power) {
    	this.power = power;
    }
    
    public void setGunAngle(double gunAngle) {
    	this.gunAngle = gunAngle;
    }
    
    public void setXVel(double xVel) {
        this.xVel = xVel;
    }

    public void setYVel(double yVel) {
        this.yVel = yVel;
    }

    
    public void  setY(double y) {
        this.y = y;
    }
    
    public void setWidth(double width) {
        this.width = width;
    }
    
    public void setJumping(boolean jumping) {
    	this.jumping = jumping;
    }
    
    public void jumpBool(boolean jumping) {
    	this.jumping = jumping;
    }
    
    
    
    public void setHeight(double height) {
        this.height = height;
    }
    
    

    public void setColor(Color color) {
        this.color = color;
    }
    
    
    
    public void setYAcceleration(double yAcceleration) {
        ay = yAcceleration;

    }
    
    public void setXAcceleration(double xAcceleration) {
        ax = xAcceleration;

    }

    
    
    
    // *************** Physics Engine ******************
    
    
        // Update the acceleration of the object
    public void applyGravity() {
        // Compute the acceleration based on external forces (e.g. gravity, friction)
        double gravity = 9.8; // acceleration due to gravity
        ay = gravity; // vertical acceleration
    }


    public double[] findAngleCoords(double vx, double vy) {
    	
    	// Readusting the coordinates using the angle
    	double coordinate[] = new double[2];
    	double rotatedAngle = Math.toRadians(angle);
    	double cx = x + width/2;
    	double cy = y + height/2;
    	double dx = vx - cx; 
    	double dy = vy - cy;
    	double distance = Math.sqrt(dx * dx + dy * dy);
    	double originalAngle = Math.atan2(dx, dy);
    	
    	double rotatedX = cx + distance * Math.cos(originalAngle + rotatedAngle);
    	double rotatedY = cy + distance * Math.sin(originalAngle + rotatedAngle);
    	coordinate[0] = rotatedX;
    	coordinate[1] = rotatedY;
    	// Returns a coordinate of X and Y of the rotated rectangle given an input
    	return coordinate;
    }
    
    
    public double[][] getRotatedCoords() {
    	double[][] rectCoords = new double[2][];
    	//Work out the new locations adapted code by https://codepen.io/LFCProductions/pen/WNpRLXa?editors=0110
    	double rectVerticiesX[] = new double[4];
    	double rectVerticiesY[] = new double[4];

    	double[] topLeft = findAngleCoords(x, y);
    	double[] topRight = findAngleCoords(x + width, y);
    	double[] bottomRight = findAngleCoords(x + width, y + height);
    	double[] bottomLeft = findAngleCoords(x, y + height);
    	
    	// Get X Points
    	rectVerticiesX[0] = topLeft[0];
    	rectVerticiesX[1] = topRight[0];
    	rectVerticiesX[2] = bottomRight[0];
    	rectVerticiesX[3] = bottomLeft[0];
    	
    	// Get Y Points
    	rectVerticiesY[0] = topLeft[1];
    	rectVerticiesY[1] = topRight[1];
    	rectVerticiesY[2] = bottomRight[1];
    	rectVerticiesY[3] = bottomLeft[1]; 
    	rectCoords[0] = rectVerticiesX;
    	rectCoords[1] = rectVerticiesY;
    	
    	// Y and X are seperated via row correspond 1 to 1 
    	/*
    	for(int r = 0; r < rectCoords.length; r++) {
    		for(int c = 0; c < rectCoords[0].length; c++) {
    			System.out.print(rectCoords[r][c] + " ");
    			if(r == 0) {
    				System.out.print(" x ");
    			}
    		}
    		System.out.println();
    	}
    	*/

    	rx = rectVerticiesX;
    	ry = rectVerticiesY;
    	
    	return rectCoords;
    }
    
   
    public boolean checkSAT(Object otherPlayer) {
    	// Find the normal vector of each edge
    	
    	
    	
    	// doesnt matter using x or y just need to find num of points
    	
    	// Loops through all the axes
    	
    	
    	for(int shape = 0; shape < 2; shape++) {
    		double[] p1 = new double[4];
    		double[] p2 = new double[4];
    		// Loops through each edge
    		for(int edge = 0; edge < 4; edge++) {
    			 // Find the normal of the edge
    			if(shape == 0) {
	    			// Takes in two adjacent points
	    			p1[0] = rx[edge % 4];
	    			p1[1] = ry[edge % 4];
	    			// Get the second point clockwise
	    			p2[0] = rx[(edge + 1) % 4];
	    			p2[1] = ry[(edge + 1) % 4];
    			}
    			else if(shape == 1) {
    				// Takes in two adjacent points
	    			p1[0] = otherPlayer.rx[edge % 4]; 
	    			p1[1] = otherPlayer.ry[edge % 4];
	    			// Get the second point clockwise
	    			p2[0] = otherPlayer.rx[(edge + 1) % 4];
	    			p2[1] = otherPlayer.ry[(edge + 1) % 4];
    			}
    			// Finds the normal Vector
    			
    			// Each row is it's own indivual point
    			 
    			//System.out.println(edge + " x: " + p1[0] + " y:  " + p1[1]);
    			// Find the inverted points    			
    			
    			double[][] invertedPoints = {{-1 * p1[1], p1[0]}, {-1 * p2[1], p2[0]}};
    			double xDistance = invertedPoints[0][0] - invertedPoints[1][0];
    			double yDistance = invertedPoints[0][1] - invertedPoints [1][1];
    			
    			double[] normalMag = {xDistance, yDistance};
    			double length = Math.sqrt(xDistance * xDistance + yDistance * yDistance);
    			double[] normalVector = {normalMag[0] / length, normalMag[1] / length};
    			
    			/*
    			System.out.println( shape + " x1: " + invertedPoints[0][0] + " y1: " + invertedPoints[0][1]);    	
    			System.out.println( shape + " x1: " + invertedPoints[1][0] + " y2: " + invertedPoints[1][1]);    
    			

    			System.out.println(edge +  " normVectX: " + normalVector[0] + " normVectY: " + normalVector[1]);    
    			System.out.println(xDistance);
    			System.out.println(yDistance);
    			*/

    			/*
    			double[] edgeVect = {axis1[0] - axis2[0], axis1[1] - axis2[1]};
    			// Subtract the two to get the edge vector
    			double[] normal = {edgeVect[1] * -1, edgeVect[0]};
    			*/
    			
    			
    			// Assuming there are no points set these are the regular infinites in case of edge cases so NAN wont be thrown also so that the original setting of variables is easier
    			
    			double p1_min = Double.POSITIVE_INFINITY;
    			double p1_max = Double.NEGATIVE_INFINITY;
    		    
    			double p2_min = Double.POSITIVE_INFINITY;
    		    double p2_max = Double.NEGATIVE_INFINITY;
    		     
    		    // Project the points onto the normal vector using theta and the magnitutde
	    		
    		    
    		    for(int l = 0; l < 4; l++) {
    		    	double[] curRotatedPoint = {rx[l], ry[l]};
        		    double proj = dotProduct(normalVector, curRotatedPoint);
    	    		    if(proj < p1_min) {
    	    		    	p1_min = proj;
    	    		    }
    	    		    if(proj > p1_max) {
    	    		    	p1_max = proj;
    	    		    }
    		    }
    		    for(int p = 0; p < 4; p++) {
    		    	double[] curRotatedPoint = {otherPlayer.getRX(p), otherPlayer.getRY(p)};
    		    	//System.out.println(" rx: " + otherPlayer.getRX(p) + " ry: " + otherPlayer.getRY(p));
        		    double proj = dotProduct(normalVector, curRotatedPoint);
    	    		    if(proj < p2_min) {
    	    		    	p2_min = proj;
    	    		    }
    	    		    if(proj > p2_max) {
    	    		    	p2_max = proj;
    	    		    }
        		    
    		    }
    		    
    		    
    		    
    		    // Apply the minimum points to each of the values
    		    
    		    
    		    
    		    
    		    // Check if the points intersect
		    	//System.out.println("p1_min: " + p1_min + " p2_ min" + p2_min);
		    	//System.out.println("p1_max: " + p1_max + " p2_ max" + p2_max);

    		    if (p1_max < p2_min || p2_max < p1_min) {
    		    	return false;
    		    }
    		    
    		}
    		        	
    	}
    	return true;
    
    }
    
    public boolean checkSATPlatform(Platform otherPlayer) {
    	for(int shape = 0; shape < 2; shape++) {
    		double[] p1 = new double[4];
    		double[] p2 = new double[4];
    		for(int edge = 0; edge < 4; edge++) {
    			if(shape == 0) {
	    			p1[0] = rx[edge % 4];
	    			p1[1] = ry[edge % 4];
	    			p2[0] = rx[(edge + 1) % 4];
	    			p2[1] = ry[(edge + 1) % 4];
    			}
    			else if(shape == 1) {
	    			p1[0] = otherPlayer.getRX(edge % 4); 
	    			p1[1] = otherPlayer.getRY(edge % 4);
	    			p2[0] = otherPlayer.getRX((edge + 1) % 4);
	    			p2[1] = otherPlayer.getRY((edge + 1) % 4);
    			}

    			double[][] invertedPoints = {{-1 * p1[1], p1[0]}, {-1 * p2[1], p2[0]}};
    			double xDistance = invertedPoints[0][0] - invertedPoints[1][0];
    			double yDistance = invertedPoints[0][1] - invertedPoints [1][1];
    			
    			double[] normalMag = {xDistance, yDistance};
    			double length = Math.sqrt(xDistance * xDistance + yDistance * yDistance);
    			double[] normalVector = {normalMag[0] / length, normalMag[1] / length};
    			double p1_min = Double.POSITIVE_INFINITY; double p1_max = Double.NEGATIVE_INFINITY;
    			double p2_min = Double.POSITIVE_INFINITY; double p2_max = Double.NEGATIVE_INFINITY;

    		    for(int l = 0; l < 4; l++) {
    		    	double[] curRotatedPoint = {rx[l], ry[l]};
        		    double proj = dotProduct(normalVector, curRotatedPoint);
    	    		    if(proj < p1_min) {
    	    		    	p1_min = proj;
    	    		    }
    	    		    if(proj > p1_max) {
    	    		    	p1_max = proj;
    	    		    }
    		    }
    		    for(int p = 0; p < 4; p++) {
    		    	double[] curRotatedPoint = {otherPlayer.getRX(p), otherPlayer.getRY(p)};
        		    double proj = dotProduct(normalVector, curRotatedPoint);
    	    		    if(proj < p2_min) {
    	    		    	p2_min = proj;
    	    		    }
    	    		    if(proj > p2_max) {
    	    		    	p2_max = proj;
    	    		    }
        		    
    		    }

    		    if (p1_max < p2_min || p2_max < p1_min) {
    		    	return false;
    		    }
    		    
    		}
    		        	
    	}
    	return true;
    
    }
    
    public boolean checkPlatform(Platform platform) {
    	for(int point = 0; point < 4; point++) {
    		if(ry[point] > platform.getY()) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public boolean checkGroundCollison(Object ground) {
    	for(int point = 0; point < 4; point++) {
    		if(ry[point] > ground.getY()) {
    			lowestRY = ry[point];
    			
    			if(getRightX() > ground.getX() && getLeftX() < ground.getX() + ground.getWidth()) {
		    		return true;
    			}
		    	if(rx[point] > ground.getX() && rx[point] < ground.getX() + ground.getWidth()) {
		    	}
    			
    		}
    	}
    	
    	
    	
    	return false;
    }
    
    public boolean checkSATGround(Ground ground) {
    	// Find the normal vector of each edge
    	
    	
    	
    	// doesnt matter using x or y just need to find num of points
    	
    	// Loops through all the axes
    	
    	
    	for(int shape = 0; shape < 2; shape++) {
    		double[] p1 = new double[4];
    		double[] p2 = new double[4];
    		// Loops through each edge
    		for(int edge = 0; edge < 4; edge++) {
    			 // Find the normal of the edge
    			if(shape == 0) {
	    			// Takes in two adjacent points
	    			p1[0] = rx[edge % 4];
	    			p1[1] = ry[edge % 4];
	    			// Get the second point clockwise
	    			p2[0] = rx[(edge + 1) % 4];
	    			p2[1] = ry[(edge + 1) % 4];
    			}
    			else if(shape == 1) {
    				// Takes in two adjacent points
    				
    				
	    			p1[0] = ground.getRX(edge % 4); 
	    			p1[1] = ground.getRY(edge % 4);
	    			// Get the second point clockwise
	    			p2[0] = ground.getRX((edge + 1) % 4);
	    			p2[1] = ground.getRY((edge + 1) % 4);
    			}
    			// Finds the normal Vector
    			
    			// Each row is it's own indivual point
    			 
    			// Find the inverted points    			
    			
    			double[][] invertedPoints = {{-1 * p1[1], p1[0]}, {-1 * p2[1], p2[0]}};
    			double xDistance = invertedPoints[0][0] - invertedPoints[1][0];
    			double yDistance = invertedPoints[0][1] - invertedPoints [1][1];
    			
    			double[] normalMag = {xDistance, yDistance};
    			double length = Math.sqrt(xDistance * xDistance + yDistance * yDistance);
    			double[] normalVector = {normalMag[0] / length, normalMag[1] / length};
    			
    			
    			double p1_min = Double.POSITIVE_INFINITY;
    			double p1_max = Double.NEGATIVE_INFINITY;
    		    
    			double p2_min = Double.POSITIVE_INFINITY;
    		    double p2_max = Double.NEGATIVE_INFINITY;
    		     
    		    // Project the points onto the normal vector using theta and the magnitutde
	    		
    		    
    		    for(int l = 0; l < 4; l++) {
    		    	double[] curRotatedPoint = {rx[l], ry[l]};
        		    double proj = dotProduct(normalVector, curRotatedPoint);
    	    		    if(proj < p1_min) {
    	    		    	p1_min = proj;
    	    		    }
    	    		    if(proj > p1_max) {
    	    		    	p1_max = proj;
    	    		    }
    		    }
    		    for(int p = 0; p < 4; p++) {
    		    	double[] curRotatedPoint = {ground.getRX(p), ground.getRY(p)};
        		    double proj = dotProduct(normalVector, curRotatedPoint);
    	    		    if(proj < p2_min) {
    	    		    	p2_min = proj;
    	    		    }
    	    		    if(proj > p2_max) {
    	    		    	p2_max = proj;
    	    		    }
        		    
    		    }
    		    

    		    if (p1_max < p2_min || p2_max < p1_min) {
    		    	return false;
    		    }
    		    
    		}
    		        	
    	}
    	touchingGround = true;
    	return true;
    
    }
    
    
    
    
    
    public double dot(Double[] rp){ // Rotated points as an input
        // Dot Product between this vector and the passed one
        return x*rp[0] + y*rp[1];
    }

    
    
    public double dotProduct(double[] p1, double[] p2) {
    	return (p1[0] * p2[0]) + (p1[1] * p2[1]); 
    }
    
    
    
   
    
    /// ***** ACTUALY PHYSICS ENGINE ***********
    
    
    
    public void updatePosition(double dt) {
    	 // Caclulate Velocity
        xVel = (xVel) + (dt * ax) * damping;
        yVel = (yVel) + (dt * ay);
        
        // Compute the new position using Euler integration
        nx = x + xVel * dt + (ax * dt * dt * 0.5);
        ny = y + yVel * dt + (ay * dt * dt * 0.5);

        
        // Update current, previous of position and velocity
        px = x;
        py = y;
        x = nx;
        y = ny;
  
        
    }
    
    
    public void resolveAngularCollison(Object other) {
    	
    }
    
    
    
    public void resolveCollision(Object other) {
    	//if(detectCollision(other)) {
    	if(checkSAT(other)) {
    		
    		
    		// Collision physics adapted from mathematical paper on 2-Dimensional Elastic Collisions without Trigonometry at https://www.vobarian.com/collisions/2dcollisions2.pdf
    		
    		// Attempting to use physics to determine the norm
    		
    		// Find the unit normal vector
    		if(Math.abs(xVel) + Math.abs(yVel) > 50 || Math.abs(other.getXVel()) + Math.abs(other.getYVel()) > 50) {
    			playFalconHit();
    		}
    		System.out.println(Math.abs(xVel) + Math.abs(yVel));
    		System.out.println(Math.abs(other.getXVel()) + Math.abs(other.getYVel()));
    		double totalXDis1  = ((width) * Math.abs(Math.cos(Math.toRadians(angle)))) + ((height) * Math.abs(Math.cos(Math.toRadians(angle))));
	    	double totalXDis2 = (other.getWidth()) * Math.abs(Math.cos(Math.toRadians(other.getAngle()))) + (other.getHeight()) * Math.abs(Math.cos(Math.toRadians(other.getAngle())));
	    	double totalYDis1  = (width) * Math.abs(Math.sin(Math.toRadians(angle))) + (height) * Math.abs(Math.sin(Math.toRadians(angle)));
	    	double totalYDis2 = (other.getWidth()) * Math.abs(Math.sin(Math.toRadians(other.getAngle()))) + (other.getHeight()) * Math.abs(Math.sin(Math.toRadians(other.getAngle())));
	    	
	    	
    		double xDistance =  (x + width/2) - (other.getX() + other.getWidth()/2);
	    	double yDistance =  (y + height/2) - (other.getY() + other.getHeight()/2);
	    	//double xDistance = totalXDis1 + totalXDis2;
	    	//double yDistance = totalYDis1 + totalYDis2;
	    	double[] normalVector = {xDistance, yDistance};
	    	double length = Math.sqrt((normalVector[0] * normalVector[0]) + (normalVector[1] * normalVector[1]));
	    	double[] unitNormalVector = {normalVector[0]/length, normalVector[1]/length};
	    	double[] tangentVector = {unitNormalVector[1] * -1, unitNormalVector[0]};

	    	System.out.println("Unit normal: " + unitNormalVector[0] + " , " + unitNormalVector[1]);
	    	System.out.println("Unit tangent: " + tangentVector[0] + " , " + tangentVector[1]);

	    	// Creating inital velocity vectors
	    	
	    	double[] vel1 = {xVel, yVel};
	    	double[] vel2 = {other.getXVel(), other.getYVel()};
	    	
	    	
	    	// Projecting the velocities into a scalar vector with tangential and normal
	    	
	    	double unitVelNormal1 = (unitNormalVector[0] * vel1[0]) + (unitNormalVector[1] * vel1[1]);
	    	double unitVelNormal2 = (unitNormalVector[0] * vel2[0]) + (unitNormalVector[1] * vel2[1]);

	    	double unitVelTan1 = (tangentVector[0] * vel1[0]) + (tangentVector[1] * vel1[1]);
	    	double unitVelTan2 = (tangentVector[0] * vel2[0]) + (tangentVector[1] * vel2[1]);
	    	
	    	// Finding the new tangent velocities after the collision (doesnt change since there is no force in the tangential direction)
	    	
	    	double nUnitVelTan1 = unitVelTan1;
	    	double nUnitVelTan2 = unitVelTan2;


	    	// Find the new normal velocities
	    	
	    	double newUnitVelNorm1 = (unitVelNormal1 * (mass - other.getMass()) + 2 * other.getMass() * unitVelNormal2) / (mass + other.getMass());
	    	double newUnitVelNorm2 = (unitVelNormal2 * (other.getMass() - mass) + 2 * mass * unitVelNormal1) / (mass + other.getMass());
	    	System.out.println("Unit Vel normal: " + newUnitVelNorm1 + " , " + newUnitVelNorm2);
	    	System.out.println("Unit Vel tangent: " + unitVelTan1 + " , " + unitVelTan2);
	    	
	    	
	    	// Convert the scalar normal and tangential velocities into vectors
	    	
	    	
	    	double finalUnitVelNorm1[] = {newUnitVelNorm1 * unitNormalVector[0], newUnitVelNorm1 * unitNormalVector[1]};
	    	double finalUnitVelTan1[] = {nUnitVelTan1 * tangentVector[0], nUnitVelTan1 * tangentVector[1]};
	    	
	    	double finalUnitVelNorm2[] = {newUnitVelNorm2 * unitNormalVector[0], newUnitVelNorm2 * unitNormalVector[1]};
	    	double finalUnitVelTan2[] = {nUnitVelTan2 * tangentVector[0], nUnitVelTan2 * tangentVector[1]};


	    	xVel = finalUnitVelNorm1[0] + finalUnitVelTan1[0];
	    	yVel = finalUnitVelNorm1[1] + finalUnitVelTan1[1];
	    	other.setXVel(finalUnitVelNorm2[0] + finalUnitVelTan2[0]);
	    	other.setYVel(finalUnitVelNorm2[1] + finalUnitVelTan2[1]);


	    	// Seperate the objects so that they do not clip and get stuck within each other

	    	totalXDis1  = ((width) * Math.abs(Math.sin(Math.toRadians(angle)))) + ((height) * Math.abs(Math.cos(Math.toRadians(angle))));
	    	totalXDis2 = (other.getWidth()) * Math.abs(Math.sin(Math.toRadians(other.getAngle()))) + (other.getHeight()) * Math.abs(Math.cos(Math.toRadians(other.getAngle())));
	    	double intersectionX = Math.sqrt(totalXDis1 * totalXDis1 + totalYDis1 * totalYDis1) - Math.abs(x - other.getX());
	    	totalYDis1  = (width) * Math.abs(Math.cos(Math.toRadians(angle))) + (height) * Math.abs(Math.sin(Math.toRadians(angle)));
	    	totalYDis2 = (other.getWidth()) * Math.abs(Math.cos(Math.toRadians(other.getAngle()))) + (other.getHeight()) * Math.abs(Math.sin(Math.toRadians(other.getAngle())));
	    	double intersectionY = Math.sqrt(totalXDis2 * totalXDis2 + totalYDis2 * totalYDis2) - Math.abs(y - other.getY());
	    	
	    	
	    	System.out.println(angle);
	    	System.out.println(other.getAngle());
	    	System.out.println(Math.cos(Math.toRadians(angle)) + " x1Dis: " + totalXDis1 + " x2Dis: " + totalXDis2);
	    	System.out.println(" y1Dis: " + totalYDis1 + " y2Dis: " + totalYDis2);

	    	
	    	System.out.println("IntersectionX: " + intersectionX);
	    	System.out.println("IntersectionY: " + intersectionY);

	    	px = x;
	    	if(x < other.getX() - width / 2 && y + height > other.getY()) {
	    		x = x - intersectionX/8;
	    		other.setX(other.getX() + intersectionX/8);
	    	}
	    	else if(x > other.getX() + width / 2) {
	    		x = x + intersectionX/8;
	    		other.setX(other.getX() - intersectionX/8);    	
	    	}
	    	if(y < other.getY() - height / 2) {
	    		y = y - intersectionY/8;
	    		other.setY(other.getY() + intersectionY/8);
	    	}
	    	if(y > other.getY() + height / 2) {
	    		y = y + intersectionY/8;
	    		other.setY(other.getY() - intersectionY/8);
	    	}
	    	
    		
    	
    	}

    	
    }
    
    public void applyFriction(double frictionCoef) {
    	friction = frictionCoef * xVel;
    	xVel = xVel - friction;
    }
    
    public void resolveGroundCollision(Object ground) {
    	System.out.println("hello");
    	if(checkGroundCollison(ground)) {
    		
    		// Collision physics adapted from mathematical paper on 2-Dimensional Elastic Collisions without Trigonometry at https://www.vobarian.com/collisions/2dcollisions2.pdf
    		
    		// Attempting to use physics to determine the norm
    		
    		// Find the unit normal vector
    		

	    	System.out.println("Unit normal: ");

	    	
	    	
    		double xDistance =  (x + width/2) - (ground.getX() + 5);
	    	double yDistance =  (y + height/2) - (ground.getY() + 5);
	    	double[] normalVector = {xDistance, yDistance};
	    	double length = Math.sqrt((normalVector[0] * normalVector[0]) + (normalVector[1] * normalVector[1]));
	    	double[] unitNormalVector = {normalVector[0]/length, normalVector[1]/length};
	    	double[] tangentVector = {unitNormalVector[1] * -1, unitNormalVector[0]};

	    	System.out.println("Unit normal: " + unitNormalVector[0] + " , " + unitNormalVector[1]);
	    	System.out.println("Unit tangent: " + tangentVector[0] + " , " + tangentVector[1]);

	    	// Creating inital velocity vectors
	    	
	    	double[] vel1 = {xVel, yVel};
	    	double[] vel2 = {ground.getXVel(), ground.getYVel()};
	    	
	    	
	    	// Projecting the velocities into a scalar vector with tangential and normal
	    	
	    	double unitVelNormal1 = (unitNormalVector[0] * vel1[0]) + (unitNormalVector[1] * vel1[1]);
	    	double unitVelNormal2 = (unitNormalVector[0] * vel2[0]) + (unitNormalVector[1] * vel2[1]);

	    	double unitVelTan1 = (tangentVector[0] * vel1[0]) + (tangentVector[1] * vel1[1]);
	    	double unitVelTan2 = (tangentVector[0] * vel2[0]) + (tangentVector[1] * vel2[1]);
	    	
	    	// Finding the new tangent velocities after the collision (doesnt change since there is no force in the tangential direction)
	    	
	    	double nUnitVelTan1 = unitVelTan1;
	    	double nUnitVelTan2 = unitVelTan2;


	    	// Find the new normal velocities
	    	
	    	double newUnitVelNorm1 = (unitVelNormal1 * (mass - ground.getMass()) + 2 * ground.getMass() * unitVelNormal2) / (mass + ground.getMass());
	    	double newUnitVelNorm2 = (unitVelNormal2 * (ground.getMass() - mass) + 2 * mass * unitVelNormal1) / (mass + ground.getMass());
	    	System.out.println("Unit Vel normal: " + newUnitVelNorm1 + " , " + newUnitVelNorm2);
	    	System.out.println("Unit Vel tangent: " + unitVelTan1 + " , " + unitVelTan2);
	    	
	    	
	    	// Convert the scalar normal and tangential velocities into vectors
	    	
	    	
	    	double finalUnitVelNorm1[] = {newUnitVelNorm1 * unitNormalVector[0], newUnitVelNorm1 * unitNormalVector[1]};
	    	double finalUnitVelTan1[] = {nUnitVelTan1 * tangentVector[0], nUnitVelTan1 * tangentVector[1]};
	    	
	    	double finalUnitVelNorm2[] = {newUnitVelNorm2 * unitNormalVector[0], newUnitVelNorm2 * unitNormalVector[1]};
	    	double finalUnitVelTan2[] = {nUnitVelTan2 * tangentVector[0], nUnitVelTan2 * tangentVector[1]};


	    	xVel = finalUnitVelNorm1[0] + finalUnitVelTan1[0];
	    	yVel = finalUnitVelNorm1[1] + finalUnitVelTan1[1];



    	
    	}

    	
    }
    
    
    public boolean detectCollision(Object other) {
        if( x+width >= other.getX() && x <= other.getX()+other.getWidth() && y+height >= other.getY() && y <= other.getY() +other.getHeight()) {
            return true;
        }
        return false;
    }

    public boolean detectCollision(Ground ground) {
        if(y + height + 1 > ground.getY() && x+width+1 > ground.getX() && x-1 < ground.getX()+ground.getWidth() && y-1 < ground.getY() +ground.getHeight()) {
            return true;
        } 
        return false;
    }
    

    

   
    
    
    // ****** Drawing methods *************
    
    public void drawPath(Graphics g) {
    	g.setColor(color.red);
    	int pathX = (int) (x + width/2);
    	int pathY = (int) (y + height/2);
    	int pathX2 = (int) (pathX + 50 * Math.cos(Math.toRadians(angle)));
    	int pathY2 = (int) (pathY - 50 * Math.sin(Math.toRadians(angle)));
    	g.drawLine(pathX, pathY, pathX2, pathY2);
    }
    
    public void drawGun(Graphics g) {
    	Graphics2D g2d = (Graphics2D) g;
    	g2d.setColor(color.blue);
    	int originX = (int) (x + width/2);
    	int originY = (int) (y + height/2);
    	int x2 = (int) (originX + 50 * Math.cos(Math.toRadians(gunAngle)));
    	int y2 = (int) (originY - 50 * Math.sin(Math.toRadians(gunAngle)));
    	g2d.drawLine(originX, originY, x2, y2);

    }
    

    public void draw2(Graphics g) {
    	Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
    	int ax = (int) x; 
        int ay = (int) y;
        int awidth = (int) width;
        int aheight = (int) height;
        Graphics2D gg = (Graphics2D) g.create();
        ImageIcon image = new ImageIcon(currentDrawing);
        
        gg.rotate(Math.toRadians(angle * -1 + 90), ax + awidth/2, ay + aheight/2);
        gg.drawImage(image.getImage(), ax, ay, awidth, aheight, null);

        gg.dispose();

        gg = (Graphics2D) g.create();
    }
    

    
    public void resetAngle(Graphics g) {
    	Graphics2D g2d = (Graphics2D) g;
    	g2d.rotate(0);
    }
    
    
    public void drawLives(int location, Graphics g) {
        ImageIcon image = new ImageIcon("heart.png");
        
        for(int i = 0; i < lives; i++) {
        	g.drawImage(image.getImage(), location+i*26+3, 5, 24, 21, null);
        }
    	g.setColor(new Color(100, 100, 100));
    }
    
    
    
    // ************ Collisions and Boundry detection **********
    
    
    
    public boolean isGrounded(Object ground) {
        if(y + height + 1 > ground.getY() && x+width+1 > ground.getX() && x-1 < ground.getX()+ground.getWidth() && y-1 < ground.getY() +ground.getHeight()) {
            return true;
        }
        return false;
    }


    public boolean outOfBounds(int rightEdge, int bottomEdge) {
        if(x+width >= rightEdge+100 || x <= -100) {
            return true;
        }
        if(y+height >= bottomEdge+100 || y-height <= -500) {

            return true;
        }
        return false;
    }

    public void applyConstraints(Object ground) {
        if(y + height < ground.getY()+ground.getHeight()/2) {
            setY(Math.min(ground.getY()-getHeight(), getY()));
            
        }
        else if(y > ground.getY()+ground.getHeight()/2) {
            setY(Math.min(ground.getY()+ground.getHeight()+getHeight(), getY()));
            if(x + width < ground.getX() + ground.getWidth()/2) {
                setX(Math.min((ground.getX()-width), getX()));
            }
            if(x > ground.getX() + ground.getWidth()/2) {
                setX(Math.max((ground.getX()+ground.getWidth()), getX()));
            }
        }
        
        
    }
    
    public void applyConstraints2(Ground ground) {
    	touchingGround = true;
    	//.out.println("before yVel: " + yVel);
		double normalForce = yVel * -1;

    	if(jumping == false && y < ground.getY()) {
    		yVel = yVel + normalForce;
    	}

    	
    	double lowestPoint = 0;
    	for(int i = 0; i < 4; i++) {
    		if(ry[i] > lowestPoint) {
    			lowestPoint = ry[i];
    		}
    	}
    	if(lowestPoint > ground.getY()) {
    	}
    	double push = Math.abs(lowestPoint - ground.getY());
    	
    	if(getRightX() > ground.getX() && y > ground.getY() && x < ground.getX() + ground.getWidth()/2) {
    		double pushR = getRightX() - ground.getX();
    		x = x - pushR;
    	}
    	if(getLeftX() < ground.getX() + ground.getWidth() && y > ground.getY() && x > ground.getX() + ground.getWidth()/2) {
    		double pushL =  Math.abs((ground.getX() + ground.getWidth()) - getLeftX());
    		System.out.println("intersected " + pushL);
    		x = x + pushL;
    	}
    	
    	
    	if(getRightX() > ground.getX() || getLeftX() < ground.getX()+ground.getWidth()) {
    		if(y < ground.getY()) {
    			setY(Math.min(y - push, y));
    		}
    	}    	
    	
    	
    	
        //setY(Math.min(ground.getY()-lengthToGround, getY()));
    }
    public void applyPlatformConstraints(Platform ground) {
    	touchingGround = true;
    	//.out.println("before yVel: " + yVel);
		double normalForce = yVel * -1;

    	if(jumping == false && y < ground.getY()) {
    		yVel = yVel + normalForce;
    	}

    	
    	double lowestPoint = 0;
    	for(int i = 0; i < 4; i++) {
    		if(ry[i] > lowestPoint) {
    			lowestPoint = ry[i];
    		}
    	}
    	if(lowestPoint > ground.getY()) {
    	}
    	double push = Math.abs(lowestPoint - ground.getY());
    	
    	if(getRightX() > ground.getX() && y > ground.getY() && x < ground.getX() + ground.getWidth()/2) {
    		double pushR = getRightX() - ground.getX();
    		x = x - pushR;
    	}
    	if(getLeftX() < ground.getX() + ground.getWidth() && y > ground.getY() && x > ground.getX() + ground.getWidth()/2) {
    		double pushL =  Math.abs((ground.getX() + ground.getWidth()) - getLeftX());
    		System.out.println("intersected " + pushL);
    		x = x + pushL;
    	}
    	
    	
    	if(getRightX() > ground.getX() || getLeftX() < ground.getX()+ground.getWidth()) {
    		if(y < ground.getY()) {
    			setY(Math.min(y - push, y));
    		}
    	}    	
    	
    	
    }
    
    
   // ********* Player Movement **********
    
    
    public void jumpMechanic(Ground ground, double angleInc) {
    	boolean isFalling = x < px;
    	if(touchingGround) {
    		jumpCount = 1;
    		yVel = yVel - power * Math.sin(Math.toRadians(angle));
    		xVel = xVel + power * Math.cos(Math.toRadians(angle));
        	jumping = true;

    	}
    	
    	else if(jumpCount <= 3) {
    		jumpCount++;
    		yVel = yVel - power * Math.sin(Math.toRadians(angle));
    		xVel = xVel + power * Math.cos(Math.toRadians(angle));
    	}
    	else {
    		System.out.println("no more jump");
    	}
    	
    }
    
    public void playerAngleChange(double angleInc) {
    	if(angle < 180 || angle > 0) {
    		angle += angleInc;
        	gunAngle = gunAngle + angleInc;
    	}
    }
    
    public void powerSetter(double powerInc) {
    	if(power < 125) {
    		power += powerInc;
    	}
    	color = new Color(225, Math.max( (int) (204-power*2),0) , Math.max( (int) (229-power*2),0));
    }
    
    public void shoot(Graphics g) {
    	bullets.add(new Bullet(x + width/2, y + height/2, gunAngle));
    }
    
    public void applyBulletPhysics(int WIDTH, int HEIGHT, double deltaTime, Object otherPlayer, Graphics g) {
    	for(int i = 0; i < bullets.size(); i++) {
	    	bullets.get(i).resolveCollision(otherPlayer);
			bullets.get(i).updatePosition(deltaTime);
			bullets.get(i).draw(g);	
			if(bullets.get(i).outOfBounds(WIDTH, HEIGHT)) {
				bullets.remove(bullets.get(i));
				System.out.println(bullets.size());
			}
    	}
    }
    
    public void playMusic() {
		File soundFile;
		soundFile = new File("FALCON PUNCH Sound Effect __ Best Sound Effects TV.wav");
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start(); 
			clip.loop(Clip.LOOP_CONTINUOUSLY); //several options here
			long clipTimePosition = clip.getMicrosecondPosition();
			clip.stop();
			clip.setMicrosecondPosition(clipTimePosition);
			clip.start();
		}
		catch (Exception e) {
			System.out.println("Audio error");
		}
	}

    
    public void playFalconHit() {
    	File soundFile;
		soundFile = new File("FALCON PUNCH Sound Effect __ Best Sound Effects TV.wav");
		try {
			if(soundFile.exists()) {
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
				Clip clip = AudioSystem.getClip();
				clip.open(audioIn);
				clip.start(); 
				
			}
			else {
 				System.out.println("Can't find file");
			}
			
		}
		catch (Exception e) {
			System.out.println();
		}
	}
    public void playHit() {
    	File soundFile;
		soundFile = new File("FALCON PUNCH Sound Effect __ Best Sound Effects TV.wav");
		try {
			if(soundFile.exists()) {
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
				Clip clip = AudioSystem.getClip();
				clip.open(audioIn);
				clip.start(); 
				
			}
			else {
 				System.out.println("Can't find file");
			}
			
		}
		catch (Exception e) {
		}
	}
    

}
     