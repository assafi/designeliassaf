package il.ac.technion.cs.sd236700.hw2;

import il.ac.technion.cs.ssdl.utils.Checkable;

public class A implements Checkable{
	
	public A() {
		System.out.println("Entering A.ctor()");
		System.out.println("Exiting A.ctor()");
	}
	
//	@Deprecated
	public void foo(){
		System.out.println("Entering A.foo()");
		System.out.println("Exiting A.foo()");
	}
	
//	@Deprecated
	public void bar(){
		System.out.println("Entering A.bar()");
		foo();
		System.out.println("Exiting A.bar()");
	}
	public static void main(String[] args) {
		new A().bar();
	}

	@Override
	public void invariant() {
		System.out.println("invariant has been invoked!");
	}
}
