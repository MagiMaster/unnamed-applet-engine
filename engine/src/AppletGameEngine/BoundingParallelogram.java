package AppletGameEngine;

import java.awt.geom.*;

/**
 * Bounding volume using parallelograms.
 *
 * @author  Tze-I Yang
 */
public class BoundingParallelogram implements BoundingVolume {
	private AffineTransform corner;
	private double[][] p;
	private double[] oy;
	private double[] py;
	private double[] length;
	private double[][] o_projected;
	private double[][] p_projected;
	private int[][] v;
	private static double[][] temp = new double[2][2];
	
	public BoundingParallelogram(double x, double y, double dx1, double dy1, double dx2, double dy2)
	{
		corner = new AffineTransform(dx1, dy1, dx2, dy2, x, y);
		p = new double[4][2];
		oy = new double[2];
		py = new double[2];
		length = new double[2];
		o_projected = new double[2][2];
		p_projected = new double[2][2];
		v = new int[2][2];
		updateLocals();
	}
	
	public BoundingParallelogram(double x, double y, double width, double height)
	{
		this(x, y, 0, height, width, 0);
	}
	
	public AffineTransform getTransform()
	{
		return corner;
	}
	
	private void updateLocals()
	{
		double[] m = new double[6];
		corner.getMatrix(m);
		p[0][0] =               m[4];
		p[1][0] =        m[2] + m[4];
		p[2][0] = m[0] + m[2] + m[4];
		p[3][0] = m[0] +        m[4];
		
		p[0][1] =               m[5];
		p[1][1] =        m[3] + m[5];
		p[2][1] = m[1] + m[3] + m[5];
		p[3][1] = m[1] +        m[5];
		
		oy[0] = (m[1] == 0) ? Double.POSITIVE_INFINITY : - m[0]/m[1];
		oy[1] = (m[3] == 0) ? Double.POSITIVE_INFINITY : - m[2]/m[3];
		length[0] = (m[1] == 0) ? 1 : Math.sqrt(1 + oy[0]*oy[0]);
		length[1] = (m[3] == 0) ? 1 : Math.sqrt(1 + oy[1]*oy[1]);
		projectMinMax(oy[0], p, o_projected[0], null);
		projectMinMax(oy[1], p, o_projected[1], null);
		
		py[0] = (m[0] == 0) ? Double.POSITIVE_INFINITY : m[1]/m[0];
		py[1] = (m[2] == 0) ? Double.POSITIVE_INFINITY : m[3]/m[2];
		projectMinMax(py[0], p, p_projected[0], v[0]);
		projectMinMax(py[1], p, p_projected[1], v[1]);
	}
	
	public void getPoints(double[][] p)
	{
		for(int i = 0; i < p.length; ++i)
			for(int j = 0; j < p[0].length; ++j)
				p[i][j] = this.p[i][j];
	}
	
	private void project(double oy, double x, double y, double[] q)
	{
		q[0] = (oy == Double.POSITIVE_INFINITY) ? y : x + oy*y;
	}
	
	private void projectMinMax(double oy, double[][] p, double[] q, int[] v)
	{
		double t;
		q[0] = (oy == Double.POSITIVE_INFINITY) ? p[0][1] : p[0][0] + oy*p[0][1];
		q[1] = q[0];
		if(v == null)
			for(int j = 1; j < p.length; ++j)
			{
				t = (oy == Double.POSITIVE_INFINITY) ? p[j][1] : p[j][0] + oy*p[j][1];
				if(t < q[0])
					q[0] = t;
				else
					if(t > q[1])
						q[1] = t;
			}
		else
			for(int j = 1; j < p.length; ++j)
			{
				t = (oy == Double.POSITIVE_INFINITY) ? p[j][1] : p[j][0] + oy*p[j][1];
				if(t < q[0])
				{
					q[0] = t;
					v[0] = j;
				}
				else
					if(t > q[1])
					{
						q[1] = t;
						v[1] = j;
					}
			}
	}

	public boolean collide(BoundingVolume o)
	{
		if((o == null) || !(o instanceof BoundingVolume))
			return false;
		if(o instanceof BoundingParallelogram) {
			//test projections using separating axes
			BoundingParallelogram a = (BoundingParallelogram)o;
			for(int i = 0; i < a.o_projected.length; ++i)
			{
				a.projectMinMax(a.oy[i], p, temp[i], null);
				if((temp[i][0] > a.o_projected[i][1]) || (temp[i][1] < a.o_projected[i][0]))
					return false;
			}
			for(int i = 0; i < o_projected.length; ++i)
			{
				projectMinMax(oy[i], a.p, temp[i], null);
				if((temp[i][0] > o_projected[i][1]) || (temp[i][1] < o_projected[i][0]))
					return false;
			}
			return true;
		}
		if(o instanceof BoundingCircle)
		{
			//test projections using separating axes
			BoundingCircle a = (BoundingCircle)o;
			double r = a.getR();
			double[] c = a.getC();
			double l;
			for(int i = 0; i < o_projected.length; ++i)
			{
				l = r * length[i];
				project(oy[i], c[0], c[1], temp[i]);
				if((temp[i][0] - l > o_projected[i][1]) || (temp[i][0] + l < o_projected[i][0]))
					return false;
			}
			//need to check parallelogram vertices to cicle center
			int vertex = 15;
			for(int i = 0; i < p_projected.length; ++i)
			{
				project(py[i], c[0], c[1], temp[i]);
				if(temp[i][0] > p_projected[i][1])
					if(i == 0)
						vertex &= (v[i][1] == 0) ? (1 + 8) : (2 + 4);
					else
						vertex &= (v[i][1] == 0) ? (1 + 2) : (4 + 8);
				else
					if(temp[i][0] < p_projected[i][0])
						if(i == 0)
							vertex &= (v[i][0] == 0) ? (1 + 8) : (2 + 4);
						else
							vertex &= (v[i][0] == 0) ? (1 + 2) : (4 + 8);
			}
			for(int i = 0; vertex > 0 && i < p.length; vertex >>= 1, ++i)
			{
				if(vertex == 1)
				{
					temp[1][1] = (p[i][0] == c[0]) ? Double.POSITIVE_INFINITY : (p[i][1] - c[1]) / (p[i][0] - c[0]);
					l = r * ((temp[1][1] == Double.POSITIVE_INFINITY) ? 1 : Math.sqrt(1 + temp[1][1]*temp[1][1]));
					projectMinMax(temp[1][1], p, temp[0], null);
					project(temp[1][1], c[0], c[1], temp[1]);
					if((temp[1][0] - l > temp[0][1]) || (temp[1][0] + l < temp[0][0]))
						return false;
					break;
				}
				if((vertex & 1) == 1)
					break;
			}
			return true;
		}
		return o.getClass().cast(o).collide(this);
	}

	public boolean collide(double x, double y)
	{
		for(int i = 0; i < o_projected.length; ++i)
		{
			project(oy[i], x, y, temp[i]);
			if((temp[i][0] > o_projected[i][1]) || (temp[i][0] < o_projected[i][0]))
				return false;
		}
		return true;
	}

	public void transformObjectSpace(AffineTransform t)
	{
		corner.concatenate(t);
		updateLocals();
	}
	
	public void transformWorldSpace(AffineTransform t)
	{
		corner.preConcatenate(t);
		updateLocals();
	}
}
