package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class OptionsPanel extends JPanel
{	
	private static final long serialVersionUID = -2426397121070482202L;

	private JButton btnTheme1, btnTheme2, btnTheme3, btnDifficulty;
	private JLabel lblThemes, lblDifficulty;
	protected static boolean choseTheme;
	protected static int[] themeChoice;
	private static boolean setDifficultyHard;
	private Color bgColour;

	public OptionsPanel()
	{
		loadLabels();
		loadButtons();
	}
	private void loadLabels()
	{
		bgColour = new Color(147, 198, 232);
		lblThemes = new JLabel();
		lblThemes.setFont(new Font("Helvetica", Font.BOLD, 14));
		
		lblDifficulty = new JLabel();
		lblDifficulty.setFont(new Font("Helvetica", Font.BOLD, 14));
		setLayout(null);
		setBackground(bgColour);
		add(Box.createRigidArea(new Dimension(0, 30))); //adds a hidden 'box' that creates spacing between components

		lblThemes.setText("New Themes");
		lblThemes.setBounds(50, 5, 125, 25);
		add(lblThemes);

		lblDifficulty.setText("Difficulty");
		lblDifficulty.setBounds(60, 125, 125, 25);
		add(lblDifficulty);
	}
	
	private void loadButtons()
	{
		choseTheme = false;
		themeChoice = new int[3];
		setDifficultyHard = false;

		add(Box.createRigidArea(new Dimension(0, 10))); 

		btnTheme1 = new JButton("Theme 1 - Retro");
		btnTheme1.setBounds(30, 30, 125, 25);
		btnTheme1.addActionListener(new SetTheme1());
		btnTheme1.setBackground(bgColour);
		add(btnTheme1);

		add(Box.createRigidArea(new Dimension(0, 10)));  

		btnTheme2 = new JButton("Theme 2 - Mono");
		btnTheme2.setBounds(30, 60, 125, 25);
		btnTheme2.addActionListener(new SetTheme2());
		btnTheme2.setBackground(bgColour);
		add(btnTheme2);

		add(Box.createRigidArea(new Dimension(0, 10)));  
		btnTheme3 = new JButton("Theme 3 - Pink");
		btnTheme3.setBounds(30, 90, 125, 25);
		btnTheme3.addActionListener(new SetTheme3());
		btnTheme3.setBackground(bgColour);
		add(btnTheme3);

		btnDifficulty = new JButton("Hard Mode");
		btnDifficulty.setBounds(22, 150, 145, 25);
		btnDifficulty.addActionListener(new HardDifficulty());
		btnDifficulty.setBackground(bgColour);
		add(btnDifficulty);
	}

	public static boolean isSetDifficultyHard() {
		return setDifficultyHard;
	}

	public static void setSetDifficultyHard(boolean setDifficultyHard) {
		OptionsPanel.setDifficultyHard = setDifficultyHard;
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
					choseTheme = true;
					btnTheme1.setText("Theme 1 - Selected");
					themeChoice[0] = 20;
					themeChoice[1] = 30;
					themeChoice[2] = 40;
				} 
				else if (btnTheme1.getText().equals("Theme 1 - Selected")) 
				{
					btnTheme1.setText("Theme 1 - Retro");
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
					choseTheme = true;
					btnTheme2.setText("Theme 2 - Selected");
					themeChoice[0] = 2;
					themeChoice[1] = 3;
					themeChoice[2] = 4;
				} 
				else if (btnTheme2.getText().equals("Theme 2 - Selected")) 
				{
					btnTheme2.setText("Theme 2 - Mono");
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
					choseTheme = true;
					btnTheme3.setText("Theme 3 - Selected");
					themeChoice[0] = 12;
					themeChoice[1] = 53; 
					themeChoice[2] = 43;
				} 
				else if (btnTheme3.getText().equals("Theme 3 - Selected")) 
				{
					btnTheme3.setText("Theme 3 - Pink");
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

