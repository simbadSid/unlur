package application;

import java.util.Iterator;
import java.util.LinkedList;

import android.graphics.Point;
import exception.AnalyzerException;











/**********************************************************************
 * @author SID-LAKHDAR Riyane
 * Determine le plus court chemain entre deux case du terrain en 
 * utilisant l'algorithme A*.
 **********************************************************************/




public class PathFinder 
{
// ----------------------------------------------- 
// Attributs:
// -----------------------------------------------
	public static final int		difrentCaseTypeMalusse	= 3;
	public static final int		sameCaseTypeMalusse		= 0;

	private Ground 				ground;
	private LinkedList<Noeud>	openList;
	private LinkedList<Noeud>	closeList;
	
// ----------------------------------------------
// Constructeur:
// ----------------------------------------------
	public PathFinder(Ground g)
	{
		this.ground 		= g;
		this.closeList 		= new LinkedList<Noeud>();
		this.openList 		= new LinkedList<Noeud>();
	}
// ---------------------------------------------
// Methodes Locales:
// ---------------------------------------------	
		/**===================================================================
		 * Determine le plus court chemain entre source et destination.
		 * Le chemain trouvee est compose des memes case que source et destination
		 * @return null si auccun chemain n'est trouve. Si non La methodes rend la 
		 * liste des point(cases) formant le chemain
		 * @throws AnalyzerException: si les point source et destination 
		 * 		- n'appartenent pas au terrain
		 * 		- ne sont pas de meme type
		 ======================================================================*/
		public LinkedList<Point> findPath(Point src, Point dst, int pathCaseType, boolean allowFreeCase)throws AnalyzerException
		{
			boolean isPath 	= false;
			int dist, tNext;
			Integer malusse;
			Noeud nCurrent, nNext=null;
			Point pCurrent, pNext;
			Iterator<Integer> it;

			if (!this.ground.isValidPosition(src.x, src.y))	throw new AnalyzerException();
			if (!this.ground.isValidPosition(dst.x, dst.y))	throw new AnalyzerException();

			dist			= this.ground.dist(src, dst);
			malusse			= computeMalusse(src, pathCaseType, allowFreeCase);
			if (malusse < 0) throw new RuntimeException("Unreacheable source point: " + src);
			this.closeList	= new LinkedList<Noeud>();
			this.openList	= new LinkedList<Noeud>();
			this.openList	.add(new Noeud(null, src, malusse, dist));
			do
			{
				nCurrent = getBestFromOpenList();								// Definir le point courrent
				pCurrent = nCurrent.current;
				closeList.add(nCurrent);										// L'ajouter dans la liste fermee
				it		= ground.getMoveIterator();
				while (it.hasNext())											// Pour chaque suivant possible:
				{
					pNext = ground.nextPosition(pCurrent, it.next());
					if (pNext == null)						continue;			// 		Cas ou le suivant est en dehors du terrain
					tNext = ground.getCaseType(pNext.x, pNext.y);
					if (ground.isFreeCase(pNext.x, pNext.y))
					{															//		Cas ou lesuivant est vides et que les cases vides ne sont pas acceptes
						if (!allowFreeCase)					continue;
					}
					else
					{
						if (tNext != pathCaseType)			continue;			//		Case ou le suivant n'est pas de meme type
					}
					if ( isInCloseList(pNext))				continue;			// 		Cas ou le suivant est deja dans la liste fermee
					nNext	= getFromOpenList(pNext);
					malusse	= computeMalusse(pNext, pathCaseType, allowFreeCase);
					int bc 	= nCurrent.backwardCost + 1 + malusse;
					if (malusse < 0) throw new RuntimeException("malusse: " + malusse);
					if (nNext == null)											//		Cas ou le suivant n'est pas dans la liste ouverte
					{
						int fc 	= ground.dist(pNext, dst);
						nNext	= new Noeud(nCurrent, pNext, bc, fc);
						openList.add(nNext);
					}
					else														//		Cas ou le suivant est deja dans la liste ouverte:
					{
						if (nNext.backwardCost>bc)								//			Si sa distance est plus grande que la nuovelle
						{
							int fc	= nNext.forwardCost;
							nNext	= new Noeud(nCurrent, pNext, bc, fc);
						}
						openList.add(nNext);
					}
					if (pNext.equals(dst))										// 		Cas ou le suivant est le point destination
					{
						isPath = true;
						break;
					}
				}
				if (isPath == true) break;
			}while(openList.size() > 0);
			if (isPath == false) 	return null;								// Cas ou il n'y a pas de chemain
			else					return buildPath(src, dst, nNext);
		}

// ---------------------------------------------
// Methodes auxilliaires:
// ---------------------------------------------
		/**=========================================================
		 * Construit le chemin de n.current jusqu'a src
		 ===========================================================*/
		private LinkedList<Point> buildPath(Point source, Point destination, Noeud n)
		{
			LinkedList<Point> res = new LinkedList<Point>();
			Noeud nn = n.previous;
			Point p;

			res.add(n.current);
			if (!n.current.equals(destination))	throw new RuntimeException("Chemin mal forme");
			while(true)
			{
				p = new Point(nn.current.x, nn.current.y);
				res.addFirst(p);
				if (p.equals(source))	return res;
				nn = nn.previous;
			}
		}
		/**==========================================================
		 * Cherche dans la "openList" le Point p dont le noeud n possede
		 * le plus peti "totalCost"
		 * Le noeud trouvee est retire de la liste ouverte
		 ============================================================**/
		private Noeud getBestFromOpenList()
		{
			Noeud	bn = openList.get(0);
			int		bi = 0;

			for(int i = 1; i<openList.size(); i++)
			{
					Noeud n = openList.get(i);
					if	(n.forwardCost < bn.forwardCost)	{bn = n;	bi = i;}
			}
			openList.remove(bi);
			return bn;
		}
		/**============================================================
		 * Rend le neoud de  la liste ouverte dont le Point courrent est p
		 * Le noeud trouve est retire de la liste
		 * @return null si le noeud n'est pas trouve
		 ==============================================================**/
		public Noeud getFromOpenList(Point p)
		{
			for (int i = 0; i<openList.size(); i++)
			{
				Noeud n = openList.get(i);
				if (n.current.equals(p))	{openList.remove(i);	return n;}
			}
			return null;
		}
		/**==========================================================
		 * Determine si la liste fermee contient une entree avec
		 * le Point p
		 ============================================================**/
		private boolean isInCloseList(Point p)
		{
			for(int i = 0; i < closeList.size(); i++)
			{
					Noeud n = closeList.get(i);
					if (n.current.equals(p)) return true;
			}
			return false;
		}
		private int computeMalusse(Point p, int pathCaseType, boolean allowFreeCase)
		{
			int caseType = this.ground.getCaseType(p.x, p.y);

			if (ground.isFreeCase(p.x, p.y))
			{
				if (!allowFreeCase)						return -1;
				else									return difrentCaseTypeMalusse;
			}
			else
			{
//TODO modifier le -2 en -1
				if (caseType != pathCaseType)			return -2;
				else									return sameCaseTypeMalusse;
			}
		}
/**========================================================
 * Structure de stockage des noeud pendant une recherche
 ==========================================================*/
public class Noeud
{
	// Attributs:
	public Noeud	previous;
	public Point	current;
	public int		backwardCost;
	public int		forwardCost;
	public int		totalCost;
	
	// Constructeur:
	public Noeud(){}
	public Noeud(Noeud previous, Point current, int bc, int fc)
	{
		this.previous		= new Noeud();
		this.previous		= previous;
		this.current		= new Point(current.x, current.y);
		this.backwardCost	= bc;
		this.forwardCost 	= fc;
		this.totalCost		= bc + fc;
	}
}
}