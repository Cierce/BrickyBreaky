package animation;
import main.GamePanel;

/**
 * This Implements Runnable and provides Animation to the GamePanel object.
 * @author Connor Phillips
 * @version 1.0
 * @since 1.0
 */
public class AnimatePane implements Runnable
{
	private GamePanel brickyBreaky; //GamePanel brickyBreaky is an object that we set to be the current instance of GamePanel
	private boolean isRunning;      //boolean isRunning is a boolean that is responsible for allowing the continued running of the brickyBreaky.update() method
	
	/**
	 * This constructor expects a GamePanel object as the argument.
	 * <br>It also sets the instance of GamePanel in this to be the same as the one passed through.
	 * @param GamePanel brickyBreaky Expects a GamePanel object to be passed through
	 */
	public AnimatePane(GamePanel brickyBreaky)
	{
		this.brickyBreaky = brickyBreaky;
	}
	
	/**
	 * The 'run' method belongs to the implemented class 'Runnable'
	 * <br>It provides the class which implemented Runnable whose instances are intended to be executed by a thread.
	 * @throws InterruptedException will be thrown if the current thread is interrupted
	 */
	public void run()
	{
		isRunning = true;

		while(isRunning)
		{
			try
			{
				brickyBreaky.updateGameState();
				Thread.sleep(10);
			}
			catch(InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
		}
	}
}