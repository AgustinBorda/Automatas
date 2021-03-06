/*
 * This file was automatically generated by EvoSuite
 * Fri Mar 06 17:23:47 ART 2020
 */

package automatas.automatas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class StateSet_ESTest  {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      StateSet stateSet0 = new StateSet();
      State state0 = stateSet0.addState("Node name invalid", true, true);
      assertTrue(state0.isFinal());
      assertTrue(state0.isInitial());

      State state1 = stateSet0.addState("Node name invalid", true, false);
      assertSame(state1, state0);
      assertFalse(state1.isFinal());
      assertTrue(state1.isInitial());
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      StateSet stateSet0 = new StateSet();
      stateSet0.addState("J", true, true);
      State state0 = stateSet0.belongTo("J");
      assertNotNull(state0);
      assertTrue(state0.isFinal());
      assertTrue(state0.isInitial());
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      StateSet stateSet0 = new StateSet();
      stateSet0.addState("Node name invalid", true, true);
      State state0 = stateSet0.addState("Node name invalid");
      assertTrue(state0.isInitial());
      assertTrue(state0.isFinal());
  }

  @Test(timeout = 4000)
  public void test3()  throws Throwable  {
      StateSet stateSet0 = new StateSet();
      try {
        stateSet0.addState((String) null, false, false);
        fail("Expecting exception: AutomatonException");

      } catch(AutomatonException e) {
         //
         // Node name invalid
         //
      }
  }

  @Test(timeout = 4000)
  public void test4()  throws Throwable  {
      StateSet stateSet0 = new StateSet();
      try {
        stateSet0.addState((String) null);
        fail("Expecting exception: AutomatonException");

      } catch(AutomatonException e) {
         //
         // Node name invalid
         //
      }
  }

  @Test(timeout = 4000)
  public void test5()  throws Throwable  {
      StateSet stateSet0 = new StateSet();
      int int0 = stateSet0.size();
      assertEquals(0, int0);
  }
}
