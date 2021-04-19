package automatas.automatas;

import java.util.HashSet;
import java.util.Set;

/**
 * This class implements the set of symbols of an automaton
 *
 *
 * **/


public class Alphabet {

	private Set<Character> symbols;


	public Alphabet() {
		symbols = new HashSet<Character>();
	}

	public Alphabet(HashSet<Character> s) {
		symbols = s;
	}

	@Override
	public boolean equals(Object other) {
		return symbols.equals(((Alphabet)other).symbols);
	}

	/**
	 * Adds the specified symbol to this alphabet if it is not already present.
	 * @param c a symbol to be added to the alphabet
	 * @return true if c was added to the alphabet, false in other case
	 * **/
	public boolean addSymbol(Character c) {
		return symbols.add(c);
	}

	/**
	 * Removes the specified symbol from this alphabet if it is  already present.
	 * @param c a symbol to be removed from the alphabet
	 * @return true if c was removed from the alphabet, false in other case
	 * **/
	public boolean removeSymbol(Character c) {
		return symbols.remove(c);
	}

	public Set<Character> getSymbols() {
		return symbols;
	}


	/**
	 * Returns true if this alphabet contains the specified symbol.
	 * @param c a symbol that we want to check if it belongs to the set or not
	 * @return true if c belong to this alphabet
	 * **/
	public boolean belongTo(Character c) {
		return symbols.contains(c);
	}

	@Override
	public String toString() {
		return symbols.toString();
	}



}
