package AppletGameEngine;

import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.util.Random;

/**
 * Divided into <code>GameRooms</code>. Passes mouse and keyboard events to
 * appropriate <code>GameRoom</codes>.
 *
 * @author  Joshua Taylor
 */
public class Game
{
	private static ArrayList<GameRoom> rooms;   // A list of rooms
	private static int currentRoom = 0;         // The current room
	private static int nextRoom = -1;           // The room to change to next
	private static long time;                   // For tracking time elapsed between frames
	private static long delay;                  // The ideal time between frames
	private static double timeLeft;
	public static long nextTime;                // For calculating the delay between frames
	public static AppletMain applet;            // The applet this game is running in
	public static Random rnd;					// A place to get random numbers
	
	public static void init(AppletMain a)
	{
		applet = a;
		delay = 33; // About 1/30 of a second
		
		rnd = new Random();
		
		rooms = new ArrayList<GameRoom>();
		rooms.add(new GameRoom());
		
		time = System.currentTimeMillis();
		nextTime = time;
		timeLeft = 0.0;
	}
	
	public static void setURL(String url)
	{
		ResourceLoader.setURL(url);
	}
	
	public static void update()
	{
		long t = System.currentTimeMillis();
		//double dt = (t - time)*0.001;
		timeLeft += (t - time)*0.001;
		time = t;
		nextTime = time + delay;
		
		if(nextRoom != -1)
		{
			currentRoom = nextRoom;
			nextRoom = -1;
		}
		
		while(timeLeft > 0.01)
		{
			rooms.get(currentRoom).update(0.01);
			timeLeft -= 0.01;
		}
	}
	
	public static void changeDelay(long newDelay)
	{
		delay = newDelay;
	}
	
	public static void draw(Graphics2D buffer)
	{
		rooms.get(currentRoom).draw(buffer);
	}
	
	public static int addRoom()
	{
		rooms.add(new GameRoom());
		return rooms.size()-1;
	}
	
	public static int addRoom(GameRoom r)
	{
		rooms.add(r);
		return rooms.size()-1;
	}
	
	public static void changeRoom(int next)
	{
		nextRoom = next;
	}
	
	public static void addObject(GameObject o)
	{
		rooms.get(currentRoom).addObject(o);
	}
	
	public static void addObject(GameObject o, int roomNum)
	{
		rooms.get(roomNum).addObject(o);
	}
	
	public static void addBackground(Background b)
	{
		rooms.get(currentRoom).addBackground(b);
	}
	
	public static void addBackground(Background b, int roomNum)
	{
		rooms.get(roomNum).addBackground(b);
	}
	
	public static void addBackground(String filename)
	{
		rooms.get(currentRoom).addBackground(filename);
	}
	
	public static void addBackground(String filename, int roomNum)
	{
		rooms.get(roomNum).addBackground(filename);
	}
	
	public static void addForeground(Background f)
	{
		rooms.get(currentRoom).addForeground(f);
	}
	
	public static void addForeground(Background f, int roomNum)
	{
		rooms.get(roomNum).addForeground(f);
	}
	
	public static void addForeground(String filename)
	{
		rooms.get(currentRoom).addForeground(filename);
	}
	
	public static void addForeground(String filename, int roomNum)
	{
		rooms.get(roomNum).addForeground(filename);
	}
	
	private Game()
	{
	}
	
	// -- MouseListener Functions --
	public static void mouseClicked(MouseEvent e)
	{
		rooms.get(currentRoom).mouseClicked(e);
	}
	
	public static void mousePressed(MouseEvent e)
	{
		rooms.get(currentRoom).mousePressed(e);
	}
	
	public static void mouseReleased(MouseEvent e)
	{
		rooms.get(currentRoom).mouseReleased(e);
	}
	
	public static void mouseEntered(MouseEvent e)
	{
		rooms.get(currentRoom).mouseEntered(e);
	}
	
	public static void mouseExited(MouseEvent e)
	{
		rooms.get(currentRoom).mouseExited(e);
	}
	
	// -- MouseMotionListener Functions --
	public static void mouseDragged(MouseEvent e)
	{
		rooms.get(currentRoom).mouseDragged(e);
	}
	
	public static void mouseMoved(MouseEvent e)
	{
		rooms.get(currentRoom).mouseMoved(e);
	}
	
	// -- KeyListener Functions --
	public static void keyPressed(KeyEvent e)
	{
		rooms.get(currentRoom).keyPressed(e);
	}
	
	public static void keyReleased(KeyEvent e)
	{
		rooms.get(currentRoom).keyReleased(e);
	}
	
	public static void keyTyped(KeyEvent e)
	{
		rooms.get(currentRoom).keyTyped(e);
	}
}
