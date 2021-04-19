 package automatas.automatas;


import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import automatas.utils.Tupla;

public abstract class FA {

	protected StateSet states;

	protected Alphabet alphabet;


	/**All states used in delta function
	* must belong to the automaton states  set
	* and all labels must belong to the automaton alphabet
	**/
	protected HashMap<State,HashMap<Character,StateSet>> delta;


	/**
	 * @return the atomaton's set of states.
	 */
	public StateSet getStates() {
		return states;
	}


	/**
	 * @return the atomaton's alphabet.
	 */
	public Alphabet getAlphabet() {

		return alphabet;

	}

	/**
	 * @return the automaton's delta function.
	 */
	public HashMap<State,HashMap<Character,StateSet>> getDeltaFunction() {
		return delta;
	}

	/**
	 * @return the atomaton's initial state.
	 */
	public State initialState() {
		for(State s:this.states) {
      if(s.isInitial()) {
        return s;
      }
    }
		return null;
	}

	/**
	 * @return the atomaton's final states.
	 */
	public StateSet finalStates() {
    StateSet result = new StateSet();
    for(State s:this.states) {
      if(s.isFinal()) {
        try {
          result.addState(s.getName(),s.isInitial(),true); //the last is true because it needs to be true to reach this line
        }
        catch(AutomatonException e) {
          System.out.println(e);
          System.exit(1);
        }
      }
    }
    return result;
	}

	/**
	 * Query for the automaton's transition function.
	 *
	 * @return A set of states (when FA is a DFA this method return a
	 * singleton set) corresponding to the successors of the given state
	 * via the given label according to the transition function.
	 */
	public  StateSet delta(State from, Character label) {
		assert states.belongTo(from.getName())!=null;
		assert alphabet.belongTo(label) || label == null ;
    StateSet result = new StateSet();
    try {
      if (delta.get(from) != null) {
        if(delta.get(from).get(label) != null) {
          for(State s:delta.get(from).get(label)) {
            result.addState(s.getName(),s.isInitial(), s.isFinal());
          }
        }
      }
    }
    catch(AutomatonException e) {
      return null;
    }
    return result;
	}

	/**
	 * Verifies whether the string is composed of characters in the alphabet of the automaton.
	 * @return True iff the string consists only of characters in the alphabet.
	 */
	public boolean verifyString(String s) {
    int stringLength = s.length();
		for(int i=0;i<stringLength; i++) {
      if(!alphabet.belongTo(s.charAt(i))) {
        return false;
      }
    }
		return true;
	}


	/**
	 * TODO: Abstract methods to be implements for the subclasses
	 * **/

	/**
	 * @return True iff the automaton is in a consistent state.
	 */
	public abstract boolean repOk();



	/**
	 * Tests whether a string belongs to the language of the current
	 * finite automaton.
	 *
	 * @param string String to be tested for acceptance.
	 * @return Returns true iff the current automaton accepts the given string.
	 */
	public abstract boolean accepts(String string);

}
