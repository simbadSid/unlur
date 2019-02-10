package listener;

import evra.path.unlur.ActivitySlider;
import evra.path.unlur.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;






public class ColorParameterColorChooserSetterListener implements OnClickListener
{
// ---------------------------
// Attributs
// ---------------------------
	public static final int		CHOOSER_GRID	= 0;
	public static final int		CHOOSER_CUSTOM	= 1;
	private static final int	nbrChooserType	= 2;

	private Activity		act;
	private int				containerType;
	private int				checkedItem = -1;
	private String[]		dialogItems;

// ---------------------------
// Constructeut
// ---------------------------
	public ColorParameterColorChooserSetterListener(Activity act, int checkedItem)
	{
		this.act			= act;
		this.containerType	= checkedItem;
		this.checkedItem	= checkedItem;
		this.initDialogItems();
	}

// ---------------------------
// Methode locale
// ---------------------------
	@Override
	public void onClick(View v)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(act);

		builder.setTitle			(R.string.colorChooserMessage);
		builder.setPositiveButton	(R.string.ok,				new OkListener());
		builder.setNegativeButton	(R.string.cancel,			null);
		builder.setSingleChoiceItems(dialogItems, containerType,new checkListener());
		builder.show();

		checkedItem = containerType;
	}

// ---------------------------
// Methode Auxiliaire
// ---------------------------
	private class checkListener implements DialogInterface.OnClickListener
	{
		public void onClick(DialogInterface arg0, int arg1)	{checkedItem = arg1;}
	}
	private class OkListener implements DialogInterface.OnClickListener
	{
		public void onClick(DialogInterface arg0, int arg1)
		{
			if (checkedItem == containerType)	return;
			switch(checkedItem)
			{
				case CHOOSER_GRID:	ActivitySlider.changeActivity(act, "ColorParameterGridChooserActivity");	break;
				case CHOOSER_CUSTOM:ActivitySlider.changeActivity(act, "ColorParameterCustomChooserActivity");	break;
				default: throw new RuntimeException("Unhandeled checkedItem");
			}
			act.finish();
			containerType = checkedItem;
		}
	}
	private void initDialogItems()
	{
		Resources res = act.getResources();
		this.dialogItems	= new String[nbrChooserType];

		this.dialogItems[CHOOSER_GRID]	= res.getString(R.string.gridColorChooser);
		this.dialogItems[CHOOSER_CUSTOM]= res.getString(R.string.customColorChooser);
	}
}