package evra.path.unlur;

import ihm.IHM;
import android.app.Activity;
import android.os.Bundle;









public class MainActivity extends Activity
{
// -----------------------------
// Methode principale
// -----------------------------
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);

		IHM.initActivityMain(this);

		ActivitySlider.changeActivity(this, "GameActivity", GameActivity.keyHide, true);	// Lancer l'activite dans la reveiller
																							// Permet d'initialiser l'application
																							// Pour etre utilise par les autres activites
	}
}