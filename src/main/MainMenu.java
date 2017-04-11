package main;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * This provides the player with the Main Menu graphical user interface and is responsible for providing the player
 * <br>with access to the game, leaderboard, how to play and options GUI's.
 * @author Connor Phillips
 * @version 1.0
 * @since 1.0
 */
public class MainMenu
{
	private JFrame frmMenu;                //JFrame frmMenu provides us with a JFrame to use for the Main Menu
	private JPanel pnlMenu;                //JPanel pnlMenu provides us with a JPanel to use with the Main Menu JFrame
	private JDialog dlgGame;               //JDialog dlgGame declares an instance of the object JDialog
	private JDialog dlgOptions;            //JDialog dlgOptions declares an instance of the object JDialog
	private JDialog dlgLeaderboard;        //JDialog dlgLeaderboard declares an instance of the object JDialog
	private GamePanel brickyBreaky;        //GamePanel brickyBreaky provides us with the core game to load and display
	private ScoreManagerPanel leaderboard; //ScoreManagerPanel provides us with the Leaderboard to load and display
	private OptionPanel optionMenu;        //OptionPanel optionMenu provides us with the Options Menu to load and display
	private ImageIcon gameLogo;            //ImageIcon gameLogo stores an image of the Bricky Breaky game logo
	private Color bgColour;                //Color bgColour stores the colour of the background
	private JButton btnPlay;               //JButton btnPlay we launch the game from this
	private JButton btnLeaderboard;        //JButton btnLeaderboard we launch the Leaderboard from this
	private JButton btnHowToPlay;          //JButton btnHowToPlay we launch the How to Play dialog from this
	private JButton btnOptions;            //JButton btnOptions we launch the Options Menu from this
	private JButton btnExit;               //JButton btnExit we exit the Main Menu from this
	private int[] themeChoice;             //int[] themeChoice stores the player theme choice if they choose one
	private boolean isSetTheme;            //boolean isSetTheme stores if the player has chosen a theme
	private boolean isSetHardMode;         //boolean isSetHardMode stores if the player has turned hardmode on

	/**
	 * This member function is the starting point of the game, as it loads the Main Menu
	 * <br>which provides us with the ability to launch the game etc.
	 * <br>We also set the look and feel throughout the GUI from this member function.
	 * @param args String[] Required paramater to use the main() member function
	 */
	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		} //documentation http://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		new MainMenu();
	}

	/**
	 * This constructor initalise's our necessary default values and loads our Main Menu panel, buttons and frame.
	 */
	MainMenu()
	{
		initaliseValues();
		loadPanel();
		loadButtons();
		loadFrame();
	}

	/**
	 * This member function initalise's our Main Menu JFrame.
	 * <br>It also adds the Main Menu JPanel to it, then displays the JFrame with the attached JPanel.
	 */
	private void loadFrame()
	{
		frmMenu = new JFrame("Bricky Breaky v1.0");
		frmMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMenu.setSize(350, 250);
		frmMenu.setResizable(false);
		frmMenu.setLocationRelativeTo(null);
		frmMenu.setIconImage(gameLogo.getImage());
		frmMenu.add(pnlMenu);
		frmMenu.setVisible(true);
	}

	/**
	 * This member function initalise's our Main Menu JButtons and displays them.
	 */
	private void loadButtons()
	{
		pnlMenu.add(Box.createRigidArea(new Dimension(0, 30))); //adds a hidden 'box' that creates spacing between components
		btnPlay = new JButton("Play");
		btnPlay.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnPlay.addActionListener(new GameHandler());
		btnPlay.setBackground(bgColour);
		pnlMenu.add(btnPlay);

		pnlMenu.add(Box.createRigidArea(new Dimension(0, 10)));
		btnLeaderboard = new JButton("Leaderboard");
		btnLeaderboard.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnLeaderboard.addActionListener(new LeaderboardHandler());
		btnLeaderboard.setBackground(bgColour);
		pnlMenu.add(btnLeaderboard);

		pnlMenu.add(Box.createRigidArea(new Dimension(0, 10)));
		btnHowToPlay = new JButton("How to Play");
		btnHowToPlay.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnHowToPlay.addActionListener(new GuideHandler());
		btnHowToPlay.setBackground(bgColour);
		pnlMenu.add(btnHowToPlay);

		pnlMenu.add(Box.createRigidArea(new Dimension(0, 10)));
		btnOptions = new JButton("Options");
		btnOptions.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnOptions.addActionListener(new OptionHandler());
		btnOptions.setBackground(bgColour);
		pnlMenu.add(btnOptions);

		pnlMenu.add(Box.createRigidArea(new Dimension(0, 10)));
		btnExit = new JButton("Exit");
		btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnExit.addActionListener(new ExitHandler());
		btnExit.setBackground(bgColour);
		pnlMenu.add(btnExit);
	}

	/**
	 * This member function initalise's our Main Menu JPanel to be displayed by our Main Menu JFrame.
	 */
	private void loadPanel()
	{
		pnlMenu = new JPanel();
		pnlMenu.setLayout(new BoxLayout(pnlMenu, javax.swing.BoxLayout.Y_AXIS));
		pnlMenu.setBackground(bgColour);
	}

	/**
	 * This member function initalise's values that are used throughout the Main Menu
	 * <br>and sets the default state of isSetTheme and isSetHardMode.
	 */
	private void initaliseValues()
	{
		gameLogo      = new ImageIcon("Resources\\Images\\logo.png");
		bgColour      = new Color(147, 198, 232);
		isSetTheme    = false; //by default we assume that the player has not set a theme in the options menu
		isSetHardMode = false; //same for the games difficulty
	}

	/**
	 * This member function sets the themeChoice values chosen on the OptionPanel and sets the state of isSetTheme.
	 * @param themeChoice int[] Stores the players theme choice values
	 * @param isSetTheme boolean Expects true or false
	 */
	public void setThemeChoice(int[] themeChoice, boolean isSetTheme)
	{
		this.themeChoice = themeChoice;
		this.isSetTheme  = isSetTheme;
	}

	/**
	 * This member function sets the difficulty type chosen on the OptionPanel.
	 * @param isSetHardMode boolean Expects true or false
	 */
	public void setDifficultyType(boolean isSetHardMode)
	{
		this.isSetHardMode = isSetHardMode;
	}

	/**
	 * This member function loads and displays the OptionPanel (Options Menu).
	 */
	private void loadOptions()
	{
		dlgOptions = new JDialog(dlgOptions, "Options MainMenu");
		optionMenu = new OptionPanel();
		optionMenu.setLayout(new BoxLayout(optionMenu, javax.swing.BoxLayout.Y_AXIS));
		dlgOptions.add(optionMenu);
		dlgOptions.setModal(true);
		dlgOptions.setIconImage(gameLogo.getImage());
		dlgOptions.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dlgOptions.setSize(185, 300);
		dlgOptions.setLocationRelativeTo(null);
		dlgOptions.setVisible(false);
		dlgOptions.setResizable(false);
		dlgOptions.setBackground(bgColour);
		dlgOptions.setVisible(true);
	}

	/**
	 * This member function will load the ScoreManagerPanel and display it,
	 * <br>however to display the Leaderboard it must be called through the instance of the ScoreManagerPanel object,
	 * <br>this is because ScoreManagerPanel is used in GamePanel without displaying it (simply to write the players score to the file).
	 * <br>This saves memory by not displaying the Leaderboard in the GamePanel because we don't use the GUI.
	 */
	private void loadLeaderboard()
	{
		dlgLeaderboard = new JDialog(dlgLeaderboard, "Leaderboard");
		leaderboard = new ScoreManagerPanel();
		leaderboard.displayLeaderboard();
		leaderboard.setLayout(new BoxLayout(leaderboard, javax.swing.BoxLayout.Y_AXIS));
		dlgLeaderboard.add(leaderboard);
		dlgLeaderboard.setModal(true);
		dlgLeaderboard.setIconImage(gameLogo.getImage());
		dlgLeaderboard.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dlgLeaderboard.setSize(320, 300);
		dlgLeaderboard.setLocationRelativeTo(null);
		dlgLeaderboard.setVisible(false);
		dlgLeaderboard.setResizable(false);
		dlgLeaderboard.setBackground(bgColour);
		dlgLeaderboard.setVisible(true);
	}

	/**
	 * This member function will load the GamePanel and display it.
	 */
	private void loadGame()
	{
		dlgGame = new JDialog(dlgGame, "Bricky Breaky v1.0");
		brickyBreaky = new GamePanel();
		dlgGame.add(brickyBreaky);
		dlgGame.setModal(true);
		dlgGame.setIconImage(gameLogo.getImage());
		dlgGame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dlgGame.setSize(607, 600);
		dlgGame.setLocationRelativeTo(null);
		dlgGame.setVisible(false);
		dlgGame.setResizable(false);
		brickyBreaky.setThemeChoice(themeChoice, isSetTheme);
		brickyBreaky.setDifficulty(isSetHardMode);
		dlgGame.setVisible(true);
	}

	/**
	 * This handler will call the loadGame() method in the MainMenu object when added to a Component as a new actionListener.
	 * @author Connor Phillips
	 * @version 1.0
	 * @since 1.0
	 */
	private class GameHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			loadGame();
		}
	}

	/**
	 * This handler will call the loadLeaderboard() method in the MainMenu object when added to a Component as a new actionListener.
	 * @author Connor Phillips
	 * @version 1.0
	 * @since 1.0
	 */
	private class LeaderboardHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			loadLeaderboard();
		}
	}

	/**
	 * This handler will construct a new JOptionPane and call JOptionPane member function showMessageDialog(Component, String, int)
	 * <br>and display a 'How to Play' message.
	 * @author Connor Phillips
	 * @version 1.0
	 * @since 1.0
	 */
	private class GuideHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			JOptionPane.showMessageDialog(
					frmMenu,
					"<html><center>Welcome to Bricky Breaky<br>Move with the A & D or the Left & Right Arrow keys"
					+ "<br>Press the ENTER key to start<br>You can escape the game by pressing the ESCAPE key at any time"
					+ "<br>You cannot pause the game, it's intense on purpose!"
					+ "<br>Good luck!</center></html>",
					"Instructions", -1);
		}
	}

	/**
	 * This handler will dispose of the frmMenu in the MainMenu object.
	 * <br>It will call the loadOptions() method in the MainMenu object when added to a Component as a new actionListener.
	 * @author Connor Phillips
	 * @version 1.0
	 * @since 1.0
	 */
	private class OptionHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			frmMenu.dispose();
			loadOptions();
		}
	}

	/**
	 * This handler will exit any application when added as a new actionListener.
	 * @author Connor Phillips
	 * @version 1.0
	 * @since 1.0
	 */
	private class ExitHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			System.exit(0);
		}
	}
}