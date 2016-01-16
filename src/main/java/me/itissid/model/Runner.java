package me.itissid.model;

/**
 * FIXME: Consider generating the expectedTimeTaken for this runner inside the class
 * for more realistic simulation?
 */
public class Runner {
	// The amount of time to complete the race
	private final long expectedTimeTaken;
	private boolean completed = false;

	
	public Runner(long expectedTimeTakenMillis) {
		this.expectedTimeTaken = expectedTimeTakenMillis;
	}

	public long timeTaken() {
		return expectedTimeTaken;
	}

	public boolean isCompleted() {
		return completed == true;
	}

	public void setCompleted() {
		completed = true;
	}

	public String toString() {
		return new String(expectedTimeTaken+"("+completed+")");
	}
}
