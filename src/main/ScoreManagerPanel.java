package main;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ScoreManagerPanel extends JPanel
{
	private static final long serialVersionUID = 5406943106354812340L;
	private JTextArea txtUsername, txtUserScore, txtUserRank;
	private JLabel lblTitle, lblColumnDesc;
	private String[] getScore;
	private String usrName, usrScore, strUsrRank;
	private Color bgColour;
	private int[] intUsrRank;
	private int maxResultsShown;
	private File leaderboard;
	private FileReader readLeaderboard;
	private BufferedReader bufferLeaderboard;
	private FileWriter writeToFile;
	private ArrayList<String[]> scores;
	private DefaultTableModel model;
	private JTable tblLeaderboard;
	private JScrollPane scrollPane;
	int count;
	int maxRowDisplay;


	ScoreManagerPanel() 
	{
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		loadScores();
		sortScores();
		//loadLeaderboard();
		newLeaderboard();
	}

	private void newLeaderboard()
	{
		count = 0;
		maxRowDisplay = scores.size();
		String[] tblHeaders = {"USERNAME", "SCORE", "RANK"};
		String[][] tblData = new String[maxRowDisplay][3];

		for(int row = 0; row < maxRowDisplay; row++)
		{
			for(int column = 0; column < 3; column++)
			{
				if(count == 2)
				{
					tblData[row][column] = Integer.toString(row + 1);
					count = 0;
				}
				else
				{
					tblData[row][column] = scores.get(row)[count];
					count++;
				}
			}
		}
		model = new DefaultTableModel(tblData, tblHeaders);
		tblLeaderboard = new JTable(model);
		scrollPane = new JScrollPane(tblLeaderboard);
		this.add(scrollPane);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new BackToMenu());
		this.add(btnBack);
	}

	private void loadScores() 
	{
		try 
		{
			scores = new ArrayList<>();
			leaderboard = new File("leaderboard.txt");
			readLeaderboard = new FileReader(leaderboard);
			bufferLeaderboard = new BufferedReader(readLeaderboard);
			String line;
			while((line = bufferLeaderboard.readLine()) != null) 
			{
				if(!(line.equals("")))
				{
					String[] lineContents = line.split("\\s+");
					scores.add(lineContents);
				}
			}
			readLeaderboard.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * Sort scores from highest to lowest
	 */
	private void sortScores() 
	{
		// Create a copy of the scores array
		ArrayList<String[]> scoresSorted = this.scores;
		if (scoresSorted.size() > 1) 
		{
			for (int x = 0; x < scoresSorted.size(); x++) 
			{
				for (int i = 0; i < scoresSorted.size() - x - 1; i++) 
				{
					// _scores[i][name][score][rank]
					if (Integer.parseInt(scoresSorted.get(i)[1]) < Integer.parseInt(scoresSorted.get(i + 1)[1])) 
					{
						// Store n+1th element into a temporary variable
						String[] tmp = scoresSorted.get(i+1);
						// Set the new n+1th element to be current n
						scoresSorted.set(i+1, scoresSorted.get(i));
						// Set current n to be the old n+1 element
						scoresSorted.set(i, tmp);
						// This acts as a simple way to 'swap' elements around
					}
				}
			}
		}
		// overwrite the scores array with the new sorted list
		this.scores = scoresSorted;
		writeSortedScores();
	}

	public void writeSortedScores()
	{
		try
		{
			leaderboard = new File("leaderboard.txt");
			writeToFile = new FileWriter(leaderboard);

			for(int i = 0; i < scores.size(); i++)
			{
				getScore = scores.get(i);
				writeToFile.write("\n" + getScore[0] + " " + Integer.parseInt(getScore[1]));
			}
			writeToFile.flush();
			writeToFile.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void writeNewScore(String username, int gameScore)
	{
		try 
		{
			leaderboard = new File("leaderboard.txt");
			writeToFile = new FileWriter(leaderboard, true);

			writeToFile.write("\n" + username + " " + Integer.toString(gameScore));
			writeToFile.flush();
			writeToFile.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public void closePanel()
	{
		SwingUtilities.getWindowAncestor(this).dispose();
	}

	/*
	 * Check if the given score should be on the on the leader board by checking
	 * if the given score is greater than the lowest current score
	 * @param score
	 */
	public boolean checkScore(int score)
	{
		return (score > Integer.parseInt(this.scores.get(this.scores.size())[1]));
	}

	class BackToMenu implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent accEvent)
		{
			closePanel();
		}
	}
}
