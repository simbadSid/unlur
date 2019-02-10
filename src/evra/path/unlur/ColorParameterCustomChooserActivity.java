package evra.path.unlur;

import graphic.ColorHistory;
import graphic.GroundView;
import graphic.Printer;
import ihm.IHM;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.SeekBar;
import application.Application;





public class ColorParameterCustomChooserActivity extends Activity
{
// -----------------------------
// Attributs
// -----------------------------
	public Application		app;
	public Printer			pr;
	public int				elementId = -1;

// -----------------------------
// Methode principale
// -----------------------------
	protected void onCreate(Bundle savedInstanceState)
	{
		ColorHistory ch;

		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_color_choose_custom);

		if (app == null)
		{
			pr		= new Printer();
			app		= Application.exhibitionApplication(pr);
		}

		elementId	= GroundView.ELEMENT_FIELD;
		ch			= GameActivity.pr.getColorHistory();
		IHM.initActivityColorParameterCustomChooser(this, app, pr, ch);
		matchSeekBarToColor();
	}

// -----------------------------
// Methode locales
// -----------------------------
	/**==================================================
	 * Fait correspondre la progression des seekbar a la
	 * couleur de l'element actuel
	 ====================================================*/
	public void matchSeekBarToColor()
	{
		SeekBar sb;
		ColorHistory ch = GameActivity.pr.getColorHistory();
		int t, r, g, b;

		switch(elementId)
		{
			case GroundView.ELEMENT_FIELD:		t = Color.alpha(ch.field);	r = Color.red(ch.field);	g = Color.green(ch.field);	b = Color.blue(ch.field);	break;
			case GroundView.ELEMENT_OUTLINE:	t = Color.alpha(ch.outline);r = Color.red(ch.outline);	g = Color.green(ch.outline);b = Color.blue(ch.outline);	break;
			case GroundView.ELEMENT_BLACK_PAWN:	t = Color.alpha(ch.black);	r = Color.red(ch.black);	g = Color.green(ch.black);	b = Color.blue(ch.black);	break;
			case GroundView.ELEMENT_WHITE_PAWN:	t = Color.alpha(ch.white);	r = Color.red(ch.white);	g = Color.green(ch.white);	b = Color.blue(ch.white);	break;
			default:	throw new RuntimeException("Unhandeled elementId");
		}

		sb	= (SeekBar)findViewById(R.id.colorChooserCustomSeekerTransparency);
		sb	.setProgress(t);

		sb	= (SeekBar)findViewById(R.id.colorChooserCustomSeekerRed);
		sb	.setProgress(r);

		sb	= (SeekBar)findViewById(R.id.colorChooserCustomSeekerGreen);
		sb	.setProgress(g);

		sb	= (SeekBar)findViewById(R.id.colorChooserCustomSeekerBlue);
		sb	.setProgress(b);
	}
	/**==================================================
	 * Fait correspondre la couleur de l'element actuel a
	 *  la progression des seekbar
	 ====================================================*/
	public void matchColorToSeekBar()
	{
		int t, r, g, b, color;
		SeekBar sb;

		sb	= (SeekBar)findViewById(R.id.colorChooserCustomSeekerTransparency);
		t	= sb.getProgress();

		sb	= (SeekBar)findViewById(R.id.colorChooserCustomSeekerRed);
		r	= sb.getProgress();

		sb	= (SeekBar)findViewById(R.id.colorChooserCustomSeekerGreen);
		g	= sb.getProgress();

		sb	= (SeekBar)findViewById(R.id.colorChooserCustomSeekerBlue);
		b	= sb.getProgress();

		color= Color.argb(t, r, g, b);

		switch(elementId)
		{
			case GroundView.ELEMENT_FIELD:			pr.setFieldColor(color);	break;
			case GroundView.ELEMENT_OUTLINE:		pr.setOutlineColor(color);	break;
			case GroundView.ELEMENT_BLACK_PAWN:		pr.setBlackPawnColor(color);break;
			case GroundView.ELEMENT_WHITE_PAWN:		pr.setWhitePawnColor(color);break;
			default:	throw new RuntimeException("Unhandeled elementId");
		}
	}
}