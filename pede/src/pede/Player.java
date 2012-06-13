package pede;

import AppletGameEngine.*;
import java.awt.Color;
import java.awt.event.MouseEvent;

public class Player extends GameObject {
	boolean ready;
	
	public Player()
	{
		bound = new BoundingParallelogram(0, 0, 16, 16);
		sprite = new GameSprite(0.0);
		sprite.addFrame("Ship.png", 7.5, 7.5, new Color(255, 0, 255));
		ready = true;
	}
	
	public void globalMouseDragged(MouseEvent e)
	{
		globalMouseMoved(e);
	}
	
	public void globalMouseMoved(MouseEvent e)
	{
		double dx = e.getX() - getX();
		translateWorldSpace(dx, 0);
	}
	
	public void globalMousePressed(MouseEvent e)
	{
		if(ready)
		{
			Bullet b = new Bullet(this);
			b.setTransform(trans);
			Game.addObject(b);
			ready = false;
		}
	}
}
