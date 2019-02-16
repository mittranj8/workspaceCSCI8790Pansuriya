package classloader;

import java.io.File;
import java.io.IOException;

import util.UtilMenu;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.Modifier;
import javassist.NotFoundException;

public class SampleLoader extends ClassLoader {
   static String WORK_DIR = System.getProperty("user.dir");
   static String INPUT_DIR = WORK_DIR + File.separator + "classfiles";
//   static String TARGET_APP = "MyApp";
   private ClassPool pool;

   public static void main(String[] args) throws Throwable {      
      while (true) {
			UtilMenu.showMenuOptions();
			
			switch (UtilMenu.getOption()) {
			case 1:
				System.out.println("1.Enter application class name(ComponentApp/ServiceApp): \n2.Enter field name(f1/f2):");
				String[] clazNames = UtilMenu.getArguments();
				
				SampleLoader s = new SampleLoader();
			    Class<?> c = s.loadClass(clazNames[0]);
      
					c.getDeclaredMethod("main", new Class[] { String[].class }). //
						invoke(null, new Object[] { clazNames }); //main method is static so null in invoke args			

			default:
				break;
			}
      }
   }
   public SampleLoader() throws NotFoundException {
      pool = new ClassPool();
      pool.insertClassPath(INPUT_DIR); // Search ComponentApp.class/ServiceApp.class in this path.
   }
   /* 
    * Find a specified class, and modify the bytecode.
    */
   protected Class<?> findClass(String name) throws ClassNotFoundException {
      try {
         CtClass cc = pool.get(name);
         
         if (name.equals("ComponentApp")) {
            CtField f = new CtField(CtClass.doubleType, "f1", cc);
            f.setModifiers(Modifier.PUBLIC);
            cc.addField(f);
         }
         else if(name.equals("ServiceApp")) {
                 CtField f = new CtField(CtClass.doubleType, "f2", cc);
                 f.setModifiers(Modifier.PUBLIC);
                 cc.addField(f);
         }
         byte[] b = cc.toBytecode();
         return defineClass(name, b, 0, b.length);
         
      } catch (NotFoundException e) {
         throw new ClassNotFoundException();
      } catch (IOException e) {
         throw new ClassNotFoundException();
      } catch (CannotCompileException e) {
         throw new ClassNotFoundException();
      }
   }
}
