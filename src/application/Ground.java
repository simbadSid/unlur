package application;

import java.util.Iterator;
import java.util.LinkedList;

import android.graphics.Point;








public class Ground
{
// --------------------------------------------- 
// Attributs:
//----------------------------------------------
		public final static int northWestSide	= 0;
		public final static int northSide		= 1;
		public final static int northEastSide	= 2;
		public final static int southEastSide	= 3;
		public final static int southSide		= 4;
		public final static int southWestSide	= 5;

		public final static int westMove		= 0;
		public final static int northWestmove	= 1;
		public final static int northEastMove	= 2;
		public final static int eastMove		= 3;
		public final static int southEastMove	= 4;
		public final static int southWestMove	= 5;
		private final static int nbrMove		= 6;

		public final static int	dimensionMin	= 2;

		private Case[][]	terrain;
		private int 		dimension;

// ---------------------------------------------
// Constructeur:
// ---------------------------------------------
		public Ground(int dimension)throws RuntimeException
		{
			if (dimension < dimensionMin)	throw new RuntimeException("dim < dimMin");
			this.dimension = dimension;
			this.reset(null);
		}
		public Ground(Ground gr)
		{
			this.dimension	= gr.dimension;
			this.reset(gr);
		}
		public void reset(Ground gr)
		{
			int height 		= this.dimension * 2 - 1;
			int width		= this.dimension;
			this.terrain	= new Case[height][];
			
			for (int i = 0; i < this.dimension; i++) 
			{
				this.terrain[i] = new Case[width];
				for(int j = 0; j < width; j++)
				{
					if (gr == null)	terrain[i][j] = new Case(Case.freeCase);
					else			terrain[i][j] = new Case(gr.terrain[i][j]);
				}
				width ++;
			}
			width -= 2;
			for (int i=this.dimension; i<height; i++)
			{
				this.terrain[i] = new Case[width];
				for(int j = 0; j < width; j++)
				{
					if (gr == null)	terrain[i][j] = new Case(Case.freeCase);
					else			terrain[i][j] = new Case(gr.terrain[i][j]);
				}
				width --;
			}
		}
// ---------------------------------------------
// Methodes Locales:
// ---------------------------------------------
		/**==================================================================
		 * Determine le suivant de p avec un mouvement dans la dirrection donne
		 * @param direction: valeurs donnes par les macros dirrection
		 * @return null si le point resultant est en dehors du terrain
		 ====================================================================**/
		public Point nextPosition(Point p, int direction)throws RuntimeException
		{
			int x = p.x;
			int y = p.y;
			int ymoy = dimension - 1;
			if (!isValidPosition(p.x, p.y)) 	   	throw new RuntimeException("point initiale en dehors du terrain");
			if ((direction < 0) || (direction > 5)) throw new RuntimeException("Dirrection non pris en charge");

			switch(direction)
			{
				case westMove:
													x = x - 1;				break;
				case northWestmove:
									if (y <= ymoy) 	{x = x - 1;	y = y - 1;	break;}
									else 			{			y = y - 1;	break;}
				case northEastMove:
									if (y <= ymoy) 	{			y = y - 1;	break;}
									else			{x = x + 1;	y = y - 1;	break;}
				case eastMove:
													x = x + 1;				break;
				case southEastMove:
									if (y >= ymoy)	{			y = y + 1;	break;}
									else			{x = x + 1;	y = y + 1;	break;}
				case southWestMove:
									if (y >= ymoy)	{x = x - 1;	y = y + 1;	break;}
									else			{			y = y + 1;	break;}
				default:
					throw new RuntimeException("Unknown direction identifiar");
			}
			if (!isValidPosition(x, y)) 	return null;				// Cas ou la nouvelle case est en dehors du terrain
			else							return new Point(x, y);
		}
		/**==========================================================
		 * Determine la plus petite distance necessaire pour aller de p1 a p2 
		 ============================================================**/
		public int dist(Point p1, Point p2)
		{
				int x1, y1, x2, y2, dx, dy;
				
				if (p1.equals(p2)) return 0;
				
				// Coordonnees hexagonale
				y1 = dimension - 1 - p1.y;
				y2 = dimension - 1 - p2.y;
				if (y1 >= 0)	x1 = p1.x - (dimension - 1);
				else			x1 = p1.x - (dimension - 1) - y1;
				if (y2 >= 0)	x2 = p2.x - (dimension - 1);
				else			x2 = p2.x - (dimension - 1) - y2;
	
				dx = x2 - x1;
				dy = y2 - y1;
	
				if (Math.signum(dx) == Math.signum(dy))
				{
					int a1 = Math.abs(dx);
					int a2 = Math.abs(dy);
					return Math.abs(a1 + a2);
				}
				else
				{
					int a1 = Math.abs(dx);
					int a2 = Math.abs(dy);
					return Math.max(a1, a2);
				}
		}
		/**================================================
		 * Rend une representation textuelle du terrain
		 ==================================================**/
		public String toString()
		{
			int height 	= dimension*2 -1;
			int width	= this.dimension;
			int space = dimension;
			String str = "";
			
			for (int i = 0; i < this.dimension; i++)
			{
				for (int j = 0; j<space; j++) str += "  ";
				for (int j = 0; j<width; j++) str += terrain[i][j].toString() + "   ";
				str += "\n\n";
				space --;
				width ++;
			}
			width -= 2;
			space +=2;
			for (int i=this.dimension; i<height; i++)
			{
				for (int j = 0; j<space; j++) str += "  ";
				for (int j = 0; j<width; j++) str += terrain[i][j].toString() + "   ";
				str += "\n\n";
				space ++;
				width --;
			}
			return str;
		}
// ----------------------------------------------
// Accesseur:
// ---------------------------------------------
		public int				getDimension()						{return this.dimension;}
		public boolean			isFreeCase	(int x, int y)			{return this.terrain[y][x].isFree();}
		public boolean			isBlackCase	(int x, int y)			{return this.terrain[y][x].isBlack();}
		public boolean			isWhiteCase	(int x, int y)			{return this.terrain[y][x].isWhite();}
		public boolean			isSameCase	(Point p0, Point p1)	{return this.terrain[p0.y][p0.x].equals(this.terrain[p1.y][p1.x]);}
		public int				getCaseType	(int x, int y)			{return this.terrain[y][x].getCaseType();}
		public Iterator<Point>	getNoBorderGroundIterator(int casee, boolean allowFreeCell){return new GroundIterator(casee, false, allowFreeCell);}
		public Iterator<Point>	getGroundIterator(int casee, boolean allowFreeCell)		{return new GroundIterator(casee, true, allowFreeCell);}
		public Iterator<Point>	getGroundIterator()					{return new GroundIterator();}
		public Iterator<Integer>getMoveIterator()					{return new MoveIterator();}
		public boolean			setCase		(int x, int y, int c)
		{
			if (terrain[y][x].equals(c)) return false;
			terrain[y][x] = new Case(c);
			return true;
		}
		public int getNbrCase()
		{
			int res = 0;
			for (int j=0; j<2*dimension-1; j++) res += terrain[j].length;
			return res;
		}
		/**=====================================================
		 * Determine si le terrain est plein
		 =======================================================**/
		public boolean isFull()
		{
			for (int i = 0; i<terrain.length; i++)
			{
				for (int j = 0; j<terrain[i].length; j++)
					if (terrain[i][j].isFree()) return false;
			}
			return true;
		}
		/**=====================================================
		 * Determine si le terrain central (prive des bord) est plein
		 =======================================================**/
		public boolean isCenterFull()
		{
			for (int i = 1; i<terrain.length-1; i++)
			{
				for (int j = 1; j<terrain[i].length-1; j++)
					if (terrain[i][j].isFree()) return false;
			}
			return true;
		}
		/**=====================================================
		 * Determine si une position (x, y) appartient au terrain
		 =======================================================**/
		public boolean isValidPosition(int x, int y)
		{
			int maxHeight = 2 * this.dimension - 1;
			if ((x < 0) || (y < 0)) 										return false;
			if  (y >= maxHeight)											return false;
			if ((y >= dimension ) && (x >= dimension + (maxHeight-1)%y)) 	return false;
			if  (x >= dimension + y)										return false;
			else															return true;
		}
		public boolean isSide(int x, int y)
		{
			if (getSideId(x, y) == -1) return false;
			else					 return true;
		}
		/**=====================================================
		 * Determine le cote correspondans au point (x, y)
		 * @param x
		 * @param y
		 * @return -1 si () n'appartien pas a un bord.
		 * Si non, l'un des macros directionnel est rendu
		 * @throws RuntimeException si (x, y) n'appartient pas au terrain
		 =======================================================**/
		public int getSideId(int x, int y)
		{
			int max = 2 * this.dimension - 2;

			if (!isValidPosition(x, y)) throw new RuntimeException("Position hors du terrain");
			
			if  (y == 0)													return northSide;
			if ((y < this.dimension)  && (x == this.dimension + y - 1))		return northEastSide;
			if ((y >= this.dimension) && (x == this.dimension + (max%y)-1)) return southEastSide;
			if  (y == max)													return southSide;
			if ((y >= this.dimension) && (x == 0))							return southWestSide;
			if ((y < this.dimension)  && (x == 0))							return northWestSide;
			else															return -1;
		}
		public LinkedList<Point> getSide(int side, int casee, boolean allowEmptyCell)
		{
			if ((side >= northWestSide) && (side <= northEastSide))		return getNorthSide(side, casee, allowEmptyCell);
			if ((side >= southEastSide) && (side <= southWestSide))		return getSouthSide(side, casee, allowEmptyCell);
			throw new RuntimeException("cote non pris en charge!");
		}
		/**=====================================================
		 * Determine la liste des case de type "casee" sur le bord "side"
		 * @param side : 0 pour Nord Ouest
		 * 			   : 1 pour Nord
		 * 			   : 2 pour Nord Est
		 * Ce parametre peut etre donne par les macros de cette classe
		 * @param casee: type de case a chercher
		 * @return null si aucun point n'est trouve
		 =======================================================**/
		public LinkedList<Point> getNorthSide(int side, int casee, boolean allowEmptyCell)
		{
			if (!Case.isValidCase(casee)) throw new RuntimeException("Type de case non pris en charge.");

			LinkedList<Point> res = new LinkedList<Point>();
			int nbrPoint = 0;

			switch(side)
			{
				case 0:															// Nord Ouest
					for (int y = 0; y<dimension; y++)
					{
						if		(terrain[y][0].equals(casee))				{nbrPoint ++;res.add(new Point(0, y));}
						else if (terrain[y][0].isFree() && allowEmptyCell)	{nbrPoint ++;res.add(new Point(0, y));}
					}
					break;
				case 1:															// Nord
					for (int x = 0; x<dimension; x++)
					{
						if		(terrain[0][x].equals(casee))				{nbrPoint ++;res.add(new Point(x, 0));}
						else if (terrain[0][x].isFree() && allowEmptyCell)	{nbrPoint ++;res.add(new Point(x, 0));}
					}
					break;
				case 2:															// Nord Est
					int x = dimension-1;
					for (int y = 0; y<dimension; y++)
					{
						if		(terrain[y][x+y].equals(casee))				{nbrPoint ++;res.add(new Point(x+y, y));}
						else if (terrain[y][x+y].isFree() && allowEmptyCell){nbrPoint ++;res.add(new Point(x+y, y));}
					}
					break;
				default:
					throw new RuntimeException("Erreur dans \"UnlurApplication.northSide\": cote non pris en charge.");
			}
			if (nbrPoint == 0)  return null;
			else				return res;
		}
		/**============================================================
		 * Determine la liste des case de type "casee" sur le bord nord "side"
		 * @param side : 3 pour Sud Est
		 * 			   : 4 pour Sud
		 * 			   : 5 pour Sud Ouest
		 * Ce parametre peut etre donne par les macros de cette classe
		 * @param casee: type de case a chercher
		 * @return 	   : null si aucun point n'est trouve
		 ==============================================================**/
		public LinkedList<Point> getSouthSide(int side, int casee, boolean allowEmptyCell)
		{
			if (!Case.isValidCase(casee)) throw new RuntimeException("Erreur dans \"UnlurApplication.getNorthSide\": type de case non pris en charge.");
	
			LinkedList<Point> res = new LinkedList<Point>();
			int nbrPoint = 0;
			int x, y;
	
			switch(side)
			{
				case 3:															// Sud Est
					x = 2*dimension - 2;
					y = dimension - 1;
					for (int i = 0; i<dimension; i++)
					{
						if		(terrain[y][x].equals(casee))				{nbrPoint ++;res.add(new Point(x, y));}
						else if (terrain[y][x].isFree() && allowEmptyCell)	{nbrPoint ++;res.add(new Point(x, y));}
						x--;
						y++;
					}
					break;
				case 4:															// Sud
					for (x = 0; x<dimension; x++)
					{
						y = getDimension() * 2 - 2;
						if		(terrain[y][x].equals(casee)) 				{nbrPoint ++;res.add(new Point(x, y));}
						else if (terrain[y][x].isFree() && allowEmptyCell)	{nbrPoint ++;res.add(new Point(x, y));}
					}
					break;
				case 5:															// Sud Ouest
					for (y = dimension-1; y<2*dimension-1; y++)
					{
						if		(terrain[y][0].equals(casee))				{nbrPoint ++;res.add(new Point(0, y));}
						else if (terrain[y][0].isFree() && allowEmptyCell)	{nbrPoint ++;res.add(new Point(0, y));}
					}
					break;
				default:
					throw new RuntimeException("Erreur dans \"UnlurApplication.northSide\": cote non pris en charge: "+ side +".");
			}
			if (nbrPoint == 0)  return null;
			else				return res;
		}
// ---------------------------------------------
// Iterateur sur les identifiant des types 
// de mouvements:
// ---------------------------------------------
		public class MoveIterator implements Iterator<Integer>
		{
			private LinkedList<Integer> l;
			public MoveIterator()
			{
				this.l = new LinkedList<Integer>();
				for (int i=0; i<nbrMove; i++)	l.addLast(i);
			}
			public Integer next()
			{
				int res = l.getFirst();
				l.removeFirst();
				return res;
			}
			public boolean hasNext(){return !l.isEmpty();}
			public void remove()	{}
		}
// ---------------------------------------------
// Iterateur sur les case du terrain:
// Les cases rendus par l'iterateur sont des cases centrales
// de type donnees
// n'appartenant pas aux bords:
// ---------------------------------------------
		public class GroundIterator implements Iterator<Point>
		{
			// Attributs
			LinkedList<Point> l;
			
			// Constructeur sans bord et avec la case casee
			public GroundIterator (int casee, boolean border, boolean allowFreeCell) throws RuntimeException
			{
				if (!Case.isValidCase(casee)) throw new RuntimeException("Unknown case type: " + casee);
				this.l = new LinkedList<Point>();
				for (int i = 0; i < terrain.length; i++)
				{
					if ((!border) && ((i==0) || (i== terrain.length-1)))		continue;
					for (int j = 0; j<terrain[i].length; j++)
					{
						if ((!border) && ((j==0) || (j== terrain[i].length-1)))	continue;
						Case c = terrain[i][j];
						if		(c.equals(casee))					l.add(new Point(j, i));
						else if	((c.isFree()) && (allowFreeCell))	l.add(new Point(j, i));
					}
				}
			}
			// Constructeur avec toutes les cases
			public GroundIterator ()
			{
				this.l = new LinkedList<Point>();
				for (int i = 0; i < terrain.length; i++)
				{
					for (int j = 0; j<terrain[i].length; j++) l.add(new Point(j, i));
				}
			}
			// Methodes Locales
			public boolean hasNext() 
			{
				return (!l.isEmpty());
			}
			public Point next()
			{
				Point res = l.get(0);
				l.remove(0);
				return res;
			}
			public void remove() {}
		}
}