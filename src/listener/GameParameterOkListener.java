package listener;

import ai.ArtificialInteligence;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import application.Application;

import evra.path.unlur.GameActivity;
import evra.path.unlur.R;
import exception.FullGroundException;




public class GameParameterOkListener implements OnClickListener
{
// -----------------------
// Attributs
// -----------------------
	private Activity			act;
	private int					dim = -1;

//-----------------------
//Constructeur
//-----------------------
	public GameParameterOkListener(Activity act)
	{
		this.act		= act;
	}

//-----------------------
//Methodes locales
//-----------------------
	@Override
	public void onClick(View arg0)
	{
		Application app = GameActivity.app;

		dim	= readDim();												// Lire le premier argument
		if (dim	== -1)	return;											// Cas d'une saisie fausse
		if ((dim != app.getDim()) && (dim != 0) && (!app.isClear()))	// Cas d'un changement de dim (=> restart game)
		{
			this.showDialogActivity();									//		Demander a l'utilisateur s'il veux restart game
			return;														//		Reagire au choix dans le traitant
		}

		onClickStep2();
	}

	private void onClickStep2()
	{
		Application app = GameActivity.app;
		int players, AI;
		String name1, name2;
		EditText et;

		et		= (EditText) act.findViewById(R.id.inputPlayer1);
		name1	= et.getText().toString();
		if (name1.equals(""))	name1 = null;
		et		= (EditText) act.findViewById(R.id.inputPlayer2);
		name2	= et.getText().toString();
		if (name2.equals(""))	name2 = null;
		players	= this.readPlayers();
		AI = this.readAILevel();

		if ((name1 != null) && (!name1.equals(app.getPlayerName1())))	app.setPlayerName1(name1);		// Changement des param
		if ((name2 != null) && (!name2.equals(app.getPlayerName2())))	app.setPlayerName2(name2);
		if (AI != app.getAILevel())										app.setAILevel(AI);
		try	{if (players != app.getPlayerAI())							app.setAI(players);}
		catch(FullGroundException e){Toast.makeText(act, R.string.fullGroundError, Toast.LENGTH_LONG).show();}
		if (dim > 0)													app.setDim(dim);				// En dernier car reinitialise l'app

		act.finish();
	}

// -------------------------
// Methodes auxiliaires
// -------------------------
	/**==========================================================
	 * Lit la dim saisie par l'utilisateur
	 * @return -1 en cas d'erreur
	 * @return 0 en cas de saisie vide
	 ============================================================*/
	private int readDim()
	{
		EditText et;
		Editable edt;
		String str;
		int dim;

		et	= (EditText)act.findViewById(R.id.inputGroundDim);
		edt = et.getText();
		str	= edt.toString();
		if (str.equals(""))	return 0;										// Pas de changement

		try
		{
			dim = Integer.parseInt(str);									// Saisie non entiere
			if ((dim < Application.minDim) ||
				(dim > Application.maxDim))	throw new Exception();			// Saisie invalide
		}
		catch(Exception e)
		{
			str = act.getResources().getString(R.string.groundDimError, Application.minDim, Application.maxDim);
			Toast.makeText(act, str, Toast.LENGTH_LONG).show();
			return -1;
		}

		return dim;
	}
	/**==========================================================
	 * Lit le type de joueur (noIA, IA1, IA2)
	 ============================================================*/
	private int readPlayers()
	{
		RadioButton rb;

		rb = (RadioButton) act.findViewById(R.id.player2AIRadioButton);
		if (rb.isChecked())		return Application.player2AI;

		rb = (RadioButton) act.findViewById(R.id.player1AIRadioButton);
		if (rb.isChecked())		return Application.player1AI;

		rb = (RadioButton) act.findViewById(R.id.twoPlayersRadioButton);
		if (rb.isChecked())		return Application.noAI;

		throw new RuntimeException("Unknown players radio button");
	}
	/**==========================================================
	 * Lit les niveaux de l'IA
	 ============================================================*/
	private int readAILevel()
	{
		RadioButton rb;

		rb = (RadioButton) act.findViewById(R.id.AIlevel1RadioButton);
		if (rb.isChecked())		return ArtificialInteligence.level1;

		rb = (RadioButton) act.findViewById(R.id.AIlevel2RadioButton);
		if (rb.isChecked())		return ArtificialInteligence.level2;

		rb = (RadioButton) act.findViewById(R.id.AIlevel3RadioButton);
		if (rb.isChecked())		return ArtificialInteligence.level3;

		throw new RuntimeException("Unknown AI radio button");
	}
	/**===========================================================
	 * Demande a l'utilisateur si il souhaite efface la partie actuelle
	 =============================================================*/
	private void showDialogActivity()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(act);

		builder.setTitle(R.string.resetDimTitle);
		builder.setMessage(R.string.resetDimMessage);
		builder.setPositiveButton(R.string.reset,	new ResetListener());
		builder.setNegativeButton(R.string.cancel,	null);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.show();
	}
	private class ResetListener implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface arg0, int arg1){onClickStep2();}
	}
}