package main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class MainMenu
{
	private JFrame frmMenu;
	private JDialog frmGame, frmOptions, frmLeaderboard;
	private JPanel pnlMenu;
	private GamePanel brickyBreaky;
	private ScoreManagerPanel leaderboard;
	private OptionPanel optionMenu;
	private JButton btnPlay, btnLeaderboard, btnHowToPlay, btnOptions, btnExit;
	private ImageIcon gameLogo;
	private Color bgColour;
	private int[] themeChoice;
	private boolean isSetTheme, isSetHardMode;
	
	public static void main(String[] args) 
	{
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //documentation http://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
			new MainMenu();
		} 
		catch(ClassNotFoundException | InstantiationException | IllegalAccessException| UnsupportedLookAndFeelException e) 
		{
			System.out.println("Couldn't load your systems local look and feel, defaulting.");
			e.printStackTrace();
		}
	}

	MainMenu()
	{
		gameLogo         = new ImageIcon("Resources\\Images\\logo.png");
		bgColour         = new Color(147, 198, 232);
        isSetTheme       = false; //by default we assume that the player has not set a theme in the options menu
        isSetHardMode    = false; //same for the games difficulty

		loadPanel();
		loadButtons();
		loadWindow();
	}

	private void loadPanel() 
	{
		pnlMenu = new JPanel();
		pnlMenu.setLayout(new BoxLayout(pnlMenu, Y_AXIS));
		pnlMenu.setBackground(bgColour);
	}

	private void loadWindow()
	{
		frmMenu = new JFrame("Bricky Breaky v1.0.7");
		frmMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMenu.setSize(350, 250);
		frmMenu.setResizable(false);
		frmMenu.setLocationRelativeTo(null);
		frmMenu.setIconImage(gameLogo.getImage());
		frmMenu.add(pnlMenu);
		frmMenu.setVisible(true);
	}

	private void loadButtons()
	{
		pnlMenu.add(Box.createRigidArea(new Dimension(0, 30))); //adds a hidden 'box' that creates spacing between components
		btnPlay = new JButton("Play");
		btnPlay.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnPlay.addActionListener(new runGameHandler());
		btnPlay.setBackground(bgColour);
		pnlMenu.add(btnPlay);

		pnlMenu.add(Box.createRigidArea(new Dimension(0, 10)));
		btnLeaderboard = new JButton("Leaderboard");
		btnLeaderboard.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnLeaderboard.addActionListener(new runLeaderboardHandler());
		btnLeaderboard.setBackground(bgColour);
		pnlMenu.add(btnLeaderboard);

		pnlMenu.add(Box.createRigidArea(new Dimension(0, 10)));
		btnHowToPlay = new JButton("How to Play");
		btnHowToPlay.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnHowToPlay.addActionListener(new guideHandler());
		btnHowToPlay.setBackground(bgColour);
		pnlMenu.add(btnHowToPlay);

		pnlMenu.add(Box.createRigidArea(new Dimension(0, 10)));
		btnOptions = new JButton("Options");
		btnOptions.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnOptions.addActionListener(new optionHandler());
		btnOptions.setBackground(bgColour);
		pnlMenu.add(btnOptions);

		pnlMenu.add(Box.createRigidArea(new Dimension(0, 10)));
		btnExit = new JButton("Exit");
		btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnExit.addActionListener(new exitHandler());
		btnExit.setBackground(bgColour);
		pnlMenu.add(btnExit);
	}

	public void setThemeChoice(int[] themeChoice, boolean isSetTheme)
	{
		this.themeChoice = themeChoice;
        this.isSetTheme  = isSetTheme;
	}

	public void setDifficultyType(boolean isSetHardMode)
    {
        this.isSetHardMode = isSetHardMode;
    }

	public void loadGame()
	{
		frmGame = new JDialog(frmGame, "Bricky Breaky v1.0.8");
		brickyBreaky = new GamePanel();
		frmGame.add(brickyBreaky);
		frmGame.setModal(true);
		frmGame.setIconImage(gameLogo.getImage());
		frmGame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmGame.setSize(607, 600);
		frmGame.setLocationRelativeTo(null);
		frmGame.setVisible(false);
		frmGame.setResizable(false);
		brickyBreaky.setThemeChoice(themeChoice, isSetTheme);
		brickyBreaky.setDifficulty(isSetHardMode);
	}

	private void loadLeaderboard()
	{
		frmLeaderboard = new JDialog(frmGame, "Leaderboard");
		leaderboard = new ScoreManagerPanel();
		leaderboard.setLayout(new BoxLayout(leaderboard, Y_AXIS));
		frmLeaderboard.add(leaderboard);
		frmLeaderboard.setModal(true);
		frmLeaderboard.setIconImage(gameLogo.getImage());
		frmLeaderboard.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmLeaderboard.setSize(300, 300);
		frmLeaderboard.setLocationRelativeTo(null);
		frmLeaderboard.setVisible(false);
		frmLeaderboard.setResizable(false);
		frmLeaderboard.setBackground(bgColour);
	}

	private void loadOptions()
	{
		frmOptions = new JDialog(frmOptions, "Options MainMenu");
		optionMenu = new OptionPanel();
		optionMenu.setLayout(new BoxLayout(optionMenu, Y_AXIS));
		frmOptions.add(optionMenu);
		frmOptions.setModal(true);
		frmOptions.setIconImage(gameLogo.getImage());
		frmOptions.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmOptions.setSize(185, 300);
		frmOptions.setLocationRelativeTo(null);
		frmOptions.setVisible(false);
		frmOptions.setResizable(false);
		frmOptions.setBackground(bgColour);
	}

	class runGameHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			loadGame();
			frmGame.setVisible(true);
		}
	}

	class runLeaderboardHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			loadLeaderboard();
			frmLeaderboard.setVisible(true);
		}
	}

	class guideHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			JOptionPane.showMessageDialog(
					frmMenu,
					"<html><center>Welcome to Bricky Breaky<br>Move with the A & D or the Left & Right Arrow keys<br>Press the 'Enter' key to start<br>Good luck!</html><center>", 
					"Instructions", -1);
		}
	}

	class optionHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			loadOptions();
			frmOptions.setVisible(true);
		}
	}

	class exitHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			System.exit(0);
		}
	}
}