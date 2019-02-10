package listener;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import application.Application;

import evra.path.unlur.R;





public class UndoListener implements OnClickListener
{
// -----------------------
// Attributs
// -----------------------
	private Application		app;
	private Context			context;

// -----------------------
// Constructeur
// -----------------------
	public UndoListener(Application app, Context ctx)
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
		boolean test = this.app.undo();
		if (!test)
		{
			String text = context.getString(R.string.undoError);
			Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
		}
	}
}