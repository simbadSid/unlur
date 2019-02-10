package listener;

import evra.path.unlur.R;
import exception.FullGroundException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import application.Application;





public class ClearAllListener implements OnClickListener
{
// -----------------------
// Attributs
// -----------------------
	private Application		app;
	private Activity		act;

// -----------------------
// Constructeur
// -----------------------
	public ClearAllListener(Application app, Activity act)
	{
		this.app		= app;
		this.act		= act;
	}

// -----------------------
// Methodes locales
// -----------------------
	@Override
	public void onClick(View arg0)
	{
		if (app.isClear())
		{
			String str = act.getResources().getString(R.string.clearAllError);
			Toast.makeText(act, str, Toast.LENGTH_LONG).show();
		}
		else	this.showDialogActivity();
	}

// -----------------------
// Methodes auxiliaires
// -----------------------
	/**===========================================================
	 * Demande a l'utilisateur si il souhaite efface la partie actuelle
	 =============================================================*/
	private void showDialogActivity()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(act);

		builder.setTitle			(R.string.clearAllTitle);
		builder.setMessage			(R.string.clearAllMessage);
		builder.setPositiveButton	(R.string.restart,	new RestartButtonListener());
		builder.setNeutralButton	(R.string.save,		new SaveButtonListener());
		builder.setNegativeButton	(R.string.cancel,	null);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.show();
	}
	private class RestartButtonListener implements DialogInterface.OnClickListener
	{
		public void onClick(DialogInterface arg0, int arg1)
		{
			app.clearAll();
			try								{app.playAI();}				// Faire jouer l'ia
			catch (FullGroundException e)	{e.printStackTrace();}
		}
	}
	private class SaveButtonListener implements DialogInterface.OnClickListener
	{
		public void onClick(DialogInterface arg0, int arg1)	{new SaveListener(true, act).onClick(null);}
	}
}