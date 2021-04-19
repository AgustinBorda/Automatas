package automatas.automatas;
import static org.junit.Assert.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import automatas.utils.DotReader;
import automatas.utils.Tupla;

public class NFALambdaTest {

  	 private static StateSet s;
  	 private static Alphabet a;
  	 private static Set<Tupla<State,Character,State>> t;



  	@BeforeClass
  	public static void setUpBeforeClass() throws Exception {
  		DotReader dotReader = new DotReader("src/test/java/automatas/nfalambda1");
  		dotReader.parse();

  		s = dotReader.getNodes();
  		a = dotReader.getSymbols();
  		t = dotReader.getArcs();
  	}

    @Test
    public void testAccept() {
      NFALambda nfal = new NFALambda(s,a,t);
      assertTrue(nfal.accepts(""));
    }

    @Test
    public void testNoAccept() {
      NFALambda nfal = new NFALambda(s,a,t);
      assertFalse(nfal.accepts("aca"));
    }


    @Test
    public void testRepOk() {
      NFALambda nfal = new NFALambda(s,a,t);
      assertTrue(nfal.repOk());
    }

    @Test
    public void toDFATest() {
      NFALambda nfal = new NFALambda(s,a,t);
      assertEquals(nfal.accepts(""),nfal.toDFA().accepts(""));
    }

    @Test
    public void toDFATest2() {
      NFALambda nfal = new NFALambda(s,a,t);
      assertEquals(nfal.accepts("aca"),nfal.toDFA().accepts("aca"));
    }

}
