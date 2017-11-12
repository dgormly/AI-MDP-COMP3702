package problem;

import solver.Action;
import solver.State;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * COMP3702 A3 2017 Support Code
 * v1.0
 * last updated by Nicholas Collins 19/10/17
 */

public class VentureManager {
	
	/** Name of customer level for this Venture Manager */
	private String name;
	/** Number of ventures to be managed */
	private int numVentures;
	/** Maximum amount of manufacturing funding across all ventures (x$10 000) */
	private int maxManufacturingFunds;
	/** Maximum amount of funding which can be added to a venture in 1 fortnight (x$10 000) */
    private int maxAdditionalFunding;
	/* Map containing all possible states and corresponding iteration value. */
	private Map<State, Integer> stateMap;
	/* Maps additional funding value to states when loading the statemap. */
	private List<Action> actionList;

	/**
	 * Constructor
	 * @param name
	 * @param maxManufacturingFunds
	 * @param numVentures
	 */
	public VentureManager(String name, int maxManufacturingFunds, int maxAdditionalFunding, int numVentures) {
		this.name = name;
		this.maxManufacturingFunds = maxManufacturingFunds;
		this.maxAdditionalFunding = maxAdditionalFunding;
		this.numVentures = numVentures;

		// Load all states into map.
		List<State> states = State.getAllStates(maxManufacturingFunds, numVentures);
		stateMap = new HashMap<>();

		for (State state : states) {
			stateMap.put(state, 0);
		}
	}
	
	/**
	 * Constructor
	 * @param name Takes values bronze, silver, gold or platinum
	 */
	public VentureManager(String name) {
		this.name = name;
		if (name.equals("bronze")) {
			numVentures = 2;
			maxManufacturingFunds = 3;
			maxAdditionalFunding = 3;
		} else if (name.equals("silver")) {
			numVentures = 2;
			maxManufacturingFunds = 5;
			maxAdditionalFunding = 4;
		} else if (name.equals("gold")) {
			numVentures = 3;
			maxManufacturingFunds = 6;
			maxAdditionalFunding = 4;
		} else if (name.equals("platinum")) {
			numVentures = 3;
			maxManufacturingFunds = 8;
			maxAdditionalFunding = 5;
		} else {
			throw new IllegalArgumentException("Invalid customer level.");
		}

		// Setup valid actions
		actionList = new ArrayList<>();

		// Load all states into map.
		List<State> states = State.getAllStates(maxManufacturingFunds, numVentures);
		stateMap = new HashMap<>();

		for (State state : states) {
			stateMap.put(state, 0);

			if (state.getFunding() <= maxAdditionalFunding) {
				actionList.add((Action) state);
			}
		}

	}

	public String getName() {
		return name;
	}

	public int getMaxManufacturingFunds() {
		return maxManufacturingFunds;
	}
	
	public int getMaxAdditionalFunding() {
	    return maxAdditionalFunding;
	}

	public int getNumVentures() {
		return numVentures;
	}

	public List<Action> getActions() {
		return actionList;
	}

	public State getNextState(State currentState, State action) {
		Integer[] state = new Integer[numVentures];
		for (int i = 0; i < numVentures; i++) {
			int c = currentState.getVenture(i);
			int a = action.getVenture(i);

			if (c + a >= maxManufacturingFunds) {
				state[i] = c + a;
			} else {
				return null;
			}
		}
		return new State(state);
	}


}
