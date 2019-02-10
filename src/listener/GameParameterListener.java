package listener;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import evra.path.unlur.ActivitySlider;




public class GameParameterListener implements OnClickListener
{
//-----------------------
//Attributs
//-----------------------
	private Activity	act;

//-----------------------
//Constructeur
//-----------------------
	public GameParameterListener(Activity act)
	{
		this.act	= act;
	}

//-----------------------
//Methodes locales
//-----------------------
	@Override
	public void onClick(View arg0)
	{
		ActivitySlider.changeActivity(act, "GameParameterActivity");
	}
}