package listener;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class GameParameterCancelListener implements OnClickListener
{
//-----------------------
//Attributs
//-----------------------
	private Activity	act;

//-----------------------
//Constructeur
//-----------------------
	public GameParameterCancelListener(Activity act)
	{
		this.act		= act;
	}

//-----------------------
//Methodes locales
//-----------------------
	@Override
	public void onClick(View arg0)
	{
		act.finish();
	}
}