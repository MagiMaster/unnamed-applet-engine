package AppletGameEngine;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;

/**
 * Loads static backgrounds.
 *
 * @author  Joshua Taylor
 */
public class Background
{
	// -- Static Sprite Management --
	/**
	 * 
	 */
	private static Map<String, Background> bkgs = null;

	/**
	 * Returns a new <code>Background<code> using the image called
	 * <code>filename</code>.
	 *
	 * @param filename  name of the image file to be used
	 * @return          A new <code>Background</code>.
	 */
	public static Background loadBackground(String filename)
	{
		if(bkgs == null)
			bkgs = new HashMap<String, Background>();
		
		if(bkgs.containsKey(filename))
			return bkgs.get(filename);
		
		Image img = ResourceLoader.loadImage(filename);
		Background b = new Background(img);
		bkgs.put(filename, b);
		return b;
	}

	// -- Variables --
	/**
	 * The image for this <code>Background</code>.
	 */
	public Image image;

	// -- Functions --
	/**
	 * Creates a <code>Background</code> and initializes its image.
	 *
	 * @param image     the image to be used for the initialization
	 */
	public Background(Image image)
	{
		this.image = image;
	}

	/**
	 * Draws the image associated with this <code>Background</code> onto a
	 * buffer, applying a transformation first.
	 *
	 * @param buffer    the buffer to draw the background image
	 * @param t         the transformation to apply before drawing
	 */
	public void draw(Graphics2D buffer, AffineTransform t)
	{
		buffer.drawImage(image, t, null);
	}

	/**
	 * Draws the image associated with this <code>Background</code> onto a
	 * buffer with its top-left corner at (<i>x</i>, <i>y</i>).
	 *
	 * @param buffer    the buffer to draw the background image
	 * @param x         the <i>x</i> coordinate
	 * @param y         the <i>y</i> coordinate
	 */
	public void draw(Graphics2D buffer, int x, int y)
	{
		buffer.drawImage(image, x, y, null);
	}

	/**
	 * Draws the image associated with this <code>Background</code> onto a
	 * buffer.
	 *
	 * @param buffer    the buffer to draw the background image
	 */
	public void draw(Graphics2D buffer)
	{
		buffer.drawImage(image, 0, 0, null);
	}

	/**
	 * Returns the width of the image associated with this
	 * <code>Background</code> in pixels.
	 *
	 * @return          The width of the background image in pixels.
	 */
	public int getWidth()
	{
		return image.getWidth(null);
	}

	/**
	 * Returns the height of the image associated with this
	 * <code>Background</code> in pixels.
	 *
	 * @return          The height of the background image in pixels.
	 */
	public int getHeight()
	{
		return image.getHeight(null);
	}
}
