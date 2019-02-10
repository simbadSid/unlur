package application;

import graphic.ColorHistory;
import graphic.Printer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import android.app.Activity;
import android.content.Context;
import auxiliaire.AuxGeneral;






public class SavedGame
{
	/**===========================================================
	 * Sauvegarde la partie actuellement contenue dans GameActivity.app
	 * dans le fichier gameName
	 * Si une partie de ce nom existe deja elle sera ecrasee
	 * @return - false en cas d'erreur lors de la sauvegarde
	 =============================================================*/
	public static boolean save(String gameName, Activity act, Application app, Printer pr)
	{
 		FileOutputStream	fic = null;
		FileWriter			f	= null;
		ColorHistory		ch;
		boolean				res;

		try
		{
			fic		= act.openFileOutput(gameName, Context.MODE_PRIVATE);
			f		= new FileWriter(fic.getFD());
			f		.write(AuxGeneral.getDate(act) +"\n");						// Ecrire la date
			ch		= pr.getColorHistory();
			ch		.save(f);													// Ecrire les couleur
			app		.save(f);													// Ecrire l'etat de l'application
			f		.flush();
			fic		.close();
			res		= true;
		}
		catch (Exception e)	{res = false;}
		finally
		{
			try {f.close(); fic	.close();}
			catch (IOException e) {}
		}

		return res;
	}
	/**===========================================================
	 * Charge la partie name dans GameActivity.app
	 * @return - false en cas d'erreur lors du chargement
	 =============================================================*/
	public static LoadInfo load(String gameName, Activity act, Application app, Printer pr)
	{
		FileInputStream		fic = null;
		Scanner				sc	= null;
		ColorHistory		ch	= new ColorHistory();
		boolean				test;
		String				date;

		try
		{
			fic		= act.openFileInput(gameName);
			sc		= new Scanner(fic);
			date	= sc.next();								// Lire la date
			ch		.load(sc);									// Lire les couleurs
			pr		.setColorHistory(ch, false);
			test	= app.load(sc);								// Lire l'etat de l'application
			if (!test) return null;		
		}
		catch (Exception e)	{return null;}
		finally
		{
			try {fic.close(); sc.close();}
			catch (IOException e) {}
		}

		return new LoadInfo(app, date);
	}
	/**===========================================================
	 * Determine si une partie sauvegardee de ce nom existe deja
	 =============================================================*/
	public static boolean exists(String gameName, Activity act)
	{
		String[] fileList = act.fileList();

		for (int i=0; i<fileList.length; i++)
			if (fileList[i].equals(gameName))	return true;

		return false;
	}
	/**===========================================================
	 * Determine s'il y a un jeux sauvegarde
	 =============================================================*/
	public static boolean isSavedGame(Activity act)
	{
		return (act.fileList().length != 0);
	}
	/**===========================================================
	 * Determine la liste des parties sauvegardees
	 =============================================================*/
	public static String[] getSavedGameList(Activity act)
	{
		return act.fileList();
	}
	/**===========================================================
	 * Supprime definitivement la partie du systeme
	 * @return - false en cas d'erreur lors de la suppression
	 =============================================================*/
	public static boolean delete(String gameName, Activity act)
	{
		return act.deleteFile(gameName);
	}
}