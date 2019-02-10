package application;

import java.util.Iterator;
import java.util.LinkedList;

import android.graphics.Point;







public class Analyzer 
{
// ----------------------------------------------- 
// Attributs:
// -----------------------------------------------
		private Ground ground;

// ----------------------------------------------
// Constructeur:
// ----------------------------------------------
		public Analyzer(Ground ground)
		{
			this.ground = ground;
		}

// ---------------------------------------------
// Methodes Locales:
// ---------------------------------------------	
		/**==================================================</br>
		 * Determine si le joueur blanc a gange.</br>
		 * @return: La liste de tous les chemin gagnant, ou null si il n'existe pas de chemain gagnant</br>
		 * @param emptyCellInPath: indique si le chemin peut contenir des cases vides.</br>
		 * Si oui, ces cases serontdefavorises par rapport aux cases blanches</br>
		 ====================================================*/
		public LinkedList<LinkedList<Point>> getWinnerWhite(boolean emptyCellInPath)
		{
			LinkedList<LinkedList<Point>> res = new LinkedList<LinkedList<Point>>();
			LinkedList<Point> path;

			for (int i = 0; i<3; i++)												// Pour chaque bord nord, chercher
			{																		//  un lien avec le bord sud oppose
				path = getBorderLine(i, i+3, Case.whiteCase, emptyCellInPath);
				if		(path		== null)					continue;
				else if	(res.isEmpty())							res.add(path);
				else if (res.getFirst().size() <  path.size())	continue;
				else if (res.getFirst().size() == path.size())	res.add(path);
				else
				{
					res.clear();
					res.add(path);
				}
			}
			if (res.isEmpty())	return null;
			return res;
		}
		/**==================================================</br>
		 * Determine si le joueur blanc a gange.</br>
		 * @return true: il existe un Y noirs entre 3 bords non adjacents)</br>
		 * @param emptyCellInPath: indique si le chemin peut contenir des cases vides.
		 * Si oui, ces cases serontdefavorises par rapport aux cases noires</br>
		 ====================================================*/
		public LinkedList<LinkedList<Point>> getWinnerBlack(boolean emptyCellInPath)
		{
			LinkedList<LinkedList<Point>> res = new LinkedList<LinkedList<Point>>();
			LinkedList<Point> path;

			for (int i = 0; i<2; i++)												// Pour chaque bord b: rechercher un Y
			{																		//  avec 2 autres bords disjoints
				path = getBorderY(i, i+2, i+4, Case.blackCase, emptyCellInPath);
				if		(path		== null)					continue;
				else if	(res.isEmpty())							res.add(path);
				else if (res.getFirst().size() <  path.size())	continue;
				else if (res.getFirst().size() == path.size())	res.add(path);
				else
				{
					res.clear();
					res.add(path);
				}
			}
			if (res.isEmpty())	return null;
			return res;
		}
		/**==================================================
		 * Determine si le joueur noir a perdu en crean une ligne entre 2 bords opposes
		 * @return: La liste des point composants le chemin noirs
		 * perdant, ou null si il n'existe pas de chemain perdant
		 ====================================================*/
		public LinkedList<Point> getLooserBlack()
		{		
			LinkedList<Point> path;
			LinkedList<Point> bestPath = null;			

			for (int i = 0; i<3; i++)												// Pour chaque bord nord, chercher
			{																		//  un lien avec le bord sud oppose
				path = getBorderLine(i, i+3, Case.blackCase, false);
				if		(path		== null)			continue;
				if		(bestPath	== null)			bestPath = path;
				else if (bestPath.size() > path.size())	bestPath = path;
			}
			return bestPath;
		}
		/**==================================================
		 * Determine si le joueur blanc a perdu en creant un Y entre 3 bords non adjacents.
		 * @return true: il existe un Y blanc entre 3 bords non adjacents)
		 ====================================================*/
		public LinkedList<Point> getLooserWhite()
		{
			LinkedList<Point> path;
			LinkedList<Point> bestPath = null;

			for (int i = 0; i<2; i++)												// Pour chaque bord b: rechercher un Y
			{																		//  avec 2 autres bords disjoints
				path = getBorderY(i, i+2, i+4, Case.whiteCase, false);
				if		(path		== null)			continue;
				if		(bestPath	== null)			bestPath = path;
				else if (bestPath.size() > path.size())	bestPath = path;
			}
			return bestPath;
		}

// ---------------------------------------------
// Methodes auxilliaires:
// ---------------------------------------------
		/**=================================================================
		 * Determine le plus court chemain (en ligne) entre les bord b1 et b2 avec des cases de type "c".
		 * Les index des bord sont Ground.*****Side:
		 * 		0: Nord Ouest
		 * 		1: Nord
		 * 		Puis tourner dans le sens anti-trigo
		 * @param c: type de case formant le chemain
		 * @return null si aucun chemin n'est trouve, et la liste des points du chemin si non
		 ===================================================================*/
		private LinkedList<Point> getBorderLine(int border1, int border2, int c, boolean emptyCellInPath)
		{
			LinkedList<Point> b1 = ground.getSide(border1, c, emptyCellInPath);	// Determiner la liste des points du bord 1 de type c
			LinkedList<Point> b2 = ground.getSide(border2, c, emptyCellInPath);	// Determiner la liste des points du bord 2 de type c
			if ((b1 == null) || (b2 == null)) 	return null;					// Cas ou l'un des bord ne contient pas de case recherches

			LinkedList<Point> path;
			LinkedList<Point> bestPath = null;
			for (int i = 0; i<b1.size(); i++)									// Pour chaque point p1 de bord1 et p2 de Bord2
			{
				Point p1 = b1.get(i);
				if ((!emptyCellInPath) && (this.ground.isFreeCase(p1.x, p1.y))) continue;
				for (int j = 0; j<b2.size(); j++)
				{
					Point p2 = b2.get(j);
					if ((!emptyCellInPath) && (this.ground.isFreeCase(p2.x, p2.y))) continue;
					path = getLine(p1, p2, c, emptyCellInPath);					//		chercher le plus court chemin entre p1 et p2
					if		(path		== null)			continue;
					if		(bestPath	== null)			bestPath = path;
					else if (bestPath.size() > path.size())	bestPath = path;
				}
			}
			return bestPath;
		}
		/**=======================================================================
		 * Determine le plus court Y entre les bord b1 et b2 et b3 avec des cases de type "c".
		 * Les index des bord sont:
		 * 		0: Nord Ouest
		 * 		1: Nord
		 * 		Puis tourner dans le sens anti-trigo
		 * @param border1: index du bord1
		 * @param c: type de case formant le chemain
		 * @return null si aucun chemin n'est trouve, et la liste des points du chemin si non
		 ==========================================================================*/
		private LinkedList<Point> getBorderY(int border1, int border2, int border3, int c, boolean emptyCellInPath)
		{
			LinkedList<Point> b1 = ground.getSide(border1, c, emptyCellInPath);		// Determiner la liste des points du bord 1 de type c
			LinkedList<Point> b2 = ground.getSide(border2, c, emptyCellInPath);		// Determiner la liste des points du bord 2 de type c
			LinkedList<Point> b3 = ground.getSide(border3, c, emptyCellInPath);		// Determiner la liste des points du bord 3 de type c
			if ((b1 == null) || (b2 == null) || (b3 == null)) return null;

			LinkedList<Point> bestPath = null;
			for (int i = 0; i<b1.size(); i++)										// Pour chaque p1, p2, p3 de bord respectif bord1, bord2, bord3
			{
				Point p1 = b1.get(i);
				if ((!emptyCellInPath) && (this.ground.isFreeCase(p1.x, p1.y))) continue;
				for (int j = 0; j<b2.size(); j++)
				{
					Point p2 = b2.get(j);
					if ((!emptyCellInPath) && (this.ground.isFreeCase(p2.x, p2.y))) continue;
					for (int k = 0; k<b3.size(); k++)
					{
						Point p3 = b3.get(k);
						if ((!emptyCellInPath) && (this.ground.isFreeCase(p3.x, p3.y))) continue;
						LinkedList<Point> path = getY(p1, p2, p3, c, emptyCellInPath);	//		Determiner le plus court Y entre b1, b2 et b3
						if		(path == null)					continue;
						if		(bestPath == null)				bestPath = path;
						else if (bestPath.size() > path.size())	bestPath = path;
					}
				}
			}
			return bestPath;
		}
		/**================================================================
		 * Determine la plus courte ligne (!= Y) entre les points p1 et p2 qui 
		 * soit composee de cases non vides de meme type que p1 et p2
		 * @param p1 Point (x, y) representant la case source
		 * @param p2 Point (x, y) representant la case destination
		 * @return null si aucun chemin n'est trouve et la liste des point si non
		 ==================================================================*/
		private LinkedList<Point> getLine(Point p1, Point p2, int c, boolean emptyCellInPath)
		{
			PathFinder pf = new PathFinder(ground);
			LinkedList<Point> res = pf.findPath(p1, p2, c, emptyCellInPath);
			return res;
		}
		/**==================================================================
		 * Determine la plus courte Y entre les points p1, p2 et p3 qui 
		 * soit composee de cases non vides de meme type que p1, p2 et p3
		 * @return null si aucun chemin n'est trouve et la liste des point si non
		 ====================================================================*/
		private LinkedList<Point> getY(Point p1, Point p2, Point p3, int c, boolean emptyCellInPath)
		{
			PathFinder pf = new PathFinder(ground);
			LinkedList<Point> bestPath = null, l1, l2, l3;
			Iterator<Point> it = ground.getNoBorderGroundIterator(ground.getCaseType(p1.x, p1.y), emptyCellInPath);

			while(it.hasNext())
			{
				Point centralPoint	= it.next();
				l1					= pf.findPath(p1, centralPoint, c, emptyCellInPath);	if (l1 == null) continue;
				l2					= pf.findPath(p2, centralPoint, c, emptyCellInPath);	if (l2 == null) continue;
				l3					= pf.findPath(p3, centralPoint, c, emptyCellInPath);	if (l3 == null) continue;
				l1.addAll(l2);
				l1.addAll(l3);

				if		(bestPath == null)				bestPath = l1;
				else if (bestPath.size() > l1.size())	bestPath = l1;
			}
			return bestPath;
		}
}