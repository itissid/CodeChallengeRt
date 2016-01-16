package me.itissid.model;

public class SystemTimeProvider implements ITimeProvider {

	private final long startTime;
	
	public SystemTimeProvider() {
		startTime = System.currentTimeMillis();
	}

	@Override
	public long getTimeMillisSinceStart() {
		long currentTime = System.currentTimeMillis();
		return currentTime - startTime;
	}

}
