package AppletGameEngine;

import java.applet.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Loads images and sound.
 *
 * @author  Joshua Taylor
 * @see     TimerCallback
 */
public class ResourceLoader
{
	/**
	 * An URL object that contains the url to the current image directory.
	 */
	private static URL url = null;
	
	/**
	 *
	 */
	private static Map<String, Image> images = null;

	private static Map<String, AudioClip> sounds = null;

	/**
	 * Sets the url of the image directory to <code>s</code>.
	 *
	 * @param s         the url of the image directory
	 */
	public static void setURL(String s)
	{
		if(s == null)
		{
			url = null;
			return;
		}
		
		try
		{
			url = new URL(s);
		}
		catch (java.net.MalformedURLException e0)
		{
			if(Pattern.matches(".*protocol.*", e0.toString()))
			{
				try
				{
					url = new URL("http://" + s);
				}
				catch (java.net.MalformedURLException e1)
				{
					url = null;
				}
			}
			else
				url = null;
		}
	}

	/**
	 * Returns the <code>Image</code> called <code>filename</code> found
	 * at the current image directory.
	 *
	 * @param filename  the filename of the image
	 * @return          The <code>Image</code> called <code>filename</code>
	 *                  found at the current image directory.
	 */
	public static Image loadImage(String filename)
	{
		if(images == null)
			images = new HashMap<String, Image>();

		if(images.containsKey(filename))
			return images.get(filename);

		if(url == null)
		{
			Image img = Toolkit.getDefaultToolkit().createImage(filename);
			images.put(filename, img);
			return img;
		}

		try
		{
			Image img = Toolkit.getDefaultToolkit().createImage(new URL(url, filename));
			images.put(filename, img);
			return img;
		}
		catch(MalformedURLException e)
		{
			return null;
		}
	}

	/**
	 * Returns the <code>Image</code> called <code>filename</code> found
	 * at the current image directory. This blocks execution until the image
	 * has fully loaded.
	 *
	 * @param filename  the filename of the image
	 * @return          The <code>Image</code> called <code>filename</code>
	 *                  found at the current image directory.
	 */
	public static Image loadImageBlocking(String filename)
	{
		if(images == null)
			images = new HashMap<String, Image>();

		if(images.containsKey(filename))
		{
			Image img = images.get(filename);
			
			MediaTracker mt = new MediaTracker(new Canvas());
			mt.addImage(img, 0);
			try
			{
				mt.waitForAll();
			} catch (InterruptedException e) {}
			
			return img;
		}

		if(url == null)
		{
			Image img = Toolkit.getDefaultToolkit().createImage(filename);

			MediaTracker mt = new MediaTracker(new Canvas());
			mt.addImage(img, 0);
			try
			{
				mt.waitForAll();
			} catch (InterruptedException e) {}

			images.put(filename, img);
			return img;
		}

		try
		{
			Image img = Toolkit.getDefaultToolkit().createImage(new URL(url, filename));

			MediaTracker mt = new MediaTracker(new Canvas());
			mt.addImage(img, 0);
			try
			{
				mt.waitForAll();
			} catch (InterruptedException e) {}

			images.put(filename, img);
			return img;
		}
		catch(MalformedURLException e)
		{
			return null;
		}
	}

	/**
	 * Loads an image and makes <code>c</code> transparent.
	 *
	 * @param filename  the file to load
	 * @param c         the <code>Color</code> to make transparent
	 * @return          A new image with <code>c</code> made transparent.
	 */
	public static Image loadImageTransparent(String filename, Color c)
	{
		Image img = loadImageBlocking(filename);
		
		int w = img.getWidth(null);
		int h = img.getHeight(null);
		
		BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)out.getGraphics();
		g.setComposite(AlphaComposite.Src);
		g.drawImage(img, 0, 0, null);
		g.dispose();
		
		MediaTracker mt = new MediaTracker(new Canvas());
		mt.addImage(out, 1);
		try
		{
			mt.waitForAll();
		} catch(InterruptedException e) {}
		
		for(int y = 0; y < h; ++y)
			for(int x = 0; x < w; ++x)
				if(out.getRGB(x, y) == c.getRGB())
					out.setRGB(x, y, 0);
		
		return out;
	}
	
	/**
	 * Loads a sound file.
	 *
	 * @param filename  the file to load
	 * @return          An <code>AudioClip</code> for the sound.
	 */
	public static AudioClip loadSound(String filename)
	{
		if(sounds == null)
			sounds = new HashMap<String, AudioClip>();

		if(sounds.containsKey(filename))
			return sounds.get(filename);

		if(url == null)
		{
			AudioClip audio = Game.applet.getAudioClip(Game.applet.getDocumentBase(), filename);
			sounds.put(filename, audio);
			return audio;
		}

		try
		{
			AudioClip audio = Game.applet.getAudioClip(new URL(url, filename));
			sounds.put(filename, audio);
			return audio;
		}
		catch(MalformedURLException e)
		{
			return null;
		}
	}
	
	/**
	 * A private default constructor to prevent instantiation of this class.
	 */
	private ResourceLoader()
	{
	}
}
