import java.awt.Color;
import java.util.*;
import java.awt.Graphics;
import java.util.Random;


public class Bullet {


    // Declaration of Variables


    // Physics engine
    double x, y; // Current Position
    double nx, ny; // New Position
    double height, width; // height and width
    double px, py; // Previous position
    double ax, ay; // Current Acceleration
    double xVel, yVel; // Velocities
    double mass; // Object Mass
    
    
    
    Color color; // Color of Object
    double angle; // Angle of Objects head


    // Constructors

    public Bullet(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.px = x;
        this.py = y;
        height = 1 + Math.abs(5 *  Math.sin(Math.toRadians(angle)));
        width = 1 + Math.abs(5 * Math.cos(Math.toRadians(angle)));
        this.ax = 0;
        this.ay = 0;
        mass = 5;
        		
        this.color = color.red;
        xVel = 700 * Math.cos(Math.toRadians(angle));
        yVel = 700 * -1 * Math.sin(Math.toRadians(angle));
        this.angle = angle;
    }


    // getters and setters for all private variables
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
    
    public Color getColor() {
        return color;
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
    
    public void setAngle(double angle) {
        this.angle = angle;
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

    public void updatePosition(double dt) {
    	 // Caclulate Velocity
    	
    	
        xVel = (xVel) + (dt * ax);
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
    
    
    public void resolveCollision(Object other) {
    	if(detectCollision(other)) {
    		
    		// Attempting to use physics to determine the norm
    		
    		// Find the unit normal vector
    		double xDistance =  (x + width/2) - (other.getX() + other.getWidth()/2);
	    	double yDistance =  (y + height/2) - (other.getY() + other.getHeight()/2);
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


	    	other.setXVel(finalUnitVelNorm2[0] + finalUnitVelTan2[0]);
	    	other.setYVel(finalUnitVelNorm2[1] + finalUnitVelTan2[1]);

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
    

    public void draw(Graphics g) {
        g.setColor(color);
        int ax = (int) x;
        int ay = (int) y;
        int awidth = (int) width;
        int aheight = (int) height;
        g.fillRect(ax, ay, awidth, aheight);
        
    }
    
    
    
    // ************ Collisions and Boundry detection **********
    
    
    
    public boolean isGrounded(Ground ground) {
        if(y + height + 1 > ground.getY() && x+width+1 > ground.getX() && x-1 < ground.getX()+ground.getWidth() && y-1 < ground.getY() +ground.getHeight()) {
            return true;
        }
        return false;
    }


    public boolean outOfBounds(int rightEdge, int bottomEdge) {
        if(x+width >= rightEdge+100 || x <= -100) {
            return true;
        }
        if(y+height >= bottomEdge+100 || y-height <= -100) {

            return true;
        }
        return false;
    }


    
    
   // ********* Player Movement **********
    
 
    
    

}
    