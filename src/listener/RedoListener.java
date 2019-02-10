package listener;

import evra.path.unlur.R;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import application.Application;






public class RedoListener implements OnClickListener
{
// -----------------------
// Attributs
// -----------------------
	private Application		app;
	private Context			context;

// -----------------------
// Constructeur
// -----------------------
	public RedoListener(Application app, Context ctx)
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
		boolean test = this.app.redo();
		if (!test)
		{
			String text = context.getString(R.string.redoError);
			Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
		}
	}
}