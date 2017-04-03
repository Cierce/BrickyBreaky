package main;
import animation.Animation;
import audio.AudioPlayer;
import entity.Ball;
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
	private ArrayList<Brick> entBricks;
	private Brick entPlatform;
	private Ball entBall;
	private Brick entBrick;
	private ScoreManagerPanel scoreManager;
	private String usrInput;
	private Color platformColour;
	private Color blockGradient;
	private JLabel lblGameScore, lblPressEnter;
	private ArrayList<AudioPlayer> gameSFX;
    private Thread thread;
    private Animation gameState;
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
	private boolean isSetHardMode;

	GamePanel()
	{
		amountOfBricks      = 10;
		ballSize            = 25;
		dialogResult        = 0;
		gameScore           = 0;
		brickWidth          = 60;
		brickHeight         = 25;
		brickX 		        = 60;
		pressedEnter        = false;
		lostGame            = false;

		loadSFX();
		loadLables();
		
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

	private void loadEntities()
	{
		entBricks = new ArrayList<>();
		if(isSetHardMode == false)
		{
            entPlatform = new Brick(230, 480, 150, 25);
		}
		else if(isSetHardMode)
		{
            entPlatform = new Brick(250, 480, 100, 25);
		}

		entBall = new Ball(290, 420, ballSize, ballSize, "Resources\\Images\\ball.png");
		entBall.setDifficulty(isSetHardMode);

		for(int i = 0; i < amountOfBricks; i++)
		{
			entBricks.add(new Brick(i * brickX, 0, brickWidth, brickHeight));
		}
		for(int i = 0; i < amountOfBricks; i++)
		{
			entBricks.add(new Brick(i * brickX, 25, brickWidth, brickHeight));
		}
		for(int i = 0; i < amountOfBricks; i++)
		{
			entBricks.add(new Brick(i * brickX, 50, brickWidth, brickHeight));
		}
		for(int i = 0; i < amountOfBricks; i++)
		{
			entBricks.add(new Brick(i * brickX, 75, brickWidth, brickHeight));
		}
	}

	public void setThemeChoice(int[] themeChoice, boolean isSetThemeChoice)
	{
        this.themeChoice = new int[3];
	    if(isSetThemeChoice == false)
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

	public void setDifficulty(boolean isSetHardMode)
    {
        if(isSetHardMode == false)
        {
            this.isSetHardMode = isSetHardMode;
            loadEntities();
        }
        else if(isSetHardMode)
        {
            this.isSetHardMode = isSetHardMode;
            loadEntities();
        }
    }

	public void paintComponent(Graphics graphics)
	{
		super.paintComponent(graphics); //super keyword reference to the immediate parent class object e.g. the 'Graphics' of the JPanel
		cR = 0;
		cG = 0;
		cB = 0;

		for(int i = 0; i < entBricks.size(); i++)
		{
			entBrick = entBricks.get(i);
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
			entBrick.drawBrick(graphics, blockGradient);
		}
		setBackground(blockGradient);
		entBall.drawBall(graphics, this);

		platformColour = new Color(245, 245, 245);
		entPlatform.drawBrick(graphics, platformColour);
	}

	public void update()
	{
		/*LEFT EDGE
		 * If the entBall is off the left edge we set the direction_x to be the velocity
		 * which we set in the class to be 3 by default
		 * If the entBall touches the left edge we set the direction to be positive
		 */
		if(entBall.x < 0)
		{
			entBall.setDirectionX(entBall.getVelocity());
		}

		/*RIGHT EDGE
		 * This time we are the opposite, if the entBall's x position is off the right edge
		 * we set the direction to be negative velocity
		 */
		if(entBall.x > getWidth() - ballSize)
		{
			entBall.setDirectionX(- (entBall.getVelocity()));
		}

		/*UPPER EDGE
		 * Here we're doing the same but now for the upper edge, the only difference is now we're setting
		 * the direction of the Y_axis by using direction_y
		 */
		if(entBall.y < 0)
		{
			entBall.setDirectionY(+ entBall.getVelocity());
		}

		/*LOWER EDGE
		 * Here we're doing the exact opposite of the upper edge
		 * However it's disabled for now because it's apart of the loss condition
		 * entBall.direction_y = -(entBall.velocity);
		 */
		if(entBall.y > getHeight() - ballSize)
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

		//The direction of the entBall is determined after it goes through the if statements
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
				
				if(isSetHardMode)
				{
					gameScore += 2; //increment score by 2 for each block destroyed if in hard mode
				}
				else
				{
					gameScore++;
				}

				lblGameScore.setText("Score: " + Integer.toString(gameScore)); //show it on the score		
			}

			if(isSetHardMode)
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

		if(entBall.intersects(entPlatform))
		{
			lblPressEnter.setText("");
			gameSFX.get(1).play();
			entBall.setDirectionY(-(entBall.getVelocity()));
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
				scoreManager.writeNewScore(usrInput, gameScore, isSetHardMode);
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
		entBricks.clear();
		loadEntities();
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
			entPlatform.x -= 60;
		}
		if((e.getKeyCode() == KeyEvent.VK_D ||e.getKeyCode() == KeyEvent.VK_RIGHT) && entPlatform.x < (getWidth() - entPlatform.width))
		{
			entPlatform.x += 60;
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