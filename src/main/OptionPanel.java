package main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionPanel extends JPanel
{	
	private static final long serialVersionUID = -2426397121070482202L;

	private JButton btnTheme1, btnTheme2, btnTheme3, btnDifficulty, btnBack;
	private JLabel lblThemes, lblDifficulty;
	private boolean choseTheme;
	private int[] themeChoice;
	private static boolean setDifficultyHard;
	private Color bgColour;
	private GamePanel gamePanel = new GamePanel();

	public OptionPanel()
	{
		loadThemes();
		loadDifficulties();
		loadBackButton();
	}
	
	private void loadThemes()
	{
		choseTheme = false;
		setDifficultyHard = false;

		bgColour = new Color(147, 198, 232);
		setBackground(bgColour);
		add(Box.createRigidArea(new Dimension(0, 10)));
		lblThemes = new JLabel();
		lblThemes.setFont(new Font("Helvetica", Font.BOLD, 14));
		lblThemes.setText("New Themes");
		lblThemes.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblThemes);

		btnTheme1 = new JButton("Theme 1 - Retro");
		btnTheme1.addActionListener(new SetTheme1());
		btnTheme1.setBackground(bgColour);
		btnTheme1.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(btnTheme1);

		add(Box.createRigidArea(new Dimension(0, 10)));
		btnTheme2 = new JButton("Theme 2 - Mono");
		btnTheme2.addActionListener(new SetTheme2());
		btnTheme2.setBackground(bgColour);
		btnTheme2.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(btnTheme2);

		add(Box.createRigidArea(new Dimension(0, 10)));  
		btnTheme3 = new JButton("Theme 3 - Pink");
		btnTheme3.addActionListener(new SetTheme3());
		btnTheme3.setBackground(bgColour);
		btnTheme3.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(btnTheme3);
	}

	public void loadDifficulties()
	{
		add(Box.createRigidArea(new Dimension(0, 10)));
		lblDifficulty = new JLabel();
		lblDifficulty.setFont(new Font("Helvetica", Font.BOLD, 14));
		lblDifficulty.setText("Difficulty");
		lblDifficulty.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblDifficulty);

		btnDifficulty = new JButton("Hard Mode");
		btnDifficulty.addActionListener(new HardDifficulty());
		btnDifficulty.setBackground(bgColour);
		btnDifficulty.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(btnDifficulty);
	}

	public void loadBackButton()
	{
		add(Box.createRigidArea(new Dimension(0, 70)));
		btnBack = new JButton("Back");
		btnBack.addActionListener(new Back());
		btnBack.setBackground(bgColour);
		btnBack.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(btnBack);
	}

	public void backToMenu()
	{
		SwingUtilities.getWindowAncestor(this).dispose();
	}

	public static boolean isSetDifficultyHard() {
		return setDifficultyHard;
	}

	public static void setSetDifficultyHard(boolean setDifficultyHard) {
		OptionPanel.setDifficultyHard = setDifficultyHard;
	}

	public void setThemeChoice(int[] themeChoice, boolean choseTheme)
	{
		gamePanel = new GamePanel();
		gamePanel.setThemeChoice(themeChoice, choseTheme);
	}

	public void themeNotSet()
	{
		gamePanel.setNoTheme();
	}

	class Back implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			backToMenu();
		}
	}

	class SetTheme1 implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			Object source = e.getSource();
			if(source instanceof JButton) 
			{
				if(btnTheme1.getText().equals("Theme 1 - Retro")  && btnTheme2.getText().equals("Theme 2 - Mono") && btnTheme3.getText().equals("Theme 3 - Pink"))
				{
					btnTheme1.setText("Theme 1 - Selected");
					themeChoice = new int[3];
					themeChoice[0] = 20;
					themeChoice[1] = 30;
					themeChoice[2] = 40;
					setThemeChoice(themeChoice, true);
				} 
				else if (btnTheme1.getText().equals("Theme 1 - Selected")) 
				{
					btnTheme1.setText("Theme 1 - Retro");
					themeNotSet();
				}
			}
		}
	}

	class SetTheme2 implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			Object source = e.getSource();
			if(source instanceof JButton)
			{
				if(btnTheme2.getText().equals("Theme 2 - Mono") && btnTheme1.getText().equals("Theme 1 - Retro") && btnTheme3.getText().equals("Theme 3 - Pink"))
				{
					btnTheme2.setText("Theme 2 - Selected");
					themeChoice = new int[3];
					themeChoice[0] = 2;
					themeChoice[1] = 3;
					themeChoice[2] = 4;
					setThemeChoice(themeChoice, true);
				}
				else if (btnTheme2.getText().equals("Theme 2 - Selected"))
				{
					btnTheme2.setText("Theme 2 - Mono");
					themeNotSet();
				}
			}
		}
	}

	class SetTheme3 implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			Object source = e.getSource();
			if(source instanceof JButton) 
			{
				if(btnTheme3.getText().equals("Theme 3 - Pink")  && btnTheme1.getText().equals("Theme 1 - Retro") && btnTheme2.getText().equals("Theme 2 - Mono"))
				{
					btnTheme3.setText("Theme 3 - Selected");
					themeChoice = new int[3];
					themeChoice[0] = 12;
					themeChoice[1] = 53;
					themeChoice[2] = 43;
					setThemeChoice(themeChoice, true);
				}
				else if (btnTheme3.getText().equals("Theme 3 - Selected"))
				{
					btnTheme3.setText("Theme 3 - Pink");
					themeNotSet();
				}
			}
		}
	}
	class HardDifficulty extends JPanel implements ActionListener
	{
		private static final long serialVersionUID = -8167521891084454059L;

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			Object source = e.getSource();
			if(source instanceof JButton) 
			{
				if(btnDifficulty.getText().equals("Hard Mode"))
				{
					setSetDifficultyHard(true);
					JOptionPane.showMessageDialog(this, "<html><center>Hard mode is for experienced players only;<br>bricks broke give x2 the points!</center></html>", "CAUTION!", 2);
					btnDifficulty.setText("Hard Mode - Selected");
				} 
				else if (btnDifficulty.getText().equals("Hard Mode - Selected")) 
				{
					setSetDifficultyHard(false);
					btnDifficulty.setText("Hard Mode");
				}
			}
		}
	}
}

