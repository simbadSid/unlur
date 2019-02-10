package graphic;

import ihm.GraphicGround;

import evra.path.unlur.R;

import java.util.Iterator;
import java.util.LinkedList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import application.Application;
import auxiliaire.AuxGeneral;








public class GroundView extends View
{
// -------------------------
// Attributs
// -------------------------
	public static final int		ELEMENT_FIELD		= 1;
	public static final int		ELEMENT_OUTLINE		= 2;
	public static final int		ELEMENT_WHITE_PAWN	= 3;
	public static final int		ELEMENT_BLACK_PAWN	= 4;

	private Paint				fieldPaint;
	private Paint 				outlinePaint;
	private Paint				whitePawnPaint;
	private Paint				blackPawnPaint;
	private Paint				winCrossPaint;

	private static final int	nbrDegrade			= 17;
	private static final int	winCrossSize		= 5;

	// Variables Graphiques du panneau
	private Application			app					= null;
	private GraphicGround		gg					= null;
	private Paint				paintCircle			= new Paint();
	private Paint				paintHexa			= new Paint();

// -------------------------
// Constructeur
// -------------------------
	public GroundView(Context context)
	{
		super(context, null, 0);
		initAttribut();
	}
	public GroundView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initAttribut();
	}
	public GroundView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		initAttribut();
	}

// -------------------------
// Methodes Locales
// -------------------------
	/**======================================================
	 * Initialise la communication entre la vue et l'application
	 ========================================================*/
	public void init(Application app, ColorHistory ch)
	{
		if (app != null)	this.app = app;
		if (ch != null)		this.setColorHistory(ch, false);
		this.gg.resetDim(this.app.getDim());
	}
	/**======================================================
	 * Refait l'affichage en fonction du contenu de l'application
	 * et du terrain graphique
	 =========================================================*/
	public void refresh()	{this.invalidate();}

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

		this.gg.resize(width, height);													// Dimensionne le terrain graphique
	}
	/**====================================================
	 * Methode call back de dessin de la View
	 * Redessine toute la vue en fonction de app
	 * Quitte sans rien fiare si la fenetre n'a pas ete initialisee
	 ======================================================*/
	@Override
	protected void onDraw(Canvas canvas)
	{
		if ((this.app == null) || (this.gg == null) ||
			(this.gg.isInitialized() == false))			return;		// Non initialisee

		this.drawGround(canvas);
		this.drawOutline(canvas);
		this.drawAllPawns(canvas);
		this.drawWinPath(canvas);
	}

// -------------------------------------------------
// Accesseur
// -------------------------------------------------
		public ColorHistory		getColorHistory()		{return new ColorHistory(fieldPaint, outlinePaint, whitePawnPaint, blackPawnPaint, winCrossPaint);}
		public GraphicGround	getGraphicGround()		{return this.gg;}

// -------------------------------------------------
// Modificateur
// -------------------------------------------------
		public void setApplication		(Application app)	{this.app				= app;}
		public void	setFieldColor		(int col)			{this.fieldPaint		.setColor(col);	this.invalidate();}
		public void setOutlineColor		(int col)			{this.outlinePaint		.setColor(col);	this.invalidate();}
		public void	setWhitePawnColor	(int col)			{this.whitePawnPaint	.setColor(col);	this.invalidate();}
		public void setBlackPawnColor	(int col)			{this.blackPawnPaint	.setColor(col);	this.invalidate();}
		public void setColorHistory		(ColorHistory ch, boolean invalidate)
		{
			this.fieldPaint				.setColor(ch.field);
			this.outlinePaint			.setColor(ch.outline);
			this.whitePawnPaint			.setColor(ch.white);
			this.blackPawnPaint			.setColor(ch.black);
			this.winCrossPaint			.setColor(ch.win);

			if (invalidate) this.invalidate();
		}

//----------------------------------------------------
//Methodes D'affichage elementaire
//----------------------------------------------------
		/**======================================================
		 * Affiche l'ensemble du terrain
		 ========================================================**/
		private void drawGround(Canvas canvas)
		{
			Iterator<Point> it = this.gg.getHexagonRealCenterIterator();
			while(it.hasNext())
			{
				Point center = it.next();
				drawHexagon(canvas, center, true, true);
			}	
		}
		/**=======================================================
		 * Affiche les contours du terrain
		 =========================================================**/
		private void drawOutline(Canvas canvas)
		{
			Iterator<Point> it = this.gg.getHexagonRealCenterIterator();
			while(it.hasNext())
			{
				Point center = it.next();
				drawHexagon(canvas, center, false, true);
			}	
		}
		/**================================================
		 * Affiche l'historique
		 ==================================================**/
		private void drawAllPawns(Canvas canvas)
		{
			Iterator<Point> it;

			it = this.app.getBlackPointIterator();
			while (it.hasNext())	this.drawPawn(canvas, it.next(), this.blackPawnPaint.getColor());

			it = this.app.getWhitePointIterator();
			while (it.hasNext())	this.drawPawn(canvas, it.next(), this.whitePawnPaint.getColor());
		}
		/**================================================
		 * Affiche un pion
		 * @param p: centr du pion en coordonnee index du terrain
		 ==================================================**/
		private void drawPawn(Canvas canvas, Point p, int color)
		{
			Point center 	= gg.groundToReal(p);
			double radius	= gg.getRadiusPlay();
			for (int i = 0; i<=nbrDegrade; i++)
			{
				double x			= radius / (double)(nbrDegrade);
				int r				= (int)(radius - i * x);
				int c				= AuxGeneral.getColorGradient(color, nbrDegrade, i);
				this.paintCircle	.setColor(c);
				canvas.drawCircle(center.x, center.y, r, paintCircle);
			}
		}
		/**================================================
		 * Affiche le chemin gagnant par des croix
		 ==================================================**/
		private void drawWinPath(Canvas canvas)
		{
			LinkedList<Point> winPath = app.getWinPath();
			Point center;

			if (winPath == null) return;
			for (int i=0; i<winPath.size(); i++)
			{
				center	= gg.groundToReal(winPath.get(i));
				this.drawWinCross(canvas, center);
			}
		}
		/**===========================================================
		 * Affiche un hexagone de centre "center" et de couler groundColor
		 * @param center: centre de l'hexagone (en coordones reel)
		 * @param drawField: indique si l'on affiche l'hexagone
		 * @param drawOutline: indique si l'on affiche les contours de l'hexagone
		 =============================================================**/
		private void drawHexagon(Canvas canvas, Point center, boolean drawField, boolean drawOutline)
		{
			Path hexagone = new Path();
			Point p0, p;

			p0 = gg.summit(center, 0);							// Construire l'hexagone
			hexagone.moveTo(p0.x, p0.y);
			for (int i = 1; i<6; i++) 
			{
				p = gg.summit(center, i);
				hexagone.lineTo(p.x, p.y);
			}
			hexagone.lineTo(p0.x, p0.y);

			if (drawField)										// Affichage de l'hexagone
			{
				paintHexa.setColor(this.fieldPaint.getColor());
				paintHexa.setStyle(Style.FILL);
				canvas.drawPath(hexagone, paintHexa);
			}
			if (drawOutline)									// Affichage des contours de l'hexagone
			{
				paintHexa.setColor(this.outlinePaint.getColor());
				paintHexa.setStyle(Style.STROKE);
				canvas.drawPath(hexagone, paintHexa);
			}
		}
		/**==========================================================
		 * Affiche une croix de victoire su "center" (en coordones reel)
		 ============================================================**/
		private void drawWinCross(Canvas canvas, Point center)
		{
			int r = gg.getRadiusPlay();
			int x = (int)(r * Math.cos(Math.PI/4));
			int y = (int)(r * Math.sin(Math.PI/4));

			canvas.drawLine(center.x-x, center.y-y, center.x+x, center.y+y, this.winCrossPaint);
			canvas.drawLine(center.x-x, center.y+y, center.x+x, center.y-y, this.winCrossPaint);
		}

// -------------------------
// Methodes Auxiliaires
// -------------------------
	private void initAttribut()
	{
		Resources		res		= getResources();

		this.gg					= new GraphicGround();

		this.fieldPaint			= new Paint(Paint.ANTI_ALIAS_FLAG);
		this.outlinePaint		= new Paint(Paint.ANTI_ALIAS_FLAG);
		this.whitePawnPaint		= new Paint(Paint.ANTI_ALIAS_FLAG);
		this.blackPawnPaint		= new Paint(Paint.ANTI_ALIAS_FLAG);
		this.winCrossPaint		= new Paint(Paint.ANTI_ALIAS_FLAG);

		this.fieldPaint			.setColor(res.getColor(R.color.defaultFieldColor));
		this.outlinePaint		.setColor(res.getColor(R.color.defaultOutlineColor));
		this.whitePawnPaint		.setColor(res.getColor(R.color.defaultWhitePawnColor));
		this.blackPawnPaint		.setColor(res.getColor(R.color.defaultBlackPawnColor));
		this.winCrossPaint		.setColor(res.getColor(R.color.defaultWinCrossColor));
		this.winCrossPaint		.setStrokeCap(Paint.Cap.SQUARE);
		this.winCrossPaint		.setStrokeWidth(winCrossSize);
	}
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