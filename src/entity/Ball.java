package entity;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;

/**
 * This extends Rectangle for its intersecting and bounds properties.
 * @author Connor Phillips
 * @version 1.0
 * @since 1.0
 */
public class Ball extends Rectangle
{
	private static final long serialVersionUID = 1671169915764987056L; //long serialVersionUID stores the serialVersionUID generated value
	private Image imgBall;    //Image imgBall stores an image of a ball 
	private Random rndGen;    //Random rndGen stores a randomly generated value between 0 and 1
	private int directionX;   //int directionX stores the directionX of this object
	private int directionY;   //int directionY stores the directionY of this object
	private int velocity;     //int velocity stores this objects velocity
	private int storedRndGen; //int storedRndGen stores the rndGen value
	
	/**
	 * This constructor expects a pair of x and y coordinates (upper-left corner) and the width and height of this and lastly an image.
	 * <br>It will also call the member function 'rndGenBallPosition' 
	 * @param x int The expected x coordinate of this
	 * @param y int The expected y coordinate of this
	 * @param width int The expected width of this 
	 * @param height int The expected height of this
	 * @param imgBall String An absolute path to the image location
	 */
	public Ball(int x, int y , int width, int height, String imgBall)
	{
		this.x 		  = x;
		this.y 		  = y;
		this.width    = width;
		this.height   = height;
		this.imgBall  = Toolkit.getDefaultToolkit().getImage(imgBall);
		rndGenBallPosition();
	}

	/**
	 * Draws this onto the component thats passed through.
	 * @param graphics Graphics Expects the component graphics of which it is being drawn to
	 * @param compImg Component Expects the component it is being drawn to 
	 */
	public void drawBall(Graphics graphics, Component compImg)
	{
		graphics.drawImage(imgBall, this.x, this.y, this.width, this.height, compImg);
	}

	/**
	 * This member function randomly generates the starting coordinates for this object.
	 */
	private void rndGenBallPosition()
	{
		rndGen = new Random();
		storedRndGen = rndGen.nextInt(2);

		if(storedRndGen == 0)
		{
			setDirectionX(-3);
			directionY = rndGen.nextInt(7)+2;
		}
		else if(storedRndGen == 1)
		{
			setDirectionX(+3);
			directionY = rndGen.nextInt(7)+2;
		}
	}

	/**
	 * This member function will set this objects velocity to be slower or faster.
	 * <br>If true the velocity will be faster, if false it will be slower.
	 * @param isSetHardMode boolean 
	 */
	public void setDifficulty(boolean isSetHardMode)
	{
		if(!isSetHardMode)
		{
			setVelocity(5);
		}
		else if(isSetHardMode)
		{
			setVelocity(7);
		}
	}

	/**
	 * This member function returns the directionX of this object.
	 * @return this objects directionX
	 */
	public int getDirectionX()
	{
		return directionX;
	}

	/**
	 * This member function sets the directionX of this object.
	 * @param directionX int
	 */
	public void setDirectionX(int directionX)
	{
		this.directionX = directionX;
	}

	/**
	 * This member function returns the directionY of this object.
	 * @return this objects directionY
	 */
	public int getDirectionY()
	{
		return directionY;
	}

	/**
	 * This member function sets the directionY of this object.
	 * @param directionY int
	 */
	public void setDirectionY(int directionY)
	{
		this.directionY = directionY;
	}

	/**
	 * This member function returns the velocity of this object.
	 * @return this objects velocity
	 */
	public int getVelocity()
	{
		return velocity;
	}

	/**
	 * This member function sets the velocity of this object.
	 * @param velocity int
	 */
	public void setVelocity(int velocity)
	{
		this.velocity = velocity;
	}
}

