package il.ac.technion.c236700;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class InvariantAdaptor extends ClassAdapter {

	private final static String INVARIANT = "invariant";
	private boolean invariantExists = false;
	public InvariantAdaptor(ClassVisitor cv) {
		super(cv);
	}
	
	@Override
	public MethodVisitor visitMethod(int access,
			String name, 
			String desc,
			String signiture, 
			String[] exceptions) {
		
		if (INVARIANT.equals(name) && ((Opcodes.ACC_PUBLIC & access) != 0) 
				&& ((Opcodes.ACC_STATIC & access) == 0)) {
			
			this.invariantExists = true;
		}
		
		MethodVisitor mv = cv.visitMethod(access, name, desc, signiture, exceptions);
		return mv;
	}
	
	public boolean invariantExists() {
		return this.invariantExists;
	}

}
