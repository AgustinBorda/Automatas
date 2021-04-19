package automatas.searchPatterns;

import java.util.*;

import automatas.automatas.*;
import automatas.utils.Tupla;

public class PatternList {

	/*
	This list stores deterministic finite automata,
	each of them represents a pattern of characters whose occurrence
	we want to check in a given text file (in hexadecimal format)
	 * */
	private List<DFA> patterns;

	private DFA patternsDFA;

	private static Set<Character>  sigma = new HashSet<Character>(Arrays.asList('0','1','2','3','4','5','6','7','8','9', 'a', 'b', 'c', 'd', 'e' , 'f'));


	public PatternList(List<DFA> list) throws AutomatonException {
		this.patterns = list;
		try {
			patternsDFA =buildAutomaton();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Builds and returns the automaton that recognizes 
	 * any of the patterns in the pattern list
	 * @return the automaton that recognizes 
	 * any of the patterns in the pattern list
	 * */
	private DFA buildAutomaton() throws AutomatonException {
		return this.completeDeltaPatternRecognizer(this.forAllUnion());
	}


	/**
	 * This method completes the delta of aut
	 * @param aut The automaton that we will complete the delta.
	 * @return aut with the complete delta.
	 */
	private DFA completeDeltaPatternRecognizer(DFA aut) {
		HashMap<State, HashMap<Character, StateSet>> newDelta = new HashMap<State, HashMap<Character, StateSet>>();
		for (State s : aut.getStates()) {
			newDelta.put(s, new HashMap<Character, StateSet>());
			for (Character c : aut.getAlphabet().getSymbols()) {
				HashSet<State> set = new HashSet<>();
				if ((!aut.getDeltaFunction().containsKey(s) || !aut.getDeltaFunction().get(s).containsKey(c)) && s.isFinal()) {
					set.add(s);
					newDelta.get(s).put(c, new StateSet(set));
				} else {
					if ((!aut.getDeltaFunction().containsKey(s) || !aut.getDeltaFunction().get(s).containsKey(c)) && !s.isFinal()) {
						set.add(aut.initialState());
						newDelta.get(s).put(c, new StateSet(set));
					} else {
						StateSet d = aut.delta(s,c);
						newDelta.get(s).put(c, aut.delta(s,c));
					}
				}
			}
		}
		return new DFA(aut.getStates(), aut.getAlphabet(), newDelta);
	}

	/**
	 * This method returns the automaton union
	 * of the automata contained in the Patterns list
	 * */
	private DFA forAllUnion() throws AutomatonException {
		if(patterns.size() == 0)
			return new DFA(new StateSet(),new Alphabet((HashSet<Character>)sigma),new HashSet<Tupla<State, Character, State>>());
		DFA	patSigma = new DFA(patterns.get(0).getStates(),new Alphabet((HashSet<Character>)sigma),patterns.get(0).getDeltaFunction());
		DFA partialResult = patSigma;
		for(DFA pat : patterns) {
			if(pat == null)
				throw new AutomatonException();
			patSigma = new DFA(pat.getStates(),new Alphabet((HashSet<Character>)sigma),pat.getDeltaFunction());
			partialResult = partialResult.union(patSigma);
		}
		return partialResult;
	}


	/* @return true if any of the patterns in
	 * the pattern list occurs in the given string,
	 * false in otherwise
	 * @param line: string to be  scanned.
	 * */
	public boolean scan(String line) throws AutomatonException {
		if (patternsDFA ==null)
			throw new AutomatonException();
		return patternsDFA.accepts(line);
	}


}
