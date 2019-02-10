package listener;

import evra.path.unlur.ColorParameterCustomChooserActivity;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;





public class ColorParameterCustomSeekListener implements OnSeekBarChangeListener
{
// --------------------------------
// Attributs
// --------------------------------
	private ColorParameterCustomChooserActivity	act;

// --------------------------------
// Constructeur
// --------------------------------
	public ColorParameterCustomSeekListener(ColorParameterCustomChooserActivity act)
	{
		this.act	= act;
	}

// --------------------------------
// Methodes locales
// --------------------------------
	@Override
	public void onStartTrackingTouch(SeekBar arg0)	{}
	@Override
	public void onStopTrackingTouch(SeekBar arg0)	{}
	@Override
	public void onProgressChanged(SeekBar arg0, int progress, boolean fromUser)
	{
		if (!fromUser)	return;

		act.matchColorToSeekBar();
	}
}