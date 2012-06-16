package pede;

import AppletGameEngine.*;
import java.awt.Color;
import java.awt.geom.AffineTransform;

public class PedeBody extends GameObject {
	public static double speed = 0.2;
	
	PedeBody next, prev;
	double time;
	AffineTransform lastPos;
	int d, lastD;
	
	public PedeBody(PedeBody next)
	{
		bound = new BoundingParallelogram(1, 1, 14, 14);
		sprite = new GameSprite();
		sprite.addFrame("images/Body.png", 7.5, 7.5, new Color(255, 0, 255));
		
		this.next = next;
		if(next != null)
			next.prev = this;
		prev = null;
		
		time = 0.0;
		lastPos = new AffineTransform(trans);
		d = 0;
		lastD = 0;
	}
	
	public void update(double dt)
	{
		time += dt;
		
		// Wait 1/10 second between moves
		if(time >= speed)
		{
			if(next != null)
			{
				// Follow the leader
				lastPos.setTransform(trans);
				lastD = d;
				
				if(next.updated)
				{
					setTransform(next.lastPos);
					d = next.lastD;
				}
				else
				{
					setTransform(next.trans);
					d = next.d;
				}
			}
			else
			{
				// Become the leader
				PedeHead h = new PedeHead(this);
				
				Game.addObject(h);
				
				die();
			}
			
			time -= speed;
		}
	}
	
	public void collided(GameObject o)
	{
		if(o instanceof Bullet)
		{
			if(o.dead)
				return;
			
			int x = (int)(getX()/16);
			int y = (int)(getY()/16);
			PedeMain.addShroom(x, y);
			if(next != null)
				next.prev = null;
			if(prev != null)
				prev.next = null;
			die();
		}
	}
}
