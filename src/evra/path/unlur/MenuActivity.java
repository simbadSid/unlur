package evra.path.unlur;

import ihm.IHM;
import android.app.Activity;
import android.os.Bundle;









public class MenuActivity extends Activity
{
// -----------------------------
// Methode principale
// -----------------------------
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_menu);

		IHM.initActivityMenu(this);
	}
}