package entity;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;

import main.OptionPanel;

public class Brick extends Rectangle //provides us with intersecting and bounds 
{
	private static final long serialVersionUID = -1617190210940294056L;

	private Image blockImg;
	private int directionX;
	private int directionY;
	private int velocity;
	private int rndGenR;
	private Random rndGen;
	private Graphics2D brickOutline;
	private boolean destroyed;

	public Brick(int x, int y , int width, int height, String img)
	{
		this.x 		= x;
		this.y 		= y;
		this.width  = width;
		this.height = height;
		blockImg 	= Toolkit.getDefaultToolkit().getImage(img);

		rndGenBallPosition();

		if(OptionPanel.isSetDifficultyHard())
		{
			setVelocity(7); //if user has clicked hard difficulty in options
		}
		else
		{
			setVelocity(5);
		}
	}

	public Brick(int x, int y, int width, int height)
	{
		destroyed = false;
		
		this.x 		= x;
		this.y 		= y;
		this.width  = width;
		this.height = height;
	}

	private void rndGenBallPosition()
	{
		rndGen = new Random();
		rndGenR = rndGen.nextInt(2);

		if(rndGenR == 0)
		{
			setDirectionX(-3);
			directionY = rndGen.nextInt(7)+2;
		}
		else if(rndGenR == 1)
		{
			setDirectionX(+3);
			directionY = rndGen.nextInt(7)+2;
		}
	}

	public void drawBall(Graphics graphics, Component compImg)
	{
		graphics.drawImage(blockImg, this.x, this.y, this.width, this.height, compImg);
	}

	public void drawBrick(Graphics graphics, Color brickColour)
	{
		brickOutline = (Graphics2D) graphics;

		if(!(isDestroyed()))
		{
			graphics.setColor(brickColour);
			graphics.fillRect(this.x, this.y, this.width, this.height);

			brickOutline.setStroke(new BasicStroke(2));
			brickOutline.setColor(new Color(0, 0, 0));
			brickOutline.drawRect(this.x, this.y, this.width, this.height);
		}
	}

	public int getDirectionX() 
	{
		return directionX;
	}

	public void setDirectionX(int directionX) 
	{
		this.directionX = directionX;
	}
	
	public int getDirectionY() 
	{
		return directionY;
	}

	public void setDirectionY(int directionY) 
	{
		this.directionY = directionY;
	}

	public int getVelocity() 
	{
		return velocity;
	}

	public void setVelocity(int velocity) 
	{
		this.velocity = velocity;
	}

	public boolean isDestroyed() 
	{
		return destroyed;
	}

	public void setDestroyed(boolean destroyed) 
	{
		this.destroyed = destroyed;
	}
}

