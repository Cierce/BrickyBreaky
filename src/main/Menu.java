package main;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Menu
{
	private JFrame frmWindow;
	private JDialog frmGame, frmOptions, frmLeaderboard;
	private JPanel pnlGame;
	private GamePanel brickyBreaky;
	private ScoreManagerPanel leaderboard;
	private OptionsPanel options;
	private JButton btnPlay, btnLeaderboard, btnHowToPlay, btnOptions, btnExit;
	private ImageIcon gameLogo;
	private Color bgColour;
	
	public static void main(String[] args) 
	{
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //documentation http://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
			new Menu();
		} 
		catch(ClassNotFoundException | InstantiationException | IllegalAccessException| UnsupportedLookAndFeelException e) 
		{
			System.out.println("Couldn't load your systems local look and feel, defaulting.");
			e.printStackTrace();
		}
	}

	Menu()
	{
		loadPanel();
		loadButtons();
		loadWindow();
	}

	private void loadPanel() 
	{
		gameLogo = new ImageIcon("Resources\\Images\\logo.png");
		bgColour = new Color(147, 198, 232);
		pnlGame = new JPanel();
		pnlGame.setLayout(new BoxLayout(pnlGame, BoxLayout.Y_AXIS));
		pnlGame.setBackground(bgColour);
	}

	private void loadWindow()
	{
		frmWindow = new JFrame("Bricky Breaky v1.0.7"); 
		frmWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWindow.setSize(350, 250);
		frmWindow.setResizable(false);
		frmWindow.setLocationRelativeTo(null);
		frmWindow.setIconImage(gameLogo.getImage());
		frmWindow.add(pnlGame);
		frmWindow.setVisible(true);
	}

	private void loadButtons()
	{
		pnlGame.add(Box.createRigidArea(new Dimension(0, 30))); //adds a hidden 'box' that creates spacing between components 

		btnPlay = new JButton("Play");
		btnPlay.setBounds(0, 200, 75, 25);
		btnPlay.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnPlay.addActionListener(new runGameHandler());
		btnPlay.setBackground(bgColour);
		pnlGame.add(btnPlay);
		pnlGame.add(Box.createRigidArea(new Dimension(0, 10)));  

		btnLeaderboard = new JButton("Leaderboard");
		btnLeaderboard.setBounds(0, 200, 75, 25);
		btnLeaderboard.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnLeaderboard.addActionListener(new runLeaderboardHandler());
		btnLeaderboard.setBackground(bgColour);
		pnlGame.add(btnLeaderboard);

		pnlGame.add(Box.createRigidArea(new Dimension(0, 10)));

		btnHowToPlay = new JButton("How to Play");
		btnHowToPlay.setBounds(100, 200, 100, 25);
		btnHowToPlay.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnHowToPlay.addActionListener(new guideHandler());
		btnHowToPlay.setBackground(bgColour);
		pnlGame.add(btnHowToPlay);

		pnlGame.add(Box.createRigidArea(new Dimension(0, 10))); 

		btnOptions = new JButton("Options");
		btnOptions.setBounds(225, 200, 75, 25);
		btnOptions.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnOptions.addActionListener(new optionHandler());
		btnOptions.setBackground(bgColour);
		pnlGame.add(btnOptions);

		pnlGame.add(Box.createRigidArea(new Dimension(0, 10))); 

		btnExit = new JButton("Exit");
		btnExit.setBounds(225, 200, 75, 25);
		btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnExit.addActionListener(new exitHandler());
		btnExit.setBackground(bgColour);
		pnlGame.add(btnExit);
	}

	public void loadGame()
	{
		frmGame = new JDialog(frmGame, "Bricky Breaky v1.0.7");
		brickyBreaky = new GamePanel();
		frmGame.add(brickyBreaky);
		frmGame.setModal(true);
		frmGame.setIconImage(gameLogo.getImage());
		frmGame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmGame.setSize(607, 600);
		frmGame.setLocationRelativeTo(null);
		frmGame.setVisible(false);
		frmGame.setResizable(false);
	}

	private void loadLeaderboard()
	{
		frmLeaderboard = new JDialog(frmGame, "Leaderboard");
		leaderboard = new ScoreManagerPanel();
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
		frmOptions = new JDialog(frmOptions, "Options Menu");
		options = new OptionsPanel();
		frmOptions.add(options);
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
					frmWindow, 
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