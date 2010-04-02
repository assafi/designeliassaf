package il.ac.technion.cs.sd236700.hw2;

import il.ac.technion.cs.ssdl.utils.Checkable;

public class A implements Checkable{
	
	public A() {
		System.out.println("ctor");
	}
	
	public void foo(){
		System.out.println("A.foo()");
	}
	
	public void bar(){
		System.out.println("A.bar()");
		foo();
	}
	public static void main(String[] args) {
		new B().h(new A());
	}

	@Override
	public void invariant() {
		System.out.println("invariant has been invoked!");
	}
}
