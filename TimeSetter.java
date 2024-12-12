
import java.awt.*;
import java.util.Random;

public class TimeSetter {
	public static float strength = 0.1f;

    public TimeSetter() 
    {
        System.out.println("Time has been set");
    }
    
    
    public static void setTime(Color time, Graphics g)
    {
		Graphics2D g2D = (Graphics2D) g;
        g2D.setComposite(comp);
        Color ec = g2D.getColor();
        
        g2D.setColor(time);
        Shape s = g2D.getClip();
        if (s != null)
        	g2D.fill(s);

        g2D.setComposite(comp);

    }
    
    private static Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, strength);

    
    public static float randFloat(float max, float min)
    {
        Random ran = new Random();
        return min + ran.nextFloat() * (max - min);

    }
    
}