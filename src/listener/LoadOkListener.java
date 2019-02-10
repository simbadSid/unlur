package listener;

import evra.path.unlur.ActivitySlider;
import evra.path.unlur.GameActivity;
import evra.path.unlur.LoadActivity;
import evra.path.unlur.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;








public class LoadOkListener implements OnClickListener
{
// ------------------------------
// Attributs
// ------------------------------
	private LoadActivity	act;
	private int				containerId;			// Bouton qui a appele l'activite load

// ------------------------------
// Constructeur
// ------------------------------
	public LoadOkListener(LoadActivity act, int containerId)
	{
		this.act			= act;
		this.containerId	= containerId;
	}

// ------------------------------
// Methode locale
// ------------------------------
	@Override
	public void onClick(View arg0)
	{
		if (containerId == R.id.buttonLoadMain)						//	Cas ou le conteneur est le load de main
		{
			if	(!act.isLoaddedGame())	showLoadErrorDialog();
			else
			{
				GameActivity.pr.setColorHistory	(LoadActivity.pr.getColorHistory(), false);
				GameActivity.app.load			(LoadActivity.app);
				ActivitySlider.changeActivity(act, "GameActivity");	//		Refait l'affichage en fonction de app et pr
				act.finish();
			}
		}
		else if	(containerId == R.id.menuLoadItem)					//	Cas ou le conteneur est le load de menu
		{
			showDialogActivity();
			return;
		}
		else	throw new RuntimeException("Unhandeled Container Id");
	}

// -----------------------
// Methodes auxiliaires
// -----------------------
	/**===========================================================
	 * Demande a l'utilisateur si il souhaite sauvegarder la partie
	 *  actuelle avant de charger la nouvelle
	 =============================================================*/
	private void showDialogActivity()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(act);

		builder.setTitle			(R.string.loadTitle);
		builder.setMessage			(R.string.loadMessage);
		builder.setPositiveButton	(R.string.load,		new LoadButtonListener());
		builder.setNegativeButton	(R.string.cancel,	null);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.show();
	}
	private class LoadButtonListener implements DialogInterface.OnClickListener
	{
		public void onClick(DialogInterface arg0, int arg1)
		{
			if (!act.isLoaddedGame())	showLoadErrorDialog();
			else
			{
				GameActivity.pr.setColorHistory(LoadActivity.pr.getColorHistory(), false);
				GameActivity.app.load(LoadActivity.app);
				act.finish();
			}
		}
	}
	/**===========================================================
	 * Indique a l'utilisateur une erreur lors du chargement
	 =============================================================*/
	private void showLoadErrorDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(act);
		String str = act.getLoaddedGameName();
		str = act.getResources().getString(R.string.loadErrorMessage, str);

		builder.setIcon				(android.R.drawable.ic_dialog_alert);
		builder.setTitle			(R.string.loadErrorTitle);
		builder.setMessage			(str);
		builder.setPositiveButton	(R.string.ok, null);
		builder.show();
	}
}