package AppletGameEngine;

import java.awt.Graphics2D;
import java.awt.event.*;
import java.util.*;

/**
 * Contains <code>GameObjects</code> and <code>Backgrounds</code>. Passes mouse
 * and keyboard events to appropriate <code>GameObjects</code>.
 *
 * @author  Joshua Taylor
 */
public class GameRoom implements MouseListener, MouseMotionListener, KeyListener
{
	// -- Variables --
	public Set<GameObject> objects;             // objects in this room
	public int prevX, prevY;                    // previous mouse coordinates

	protected Set<GameObject> clicked;            // a set of objects clicked for use with mouseDragged
	protected Set<GameObject> waiting;            // a set of objects waiting to be created

	public ArrayList<Background> backgrounds;   // A list of backgrounds to be drawn behind objects
	public ArrayList<Background> foregrounds;   // A list of backgrounds to be drawn in front of objects
	
	// -- Manager Functions --
	public GameRoom()
	{
		objects = new TreeSet<GameObject>();
		clicked = new HashSet<GameObject>();
		waiting = new HashSet<GameObject>();
		backgrounds = new ArrayList<Background>();
		foregrounds = new ArrayList<Background>();
	}

	public void addObject(GameObject o)
	{
		waiting.add(o);
	}

	public void addBackground(Background b)
	{
		backgrounds.add(b);
	}
	
	public void addBackground(String filename)
	{
		backgrounds.add(new Background(ResourceLoader.loadImage(filename)));
	}
	
	public void addForeground(Background b)
	{
		foregrounds.add(b);
	}
	
	public void addForeground(String filename)
	{
		foregrounds.add(new Background(ResourceLoader.loadImage(filename)));
	}
	
	public synchronized void update(double dt)
	{
		// Create any waiting objects
		for(GameObject o : waiting)
		{
			objects.add(o);
			o.room = this;
		}
		waiting.clear();

		// Update objects and object flags
		for(GameObject o : objects)
		{
			o.update(dt);
			o.updated = true;
		}
		for(GameObject o : objects)
			o.updated = false;
		
		// Delete dead objects
		Iterator<GameObject> iter = objects.iterator();
		while(iter.hasNext())
		{
			GameObject o = iter.next();
			
			if(o.dead)
			{
				iter.remove();
				clicked.remove(o);
			}
		}
		
		// Check for collisions
		for(GameObject o1 : objects)
			for(GameObject o2 : objects)
			{
				if(o1.UID >= o2.UID)
					continue;
				if(o1.hit(o2))
				{
					o1.collided(o2);
					o2.collided(o1);
				}
			}
	}

	public synchronized void draw(Graphics2D buffer)
	{
		for(Background b : backgrounds)
			b.draw(buffer);

		for(GameObject o : objects)
			o.draw(buffer);

		for(Background f : foregrounds)
			f.draw(buffer);
	}

	// -- MouseListener Functions --
	public synchronized void mouseClicked(MouseEvent e)
	{
		// Java's mouseClicked is too unresponsive
		// mouseClicked is generated in mouseReleased
	}

	public synchronized void mousePressed(MouseEvent e)
	{
		for(GameObject o : objects)
		{
			o.globalMousePressed(e);
			if(o.hit(e.getX(), e.getY()))
			{
				o.mousePressed(e);
				clicked.add(o);
			}
		}
	}

	public synchronized void mouseReleased(MouseEvent e)
	{
		for(GameObject o : objects)
		{
			o.globalMouseReleased(e);
			o.globalMouseClicked(e);
			if(o.hit(e.getX(), e.getY()))
			{
				o.mouseReleased(e);
				if(clicked.contains(o))
					o.mouseClicked(e);
			}
		}
		clicked.clear();
	}

	public synchronized void mouseEntered(MouseEvent e)
	{
		for(GameObject o : objects)
			o.globalMouseEntered(e);
	}

	public synchronized void mouseExited(MouseEvent e)
	{
		for(GameObject o : objects)
			o.globalMouseExited(e);
	}

	// -- MouseMotionListener Functions --
	public synchronized void mouseDragged(MouseEvent e)
	{
		for(GameObject o : clicked)
			o.mouseDragged(e);
		
		for(GameObject o : objects)
			o.globalMouseDragged(e);
	}

	public synchronized void mouseMoved(MouseEvent e)
	{
		for(GameObject o : objects)
		{
			o.globalMouseMoved(e);
			
			boolean h1 = o.hit(e.getX(), e.getY());
			if(h1)
				o.mouseMoved(e);
			
			boolean h2 = o.hit(prevX, prevY);
			if(h1 && !h2)
				o.mouseEntered(e);
			if(!h1 && h2)
				o.mouseExited(e);
		}

		prevX = e.getX();
		prevY = e.getY();
	}

	// -- KeyListener Functions --
	public synchronized void keyPressed(KeyEvent e)
	{
		for(GameObject o : objects)
			o.keyPressed(e);
	}

	public synchronized void keyReleased(KeyEvent e)
	{
		for(GameObject o : objects)
			o.keyReleased(e);
	}

	public synchronized void keyTyped(KeyEvent e)
	{
		for(GameObject o : objects)
			o.keyTyped(e);
	}
}
