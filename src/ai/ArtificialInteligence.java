package ai;

import exception.FullGroundException;
import application.Action;
import application.Application;





public class ArtificialInteligence
{
// -----------------------------------------------
// Attributs:
// -----------------------------------------------
	public final static int			level1				= 0;
	public final static int			level2				= 1;
	public final static int			level3				= 2;

	private int						aiLevel;
	private Automaton				automaton;

// ----------------------------------------------
// Constructeur:
// ----------------------------------------------
	public ArtificialInteligence(int aiLevel)
	{
		switch(aiLevel)
		{
			case level1: this.automaton = new AutomatonLevel1(); break;
			case level2: this.automaton = new AutomatonLevel2(); break;
			case level3: this.automaton = new AutomatonLevel3(); break;
			default: throw new RuntimeException("Unknown ai level: " + aiLevel);
		}
		this.aiLevel = aiLevel;
	}

// ----------------------------------------------
// Methodes mocames:
// ----------------------------------------------
	public int getAiLevel()
	{
		return this.aiLevel;
	}
	public void setAiLevel(int aiLevel)
	{
		if (aiLevel == this.aiLevel) return;
		switch(aiLevel)
		{
			case level1: this.automaton = new AutomatonLevel1(); break;
			case level2: this.automaton = new AutomatonLevel2(); break;
			case level3: this.automaton = new AutomatonLevel3(); break;
			default: throw new RuntimeException("Unknown ai level: " + aiLevel);
		}
		this.aiLevel = aiLevel;
	}
	public Action makeChoice(Application app) throws FullGroundException
	{
		return this.automaton.makeChoice(app);
	}
}