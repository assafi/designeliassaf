package il.ac.technion.cs.sd236700.hw2;

import il.ac.technion.cs.ssdl.utils.Checkable;

public class C implements Checkable{

	public C(){
		System.out.println("Entering C.ctor()");
		System.out.println("Exiting C.ctor()");
		
	}
	public void k() {
		System.out.println("Entering C.k()");
		new B().h();
		System.out.println("Exiting C.k()");
	}
	
	@Override
	public void invariant() {
		System.out.println("invariant has been invoked!");
	}
}
