package evra.path.unlur;

import ihm.IHM;
import ai.ArtificialInteligence;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import application.Application;






public class GameParameterActivity extends Activity
{
// -----------------------------
// Methode principale
// -----------------------------
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_game_parameter);

		initActivity();
		IHM.initActivityGameParameter(this);
	}

// -----------------------------
// Methode auxiliaire
// -----------------------------
	/**===============================================
	 * Initialise le panneau en fonction du contenu de app
	 =================================================*/
	private void initActivity()
	{
		Application app = GameActivity.app;
		EditText	et;
		String		str;
		RadioButton	rb;

		et	= (EditText)findViewById(R.id.inputGroundDim);
		str	= getResources().getString(R.string.groundDimHint, Application.minDim, Application.maxDim);
		et	.setHint(str);

		et	= (EditText)findViewById(R.id.inputPlayer1);
		et	.setText(app.getPlayerName1());

		et	= (EditText)findViewById(R.id.inputPlayer2);
		et	.setText(app.getPlayerName2());

		switch(app.getPlayerAI())
		{
			case Application.noAI:
				rb	= (RadioButton)findViewById(R.id.twoPlayersRadioButton);
				rb	.setChecked(true);
				break;
			case Application.player1AI:
				rb	= (RadioButton)findViewById(R.id.player1AIRadioButton);
				rb	.setChecked(true);
				break;
			case Application.player2AI:
				rb	= (RadioButton)findViewById(R.id.player2AIRadioButton);
				rb	.setChecked(true);
				break;
		}
		switch(app.getAILevel())
		{
			case ArtificialInteligence.level1:
				rb	= (RadioButton)findViewById(R.id.AIlevel1RadioButton);
				rb	.setChecked(true);
				break;
			case ArtificialInteligence.level2:
				rb	= (RadioButton)findViewById(R.id.AIlevel2RadioButton);
				rb	.setChecked(true);
				break;
			case ArtificialInteligence.level3:
				rb	= (RadioButton)findViewById(R.id.AIlevel3RadioButton);
				rb	.setChecked(true);
				break;
			default: throw new RuntimeException("Unknown AI level");
		}
	}
}