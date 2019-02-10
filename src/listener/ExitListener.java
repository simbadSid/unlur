package listener;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;





public class ExitListener implements OnClickListener
{
// -----------------------
// Attributs
// -----------------------
	private Activity act;

// -----------------------
// Constructeur
// -----------------------
	public ExitListener(Activity act)
	{
		this.act = act;
	}

// -----------------------
// Methodes locales
// -----------------------
	@Override
	public void onClick(View v)
	{
		act.finish();
	}
}