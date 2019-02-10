package evra.path.unlur;

import graphic.ColorHistory;
import graphic.GroundView;
import graphic.Printer;
import ihm.IHM;
import android.app.Activity;
import android.os.Bundle;
import application.Application;









public class ColorParameterGridChooserActivity extends Activity
{
// -----------------------------
// Attributs
// -----------------------------
	public static Application		app;
	public static Printer			pr;
	public static int				elementId = -1;

// -----------------------------
// Methode principale
// -----------------------------
	protected void onCreate(Bundle savedInstanceState)
	{
		ColorHistory ch;

		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_color_choose_grid);

		if (app == null)
		{
			pr		= new Printer();
			app		= Application.exhibitionApplication(pr);
		}

		elementId	= GroundView.ELEMENT_FIELD;
		ch			= GameActivity.pr.getColorHistory();
		IHM.initActivityColorParameterGridChooser(this, app, pr, ch);
	}
}