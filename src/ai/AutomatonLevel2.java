package ai;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import android.graphics.Point;
import application.Action;
import application.Analyzer;
import application.Application;
import application.Case;
import application.Ground;
import exception.FullGroundException;

public class AutomatonLevel2 implements Automaton
{
// -----------------------------------------------
// Attributes:
// -----------------------------------------------
	public final static double		probaChangeStep		= 3./5.;

	private LinkedList<Point>		objective;			// List of free points that the AI must play to win

// -----------------------------------------------
// Local Methods:
// -----------------------------------------------
	public Action makeChoice(Application app) throws FullGroundException
	{
		Iterator<Point> it;
		int step = app.getStep();
		Ground ground = app.getGround();

		if (this.goToStep1(step))	{this.initObjective(ground, false); return new Action(null, Action.stepChange);}

		if (step == 0)
		{
			it = ground.getNoBorderGroundIterator(Case.freeCase, true);
			if (!it.hasNext())		{this.initObjective(ground, false); return new Action(null, Action.stepChange);}
			else					return this.makeChoiceStep0(app, it);
		}
		else
		{
			it = ground.getGroundIterator(Case.freeCase, true);
			if (!it.hasNext())	throw new FullGroundException();
			else				return this.makeChoiceStep1(app);
		}
	}

// -----------------------------------------------
// Private Methods:
// -----------------------------------------------
	/**=================================================
	 * Determine si l'AI doit passer a l'etape 2
	 ====================================================*/
	private boolean goToStep1(int step)
	{
		Random rnd = new Random(System.nanoTime());

		if ((step == 0) && (rnd.nextDouble() > probaChangeStep))	return true;
		return false;
	}
	/**==================================================
	 * Initialize the player's objectives
	 ====================================================*/
	private void initObjective(Ground ground, boolean isCurrentPlayerWhite)
	{
		Analyzer an = new Analyzer(ground);
		LinkedList<LinkedList<Point>> pathList;
		LinkedList<Point> tmpObjective;

		if (isCurrentPlayerWhite)	pathList = an.getWinnerWhite(true);
		else						pathList = an.getWinnerBlack(true);

		if (pathList == null) throw new RuntimeException("The AI has no reachable solution");
		this.objective	= new LinkedList<Point>();
		int choice		= (new Random()).nextInt(pathList.size());
		tmpObjective	= pathList.get(choice);
		for (Point p: tmpObjective) if (ground.isFreeCase(p.x, p.y)) this.objective.add(p);
	}
	/**=======================================================</br>
	 * Check wether the player's objective is still reacheble.</br>
	 =========================================================*/
	private boolean isObjectiveHijacked(Application app)
	{
		Ground ground = app.getGround();
		for (Point p:this.objective)
		{
			if (!ground.isFreeCase(p.x, p.y))	return true;
		}
		return false;
	}
	private Action makeChoiceStep0(Application app, Iterator<Point> it)
	{
		Point p = null;
		Random rnd = new Random();
		int max = 1+rnd.nextInt(app.getGround().getNbrCase()-1);

		for (int i=0; (i<max && it.hasNext()); i++) p = it.next();
		return new Action(p, Action.currentGame);
	}
	private Action makeChoiceStep1(Application app)
	{
		Ground ground = app.getGround();
		boolean isCurrentPlayerWhite = app.isCurrentPlayerWhite();
		if		(this.objective == null)	this.initObjective(ground, isCurrentPlayerWhite);
		else if	(isObjectiveHijacked(app))	this.initObjective(ground, isCurrentPlayerWhite);

		int		choice	= (new Random()).nextInt(this.objective.size());
		Point	res		= this.objective.get(choice);
		this.objective.remove(choice);
		return new Action(res, Action.currentGame);
	}
}