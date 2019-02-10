package listener;

import evra.path.unlur.ActivitySlider;
import evra.path.unlur.GameActivity;
import evra.path.unlur.R;
import exception.FullGroundException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;






public class NewGameListener implements OnClickListener
{
// -----------------------
// Attributs
// -----------------------
	private Activity	act;
	private int			id;			// Identite du bouton conteneur

// -----------------------
// Constructeur
// -----------------------
	/**========================================================
	 * @param containerId: id du bouton contenant le listener
	 ==========================================================*/
	public NewGameListener(Activity act, int containerId)
	{
		this.act	= act;
		this.id		= containerId;
	}

// -----------------------
// Methodes locales
// -----------------------
	/**=========================================================
	 * Reagit en fonction de lactivite qui conteneur
	 ===========================================================*/
	@Override
	public void onClick(View v)
	{
		if (id == R.id.menuNewItem)	showDialogActivity();
		else						ActivitySlider.changeActivity(act, "GameActivity", GameActivity.keyClear, true);
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

		builder.setTitle(R.string.newGameTitle);
		builder.setMessage(R.string.newGameMessage);
		builder.setPositiveButton(R.string.newGame,	new ClearListener());
		builder.setNegativeButton(R.string.cancel,	null);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.show();
	}
	private class ClearListener implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface arg0, int arg1)
		{
			GameActivity.app.clearAll();
			try								{GameActivity.app.playAI();}		// Faire jouer l'ia
			catch (FullGroundException e)	{e.printStackTrace();}
			act.finish();
		}
	}
}