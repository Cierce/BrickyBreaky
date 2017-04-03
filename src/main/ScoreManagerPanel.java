package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class ScoreManagerPanel extends JPanel
{
	private static final long serialVersionUID = 5406943106354812340L;
	private String[] getScore;
	private File ldrBrdDataStore;
	private FileReader readLdrBrdData;
	private BufferedReader ldrBrdBuffer;
	private FileWriter writeToFile;
	private ArrayList<String[]> scores;
	private DefaultTableModel model;
	private JTable tblLeaderboard;
	private JScrollPane scrollPane;
	private JButton btnBack;
	private int userScoreIndex;
	private int maxRowDisplay;
	private String hardMode;
	private ArrayList<String[]> scoresSorted;

	ScoreManagerPanel()
	{
		loadScoreDB();
		loadScores();
		sortScores();
		loadLeaderboard();
        writeSortedScores();
	}

	private void loadLeaderboard()
	{
		userScoreIndex = 0;
		maxRowDisplay  = scores.size();
		String[] tblHeaders = {"USERNAME", "SCORE", "HARD MODE", "RANK"};
		String[][] tblData = new String[maxRowDisplay][tblHeaders.length];

		for(int row = 0; row < maxRowDisplay; row++)
		{
			for(int column = 0; column < tblHeaders.length; column++)
			{
               if(column == 3)
               {
                   tblData[row][column] = Integer.toString((row + 1));
               }
               else
               {
                   tblData[row][column] = scores.get(row)[column].toUpperCase();
               }
            }
		}
		model = new DefaultTableModel(tblData, tblHeaders);
		tblLeaderboard = new JTable(model);
		scrollPane = new JScrollPane(tblLeaderboard);
		this.add(scrollPane);

		btnBack = new JButton("Back");
		btnBack.addActionListener(new BackToMenu());
		btnBack.setAlignmentX(Component.RIGHT_ALIGNMENT);
		this.add(btnBack);
	}

	public void loadScoreDB()
	{
		ldrBrdDataStore = new File("ldrBrdDataStore.txt");
		try
		{
			readLdrBrdData = new FileReader(ldrBrdDataStore);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		ldrBrdBuffer = new BufferedReader(readLdrBrdData);
	}

	private void loadScores()
	{
		try
		{
			scores = new ArrayList<>();
			String line;
			while((line = ldrBrdBuffer.readLine()) != null)
			{
				if(!(line.equals("")))
				{
					String[] lineContents = line.split("\\s+");
					scores.add(lineContents);
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
	 * Sort scores from highest to lowest
	 */
	private void sortScores()
	{
		// Create a copy of the scores array
		scoresSorted  = scores;
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
		scores = scoresSorted;
	}

	public void writeSortedScores()
	{
		try
		{
			writeToFile = new FileWriter(ldrBrdDataStore);

			for(int i = 0; i < scores.size(); i++)
			{
				getScore = scores.get(i);
				writeToFile.write("\n" + getScore[0] + " " + Integer.parseInt(getScore[1]) + " " + getScore[2]);
			}
			writeToFile.flush();
			writeToFile.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void writeNewScore(String username, int gameScore, boolean isSetHardmode)
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
