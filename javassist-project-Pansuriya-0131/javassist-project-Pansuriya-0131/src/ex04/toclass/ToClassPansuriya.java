package ex04.toclass;

import ex04.util.UtilMenu;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.NotFoundException;
import target.CommonComponentB;

public class ToClassPansuriya {
   public static void main(String[] args) {
      try {
    	  
    	  while (true) {
				UtilMenu.showMenuOptions();
				
				switch (UtilMenu.getOption()) {
				case 1:
					System.out.println("Choose Class: 1.CommonServiceA 2.CommonComponentB \nChoose 2 field names: 1.idA, nameA 2.idB, nameB");
					String[] clazNames = UtilMenu.getArguments();	
					
					if(clazNames.length == 3)
					{
						String newClass = clazNames[0].replaceAll(",$", "");
						String field1 = clazNames[1].replaceAll(",$", "");
						String field2 = clazNames[2];
						ClassPool cp = ClassPool.getDefault();
				         CtClass cc = cp.get("target."+newClass);
				         
				         if(newClass.equals("CommonServiceA") && field1.equals("idA") && field2.equals("nameA") )
				         {
				        	 CtConstructor declaredConstructor = cc.getDeclaredConstructor(new CtClass[0]);
					         declaredConstructor.insertAfter("{ " //
					               + "System.out.println(\"[TR] After calling a constructor: \\nidA: \" + idA + \"\\nnameA: \"+ nameA); }");

					         Class<?> c = cc.toClass();
					         Object nc = c.newInstance();
				         }
				         else if(newClass.equals("CommonComponentB") && field1.equals("idB") && field2.equals("nameB"))
				         {
				        	 CtConstructor declaredConstructor = cc.getDeclaredConstructor(new CtClass[0]);
					         declaredConstructor.insertAfter("{ " //
					               + "System.out.println(\"[TR] After calling a constructor: \\nidB: \" + idB + \"\\nnameB: \"+ nameB); }");

					         Class<?> c = cc.toClass();
					         Object nc = c.newInstance();
				         }
				         else 
				        	 System.out.println("[WNG] Invalid Input");
					}
					else
						System.out.println("[WNG] Invalid Input");
				}
			}
      } 
      catch (NotFoundException | CannotCompileException | //
            InstantiationException | IllegalAccessException e) {
         System.out.println(e.toString());
      }
   }
}
