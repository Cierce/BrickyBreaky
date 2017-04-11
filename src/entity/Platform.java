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
public class Platform extends Rectangle
{
    private Graphics2D brickOutline; //Graphics2D brickOutline stores
    private int oldWidth;

    public Platform(int x, int y, int width, int height)
    {
        this.x 		= x;
        this.y 		= y;
        this.width  = width;
        this.height = height;
        oldWidth    = width;
    }

    public void drawBrick(Graphics graphics, Color brickColour)
    {
        brickOutline = (Graphics2D) graphics;
        graphics.setColor(brickColour);
        graphics.fillRect(this.x, this.y, this.width, this.height);
        brickOutline.setStroke(new BasicStroke(2));
        brickOutline.setColor(new Color(0, 0, 0));
        brickOutline.drawRect(this.x, this.y, this.width, this.height);
    }

    public int getXPos()
    {
        return this.x;
    }

    public int getYPos()
    {
        return  this.y;
    }

    public void widthPowerUp(int widthPowerUp)
    {
        width = widthPowerUp;
    }

    public void resetWidth()
    {
        width = oldWidth;
    }
}

