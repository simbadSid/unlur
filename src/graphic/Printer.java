package graphic;

import evra.path.unlur.GameActivity;
import evra.path.unlur.R;

import android.widget.TextView;
import application.Application;
import application.Action;











public class Printer
{
// --------------------------------------------
// Attributs:
// --------------------------------------------
	private Application		app;
	private GroundView		groundView;
	private TextView		stepView;
	private TextView		playerView;

// --------------------------------------------
// Accesseur:
// --------------------------------------------
	public ColorHistory		getColorHistory()	{return groundView.getColorHistory();}

// --------------------------------------------
// Modificateur:
// --------------------------------------------
	public void setColorHistory		(ColorHistory ch, boolean refresh)	{groundView.setColorHistory	(ch, refresh);}
	public void setFieldColor		(int color)							{groundView.setFieldColor(color);}
	public void setOutlineColor		(int color)							{groundView.setOutlineColor(color);}
	public void setWhitePawnColor	(int color)							{groundView.setWhitePawnColor(color);}
	public void setBlackPawnColor	(int color)							{groundView.setBlackPawnColor(color);}

//--------------------------------------------
// Methodes locales
//--------------------------------------------
	/**=====================================================
	 * Initialise l'affichage de tous les panneaux et
	 * menu pour l'activite GameActivite
	 =======================================================*/
	public void initGameActivity(GameActivity act, Application app, ColorHistory ch)
	{
		this.app				= app;
		this.groundView			= (GroundView)	act.findViewById(R.id.groundView);
		this.stepView			= (TextView)	act.findViewById(R.id.stepValue);
		this.playerView			= (TextView)	act.findViewById(R.id.playerValue);

		this.groundView			.init(app, ch);
		this.groundView			.refresh();

		this.stepView			.setText(this.getStepRepresentation());
		this.playerView			.setText(this.app.getPlayerName());
	}
	/**=====================================================
	 * Initialise l'affichage de tous les panneaux et
	 * menu pour l'activite ColorParameterActivite
	 =======================================================*/
	public void initExhibitionActivity(Application app, GroundView gv, ColorHistory ch)
	{
		this.app				= app;
		this.groundView			= gv;
		this.groundView			.init(app, ch);
		this.groundView			.refresh();
	}
	/**=====================================================
	 * Refait l'affichage du panneau playerNameWindow
	 =======================================================*/
	public void setPlayerName()
	{
		this.playerView.setText(app.getPlayerName());
	}
	/**=====================================================
	 * Affiche le coup joue p
	 * Affiche la liste des points d'une victoire winPath si non null
	 =======================================================*/
	public void play()
	{
		this.groundView		.refresh();
		this.stepView		.setText(getStepRepresentation());
		this.playerView		.setText(app.getPlayerName());
	}
	/**=====================================================
	 * Realise l'affichage du changement d"etape
	 =======================================================*/
	public void goToStep2()
	{
		this.playerView			.setText(app.getPlayerName());
		this.stepView			.setText(this.getStepRepresentation());
	}
	/**=====================================================
	 * Reinitialise l'ensemble des panneaux graphiques
	 =======================================================*/
	public void clearAll()
	{
		this.groundView			.refresh();

		this.stepView			.setText(this.getStepRepresentation());
		this.playerView			.setText(this.app.getPlayerName());
	}
	/**=====================================================
	 * Reinitialise le terrain graphique
	 * Aucun affichage n'est fait
	 =======================================================*/
	public void setDim(int dim)
	{
		this.groundView			.getGraphicGround().resetDim(dim);
		this.groundView			.refresh();
		this.stepView			.setText(this.getStepRepresentation());
		this.playerView			.setText(this.app.getPlayerName());
	}
	/**=====================================================
	 * Annule l'affichage du dernier coup joue hc
	 =======================================================*/
	public void undo(Action hc)
	{
		if (hc.isStepChange())
			this.stepView		.setText(getStepRepresentation());

		this.groundView			.refresh();
		this.playerView			.setText(app.getPlayerName());
	}
	/**=====================================================
	 * Refait l'affichage du prochain coup joue
	 =======================================================*/
	public void redo(Action hc)
	{
		if (hc.isStepChange())
			this.stepView		.setText(getStepRepresentation());

		this.groundView			.refresh();
		this.playerView			.setText(app.getPlayerName());
	}
	/**=====================================================
	 * Refait l'affichage de tous les panneaux apres le chargement 
	 * d'une nouvelle partie
	 =======================================================*/
	public void load()
	{
		this.groundView			.getGraphicGround().resetDim(app.getDim());
		this.groundView			.refresh();

		if (this.playerView != null)
		{
			this.playerView			.setText(app.getPlayerName());
			this.stepView			.setText(""+app.getStep());
		}
	}

// -----------------------------
// Definie la ressource correspondant
// a la valeur du param recherche
// -----------------------------
	private int getStepRepresentation()
	{
		int step = this.app.getStep();

		switch(step)
		{
			case 0:		return R.string.step0;
			case 1:		return R.string.step1;
			case 2:		return R.string.step2;
			default:	throw new RuntimeException("unknown step value");
		}
	}
}