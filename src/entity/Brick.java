package entity;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * This extends Rectangle for its intersecting and bounds properties. 
 * @author Connor Phillips
 * @version 1.0
 * @since 1.0
 */
public class Brick extends Rectangle 
{
	private static final long serialVersionUID = -1617190210940294056L; //long serialVersionUID stores the serialVersionUID generated value
	private Graphics2D brickOutline; //Graphics2D brickOutline stores
	private boolean destroyed;       //boolean destroyed stores

	/**
	 * This constructor expects a pair of x and y coordinates (upper-left corner) and the width and height of this.
	 * <br>It also sets the default state of this to not destroyed.
	 * @param x int The expected x coordinate of this
	 * @param y int The expected y coordinate of this
	 * @param width int The expected width of this
	 * @param height int The expected height of this
	 */
	public Brick(int x, int y, int width, int height)
	{
		this.x 		= x;
		this.y 		= y;
		this.width  = width;
		this.height = height;
		destroyed   = false;
	}

	/**
	 * This member function draws this, colours it and displays it. 
	 * <br>This member function will only draw this object if 'destroyed' is set to false in this object, otherwise it will not draw it.
	 * @param graphics Graphics Expects the component graphics of which it is being drawn to
	 * @param brickColour Color Expects the colour (RGB) of this
	 */
	public void drawBrick(Graphics graphics, Color brickColour)
	{
		if(!(destroyed))
		{
			brickOutline = (Graphics2D) graphics;
			graphics.setColor(brickColour);
			graphics.fillRect(this.x, this.y, this.width, this.height);
			brickOutline.setStroke(new BasicStroke(2));
			brickOutline.setColor(new Color(0, 0, 0));
			brickOutline.drawRect(this.x, this.y, this.width, this.height);
		}
	}

	/**
	 * This member function returns the state of this object.
	 * @return this object's state
	 */
	public boolean isDestroyed() 
	{
		return destroyed;
	}

	/**
	 * This member function sets the state of this object.
	 * @param destroyed boolean Expects true or false
	 */
	public void setDestroyed(boolean destroyed) 
	{
		this.destroyed = destroyed;
	}
}

