package ex11.newexpr;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.NewExpr;
import util.UtilMenu;
import javassist.CtField;

public class NewExprAccess extends ClassLoader {
	static final String WORK_DIR = System.getProperty("user.dir");
	static final String CLASS_PATH = WORK_DIR + File.separator + "classfiles";
	// static final String TARGET_MY_APP2 = "target.MyAppField";
	static String _L_ = System.lineSeparator();
	static String[] clazNames;
	static String fieldName, fieldType;

	public static void main(String[] args) throws Throwable {
		try {
			while (true) {
				UtilMenu.showMenuOptions();

				switch (UtilMenu.getOption()) {
				case 1:
					System.out.println("Enter Two Inputs (e.g.ComponentApp,1 or ServiceApp,100");
					clazNames = UtilMenu.getArguments();

					if (clazNames.length == 2) {
						NewExprAccess s = new NewExprAccess();
						Class<?> c = s.loadClass("target." + clazNames[0]);
						Method mainMethod = c.getDeclaredMethod("main", new Class[] { String[].class });
						mainMethod.invoke(null, new Object[] { args });
					}
					else
						System.out.println("[WNG] Invalid input size.!!");
				}
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}

	private ClassPool pool;

	public NewExprAccess() throws NotFoundException {
		pool = new ClassPool();
		pool.insertClassPath(new ClassClassPath(new java.lang.Object().getClass()));
		pool.insertClassPath(CLASS_PATH); // TARGET must be there.
	}

	/*
	 * Finds a specified class. The bytecode for that class can be modified.
	 */
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		CtClass cc = null;
		try {
			cc = pool.get(name);
			cc.instrument(new ExprEditor() {
				public void edit(NewExpr newExpr) throws CannotCompileException { // NewExpr() override
					try {
						String longName = newExpr.getConstructor().getLongName();
						if (longName.startsWith("java.")) {
							return;
						}
						CtField fields[] = newExpr.getEnclosingClass().getDeclaredFields();
						for (int i = 0; i < (Integer.parseInt(clazNames[1])) ; i++) {
							
							fieldName = fields[i].getName();
							fieldType = fields[i].getType().getName();

							String log = String.format("[Edited by ClassLoader] new expr: %s, " //
									+ "line: %d, signature: %s", newExpr.getEnclosingClass().getName(), //
									newExpr.getLineNumber(), newExpr.getSignature());
							System.out.println(log);

							String block1 = "{ " + _L_ //
									+ "  $_ = $proceed($$);" + _L_ //
									+ "  { " + _L_ //
									+ "    String cName = $_.getClass().getName();" + _L_ //
									+ "    String fName = $_.getClass().getDeclaredFields()[" + i + "].getName();" + _L_ //
									// + " + \": \" + " + "$_." + fieldName + "+ " + "\", \" );" + _L_ //
									+ "    String fieldFullName = cName + \".\" + fName;" + _L_ //
									+ "    " + fieldType + " fieldValue = $_." + fieldName + ";" + _L_ //
									+ "    System.out.println(\"  [Instrument] \"+ fieldFullName + \": \" + fieldValue);"
									+ _L_ //

									// + " System.out.println($_.getClass().getDeclaredFields()[1].getName()" //
									// + " + \": \" + " + "$_."+fieldName+");" + _L_ //
									+ "  } " + _L_ //
									+ "}";
							System.out.println(block1);
							newExpr.replace(block1.toString());
						}
					} catch (NotFoundException e) {
						e.printStackTrace();
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