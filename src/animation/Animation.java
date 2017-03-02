package animation;
import main.GamePanel;

public class Animation implements Runnable
{
	private GamePanel blockyBreaker;
	private boolean run;
	
	public Animation(GamePanel move)
	{
		blockyBreaker = move;
	}

	public void run() 
	{
		run = true;
		
		while(run)
		{
			blockyBreaker.update();
			try 
			{
				Thread.sleep(10);  
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public void stopGame()
	{
		run = false;
	}
}