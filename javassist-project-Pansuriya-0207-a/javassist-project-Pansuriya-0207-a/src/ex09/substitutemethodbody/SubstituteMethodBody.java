package ex09.substitutemethodbody;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import util.UtilMenu;

public class SubstituteMethodBody extends ClassLoader {
   static final String WORK_DIR      = System.getProperty("user.dir");
   static final String INPUT_PATH    = WORK_DIR + File.separator + "classfiles";

   static String _L_ = System.lineSeparator();
   static String[] clazNames;
   
   public static void main(String[] args) throws Throwable {	// for overriding method
	   try {
			while (true) {
				UtilMenu.showMenuOptions();

				switch (UtilMenu.getOption()) {
				case 1:
					System.out.println("Enter Four Inputs (e.g.ComponentApp,move,1,0 or ServiceApp,fill,2,10");
					clazNames = UtilMenu.getArguments();
					
					if(clazNames.length == 4)
					{
					      SubstituteMethodBody s = new SubstituteMethodBody();
					      Class<?> c = s.loadClass("target." + clazNames[0]);
					      Method mainMethod = c.getDeclaredMethod("main", new Class[] { String[].class });
					      mainMethod.invoke(null, new Object[] { args });
					}
					else
						System.out.println("[WNG] Invalid input size.!!");
				}
			}
	   }catch (NotFoundException e) {
			e.printStackTrace();
		}catch (RuntimeException e) {
			System.out.println("[WNG] This method \"" + clazNames[0] + "\" has been modified.");
			e.printStackTrace();
		}
   }

   private ClassPool pool;

   public SubstituteMethodBody() throws NotFoundException {
      pool = new ClassPool().getDefault();	//.getDefault() for class frozen and allow change to class only once.
      pool.insertClassPath(new ClassClassPath(new java.lang.Object().getClass()));	// Object is used to get classpath info
      pool.insertClassPath(INPUT_PATH); // "target" must be there.
   }

   /*
    * Finds a specified class. The bytecode for that class can be modified.
    */
   protected Class<?> findClass(String name) throws ClassNotFoundException {
      CtClass cc = null;
      try {
         cc = pool.get(name);
         cc.instrument(new ExprEditor() {	//ExprEditor is used for byecode editing like editing expresssions
            public void edit(MethodCall m) throws CannotCompileException {
               String className = m.getClassName();
               String methodName = m.getMethodName();

               if (className.equals("target." + clazNames[0]) && methodName.equals("draw")) {
                  System.out.println("[Edited by ClassLoader] method name: " + methodName + ", line: " + m.getLineNumber());
                  String block1 = "{" + _L_ //
                        + "System.out.println(\"Before a call to " + methodName + ".\"); " + _L_ //
                        + "$proceed($$); " + _L_ // $$ = all actual parameters ($1, $2...)
                        + "System.out.println(\"After a call to " + methodName + ".\"); " + _L_ //
                        + "}";
                  System.out.println("[DBG] BLOCK1: " + block1);
                  System.out.println("------------------------");
                  m.replace(block1);
               } else if (className.equals("target." + clazNames[0]) && methodName.equals(clazNames[1])) {
                  System.out.println("[Edited by ClassLoader] method name: " + methodName + ", line: " + m.getLineNumber());
                  String block2 = "{" + _L_ //
                        + "System.out.println(\"\tReset param " + clazNames[2] + " to " + clazNames[3] + ".\t\"); " + _L_ //
                        + "$" + clazNames[2] + " = " + clazNames[3] + "; " + _L_ //
                        + "$proceed($$); " + _L_ //
                        + "}";
                  System.out.println("[DBG] BLOCK2: " + block2);
                  System.out.println("------------------------");
                  m.replace(block2);
               }
            }
         });
         byte[] b = cc.toBytecode();
         return defineClass(name, b, 0, b.length);
      } catch (NotFoundException e) {
         throw new ClassNotFoundException();
      } catch (IOException e) {
         throw new ClassNotFoundException();
      } catch (CannotCompileException e) {
         e.printStackTrace();
         throw new ClassNotFoundException();
      }
   }
}
