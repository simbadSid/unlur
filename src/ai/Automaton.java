package ai;

import exception.FullGroundException;
import application.Action;
import application.Application;





public interface Automaton
{
	public Action makeChoice(Application app) throws FullGroundException;
}
