package ex07.insertmethodbody;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import util.UtilMenu;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import util.UtilFile;

public class InsertMethodBody extends ClassLoader {
	static String WORK_DIR = System.getProperty("user.dir");
	static String INPUT_DIR = WORK_DIR + File.separator + "classfiles";
	static String OUTPUT_DIR = WORK_DIR + File.separator + "output";

	static String _L_ = System.lineSeparator();
	private ClassPool pool;

	public static void main(String[] args) throws Throwable {
		try {
			while (true) {
				UtilMenu.showMenuOptions();

				switch (UtilMenu.getOption()) {
				case 1:
					System.out.println("Enter Three Inputs (e.g.ComponentApp,foo,1 or ServiceApp,bar,2");
					String[] clazNames = UtilMenu.getArguments();
					
					if(clazNames.length == 3)
					{
						ClassPool pool = ClassPool.getDefault();
						pool.insertClassPath(INPUT_DIR);
						CtClass cc = pool.get("target." + clazNames[0]);
						CtMethod m = cc.getDeclaredMethod(clazNames[1]);
						String block1 = "{ " + _L_ //
								+ "System.out.println(\"[INSERTED] target." + clazNames[0] + "." + clazNames[1] + "'s param "
								+ clazNames[2] + ": \" + $" + clazNames[2] + "); " + _L_ //
								+ "}";
						System.out.println(block1);
						m.insertBefore(block1);
						cc.writeFile(OUTPUT_DIR);
						System.out.println("[DBG] write output to: " + OUTPUT_DIR);
						System.out.println("[DBG]" + UtilFile.getShortFileName(OUTPUT_DIR));
	
						InsertMethodBody s = new InsertMethodBody();
						Class<?> c = s.loadClass("target." + clazNames[0]);
						Method mainMethod = c.getDeclaredMethod("main", new Class[] { String[].class });
						mainMethod.invoke(null, new Object[] { args });
					}
					else
						System.out.println("[WNG] Invalid Input");
				}
			}
		} catch (NotFoundException | CannotCompileException | IOException e) {
			e.printStackTrace();
		}
	}

	public InsertMethodBody() throws NotFoundException {
		pool = new ClassPool();
		pool.insertClassPath(OUTPUT_DIR); // TARGET must be there.
		// System.out.println("[CLASS-LOADER] CLASS_PATH: " + INPUT_PATH);
	}

	protected Class<?> findClass(String name) throws ClassNotFoundException {
		CtClass cc = null;
		try {
			cc = pool.get(name);
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
