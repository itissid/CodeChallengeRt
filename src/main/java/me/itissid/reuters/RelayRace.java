package me.itissid.reuters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.LongStream.Builder;

import org.apache.commons.math3.random.RandomDataGenerator;

import me.itissid.model.ITimeProvider;
import me.itissid.model.Runner;
import me.itissid.model.SystemTimeProvider;
import me.itissid.model.Team;

/**
 * Assumptions: The 
 * @author sidharth
 *
 */
public class RelayRace implements IRelayRace {

	private final ITimeProvider provider;
	private final List<Team> teams;
	// Keep this low <= 10 millis to measure runner times accurately
	private final long toleranceMillis;
			
	public RelayRace(ITimeProvider provider, List<Team> teams, long toleranceMillis) {
		this.provider = provider;
		this.teams = teams;
		this.toleranceMillis = toleranceMillis;
		
	}
	
	@Override
	public void startRace() {
		while(!runStep()) {}
		System.out.println("Race completed!");
		
		List<Team> sortedCopy = new ArrayList<>();
		for(Team team: teams) {
			sortedCopy.add(team);
		}

		Collections.sort(sortedCopy, new Comparator<Team>(){

			@Override
			public int compare(Team o1, Team o2) {
				return ((int)(o1.getCompletedTimes() - o2.getCompletedTimes()));
			}
			
		});

		int teamNum = 0;
		String[] suffixes = new String[] { "st", "nd", "rd", "th" };
		for (Team team : sortedCopy) {
			System.out.println(
					String.format("%d%s Team %d  - %f seconds", 
							teamNum + 1, teamNum <= 3 ? suffixes[teamNum] : suffixes[3],
							teams.indexOf(team) + 1, 
							((double) (team.getCompletedTimes())) / 1000.));
			teamNum++;
		}
	}

	/**
	 * Runs an iteration of the race. If its completed return true.
	 * TODO: Consider making this part of the Interface
	 * @return true if all runners have completed.
	 */
	public boolean runStep() {
		boolean isCompleted = false; 
		for(Team team: teams) {
			Runner latestRunner = team.getLatestRunner();
			long timeSinceStart = provider.getTimeMillisSinceStart();
			long millisForOldRunners = team.getCompletedTimes();
			isCompleted = true;
			if(latestRunner != null) {
				isCompleted = false;
				long timeTakenAllRunners = latestRunner.timeTaken() + millisForOldRunners;
				long deltaMillis = Math.abs(timeTakenAllRunners - timeSinceStart);
				// FIXME: It maybe possible that the clock has skipped ahead, in the "near now"
				// case. Consider using System.nanoTime();
				// If the runner did not complete the race or time is within a small tolerance
				// set runner to complete.
				if(timeTakenAllRunners <= timeSinceStart || deltaMillis < toleranceMillis) {
					latestRunner.setCompleted();
				}
			}
		}
		return isCompleted;
	}
	
	public static void main(String[] args) {
		ITimeProvider defaultProvider = new SystemTimeProvider();
		List<Team> teams = new ArrayList<>();
		RandomDataGenerator gen = new RandomDataGenerator();
		for(int i = 0; i < 6 ; i++) {
			Builder timeBuilder = LongStream.builder();
			for(int j = 0; j < 4; j++) {
				timeBuilder.add(gen.nextLong(1000, 1005));
			}
			List<Long> times = timeBuilder.build().boxed().collect(Collectors.toList());
			teams.add(Team.teamFromRandomTimes(times));
		}
		RelayRace relayRace = new RelayRace(defaultProvider, teams , 10);
		relayRace.startRace();
	}

}
