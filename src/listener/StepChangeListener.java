package listener;

import evra.path.unlur.R;
import exception.FullGroundException;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import application.Application;






public class StepChangeListener implements OnClickListener
{
// -----------------------
// Attributs
// -----------------------
	private Application		app;
	private Context			context;

// -----------------------
// Constructeur
// -----------------------
	public StepChangeListener(Application app, Context ctx)
	{
		this.app		= app;
		this.context	= ctx;
	}

// -----------------------
// Methodes locales
// -----------------------
	@Override
	public void onClick(View v)
	{
		boolean test = this.app.goToStep2();
		Integer test0;

		if (!test)
		{
			String text = context.getString(R.string.stepChangeError);
			Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
			return;
		}
		try
		{
			test0 = app.playAI();												// Faire jouer l'ia
			if (test0 != null) Application.printPlayResult(app, test0, context);
		}
		catch (FullGroundException e)
		{
			Toast.makeText(context, R.string.fullGroundError, Toast.LENGTH_LONG).show();
		}
	}
}