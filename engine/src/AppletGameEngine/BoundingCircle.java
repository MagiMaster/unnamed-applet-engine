package AppletGameEngine;

import java.awt.geom.AffineTransform;

/**
 * Bounding volume using circles.
 *
 * @author  Joshua Taylor
 */
public class BoundingCircle implements BoundingVolume {
	private double r;
	private double[] c;
	private AffineTransform center;
	
	public BoundingCircle(double x, double y, double r)
	{
		center = AffineTransform.getTranslateInstance(x, y);
		this.r = r;
		c = new double[2];
		c[0] = x;
		c[1] = y;
	}
	
	public double getR() {
		return r;
	}
	
	public double[] getC() {
		return c;
	}

	public AffineTransform getTransform() {
		return center;
	}

	public boolean collide(BoundingVolume o)
	{
		if(o == null)
			return false;
		if(o instanceof BoundingCircle)
		{
			BoundingCircle c = (BoundingCircle)o;
			
			double xx = center.getTranslateX() - c.center.getTranslateX();
			double yy = center.getTranslateY() - c.center.getTranslateY();
			double rr = r + c.r;
			return (xx*xx + yy*yy < rr*rr);
		}
		return o.getClass().cast(o).collide(this);
	}

	public boolean collide(double u, double v)
	{
		double xx = center.getTranslateX() - u;
		double yy = center.getTranslateY() - v;
		return (xx*xx + yy*yy < r*r);
	}
	
	private void updateLocals(AffineTransform t) {
		double scalexx = t.getScaleX();
		double scalexy = t.getShearX();
		double scalex = scalexx*scalexx + scalexy*scalexy;
		
		double scaleyx = t.getScaleX();
		double scaleyy = t.getShearX();
		double scaley = scaleyx*scaleyx + scaleyy*scaleyy;
		
		double scale = (scalex > scaley) ? scalex : scaley;

		r = r * Math.sqrt(scale);
		c[0] = center.getTranslateX();
		c[1] = center.getTranslateY();
	}

	public void transformObjectSpace(AffineTransform t)
	{
		center.concatenate(t);
		updateLocals(t);
	}

	public void transformWorldSpace(AffineTransform t)
	{
		center.preConcatenate(t);
		updateLocals(t);
	}
}
