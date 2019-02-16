package ex04.toclass;

import ex04.util.UtilMenu;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.NotFoundException;
import target.Hello;
import target.CommonComponentB;

public class ToClass {
   public static void main(String[] args) {
      try {
    	  
    	  while (true) {
				UtilMenu.showMenuOptions();
				
				switch (UtilMenu.getOption()) {
				case 1:
					System.out.println("Choose Class: 1.CommonComponentB 2.CommonServiceA");
					String[] clazNames = UtilMenu.getArguments();	
					
					if(clazNames.length==1)
					{
						String newClass = clazNames[0];
						ClassPool cp = ClassPool.getDefault();
				         CtClass cc = cp.get("target."+newClass);
				         
				         CtConstructor declaredConstructor = cc.getDeclaredConstructor(new CtClass[0]);
				         declaredConstructor.insertAfter("{ " //
				               + "System.out.println(\"[TR] After calling a constructor: \\nid: \" + id + \"\\nyear:\"+ year); }");

				         Class<?> c = cc.toClass();
				         Object nc = c.newInstance();
					}
					else
						System.out.println("[WNG] Invalid Input");
						
					
				}}
      } catch (NotFoundException | CannotCompileException | //
            InstantiationException | IllegalAccessException e) {
         System.out.println(e.toString());
      }
   }
}
