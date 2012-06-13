package AppletGameEngine;

/**
 * A timer that runs in its own thread.
 *
 * @author  Joshua Taylor
 * @see     TimerCallback
 */
public class GameTimer implements Runnable
{
	/**
	 * Length of time to run the timer in milliseconds.
	 */
	private long delay;

	/**
	 * A <code>TimerCallback</code> object that will be notified when the timer
	 * reaches zero.
	 */
	private TimerCallback callback;

	/**
	 * Creates a timer, sets its <code>delay</code> time, starts it in a new
	 * thread, then returns the thread.
	 *
	 * @param delay     the length of time to run the timer in milliseconds
	 * @param tc        the <code>TimerCallback</code> that will be notified
	 *                  when the timer reaches zero
	 * @return          A new thread that contains the timer.
	 */
	public static Thread startTimer(long delay, TimerCallback tc)
	{
		GameTimer timer = new GameTimer(delay, tc);
		Thread t = new Thread(timer);
		t.start();
		return t;
	}

	/**
	 * Creates a timer and sets the delay and callback.
	 *
	 * @param delay     the length of time to run the timer in milliseconds
	 * @param tc        the <code>TimerCallback</code> that will be notified
	 *                  when the timer reaches zero
	 */
	public GameTimer(long delay, TimerCallback tc)
	{
		this.delay = delay;
		callback = tc;
	}

	/**
	 * Called by <code>Thread.start()</code>.
	 */
	public void run()
	{
		try
		{
			Thread.sleep(delay);
		}
		catch(InterruptedException e)
		{
		}

		callback.timerFinished(this);
	}
}
