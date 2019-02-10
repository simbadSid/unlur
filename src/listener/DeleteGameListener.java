package listener;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import application.SavedGame;

import evra.path.unlur.LoadActivity;
import evra.path.unlur.R;




public class DeleteGameListener implements OnClickListener
{
// -----------------------
// Attributs
// -----------------------
	private LoadActivity		act;

// -----------------------
// Constructeur
// -----------------------
	public DeleteGameListener(LoadActivity act)
	{
		this.act		= act;
	}

// -----------------------
// Methodes locales
// -----------------------
	@Override
	public void onClick(View arg0)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(act);

		builder.setTitle			(R.string.deleteGameTitle);
		builder.setMessage			(R.string.deleteGameMessage);
		builder.setPositiveButton	(R.string.deleteGame,	new DeleteButtonListener());
		builder.setNegativeButton	(R.string.cancel,		null);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.show();
	}
	private class DeleteButtonListener implements DialogInterface.OnClickListener
	{
		public void onClick(DialogInterface arg0, int arg1)
		{
			String game = act.getLoaddedGameName();

			if ((game == null) || (!SavedGame.delete(game, act)))	showDeleteErrorDialog();
			else													act.finish();
		}
	}
	/**===========================================================
	 * Indique a l'utilisateur une erreur lors de la sauvegarde
	 * @param finishActAfterPrint: indique si l'on fait act.finish en clickant sur OK
	 =============================================================*/
	private void showDeleteErrorDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(act);
		String str = act.getLoaddedGameName();
		str = act.getResources().getString(R.string.deleteGameError, str);

		builder.setIcon				(android.R.drawable.ic_dialog_alert);
		builder.setMessage			(str);
		builder.setPositiveButton	(R.string.ok, null);
		builder.show();
	}
}