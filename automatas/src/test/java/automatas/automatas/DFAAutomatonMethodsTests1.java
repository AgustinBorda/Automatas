package automatas.automatas;
import static org.junit.Assert.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import automatas.utils.DotReader;
import automatas.utils.Tupla;

public class DFAAutomatonMethodsTests1 {

	 private static StateSet s;
	 private static Alphabet a;
	 private static Set<Tupla<State,Character,State>> t;
	 private static StateSet s2;
	 private static Alphabet a2;
	 private static Set<Tupla<State,Character,State>> t2;




	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DotReader dotReader = new DotReader("src/test/java/automatas/dfa2");
		dotReader.parse();

		s = dotReader.getNodes();
		a = dotReader.getSymbols();
		t = dotReader.getArcs();

		dotReader = new DotReader("src/test/java/automatas/dfa6");
		dotReader.parse();

		s2 = dotReader.getNodes();
		a2= dotReader.getSymbols();
		t2 = dotReader.getArcs();
	}

	// Tests for DFA2

	@Test
	public void testRepOk() {
		DFA dfa = new DFA(s,a,t);
		assertTrue(dfa.repOk());
	}

	// Tests for DFA2

	@Test
	public void testAccept() {
		DFA dfa = new DFA(s,a,t);
		assertTrue(dfa.accepts("bbbbbb"));
	}

	@Test
	public void testNoAccept() {
		DFA dfa = new DFA(s,a,t);

		assertFalse(dfa.accepts("bbbbb"));
	}

	@Test
	public void testComplement1() {
		DFA dfa = new DFA(s,a,t);
		assertFalse(dfa.complement().accepts("bb"));
	}

	@Test
	public void testComplement2() {
		DFA dfa = new DFA(s,a,t);
		assertTrue(dfa.complement().accepts("b"));
	}

	@Test
	public void unionTest() {
		DFA dfa1 = new DFA(s,a,t);
		DFA dfa2 = new DFA(s2,a2,t2);
		DFA union = dfa1.union(dfa2);
		assertEquals(dfa1.accepts("bbbbbb"),union.accepts("bbbbbb"));
		assertEquals(dfa2.accepts("bbbbbbb"),union.accepts("bbbbbbb"));
	}

	@Test
	public void intersectionTest() {
		DFA dfa1 = new DFA(s,a,t);
		DFA dfa2 = new DFA(s2,a2,t2);
		DFA intersection = dfa1.intersection(dfa2);
		/*Intersection of complements == void*/
		assertFalse(intersection.accepts("bbbbbb"));
		assertFalse(intersection.accepts("bbbbbbb"));
	}

	@Test
	public void intersectionTest2() {
		DFA dfa1 = new DFA(s,a,t);
		DFA intersection = dfa1.intersection(dfa1);
		/*Intersection of complements == void*/
		assertTrue(intersection.accepts("bb"));
		assertFalse(intersection.accepts("bbbbbbb"));
	}

}
