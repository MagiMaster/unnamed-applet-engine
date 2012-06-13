package AppletGameEngine;

import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.geom.*;

/**
 * The superclass of all classes that represent objects in the game.
 *
 * @author  Joshua Taylor
 * @see     GameRoom
 */
public class GameObject implements MouseListener, MouseMotionListener, KeyListener, Comparable
{
	// -- Variables --
	/**
	 * The <code>GameRoom</code> of this <code>GameObject</code>.
	 */
	public GameRoom room;

	/**
	 * Set to <code>true</code> to garbage collect this <code>GameObject</code> at the next chance.
	 */
	public boolean dead;


	/**
	 * The draw depth of this <code>GameObject</code>.
	 */
	public int depth = 0;

	/**
	 * The <code>GameSprite</code> associated with this <code>GameObject</code>.
	 */
	public GameSprite sprite;

	/**
	 * Some form of bounding volume of this <code>GameObject</code>.
	 */
	public BoundingVolume bound;

	/**
	 * The current transformation of this <code>GameObject</code>.
	 */
	public AffineTransform trans;

	/**
	 * A unique ID number for this <code>GameObject</code>.
	 */
	public int UID = -1;

	/**
	 * A flag showing whether or not this <code>GameObject</code> has been 
	 * updated this frame.
	 */
	public boolean updated = false;
	
	/**
	 * The number of <code>GameObjects</code> created.
	 */
	static int count = 0;

	// -- Object Functions -- 
	/**
	 * Class constructor. Initializes <code>sprite</code> and
	 * <code>bound</code> to <code>null</code>, creates a new transformation,
	 * assigns a unique UID, and updates <code>count</code>.
	 */
	public GameObject()
	{
		sprite = null;
		bound = null;
		trans = new AffineTransform();

		UID = count;
		count += 1;
	}

	/**
	 * Class constructor that initializes the <code>sprite</code> of this
	 * GameObject to a user-supplied <code>GameSprite</code>. Initializes
	 * <code>bound</code> to <code>null</code>, creates a new transformation,
	 * assigns a unique UID, and updates <code>count</code>.
	 *
	 * @param sprite    user-supplied <code>GameSprite</code>
	 */
	public GameObject(GameSprite sprite)
	{
		this.sprite = sprite;
		bound = null;
		trans = new AffineTransform();

		UID = count;
		count += 1;
	}

	/**
	 * Flags this <code>GameObject</code> to be garbage collected at the next
	 * chance.
	 */
	public void die()
	{
		dead = true;
	}

	/**
	 * Called once each frame by the <code>room</code> of this
	 * <code>GameObject</code>. Currently does nothing. Overwrite this method
	 * to supply your own actions.
	 *
	 * @param dt        the time elapsed since the last update, in milliseconds
	 */
	public void update(double dt)
	{
	}

	/**
	 * Draws the sprite associated with this <code>GameObject</code> onto the
	 * user-supplied buffer using the transformation stored with this
	 * <code>GameObject</code>.
	 *
	 * @param buffer    the buffer to draw the sprite
	 */
	public void draw(Graphics2D buffer)
	{
		if(sprite != null)
			sprite.draw(buffer, trans);
	}

	/**
	 * Tests if a point is within the bounds of this <code>GameObject</code>.
	 *
	 * @param x         <i>x</i> coordinate
	 * @param y         <i>y</i> coordinate
	 * @return          <code>true</code> if the (<i>x</i>, <i>y</i>) point is
	 *                  within the bounds of this <code>GameObject</code>;
	 *                  <code>false</code> otherwise.
	 */
	public boolean hit(double x, double y)
	{
		return (bound != null) && bound.collide(x, y);
	}

	/**
	 * Tests if a <code>BoundingVolume</code> intersects the bounds of this
	 * <code>GameObject</code>.
	 *
	 * @param b         the <code>BoundingVolume</code> to use in the collision
	 *                  test
	 * @return          <code>true</code> if <code>b</code> intersects the
	 *                  bounds of this <code>GameObject</code>;
	 *                  <code>false</code> otherwise.
	 */
	public boolean hit(BoundingVolume b)
	{
		return (bound != null) && bound.collide(b);
	}

	/**
	 * Tests if the bounds of a <code>GameObject</code> intersects the bounds
	 * of this <code>GameObject</code>.
	 *
	 * @param go        the <code>GameObject</code> to use in the collision test
	 * @return          <code>true</code> if the bounds of <code>go</code>
	 *                  intersects the bounds of this <code>GameObject</code>;
	 *                  <code>false</code> otherwise.
	 */
	public boolean hit(GameObject go)
	{
		return (bound != null) && bound.collide(go.bound);
	}

	/**
	 * Called by the <code>room</code> of this <code>GameObject</code> if it
	 * collides with any other <code>GameObject</code>. Currently does nothing.
	 * Overwrite this method to supply your own actions.
	 * 
	 * @param go        the other <code>GameObject</code> in the collision
	 */
	public void collided(GameObject go)
	{
	}

	/**
	 * Translates this <code>GameObject</code> in object space by (<i>x</i>,
	 * <i>y</i>).
	 *
	 * @param x         pixels to translate this <code>GameObject</code> in the
	 *                  <i>x</i>-direction
	 * @param y         pixels to translate this <code>GameObject</code> in the
	 *                  <i>y</i>-direction
	 */
	public void translateObjectSpace(double x, double y)
	{
		transformObjectSpace(AffineTransform.getTranslateInstance(x, y));
	}

	/**
	 * Rotates this <code>GameObject</code> in object space by specified
	 * radians.
	 *
	 * @param a         radians to rotate this <code>GameObject</code>
	 */
	public void rotateObjectSpace(double a)
	{
		transformObjectSpace(AffineTransform.getRotateInstance(a));
	}

	/**
	 * Translates this <code>GameObject</code> in world space by (<i>x</i>,
	 * <i>y</i>).
	 *
	 * @param x         pixels to translate this <code>GameObject</code> in the
	 *                  <i>x</i>-direction
	 * @param y         pixels to translate this <code>GameObject</code> in the
	 *                  <i>y</i>-direction
	 */
	public void translateWorldSpace(double x, double y)
	{
		transformWorldSpace(AffineTransform.getTranslateInstance(x, y));
	}

	/**
	 * Rotates this <code>GameObject</code> in world space by specified
	 * radians.
	 *
	 * @param a         radians to rotate this <code>GameObject</code>
	 */
	public void rotateWorldSpace(double a)
	{
		transformWorldSpace(AffineTransform.getRotateInstance(a));
	}

	/**
	 * Applies a transformation to this <code>GameObject</code> in object space.
	 *
	 * @param t         a transformation to apply to this <code>GameObject</code>
	 */
	public void transformObjectSpace(AffineTransform t)
	{
		trans.concatenate(t);
		if(bound != null)
			bound.transformObjectSpace(t);
	}

	/**
	 * Applies a transformation to this <code>GameObject</code> in world space.
	 *
	 * @param t         a transformation to apply to this <code>GameObject</code>
	 */
	public void transformWorldSpace(AffineTransform t)
	{
		trans.preConcatenate(t);
		if(bound != null)
			bound.transformWorldSpace(t);
	}

	/**
	 * Sets the tranformation of the <code>GameObject</code> and attempts to do
	 * the same for the bounding volume.
	 *
	 * @param t         the new transformation
	 */
	public void setTransform(AffineTransform t)
	{
		AffineTransform i;
		if(bound != null)
		{
			try
			{
				i = trans.createInverse();
				bound.transformWorldSpace(i);
			} catch(NoninvertibleTransformException e) {}
			
			bound.transformWorldSpace(t);
		}
		
		trans = new AffineTransform(t);
	}
	
	/**
	 * Returns the <i>x</i> coordinate of the center of this
	 * <code>GameObject</code>.
	 *
	 * @return          The <i>x</i> coordinate of the center of this
	 *                  <code>GameObject</code>.
	 */
	public double getX()
	{
		return trans.getTranslateX();
	}

	/**
	 * Returns the <i>y</i> coordinate of the center of this
	 * <code>GameObject</code>.
	 *
	 * @return          The <i>y</i> coordinate of the center of this
	 *                  <code>GameObject</code>.
	 */
	public double getY()
	{
		return trans.getTranslateY();
	}

	// -- MouseListener Functions --
	/**
	 * Called by the <code>room</code> when a mouse button has been clicked
	 * on this <code>GameObject</code>. Currently does nothing. Overwrite this
	 * method to supply your own actions.
	 * 
	 * @param e         details of the <code>MouseEvent</code>
	 */
	public void mouseClicked(MouseEvent e)
	{
	}

	/**
	 * Called by the <code>room</code> when a mouse button has been clicked
	 * anywhere. Currently does nothing. Overwrite this method to supply 
	 * your own actions.
	 * 
	 * @param e         details of the <code>MouseEvent</code>
	 */
	public void globalMouseClicked(MouseEvent e)
	{
	}

	/**
	 * Called by the <code>room</code> when a mouse button has been pressed
	 * on this <code>GameObject</code>. Currently does nothing. Overwrite this
	 * method to supply your own actions.
	 * 
	 * @param e         details of the <code>MouseEvent</code>
	 */
	public void mousePressed(MouseEvent e)
	{
	}

	/**
	 * Called by the <code>room</code> when a mouse button has been pressed
	 * anywhere. Currently does nothing. Overwrite this method to supply 
	 * your own actions.
	 * 
	 * @param e         details of the <code>MouseEvent</code>
	 */
	public void globalMousePressed(MouseEvent e)
	{
	}

	/**
	 * Called by the <code>room</code> when a mouse button has been released
	 * on this <code>GameObject</code>. Currently does nothing. Overwrite this
	 * method to supply your own actions.
	 * 
	 * @param e         details of the <code>MouseEvent</code>
	 */
	public void mouseReleased(MouseEvent e)
	{
	}

	/**
	 * Called by the <code>room</code> when a mouse button has been released
	 * anywhere. Currently does nothing. Overwrite this method to supply
	 * your own actions.
	 * 
	 * @param e         details of the <code>MouseEvent</code>
	 */
	public void globalMouseReleased(MouseEvent e)
	{
	}

	/**
	 * Called by the <code>room</code> when the mouse enters this
	 * <code>GameObject</code>. Currently does nothing. Overwrite this method to
	 * supply your own actions.
	 * 
	 * @param e         details of the <code>MouseEvent</code>
	 */
	public void mouseEntered(MouseEvent e)
	{
	}

	/**
	 * Called by the <code>room</code> when the mouse enters the applet. 
	 * Currently does nothing. Overwrite this method to supply your own 
	 * actions.
	 * 
	 * @param e         details of the <code>MouseEvent</code>
	 */
	public void globalMouseEntered(MouseEvent e)
	{
	}

	/**
	 * Called by the <code>room</code> when the mouse exits this
	 * <code>GameObject</code>. Currently does nothing. Overwrite this method to
	 * supply your own actions.
	 * 
	 * @param e         details of the <code>MouseEvent</code>
	 */
	public void mouseExited(MouseEvent e)
	{
	}

	/**
	 * Called by the <code>room</code> when the mouse exits the applet. 
	 * Currently does nothing. Overwrite this method to supply your own
	 * actions.
	 * 
	 * @param e         details of the <code>MouseEvent</code>
	 */
	public void globalMouseExited(MouseEvent e)
	{
	}

	// -- MouseMotionListener Functions --
	/**
	 * Called by the <code>room</code> when a mouse button is pressed on this
	 * <code>GameObject</code> and dragged. Currently does nothing. Overwrite
	 * this method to supply your own actions.
	 * 
	 * @param e         details of the <code>MouseEvent</code>
	 */
	public void mouseDragged(MouseEvent e)
	{
	}

	// -- MouseMotionListener Functions --
	/**
	 * Called by the <code>room</code> when a mouse button is pressed on the
	 * applet and dragged. Currently does nothing. Overwrite this method to
	 * supply your own actions.
	 * 
	 * @param e         details of the <code>MouseEvent</code>
	 */
	public void globalMouseDragged(MouseEvent e)
	{
	}

	/**
	 * Called by the <code>room</code> when the mouse cursor has been moved
	 * within this <code>GameObject</code> but no buttons have been pushed.
	 * Currently does nothing. Overwrite this method to supply your own actions.
	 * 
	 * @param e         details of the <code>MouseEvent</code>
	 */
	public void mouseMoved(MouseEvent e)
	{
	}

	/**
	 * Called by the <code>room</code> when the mouse cursor has been moved
	 * within the applet but no buttons have been pushed. Currently does
	 * nothing. Overwrite this method to supply your own actions.
	 * 
	 * @param e         details of the <code>MouseEvent</code>
	 */
	public void globalMouseMoved(MouseEvent e)
	{
	}

	// -- KeyListener Functions --
	/**
	 * Called by the <code>room</code> of this <code>GameObject</code> when a
	 * key has been pressed. Currently does nothing. Overwrite this method to
	 * supply your own actions.
	 * 
	 * @param e         details of the <code>KeyEvent</code>
	 */
	public void keyPressed(KeyEvent e)
	{
	}

	/**
	 * Called by the <code>room</code> of this <code>GameObject</code> when a
	 * key has been released. Currently does nothing. Overwrite this method to
	 * supply your own actions.
	 * 
	 * @param e         details of the <code>KeyEvent</code>
	 */
	public void keyReleased(KeyEvent e)
	{
	}

	/**
	 * Called by the <code>room</code> of this <code>GameObject</code> when a
	 * key has been typed. Currently does nothing. Overwrite this method to
	 * supply your own actions.
	 * 
	 * @param e         details of the <code>KeyEvent</code>
	 */
	public void keyTyped(KeyEvent e)
	{
	}

	// -- Comparable Functions --
	/**
	 * Allows objects to be sorted first by depth then by UID.
	 *
	 * @param o         the <code>Object</code> to compare with this
	 *                  <code>GameObject</code>
	 * @return          Negative if the draw depth of <code>o</code> is less
	 *                  than the draw depth of this <code>GameObject</code>;
	 *                  <code>0</code> if <code>o</code> is this <code>GameObject</code>;
	 *                  positive otherwise.
	 */
	public int compareTo(Object o)
	{
		GameObject go = (GameObject)o;
		if(depth == go.depth)       // Prevents objects at the same depth from being considered the same
			return UID - go.UID;
		return depth - go.depth;
	}
}
