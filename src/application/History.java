package application;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import android.graphics.Point;
import exception.LoadFailException;









public class History
{
// --------------------------------------------
// Attributs:
// --------------------------------------------
	private LinkedList<Action>	history;
	private int					ptr;

// --------------------------------------------
// Constructeur:
// --------------------------------------------
	public History()
	{
		this.history	= new LinkedList<Action>();
		this.ptr		= -1;
	}
	public History(History hs)
	{
		this.ptr		= hs.ptr;
		this.history	= new LinkedList<Action>(hs.history);
	}

// --------------------------------------------
// Methodes locales:
// --------------------------------------------
	public boolean	isNext()			{return (this.ptr < this.history.size()-1);}
	public boolean	isPrevious()		{return (this.ptr > -1);}
	public boolean	isEmpty()			{return (this.history.size() == 0);}
	public boolean	are2Previous()		{return (this.ptr > 0);}
	public Action	getPrevious()		{return this.history.get(ptr);}
	public Action	getNext()			{return this.history.get(ptr+1);}
	public void		goBackward()		{if (ptr == -1)					throw new RuntimeException("Can not go backWard!");ptr --;}
	public void		goForward()			{if (ptr == history.size()-1)	throw new RuntimeException("Can not go forWard!"); ptr ++;}
	public void		add(Action hc)
	{
		int n = history.size();
		for (int i=ptr+1; i<n; i++)	history.removeLast();
		this.history.addLast(new Action(hc));
		this.ptr ++;
	}
	public Iterator<Point> getPointIterator (boolean whitePoint)	{return new PointIterator(whitePoint);}

// --------------------------------------------
// Methodes de lecture ecriture d'historique:
// --------------------------------------------
	public void saveHistory(FileWriter fic) throws IOException
	{
		int size	= this.history.size();
		Action hc;
		Point p;

		fic.write(this.ptr	+ "\n");							// Ecrire lindice du jeux courrent
		fic.write(size		+ "\n");							// Ecrire le nombre de coups joues
		for (int i = 0; i<size; i++)							// Pour chaque jeux de l'historique:
		{
			hc = this.history.get(i);
			fic.write(hc.option + " ");							// 		Ecrire Le type de coup joue
			if (hc.isStepChange())	{fic.write("\n"); continue;}//		Si C un changement d'etape, passer
			p = hc.newPoint;
			fic.write(p.x + " " + p.y + "\n");					// 		Ecrir le point ajoute
		}
	}
	/**=======================================================
	 * Charge l'historique contenu dans sc
	 * Ne change l'historique actuel que si tous les param on ete lu avec succes
	 * Joue l'ensembles des coup charger dans g
	 * @return true si la partie fini par une victoire et false si non
	 * @throws LoadFailException
	 ==========================================================*/
	public boolean loadHistory(Scanner sc, Ground g) throws LoadFailException
	{
		int	currentGame	= sc.nextInt();								// Lire lindice du jeux courrent
		int nbrElem		= sc.nextInt();								// Lire le nombre de coups joues
		int playerWhite = -1;
		boolean win		= false;
		Action hc;
		Point np;
		LinkedList<Action>	res	= new LinkedList<Action>();

		for (int i=0; i<nbrElem; i++)
		{
			int option = sc.nextInt();
			if (option == Action.winGame)	win = true;				// Cas d'une victoire
			if (option == Action.stepChange)						// Cas d'un changement d'etape
			{
				playerWhite = (i + 1) % 2;
				np			= null;
			}
			else													// Cas d'un jeux (avec victoire possible)
			{
				int x = sc.nextInt();								//		Lire le nouveau point
				int y = sc.nextInt();
				if ((!g.isValidPosition(x, y)) || 					// 		Cas ou le nouveau point n'est pas valide
					(!g.isFreeCase(x, y))) throw new LoadFailException();

				if (i <= currentGame)
				{
					if ((i%2) == playerWhite)	g.setCase(x, y, Case.whiteCase);
					else						g.setCase(x, y, Case.blackCase);
				}
				np = new Point(x, y);
			}
			hc = new Action(np, option);
			res.add(hc);
		}
		this.ptr		= currentGame;
		this.history	= res;
		return (win && (this.ptr == this.history.size()-1));
	}
// --------------------------------
// Classe de iterateur de points
// --------------------------------
	class PointIterator implements Iterator<Point>
	{
		// Attributs
		private LinkedList<Point> l;

		// Constructeur
		public PointIterator(boolean whitePoint)
		{
			Action hc;
			int white = -1;

			l = new LinkedList<Point>();
			for (int i=0; i<=ptr; i++)
			{
				hc = history.get(i);
				if (hc.isStepChange())	{white = (i+1)%2;	continue;}
				if ((whitePoint)  && ((i%2)==white))	l.addLast(new Point(hc.newPoint));
				if ((!whitePoint) && ((i%2)!=white))	l.addLast(new Point(hc.newPoint));
			}
		}
		public boolean	hasNext()	{return (!l.isEmpty());}
		public Point	next()		{Point res = l.getFirst(); l.removeFirst(); return res;}
		public void		remove()	{}
	}
}