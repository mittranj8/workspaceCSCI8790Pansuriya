package javassistloader;

import java.io.File;
import java.lang.reflect.Method;

import util.UtilMenu;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Loader;

public class JavassistLoaderExample {
   private static final String WORK_DIR = System.getProperty("user.dir");
   private static final String INPUT_DIR = WORK_DIR + File.separator + "classfiles";
   private static final String TARGET_POINT = "target.Point";
   private static final String TARGET_RECTANGLE = "target.Rectangle";

   public static void main(String[] args) {
      try {
    	  //int cnt = 0;
    	  while (true) {
				UtilMenu.showMenuOptions();
				
				switch (UtilMenu.getOption()) {
				case 1:
					System.out.println("1.Enter usage method(add/remove): \n2.Enter increment method(incX/decX): \n3.Enter getter method(getX/getY):\n");
					String[] clazNames = UtilMenu.getArguments();	
					//System.out.println(clazNames[0]);
					
					if(clazNames.length == 3)
					{ 
						if(clazNames[0].equals("add") && clazNames[1].equals("incX") && clazNames[2].equals("getX"))
				         {
				        	 //cnt = cnt+1;
				        	 //System.out.println("\n"+ cnt);
				        	 ClassPool cp = ClassPool.getDefault();
					         cp.insertClassPath(INPUT_DIR);
					         System.out.println("[DBG] insert classpath: " + INPUT_DIR);
					         
				        	 CtClass cc = cp.get(TARGET_RECTANGLE);
				        	 cc.defrost();
					         cc.setSuperclass(cp.get(TARGET_POINT));
					         CtMethod m1 = cc.getDeclaredMethod("add");
					         m1.insertBefore("{ " //
					               + "incX();" //
					               + "System.out.println(\"[TR] getX result : \" + getX()); }");
					
					         Loader cl = new Loader(cp);
					         Class<?> c = cl.loadClass(TARGET_RECTANGLE);
					         Object rect = c.newInstance();
					         System.out.println("[DBG] Created a Rectangle object.");
					
					         Class<?> rectClass = rect.getClass();
					         Method m = rectClass.getDeclaredMethod("add", new Class[] {});
					         System.out.println("[DBG] Called getDeclaredMethod.");
					         Object invoker = m.invoke(rect, new Object[] {});
					         System.out.println("[DBG] add result: " + invoker);
						
				         }
				         else if(clazNames[0].equals("add") && clazNames[1].equals("incY") && clazNames[2].equals("getY"))
				         {
				        	 //cnt = cnt+1;
				        	 //System.out.println("\n"+ cnt);
				        	 ClassPool cp = ClassPool.getDefault();
					         cp.insertClassPath(INPUT_DIR);
					         System.out.println("[DBG] insert classpath: " + INPUT_DIR);
					         
				        	 CtClass cc = cp.get(TARGET_RECTANGLE);
				        	 cc.defrost();
					         cc.setSuperclass(cp.get(TARGET_POINT));
					         CtMethod m1 = cc.getDeclaredMethod("add");
					         m1.insertBefore("{ " //
					               + "incY();" //
					               + "System.out.println(\"[TR] getY result : \" + getY()); }");
					
					         Loader cl = new Loader(cp);
					         Class<?> c = cl.loadClass(TARGET_RECTANGLE);
					         Object rect = c.newInstance();
					         System.out.println("[DBG] Created a Rectangle object.");
					
					         Class<?> rectClass = rect.getClass();
					         Method m = rectClass.getDeclaredMethod("add", new Class[] {});
					         System.out.println("[DBG] Called getDeclaredMethod.");
					         Object invoker = m.invoke(rect, new Object[] {});
					         System.out.println("[DBG] add result: " + invoker);
						
				         }
				         else if(clazNames[0].equals("remove") && clazNames[1].equals("incX") && clazNames[2].equals("getX"))
				         {
				        	 //cnt = cnt+1;
				        	 //System.out.println("\n"+ cnt);
				        	 ClassPool cp = ClassPool.getDefault();
					         cp.insertClassPath(INPUT_DIR);
					         System.out.println("[DBG] insert classpath: " + INPUT_DIR);
					         
				        	 CtClass cc = cp.get(TARGET_RECTANGLE);
				        	 cc.defrost();
					         cc.setSuperclass(cp.get(TARGET_POINT));
					         CtMethod m1 = cc.getDeclaredMethod("remove");
					         m1.insertBefore("{ " //
					               + "incX();" //
					               + "System.out.println(\"[TR] getX result : \" + getX()); }");
					
					         Loader cl = new Loader(cp);
					         Class<?> c = cl.loadClass(TARGET_RECTANGLE);
					         Object rect = c.newInstance();
					         System.out.println("[DBG] Created a Rectangle object.");
					
					         Class<?> rectClass = rect.getClass();
					         Method m = rectClass.getDeclaredMethod("remove", new Class[] {});
					         System.out.println("[DBG] Called getDeclaredMethod.");
					         Object invoker = m.invoke(rect, new Object[] {});
					         System.out.println("[DBG] remove result: " + invoker);
						
				         }
				         else if(clazNames[0].equals("remove") && clazNames[1].equals("incY") && clazNames[2].equals("getY"))
				         {
				        	 //cnt = cnt+1;
				        	 //System.out.println("\n"+ cnt);
				        	 ClassPool cp = ClassPool.getDefault();
					         cp.insertClassPath(INPUT_DIR);
					         System.out.println("[DBG] insert classpath: " + INPUT_DIR);
					         
				        	 CtClass cc = cp.get(TARGET_RECTANGLE);
				        	 cc.defrost();
					         cc.setSuperclass(cp.get(TARGET_POINT));
					         CtMethod m1 = cc.getDeclaredMethod("remove");
					         m1.insertBefore("{ " //
					               + "incY();" //
					               + "System.out.println(\"[TR] incY result : \" + getY()); }");
					
					         Loader cl = new Loader(cp);
					         Class<?> c = cl.loadClass(TARGET_RECTANGLE);
					         Object rect = c.newInstance();
					         System.out.println("[DBG] Created a Rectangle object.");
					
					         Class<?> rectClass = rect.getClass();
					         Method m = rectClass.getDeclaredMethod("remove", new Class[] {});
					         System.out.println("[DBG] Called getDeclaredMethod.");
					         Object invoker = m.invoke(rect, new Object[] {});
					         System.out.println("[DBG] remove result: " + invoker);
						
				         }
				         else
				        	 System.out.println("[WNG] Invalid input string!!");
				      
					}
					else
						System.out.println("[WNG] Invalid input size!!");
			         
				} 
    	  }}
      catch (Exception e) {
         e.printStackTrace();
      }
   }

   /*static void insertClassPath(ClassPool pool) throws NotFoundException {
      String strClassPath = WORK_DIR + File.separator + "classfiles";
      pool.insertClassPath(strClassPath);
      System.out.println("[DBG] insert classpath: " + strClassPath);
   }*/
}
