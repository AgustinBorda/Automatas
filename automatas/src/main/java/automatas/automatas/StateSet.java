package automatas.automatas;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class StateSet implements Iterable<State>{

	private Set<State> states;

	public StateSet() {
		states = new HashSet<State>();
	}

	public StateSet(Set<State> s) {
		states = new HashSet<State>();
		for (State st : s) {
			states.add(st);
		}
	}

	public StateSet addState(State s) throws AutomatonException {
		if(s.getName() == null || s.getName() =="")
			throw new AutomatonException("Node name invalid");

		if(!states.contains(s)) {
			states.add(s);
		}

		return this;
	}

	public State addState(String name)throws AutomatonException {
		if(name == null || name =="")
			throw new AutomatonException("Node name invalid");

		for(State f : states) {
			if(f.getName().equals(name)) {
				return f;
			}
		}
		State freshState = new State(name, false, false);
		states.add(freshState);
		return freshState;
	}

	public State addState(String name, boolean isInitial, boolean isFinal) throws AutomatonException {
		if(name == null || name =="")
			throw new AutomatonException("Node name invalid");

		for(State f : states) {
			if(f.getName().equals(name)) {
				f.setInitial(isInitial);
				f.setFinal(isFinal);
				return f;
			}
		}
		State freshState = new State(name, isInitial, isFinal);
		states.add(freshState);
		return freshState;
	}

	public void deleteState(String name) {
		for(State f : states) {
			if(f.getName().equals(name)) {
				states.remove(f);
				return;
			}
		}
	}

	public State belongTo(String name) {
		for(State f : states) {
			if(f.getName().equals(name)) {
				return f;
			}
		}
		return null;
	}

	public int size() {
		return states.size();
	}

	public StateSet clone() {
		StateSet result = new StateSet();
		for(State s: states) {
			result.states.add(new State(s.getName(),s.isInitial(),s.isFinal()));
		}
		return result;
	}

	@Override
	public String toString() {
		return states.toString();
	}


	@Override
	  public Iterator<State> iterator() {
	        return states.iterator();
	  }

		public State mergeIntoState() {
			boolean isInitial = false;
			boolean isFinal = false;
			String name = "";
			for (State s : states) {
					isInitial = isInitial || s.isInitial();
					isFinal = isFinal || s.isFinal();
					name += s.getName() + ";";
			}
			return new State(name,isInitial,isFinal);
		}

}
