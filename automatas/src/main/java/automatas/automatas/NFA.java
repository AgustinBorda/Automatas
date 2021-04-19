package automatas.automatas;

import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

import automatas.utils.Tupla;

public class NFA extends FA {

	/*
	 *  Construction
	*/

	// Constructor

		public NFA(StateSet states, Alphabet alphabet,
				Set<Tupla<State,Character,State>> transitions)
	      {
					this.states = states;
					this.alphabet = alphabet;
					this.delta = new HashMap<State,HashMap<Character,StateSet>>();
	        try{
	          for (Tupla<State,Character,State> t: transitions) {
	            /*If the State isn't on the delta, we put the state in it (with the transition)*/
	            if (!delta.containsKey(t.first())) {
	              delta.put(t.first(), new HashMap<Character,StateSet>());
	            }
							if(!delta.get(t.first()).containsKey(t.second())) {
								 delta.get(t.first()).put(t.second(),new StateSet());
							}
	            delta.get(t.first()).get(t.second()).addState(t.third());
	          }
	        }
	        catch(AutomatonException e) {
	          e.printStackTrace();
	        }
					//assert this.repOk(); If we want to break the repOk this will throw an assertion error
				}




	/*
	 *  Automata methods
	*/


	@Override
	public boolean accepts(String string) {
		assert repOk();
		assert string != null;
		assert verifyString(string);
		return acceptsWithState(this.initialState(),string);
	}

	private boolean acceptsWithState(State s, String string) {
		if(string.length() == 0) {
			return s.isFinal(); //If the string is consumed, return if the current state is final
		}
		else {
			boolean result = false;
      String newString = string.substring(1);
      for(State st:delta(s,string.charAt(0))) {
        result = result || this.acceptsWithState(st,newString); /*Else we call with the succesor state of each habilited transition and the string without the first element*/
      }
			return result;
		}
	}

	@Override
	public boolean repOk() {

		for(State s:states) {
			if(s.isInitial() && !this.initialState().equals(s)) {
				return false; //If are two diferent initial States, the repOk is broken.
			}
			if(states.belongTo(s.getName()) == null) {
				return false; //If a state in the automata isn't in the state Set, the repOk is broken.
			}
      if(delta.get(s)!=null) {
        if(delta.get(s).containsKey(null)) {
          return false; /*If a transition key is null, the transition is a lambda-transition.*/
        }
				for(Character c: delta.get(s).keySet()) {
					if(!alphabet.belongTo(c)) {
						return false;
					}
				}
      }
		}
		return true;
	}

}
