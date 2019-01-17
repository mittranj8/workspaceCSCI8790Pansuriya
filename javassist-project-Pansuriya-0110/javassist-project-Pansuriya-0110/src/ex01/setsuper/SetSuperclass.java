package ex01.setsuper;

import java.io.File;
import java.io.IOException;

import javassist.CannotCompileException;
/*import javassist.ClassClassPath;*/
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
/*import target.Rectangle;*/

public class SetSuperclass {
   static String _S = File.separator;
   static String workDir = System.getProperty("user.dir");
   static String outputDir = workDir + _S + "output";

   public static void main(String[] args) {
	   	int j=0;
	   	String sup,sub;
		if(args.length < 3)
		{
			for(int i=0; i<args.length; i++)
			{
				System.out.println("[DBG] ARG " + i + ":" +args[i]);
				if(args[i].startsWith("Common"))
				{
					j=j+1;
				}
				else 
					continue;	
			}
			try 
			{
				ClassPool pool = ClassPool.getDefault();
         
				insertClassPath(pool);
				
				if(j==2)
				{
					if(args[0].length() > args[1].length())
					{
						sup = args[0];
						sub = args[1];
						CtClass cc = pool.get("target."+sub);
						setSuperclass(cc, "target."+sup, pool);
						cc.writeFile(outputDir);
						System.out.println("[DBG] write output to: " + outputDir);
					}
					else
					{
						sup = args[1];
						sub = args[0];
						CtClass cc = pool.get("target."+sub);
						setSuperclass(cc, "target."+sup, pool);
						cc.writeFile(outputDir);
						System.out.println("[DBG] write output to: " + outputDir);
					}		
				}
				else
				{
					sup = args[0];
					sub = args[1];
					CtClass cc = pool.get("target."+sub);
					setSuperclass(cc, "target."+sup, pool);
					cc.writeFile(outputDir);
					System.out.println("[DBG] write output to: " + outputDir);
				}

			} 
			catch (NotFoundException | CannotCompileException | IOException e) {
				e.printStackTrace();
			}
		}
		else
			return;
   }

   static void insertClassPath(ClassPool pool) throws NotFoundException {
      String strClassPath = workDir + _S + "classfiles";
      pool.insertClassPath(strClassPath);
      System.out.println("[DBG] insert classpath: " + strClassPath);
   }

   static void setSuperclass(CtClass curClass, String superClass, ClassPool pool) throws NotFoundException, CannotCompileException {
      curClass.setSuperclass(pool.get(superClass));
      System.out.println("[DBG] set superclass: " + curClass.getSuperclass().getName() + //
            ", subclass: " + curClass.getName());
   }
}
