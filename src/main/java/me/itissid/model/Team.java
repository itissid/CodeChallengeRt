package me.itissid.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Team {
	public static final int NUM_RELAY_MEMBERS = 4;
	private final List<Runner> runners;

	/**
	 * Use the factory method to get a team.
	 * @param numTeamMembers
	 */
	private Team(int numTeamMembers) {
		if(numTeamMembers != NUM_RELAY_MEMBERS) {
			throw new IllegalArgumentException("Must have 4 members in the team");
		}
		runners = new ArrayList<Runner>(numTeamMembers);
	}

	/**
	 * Given 4 times initialize the team.
	 * 
	 * For example [9000, 9101, 10000, 9800]. Runner 1 will have 9000 time, runner 2 will have 9101 and so on.
	 * @param times
	 * @return
	 */
	public static Team teamFromRandomTimes(long[] timesMillis) {
		Team team = new Team(timesMillis.length);
		for(int i = 0; i < timesMillis.length; i++) {
			team.runners.add(new Runner(timesMillis[i]));
		}
		return team;
	}
	public static Team teamFromRandomTimes(Collection<Long> timesMillis) {
		Team team = new Team(timesMillis.size());
		for(Long time: timesMillis) {
			team.runners.add(new Runner(time));
		}
		return team;
	}

	/**
	 * Return the latest runner who has not completed his leg. If all have completed
	 * their legs return null;
	 * @return a Runner object corresponding to the the latest runner in the team.
	 */
	public Runner getLatestRunner() {
		for(Runner r: runners){
			if(!r.isCompleted()) { 
				return r;
			}
		}
		return null;
	}

	
	/**
	 * For the runners that did complete, get the total time taken.
	 */
	public long getCompletedTimes() {
		// FIXME: chance of over flow.
		long completedTimes = 0; 
		for(Runner r: runners){
			if(r.isCompleted()) { 
				completedTimes += r.timeTaken();
			}
		}
		return completedTimes;
	}
}
