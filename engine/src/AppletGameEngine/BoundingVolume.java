package AppletGameEngine;

import java.awt.geom.AffineTransform;

/**
 * The interface for all bounding volumes.
 *
 * @author  Joshua Taylor
 */
public interface BoundingVolume {
	// Add a class variable for an AffineTransform here?

	/**
	 * @param other     a <code>BoundingVolume</code> use in collision test
	 * @return          <code>true</code>.
	 */
	boolean collide(BoundingVolume other);

	/**
	 * 
	 */
	abstract boolean collide(double x, double y);

	/**
	 * 
	 */
	abstract void transformObjectSpace(AffineTransform t);

	/**
	 * 
	 */
	abstract void transformWorldSpace(AffineTransform t);
}
