package listener;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import evra.path.unlur.ActivitySlider;
import evra.path.unlur.LoadActivity;







public class LoadListener implements OnClickListener
{
// -----------------------
// Attributs
// -----------------------
	private Activity		act;
	private int				containerId;

// -----------------------
// Constructeur
// -----------------------
	public LoadListener(Activity act, int containerId)
	{
		this.act			= act;
		this.containerId	= containerId;
	}

// -----------------------
// Methodes locales
// -----------------------
	@Override
	public void onClick(View arg0)
	{
		ActivitySlider.changeActivity(	act,
										"LoadActivity",
										LoadActivity.keyContainerIdLoad,	// Clef du parametre a envoyer a l'activite
										containerId);						// Valeur du param a envoyer a l'activite
	}
}