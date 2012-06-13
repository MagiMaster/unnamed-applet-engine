package AppletGameEngine;

import java.awt.geom.AffineTransform;

/**
 *
 * @author Tze-I Yang
 */
public class UnionedBoundingVolumes implements BoundingVolume {
	private BoundingVolume first;
	private BoundingVolume second;
	
	public UnionedBoundingVolumes(BoundingVolume first, BoundingVolume second)
	{
		this.first = first;
		this.second = second;
	}
	
	public BoundingVolume getFirst() {
		return first;
	}
	
	public BoundingVolume getSecond() {
		return second;
	}
	
	public boolean collide(BoundingVolume other)
	{
		if(first.collide(other) || second.collide(other))
			return true;
		return false;
	}

	public boolean collide(double x, double y)
	{
		if(first.collide(x, y) || second.collide(x, y))
			return true;
		return false;
	}

	public void transformObjectSpace(AffineTransform t)
	{
		first.transformObjectSpace(t);
		second.transformObjectSpace(t);
	}

	public void transformWorldSpace(AffineTransform t)
	{
		first.transformWorldSpace(t);
		second.transformWorldSpace(t);
	}
}
