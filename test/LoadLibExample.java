package test;

import java.util.Date;

import luajava.JavaFunction;
import luajava.LuaException;
import luajava.LuaState;

/**
 * Example of a library in Java openned in Lua using loadLib 
 * 
 * @author Thiago Ponte
 */
public class LoadLibExample
{
  private final static String LIB_NAME = "eg";
  
  private static void getLibTable(LuaState L)
  {
    L.getGlobal(LIB_NAME);
    if (L.isNil(-1))
    {
      L.pop(1);
      L.newTable();
      L.pushValue(-1);
      L.setGlobal(LIB_NAME);
    }
  }

  /**
   * Method called by loadLib
   */
  public static void open(LuaState L) throws LuaException
  {
    getLibTable(L);

    L.pushString("example");

    L.pushJavaFunction(new JavaFunction(L) {
      /**
       * Example for loadLib.
       * Prints the time and the first parameter, if any.
       */
      public int foo() throws LuaException
      {
        System.out.println(new Date().toString());
        
        if (L.getTop() > 1)
        {
          System.out.println(getParam(2));
        }

        return 0;
      }
    });
  
    L.setTable(-3);
    
    L.pop(1);
  }
}