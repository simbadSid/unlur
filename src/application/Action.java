package application;

import android.graphics.Point;






//------------------------------------ 
//Classe cellule dans un historique
//------------------------------------
public class Action
{
// ----------------------------------
// Attributs
// ----------------------------------
	// Macros de definition de jeux 
	public final static int	 currentGame 	= 0;
	public final static int  stepChange		= 1;
	public final static int  winGame		= 2;
	private final static int nbrOption		= 3;
	
	// Parametres d'un jeux
	public Point	newPoint;
	public int		option;
		
// ----------------------------------
//Constructeur
// ----------------------------------
	public Action(Point newPoint, int option) throws RuntimeException
	{
		if ((option < 0) || (option >= nbrOption)) 
			throw new RuntimeException("type de jeux non pris en charge: " + option);
		
		if (newPoint == null)	this.newPoint	= null;
		else					this.newPoint	= new Point(newPoint);
		this.option	= option;
	}
	public Action(Action hc)
	{
		if (hc.newPoint == null)	this.newPoint	= null;
		else						this.newPoint	= new Point(hc.newPoint);
		this.option	= hc.option;
	}
// ----------------------------------
// Methodes Locales:
// ----------------------------------
	public boolean isStepChange()	{return (this.option == stepChange);}
	public boolean isWin()			{return (this.option == winGame);}
	public boolean isCurrentGame()	{return (this.option == currentGame);}
}