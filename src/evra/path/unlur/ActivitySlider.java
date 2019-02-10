package evra.path.unlur;

import android.app.Activity;
import android.content.Intent;




public class ActivitySlider
{
	/**==============================================
	 * Permet la passage de l'activite actuelle act
	 * vers la nouvelle activite
	 * @param act
	 * @param newActivityName: nom de la classe contenant 
	 * l'activite a atteindre
	 ================================================*/
	public static void changeActivity(Activity act, String newActivityName)
	{
		String name		= "evra.path.unlur." + newActivityName;
		Intent intent	= new Intent();

		intent.setClassName("evra.path.unlur", name);
		act.startActivity(intent);
	}
	/**==============================================
	 * Permet la passage de l'activite actuelle act
	 * vers la nouvelle activite
	 * Ajoute a l'activite la valeur value associee a la clef key
	 * @param act
	 * @param newActivityName: nom de la classe contenant 
	 * l'activite a atteindre
	 ================================================*/
	public static void changeActivity(Activity act, String newActivityName, String key, boolean value)
	{
		String name		= "evra.path.unlur." + newActivityName;
		Intent intent	= new Intent();

		intent.setClassName("evra.path.unlur", name);
		intent.putExtra(key, value);
		act.startActivity(intent);
	}
	/**==============================================
	 * Permet la passage de l'activite actuelle act
	 * vers la nouvelle activite
	 * Ajoute a l'activite la valeur value associee a la clef key
	 * @param act
	 * @param newActivityName: nom de la classe contenant 
	 * l'activite a atteindre
	 ================================================*/
	public static void changeActivity(Activity act, String newActivityName, String key, int value)
	{
		String name		= "evra.path.unlur." + newActivityName;
		Intent intent	= new Intent();

		intent.setClassName("evra.path.unlur", name);
		intent.putExtra(key, value);
		act.startActivity(intent);
	}
}