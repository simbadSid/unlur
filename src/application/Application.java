package application;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import evra.path.unlur.R;
import exception.FullGroundException;

import ai.ArtificialInteligence;
import android.content.Context;
import android.graphics.Point;
import android.widget.Toast;
import graphic.Printer;









public class Application
{
// -----------------------------------------------
// Attributs:
// -----------------------------------------------
		public final static int			defaultDimension	= 5;
		public final static int			exhibitionDimension	= 3;
		public final static int			minDim				= 3;
		public final static int			maxDim				= 9;

		public final static int			noAI				= 0;
		public final static int			player1AI			= 1;
		public final static int			player2AI			= 2;

		public final static int			initialAiLevel		= ArtificialInteligence.level2;
		private final static int		winStep				= 2;

		private final static Point[]	exhibitionGame		= {new Point(1, 1), new Point(1, 3), new Point(2, 1), new Point(2, 3)};

		// Variables du module
		private Ground					ground;
		private Printer					printer;
		private ArtificialInteligence	AI;
		private	int 					player;
		private int						step;
		private int						playerAI;
		private String[]				playerName			= {"Kobe", "Bryant"};
		private int						playerWhite;
		private History					history;
		private LinkedList<Point>		winPath;

// ----------------------------------------------
// Constructeur:
// ----------------------------------------------
		public Application(Printer pr)
		{
			this.printer		= pr;
			this.ground			= new Ground(defaultDimension);
			this.winPath		= null;
			this.step 			= 0;
			this.player			= 0;
			this.playerWhite	= -1;
			this.playerAI		= player2AI;
			this.AI				= new ArtificialInteligence(initialAiLevel);
			this.history		= new History();
		}
		/**===================================================================
		 * Cree une application reserve a l'affichage de ColorParameterActivity
		 * Tous les parametres ne sont pas initialise
		 * La dimmension et les coups joues sont precisés en attributs: exhibitionDimension et
		 * exhibitionPawns
		 * Cette application ne peux pas etre utilise pour le jeux, mais que pour afficher un terrain particulier
		 ======================================================================*/
		public static Application exhibitionApplication(Printer pr) throws RuntimeException
		{
			Application res	= new Application(pr);
			Action hc		= new Action(null, Action.stepChange);

			res.ground		= new Ground(exhibitionDimension);
			res.history		= new History();
			res.history		.add(hc);

			for (int i=0; i<exhibitionGame.length; i++)
			{
				hc = new Action(exhibitionGame[i], Action.currentGame);
				res.history.add(hc);
			}

			return res;
		}

// ---------------------------------------------
// Accesseur
// ---------------------------------------------
		public String			getPlayerName1			()	{return new String(playerName[0]);}
		public String			getPlayerName2			()	{return new String(playerName[1]);}
		public String			getPlayerName			()	{return new String(playerName[player]);}
		public int				getDim					()	{return ground.getDimension();}
		public Ground			getGround				()	{return this.ground;}
		public int				getPlayerAI				()	{return playerAI;}
		public int				getAILevel				()	{return this.AI.getAiLevel();}
		public int				getStep					()	{return this.step;}
		public Iterator<Point>	getBlackPointIterator	()	{return history.getPointIterator(false);}
		public Iterator<Point>	getWhitePointIterator	()	{return history.getPointIterator(true);}
		public LinkedList<Point>getWinPath				()	{if (winPath == null) return null; return new LinkedList<Point>(winPath);}
		public int				getNbrCase				()	{return this.ground.getNbrCase();}
		public boolean			isPrevious				()	{return history.isPrevious();}
		public boolean			isNext					()	{return history.isNext();}
		public boolean			isClear					()	{return history.isEmpty();}
		public boolean			isCurrentPlayerWhite	()	{return this.player == this.playerWhite;}

// ---------------------------------------------
// modificateur:
// ---------------------------------------------
		public void		setPlayerName1	(String name)	{this.playerName[0] = new String(name);	this.printer.setPlayerName();}
		public void		setPlayerName2	(String name)	{this.playerName[1] = new String(name);	this.printer.setPlayerName();}
		public void		setAILevel		(int aiLevel)	{this.AI.setAiLevel(aiLevel);}
		public void		setAI(int ai) throws FullGroundException
		{
			if ((ai != noAI) && (ai != player1AI) && (ai != player2AI))	throw new RuntimeException();
			this.playerAI = ai;

			this.playAI();
		}

// ---------------------------------------------
// Methodes d'execution des commandes:
// Les parametres du modules sont mis a jours
// ---------------------------------------------
		/**==============================================
		 * Joue un coup a la case p
		 * @param p: Case du terrain
		 * @return -4 si p n'est pas une case libre
		 * @return -3 si p n'est pas une position valide
		 * @return -2 si le jeux est termine
		 * @return -1 si p est sur le bord et que step = 0
		 * @return 0 en cas de victoire
		 * @return 1 en cas de defaite
		 * @return 2 si non
		 ===============================================**/
		public int play(Point p)
		{
			Action hc;
			int c, res;
			boolean test;

			if (this.step == winStep)						return -2;
			if (!this.ground.isValidPosition(p.x, p.y)) 	return -3;
			if (!this.ground.isFreeCase(p.x, p.y))			return -4;

			switch (this.step)
			{
				case 0:															// Etape 1
						if (this.ground.isSide(p.x, p.y)) 	return-1;			//		Cas ou le coup est sur les bord
						c = Case.blackCase;
						this.ground.setCase(p.x, p.y, c);						//		Mise a jour de la matrice du terrain
						hc = new Action(p, Action.currentGame);
						res = 2;
						break;
				case 1:															// Etape 2
						if (player == playerWhite)	c = Case.whiteCase;
						else						c = Case.blackCase;
						test = this.ground.setCase(p.x, p.y, c);				//		Mise a jour du terrain
						if (!test) return 1;
						winPath = new LinkedList<Point> ();
						Boolean win = getWin(winPath);							//		Calculer le chemin victorieux
						if (win == null)										//		Cas d'un coup sans victoire
						{
								hc		= new Action(p, Action.currentGame);
								winPath	= null;
								res		= 2;
						}
						else													//		Cas d'une victoire
						{
								step	= winStep;								//		Changer d'etape
								hc		= new Action(p, Action.winGame);
								if (win)res	= 0;
								else	res = 1;
						}
						break;
				default:
						throw new RuntimeException("step = " + step);
			}
			this.history.add(hc);												// Mis a jours de l'historique
			this.player = (player + 1)%2;										// Mise a jours du joueur
			this.printer.play();
			return res;
		}
		/**==========================================================
		 * Determine si c'est le tour de l'AI de jouer
		 * Si oui, la fonction joue un coup ou passe a l'etape 2
		 * @return null if it is not the AI turn
		 * @return 3 if the AI has changed step
		 * @return the return value of the methode play
		 * @throws FullGroundException si l'AI doit jouer mais que le terrain est plein
		 ==============================================================**/
		public Integer playAI() throws FullGroundException
		{
			if (playerAI == noAI)							return null;
			if ((playerAI == player1AI) && (player != 0))	return null;
			if ((playerAI == player2AI) && (player != 1))	return null;

			Action a = this.AI.makeChoice(this);
			if		(a.isStepChange())	{this.goToStep2(); return 3;}
			else if	(a.isCurrentGame())
			{
				int res = this.play(a.newPoint);
				if (res < 0) throw new RuntimeException("Wrong automaton choice: " + res);
				return res;
			}
			else						throw new RuntimeException("Unknonw Ai choice");
		}
		/**===============================================
		 * Fait passer le jeux a l'etape 2
		 * Le jeux doit imperativement etre en etape 1
		 =================================================**/
		public boolean goToStep2()
		{
			if (this.step != 0) return false;

			this.step			= 1;
			this.player			= (this.player + 1)%2;
			this.playerWhite	= this.player;
			Action hc		= new Action(null, Action.stepChange);
			this.history		.add(hc);
			this.printer		.goToStep2();

			return true;
		}
		/**=================================================
		 * Reinitialisation des param du jeux sans changer la
		 * dim du terrain
		 ===================================================**/
		public void clearAll()
		{
			if (this.history.isEmpty())	return;

			this.step				= 0;
			this.ground				.reset(null);
			this.winPath			= null;
			this.player				= 0;
			this.playerWhite		= -1;
			this.history			= new History();
			this.printer			.clearAll();
		}
		/**=================================================
		 * Reinitialisation des param du jeux en changent
		 * dim du terrain
		 ===================================================**/
		public void	setDim(int dim)
		{
			if ((dim < minDim) || (dim > maxDim))	throw new RuntimeException("Invalid dim: " + dim);
			if (dim == ground.getDimension())		return;
			this.ground				= new Ground(dim);
			this.winPath			= null;
			this.step				= 0;
			this.player				= 0;
			this.playerWhite		= -1;
			this.history			= new History();
			this.printer			.setDim(dim);
		}
		/**======================================================
		 * Annule le dernier coup joue
		 * Annule le dernier coup de l'AI
		 * @return false si il n'y a pas de coup precedent
		 ========================================================**/
		public boolean undo()
		{
			boolean twoPrevious = false;

			if (!history.isPrevious())				return false;	// Cas ou il n'y a pas de jeu precedent
			if ((playerAI != noAI) ||
				((step == winStep) && 
				 (((playerAI == player1AI) && (player == 1)) ||
				  ((playerAI == player2AI) && (player == 0)))))
			{
				if (!this.history.are2Previous())	return true;	// Cas ou le seule precedent est celui de l'ia
				else twoPrevious = true;
			}
			auxUndo();
			if (twoPrevious) auxUndo();
			return true;
		}
		private void auxUndo()
		{
			Action hc;
			Point p;

			hc = this.history	.getPrevious();
			this.history		.goBackward();
			this.player 		= (player + 1)%2;

			if (hc.isStepChange())								// Cas ou le jeu ete un changement d'etape
			{
					this.playerWhite	= -1;
					this.step			= 0;
			}
			else												// Cas ou le jeux est un coup (x, y)
			{
					if (hc.isWin())								//		Cas ou le jeux ete gagne: Arreter le clignotement du chemain
					{
						this.step		= 1;
						this.winPath	= null;
					}
					p = hc.newPoint;
					this.ground.setCase(p.x, p.y, Case.freeCase);
			}
			this.printer.undo(hc);
		}
		/**===================================================
		 * Rejoue le dernier coup annule et rafraichie
		 * Rejoue le dernier coup de l'AI
		 * la fenetre graphique
		 * @return false s'il n'y a pas de coup suivant
		 =====================================================**/
		public boolean redo()
		{
			if (!this.history.isNext()) return false;

			int casee = -1;
 			Action hc	= this.history.getNext();
			this.history	.goForward();
			this.player		= (player + 1) % 2;

			if (hc.isStepChange())						// Cas ou le coup suivant est un changement d'etape
			{
					this.step			= 1;
					this.playerWhite	= this.player;
			}
			else										// Cas ou le coup suivant est un jeux(victoire possible)
			{
					int pla	= (player + 1) % 2;
					if (pla == playerWhite) casee = Case.whiteCase;
					else 					casee = Case.blackCase;
					this.ground.setCase(hc.newPoint.x, hc.newPoint.y, casee);
					if (hc.isWin())
					{
						this.step		= winStep;
						this.winPath	= new LinkedList<Point>();
						Boolean test	= getWin(winPath);
						if (test == null) throw new RuntimeException();
					}
			}
			if (((playerAI == player1AI) && (player == 0)) ||
				((playerAI == player2AI) && (player == 1)))
					this.redo();
			else this.printer.redo(hc);
			return true;
		}
		/**==========================================================
		 * Enregistre une partie dans le fichier f selon le format 
		 * indique dans UnlurHistory
		 * @return true en cas de reussite et false si non
		 * @throws IOException
		 ============================================================*/
		public void save(FileWriter fic) throws IOException
		{
				fic.write(this.ground.getDimension()	+ "\n");		// Ecrire la dim du jeux
				fic.write(this.player					+ "\n");		// Ecrire l'id du joueur
				fic.write(this.playerWhite				+ "\n");		// Ecrire l'id du joueur blanc
				fic.write(this.step						+ "\n");		// Ecrire l'etape
				fic.write(this.playerAI					+ "\n");		// Ecrire l'identifiant de l'ia
				fic.write(this.AI.getAiLevel()			+ "\n");		// Ecrire le niveau de l'ia
				fic.write(this.playerName[0]			+ "\n");		// Ecrire le nom du joueur 1
				fic.write(this.playerName[1]			+ "\n");		// Ecrire le nom du joueur 2
				this.history.saveHistory(fic);							// Ecrire l'historique
		}
		/**==========================================================
		 * Charge une partie depuis le fichier f
		 * Reinitialise l'ensemble des donnes et la fenetre graphique
		 * @return true en cas de reussite et false si non (sans changement des parametres)
		 ============================================================**/
		public boolean load(Scanner sc)
		{
			int dim, player, playerWhite, step, ai, aiLevel;
			String name1, name2;
			History history;
			Ground ground;
			boolean win;
			Boolean test;

			try 													// Charger l'historique
			{
				dim				= sc.nextInt();						//		Lire la dim du jeux
				player			= sc.nextInt();						//		Lire l'id du joueur
				playerWhite		= sc.nextInt();						//		Lire l'id du joueur
				step			= sc.nextInt();						//		Lire l'etape
				ai				= sc.nextInt();						//		Lire l'id de l'ia
				aiLevel			= sc.nextInt();						//		Lire le niveau de l'ia
				name1			= sc.next();						// 		Lire le nom des joueurs
				name2			= sc.next();
				history			= new History();
				ground			= new Ground(dim);
				win				= history.loadHistory(sc, ground);
			}
			catch (Exception e) {e.printStackTrace(); return false;}// Cas d'une erreur lors du chargement
			this.ground			= ground;							// Si non, on change les parametres courrents
			this.player			= player;
			this.playerWhite	= playerWhite;
			this.step			= step;
			this.playerAI		= ai;
			this.AI				.setAiLevel(aiLevel);
			this.playerName[0] 	= name1;
			this.playerName[1] 	= name2;
			this.history		= history;
			this.winPath		= null;
			if (win)
			{
				winPath = new LinkedList<Point>();
				test	= getWin(winPath);
				if (test == null)	throw new RuntimeException();
			}
			this.printer.load();
			return true;
		}
		/**==========================================================
		 * Charge une partie depuis le l'application app
		 * Reinitialise l'ensemble des donnes et la fenetre graphique
		 ============================================================**/
		public void load(Application app)
		{
			this.ground			= new Ground(app.ground);
			this.player			= app.player;
			this.playerWhite	= app.playerWhite;
			this.step			= app.step;
			this.playerAI		= app.playerAI;
			this.AI				.setAiLevel(app.getAILevel());
			this.playerName[0] 	= new String(app.playerName[0]);
			this.playerName[1] 	= new String(app.playerName[1]);
			this.history		= new History(app.history);
			this.winPath		= app.getWinPath();

			this.printer.load();
		}
		/**=====================================================================
		 * Affiche un message correspondant au resultat de la fonction play
		 * @return true si le play a change le coup a ete realise par play
		 * @return false si non
		 =======================================================================*/
		public static boolean printPlayResult(Application app, int res, Context context)
		{
			String str;

			switch (res)
			{
				case -2: return false;												// Cas ou p n'est pas une case valable
				case -1:															// Cas ou p est un bord pendand l'etape 1
					str = context.getString(R.string.bordError);
					Toast.makeText(context, str, Toast.LENGTH_LONG).show();
					return false;
				case 0:																// Cas d'un coup gagnant
					str = context.getString(R.string.winMessage, app.getPlayerName());
					Toast.makeText(context, str, Toast.LENGTH_LONG).show();
					return false;
				case 1:																// Cas d'un coup perdant
					str = context.getString(R.string.looseMessage, app.getPlayerName());
					Toast.makeText(context, str, Toast.LENGTH_LONG).show();
					return false;
				case 3:																// Cas ou l'AI a change d'etape
					str = context.getString(R.string.AIChangeStep, app.getPlayerName());
					Toast.makeText(context, str, Toast.LENGTH_LONG).show();
					return true;
				default: return true;
			}
		}
		/**==========================================================
		 * @return l'identifiant du joueur gagnant
		 * @return -1 si aucun des 2 joueur n'a gagne
		 * @param: si l'un des joueur a gagne, winPath serra rempli des points gagnants
		 ==============================================================**/
		private Boolean getWin(LinkedList<Point> winPath)
		{
			LinkedList<LinkedList<Point>> ll;
			LinkedList<Point> l;
			Analyzer a;
			winPath.clear();

			if (this.step == 0)								return null;

			a = new Analyzer(this.ground);
			ll = a.getWinnerWhite(false);
			if (ll != null)				{winPath.addAll(ll.getFirst()); return true;}
			ll = a.getWinnerBlack(false);
			if (ll != null) 			{winPath.addAll(ll.getFirst());	return true;}

			l = a.getLooserWhite();
			if (l != null)				{winPath.addAll(l); return false;}
			l = a.getLooserBlack();
			if (l != null) 				{winPath.addAll(l);	return false;}

			else											return null;
		}
}