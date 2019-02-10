package ihm;

import java.util.Iterator;

import android.graphics.Point;
import auxiliaire.AuxMath;
import application.Application;
import application.Ground;














public class GraphicGround 
{
// --------------------------------------------- 
// Attributs:
//----------------------------------------------
		private int	 			dimGround			= -1;
		private int				width				= -1;		// Largeur du panneau
		private int				height				= -1;		// Hauteur du panneau
		private Point			cr;								// Coordonnes reel de l'hexagone centrale (referentiel de la fenetre graphique)
		private int				widthHexa;						// Largeur d'une case (hexagone)
		private int				heightHexa;						// Hauteur d'une case (hexagone)
		private int				diagoHexa;						// Hauteur d'une diagonele de l'hexagone (120° du cercle trigo)
		private int				radius;							// Rayon d'un cercle joue
		private final double 	freeSpaceCircle		= 8./10.;	// Portion d'espace libre d'un cercle

// ---------------------------------------------
// Accesseur
// ---------------------------------------------
	public int		getRadiusPlay()	{return this.radius;}
	public int		getDim()		{return this.dimGround;}
	public boolean	isInitialized()	{return ((dimGround	> 0)&&
											 (width		> 0)&&
											 (height	> 0));}

// ---------------------------------------------
// Methodes Locales:
// ---------------------------------------------
	/**========================================================================
	 * Converti un point du referentiel reel(de la fenetre graphique) au
	 * referentiel du terrain
	 * @return null si le pReal est en dehors du terrain
	 ==========================================================================**/
	public Point realToGround(Point p)
	{
		Point centerHexa;
		centerHexa = realToHexa(p);												// Determiner les coordonnees hexa arrondies de p
		int x = centerHexa.x;
		int y = centerHexa.y;
		int dim = this.dimGround;

		if ((x > dim) || (y > dim) || (x < -dim) || (y < -dim))  return null;	// Cas ou p est en dehors du terrain (scan grossier)
																				// Tester l'appartenance de p aux hexagones
																				//   adjacents superieurs:
		centerHexa = new Point(x + 1, y);										// 		Point Est
		if (isInHexagon(centerHexa, p)) return hexaToGround(centerHexa);

		centerHexa = new Point(x + 1, y + 1);									//		Point Nord Est
		if (isInHexagon(centerHexa, p)) return hexaToGround(centerHexa);

		centerHexa = new Point(x, y + 1);										//		Point Nord Ouest
		if (isInHexagon(centerHexa, p)) return hexaToGround(centerHexa);

		centerHexa = new Point(x - 1, y);										//		Point Ouest
		if (isInHexagon(centerHexa, p)) return hexaToGround(centerHexa);

		centerHexa = new Point(x, y);											// 		Point central								
		return hexaToGround(centerHexa);		
	}
	/**==========================================================================
	 * @param index d'une case du terrain
	 * @return coordonne du centre de la case (coordonnees reels)
	 ==========================================================================**/
	public Point groundToReal(Point p)
	{
		Ground t = new Ground(this.dimGround);
		if (!t.isValidPosition(p.x, p.y)) throw new RuntimeException("Entree invalide: " + p );

		int y = this.dimGround - 1 - p.y;			//Coordones hexagonale du centr de la case
		int x;
		if (p.y >= dimGround)	x = p.x - (this.dimGround-1);
		else					x = p.x - p.y;
		Point res = new Point(x, y);

		return hexaToReal(res);
	}
	/**==========================================================================
	 * Determine le sommet s de l'hexagone de centre center
	 * @param center: centre de l'hexagone en coordonnees reel
	 * @param s: index du sommet recherche:
	 * 			0: Nord Est
	 * 			1: Nord
	 * 			2: Nord Ouest
	 *			... tourner dans le sense trigo
	 * @return Le sommet recherche en coordonnes reel
	 ==========================================================================**/
	public Point summit(Point center, int s)
	{
		switch (s)
		{
			// Sommet Nord Est 
			case 0:		return new Point(center.x + widthHexa/2,
										 center.y - (int)(widthHexa/2 * Math.tan(Math.PI/6)));
			// Sommet Nord
			case 1:		return new Point(center.x,
										 center.y - heightHexa/2);
			// Sommet Nord Ouest 
			case 2:		return new Point(center.x - widthHexa/2,
										 center.y - (int)(widthHexa/2 * Math.tan(Math.PI/6)));
			// Sommet Sud Ouest 
			case 3:		return new Point(center.x - widthHexa/2,
										 center.y + (int)(widthHexa/2 * Math.tan(Math.PI/6)));
			// Sommet Sud 
			case 4:		return new Point(center.x,
										 center.y + heightHexa/2);
			// Sommet Sud Est
			case 5:		return new Point(center.x + widthHexa/2,
										 center.y + (int)(widthHexa/2 * Math.tan(Math.PI/6)));
		}
		throw new RuntimeException("Unlnown summit id");
	}
	/**=============================================================
	 * redimensionne le panneau
	 =============================================================**/
	public void resize(int width, int height)
	{
		if (width	<= 0)			throw new RuntimeException("Width <= 0: "	+ width);
		if (height	<= 0)			throw new RuntimeException("Height <= 0: "	+ height);

		this.width			= width;
		this.height			= height;

		if (this.dimGround != -1) this.initAttributs();
	}
	/**=============================================================
	 * etabli la dimmension du jeux
	 =============================================================**/
	public void resetDim(int dim)
	{
		if (dim < Application.minDim)	throw new RuntimeException("Game dimension < minDim: " + dim);
		if (dim > Application.maxDim)	throw new RuntimeException("Game dimension > maxDim: " + dim);

		this.dimGround		= dim;

		if (this.width != -1) this.initAttributs();
	}
	/**=============================================================
	 * @return un iterateur sur l'ensemble des centres 
	 * 			des hexagones du terrain
	 * 	Les Coordonnees des centre sont dans le referentiel reel
	 =============================================================**/
	public Iterator<Point> getHexagonRealCenterIterator()
	{
		class HexagonRealCenterIterator implements Iterator<Point>
		{
			// Attributs
			private Iterator<Point> it = new Ground(dimGround).getGroundIterator();

			// Methodes Locales
			public boolean hasNext()	{return it.hasNext();}
			public Point next()			{return groundToReal(it.next());}
			public void remove()		{}
		}
		return new HexagonRealCenterIterator();
	}
// ---------------------------------------------
// Methodes Auxiliaires:
// ---------------------------------------------
	/**==========================================================================
	 * Determine l'abscisse de P dans le referentiel Hexagonal.
	 * Le resultat est arondi a l'hexagone sud ouest le plus proche
	 * @param p: Point dans le referentiel Reel
	 * @return : les coordones du centre de l'hexagone sud ouest le plus proche
	 ==========================================================================**/
	private Point realToHexa(Point p)
	{
		double x	= p.x - this.cr.x;					// Coordonnee de p dans le ref cartesien de centre this.cr et de vecteur y inversé
		double y	= this.cr.y - p.y;

		double w = 1 / (double)widthHexa;
		double h = 2 / (double)(heightHexa * 3);

		x		= x*w + y * h;							// Coordonnes de p dans le ref hexagonale
		y		= 		y * 2 * h;

		int xi	= (int)Math.floor(x);					// On arondi a l'hexagone sud ouest
		int yi	= (int)Math.floor(y);
		return (new Point(xi, yi));					
	}
	/**==========================================================================
	 * Determine si le point pReal dans le referentiel reel appartien a 
	 * 			l'hexagone de centre centerHexa (dans le referentiel hexagonal)
	 * Methode: Pour tout p1, p2 sommet successifs de l'hexa (dans le sens trigo) on a:
	 * 			si l'angle (pReal p1, pReal p2) est negatif, alors pReal n'appartient pas a l'hexagone
	 * @param centerHexa: coordonnees du centre de l'hexagone dans le ref hexagonal
	 * @param pReal: point a tester (dans le referentiel reel)
	 ==========================================================================**/
	private boolean isInHexagon(Point centerHexa, Point pReal)
	{
		Point c = hexaToReal(centerHexa);
		Point s1 = summit(c, 0);
		Point s2;

		for (int i = 1; i<7; i++)							
		{
			s2 = summit(c, i%6);
			double vect = AuxMath.vect(pReal, s1, pReal, s2);
			if (vect < 0) return false;
			s1 = s2;
		}

		return true;
	}
	/**==========================================================================
	 * Convertit le point centerHexa (dans le repere hexagonal) en une case du terrain
	 * @param centerHexa point en coordonne hexagonale. Represente le centre d'un hexagone
	 * @return null si la case trouvee n'appartien pas au terrain
	 ==========================================================================**/
	private Point hexaToGround(Point centerHexa)
	{
		int x, y, dim = this.dimGround;
		Ground terrain = new Ground(dim);

								y = dim - 1 - centerHexa.y;
		if (centerHexa.y < 0)	x = dim - 1 + centerHexa.x;
		else					x = dim - 1 + centerHexa.x - centerHexa.y;

		if (!terrain.isValidPosition(x, y))	return null;
		else								return new Point(x, y);
	}
	/**==========================================================================
	 * Convertit le point centerHexa (dans le repere hexagonal) en coordonnee reel
	 * @param centerHexa point en coordonne hexagonale. Represente le centre d'un hexagone
	 ==========================================================================**/
	private Point hexaToReal(Point centerHexa)
	{
		double	x, y;

		x = centerHexa.x;											// Coordonnees hexagonale
		y = centerHexa.y;

		x = x * widthHexa - y * widthHexa / 2;						// Coordonnees cartesienne centree en this.cr
		y = 				y * diagoHexa * Math.cos(Math.PI/6);

		x = cr.x + x;												// Coordonnees cartesienne reel
		y = cr.y - y;

		return new Point((int)x, (int)y);
	}
	private void initAttributs()
	{
		int nbr = 2*dimGround - 1;	// Nombre max de cases par ligne

		this.cr		 		= new Point(width/2, height/2);

		if (width > height)
		{
			double l = height;
			double h = l / (1. + (nbr-1.)*(1.-(.5*Math.sin(Math.PI/6.))));
			this.heightHexa = (int)h;
			this.widthHexa	= (int)(h * Math.cos(Math.PI/6));
		}
		else
		{
			double l = width;
			int secure = 0;
			int totalHeight = 0;
			do
			{
				l -= secure;
				secure += 10;
				double w = l / nbr;
				this.widthHexa	= (int) w;
				this.heightHexa	= (int)(w / Math.cos(Math.PI/6));
				totalHeight = heightHexa * (2*dimGround - 1);
			}while(totalHeight > height);
		}

		this.diagoHexa	= (int)(this.heightHexa	* Math.cos(Math.PI/6));
		this.radius		= (int)(this.widthHexa	* this.freeSpaceCircle)/2;
	}
}