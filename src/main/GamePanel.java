package main;
import animation.AnimatePane;
import audio.AudioPlayer;
import entity.Ball;
import entity.Brick;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * This extends JPanel and implements KeyListener.
 * <br>It allows this to have keyboard interaction with the player,
 * as well as adding component element directly to this due to inheritance.
 * <br>This provides the player with the BrickyBreaky game graphical user interface. 
 * @author Connor Phillips
 * @version 1.0
 * @since 1.0
 */
public class GamePanel extends JPanel implements KeyListener
{
	private static final long serialVersionUID = 2884832087031349542L; //long serialVersionUID stores the serialVersionUID generated value

	private ArrayList<Brick> entBricks;     //ArrayList<Brick> entBricks stores an ArrayList of Brick objects
	private ArrayList<AudioPlayer> gameSFX; //ArrayList<AudioPlayer> gameSFX stores an ArrayList of AudioPlayer objects
	private ScoreManagerPanel scoreManager; //ScoreManagerPanel scoreManager declares an instance of the object ScoreManagerPanel
	private Brick entPlatform;              //Brick entPlatform declares an instance of the object Brick
	private Brick entBrick;                 //Brick entBrick declares an instance of the object Brick
	private Ball entBall;                   //Ball entBall declares an instance of the object Ball
	private Thread gameThread;              //Thread gameThread declares an instance of the object Thread
	private AnimatePane gameState;          //AnimatePane gameState declares an instance of the object AnimatePane
	private JLabel lblGameScore;            //JLabel lblGameScore displays the players score
	private JLabel lblPressEnter;           //JLabel lblPressEnter displays the enter key message
	private String playerName;              //String playerName stores the players name
	private String strIsSetHardmode;        //String strIsSetHardmode stores if hard mode was turned on (YES) or off (NO)
	private int amountOfBricks;             //int amountOfBricks stores the amount of bricks to be created
	private int ballSize;                   //int ballSize stores the size of the ball
	private int playAgain;                  //int playAgain stores if the player selected to play again or not
	private int	gameScore;                  //int gameScore stores the players score
	private int brickWidth;                 //int brickWidth stores the width of the Brick object
	private int brickHeight;                //int brickHeight stores the Height of the Brick object
	private int[] baseRGBVal;               //int[] baseRGBVal stores the base RGB values [0] = red, [1] = green, [2] = blue
	private int[] crntRGBVal;               //int[] crntRGBVal stores the current RGB values [0] = red, [1] = green, [2] = blue
	private int[] themeChoice;              //int[] themeChoice stores the players chosen theme from the options panel 
	private boolean pressedEnter;           //boolean pressedEnter stores if the player has pressed the enter key or not
	private boolean lostGame;               //boolean lostGame stores if the player has lost the game or not
	private boolean boolIsSetHardmode;      //boolean boolIsSetHardmode stores if hardmode has been turned on or not
	private boolean hasPlayerWon;           //boolean hasPlayerWon stores if the player has won the game or not

	/**
	 * This constructor sets the default values for the game, loads the sound effects and this' labels.
	 */
	GamePanel()
	{
		initaliseValues();
		loadSFX();
		loadLabels();
		addKeyListener(this);
		setFocusable(true);  
	}

	/**
	 * This member function initalise's the labels and adds them to this.
	 */
	private void loadLabels()
	{
		setLayout(null);
		lblGameScore = new JLabel();
		lblGameScore.setFont(new Font("Helvetica", Font.BOLD, 14));
		lblGameScore.setForeground(Color.BLACK);
		lblGameScore.setText("Score: 0");
		lblGameScore.setBounds(10, 450, 200, 200);
		add(lblGameScore);
		
		lblPressEnter = new JLabel();
		lblPressEnter.setText("Press the ENTER key to Start");
		lblPressEnter.setFont(new Font("Helvetica", Font.BOLD, 14));
		lblPressEnter.setForeground(Color.BLACK);
		lblPressEnter.setBounds(200, 200, 220, 200);
		add(lblPressEnter);
	}
	
	/**
	 * This member function initalise's the gameSFX array and adds the sfx to the array for use by this.
	 */
	private void loadSFX()
	{
		gameSFX = new ArrayList<>();
		gameSFX.add(0, new AudioPlayer("/SFX/brickbreak.mp3"));
		gameSFX.add(1, new AudioPlayer("/SFX/paddlehit.mp3"));
		gameSFX.add(2, new AudioPlayer("/SFX/gameover.mp3"));
		gameSFX.add(3, new AudioPlayer("/SFX/gamewon.mp3"));
	}
	
	/**
	 * This member function initalise's and sets this' member variables default values.
	 */
	private void initaliseValues()
	{
		amountOfBricks = 10;
		ballSize       = 25;
		playAgain      = 0;
		gameScore      = 0;
		brickWidth     = 60;
		brickHeight    = 25;
		pressedEnter   = false;
		lostGame       = false;
		hasPlayerWon   = false;
		baseRGBVal     = new int[3];
		gameState      = new AnimatePane(this);
		gameThread     = new Thread(gameState);
	}

	/**
	 * This member function initalise's the entities, entBricks, entPlatform and entBall. 
	 * As well as setting their default values for this. 
	 * <br>The default value for entPlatform will change based on boolIsSetHardmode being true or false.
	 */
	private void loadEntities()
	{
		entBricks = new ArrayList<>();
		if(!boolIsSetHardmode)
		{
			entPlatform = new Brick(230, 480, 150, 25);
		}
		else if(boolIsSetHardmode)
		{
			entPlatform = new Brick(250, 480, 100, 25);
		}

		entBall = new Ball(290, 420, ballSize, ballSize, "Resources\\Images\\ball.png");
		entBall.setDifficulty(boolIsSetHardmode);

		for(int i = 0; i < amountOfBricks; i++)
		{
			entBricks.add(new Brick((i * 60), 0, brickWidth, brickHeight));
		}
		for(int i = 0; i < amountOfBricks; i++)
		{
			entBricks.add(new Brick((i * 60), 25, brickWidth, brickHeight));
		}
		for(int i = 0; i < amountOfBricks; i++)
		{
			entBricks.add(new Brick((i * 60), 50, brickWidth, brickHeight));
		}
		for(int i = 0; i < amountOfBricks; i++)
		{
			entBricks.add(new Brick((i * 60), 75, brickWidth, brickHeight));
		}
	}
	
	/**
	 * This member function is inherited through the JComponent JPanel, see its documentation for more.
	 * @param Graphics graphics Allows us to use this paintComponent to draw objects to this
	 */
	public void paintComponent(Graphics graphics)
	{
		super.paintComponent(graphics);  //super keyword reference to the immediate parent class object e.g. the 'Graphics' of this
		crntRGBVal  = new int[]{0, 0, 0};
		
		for(int brickIndex = 0; brickIndex < entBricks.size(); brickIndex++)
		{
			rndGenColour();
			entBrick = entBricks.get(brickIndex);
			entBrick.drawBrick(graphics, new Color(crntRGBVal[0], crntRGBVal[1], crntRGBVal[2]));
		}
		
		entBall.drawBall(graphics, this);
		entPlatform.drawBrick(graphics, new Color(245, 245, 245));
		setBackground(new Color(crntRGBVal[0], crntRGBVal[1], crntRGBVal[2]));
	}
	
	/**
	 * This member function randomly generates a theme based on the inital theme values passed through.
	 * <br>If none are passed through then it will generate a default theme for the player.
	 */
	private void rndGenColour()
	{
		baseRGBVal[0] = themeChoice[0];
		baseRGBVal[1] = themeChoice[1];
		baseRGBVal[2] = themeChoice[2];
		crntRGBVal[0] += baseRGBVal[0];
		crntRGBVal[1] += baseRGBVal[1];
		crntRGBVal[2] += baseRGBVal[2];
		
		if(crntRGBVal[0] >= 255)
		{
			crntRGBVal[0] = 0;
		}
		if(crntRGBVal[1] >= 255)
		{
			crntRGBVal[1] = 0;
		}
		if(crntRGBVal[2] >= 255)
		{
			crntRGBVal[2] = 0;
		}
	}

	/**
	 * This member function updates the state of the game every 10 ticks, for example:
	 * <br>it evaluates the direction of the ball and sets it a new one, 
	 * <br>it sets the state of the entBricks (e.g. has it been destroyed),
	 * <br>it evaluates if the players entBall has interacted with the lower bound of the screen (if so the player loses),
	 * <br>it evaluates if the entBall has interacted with the bounds of the entPlatform.
	 * <br>Lastly, it repaints this object with the correct entity states and evaluates if the player has won.
	 */
	public void updateGameState()
	{
		if(entBall.x < 0)
		{
			entBall.setDirectionX(entBall.getVelocity());
		}
		if(entBall.x > getWidth() - ballSize)
		{
			entBall.setDirectionX(- (entBall.getVelocity()));
		}
		if(entBall.y < 0)
		{
			entBall.setDirectionY(+ entBall.getVelocity());
		}
		if(entBall.y > getHeight() - ballSize)
		{
			if(!lostGame)
			{
				lostGame = true;
				if(this.isFocusOwner())
				{
					gameSFX.get(2).play();
					JOptionPane.showMessageDialog(this, "YOU LOSE!", "GAME OVER", 0);
					saveScore();
					playAgain();
				}
			}
		}
		entBall.x += entBall.getDirectionX();
		entBall.y += entBall.getDirectionY();

		for(int i = 0; i < entBricks.size(); i++)
		{
			entBrick = entBricks.get(i);

			if(entBall.intersects(entBrick) && !entBrick.isDestroyed())
			{
				entBrick.setDestroyed(true);
				entBall.setDirectionY(entBall.getVelocity());
				gameSFX.get(0).play();

				if(!boolIsSetHardmode)
				{
					gameScore++; 
				}
				else if(boolIsSetHardmode)
				{
					gameScore += 2;
				}
				lblGameScore.setText("Score: " + Integer.toString(gameScore));
			}
		}
		if(entBall.intersects(entPlatform))
		{
			gameSFX.get(1).play();
			lblPressEnter.setText("");
			entBall.setDirectionY(-(entBall.getVelocity()));
		}
		repaint();
		checkPlayerWin();
	}
	
	/**
	 * This member function evaluates if the player has won before (during the instance of the exact same game),
	 * <br>evaluates if the player is in hardmode and then determines if they have met the respective win conditions,
	 * <br>for the default game mode and hardmode.
	 */
	private void checkPlayerWin()
	{
		if(!hasPlayerWon)
		{
			if(!boolIsSetHardmode)
			{
				if(gameScore == 40) //if hard mode isn't set then 40 points will trigger the win
				{
					hasPlayerWon = true;
					gameSFX.get(3).play();
					JOptionPane.showMessageDialog(this, "YOU WIN!", "WINNER", 1);
					saveScore();
					playAgain();
				}
			}
			else if(boolIsSetHardmode)
			{
				if(gameScore == 80) //if hard mode is set then 80 points will trigger the win
				{
					hasPlayerWon = true;
					gameSFX.get(3).play();
					JOptionPane.showMessageDialog(this, "YOU WIN HARD MODE!", "WINNER", 1);
					saveScore();
					playAgain();
				}
			}
		}
	}

	/**
	 * This member function saves the players name (providing its valid), score and if they were playing in hardmode or not to the leaderboard.
	 */
	private void saveScore()
	{
		scoreManager = new ScoreManagerPanel();
		playerName = (String)JOptionPane.showInputDialog
				(this, "Enter playername (no spaces) to Save Score: ", "Save Score to Leaderboard", JOptionPane.PLAIN_MESSAGE);

		if(!(playerName == null))
		{
			if(!(playerName.trim().length() == 0))
			{
				playerName = playerName.replace(" ", "");
				if(playerName.length() >= 10)
				{
					playerName = playerName.substring(0, 10);
				}
				scoreManager.writeNewPlayer(playerName, gameScore, strIsSetHardmode);
			}
		}
	}

	/**
	 * This member function evaluates if the player has chosen to play again or exit. 
	 * <br>By default we assume they do not want to play again if they fail to select 'yes' or 'no'.
	 * <br>For example this can happen if they press ESC.
	 */
	private void playAgain()
	{
		playAgain = (int)JOptionPane.showConfirmDialog (this, "Would you like to play again?","You lose! Score: " + Integer.toString(gameScore), playAgain);

		if(playAgain == JOptionPane.NO_OPTION)
		{
			exitGame();
		}
		else if(playAgain == JOptionPane.YES_OPTION)
		{
			resetGame();
		}
		else
		{
			exitGame();
		}
	}
	
	/**
	 * This member function is called if the player decides to play again.
	 * <br>It clears the current ArrayList of entBrick, loads the new entities with their default settings
	 * <br>and gives the appearance of resetting the game.
	 */
	private void resetGame()
	{
		entBricks.clear();
		pressedEnter = false;
		lostGame     = false;
		gameScore    = 0;
		loadEntities();
	}

	/**
	 * This member function disposes of this and brings its ancestor component to the front.
	 * <br>It also closes any open gameSFX's, preventing sound from playing when this has been disposed.
	 */
	private void exitGame()
	{
		gameSFX.get(0).close();
		gameSFX.get(1).close();
		gameSFX.get(2).close();
		gameSFX.get(3).close();
		SwingUtilities.getWindowAncestor(this).dispose();
		SwingUtilities.getWindowAncestor(this).setAlwaysOnTop(true);
	}

	/**
	 * This member function sets a default theme for the player if they have not selected one.
	 * <br>If they have selected a theme in the options menu, then it will set it to be the chosen them.
	 * @param themeChoice int[] Expects an array containing the value of the themes
	 * @param isSetThemeChoice boolean Expects true or false
	 */
	public void setThemeChoice(int[] themeChoice, boolean isSetThemeChoice)
	{
		this.themeChoice = new int[3];
		if(!isSetThemeChoice)
		{
			this.themeChoice[0] = 5;
			this.themeChoice[1] = 5;
			this.themeChoice[2] = 5;
		}
		else if(isSetThemeChoice)
		{
			this.themeChoice[0] = themeChoice[0];
			this.themeChoice[1] = themeChoice[1];
			this.themeChoice[2] = themeChoice[2];
		}
	}

	/**
	 * This member function evaluates if hardmode is true or not,
	 * <br>the entities are then loaded with hardmode being set or not in mind.
	 * @param isSetHardMode boolean Expects true or false
	 */
	public void setDifficulty(boolean isSetHardMode)
	{
		if(!isSetHardMode)
		{
			boolIsSetHardmode = isSetHardMode;
			strIsSetHardmode  = "NO";
			loadEntities();
		}
		else if(isSetHardMode)
		{
			boolIsSetHardmode = isSetHardMode;
			strIsSetHardmode  = "YES";
			lblPressEnter.setText("<html><center>" + lblPressEnter.getText() + "<br>HARD MODE</center></html>");
			loadEntities();
		}
	}

	/**
	 * This member function is implemented through the KeyListener, see its documentation for more.
	 * <br>This member function allows us to listen for key strokes by the player,
	 * <br>including moving the platform by incrementing or decrementing the entPlatform's x coordinate.
	 * <br>This member function ultimately allows the player to start the game by pressing ENTER and starting the gameThread.
	 * @throws IllegalThreadStateException this is thrown if the thread ends up in a state its not allowed
	 */
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_ENTER && !pressedEnter)
		{
			pressedEnter = true;
			try
			{
				gameThread.start();
			}
			catch(IllegalThreadStateException illThreadState)
			{
				System.out.println("Caught the illegal thread state.");
			}
		}
		if((e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) && entPlatform.x > 0)
		{
			entPlatform.x -= 60;
		}
		if((e.getKeyCode() == KeyEvent.VK_D ||e.getKeyCode() == KeyEvent.VK_RIGHT) && entPlatform.x < (getWidth() - entPlatform.width))
		{
			entPlatform.x += 60;
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			exitGame();
		}
	}

	/**
	 * This member function is implemented through the KeyListener, see its documentation for more.
	 * <br>Note: Because we implement KeyListener the member function 'keyReleased' must be present,
	 * <br>even if we do not use it.
	 */
	public void keyReleased(KeyEvent e)
	{

	}
	
	/**
	 * This member function is implemented through the KeyListener, see its documentation for more.
	 * <br>Note: Because we implement KeyListener the member function 'keyTyped' must be present,
	 * <br>even if we do not use it.
	 */
	public void keyTyped(KeyEvent e)
	{

	}
}