package il.ac.technion.c236700;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class DBCClassAdapter extends ClassAdapter {

	private String subjectClass = null;
	
	public DBCClassAdapter(ClassVisitor cv, String subjectClass) {
		super(cv);
		this.subjectClass = subjectClass;
	}

	@Override
	public MethodVisitor visitMethod(int access,
			String name, 
			String desc,
			String signiture, 
			String[] exceptions) {
		
		MethodVisitor mv = cv.visitMethod(access, name, desc, signiture, exceptions);
		if (mv != null && isMethodSuitible(access, name, desc))
			mv = new DBCMethodVisitor(mv, subjectClass);
		return mv;
	}

	private boolean isMethodSuitible(int access, String name, String desc) {
		if ("invariant".equals(name))
			return false;
		if ( (Opcodes.ACC_STATIC & access) != 0 )
			return false;
		if ( (Opcodes.ACC_PUBLIC & access) == 0 )
			return false;
		if ( (Opcodes.ACC_DEPRECATED & access) != 0)		//Question #3
			return false;
		
		return true;
	}

}//End class
