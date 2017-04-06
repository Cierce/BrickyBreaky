package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * This extends JPanel allows it to add component elements directly to this due to inheritance. 
 * <br>This provides the player with the Options Menu graphical user interface.
 * @author Connor Phillips
 * @version 1.0
 * @since 1.0
 */ 
public class OptionPanel extends JPanel
{	
	private static final long serialVersionUID = -2426397121070482202L; //long serialVersionUID stores the serialVersionUID generated value
	private JButton btnTheme1;     //JButton btnTheme1 we set and unset theme 1 option from this
	private JButton btnTheme2;     //JButton btnTheme2 we set and unset theme 2 option from this
	private JButton btnTheme3;     //JButton btnTheme3 we set and unset theme 3 option from this
	private JButton btnDifficulty; //JButton btnDifficulty we set and unset hardmode option from this
	private JButton btnBack;       //JButton btnBack we allow the user to go back to the MainMenu object from this
	private JButton btnInfo;       //JButton btnInfo we display a 'How to Use' message from this
	private JLabel lblThemes;      //JLabel lblThemes we display the 'Themes' title from this
	private JLabel lblDifficulty;  //JLabel lblDifficulty we display the 'Difficulty' title from this
	private Color bgColour;        //Color bgColour  we stores the background colour in this
	private MainMenu mainMenu;     //MainMenu mainMenu we declare an instance of MainMenu
	private int[] themeChoice;     //int[] themeChoice we store the players theme choice values in this

	/**
	 * This constructor is responsible for initalising our declared instance of MainMenu
	 * <br>as well as loading the themes, hardmode option and the buttons.
	 */
	public OptionPanel()
	{
		mainMenu = new MainMenu();
		loadThemes();
		loadHardmode();
		loadButtons();
	}

	/**
	 * This member function loads the themes, sets the background colour of all the components, it also loads the buttons,
	 * <br>ridged boxes and adds them to this.
	 */
	private void loadThemes()
	{
		bgColour = new Color(147, 198, 232);
		setBackground(bgColour);

		add(Box.createRigidArea(new Dimension(0, 10)));
		lblThemes = new JLabel();
		lblThemes.setFont(new Font("Helvetica", Font.BOLD, 14));
		lblThemes.setText("New Themes");
		lblThemes.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblThemes);

		btnTheme1 = new JButton("Theme 1 - Retro");
		btnTheme1.addActionListener(new SetTheme1Handler());
		btnTheme1.setBackground(bgColour);
		btnTheme1.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(btnTheme1);

		add(Box.createRigidArea(new Dimension(0, 10)));
		btnTheme2 = new JButton("Theme 2 - Mono");
		btnTheme2.addActionListener(new SetTheme2Handler());
		btnTheme2.setBackground(bgColour);
		btnTheme2.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(btnTheme2);

		add(Box.createRigidArea(new Dimension(0, 10)));  
		btnTheme3 = new JButton("Theme 3 - Pink");
		btnTheme3.addActionListener(new SetTheme3Handler());
		btnTheme3.setBackground(bgColour);
		btnTheme3.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(btnTheme3);
	}

	/**
	 * This member function loads the hardmode option and adds them to this.
	 */
	private void loadHardmode()
	{
		add(Box.createRigidArea(new Dimension(0, 10)));
		lblDifficulty = new JLabel();
		lblDifficulty.setFont(new Font("Helvetica", Font.BOLD, 14));
		lblDifficulty.setText("Difficulty");
		lblDifficulty.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblDifficulty);

		btnDifficulty = new JButton("Hard Mode");
		btnDifficulty.addActionListener(new HardDifficultyHandler());
		btnDifficulty.setBackground(bgColour);
		btnDifficulty.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(btnDifficulty);
	}

	/**
	 * This member function loads the info and back button and adds them to this.
	 */
	private void loadButtons()
	{
		add(Box.createRigidArea(new Dimension(0, 40)));
		btnInfo = new JButton("Info");
		btnInfo.addActionListener(new HowToPlayHandler());
		btnInfo.setBackground(bgColour);
		btnInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(btnInfo);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new BackHandler());
		btnBack.setBackground(bgColour);
		btnBack.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(btnBack);
	}
	
	/**
	 * This member function immedietly disposes of this.
	 */
	private void backToMenu()
	{
		SwingUtilities.getWindowAncestor(this).dispose();
	}

	/**
	 * This handler will change the text of the btnTheme1, as well as set and unset the theme choice to be the values of theme 1.
	 * @author Connor Phillips
	 * @version 1.0
	 * @since 1.0
	 */
	private class SetTheme1Handler implements ActionListener
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
					mainMenu.setThemeChoice(themeChoice, true);
				} 
				else if (btnTheme1.getText().equals("Theme 1 - Selected")) 
				{
					btnTheme1.setText("Theme 1 - Retro");
					mainMenu.setThemeChoice(themeChoice, false);
				}
			}
		}
	}

	/**
	 * This handler will change the text of the btnTheme2, as well as set and unset the theme choice to be the values of theme 2.
	 * @author Connor Phillips
	 * @version 1.0
	 * @since 1.0
	 */
	private class SetTheme2Handler implements ActionListener
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
					mainMenu.setThemeChoice(themeChoice, true);
				}
				else if (btnTheme2.getText().equals("Theme 2 - Selected"))
				{
					btnTheme2.setText("Theme 2 - Mono");
					mainMenu.setThemeChoice(themeChoice, false);
				}
			}
		}
	}

	/**
	 * This handler will change the text of the btnTheme3, as well as set and unset the theme choice to be the values of theme 3.
	 * @author Connor Phillips
	 * @version 1.0
	 * @since 1.0
	 */
	private class SetTheme3Handler implements ActionListener
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
					mainMenu.setThemeChoice(themeChoice, true);
				}
				else if (btnTheme3.getText().equals("Theme 3 - Selected"))
				{
					btnTheme3.setText("Theme 3 - Pink");
					mainMenu.setThemeChoice(themeChoice, false);
				}
			}
		}
	}
	
	/**
	 * This handler will set and unset the hard mode difficulty on GamePanel.
	 * @author Connor Phillips
	 * @version 1.0
	 * @since 1.0
	 */
	private class HardDifficultyHandler extends JPanel implements ActionListener
	{
		private static final long serialVersionUID = 2350525625329852354L;
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			Object source = e.getSource();
			if(source instanceof JButton) 
			{
				if(btnDifficulty.getText().equals("Hard Mode"))
				{
					mainMenu.setDifficultyType(true);
					JOptionPane.showMessageDialog(this, "<html><center>Hard mode is for experienced players only;"
							+ "<br>bricks broke give x2 the points!</center></html>", "CAUTION!", 2);
					btnDifficulty.setText("Hard Mode - Selected");
				} 
				else if(btnDifficulty.getText().equals("Hard Mode - Selected"))
				{
					mainMenu.setDifficultyType(false);
					btnDifficulty.setText("Hard Mode");
				}
			}
		}
	}

	/**
	 * This handler will show a new JDialog message with information about how to use the options menu.
	 * @author Connor Phillips
	 * @version 1.0
	 * @since 1.0
	 */
	private class HowToPlayHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(OptionPanel.this), "<html><center>Select a theme or difficulty by clicking on it"
					+ "<br>click on it again to deselect!</center></html>", "Info", 1);
		}
	}
	
	/**
	 * This handler will call OptionPanel member function 'backToMenu'.
	 * @author Connor Phillips
	 * @version 1.0
	 * @since 1.0
	 */
	private class BackHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			backToMenu();
		}
	}
}

