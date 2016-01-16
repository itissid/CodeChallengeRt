package me.itissid.model;

/**
 * A time provider to make things more testable.
 * It provides the abstraction for start time of the race and the amount of time passed
 * since the race began.
 * @author sidharth
 *
 */
public interface ITimeProvider {

	/**
	 * Gets the race start time accurate to 3 decimal digits.
	 * @return
	 */
	public long getTimeMillisSinceStart();
}
