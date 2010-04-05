package il.ac.technion.cs.sd236700.hw2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import il.ac.technion.cs.ssdl.utils.Checkable;

public class InvariantChecker {

	public static void check(Object c){

		//Does c implements Checkable?
		if (!(c instanceof Checkable)) {
			System.out.println("Invalid object");
			return;
		}

		//Does the class of the method that is on the two before the top of the stack [stack: check(),f(),g(),...]
		try {
			String topMethodClassName = c.getClass().getName();
			String callerMethodClassName = getCallerMethodClass();
			String callerMethodName = getCallerMethodName();

			if (topMethodClassName.equals(callerMethodClassName)
					&& (!methodIsStatic(callerMethodClassName, callerMethodName))) {
//				System.out.println("Second condition is not valid");
				return;
			}

		} catch (NoCallerClassException e) {
			//no need to check the second condition (classes equality)
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}
		invariant(c);
	}

	private static void invariant(Object c) {
		Class<?> cClass = c.getClass();
		try {
			Method invariantMethod = cClass.getMethod("invariant", new Class<?>[] {});
			invariantMethod.invoke(c, new Object[] {});
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private static String getCallerMethodName() throws NoCallerClassException {
		Throwable ex = new Throwable();
		StackTraceElement[] stackElements = ex.getStackTrace();

		if (stackElements.length < 4) {
			throw new NoCallerClassException();
		}

		return stackElements[3].getMethodName();
	}

	private static String getCallerMethodClass() throws NoCallerClassException {
		Throwable ex = new Throwable();
		StackTraceElement[] stackElements = ex.getStackTrace();

		if (stackElements.length < 4) { // stack: [ getCallerMethodClass(), check(), callee(), caller(),... ]
			throw new NoCallerClassException();
		}

		return stackElements[3].getClassName();
	}

	private static boolean methodIsStatic(String callerMethodClassName,
			String callerMethodName) throws ClassNotFoundException {

		Class<?> callerMethodClass = Class.forName(callerMethodClassName);
		Method[] classMethods = callerMethodClass.getMethods();
		for (Method method : classMethods) {
			if (method.getName().equals(callerMethodName)) {
				return ((method.getModifiers() & Modifier.STATIC) != 0);
			}
		}
		return false;
	}
}
