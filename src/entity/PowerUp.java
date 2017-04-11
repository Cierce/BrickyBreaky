package entity;

import java.awt.*;

/**
 * Created by Connor Phillips on 10/04/2017.
 * This is responsible for giving the player extra points!
 */
public class PowerUp extends Rectangle
{
    private Image imgPowerUp;
    private int dY;
    private int pupDuration;
    private boolean destroyed;
    private boolean isEmptyPUP;
    private boolean isSpawnedPUP;

    public PowerUp()
    {

    }

    public void spawnPowerUp(int x, int y, int width, int height, String imgPowerUp)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imgPowerUp = Toolkit.getDefaultToolkit().getImage(imgPowerUp);
        pupDuration  = 60*5;
        destroyed    = false;
        isSpawnedPUP = false;
    }

    public void drawPowerUp(Graphics graphics, Component comp)
    {
        if(!destroyed)
        {
            graphics.drawImage(imgPowerUp, x, y, width, height, comp);
        }
    }

    public void setFallSpeed(int val)
    {
        dY = val;
    }

    public int getFallSpeed()
    {
        return dY;
    }

    public void setDestroyed(boolean destroyed)
    {
        this.destroyed = destroyed;
    }

    public boolean isSetDestroyed()
    {
        return destroyed;
    }

    public int getPowerUp()
    {
        return 155;
    }

    public void reducePUPDuration()
    {
        pupDuration--;
        if(pupDuration == 0)
        {
            isEmptyPUP = true;
        }
    }

    public Boolean isEmptyPUP()
    {
        return isEmptyPUP;
    }

    public Boolean isSpawnedPUP()
    {
        return isSpawnedPUP;
    }
}
