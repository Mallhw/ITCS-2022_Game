import java.awt.Graphics;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class GameSettings {
	
	public static boolean checkGame(Object player1, Object player2) {
		if(player1.getLives() <= 0 || player2.getLives() <= 0) {
			return false;
		}
		return true;
	}
	
	
	public static void drawEverything(int WIDTH, int HEIGHT, Object player1, Object player2, Ground ground, Graphics g) {
		player1.drawLives(0, g);
		player2.drawLives(WIDTH-100, g);
		ground.draw(g);
		player1.draw2(g);
		player2.draw2(g);
	}
	
	public static void gameOver(Graphics g) {
		g.drawString(" GAME OVER ", 500, 500);
		g.drawString(" press A to start over", 200, 200);
		
	}

	
	public static void outOfBounds(Ground ground, Object player) {
		player.setAngle(90);
		int randStart = (int) (Math.random() * ground.getWidth() + ground.getX());
		player.setX(randStart);
		player.setY(200);
		player.setXVel(0);
		player.setYVel(0);
		player.setPower(0);
	}
	
	
	public static void playSoundTrack() {
		File soundFile;
		soundFile = new File("Coconut Mall - Mario Kart Wii OST.wav");
		try {
			if(soundFile.exists()) {
				
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
				Clip clip = AudioSystem.getClip();
				clip.open(audioIn);
				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

				gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.
				clip.start(); 
				clip.loop(Clip.LOOP_CONTINUOUSLY); 
			}
			else {
 				System.out.println("Can't find file");
			}
			
		}
		catch (Exception e) {
			System.out.println();
		}
	}
}
