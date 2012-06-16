/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pede;

import AppletGameEngine.*;

/**
 *
 * @author Joshua Taylor
 */
public class PedeMain extends AppletMain
{
	public static Shroom[][] grid = new Shroom[30][30];

	public static void addShroom(int x, int y)
	{
		if((x < 0) || (y < 0) || (x >= 30) || (y >= 30) || (grid[x][y] != null))
			return;

		Shroom s = new Shroom();
		s.translateWorldSpace(x*16+7.5, y*16+7.5);
		Game.addObject(s);
		grid[x][y] = s;
	}

	public void setup()
	{
		Player p = new Player();
		p.translateWorldSpace(0+7.5, 464+7.5);
		Game.addObject(p);

		for(int y = 1; y < 29; ++y)
		{
			addShroom(Game.rnd.nextInt(30), y);
			addShroom(Game.rnd.nextInt(30), y);
		}

		PedeBody pede = new PedeHead();
		pede.translateWorldSpace(7.5, 7.5);
		Game.addObject(pede);

		for(int i = 0; i < 15; ++i)
		{
			pede = new PedeBody(pede);
			pede.translateWorldSpace(7.5, 7.5);
			Game.addObject(pede);
		}
	}
}
