package automatas.automatas;
import static org.junit.Assert.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import automatas.utils.DotReader;
import automatas.utils.Tupla;

public class NFATest {

  	 private static StateSet s;
  	 private static Alphabet a;
  	 private static Set<Tupla<State,Character,State>> t;



  	@BeforeClass
  	public static void setUpBeforeClass() throws Exception{
  		DotReader dotReader = new DotReader("src/test/java/automatas/nfa1");
  		dotReader.parse();

  		s = dotReader.getNodes();
  		a = dotReader.getSymbols();
  		t = dotReader.getArcs();
  	}

    @Test
    public void testAccept(){
      NFA nfa = new NFA(s,a,t);
      assertTrue(nfa.accepts("ab"));
    }

    @Test
    public void testNoAccept(){
      NFA nfa = new NFA(s,a,t);
      assertFalse(nfa.accepts("baaaaa"));
    }


    @Test
    public void testRepOk(){
      NFA nfa = new NFA(s,a,t);
      assertTrue(nfa.repOk());
    }


}
