package entity;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;

public class Ball extends Rectangle //provides us with intersecting and bounds
{
    private static final long serialVersionUID = -1617190210940294056L;
    private Image blockImg;
    private int directionX;
    private int directionY;
    private int velocity;
    private int rndGenR;
    private Random rndGen;
    private boolean isSetHardMode;

    public Ball(int x, int y , int width, int height, String img)
    {
        this.x 		  = x;
        this.y 		  = y;
        this.width    = width;
        this.height   = height;
        blockImg 	  = Toolkit.getDefaultToolkit().getImage(img);

        rndGenBallPosition();
    }

    public void drawBall(Graphics graphics, Component compImg)
    {
        graphics.drawImage(blockImg, this.x, this.y, this.width, this.height, compImg);
    }

    public void setDifficulty(boolean isSetHardMode)
    {
        if(isSetHardMode == false)
        {
            setVelocity(5);
        }
        else if(isSetHardMode)
        {
            setVelocity(7);
        }
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
}

