package listener;

import graphic.GridColorChooser;
import graphic.GroundView;
import graphic.Printer;

import evra.path.unlur.ColorParameterGridChooserActivity;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;








public class ColorChooserGridListener implements OnTouchListener
{
// -----------------------
// Attributs
// -----------------------
	private GridColorChooser	gridColorChooser;
	private Printer				pr;

// -----------------------
// Constructeur
// -----------------------
	public ColorChooserGridListener(GridColorChooser gridColorChooser, Printer pr)
	{
		this.gridColorChooser	= gridColorChooser;
		this.pr					= pr;
	}

// -----------------------
// Methodes locales
// -----------------------
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1)
	{
		if (arg1.getAction() != MotionEvent.ACTION_DOWN) return false;

		int		x	= (int) arg1.getX();
		int		y	= (int) arg1.getY();
		int		color	= gridColorChooser.getColor(x, y);

		if (color == -1)	return true;
		switch (ColorParameterGridChooserActivity.elementId)
		{
			case GroundView.ELEMENT_FIELD:			pr.setFieldColor(color);		break;
			case GroundView.ELEMENT_OUTLINE:		pr.setOutlineColor(color);		break;
			case GroundView.ELEMENT_BLACK_PAWN:		pr.setBlackPawnColor(color);	break;
			case GroundView.ELEMENT_WHITE_PAWN:		pr.setWhitePawnColor(color);	break;
			default:								throw new RuntimeException("unhandeled containerId");
		}
		return true;
	}
}