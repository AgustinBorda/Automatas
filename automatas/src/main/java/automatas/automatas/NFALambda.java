package automatas.automatas;

import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import automatas.utils.Tupla;

public class NFALambda extends FA {

	/*
	 *  Construction
	*/

	// Constructor
	public NFALambda(StateSet states,	Alphabet alphabet,
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
		return acceptsWithState(this.initialState(),string);
	}

	private boolean acceptsWithState(State s, String string) {
		boolean result = false;
		if(string.length() == 0) { //if the string is consumed
			HashSet<State> visitedStates = new HashSet();
			visitedStates.add(s);
			return isFinalStateLambdaReachable(s,visitedStates);
		}
		else {
			String newString = string.substring(1);
			for(State st : delta(s,string.charAt(0))) {
				result = result || this.acceptsWithState(st,newString); /*Call the normal transitions*/
			}
			for(State st : delta(s,null)) {
				result = result || this.acceptsWithState(st,string); /*call the lambda-transitions*/
			}
			return result;
		}
	}

	private boolean isFinalStateLambdaReachable(State s, Set<State> visitedStates) {
		boolean result =s.isFinal();
		for (State st: delta(s,null)) {
			if(!visitedStates.contains(st)) {
				visitedStates.add(st);
				result = result || isFinalStateLambdaReachable(st,visitedStates);
			}
		}
		return result;
	}

	private StateSet reachableLambdaStates(StateSet s) {
		StateSet newS = s.clone();
		for(State st : s) {
			for(State sta : delta(st,null)) {
				try{
					newS.addState(sta.getName(),sta.isInitial(),sta.isFinal());
				}
				catch(AutomatonException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return newS;
	}

	private StateSet reachableStates(StateSet s,Character c) {
		StateSet reachableStates = new StateSet();
		for(State st : s) {
			for(State sta : delta(st,c)) {
				try{
					reachableStates.addState(sta.getName(),sta.isInitial(),sta.isFinal());
				}
				catch(AutomatonException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return reachableStates;
	}


	public DFA toDFA() {
		StateSet initialStateSet = new StateSet();

		boolean done = false;
		try {
			initialStateSet.addState(initialState().getName(),true,initialState().isFinal());
		}
		catch(AutomatonException e) {
			e.printStackTrace();
			return null;
		}
		Tupla<StateSet,Alphabet,Set<Tupla<State,Character,State>>> params = generateParamsDFAInitial(initialStateSet,alphabet,new StateSet());
		return new DFA(params.first(),params.second(),params.third());
	}

	private Tupla<StateSet,Alphabet,Set<Tupla<State,Character,State>>> generateParamsDFAInitial(StateSet initial, Alphabet a, StateSet visited) {
		initial = this.reachableLambdaStates(initial);
		StateSet end;
		Set<Tupla<State,Character,State>> transitions = new HashSet<Tupla<State,Character,State>>();
		try {
			visited.addState(initial.mergeIntoState().getName(),true,initial.mergeIntoState().isFinal());
		}
		catch(AutomatonException e) {
			e.printStackTrace();
			return null;
		}
		for (Character c: a.getSymbols()) {
			end = reachableStates(initial,c);
			if(end.size() >0) {
				transitions.add(new Tupla<State,Character,State>(initial.mergeIntoState(),c,end.mergeIntoState()));
				if(visited.belongTo(end.mergeIntoState().getName()) == null) {
					Tupla<StateSet,Alphabet,Set<Tupla<State,Character,State>>> t = generateParamsDFA(end,a,visited);
					try {
						for(State s : t.first()) {
							visited.addState(s.getName(),s.isInitial(),s.isFinal());
						}
					}
					catch(AutomatonException e) {
						e.printStackTrace();
						return null;
					}
					transitions.addAll(t.third());
				}
			}
		}
		return new Tupla<StateSet,Alphabet,Set<Tupla<State,Character,State>>>(visited,a,transitions);
	}



	private Tupla<StateSet,Alphabet,Set<Tupla<State,Character,State>>> generateParamsDFA(StateSet initial, Alphabet a, StateSet visited) {
				initial = this.reachableLambdaStates(initial);
				StateSet end;
				Set<Tupla<State,Character,State>> transitions = new HashSet<Tupla<State,Character,State>>();
				try {
					visited.addState(initial.mergeIntoState().getName(),false,initial.mergeIntoState().isFinal());
				}
				catch(AutomatonException e) {
					e.printStackTrace();
					return null;
				}
				for (Character c: a.getSymbols()) {
					end = reachableStates(initial,c);
					if(end.size() >0) {
						transitions.add(new Tupla<State,Character,State>(initial.mergeIntoState(),c,end.mergeIntoState()));
						if(visited.belongTo(end.mergeIntoState().getName()) == null) {
							Tupla<StateSet,Alphabet,Set<Tupla<State,Character,State>>> t = generateParamsDFA(end,a,visited);
							try {
								for(State s : t.first()) {
									visited.addState(s.getName(),s.isInitial(),s.isFinal());
								}
							}
							catch(AutomatonException e) {
								e.printStackTrace();
								return null;
							}
						transitions.addAll(t.third());
					}
				}
			}
			return new Tupla<StateSet,Alphabet,Set<Tupla<State,Character,State>>>(visited,a,transitions);
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
        boolean result = true;
				for(Character c: delta.get(s).keySet()) {
					if(!alphabet.belongTo(c) && c != null) {
						return false;
					}
				}
      }
		}
		return true;
	}

}
