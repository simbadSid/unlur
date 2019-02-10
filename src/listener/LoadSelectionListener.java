package listener;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import application.SavedGame;

import evra.path.unlur.LoadActivity;
import evra.path.unlur.R;



public class LoadSelectionListener implements OnClickListener
{
// ------------------------------
// Attributs
// ------------------------------
	private LoadActivity	act;
	private int				checkedItem;
	private int				loaddedGame;
	private String[]		gameList;

// ------------------------------
// Constructeur
// ------------------------------
	public LoadSelectionListener(LoadActivity act)
	{
		this.act			= act;
		this.loaddedGame	= 0;
		this.gameList		= SavedGame.getSavedGameList(act);

		act.loadPreview(gameList[0]);
	}

// ------------------------------
// Methode locale
// ------------------------------
	@Override
	public void onClick(View arg0)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(act);

		builder.setTitle			(R.string.loadLabel);
		builder.setPositiveButton	(R.string.ok,			new OkListener());
		builder.setNegativeButton	(R.string.cancel,		null);
		builder.setSingleChoiceItems(gameList, loaddedGame,	new checkListener());
		builder.show();

		checkedItem = loaddedGame;
	}

// -----------------------
// Methodes auxiliaires
// -----------------------
	private class checkListener implements DialogInterface.OnClickListener
	{
		public void onClick(DialogInterface arg0, int arg1)	{checkedItem = arg1;}
	}
	private class OkListener implements DialogInterface.OnClickListener
	{
		public void onClick(DialogInterface arg0, int arg1)
		{
			if (checkedItem == loaddedGame)	return;

			boolean test = act.loadPreview(gameList[checkedItem]);

			if (!test)	showLoadError();
			else		loaddedGame = checkedItem;
		}
	}
	/**===========================================================
	 * Indique a l'utilisateur une erreur lors du chargement
	 =============================================================*/
	private void showLoadError()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(act);
		String str = gameList[checkedItem];
		str = act.getResources().getString(R.string.loadErrorMessage, str);

		builder.setIcon				(android.R.drawable.ic_dialog_alert);
		builder.setTitle			(R.string.loadErrorTitle);
		builder.setMessage			(str);
		builder.setPositiveButton	(R.string.ok, null);
		builder.show();
	}
}