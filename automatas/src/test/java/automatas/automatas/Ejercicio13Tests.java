package automatas.automatas;
import static org.junit.Assert.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import automatas.utils.DotReader;
import automatas.utils.Tupla;

public class Ejercicio13Tests {

	 private static StateSet s;
	 private static Alphabet a;
	 private static Set<Tupla<State,Character,State>> t;
	 private static StateSet s2;
	 private static Alphabet a2;
	 private static Set<Tupla<State,Character,State>> t2;


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DotReader dotReader = new DotReader("src/test/java/automatas/Ejercicio13Automata1");
		dotReader.parse();

		s = dotReader.getNodes();
		a = dotReader.getSymbols();
		t = dotReader.getArcs();

		dotReader = new DotReader("src/test/java/automatas/Ejercicio13Automata2");
		dotReader.parse();

		s2 = dotReader.getNodes();
		a2= dotReader.getSymbols();
		t2 = dotReader.getArcs();
	}

  @Test
  public void testAccept1() {
    DFA dfa = new DFA(s,a,t);
    assertTrue(dfa.accepts("X5800ABC"));
  }

  @Test
  public void testAccept2() {
    DFA dfa = new DFA(s,a,t);
    assertTrue(dfa.accepts("B1234ABC"));
  }

  @Test
  public void testAccept3() {
    DFA dfa = new DFA(s,a,t);
    assertTrue(dfa.accepts("C1234ABC"));
  }

  @Test
  public void testNoAccept() {
    DFA dfa = new DFA(s,a,t);
    assertFalse(dfa.accepts("O1234AER"));
  }

  @Test
  public void testAccept4() {
    DFA dfa = new DFA(s,a,t);
    assertTrue(dfa.accepts("Z8975WQE"));
  }

  @Test
  public void testAccept5() {
    DFA dfa = new DFA(s,a,t);
    assertTrue(dfa.accepts("A0000AAA"));
  }

  @Test
  public void testNoAccept2() {
    DFA dfa = new DFA(s,a,t);
    assertFalse(dfa.accepts("H01AVA13"));
  }

  @Test
  public void testNoCordobaBuenosAires() {
    DFA dfa = new DFA(s,a,t);
    DFA dfa2 = new DFA(s2,a2,t2);
    assertTrue(dfa.intersection(dfa2).accepts("A1234ABC"));
  }

  @Test
  public void testNoCordobaBuenosAires1() {
    DFA dfa = new DFA(s,a,t);
    DFA dfa2 = new DFA(s2,a2,t2);
    assertTrue(dfa.intersection(dfa2).accepts("T1234ABC"));
  }

  @Test
  public void testNoCordobaBuenosAires2() {
    DFA dfa = new DFA(s,a,t);
    DFA dfa2 = new DFA(s2,a2,t2);
    assertFalse((dfa2.intersection(dfa2)).accepts("X1234ABC"));
  }

  @Test
  public void testNoCordobaBuenosAires3() {
    DFA dfa = new DFA(s,a,t);
    DFA dfa2 = new DFA(s2,a2,t2);
    assertFalse(dfa.intersection(dfa2).accepts("B1234AAA"));
  }

  @Test
  public void testNoCordobaBuenosAires4() {
    DFA dfa = new DFA(s,a,t);
    DFA dfa2 = new DFA(s2,a2,t2);
    assertTrue(dfa.intersection(dfa2).accepts("Z1234ABC"));
  }

  @Test
  public void testNoCordobaBuenosAires5() {
    DFA dfa = new DFA(s,a,t);
    DFA dfa2 = new DFA(s2,a2,t2);
    assertTrue(dfa.intersection(dfa2).accepts("Q0000OOO"));
  }

  @Test
  public void testNoCordobaBuenosAires6() {
    DFA dfa = new DFA(s,a,t);
    DFA dfa2 = new DFA(s2,a2,t2);
    assertFalse(dfa.intersection(dfa2).accepts("X0000FGH"));
  }

  @Test
  public void testNoCordobaBuenosAires7() {
    DFA dfa = new DFA(s,a,t);
    DFA dfa2 = new DFA(s2,a2,t2);
    assertTrue(dfa.intersection(dfa2).accepts("Q1234ABC"));
  }

  @Test
  public void testNoCordobaBuenosAires8() {
    DFA dfa = new DFA(s,a,t);
    DFA dfa2 = new DFA(s2,a2,t2);
    assertFalse(dfa.intersection(dfa2).accepts(""));
  }

  @Test
  public void testNoCordobaBuenosAires9() {
    DFA dfa = new DFA(s,a,t);
    DFA dfa2 = new DFA(s2,a2,t2);
    assertTrue(dfa.intersection(dfa2).accepts("A0001QEW"));
  }
}
