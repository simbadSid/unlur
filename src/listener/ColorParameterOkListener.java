package listener;

import graphic.ColorHistory;
import graphic.Printer;

import evra.path.unlur.GameActivity;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;




public class ColorParameterOkListener implements OnClickListener
{
// ------------------------
// Attribut
// ------------------------
	private Activity	act;
	private Printer		pr;

// ------------------------
// Constructeur
// ------------------------
	public ColorParameterOkListener(Activity act, Printer pr)
	{
		this.act	= act;
		this.pr		= pr;
	}

// ------------------------
// Methode locale
// ------------------------
	@Override
	public void onClick(View arg0)
	{
		ColorHistory chOld	= GameActivity	.pr.getColorHistory();
		ColorHistory chNew	= 				 pr.getColorHistory();

		if (!chOld.equals(chNew))	GameActivity.pr.setColorHistory(chNew, true);
		act.finish();
	}
}