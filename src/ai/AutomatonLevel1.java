package ai;

import java.util.Iterator;
import java.util.Random;

import exception.FullGroundException;

import android.graphics.Point;
import application.Action;
import application.Application;
import application.Case;
import application.Ground;




public class AutomatonLevel1 implements Automaton
{
// -----------------------------------------------
// Attributes:
// -----------------------------------------------
	public final static double		probaChangeStep		= 4./5.;

// -----------------------------------------------
// Local Methods:
// -----------------------------------------------
	public Action makeChoice(Application app) throws FullGroundException
	{
		Iterator<Point> it;
		Random rnd = new Random();
		int max;
		int step = app.getStep();
		Ground ground = app.getGround();
		Point p = null;

		if (this.goToStep2(step)) return new Action(null, Action.stepChange);

		if (step == 0)
		{
			it = ground.getNoBorderGroundIterator(Case.freeCase, true);
			if (!it.hasNext()) return new Action(null, Action.stepChange);
		}
		else
		{
			it = ground.getGroundIterator(Case.freeCase, true);
			if (!it.hasNext())throw new FullGroundException();
		}
		max = 1+rnd.nextInt(ground.getNbrCase()-1);
		for (int i=0; (i<max && it.hasNext()); i++) p = it.next();
		return new Action(p, Action.currentGame);
	}

// -----------------------------------------------
// Private Methods:
// -----------------------------------------------
	/**=================================================
	 * Determine si l'AI doit passer a l'etape 2
	 ====================================================*/
	private boolean goToStep2(int step)
	{
		Random rnd = new Random(System.nanoTime());

		if ((step == 0) && (rnd.nextDouble() > probaChangeStep))	return true;
		return false;
	}
}