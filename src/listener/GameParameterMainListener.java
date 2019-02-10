package listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;

import evra.path.unlur.ActivitySlider;
import evra.path.unlur.R;

public class GameParameterMainListener implements OnClickListener
{
//-----------------------
//Attributs
//-----------------------
	private Activity	act;

//-----------------------
//Constructeur
//-----------------------
	public GameParameterMainListener(Activity act)
	{
		this.act	= act;
	}

//-----------------------
//Methodes locales
//-----------------------
	@Override
	public void onClick(View arg0)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(act);

		builder.setMessage			(R.string.parameterMainMessage);
		builder.setPositiveButton	(R.string.Game,	new GameButtonListener());
		builder.setNegativeButton	(R.string.color,new ColorButtonListener());
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.show();
	}

//-----------------------
//Methode Auxiliaire
//-----------------------
	private class GameButtonListener implements DialogInterface.OnClickListener
	{
		public void onClick(DialogInterface arg0, int arg1)	{ActivitySlider.changeActivity(act, "GameParameterActivity");}
	}
	private class ColorButtonListener implements DialogInterface.OnClickListener
	{
		public void onClick(DialogInterface arg0, int arg1)	{ActivitySlider.changeActivity(act, "ColorParameterGridChooserActivity");}
	}
}