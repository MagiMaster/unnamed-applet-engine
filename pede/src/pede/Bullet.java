package pede;

import AppletGameEngine.*;
import java.awt.Color;

public class Bullet extends GameObject {
	Player p;
	
	public Bullet(Player p)
	{
		bound = new BoundingParallelogram(5, 1, 6, 14);
		sprite = new GameSprite(0.0);
		sprite.addFrame("Bullet.png", 7.5, 7.5, new Color(255, 0, 255));
		this.p = p;
	}
	
	public void update(double dt)
	{
		translateWorldSpace(0, -400*dt);
		
		if(getY() < 0)
		{
			die();
			p.ready = true;
		}
	}
	
	public void collided(GameObject o)
	{
		if(o instanceof Player)
			return;

		p.ready = true;
		
		die();
	}
}
