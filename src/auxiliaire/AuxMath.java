package auxiliaire;

import android.graphics.Point;









public class AuxMath
{
	/**====================================================
	 * Determine le produit vectoriel entre 
	 * les vecteur P0P1 et P2P3
	 * ===================================================**/
	public static double vect(Point p0, Point p1, Point p2, Point p3)
	{
		int v1x = p1.x - p0.x;
		int v2x = p3.x - p2.x;
		int v1y = (p1.y - p0.y) * (-1);		// -1 : cf orientation de l'axe des abscices
		int v2y = (p3.y - p2.y) * (-1);
		return (v1x * v2y - v1y * v2x);
	}
	/** ==================================================
	 * Angle entre (P0P1, P2P3)
	 * @return nl'angle directe entre [0, 2PI[
	 * ===================================================**/
	public static double angle(Point p0, Point p1, Point p2, Point p3)
	{
		if (p0.equals(p1))	throw new RuntimeException("p0 == p1");
		if (p2.equals(p3))	throw new RuntimeException("p2 == p3");
		double v1x	=  p1.x - p0.x;
		double v2x	=  p3.x - p2.x;
		double v1y	= (p1.y - p0.y)*(-1);
		double v2y	= (p3.y - p2.y)*(-1);
		double d	= dist(p0, p1) * dist(p2, p3);
		double cos	= (v1x*v2x + v1y*v2y) / d;
		double sin	= (v1x*v2y - v1y*v2x) / d;

		if ((sin == 0) && (cos == 0)) throw new RuntimeException("Erreur dans la fonction angle.");
		if (cos == 0)
		{
			if (sin > 0) return   Math.PI/2;
			if (sin < 0) return 3*Math.PI/2;
		}
		if (sin == 0)
		{
			if (cos > 0) return 0;
			if (cos < 0) return Math.PI;
		}
		double teta = Math.atan(sin/cos);
		if (teta > 0)
		{
			if ((sin > 0) && (cos > 0)) return teta;
			if ((sin < 0) && (cos < 0)) return teta + Math.PI;
		}
		if (teta < 0)
		{
			if ((sin > 0) && (cos < 0)) return (teta + Math.PI);
			if ((sin < 0) && (cos > 0)) return (teta + Math.PI*2.);
		}
		return (double) 0;
	}
	/** ==================================================
	 * rend la distance (absolue) entre p0 et p1
	 * ===================================================**/
	public static double dist(Point p0, Point p1)
	{
		double x	= p0.x - p1.x;
		double y	= p0.y - p1.y;
		return Math.sqrt(x*x + y*y);
	}
	/**===================================================
	 * Rend 1 si x >= 0
	 * Rend -1 si non
	 =====================================================*/
	public static int signum(double x)
	{
		if (x >= 0)	return 1;
		else		return -1;
	}
}