package main;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This extends JPanel allows it to add component elements directly to this due to inheritance. 
 * <br>This provides the player with the Leaderboard graphical user interface.
 * @author Connor Phillips
 * @version 1.0
 * @since 1.0
 */
public class ScoreManagerPanel extends JPanel
{
	private static final long serialVersionUID = 5406943106354812340L; //long serialVersionUID stores the serialVersionUID generated value
	private File ldrBrdDataStore;             //File ldrBrdDataStore stores the name of the leaderboard txt file
	private FileReader readLdrBrdData;        //FileReader readLdrBrdData reads in the var ldrBrdDataStore 
	private BufferedReader ldrBrdBuffer;      //BufferedReader ldrBrdBuffer buffers the ldrBrdDataStore var
	private FileWriter writeToFile;           //FileWriter writeToFile allows us to write to our leaderboard txt file
	private JTable tblLeaderboard;            //JTable tblLeaderboard declaes a new instance of JTable that we use as the leaderboard GUI
	private JScrollPane scrollPane;           //JScrollPane scrollPane this is the scrollPane we use with the JTable when we get x amount of players loaded
	private DefaultTableModel tblLdrBrdModel; //DefaultTableModel tblLdrBrdModel is the model for our JTable  
	private JButton btnBack;                  //JButton btnBack allows the player to go back to the MainMenu
	private Color bgColour;                   //Color bgColour stores the background colour
	private String currentLine;               //String currentLine stores the current line read in from the txt file 
	private String[] currentScore;            //String[] currentScore stores the current score from var 'scores'
	private String[] tblHeaders;              //String[] tblHeaders stores the headers for our JTable
	private String[][] tblData;               //String[][] tblData stores all of our player's username, score and if they played in hardmode to be used with the JTable
	private ArrayList<String[]> playerData;   //ArrayList<String[]> playerData stores all of our player's data
	private int maxRowDisplay;                //int maxRowDisplay stores the maximum amount of rows we will display

	/**
	 * This constructor will call the member functions that load the leaderboard file, load the player data,
	 * <br>sort the players data and then write it back to the leaderboard file.
	 */
	ScoreManagerPanel()
	{
		loadLeaderboardFile();
		loadPlayerData();
		sortPlayerData();
		writeSortedPlayerData();
	}

	/**
	 * This member function displays the JTable with all of the respective player data [name][score][hardmode] 
	 * <br>and it displays the back button.
	 */
	public void displayLeaderboard()
	{
		maxRowDisplay = playerData.size();
		tblHeaders = new String[]{"RANK", "USERNAME", "SCORE", "HARD MODE"};
		tblData = new String[maxRowDisplay][tblHeaders.length];

		for(int row = 0; row < maxRowDisplay; row++)
		{
			for(int column = 0; column < tblHeaders.length; column++)
			{
				if(column == 0)
				{
					tblData[row][column] = Integer.toString((row + 1));
				}
				else
				{
					tblData[row][column] = playerData.get(row)[column - 1];
				}
			}
		}
		
		bgColour = new Color(147, 198, 232);

		tblLdrBrdModel = new DefaultTableModel(tblData, tblHeaders);
		tblLeaderboard = new JTable(tblLdrBrdModel);
		tblLeaderboard.setEnabled(false);
		tblLeaderboard.getTableHeader().setReorderingAllowed(false);
		tblLeaderboard.getTableHeader().setResizingAllowed(false);
		scrollPane = new JScrollPane(tblLeaderboard);
		tblLeaderboard.setBackground(new Color(155, 209, 245));
		this.add(scrollPane);

		btnBack = new JButton("Back");
		btnBack.addActionListener(new BackToMenuHandler());
		btnBack.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnBack.setBackground(bgColour);
		this.add(btnBack);
		this.setBackground(bgColour);
	}
	
	/**
	 * This member function loads the leaderboard data store and buffers the file for use.
	 */
	private void loadLeaderboardFile()
	{
		ldrBrdDataStore = new File("ldrBrdDataStore.txt");
		try
		{
			readLdrBrdData = new FileReader(ldrBrdDataStore);
			ldrBrdBuffer   = new BufferedReader(readLdrBrdData);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * This member function loads the player data from the leaderboard data store.
	 */
	private void loadPlayerData()
	{
		try
		{
			playerData = new ArrayList<>();
			while((currentLine = ldrBrdBuffer.readLine()) != null)
			{
				if(!(currentLine.equals("")))
				{
					String[] lineContents = currentLine.split("\\s+");
					playerData.add(lineContents);
				}
			}
			readLdrBrdData.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * This member function sorts the player's score from highest to lowest.
	 */
	private void sortPlayerData()
	{
		ArrayList<String[]> sortedPlayerData = playerData;
		if (sortedPlayerData.size() > 1)
		{
			for (int x = 0; x < sortedPlayerData.size(); x++)
			{
				for (int i = 0; i < sortedPlayerData.size() - x - 1; i++)
				{
					// _sortedPlayerData[i][name][score][hardmode]
					if (Integer.parseInt(sortedPlayerData.get(i)[1]) < Integer.parseInt(sortedPlayerData.get(i + 1)[1]))
					{
						String[] tmp = sortedPlayerData.get(i+1);
						sortedPlayerData.set(i+1, sortedPlayerData.get(i));
						sortedPlayerData.set(i, tmp);
					}
				}
			}
		}
		playerData = sortedPlayerData;
	}

	/**
	 * This member function writes the sorted player data to the leaderboard data store.
	 */
	private void writeSortedPlayerData()
	{
		try
		{
			writeToFile = new FileWriter(ldrBrdDataStore);

			for(int i = 0; i < playerData.size(); i++)
			{
				currentScore = playerData.get(i);
				writeToFile.write("\n" + currentScore[0] + " " + Integer.parseInt(currentScore[1]) + " " + currentScore[2]);
			}
			writeToFile.flush();
			writeToFile.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * This member function writes new player data to the leaderboard data store.
	 * @param username String Expects the username of the player
	 * @param gameScore int Expects the score of the player
	 * @param isSetHardmode String Expects if they played in hardmode or not
	 */
	public void writeNewPlayer(String username, int gameScore, String isSetHardmode)
	{
		try
		{
			writeToFile = new FileWriter(ldrBrdDataStore, true);
			writeToFile.write("\n" + username + " " + Integer.toString(gameScore) + " " + isSetHardmode);
			writeToFile.flush();
			writeToFile.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * This member function closes this object.
	 */
	private void backToMenu()
	{
		SwingUtilities.getWindowAncestor(this).dispose();
	}

	/**
	 * This handler will call ScoreManagerPanel member function 'backToMenu'.
	 * @author Connor Phillips
	 * @version 1.0
	 * @since 1.0
	 */
	private class BackToMenuHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent accEvent)
		{
			backToMenu();
		}
	}
}
