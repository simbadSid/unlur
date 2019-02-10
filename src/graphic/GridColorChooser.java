package graphic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import auxiliaire.AuxGeneral;

public class GridColorChooser extends View
{
// -------------------------
// Attributs
// -------------------------
	private final int	nbrColorX	= 5;
	private final int	nbrColorY	= 5;
	private final int	sepXPercent = 2;								// % de la separation par rapport a la largeur
	private final int	sepYPercent = 2;								// %de la separation par rapport a la hauteur

	private int[][]		colorTab	= new int[nbrColorX][nbrColorY];
	private int			widthSquare;
	private int			heightSquare;
	private int			sepX;
	private int			sepY;
	private Paint		paint		= new Paint();

// -------------------------
// Constructeur
// -------------------------
	public GridColorChooser(Context context)
	{
		super(context, null, 0);
	}
	public GridColorChooser(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	public GridColorChooser(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}

// -------------------------
// Methodes Locales
// -------------------------
	/**====================================================
	 * Methode call back de dimensionnement de la View
	 ======================================================*/
	@Override
	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec)
	{
		DisplayMetrics	metrics		= getContext().getResources().getDisplayMetrics();	// Dimensions de l'ecran
		int				screenWidth	= metrics.widthPixels;								// Largeur de l'ecran
		int				screenHeight= metrics.heightPixels;								// Hauteur de l'ecran

		int				width		= singleMeasure(widthMeasureSpec,	screenWidth);	// Dim du panneau voulu
		int				height		= singleMeasure(heightMeasureSpec,	screenHeight);

		setMeasuredDimension(width, height);											// Dimensionne le panneau

		this.sepX			= (width  * sepXPercent) / 100;
		this.sepY			= (height * sepYPercent) / 100;
		this.widthSquare	= (width  - sepX * (nbrColorX+1)) / nbrColorX;
		this.heightSquare	= (height - sepY * (nbrColorY+1)) / nbrColorY;
	}
	/**====================================================
	 * Methode call back de dessin de la View
	 * Redessine toute la vue en fonction de app
	 * Quitte sans rien fiare si la fenetre n'a pas ete initialisee
	 ======================================================*/
	@Override
	protected void onDraw(Canvas canvas)
	{
		int x		= sepX;
		int y		= sepY;
		int xc		= 0;
		int yc		= 0;
		int color	= 0;
		int add		= 0xFFFFFF / (nbrColorX * nbrColorY);

		while(xc < nbrColorX)
		{
			while(yc < nbrColorY)
			{
				colorTab[xc][yc]= AuxGeneral.getColor(color);
				paint			.setColor(colorTab[xc][yc]);
				canvas			.drawRect(x, y, x+widthSquare, y+heightSquare, paint);
				color			+= add;
				y				+= heightSquare + sepY;
				yc				++;
			}
			x	+= widthSquare + sepX;
			xc	++;
			y	= sepY;
			yc	= 0;
		}
	}
	/**===================================================================
	 * Determine la couleur presente sur la fenetre au coordonnes (x, y)
	 * @param x, y - coordonnees dans le ref de la fenetre
	 * @return la couleur , ou -1 si (x, y) correspond a un vide
	 =====================================================================*/
	public int getColor(int x, int y)
	{
		int w	= widthSquare 	+ sepX;
		int h	= heightSquare 	+ sepY;
		int xc	= x / w;
		int yc	= y / h;

		if ((xc	>= nbrColorX)	|| (yc >= nbrColorY))	return -1;

		return colorTab[xc][yc];
	}

//----------------------------------------------------
// Methodes auxiliaires
//----------------------------------------------------
	/**========================================================
	 * Calcule la bonne mesure sur un axe uniquement
	 * @param spec - Type de Mesure sur un axe(void onMesure dans cette classe)
	 * @param screenDim - Dimension de l'écran sur cet axe
	 * @return - la bonne taille sur cet axe
	 ==========================================================*/
	private int singleMeasure(int spec, int screenDim)
	{
		int mode = MeasureSpec.getMode(spec);
		int size = MeasureSpec.getSize(spec);

		// Si le layout n'a pas précisé de dimensions, la vue prendra la moitié de l'écran
		if(mode == MeasureSpec.UNSPECIFIED)	return screenDim/2;
	    // Sinon, elle prendra la taille demandée par le layout
		else								return size;
	}
}