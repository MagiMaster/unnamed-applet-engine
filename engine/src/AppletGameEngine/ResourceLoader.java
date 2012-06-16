package AppletGameEngine;

import java.applet.*;
import java.awt.*;
import java.awt.image.*;
import java.net.*;
import java.util.*;

/**
 * Loads images and sound.
 *
 * @author  Joshua Taylor
 * @see     TimerCallback
 */
public class ResourceLoader
{
	/**
	 * A cache for images
	 */
	private static Map<String, Image> images = null;

	/**
	 * A cache for audio
	 */
	private static Map<String, AudioClip> sounds = null;

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

		URL fileURL = Game.applet.getClass().getResource(filename);
		Image img = Toolkit.getDefaultToolkit().createImage(fileURL);
		images.put(filename, img);
		return img;
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

		URL fileURL = Game.applet.getClass().getResource("/" + filename);
		Image img = Toolkit.getDefaultToolkit().createImage(fileURL);

		MediaTracker mt = new MediaTracker(new Canvas());
		mt.addImage(img, 0);
		try
		{
			mt.waitForAll();
		} catch (InterruptedException e) {}

		images.put(filename, img);
		return img;
	}

	/**
	 * Loads an image and makes <code>c</code> transparent.
	 *
	 * @param filename  the file to load
	 * @param c         the <code>Color</code> to make transparent
	 * @return          A new image with <code>c</code> made transparent.
	 */
	public static Image loadImageTransparent(String filename, final Color c)
	{
		if(images == null)
			images = new HashMap<String, Image>();

		if(images.containsKey(filename + "@" + c.toString()))
			return images.get(filename + "@" + c.toString());

		Image img = loadImageBlocking(filename);

		ImageFilter filter = new RGBImageFilter()
		{
			public final int filterRGB(int x, int y, int rgb)
			{
				if(rgb == c.getRGB())
					return 0;
				else
					return rgb;
			}
		};

		ImageProducer ip = new FilteredImageSource(img.getSource(), filter);
		Image out = Toolkit.getDefaultToolkit().createImage(ip);

		MediaTracker mt = new MediaTracker(new Canvas());
		mt.addImage(out, 0);
		try
		{
			mt.waitForAll();
		} catch (InterruptedException e) {}

		images.put(filename + "@" + c.toString(), out);
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

		URL fileURL = Game.applet.getClass().getResource("/" + filename);
		AudioClip audio = Game.applet.getAudioClip(fileURL);
		sounds.put(filename, audio);
		return audio;
	}
	
	/**
	 * A private default constructor to prevent instantiation of this class.
	 */
	private ResourceLoader()
	{
	}
}
