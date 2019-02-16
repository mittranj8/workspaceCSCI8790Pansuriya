package ex02.setsuper;

import java.io.File;
import java.io.IOException;

import ex02.util.UtilMenu;
import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import target.Rectangle;

public class SetSuperclass {
   static String _S = File.separator;
   static String WORK_DIR = System.getProperty("user.dir");
   // static String CLASSPATH_DIR = WORK_DIR + _S + "classfiles";
   static String OUTPUT_DIR = WORK_DIR + _S + "output";
   static String comm1,comm2;
   
   public static void main(String[] args) {
	   int j=0;
	   try 
		{
			while (true) {
				UtilMenu.showMenuOptions();
				switch (UtilMenu.getOption()) {
				case 1:
					System.out.println("Enter two class names:");
					String[] clazNames = UtilMenu.getArguments();
					if(clazNames.length < 3)
					{
						for(int i=0; i<clazNames.length; i++)
						{
							System.out.println("[DBG] ARG " + i + ":" +clazNames[i]);
							if(clazNames[i].startsWith("Common"))
							{
								j=j+1;
							}
							else 
								continue;	
						}
					if(j==2)
					{
						if(clazNames[0].length() > clazNames[1].length())
						{
							setSuperClass(clazNames[1], clazNames[0]);
							break;
						}
						else
						{
							setSuperClass(clazNames[0], clazNames[1]);
							break;
						}
					}
					else if(j==1)
					{
						System.out.println("start");
						for(int i=0; i<clazNames.length; i++)
						{
							if(clazNames[i].startsWith("Common")) 
							{
								comm1=clazNames[i];
							}	
							else
							{
								comm2=clazNames[i];
								continue;
							}
						}	
						setSuperClass(comm2, comm1);
						System.out.println("yeyy");
					}
					else
					{
						setSuperClass(clazNames[1], clazNames[0]);
						System.out.println("yeyy222");
					}
					}
				default:
					break;
					}}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
 

   static void setSuperClass(String clazSub, String clazSuper) {
      try {
         ClassPool pool = ClassPool.getDefault();
         insertClassPathRunTimeClass(pool);

         CtClass ctClazSub = pool.get("target." + clazSub);
         CtClass ctClazSuper = pool.get("target." + clazSuper);
         ctClazSub.setSuperclass(ctClazSuper);
         System.out.println("[DBG] set superclass: " //
               + ctClazSub.getSuperclass().getName() //
               + ", subclass: " + ctClazSub.getName());

         ctClazSub.writeFile(OUTPUT_DIR);
         System.out.println("[DBG] write output to: " + OUTPUT_DIR);
      } catch (NotFoundException | CannotCompileException | IOException e) {
         e.printStackTrace();
      }
   }

   static void insertClassPathRunTimeClass(ClassPool pool) throws NotFoundException {
      Rectangle rectangle = new Rectangle();
      Class<?> runtimeObject = rectangle.getClass();
      ClassClassPath classPath = new ClassClassPath(runtimeObject);
      pool.insertClassPath(classPath);
      System.out.println("[DBG] insert classpath: " + classPath.toString());
   }
}
