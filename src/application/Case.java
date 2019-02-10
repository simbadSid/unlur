package application;




public class Case
{
// --------------------------------------------- 
// Attributs:
//---------------------------------------------- 
	public final static int freeCase	= 0;
	public final static int whiteCase	= 1;
	public final static int blackCase	= 2;

	private int codeCase;

// ---------------------------------------------
// Constructeur:
// ---------------------------------------------	
	public Case (int code) throws RuntimeException
	{
		if (!isValidCase(code)) throw new RuntimeException("Erreur dans le constructeur de \"Case\"");
		this.codeCase = code;
	}
	public Case (Case c)
	{
		this.codeCase = c.codeCase;
	}
// ---------------------------------------------
// Modificateur:
// ---------------------------------------------
	public void setCase(int c)
	{
		if (!isValidCase(c)) throw new RuntimeException("Erreur dans \"Case.setCase\": parametre non pris en charge.");
		this.codeCase = c;
	}
// ---------------------------------------------
// Accesseur:
// ---------------------------------------------
	public boolean	isFree()		{return (this.codeCase == freeCase);}
	public boolean	isWhite()		{return (this.codeCase == whiteCase);}
	public boolean	isBlack()		{return (this.codeCase == blackCase);}
	public int		getCaseType()	{return this.codeCase;}
	public boolean	equals(int c)	{return (this.codeCase == c);}
	public boolean	equals(Case c)	{return (this.codeCase == c.codeCase);}
	public String	toString()		{return ""+this.codeCase;}
	public static boolean isValidCase(int code)
	{
		if ((code < 0) || (code > 2)) return false;
		return true;
	}
}