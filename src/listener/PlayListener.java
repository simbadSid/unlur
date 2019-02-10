package listener;

import evra.path.unlur.R;
import exception.FullGroundException;

import ihm.GraphicGround;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;
import application.Application;






public class PlayListener implements OnTouchListener
{
// -----------------------
// Attributs
// -----------------------
	private Application		app;
	private GraphicGround	gg;
	private Context			context;

// -----------------------
// Constructeur
// -----------------------
	public PlayListener(Application app, GraphicGround gg, Context ctx)
	{
		this.app		= app;
		this.gg			= gg;
		this.context	= ctx;
	}

// -----------------------
// Methodes locales
// -----------------------
	@Override
	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouch(View arg0, MotionEvent arg1)
	{
		if (arg1.getAction() != MotionEvent.ACTION_DOWN) return false;

		int		x = (int) arg1.getX();
		int		y = (int) arg1.getY();
		Point	p = gg.realToGround(new Point(x, y));
		int test;
		Integer test0 = null;
		boolean testB;

		if (p == null) return true;												// Cas ou le point est en dehors du terrain
		test = app.play(p);														// Joue la pion
		testB = Application.printPlayResult(app, test, context);
		if (!testB)	return true;												// Cas ou le coup ne peut pas avoir de suivants
		try {test0 = app.playAI();}												// Faire jouer l'ia
		catch (FullGroundException e)
		{
			Toast.makeText(context, R.string.fullGroundError, Toast.LENGTH_LONG).show();
			return true;
		}
		if (test0 != null) Application.printPlayResult(app, test0, context);
		return true;
	}
}