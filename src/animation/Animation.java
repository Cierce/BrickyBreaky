package animation;
import main.GamePanel;

public class Animation implements Runnable
{
	private GamePanel brickyBreaker;
	private boolean run;
	
	public Animation(GamePanel move)
	{
		brickyBreaker = move;
	}

	public void run() 
	{
		run = true;
		
		while(run)
		{
			brickyBreaker.update();
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