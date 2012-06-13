package pede;

import AppletGameEngine.*;
import java.awt.Color;
import java.awt.geom.AffineTransform;

public class PedeHead extends PedeBody {
	enum State { LEFT, RIGHT }
	State state;
	boolean bump;
	
	public PedeHead()
	{
		super(null);
		sprite = new GameSprite();
		sprite.addFrame("Head.png", 7.5, 7.5, new Color(255, 0, 255));
		state = State.RIGHT;
	}

	public PedeHead(PedeBody b)
	{
		this();
		
		setTransform(b.trans);
		lastPos.setTransform(b.lastPos);
		prev = b.prev;
		if(prev != null)
			prev.next = this;

		lastD = b.d;
		
		if((b.d == 2) || ((b.d == 1) && Game.rnd.nextBoolean()))
			state = State.LEFT;
		else
			state = State.RIGHT;
	}
	
	public int think(int x, int y)
	{		
		switch(state)
		{
			case LEFT:
				if((x == 0) || (PedeMain.grid[x-1][y] != null))
				{
					state = State.RIGHT;
					if((y == 29) || (PedeMain.grid[x][y+1] != null))
					{
						if((x == 29) || (PedeMain.grid[x+1][y] != null))
							return -1;
						else
							return 0;
					}
					else
						return 1;
				}
				else
					return 2;
				
			case RIGHT:
				if((x == 29) || (PedeMain.grid[x+1][y] != null))
				{
					state = State.LEFT;
					if((y == 29) || (PedeMain.grid[x][y+1] != null))
					{
						if((x == 0) || (PedeMain.grid[x-1][y] != null))
							return -1;
						else
							return 2;
					}
					else
						return 1;
				}
				else
					return 0;
		}
		return 1;
	}
	
	public void update(double dt)
	{
		time += dt;
		
		if(time >= speed)
		{
			lastPos.setTransform(trans);
			lastD = d;

			setTransform(AffineTransform.getTranslateInstance(getX(), getY()));
			
			int x = (int)(getX()/16.0);
			int y = (int)(getY()/16.0);

			d = think(x, y);
			switch(d)
			{
				case 0:
					translateWorldSpace(16, 0);
					break;
				case 1:
					translateWorldSpace(0, 16);
					rotateObjectSpace(0.5*Math.PI);
					break;
				case 2:
					translateWorldSpace(-16, 0);
					rotateObjectSpace(Math.PI);
					break;
			}
			
			bump = false;
			
			time -= speed;
		}
	}
}
