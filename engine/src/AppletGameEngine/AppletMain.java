package AppletGameEngine;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

/**
 * Entry point for applet program.
 *
 * @author  Joshua Taylor
 */
public abstract class AppletMain extends Applet implements Runnable, MouseListener, MouseMotionListener, KeyListener
{
	// -- Variables --
	private Image offscreen;        // Offscreen rendering target
	private Graphics2D buffer;      // Offscreen rendering target
	private Thread thread;          // The thread animating this applet
	private Boolean running;		// Is the thread currently running

	// -- Applet Functions --
	public void init()
	{
		offscreen = createImage(getWidth(), getHeight());
		buffer = (Graphics2D) (offscreen.getGraphics());
		buffer.setBackground(Color.black);
		buffer.setColor(Color.white);

		Game.init(this);
		setup();

		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setFocusable(true);

		running = false;
		thread = new Thread(this);
	}

	/**
	 * Method for creating instances of objects at game startup.
	 */
	public abstract void setup();

	public void start()
	{
		thread.start();	
	}

	public void destroy()
	{
		thread = null;
	}

	/**
	 * The main game loop.
	 */
	public void run()
	{
		running = true;
		while(Thread.currentThread() == thread)
		{
			Game.update();
			repaint();

			long delay = Game.nextTime - System.currentTimeMillis();

			try
			{
				if(delay > 0)
					Thread.sleep(delay);    // Change this to vsync?
			}
			catch(InterruptedException e)
			{
				break;
			}
		}
		running = false;
	}

	public Boolean isRunning()
	{
		return running;
	}

	public void paint(Graphics g)
	{
		buffer.clearRect(0, 0, getWidth(), getHeight());
		Game.draw(buffer);
		g.drawImage(offscreen, 0, 0, this);
	}

	public void update(Graphics g)
	{
		paint(g);
	}

	// -- MouseListener Functions --
	public void mouseClicked(MouseEvent e)
	{
		Game.mouseClicked(e);
	}

	public void mousePressed(MouseEvent e)
	{
		Game.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e)
	{
		Game.mouseReleased(e);
	}

	public void mouseEntered(MouseEvent e)
	{
		Game.mouseEntered(e);
	}

	public void mouseExited(MouseEvent e)
	{
		Game.mouseExited(e);
	}

	// -- MouseMotionListener Functions --
	public void mouseDragged(MouseEvent e)
	{
		Game.mouseDragged(e);
	}

	public void mouseMoved(MouseEvent e)
	{
		Game.mouseMoved(e);
	}

	// -- KeyListener Functions --
	public void keyPressed(KeyEvent e)
	{
		Game.keyPressed(e);
	}

	public void keyReleased(KeyEvent e)
	{
		Game.keyReleased(e);
	}

	public void keyTyped(KeyEvent e)
	{
		Game.keyTyped(e);
	}
}
