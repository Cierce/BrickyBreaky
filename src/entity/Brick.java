package entity;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Brick extends Rectangle //provides us with intersecting and bounds 
{
	private static final long serialVersionUID = -1617190210940294056L;
	private Graphics2D brickOutline;
	private boolean destroyed;

	public Brick(int x, int y, int width, int height)
	{
		destroyed = false;
		this.x 		= x;
		this.y 		= y;
		this.width  = width;
		this.height = height;
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

	public boolean isDestroyed() 
	{
		return destroyed;
	}

	public void setDestroyed(boolean destroyed) 
	{
		this.destroyed = destroyed;
	}
}

