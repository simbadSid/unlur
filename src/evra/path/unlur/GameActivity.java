package evra.path.unlur;

import exception.FullGroundException;
import graphic.ColorHistory;
import graphic.Printer;
import ihm.IHM;
import android.app.Activity;
import android.os.Bundle;
import application.Application;






public class GameActivity extends Activity
{
// -----------------------------
// Attributs
// -----------------------------
	public static final	String		keyHide			= "keyHide";
	public static final	String		keyClear		= "keyClear";

	public static		Application	app;
	public static		Printer		pr;

// -----------------------------
// Methode principale
// -----------------------------
	protected void onCreate(Bundle savedInstanceState)
	{
		ColorHistory ch;

		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_game);

		if (app == null)
		{
			pr	= new Printer();
			app	= new Application(pr);
			ch	= null;
		}
		else if ((getClearExtra()) && (app != null))
		{
			app.clearAll();
			try								{app.playAI();}				// Faire jouer l'ia
			catch (FullGroundException e)	{e.printStackTrace();}
			ch	= pr.getColorHistory();
		}
		else ch	= pr.getColorHistory();

		IHM.initActivityGame(this, app, pr, ch);
		if (getHideExtra())	super.finish();								// Cas ou l'appelant de l'activite ne veut pas l'afficher
	}

// ----------------------------------
// Methode auxiliaire
// ----------------------------------
	/**===================================================================
	 * Lit l'argument "hide" contenu dans l'intent qui a cree cette activite
	 =====================================================================*/
	private boolean getHideExtra()
	{
		boolean hide;
		try
		{
			hide = (Boolean) this.getIntent().getExtras().get(keyHide);
			return hide;
		}
		catch (Exception e)	{return false;}
	}
	/**===================================================================
	 * Lit l'argument "hide" contenu dans l'intent qui a cree cette activite
	 =====================================================================*/
	private boolean getClearExtra()
	{
		boolean clear;
		try
		{
			clear = (Boolean) this.getIntent().getExtras().get(keyClear);
			return clear;
		}
		catch (Exception e)	{return false;}
	}
}