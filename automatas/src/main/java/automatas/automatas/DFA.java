package automatas.automatas;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import automatas.utils.Tupla;

/* Implements a DFA (Deterministic Finite Automaton).
 */
public class DFA extends FA {

    /*
     * 	Construction
     */


    // Constructor


    public DFA(StateSet states, Alphabet alphabet,
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
                delta.get(t.first()).put(t.second(),new StateSet().addState(t.third()));
            }
        }
        catch(AutomatonException e) {
            e.printStackTrace();
        }
        //assert this.repOk(); If we want to break the repOk this will throw an assertion error
    }


    public DFA(StateSet states, Alphabet alphabet, HashMap<State,HashMap<Character,StateSet>> delta) {
        this.states = states;
        this.alphabet = alphabet;
        this.delta = delta;
    }

    /*
     *	State querying
     */



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
                    if(delta.get(s).get(c)!= null) {
                        if(delta.get(s).get(c).size() > 1) {
                            return false; /*If a State-Hash size is bigger than 1, there are non-deterministic transitions.*/
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Returns a new automaton with a complete definition
     * of the delta.
     * @return a new DFA with a complete Delta.
     */
    private DFA completeDelta() {
        State errorState = new State("error",false,false);
        StateSet newStates = this.states.clone();
        StateSet errorStateSet = new StateSet();
        try {
            newStates.addState(errorState);
            errorStateSet.addState(errorState);
        }
        catch(AutomatonException e) {
            e.printStackTrace();
            return null;
        }
        HashMap<State,HashMap<Character,StateSet>> completeDelta = new   HashMap<State,HashMap<Character,StateSet>>();
        for(State s : newStates) {
            completeDelta.put(s,new HashMap<Character,StateSet>());
            for(Character c : alphabet.getSymbols()) {
                if(!delta.containsKey(s) || !delta.get(s).containsKey(c)) {
                    completeDelta.get(s).put(c,errorStateSet.clone());
                }
                else {
                    completeDelta.get(s).put(c,delta(s,c).clone());
                }
            }
        }
        return new DFA(newStates,alphabet,completeDelta);
    }

    /**
     * Return a new automaton wich recognizes the union of
     * two given automatons.
     *
     * @param other The second Automaton.
     * @return a new DFA accepting the union of two languages.
     */
    public DFA union(DFA other) {
        /*Initializations*/
        assert repOk();
        assert other.repOk();
        assert getAlphabet().equals(other.getAlphabet());
        State thisInitial = new State(null,false,false);
        State otherInitial = new State(null,false,false);
        StateSet unionStates = new StateSet();
        State q0 = new State("new Initial",true,false);
        try {
            unionStates.addState(q0);
        }
        catch(AutomatonException e) {
            e.printStackTrace();
            return null;
        }
        Set<Tupla<State,Character,State>> unionTransitions = new HashSet<Tupla<State,Character,State>>();

        for(State s : states) {
            try {
                /*Mark the states of the first automaton*/
                unionStates.addState("Aut1"+s.getName(),false,s.isFinal());
            }
            catch(AutomatonException e) {
                e.printStackTrace();
                return null;
            }
            if(s.isInitial()) {
                /*If it's initial, we save it*/
                thisInitial = new State("Aut1"+s.getName(),false,s.isFinal());
            }
            for(Character c : alphabet.getSymbols()) {
                if(delta.get(s) != null && delta.get(s).get(c) != null) {
                    for(State st : delta(s,c)) {
                        /*Put the transitions into a new set of transitions*/
                        unionTransitions.add(new Tupla<State,Character,State>(new State("Aut1"+s.getName(),false,s.isFinal()),c,new State("Aut1"+st.getName(),false,st.isFinal())));
                    }
                }
            }
        }
        /*Idem as above*/
        for(State s : other.states) {
            try {
                unionStates.addState("Aut2"+s.getName(),false,s.isFinal());
            }
            catch(AutomatonException e) {
                e.printStackTrace();
                return null;
            }
            if(s.isInitial()) {
                otherInitial = new State("Aut2"+s.getName(),false,s.isFinal());
            }
            for(Character c : other.alphabet.getSymbols()) {
                if(other.delta.get(s) != null && other.delta.get(s).get(c) != null) {
                    for(State st : other.delta(s,c)) {
                        unionTransitions.add(new Tupla<State,Character,State>(new State("Aut2"+s.getName(),false,s.isFinal()),c,new State("Aut2"+st.getName(),false,st.isFinal())));
                    }
                }
            }
        }
        unionTransitions.add(new Tupla<State,Character,State>(q0,null,thisInitial));
        unionTransitions.add(new Tupla<State,Character,State>(q0,null,otherInitial));
        /*Create the NFA-Automaton, then convert it into a DFA*/
        return new NFALambda(unionStates,alphabet,unionTransitions).toDFA();
    }

    /**
     * Returns a new automaton which recognizes the complementary
     * language.
     *
     * @return a new DFA accepting the language's complement.
     */
    public DFA complement() { /*We don't need the "other" param.*/
        assert repOk();
        DFA completeAutomaton = this.completeDelta();
        for(State s : completeAutomaton.getStates()) {
            s.setFinal(!s.isFinal());
            for(Character c : alphabet.getSymbols()) {
                StateSet complementedStateSet = completeAutomaton.delta(s,c).clone();
                for (State st : complementedStateSet) {
                    st.setFinal(!st.isFinal());
                }
                completeAutomaton.delta.get(s).put(c,complementedStateSet);
            }
        }
        return completeAutomaton;
    }


    /**
     * Returns a new automaton which recognizes the intersection of both
     * languages, the one accepted by 'this' and the one represented
     * by 'other'.
     *
     * @pre The two automata use the same alphabet and both deltas are complete.
     * @returns a new DFA accepting the intersection of both languages.
     */
    public DFA intersection(DFA other) {
        assert repOk();
        assert other.repOk();
        assert getAlphabet().equals(other.getAlphabet());
        return this.complement().union(other.complement()).complement();
    }
}
