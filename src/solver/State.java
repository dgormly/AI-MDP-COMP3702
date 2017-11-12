package solver;

import java.util.*;

public class State {

    protected Integer[] ventureStates;
    private static List<State> allStates;
    private int sum = 0;


    /**
     * Holds the state of all ventures.
     *
     * @param funding
     *      Initial state.
     */
    public State(Integer[] funding) {
        ventureStates = funding;

        for (Integer i : funding) {
            sum += i;
        }
    }


    /**
     * The total amount of funding across all ventures.
     *
     * @return
     *      The total sum.
     */
    public int getFunding() {
        return sum;
    }


    /**
     * Returns the state of a given venture.
     *
     * @param ventureNum
     *      Venture index to return starting from zero.
     * @return
     *      Venture state.
     */
    public int getVenture(int ventureNum) {
        return ventureStates[ventureNum];
    }

    /**
     * Returns all state permutations for a given number of ventures with a maximum funding.
     *
     * Runtime efficiency: O(n)
     *
     * @param maxFunding
     *      The maximum value allowed for each venture.
     * @param numVentures
     *      Number of ventures.
     * @return
     *      List of permutations.
     */
    public static List<State> getAllStates(int maxFunding, int numVentures) {

        if (allStates != null) {
            return allStates;
        }

        List<Integer[]> tempList = new ArrayList<>();

        // Get base
        for (int i = 0; i <= maxFunding; i++) {
            Integer[] temp = new Integer[numVentures];
            Arrays.fill(temp, 0);
            temp[0] = i;
            tempList.add(temp);
        }

        // increment there-after
        for (int i = 1; i < numVentures; i++) {
            sum(tempList, i, maxFunding);
        }

        // Convert to states.
        List<State> list = new ArrayList<>();
        for (Integer[] state : tempList) {
            State s = new State(state);
            list.add(s);
        }

        allStates = list;
        return list;
    }


    /**
     * Don't touch this! Too hard to explain but is used to work out all permutations.
     *
     * @param list
     * @param ventureNum
     * @param maxFunding
     * @return
     */
    private static List<Integer[]> sum(List<Integer[]> list, int ventureNum, int maxFunding) {
        List<Integer[]> finalList = new ArrayList<>();
        // Get all values from given list, copy and increment the column.

        for (int i = 1; i <= maxFunding; i++) {
            for (Integer[] state : list) {
                Integer[] s = state.clone();
                s[ventureNum] = i;
                finalList.add(s);
            }
        }

        list.addAll(finalList);
        return list;
    }


    /**
     * Returns the next state given a current state, action, and maximum funding.
     *
     * @param currentState
     *      Current state to find the next state of.
     * @param action
     *      Action applied to the current state.
     * @param maxFunding
     *      The upperbound that each venture is allowed to have.
     * @return
     *      Next State given the action. If the action is invalid the function will return null.
     */
    public static State getNextState(State currentState, Action action, int maxFunding) {
        Integer[] state = new Integer[currentState.ventureStates.length];
        for (int i = 0; i < currentState.ventureStates.length; i++) {
            int c = currentState.getVenture(i);
            int a = action.getVenture(i);

            if (c + a <= maxFunding) {
                state[i] = c + a;
            } else {
                return null;
            }
        }
        return new State(state);
    }

}
