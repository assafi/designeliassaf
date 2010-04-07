package il.ac.technion.cs.sd236700.hw2;

public class B extends A{
	public B(){
		System.out.println("Entering A.ctor()");
		System.out.println("Exiting A.ctor()");
	}
	public void h(){
		foo();
	}
}
