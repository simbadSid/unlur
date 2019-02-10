package listener;

import evra.path.unlur.GameActivity;
import evra.path.unlur.R;
import exception.FullGroundException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import application.SavedGame;





public class SaveListener implements OnClickListener
{
// -----------------------
// Attributs
// -----------------------
	private boolean			clearAppAfterSave;
	private Activity		act;
	private EditText		editText;

// -----------------------
// Constructeur
// -----------------------
	public SaveListener(boolean clearAppAfterSave, Activity act)
	{
		this.clearAppAfterSave	= clearAppAfterSave;
		this.act				= act;
	}

// -----------------------
// Methodes locales
// -----------------------
	@Override
	public void onClick(View arg0)
	{
		showMainDialog();
	}
	private void showMainDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(act);
		this.editText				= new EditText(act);
		this.editText				.setHint(R.string.saveHint);

		builder.setMessage			(R.string.saveLabel);
		builder.setPositiveButton	(R.string.save,		new SaveOkListener());
		builder.setNegativeButton	(R.string.cancel,	null);
		builder.setView				(editText);
		builder.show();
	}
public class SaveOkListener implements DialogInterface.OnClickListener
{
//-----------------------
//Attributs
//-----------------------
	private String			str;

//-----------------------
//Methodes locales
//-----------------------
	@Override
	public void onClick(DialogInterface arg0, int arg1)
	{
		boolean test;
		this.		str	= editText.getText().toString();

		if (str.equals(""))															// Cas d'un nom vide
		{
			str = act.getResources().getString(R.string.saveEmptyNameMessage);
			Toast.makeText(act, str, Toast.LENGTH_LONG).show();
			return;
		}
		if (SavedGame.exists(str, act))												// Cas d'un nom qui existe deja
		{
			this.showSaveExistDialog();
			return;
		}

		test = SavedGame.save(str, act, GameActivity.app, GameActivity.pr);
		if (!test)																	// Cas d'une erreur de sauvegade
		{
			this.showSaveErrorDialog();
			return;
		}
		if (clearAppAfterSave)
		{
			GameActivity.app.clearAll();
			try								{GameActivity.app.playAI();}			// Faire jouer l'ia
			catch (FullGroundException e)	{e.printStackTrace();}
		}
	}

// -------------------------
// Methodes auxiliaires
// -------------------------
	/**===========================================================
	 * Demande a l'utilisateur si il souhaite efface la partie actuelle
	 =============================================================*/
	private void showSaveExistDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(act);

		builder.setMessage			(R.string.saveExistingMessage);
		builder.setPositiveButton	(R.string.replaceExistingSave,	new ReplaceListener());
		builder.setNeutralButton	(R.string.changeGameName,		new ChangeNameListener ());
		builder.setNegativeButton	(R.string.cancel,				null);
		builder.show();
	}
	private class ChangeNameListener implements DialogInterface.OnClickListener
	{
		public void onClick(DialogInterface arg0, int arg1)	{showMainDialog();}
	}
	private class ReplaceListener implements DialogInterface.OnClickListener
	{
		public void onClick(DialogInterface arg0, int arg1)
		{
			boolean test = SavedGame.save(str, act, GameActivity.app, GameActivity.pr);
			if (!test) {showSaveErrorDialog(); return;}
			if (clearAppAfterSave)
			{
				GameActivity.app.clearAll();
				try								{GameActivity.app.playAI();}			// Faire jouer l'ia
				catch (FullGroundException e)	{e.printStackTrace();}
			}
		}
	}
	/**===========================================================
	 * Indique a l'utilisateur une erreur lors de la sauvegarde
	 =============================================================*/
	private void showSaveErrorDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(act);

		builder.setIcon				(android.R.drawable.ic_dialog_alert);
		builder.setTitle			(R.string.saveErrorTitle);
		builder.setMessage			(R.string.saveErrorMessage);
		builder.setPositiveButton	(R.string.ok, null);
		builder.show();
	}
}
}