package listener;

import graphic.GroundView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import evra.path.unlur.ColorParameterGridChooserActivity;
import evra.path.unlur.R;




public class ColorParameterChooserGridIdListener implements OnClickListener
{
// ---------------------------
// Attributs
// ---------------------------
	private Activity		act;
	private int				checkedItem = -1;
	private String[]		dialogItems	= null;

// ---------------------------
// Constructeut
// ---------------------------
	public ColorParameterChooserGridIdListener(Activity act)
	{
		this.act = act;

		String		str;
		Resources	res = act.getResources();
		Button		b	= (Button)act.findViewById(R.id.colorChooserGridParameterId);

		switch (ColorParameterGridChooserActivity.elementId)
		{
			case GroundView.ELEMENT_FIELD:			str = res.getString(R.string.field);		this.checkedItem = 0; break;
			case GroundView.ELEMENT_OUTLINE:		str = res.getString(R.string.outline);		this.checkedItem = 1; break;
			case GroundView.ELEMENT_BLACK_PAWN:		str = res.getString(R.string.blackPawn);	this.checkedItem = 2; break;
			case GroundView.ELEMENT_WHITE_PAWN:		str = res.getString(R.string.whitePawn);	this.checkedItem = 3; break;
			default:								throw new RuntimeException("unhandeled containerId");
		}
		str = res.getString(R.string.colorChooserParameterId, str);
		b.setText(str);
		this.initDialogItems();
	}

// ---------------------------
// Methode locale
// ---------------------------
	public void onClick(View v)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(act);

		builder.setTitle			(R.string.elementColorMessage);
		builder.setPositiveButton	(R.string.ok,				new OkListener());
		builder.setNegativeButton	(R.string.cancel,			null);
		builder.setSingleChoiceItems(dialogItems, checkedItem,	new checkListener());
		builder.show();
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
		public void onClick(DialogInterface dialog, int which)
		{
			Resources	res	= act.getResources();
			String		str	= res.getString(R.string.colorChooserParameterId, dialogItems[checkedItem]);
			Button		b	= (Button)act.findViewById(R.id.colorChooserGridParameterId);

			b.setText(str);
			switch(checkedItem)
			{
				case 0:	ColorParameterGridChooserActivity.elementId	= GroundView.ELEMENT_FIELD;		break;
				case 1:	ColorParameterGridChooserActivity.elementId	= GroundView.ELEMENT_OUTLINE;	break;
				case 2:	ColorParameterGridChooserActivity.elementId	= GroundView.ELEMENT_BLACK_PAWN;break;
				case 3:	ColorParameterGridChooserActivity.elementId	= GroundView.ELEMENT_WHITE_PAWN;break;
				default:	throw new RuntimeException("Unhandeled elementId");
			}
		}
	}
	private void initDialogItems()
	{
		Resources res = act.getResources();
		this.dialogItems	= new String[4];

		this.dialogItems[0]	= res.getString(R.string.field);
		this.dialogItems[1]	= res.getString(R.string.outline);
		this.dialogItems[2]	= res.getString(R.string.blackPawn);
		this.dialogItems[3]	= res.getString(R.string.whitePawn);
	}
}