package listener;

import evra.path.unlur.ActivitySlider;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;




public class ContactUsListener implements OnClickListener
{
// -----------------------
// Attributs
// -----------------------
	private Activity		act;

// -----------------------
// Constructeur
// -----------------------
	public ContactUsListener(Activity act)
	{
		this.act		= act;
	}

// -----------------------
// Methodes locales
// -----------------------
	@Override
	public void onClick(View arg0)
	{
		ActivitySlider.changeActivity(act, "ContactUsActivity");
	}
}