package ex13.newfield;

import java.io.File;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;
import target.Author;
import target.Column;
import target.Row;
import target.Table;
import util.UtilMenu;
import java.lang.reflect.Field;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;

public class AnnotatedFieldExample3 {
	static String workDir = System.getProperty("user.dir");
	static String inputDir = workDir + File.separator + "classfiles";
	static String outputDir = workDir + File.separator + "output";
	static String[] clazNames;

	public static void main(String[] args) throws Exception {
		try {
			while (true) {
				UtilMenu.showMenuOptions();

				switch (UtilMenu.getOption()) {
				case 1:
					System.out.println("Enter Two Inputs (e.g.ComponentApp,Column or ServiceApp,Row");
					clazNames = UtilMenu.getArguments();

					if (clazNames.length == 2) {

						ClassPool pool = ClassPool.getDefault();
						pool.insertClassPath(inputDir);

						CtClass ct = pool.get("target." + clazNames[0]);
						CtField[] cf = ct.getFields();

						process(ct.getAnnotations());
						System.out.println();

						for (int i = 0; i < cf.length; i++) {
							// System.out.println("CF"+cf[i]);
							process(cf[i].getAnnotations());
						}
						// for(int i=0;i<cf.length;i++)
						// {
						// Object[] b = cf[i].getAnnotations();
						// System.out.println("SACOUT"+b);
						// if(b[i].toString().contains("Column"))
						// {
						// System.out.println("SACIN"+b[i]);
						// process(b);
						// }
						// else
						// continue;
						// }

						// Object b = cf.getAnnotations();
						// for(int i=0;i<b.length;i++)
						// {
						// System.out.println("SAC"+b[i]);
						// }
					} else
						System.out.println("[WNG] Invalid input size.!!");
				default:
					break;
				}
			}
		} catch (NotFoundException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	static void process(Object[] annoList) {
		if(clazNames[1] == "Column") {
			for (int i = 0; i < annoList.length; i++) {
		    	  System.out.println(annoList[i]);
//		    	  if(annoList[i].getClass().getField(name))
		    	  if(annoList[i].toString().contains("Column"))
		    	  {
		    		  if (annoList[i] instanceof Table) {
		    	            Table table = (Table) annoList[0];
		    	            System.out.println("Table: " + table.name() + ", ID: " + table.id());
		    	         } else if (annoList[i] instanceof Column) {
		    	            Column column = (Column) annoList[i];
		    	            System.out.println("Column: " + column.name() + ", ID: " + column.id());
		    	         } else if (annoList[i] instanceof Author) {
		    	             Author author = (Author) annoList[i];
		    	             System.out.println("Name: " + author.name() + ", Year: " + author.year());
		    	          }
		    	  }
		    	  else
		    		  break;
			}
			}
      
    	  else {
    		  for (int i = 0; i < annoList.length; i++) {
    		  if (annoList[i] instanceof Table) {
  	            Table table = (Table) annoList[0];
  	            System.out.println("Table: " + table.name() + ", ID: " + table.id());
  	         } else if (annoList[i] instanceof Row) {
  	            Row row = (Row) annoList[i];
  	            System.out.println("Row: " + row.name() + ", ID: " + row.id());
  	         }else if (annoList[i] instanceof Author) {
  	             Author author = (Author) annoList[i];
  	             System.out.println("Name: " + author.name() + ", Year: " + author.year());
  	          }

    	  }  
      } } 
}