package application;

public class LoadInfo
{
// -------------------------------
// Attributs
// -------------------------------
	public String	player1;
	public String	player2;
	public String	dim;
	public String	step;
	public String	date;

// -------------------------------
// Constructeur
// -------------------------------
	public LoadInfo(){}
	public LoadInfo(LoadInfo li)
	{
		this.player1	= new String(li.player1);
		this.player2	= new String(li.player2);
		this.dim		= new String(li.dim);
		this.step		= new String(li.step);
		this.date		= new String(li.date);
	}
	public LoadInfo(Application app, String date)
	{
		this.player1	= app.getPlayerName1();
		this.player2	= app.getPlayerName2();
		this.dim		= "" + app.getDim();
		this.step		= "" + (app.getStep()+1);
		this.date		= new String(date);
	}
}