package AppletGameEngine;

import java.awt.*;
import java.awt.geom.*;

public class SimpleGraphics {
	private SimpleGraphics() {};
	
	public static void setColor(Graphics2D g, Color c)
	{
		g.setColor(c);
	}
	
	public static void drawLine(Graphics2D g, double x1, double y1, double x2, double y2) 
	{
		g.draw(new Line2D.Double(x1, y1, x2, y2));
	}
	
	public static void drawLine(Graphics2D g, double x1, double y1, double x2, double y2, Color c) 
	{
		g.setColor(c);
		g.draw(new Line2D.Double(x1, y1, x2, y2));
	}
	
	public static void drawCircle(Graphics2D g, double x, double y, double r)
	{
		g.draw(new Ellipse2D.Double(x-r, y-r, r+r, r+r));
	}
	
	public static void drawCircle(Graphics2D g, double x, double y, double r, Color c1, Color c2)
	{
		Ellipse2D e = new Ellipse2D.Double(x-r, y-r, r+r, r+r);
		if(c2 != null)
		{
			g.setColor(c2);
			g.fill(e);
		}
		if(c1 != null)
		{
			g.setColor(c1);
			g.draw(e);
		}
	}
	
	public static void drawEllipse(Graphics2D g, double x, double y, double w, double h)
	{
		g.draw(new Ellipse2D.Double(x, y, w, h));
	}
	
	public static void drawEllipse(Graphics2D g, double x, double y, double w, double h, Color c1, Color c2)
	{
		Ellipse2D e = new Ellipse2D.Double(x, y, w, h);
		if(c2 != null)
		{
			g.setColor(c2);
			g.fill(e);
		}
		if(c1 != null)
		{
			g.setColor(c1);
			g.draw(e);
		}
	}
	
	public static void drawRectangle(Graphics2D g, double x1, double y1, double x2, double y2)
	{
		g.draw(new Rectangle2D.Double(x1, y1, x2-x1, y2-y1));
	}
	
	public static void drawRectangle(Graphics2D g, double x1, double y1, double x2, double y2, Color c1, Color c2)
	{
		Rectangle2D r = new Rectangle2D.Double(x1, y1, x2-x1, y2-y1);
		if(c2 != null)
		{
			g.setColor(c2);
			g.fill(r);
		}
		if(c1 != null)
		{
			g.setColor(c1);
			g.draw(r);
		}
	}
	
	public static void drawTriangle(Graphics2D g, double x1, double y1, double x2, double y2, double x3, double y3)
	{
		g.draw(new Line2D.Double(x1, y1, x2, y2));
		g.draw(new Line2D.Double(x2, y2, x3, y3));
		g.draw(new Line2D.Double(x3, y3, x1, y1));
	}

	public static void drawTriangle(Graphics2D g, double x1, double y1, double x2, double y2, double x3, double y3, Color c)
	{
		g.setColor(c);
		g.draw(new Line2D.Double(x1, y1, x2, y2));
		g.draw(new Line2D.Double(x2, y2, x3, y3));
		g.draw(new Line2D.Double(x3, y3, x1, y1));
	}

	public static Color findColor(String s)
	{
		Color temp = null;
		
		try
		{
			try
			{
				temp = (Color)Color.class.getField(s.toUpperCase()).get(null);
			}
			catch(Exception e) 
			{
				temp = Color.decode(s);
			}
		}
		catch(Exception e)
		{
			temp = null;
		}
		
		return temp;
	}
}
