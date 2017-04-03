package main;
import animation.Animation;
import audio.AudioPlayer;
import entity.Brick;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener
{
	private static final long serialVersionUID = 2884832087031349542L;

	private int[] themeChoice;
	private ArrayList<Brick> bricks;
	private Brick platform;
	private Brick ball;
	private Brick brick;
	private ScoreManagerPanel scoreManager;
	private String usrInput;
	private Color platformColour;
	private Color blockGradient;
	private int amountOfBricks;
	private int ballSize;
	private int dialogResult;
	private int	gameScore;
	private int brickWidth;
	private int brickHeight;
	private int brickX;
	private int baseR, baseG, baseB, cR, cG, cB;
	private boolean pressedEnter;
	private boolean lostGame;
	private boolean boolThemeChoice;
	private JLabel lblGameScore, lblPressEnter;
	private ArrayList<AudioPlayer> gameSFX;
	Thread thread;
	Animation gameState;

	GamePanel()
	{
		amountOfBricks = 10;
		ballSize       = 25;
		dialogResult   = 0;
		gameScore      = 0;
		brickWidth     = 60;
		brickHeight    = 25;
		brickX 		   = 60;
		pressedEnter   = false;
		lostGame       = false;

		loadSFX();
		loadLables();
		drawBricks();
		
		addKeyListener(this); //Gives our gamePanel class an event 'KeyListener' that will listen for key strokes
		setFocusable(true);   //Brings gamePanel to front if it isn't
	}

	private void loadSFX()
	{
		gameSFX = new ArrayList<AudioPlayer>();
		gameSFX.add(0, new AudioPlayer("/SFX/brickbreak.mp3"));
		gameSFX.add(1, new AudioPlayer("/SFX/paddlehit.mp3"));
		gameSFX.add(2, new AudioPlayer("/SFX/gameover.mp3"));
		gameSFX.add(3, new AudioPlayer("/SFX/gamewon.mp3"));
	}

	private void loadLables()
	{
		setLayout(null);
		lblGameScore = new JLabel();
		lblGameScore.setFont(new Font("Helvetica", Font.BOLD, 14));
		lblGameScore.setForeground(Color.BLACK);
		lblGameScore.setText("Score: 0");
		add(lblGameScore);
		lblGameScore.setBounds(10, 450, 200, 200);

		lblPressEnter = new JLabel();
		lblPressEnter.setText("Press ENTER to Start");
		lblPressEnter.setFont(new Font("Helvetica", Font.BOLD, 14));
		lblPressEnter.setForeground(Color.BLACK);
		lblPressEnter.setBounds(235, 200, 200, 200);
		add(lblPressEnter);
	}

	private void drawBricks()
	{
		bricks = new ArrayList<>();

		if(OptionPanel.isSetDifficultyHard())
		{
			platform = new Brick(250, 480, 100, 25);
		}
		else
		{
			platform = new Brick(230, 480, 150, 25);
		}

		ball = new Brick(290, 420, ballSize, ballSize, "Resources\\Images\\ball.png");

		for(int i = 0; i < amountOfBricks; i++)
		{
			bricks.add(new Brick(i * brickX, 0, brickWidth, brickHeight));
		}
		for(int i = 0; i < amountOfBricks; i++)
		{
			bricks.add(new Brick(i * brickX, 25, brickWidth, brickHeight));
		}
		for(int i = 0; i < amountOfBricks; i++)
		{
			bricks.add(new Brick(i * brickX, 50, brickWidth, brickHeight));
		}
		for(int i = 0; i < amountOfBricks; i++)
		{
			bricks.add(new Brick(i * brickX, 75, brickWidth, brickHeight));
		}
	}

	public void setThemeChoice(int[] themeChoice, boolean themeChosen)
	{
		this.themeChoice = new int[3];
		this.themeChoice[0] = themeChoice[0];
		this.themeChoice[1] = themeChoice[1];
		this.themeChoice[2] = themeChoice[2];
		System.out.println(this.themeChoice[0]);
		this.boolThemeChoice = themeChosen;
	}

	public void setNoTheme()
	{
		themeChoice = new int[3];
		themeChoice[0] = 5;
		themeChoice[1] = 5;
		themeChoice[2] = 5;
		boolThemeChoice = false;
	}

	public void paintComponent(Graphics graphics)
	{
		super.paintComponent(graphics); //super keyword reference to the immediate parent class object e.g. the 'Graphics' of the JPanel
		cR = 0;
		cG = 0;
		cB = 0;

		if(boolThemeChoice == false)
		{
			themeChoice = new int[3];
			themeChoice[0] = 5;
			themeChoice[1] = 5;
			themeChoice[2] = 5;
		}

		for(int i = 0; i < bricks.size(); i++)
		{
			brick = bricks.get(i);

			baseR = themeChoice[0];
			baseG = themeChoice[1];
			baseB = themeChoice[2];

			cR += baseR;
			cG += baseG;
			cB += baseB;

			if(cR >= 255)
			{
				cR = 0;
			}
			if(cG >= 255)
			{
				cG = 0;
			}
			if(cB >= 255)
			{
				cB = 0;
			}

			blockGradient = new Color(cR, cG, cB);
			brick.drawBrick(graphics, blockGradient);
		}
		setBackground(blockGradient);
		ball.drawBall(graphics, this);

		platformColour = new Color(245, 245, 245);
		platform.drawBrick(graphics, platformColour);
	}

	public void update()
	{
		/*LEFT EDGE
		 * If the ball is off the left edge we set the direction_x to be the velocity
		 * which we set in the class to be 3 by default
		 * If the ball touches the left edge we set the direction to be positive 
		 */
		if(ball.x < 0)
		{
			ball.setDirectionX(ball.getVelocity());
		}

		/*RIGHT EDGE
		 * This time we are the opposite, if the ball's x position is off the right edge
		 * we set the direction to be negative velocity
		 */
		if(ball.x > getWidth() - ballSize)
		{
			ball.setDirectionX(- (ball.getVelocity()));
		}

		/*UPPER EDGE
		 * Here we're doing the same but now for the upper edge, the only difference is now we're setting
		 * the direction of the Y_axis by using direction_y
		 */
		if(ball.y < 0)
		{
			ball.setDirectionY(+ ball.getVelocity());
		}

		/*LOWER EDGE
		 * Here we're doing the exact opposite of the upper edge
		 * However it's disabled for now because it's apart of the loss condition
		 * ball.direction_y = -(ball.velocity);
		 */
		if(ball.y > getHeight() - ballSize)
		{
			if(lostGame == false)
			{
				lostGame = true;
				if(this.isFocusOwner() == true)
				{
					gameSFX.get(2).play();
					JOptionPane.showMessageDialog(this, "YOU LOSE!", "GAME OVER", 0);
					saveScore();
					playAgain();
				}
			}
		}

		//The direction of the ball is determined after it goes through the if statements
		ball.x += ball.getDirectionX();
		ball.y += ball.getDirectionY();

		for(int i = 0; i < bricks.size(); i++)
		{
			brick = bricks.get(i);

			if(ball.intersects(brick) && !brick.isDestroyed())
			{
				brick.setDestroyed(true);
				ball.setDirectionY(ball.getVelocity());

				gameSFX.get(0).play();
				
				if(OptionPanel.isSetDifficultyHard())
				{
					gameScore += 2; //increment score by 2 for each block destroyed if in hard mode
				}
				else
				{
					gameScore++;
				}

				lblGameScore.setText("Score: " + Integer.toString(gameScore)); //show it on the score		
			}

			if(OptionPanel.isSetDifficultyHard())
			{
				if(gameScore == 80) //if hard mode is set then 80 points will trigger the win
				{
					gameSFX.get(3).play();
					JOptionPane.showMessageDialog(this, "YOU WON HARD MODE!", "WINNER", 1);
					saveScore();
					playAgain();
				}
			}
			else
			{
				if(gameScore == 40) //if hard mode isn't set then 40 points will trigger the win
				{
					gameSFX.get(3).play();
					JOptionPane.showMessageDialog(this, "YOU WIN!", "WINNER", 1);
					saveScore();
					playAgain();
				}
			}
		}

		if(ball.intersects(platform))
		{
			lblPressEnter.setText("");
			gameSFX.get(1).play();
			ball.setDirectionY(-(ball.getVelocity()));
		}
		repaint();	//repaints 'this' every tick
	}

	public void saveScore()
	{
		scoreManager = new ScoreManagerPanel();
		usrInput = JOptionPane.showInputDialog
				(this, "Enter Username (no spaces) to Save Score: ", "Save Score to Leaderboard", JOptionPane.PLAIN_MESSAGE);

		if(!(usrInput == null))
		{
			if(!(usrInput.trim().length() == 0))
			{
				usrInput = usrInput.replace(" ", "");
				if(usrInput.length() >= 10)
				{
					usrInput = usrInput.substring(0, 10);
				}
				//usrInput = usrInput.toUpperCase();
				scoreManager.writeNewScore(usrInput, gameScore);
			}
		}
	}

	public void playAgain()
	{
		dialogResult = JOptionPane.showConfirmDialog (this, "Would you like to play again?","You lose! Score: " + Integer.toString(gameScore), dialogResult);

		if(dialogResult == JOptionPane.NO_OPTION)
		{
			gameState.stopGame();
			SwingUtilities.getWindowAncestor(this).dispose();
		}
		else if(dialogResult == JOptionPane.YES_OPTION)
		{
			resetGame();
		}
	}

	private void resetGame()
	{
		bricks.clear();
		drawBricks();
		lostGame  = false;
		gameScore = 0;
		lblGameScore.setText("Score: 0");
	}

	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_ENTER && pressedEnter == false)
		{
			pressedEnter = true;
			gameState = new Animation(this);
			thread  = new Thread(gameState);
			thread.start();
		}
		if((e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT))
		{
			platform.x -= 60;
		}
		if((e.getKeyCode() == KeyEvent.VK_D ||e.getKeyCode() == KeyEvent.VK_RIGHT) && platform.x < (getWidth() - platform.width))
		{
			platform.x += 60;
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			thread.interrupt();
			SwingUtilities.getWindowAncestor(this).dispose();
		}
	}

	public void keyReleased(KeyEvent e) 
	{

	}

	public void keyTyped(KeyEvent e) 
	{

	}
}