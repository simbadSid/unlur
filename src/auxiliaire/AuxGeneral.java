package auxiliaire;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;






/**********************************************
 * Methodes auxiliaires general
 * @author kassuskley
 **********************************************/




public class AuxGeneral
{
	/**----------------------------------------------
	 * Determine un degrade de la couleur c de degre d sachant
	 *    que le degres max est nbrDegrades
	 * @param c				: Couleur a degrader
	 * @param nbrDegrades	: nombre max de degres
	 * @param degrade		: degres de la couleur recherchee
	 * @param darkness		: vrai si le resulta doit etre plus foncé
	 -------------------------------------------------*/
	public static int getColorGradient(int color, int nbrDegrades, int d)
	{
		if ((nbrDegrades <= 0) || (d < 0) || (d > nbrDegrades)) throw new RuntimeException();
		boolean darkness;
		int red   = Color.red(color);
		int green = Color.green(color);
		int blue  = Color.blue(color);

		darkness = initDarkness(red, green, blue);
		if (darkness == true)
		{
			red   -= d * red   /(2*nbrDegrades);
			green -= d * green /(2*nbrDegrades);
			blue  -= d * blue  /(2*nbrDegrades);
		}
		else
		{
			red   += d * (255 - red)   / nbrDegrades;
			green += d * (255 - green) / nbrDegrades;
			blue  += d * (255 - blue)  / nbrDegrades;

		}
		return Color.rgb(red, green, blue);
	}
	// Indique si le degrade doit etre positif ou negatif
	private static boolean initDarkness(int red, int green, int blue)
	{
		int half 	= 256 / 2;
		int nbrPlus	= 0;

		if (red		> half)	nbrPlus ++;
		if (green	> half)	nbrPlus ++;
		if (blue	> half)	nbrPlus ++;
		if (nbrPlus > 1)	return true;
		else				return false;
	}
	/**---------------------------------------------------------------
	 * Cree une liste identique a l (en changeant tout les pointeurs de l)
	 -----------------------------------------------------------------*/
	public static LinkedList<Point> copyList(LinkedList<Point> l)
	{
		LinkedList<Point> res = new LinkedList<Point>();
		
		for (int i = 0; i<l.size(); i++)
		{
			Point pi = l.get(i);
			Point pf = new Point(pi.x, pi.y);
			res.addLast(pf);
		}
		return res;
	}
	/**---------------------------------------------------------------
	 * Determine le code de la couleur de code rgb donne en entree
	 * @param codeColor
	 * @return
	 -----------------------------------------------------------------*/
	public static int getColor(int colorCode)
	{
		int r = (colorCode & 0xFF0000) >> 16;
		int g = (colorCode & 0x00FF00) >> 8;
		int b = colorCode & 0x0000FF;

		return Color.rgb(r, g, b);
	}
	/**---------------------------------------------------------------
	 * Rend la date actuelle:
	 * @return - Mois-Jour-Year:time
	 -----------------------------------------------------------------*/
	@SuppressLint("SimpleDateFormat")
	public static String getDate(Activity act)
	{
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());

		SimpleDateFormat df = new SimpleDateFormat("MMM-dd-yyyy:HH:mm");
		return df.format(c.getTime());
	}
}