package listener;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import evra.path.unlur.ColorParameterCustomChooserActivity;
import evra.path.unlur.R;





public class ColorParameterChooserCustomTextViewListener implements OnClickListener
{
// -----------------------
// Attributs
// -----------------------
	private ColorParameterCustomChooserActivity	act;
	private int									color;
	private SeekBar								seekBar;
	private EditText							editText;

// -----------------------
// Constructeur
// -----------------------
	public ColorParameterChooserCustomTextViewListener(ColorParameterCustomChooserActivity act, int color, SeekBar seekBar)
	{
		this.act		= act;
		this.color		= color;
		this.seekBar	= seekBar;
	}

// -----------------------
// Methodes locales
// -----------------------
	@Override
	public void onClick(View arg0)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(act);
		Resources res = act.getResources();
		int val;
		String str;

		str				= getColorText(res);
		str				= res.getString(R.string.setColorProgressMessage, str);
		val				= this.seekBar.getProgress();
		this.editText	= new EditText(act);
		this.editText	.setInputType(InputType.TYPE_CLASS_NUMBER);
		this.editText	.setText(""+val);

		builder.setMessage			(str);
		builder.setPositiveButton	(R.string.ok,		new OkButtonListener());
		builder.setNegativeButton	(R.string.cancel,	null);
		builder.setView(this.editText);
		builder.show();
	}

// -----------------------
// Methodes auxiliaires
// -----------------------
	/**===========================================================
	 * Demande a l'utilisateur si il souhaite efface la partie actuelle
	 =============================================================*/
	private class OkButtonListener implements DialogInterface.OnClickListener
	{
		public void onClick(DialogInterface arg0, int arg1)
		{
			int res;
			Resources resource = act.getResources();
			try
			{
				res = Integer.parseInt(editText.getText().toString());
				if ((res < 0) || (res > 255))	throw new Exception();
			}
			catch(Exception e)
			{
				String str = act.getResources().getString(R.string.setColorProgressError, getColorText(resource));
				Toast.makeText(act, str, Toast.LENGTH_LONG).show();
				return;
			}

			seekBar.setProgress(res);
			act.matchColorToSeekBar();
		}
	}
	private String getColorText(Resources res)
	{
		switch(color)
		{
			case Color.RED:			return res.getString(R.string.red);
			case Color.GREEN:		return res.getString(R.string.green);
			case Color.BLUE:		return res.getString(R.string.blue);
			case Color.TRANSPARENT:	return res.getString(R.string.transparency);
			default:				throw new RuntimeException("Unhandeled Color");
		}

	}
}