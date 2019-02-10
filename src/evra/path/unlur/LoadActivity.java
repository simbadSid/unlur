package evra.path.unlur;

import graphic.Printer;
import ihm.IHM;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import application.Application;
import application.LoadInfo;
import application.SavedGame;






public class LoadActivity extends Activity
{
// ----------------------------------
// Attributs
// ----------------------------------
	public static final	String	keyContainerIdLoad	= "keyContainerId";
	public static Application	app;
	public static Printer		pr;

	private static String		loaddedGame			 = null;

// ----------------------------------
// Methode principale
// ----------------------------------
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		if (app == null)
		{
			pr				= new Printer();
			app				= new Application(pr);
		}

		if (!SavedGame.isSavedGame(this))	this.setContentView(R.layout.activity_load_empty);
		else
		{
			this.setContentView(R.layout.activity_load);
			int containerId = getContainerIdLoadExtra();
			IHM.initActivityLoad(this, containerId);
		}
	}
	/**========================================================
	 * Rafraichie l'activite en fonction des jeux enregistres et de li
	 ==========================================================*/
	public boolean loadPreview(String game)
	{
		LoadInfo	li	= SavedGame.load(game, this, app, pr);

		if (li == null)
		{
			setText(null, game);
			loaddedGame = null;
			return false;
		}
		else
		{
			setText(li, game);
			loaddedGame = new String(game);
			return true;
		}
	}
	public boolean	isLoaddedGame()			{return (loaddedGame != null);}
	public String	getLoaddedGameName()	{return new String(loaddedGame);}

// ----------------------------------
// Methode auxiliaire
// ----------------------------------
	/**===================================================================
	 * Lit l'argument contenu dans l'intent qui a cree cette activite
	 =====================================================================*/
	private int getContainerIdLoadExtra()
	{
		int containerId;
		try
		{
			containerId = (Integer) this.getIntent().getExtras().get(keyContainerIdLoad);
			return containerId ;
		}
		catch (NullPointerException e)
		{
			throw new RuntimeException("The caller of this intent does not specifie an extra with the key: keyContainerId");
		}
		catch (ClassCastException e)
		{
			throw new RuntimeException("The intent extra with the key \"keyContainerId\"is not of type \"int\"");
		}
	}
	/**===================================================================
	 * Ecrit l'ensemble des info sur le jeux charge
	 =====================================================================*/
	private void setText(LoadInfo li, String gameName)
	{
		TextView tvLab, tvText;

		if (li == null)
		{
			tvLab	= (TextView) findViewById(R.id.loadInfoGameNameLab);
			tvText	= (TextView) findViewById(R.id.loadInfoGameNameText);
			tvLab	.setText(this.getResources().getString(R.string.loadErrorMessage, gameName));
			tvText	.setText("");

			tvLab	= (TextView) findViewById(R.id.loadInfoDimLab);
			tvText	= (TextView) findViewById(R.id.loadInfoDimText);
			tvLab	.setText("");
			tvText	.setText("");

			tvLab	= (TextView) findViewById(R.id.loadInfoPlayer1Lab);
			tvText	= (TextView) findViewById(R.id.loadInfoPlayer1Text);
			tvLab	.setText("");
			tvText	.setText("");

			tvLab	= (TextView) findViewById(R.id.loadInfoPlayer2Lab);
			tvText	= (TextView) findViewById(R.id.loadInfoPlayer2Text);
			tvLab	.setText("");
			tvText	.setText("");

			tvLab	= (TextView) findViewById(R.id.loadInfoStepLab);
			tvText	= (TextView) findViewById(R.id.loadInfoStepText);
			tvLab	.setText("");
			tvText	.setText("");

			tvLab	= (TextView) findViewById(R.id.loadInfoDateLab);
			tvText	= (TextView) findViewById(R.id.loadInfoDateText);
			tvLab	.setText("");
			tvText	.setText("");
		}
		else
		{
			tvLab	= (TextView) findViewById(R.id.loadInfoGameNameLab);
			tvText	= (TextView) findViewById(R.id.loadInfoGameNameText);
			tvLab	.setText(R.string.gameName);
			tvText	.setText(" : " + gameName);

			tvLab	= (TextView) findViewById(R.id.loadInfoDimLab);
			tvText	= (TextView) findViewById(R.id.loadInfoDimText);
			tvLab	.setText(R.string.groundDim);
			tvText	.setText(" : " + li.dim);

			tvLab	= (TextView) findViewById(R.id.loadInfoPlayer1Lab);
			tvText	= (TextView) findViewById(R.id.loadInfoPlayer1Text);
			tvLab	.setText(R.string.player1);
			tvText	.setText(" : " + li.player1);

			tvLab	= (TextView) findViewById(R.id.loadInfoPlayer2Lab);
			tvText	= (TextView) findViewById(R.id.loadInfoPlayer2Text);
			tvLab	.setText(R.string.player2);
			tvText	.setText(" : " + li.player2);

			tvLab	= (TextView) findViewById(R.id.loadInfoStepLab);
			tvText	= (TextView) findViewById(R.id.loadInfoStepText);
			tvLab	.setText(R.string.step);
			tvText	.setText(" : " + li.step);

			tvLab	= (TextView) findViewById(R.id.loadInfoDateLab);
			tvText	= (TextView) findViewById(R.id.loadInfoDateText);
			tvLab	.setText(R.string.creationDate);
			tvText	.setText(" : " + li.date);
		}
	}
}