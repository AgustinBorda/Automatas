package automatas.automatas;
import static org.junit.Assert.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import automatas.utils.DotReader;
import automatas.utils.Tupla;

public class DFAAutomatonMethodsTests2 {


	private static StateSet s;
	 private static Alphabet a;
	 private static Set<Tupla<State,Character,State>> t;



	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		DotReader dotReader = new DotReader("src/test/java/automatas/dfa1");
		dotReader.parse();

		s = dotReader.getNodes();
		a = dotReader.getSymbols();
		t = dotReader.getArcs();
	}

	// Tests for DFA
	@Test
	public void testBrokenRepOk(){
		DFA dfa = new DFA(s,a,t);
		assertFalse(dfa.repOk());
	}

}
