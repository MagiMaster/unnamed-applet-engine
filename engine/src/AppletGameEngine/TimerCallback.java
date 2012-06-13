package AppletGameEngine;

/**
 * The <code>TimerCallback</code> interface should be implemented by any class
 * whose instances want to be notified by a <code>Timer</code>.
 *
 * @author  Joshua Taylor
 * @see     GameTimer
 */
// This could be replaced by a TimerCallback class to enforce synchronization
// but it would be harder to allow the callback to actually do something
public interface TimerCallback
{
	/**
	 * When a <code>Timer</code> object reaches zero, it calls the
	 * <code>timerFinished</code> method of the associated object implementing
	 * interface <code>TimerCallback</code>. Any implementation of this method
	 * needs to be synchronized.
	 *
	 * @param t         the timer that called this method
	 */
	void timerFinished(GameTimer t);
}
