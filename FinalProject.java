 import javax.swing.*;
   import java.awt.*;
	import java.awt.event.*;
   public class FinalProject extends JPanel implements ActionListener
   {
	
	public int met1X = 200;
	public int met2X = 550;
	Timer t = new Timer(5, this);
		
		public FinalProject()
		{
		t.start();
		}
  
     public void paintComponent(Graphics g)
      {
      //	super.paintComponent(g);
		
			//backround
			g.setColor(Color.black);
			g.fillRect(0, 0, 1000, 1000);
			
			//stars
			for(int i = 0; i < 500; i++)
			{
			int x = (int)(1000 * Math.random());
			int y = (int)(1000 * Math.random());
			g.setColor(Color.black);
			g.drawOval(x, y, 5, 5);
			g.setColor(Color.white);
			g.fillOval(x, y, 5, 5);		
			}			
      
			//body
			g.setColor(Color.black);
         g.drawRect(350, 150, 100, 200);
			g.setColor(new Color(0, 185, 255));
			g.fillRect(350, 150, 100, 200);
      
         //roof
			g.setColor(Color.black);
         int xPoints[] = {340, 400, 460};
         int yPoints[] = {150, 100, 150};
         g.drawPolygon(xPoints, yPoints, 3);
			g.setColor(new Color(254, 32, 27));
			int xPoints1[] = {340, 400, 460};
         int yPoints1[] = {150, 100, 150};
         g.fillPolygon(xPoints1, yPoints1, 3);
			
			//wings
			g.setColor(Color.black);
			int xPoints2[] = {300, 350, 350, 300};
			int yPoints2[] = {335, 285, 350, 400};
			g.drawPolygon(xPoints2, yPoints2, 4);
			g.setColor(new Color(254, 32, 27));
			int xPoints3[] = {300, 350, 350, 300};
			int yPoints3[] = {335, 285, 350, 400};
			g.fillPolygon(xPoints3, yPoints3, 4);
			g.setColor(Color.black);
			int xPoints4[] = {450, 500, 500, 450};
			int yPoints4[] = {285, 335, 400, 350};
			g.drawPolygon(xPoints4, yPoints4, 4);
			g.setColor(new Color(254, 32, 27));
			int xPoints5[] = {450, 500, 500, 450};
			int yPoints5[] = {285, 335, 400, 350};
			g.fillPolygon(xPoints5, yPoints5, 4);
		
         //window
			g.setColor(Color.black);
         g.drawRoundRect(375, 175, 50, 50, 20, 20);
			g.setColor(Color.white);
			g.fillRoundRect(375, 175, 50, 50, 20, 20);
      
         //thruster
			g.setColor(Color.black);	
         int xPoints6[] = {350, 450, 480, 320};
         int yPoints6[] = {350, 350, 400, 400};
         g.drawPolygon(xPoints6, yPoints6, 4);
			g.setColor(Color.gray);
			int xPoints7[] = {350, 450, 480, 320};
         int yPoints7[] = {350, 350, 400, 400};
         g.fillPolygon(xPoints7, yPoints7, 4);
      	
			//fire
			g.setColor(Color.black);
			g.drawArc(350, 150, 100, 500, 180, 180);
			g.setColor(Color.yellow);
			g.fillArc(350, 150, 100, 500, 180, 180);
			g.setColor(Color.black);
			g.drawArc(367, 235, 66, 330, 180, 180);
			g.setColor(Color.orange);
			g.fillArc(367, 235, 66, 330, 180, 180);
			g.setColor(Color.black);
			g.drawArc(384, 320, 33, 160, 180, 180);
			g.setColor(Color.red);
			g.fillArc(384, 320, 33, 160, 180, 180);
			
			//sun
			g.setColor(Color.black);
			g.drawOval(10, 10, 250, 250);
			g.setColor(Color.orange);
			g.fillOval(10, 10, 250, 250);
			g.setColor(Color.black);
			g.drawOval(46, 46, 175, 175);
			g.setColor(Color.yellow);
			g.fillOval(46, 46, 175, 175);
			
			//moon
			g.setColor(Color.black);
			g.drawOval(500, 75, 150, 150);
			g.setColor(Color.white);
			g.fillOval(500, 75, 150, 150);
			
			//craters on moon
			g.setColor(Color.black);
			g.drawOval(520, 110, 35, 35);
			g.drawOval(550, 150, 45, 45);
			g.drawOval(560, 105, 65, 35);
			g.drawOval(600, 140, 25, 25);
			g.setColor(Color.gray);
			g.fillOval(520, 110, 35, 35);
			g.fillOval(550, 150, 45, 45);
			g.fillOval(560, 105, 65, 35);
			g.fillOval(600, 140, 25, 25);
			
			
			//planet 1
			g.setColor(Color.black);
			g.drawOval(650, 300, 250, 250);
			g.setColor(new Color(48, 255, 43));
			g.fillOval(650, 300, 250, 250);
			
			//craters on planet 1
			g.setColor(Color.black);
			g.drawOval(750, 350, 100, 100);
			g.drawOval(700, 450, 75, 75);
			g.drawOval(665, 365, 85, 85);
			g.setColor(new Color(9, 70, 2));
			g.fillOval(750, 350, 100, 100);
			g.fillOval(700, 450, 75, 75);
			g.fillOval(665, 365, 85, 85);
			
			//planet 2
			g.setColor(Color.black);
			g.drawOval(0, 600, 450, 450);
			g.setColor(Color.red);
			g.fillOval(0, 600, 450, 450);
			
			//craters on planet 2
			g.setColor(Color.black);
			g.drawOval(100, 700, 250, 250);
			g.drawOval(75, 650, 75, 75);
			g.drawOval(250, 618, 95, 95);
			g.setColor(new Color(95, 0, 0));
			g.fillOval(100, 700, 250, 250);
			g.fillOval(75, 650, 75, 75);
			g.fillOval(250, 618, 95, 95);
			
			//tail on meteor 1
			g.setColor(Color.black);
			int xPoints8[] = {200, 213, 225};
			int yPoints8[] = {463, 563, 463};
			g.drawPolygon(xPoints8, yPoints8, 3);
			g.setColor(new Color(74, 247, 255));
			int xPoints9[] = {200, 213, 225};
			int yPoints9[] = {463, 563, 463};
			g.fillPolygon(xPoints9, yPoints9, 3);
			
			//meteor 1
			
			g.setColor(Color.black);
			g.drawOval(met1X, 450, 25, 25);
			g.setColor(new Color(200, 200, 200));
			g.fillOval(met1X, 450, 25, 25);
			
			//tail on meteor 2
			g.setColor(Color.black);
			int xPoints10[] = {550, 588, 625};
			int yPoints10[] = {388, 888, 388};
			g.drawPolygon(xPoints10, yPoints10, 3);
			g.setColor(new Color(74, 247, 255));
			int xPoints11[] = {550, 588, 625};
			int yPoints11[] = {388, 888, 388};
			g.fillPolygon(xPoints11, yPoints11, 3);
			
			//meteor 2
			g.setColor(Color.black);
			g.drawOval(met2X, 350, 75, 75);
			g.setColor(new Color(200, 200, 200));
			g.fillOval(met2X, 350, 75, 75);
 
			
			//alien ship (arcs)
			g.setColor(Color.black);
			g.drawOval(125, 325, 50, 50);
			g.setColor(new Color(130, 249, 255));
			g.fillOval(125, 325, 50, 50);
			g.setColor(Color.gray);
			g.fillArc(50, 350, 200, 50, 0, 360);
			g.setColor(Color.black);
			g.drawLine(50, 375, 250, 375);
			g.drawArc(50, 350, 200, 50, 0, 360);
			
			
			}
			public void actionPerformed(ActionEvent e)
			{
			met1X += 10;
			met2X += 10;
			repaint();
			}
	   }
