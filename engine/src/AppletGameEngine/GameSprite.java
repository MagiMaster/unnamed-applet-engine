package AppletGameEngine;

import java.awt.*;
import java.awt.image.*;
import java.awt.geom.AffineTransform;
import java.util.*;

/**
 * Loads sprites.
 *
 * @author  Joshua Taylor
 */
public class GameSprite
{
	// -- Variables --
	public ArrayList<Image> images;             // The image for this sprite
	public ArrayList<AffineTransform> origins;  // A transformation defining the origin of the sprite
	public double frame = 0.0;                  // The current frame of animation
	public double speed;                        // Frames to advance per draw

	// -- Functions --
	public GameSprite()
	{
		images = new ArrayList<Image>();
		origins = new ArrayList<AffineTransform>();
		speed = 1.0;
	}

	public GameSprite(double animSpeed)
	{
		images = new ArrayList<Image>();
		origins = new ArrayList<AffineTransform>();
		speed = animSpeed;
	}
	
	public void addFrame(String filename, double originX, double originY)
	{
		Image img = ResourceLoader.loadImage(filename);
		AffineTransform t = AffineTransform.getTranslateInstance(-originX, -originY);
		images.add(img);
		origins.add(t);
	}
	
	public void addFrame(String filename, double originX, double originY, Color c)
	{
		Image img = ResourceLoader.loadImageTransparent(filename, c);
		AffineTransform t = AffineTransform.getTranslateInstance(-originX, -originY);
		images.add(img);
		origins.add(t);
	}
	
	public synchronized void addFilmstrip(String filename, double originX, double originY, int width, int height, int offsetX, int offsetY, int borderX, int borderY, int countX, int countY)
	{
		Image strip = ResourceLoader.loadImageBlocking(filename);
		AffineTransform t = AffineTransform.getTranslateInstance(-originX, -originY);
		
		for(int y = 0; y < countY; ++y)
		{
			int sy = y*(height + borderY) + offsetY;
			for(int x = 0; x < countX; ++x)
			{
				int sx = x*(width + borderX) + offsetX;
				Image temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				Graphics g = temp.getGraphics();
				g.drawImage(strip, 0, 0, width, height, sx, sy, sx+width, sy+height, null);
				g.dispose();
				images.add(temp);
				origins.add(t);
			}
		}
	}
			
	public synchronized void addFilmstrip(String filename, double originX, double originY, int width, int height, int offsetX, int offsetY, int borderX, int borderY, int countX, int countY, Color c)
	{
		Image strip = ResourceLoader.loadImageTransparent(filename, c);
		AffineTransform t = AffineTransform.getTranslateInstance(-originX, -originY);
		
		for(int y = 0; y < countY; ++y)
		{
			int sy = y*(height + borderY) + offsetY;
			for(int x = 0; x < countX; ++x)
			{
				int sx = x*(width + borderX) + offsetX;
				Image temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				Graphics g = temp.getGraphics();
				g.drawImage(strip, 0, 0, width, height, sx, sy, sx+width, sy+height, null);
				g.dispose();
				images.add(temp);
				origins.add(t);
			}
		}
	}
			
	public void draw(Graphics2D buffer, AffineTransform t)
	{
		int f = (int)frame;
		
		AffineTransform temp = (AffineTransform)t.clone();
		temp.concatenate(origins.get(f));
		buffer.drawImage(images.get(f), temp, null);
		
		frame += speed;
		if(frame >= images.size())
			frame -= images.size();
	}

	public int getWidth()
	{
		return images.get((int)frame).getWidth(null);
	}

	public int getHeight()
	{
		return images.get((int)frame).getHeight(null);
	}
}
