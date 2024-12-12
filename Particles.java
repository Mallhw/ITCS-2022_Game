import java.awt.*;
import java.util.Random;

public class Particles {
	
	private int stableStarX;
	private int stableStarY;
	private int originalYPos;
	private float opacityOrignal;
	private Random rand;
	


    public Particles(int posX, int posY, int yHeight) 
    {
    	rand = new Random();
        //System.out.println("Particles have been created");
        stableStarX = (int) (Math.random() * posX);
        stableStarY = (int) (Math.random() * posY) - yHeight;
        originalYPos = stableStarY;
        opacityOrignal = rand.nextFloat(0.5f) + 0.5f;
    }
    
    public static void spawnParticles(int posX, int posY, int num, int pWidth, int pHeight, Color particleColor, Graphics g)
    {
    	// Creating the random generator
        g.setColor(particleColor);
        
        // Using the position of the map to pick a random position to place the stars
        for(int i = 0; i<num; i++)
        {
            g.drawOval(randInt(posX-1, 0), randInt(posY-1, 0), pWidth, pHeight);
        }
    }

    public void spawnStars(int posX, int posY, int num, Color particleColor, Graphics g)
    {
		Stars[] stars = new Stars[num];

        for(int i = 0; i<num; i++)
        {
        	
        	// Creating the star
        	float starMult = randFloat(1.5f, 0.5f);
        	int firstRadi = Math.round(7*starMult);
        	int secondRadi = Math.round(3*starMult);
        	int thirdRadi = Math.round(6*starMult);
        	int fourthRadi = Math.round(3*starMult);
        	Color opacityparticleColor=new Color(randFloat(1.0f,0.8f),randFloat(1.0f,0.8f),0f, randFloat(0.5f,0.0f));
        	g.setColor(opacityparticleColor);
        	
        	// Setting the variables 
            int xPos = randInt(posX, 0);
            int yPos = randInt(posY, 0);
            int radius[] = {firstRadi,secondRadi,thirdRadi,fourthRadi};
            int nPoints = 10;
            int[] X = new int[nPoints];
            int[] Y = new int[nPoints];
            
            
            // Creates a certain amount of points which are each filled by creating a radius
            for (double current=0.0; current<nPoints; current++)
            {
                int a = (int) current;
                double x = Math.cos(current*((2*Math.PI)/10))*radius[a % 4];
                double y = Math.sin(current*((2*Math.PI)/10))*radius[a % 4];

                X[a] = (int) x+xPos;
                Y[a] = (int) y+yPos;
            }

            g.fillPolygon(X, Y, nPoints);

        }
    }    
    
    public void drawStableStars(Color particleColor, Graphics g)
    {
    	
        	// Creating the star
    	
    		
        	float starMult = randFloat(1.5f, 0.5f);
        	int firstRadi = Math.round(7*starMult);
        	int secondRadi = Math.round(3*starMult);
        	int thirdRadi = Math.round(6*starMult);
        	int fourthRadi = Math.round(3*starMult);
        	Color permOpacity=new Color(1.0f, 1.0f, 0.5f);
        	Color opacityparticleColor=new Color(randFloat(opacityOrignal, opacityOrignal-0.2f), randFloat(opacityOrignal, opacityOrignal-0.2f), randFloat(opacityOrignal, opacityOrignal-0.3f));

        	g.setColor(opacityparticleColor);
        	
        	// Setting the variables 
        	
            int radius[] = {firstRadi,secondRadi,thirdRadi,fourthRadi};
            int nPoints = 10;
            int[] X = new int[nPoints];
            int[] Y = new int[nPoints];
            
            
            // Creates a certain amount of points which are each filled by creating a radius
            for (double current=0.0; current<nPoints; current++)
            {
                int a = (int) current;
                double x = Math.cos(current*((2*Math.PI)/10))*radius[a % 4];
                double y = Math.sin(current*((2*Math.PI)/10))*radius[a % 4];

                X[a] = (int) x+stableStarX;
                Y[a] = (int) y+stableStarY;
            }

            g.fillPolygon(X, Y, nPoints);

        
    }
    
    
    public static void groundGen(int posX, int posY, int num, int pWidth, int pHeight, Color groundColor, Graphics g) {
    	
    	// Generating the ground
    	
        for(int i = 0; i<num; i++)
        {
        	int curY = randInt(posY-1, 600);
        	g.setColor(new Color(randInt(25, 0), randInt(10, 0), randInt(25, 0)));
            g.fillArc(randInt(posX-1, 0), curY, pWidth, pHeight, 0, 180);
            
            
        }
    	
    }
    
    
    public void setX(int stableStarX) {
    	this.stableStarX = stableStarX;
    }
    
    public void setY(int stableStarY) {
    	this.stableStarY = stableStarY;
    }
    
    public int getX() {
    	return stableStarX;
    }
    
    public int getY() {
    	return stableStarY;
    }
    
    public int getOriginalYPos() {
    	return originalYPos;
    }
    
    public static int randInt(int max, int min)
    {
        Random ran = new Random();
        return ran.nextInt((max-min) + 1) + min;
    }
    
    
    public static float randFloat(float max, float min)
    {
        Random ran = new Random();
        return min + ran.nextFloat() * (max - min);

    }
    

    
}