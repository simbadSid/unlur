package ihm;

import evra.path.unlur.ColorParameterCustomChooserActivity;
import evra.path.unlur.GameActivity;
import evra.path.unlur.LoadActivity;
import evra.path.unlur.MainActivity;
import evra.path.unlur.MenuActivity;
import evra.path.unlur.R;
import listener.ClearAllListener;
import listener.ColorChooserGridListener;
import listener.ColorParameterChooserCustomIdListener;
import listener.ColorParameterChooserCustomTextViewListener;
import listener.ColorParameterChooserGridIdListener;
import listener.ColorParameterColorChooserSetterListener;
import listener.ColorParameterCustomSeekListener;
import listener.ColorParameterListener;
import listener.ColorParameterOkListener;
import listener.ContactUsListener;
import listener.DeleteGameListener;
import listener.ExitListener;
import listener.GameParameterCancelListener;
import listener.GameParameterListener;
import listener.GameParameterMainListener;
import listener.GameParameterOkListener;
import listener.LoadListener;
import listener.LoadOkListener;
import listener.LoadSelectionListener;
import listener.MenuListener;
import listener.NewGameListener;
import listener.PlayListener;
import listener.RedoListener;
import listener.RulesListener;
import listener.SaveListener;
import listener.StepChangeListener;
import listener.UndoListener;
import graphic.ColorHistory;
import graphic.GridColorChooser;
import graphic.GroundView;
import graphic.Printer;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import application.Application;






public class IHM
{
	/**===============================================
	 * Initialise les interface et les listener 
	 * de l'activite principale
	 =================================================*/
	public static void initActivityMain(MainActivity act)
	{
		Button b;

		b	= (Button)act.findViewById(R.id.buttonNewMain);
		b	.setOnClickListener(new NewGameListener(act, R.id.buttonNewMain));

		b	= (Button)act.findViewById(R.id.buttonLoadMain);
		b	.setOnClickListener(new LoadListener(act, R.id.buttonLoadMain));

		b	= (Button)act.findViewById(R.id.buttonRulesMain);
		b	.setOnClickListener(new RulesListener(act));

		b	= (Button)act.findViewById(R.id.buttonParametersMain);
		b	.setOnClickListener(new GameParameterMainListener(act));

		b	= (Button)act.findViewById(R.id.buttonContactUsMain);
		b	.setOnClickListener(new ContactUsListener(act));

		b	= (Button)act.findViewById(R.id.buttonExitMain);
		b	.setOnClickListener(new ExitListener(act));
	}
	/**===============================================
	 * Initialise les interface et les listener
	 * de l'activite de jeux
	 =================================================*/
	@SuppressLint("ClickableViewAccessibility")
	public static void initActivityGame(GameActivity act, Application app, Printer pr, ColorHistory ch)
	{
		GroundView		groundView		= (GroundView)act.findViewById(R.id.groundView);
		GraphicGround	gg				= groundView.getGraphicGround();
		ImageButton button;
		GameParameterListener listener	= new GameParameterListener(act);
		TextView tv;

		groundView			.setOnTouchListener(new PlayListener(app, gg, act));	// Init Ground View
		pr					.initGameActivity(act, app, ch);

		tv					= (TextView)act.findViewById(R.id.playerValue);
		tv					.setOnClickListener(listener);

		tv					= (TextView)act.findViewById(R.id.playerTitle);
		tv					.setOnClickListener(listener);

		button				= (ImageButton)act.findViewById(R.id.menuButton);		// Init MenuButton
		button				.setOnClickListener(new MenuListener(act));

		act.findViewById(R.id.buttonChangeStep)	.setOnClickListener(new StepChangeListener(app, act));	// Init Control Layout
		act.findViewById(R.id.buttonRedo)		.setOnClickListener(new RedoListener(app, act));
		act.findViewById(R.id.buttonUndo)		.setOnClickListener(new UndoListener(app, act));
		act.findViewById(R.id.buttonClearAll)	.setOnClickListener(new ClearAllListener(app, act));
	}
	/**===============================================
	 * Initialise les interface et les listener
	 * de l'activite menu
	 =================================================*/
	public static void initActivityMenu(MenuActivity act)
	{
		TextView view;

		view	= (TextView)act.findViewById(R.id.menuNewItem);
		view	.setOnClickListener(new NewGameListener(act, R.id.menuNewItem));

		view	= (TextView)act.findViewById(R.id.menuLoadItem);
		view	.setOnClickListener(new LoadListener(act, R.id.menuLoadItem));

		view	= (TextView)act.findViewById(R.id.menuSaveItem);
		view	.setOnClickListener(new SaveListener(false, act));

		view	= (TextView)act.findViewById(R.id.menuRulesItem);
		view	.setOnClickListener(new RulesListener(act));

		view	= (TextView)act.findViewById(R.id.menuGameItem);
		view	.setOnClickListener(new GameParameterListener(act));

		view	= (TextView)act.findViewById(R.id.menuColorItem);
		view	.setOnClickListener(new ColorParameterListener(act));
	}
	/**===============================================
	 * Initialise les interface et les listener
	 * de l'activite GameParameter
	 =================================================*/
	public static void initActivityGameParameter(Activity act)
	{
		Button b;

		b = (Button) act.findViewById(R.id.gameParameterOkButton);
		b.setOnClickListener(new GameParameterOkListener(act));
		b = (Button) act.findViewById(R.id.gameParameterCancelButton);
		b.setOnClickListener(new GameParameterCancelListener(act));
	}
	/**===============================================
	 * Initialise les interface et les listener
	 * de l'activite ColorParameterGridChooser
	 =================================================*/
	@SuppressLint("ClickableViewAccessibility")
	public static void initActivityColorParameterGridChooser(Activity act, Application app, Printer pr, ColorHistory ch)
	{
		Button b;
		GroundView			gv	= (GroundView)act.findViewById(R.id.colorGridGroundView);
		GridColorChooser	gcc	= (GridColorChooser)act.findViewById(R.id.gridColorChooser);

		pr	.initExhibitionActivity(app, gv, ch);

		gcc	.setOnTouchListener(new ColorChooserGridListener(gcc, pr));

		b	= (Button)act.findViewById(R.id.colorChooserGridParameterId);
		b	.setOnClickListener(new ColorParameterChooserGridIdListener(act));

		b	= (Button)act.findViewById(R.id.colorParameterChooseGridOkButton);
		b	.setOnClickListener(new ColorParameterOkListener(act, pr));

		b	= (Button)act.findViewById(R.id.colorParameterChooserGridSetChooserButton);
		b	.setOnClickListener(new ColorParameterColorChooserSetterListener(act, ColorParameterColorChooserSetterListener.CHOOSER_GRID));
	}
	/**===============================================
	 * Initialise les interface et les listener
	 * de l'activite ColorParameterCustomChooser
	 =================================================*/
	public static void initActivityColorParameterCustomChooser(ColorParameterCustomChooserActivity act, Application app, Printer pr, ColorHistory ch)
	{
		Button b;
		GroundView gv = (GroundView)act.findViewById(R.id.colorCustomGroundView);
		SeekBar sb;
		TextView tv;

		pr	.initExhibitionActivity(app, gv, ch);

		b	= (Button)act.findViewById(R.id.colorChooserCustomParameterId);
		b	.setOnClickListener(new ColorParameterChooserCustomIdListener(act));

		b	= (Button)act.findViewById(R.id.colorParameterChooseCustomOkButton);
		b	.setOnClickListener(new ColorParameterOkListener(act, pr));

		b	= (Button)act.findViewById(R.id.colorParameterChooserCustomSetChooserButton);
		b	.setOnClickListener(new ColorParameterColorChooserSetterListener(act, ColorParameterColorChooserSetterListener.CHOOSER_CUSTOM));

		sb	= (SeekBar)act.findViewById(R.id.colorChooserCustomSeekerTransparency);
		sb	.setOnSeekBarChangeListener(new ColorParameterCustomSeekListener(act));
		tv	= (TextView)act.findViewById(R.id.colorChooserCustomTransparency);
		tv	.setOnClickListener(new ColorParameterChooserCustomTextViewListener(act, Color.TRANSPARENT, sb));

		sb	= (SeekBar)act.findViewById(R.id.colorChooserCustomSeekerRed);
		sb	.setOnSeekBarChangeListener(new ColorParameterCustomSeekListener(act));
		tv	= (TextView)act.findViewById(R.id.colorChooserCustomRed);
		tv	.setOnClickListener(new ColorParameterChooserCustomTextViewListener(act, Color.RED, sb));

		sb	= (SeekBar)act.findViewById(R.id.colorChooserCustomSeekerGreen);
		sb	.setOnSeekBarChangeListener(new ColorParameterCustomSeekListener(act));
		tv	= (TextView)act.findViewById(R.id.colorChooserCustomGreen);
		tv	.setOnClickListener(new ColorParameterChooserCustomTextViewListener(act, Color.GREEN, sb));

		sb	= (SeekBar)act.findViewById(R.id.colorChooserCustomSeekerBlue);
		sb	.setOnSeekBarChangeListener(new ColorParameterCustomSeekListener(act));
		tv	= (TextView)act.findViewById(R.id.colorChooserCustomBlue);
		tv	.setOnClickListener(new ColorParameterChooserCustomTextViewListener(act, Color.BLUE, sb));
	}
	/**===============================================
	 * Initialise les interface et les listener
	 * de l'activite load
	 =================================================*/
	public static void initActivityLoad(LoadActivity act, int containerId)
	{
		GroundView		groundView		= (GroundView)act.findViewById(R.id.loadGroundView);
		Application		app				= LoadActivity.app;
		Printer			pr				= LoadActivity.pr;
		Button b;

		pr	.initExhibitionActivity(app, groundView, null);

		b = (Button) act.findViewById(R.id.loadSelectionButton);
		b .setOnClickListener(new LoadSelectionListener(act));

		b = (Button) act.findViewById(R.id.loadOkButton);
		b .setOnClickListener(new LoadOkListener(act, containerId));

		b = (Button) act.findViewById(R.id.loadDeleteButton);
		b .setOnClickListener(new DeleteGameListener(act));
	}
}