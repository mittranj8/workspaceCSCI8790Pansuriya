package ex03.defrost;

import java.io.File;
import java.io.IOException;

import ex03.util.UtilMenu;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

public class DefrostClass {
   static String WORK_DIR = System.getProperty("user.dir");
   static String OUTPUT_DIR = WORK_DIR + File.separator + "output";
   static String comm1,comm2;

   public static void main(String[] args) {
	   int j=0;
	
			try {
			
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
					
					ClassPool pool = ClassPool.getDefault();
					pool.insertClassPath(OUTPUT_DIR);
					System.out.println("[DBG] class path: " + OUTPUT_DIR);

					if(j==2)
					{
						if(clazNames[0].length() > clazNames[1].length())
						{
							CtClass ccPoint2 = pool.makeClass(clazNames[0]);
							ccPoint2.writeFile(OUTPUT_DIR);
							System.out.println("[DBG] write output to: " + OUTPUT_DIR);
							System.out.println("[DBG]\t new class: " + ccPoint2.getName());

							CtClass ccRectangle2 = pool.makeClass(clazNames[1]);
							ccRectangle2.writeFile(OUTPUT_DIR);
							System.out.println("[DBG] write output to: " + OUTPUT_DIR);
							System.out.println("[DBG]\t new class: " + ccRectangle2.getName());

							ccRectangle2.defrost();
							System.out.println("[DBG] modifications of the class definition will be permitted.");

							ccRectangle2.setSuperclass(ccPoint2);
							System.out.println("[DBG] set super class, " + ccRectangle2.getName() + " -> " + ccPoint2.getName());

							ccRectangle2.writeFile(OUTPUT_DIR);
							System.out.println("[DBG] write output to: " + OUTPUT_DIR);
						}
						else 
						{
							CtClass ccPoint2 = pool.makeClass(clazNames[1]);
							ccPoint2.writeFile(OUTPUT_DIR);
							System.out.println("[DBG] write output to: " + OUTPUT_DIR);
							System.out.println("[DBG]\t new class: " + ccPoint2.getName());

							CtClass ccRectangle2 = pool.makeClass(clazNames[0]);
							ccRectangle2.writeFile(OUTPUT_DIR);
							System.out.println("[DBG] write output to: " + OUTPUT_DIR);
							System.out.println("[DBG]\t new class: " + ccRectangle2.getName());

							ccRectangle2.defrost();
							System.out.println("[DBG] modifications of the class definition will be permitted.");

							ccRectangle2.setSuperclass(ccPoint2);
							System.out.println("[DBG] set super class, " + ccRectangle2.getName() + " -> " + ccPoint2.getName());

							ccRectangle2.writeFile(OUTPUT_DIR);
							System.out.println("[DBG] write output to: " + OUTPUT_DIR);
						}
					}
					else if(j==1)
					{

						for(int i=0; i<clazNames.length; i++)
						{
							if(clazNames[i].startsWith("Common"))
								comm1=clazNames[i];
							else
							{
								comm2=clazNames[i];
								continue;
							}
						}	

						CtClass ccPoint2 = pool.makeClass(comm1);
						ccPoint2.writeFile(OUTPUT_DIR);
						System.out.println("[DBG] write output to: " + OUTPUT_DIR);
						System.out.println("[DBG]\t new class: " + ccPoint2.getName());

						CtClass ccRectangle2 = pool.makeClass(comm2);
						ccRectangle2.writeFile(OUTPUT_DIR);
						System.out.println("[DBG] write output to: " + OUTPUT_DIR);
						System.out.println("[DBG]\t new class: " + ccRectangle2.getName());

						ccRectangle2.defrost();
						System.out.println("[DBG] modifications of the class definition will be permitted.");

						ccRectangle2.setSuperclass(ccPoint2);
						System.out.println("[DBG] set super class, " + ccRectangle2.getName() + " -> " + ccPoint2.getName());

						ccRectangle2.writeFile(OUTPUT_DIR);
						System.out.println("[DBG] write output to: " + OUTPUT_DIR);
					}
					else
					{
						CtClass ccPoint2 = pool.makeClass(clazNames[0]);
						ccPoint2.writeFile(OUTPUT_DIR);
						System.out.println("[DBG] write output to: " + OUTPUT_DIR);
						System.out.println("[DBG]\t new class: " + ccPoint2.getName());

						CtClass ccRectangle2 = pool.makeClass(clazNames[1]);
						ccRectangle2.writeFile(OUTPUT_DIR);
						System.out.println("[DBG] write output to: " + OUTPUT_DIR);
						System.out.println("[DBG]\t new class: " + ccRectangle2.getName());

						ccRectangle2.defrost();
						System.out.println("[DBG] modifications of the class definition will be permitted.");

						ccRectangle2.setSuperclass(ccPoint2);
						System.out.println("[DBG] set super class, " + ccRectangle2.getName() + " -> " + ccPoint2.getName());

						ccRectangle2.writeFile(OUTPUT_DIR);
						System.out.println("[DBG] write output to: " + OUTPUT_DIR);
					}
					}
					else
						System.out.println("[WNG] Invalid Input");
				default:
					break;
				
			}}}
			catch (NotFoundException | CannotCompileException | IOException e) {
					e.printStackTrace();
			}
		
   }

   static void insertClassPath(ClassPool pool) throws NotFoundException {
      String strClassPath = OUTPUT_DIR;
      pool.insertClassPath(strClassPath);
      System.out.println("[DBG] insert classpath: " + strClassPath);
   }
}
