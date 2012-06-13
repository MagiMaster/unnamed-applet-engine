package AppletGameEngine;

import java.awt.geom.*;

public class PhysicsObject extends GameObject
{
	private double _vx, _vy;
	private double _ax, _ay;
	private double _dt;
	private double fX, fY, mass, friction, limit;
	
	public PhysicsObject(double x, double y)
	{
		super();
		
		setTransform(AffineTransform.getTranslateInstance(x, y));
		_vx = _vy = 0.0;
		_ax = _ay = 0.0;
		fX = fY = 0.0;
		mass = 1;
		friction = 0.0;
		_dt = -1;
		limit = 1.0/4000000.0;
	}
	
	public PhysicsObject(double x, double y, double mass)
	{
		super();
		
		setTransform(AffineTransform.getTranslateInstance(x, y));
		_vx = _vy = 0.0;
		_ax = _ay = 0.0;
		fX = fY = 0.0;
		this.mass = 1.0/mass;
		friction = 0.0;
		_dt = -1;
		limit = 1.0/4000000.0;
	}
	
	public void setMass(double m)
	{
		mass = 1.0/m;
	}

	public double getMass()
	{
		return 1.0/mass;
	}
	
	public void setFriction(double f)
	{
		friction = f;
	}
	
	// Defaults to 2000
	public void setSpeedLimit(double l)
	{
		limit = 1.0/(l*l);
	}
	
	public void update(double dt)
	{
		if(_dt < 0)
			_dt = dt;

		double ax = (fX - _vx*friction)*mass;
		double ay = (fY - _vy*friction)*mass;
		
		double ux = 0.5*(_dt*_ax + dt*ax);
		double uy = 0.5*(_dt*_ay + dt*ay);
		double div = 1.0/(1.0 + Math.sqrt((ux*ux + uy*uy)*(_vx*_vx + _vy*_vy))*limit);
		double vx = (_vx + ux)*div;
		double vy = (_vy + uy)*div;
		double x = getX() + dt*vx;
		double y = getY() + dt*vy;
		
		setTransform(AffineTransform.getTranslateInstance(x, y));
		_dt = dt;
		_vx = vx;
		_vy = vy;
		_ax = ax;
		_ay = ay;
		
		fX = fY = 0.0;
	}
	
	public void applyForce(double fx, double fy)
	{
		this.fX += fx;
		this.fY += fy;
	}

	public void applyAcceleration(double ax, double ay)
	{
		this.fX += ax/mass;
		this.fY += ay/mass;
	}
	
	public double getVelocityX()
	{
		return _vx;
	}

	public double getVelocityY()
	{
		return _vy;
	}
	
	public void teleport(double x, double y)
	{
		setTransform(AffineTransform.getTranslateInstance(x, y));
	}
}
