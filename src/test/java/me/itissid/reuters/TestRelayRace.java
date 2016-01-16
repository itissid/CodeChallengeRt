package me.itissid.reuters;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import me.itissid.model.ITimeProvider;
import me.itissid.model.Team;

@RunWith(MockitoJUnitRunner.class)
public class TestRelayRace {
	private static final long[] TEAM_1_TIMES = new long[]{9000, 9005, 9100, 9050};
	private static final long[] TEAM_2_TIMES = new long[]{9020, 9000, 9400, 10050};

	private static long TOLERANCE = 8L;
	private List<Team> testTeams = new ArrayList<>();
	private RelayRace relayRace;
	
	@Mock
	private ITimeProvider provider;
	
	@Before
	public void setUp(){
		testTeams.add(Team.teamFromRandomTimes(TEAM_1_TIMES));
		testTeams.add(Team.teamFromRandomTimes(TEAM_2_TIMES));
		// Cumulative sums for the call to provider to get the times.
		when(provider.getTimeMillisSinceStart()).thenReturn(9000L, 9018L, 18007L, 18018L, 27102L, 27420L, 36150L, 376471L);
		relayRace = new RelayRace(provider, testTeams, TOLERANCE);
	}

	/**
	 * Test that as the clock ticks the Runners complete their race.
	 * We assert against the cumulative running time of the completed runners after every
	 * step
	 */
	@Test
	public void testRunStep() {
		// Assert that first set of runners completed there race
		// Run the race till the end
		for(int i = 0; i < 4; i ++){
			// simulate a step
			relayRace.runStep();
			List<Long> latestTimes = getCompletedTimes();
			assertThat(latestTimes.size(), equalTo(2));
			long team1ExpectedTime = IntStream.rangeClosed(0, i).mapToLong(l -> TEAM_1_TIMES[l]).sum();
			long team2ExpectedTime = IntStream.rangeClosed(0, i).mapToLong(l -> TEAM_2_TIMES[l]).sum();
			assertThat(latestTimes, equalTo(Arrays.asList(team1ExpectedTime, team2ExpectedTime)));
		}
	}

	private List<Long> getCompletedTimes() {
		return testTeams.stream().map(
				team -> team.getCompletedTimes()
			).collect(Collectors.toList());
	}

	
}
