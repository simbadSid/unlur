package graphic;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


import android.graphics.Paint;







public class ColorHistory
{
// ---------------------------
// Attributs
// ---------------------------
	public int field;
	public int outline;
	public int white;
	public int black;
	public int win;

// ---------------------------
// Constructeur
// ---------------------------
	public ColorHistory(Paint field, Paint outline, Paint whit, Paint black, Paint win)
	{
		this.field		= field		.getColor();
		this.outline	= outline	.getColor();
		this.white		= whit		.getColor();
		this.black		= black		.getColor();
		this.win		= win		.getColor();
	}
	public ColorHistory()	{}

// ---------------------------
// Methodes locales
// ---------------------------
	public void load(Scanner sc)
	{
		this.field		= sc.nextInt();
		this.outline	= sc.nextInt();
		this.white		= sc.nextInt();
		this.black		= sc.nextInt();
		this.win		= sc.nextInt();
	}
	public void save(FileWriter fic) throws IOException
	{
		fic.write(this.field		+ "\n");
		fic.write(this.outline		+ "\n");
		fic.write(this.white		+ "\n");
		fic.write(this.black		+ "\n");
		fic.write(this.win			+ "\n");
	}
	public boolean equals(ColorHistory ch)
	{
		if (ch.field		!= this.field)		return false;
		if (ch.outline		!= this.outline)	return false;
		if (ch.white		!= this.white)		return false;
		if (ch.black		!= this.black)		return false;
		if (ch.win			!= this.win)		return false;
		else									return true;
	}
}