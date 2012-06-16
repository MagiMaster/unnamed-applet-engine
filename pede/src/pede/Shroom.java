package pede;

import AppletGameEngine.*;
import java.awt.Color;

public class Shroom extends GameObject {
	int hp;
	
	public Shroom()
	{
		bound = new BoundingParallelogram(0, 0, 16, 16);
		sprite = new GameSprite(0.0);
		sprite.addFilmstrip("images/Shroom.png", 7.5, 7.5, 16, 16, 0, 0, 0, 0, 4, 1, new Color(255, 0, 255));
		hp = 4;
	}

	public void collided(GameObject o)
	{
		if(o instanceof Bullet)
		{
			if(o.dead)
				return;
			
			hp -= 1;
			if(hp == 0)
			{
				int x = (int)(getX()/16);
				int y = (int)(getY()/16);
				PedeMain.grid[x][y] = null;
				die();
			}
			else
				sprite.frame = 4-hp;
		}
	}
}
