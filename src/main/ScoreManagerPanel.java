package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

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

	ScoreManagerPanel() 
	{
		loadScores();
		sortScores();
		loadLeaderboard();
	}

	private void loadLeaderboard()
	{	
		//Loads the leaderboard components 
		setLayout(null);
		lblTitle      = new JLabel();
		lblColumnDesc = new JLabel();
		txtUsername   = new JTextArea();
		txtUserScore  = new JTextArea();
		txtUserRank   = new JTextArea();
		bgColour = new Color(147, 198, 232); //alternative 155, 209, 245

		//populates the leaderboard with data
		maxResultsShown = 10;
		getScore    = new String[maxResultsShown];
		intUsrRank  = new int[maxResultsShown];
		usrName     = "";
		usrScore    = "";
		strUsrRank  = "";

		add(Box.createRigidArea(new Dimension(100, 0))); //sets the spacing offset the pane
		setBackground(bgColour);
		lblTitle.setText("<html><center>LEADERBOARD<br>TOP 10</center></html>");
		lblTitle.setFont(new Font("Helvetica", Font.BOLD, 14));

		lblColumnDesc.setText("USERNAME         SCORE           RANK");
		lblColumnDesc.setFont(new Font("Helvetica", Font.BOLD, 11));

		txtUsername.setEditable(false);
		txtUserScore.setEditable(false);
		txtUserRank.setEditable(false);

		txtUsername.setOpaque(false);
		txtUserScore.setOpaque(false);
		txtUserRank.setOpaque(false);

		txtUsername.setFont(new Font("Helvetica",  Font.BOLD, 12));
		txtUserScore.setFont(new Font("Helvetica", Font.BOLD, 12));
		txtUserRank.setFont(new Font("Helvetica",  Font.BOLD, 12));
		
		lblTitle.setBounds(95, -25, 200, 100);
		lblColumnDesc.setBounds(50, 5, 250, 100);
		txtUsername.setBounds(50, 65, 250, 200);
		txtUserScore.setBounds(145, 65, 250, 200);
		txtUserRank.setBounds(220, 65, 250, 200);
		
		add(lblTitle);
		add(lblColumnDesc);
		add(txtUsername);
		add(Box.createRigidArea(new Dimension(30, 0)));
		add(txtUserScore);
		add(Box.createRigidArea(new Dimension(45, 0)));
		add(txtUserRank);
	
		
		maxResultsShown = 0;
		for(int i = 0; i < scores.size(); i++)
		{
			if(maxResultsShown < 10)
			{
				getScore = scores.get(i);
				usrName  += getScore[0] + "\n";
				usrScore += getScore[1] + "\n";
				intUsrRank[i] = (i + 1);
				strUsrRank += Integer.toString(intUsrRank[i]) + "\n";
			}
			maxResultsShown++;
		}

		txtUsername.setText(usrName.toLowerCase());
		txtUserScore.setText(usrScore);
		txtUserRank.setText(strUsrRank);
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
					// _scores[i][name][score]
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
	}

	public void writeScores(String username, int gameScore)
	{
		try 
		{
			leaderboard = new File("leaderboard.txt");
			writeToFile = new FileWriter(leaderboard, true);
			
			writeToFile.write("\n" + username + " ");
			writeToFile.write(Integer.toString(gameScore));
			writeToFile.flush();
			writeToFile.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
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

	public void addScore(int score)
	{
		/* TODO: add the new score in the correct position*/
	}
}
