package il.ac.technion.c236700;

import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class DBCMethodVisitor extends MethodAdapter {

	private String subjectClass = null;
	public DBCMethodVisitor(MethodVisitor mv, String subjectClass) {
		super(mv);
		this.subjectClass = subjectClass.replaceAll("\\.", "/");
	}

	@Override
	public void visitInsn(int opcode) {
		if (isTerminatingOpCode(opcode)){
			mv.visitVarInsn(Opcodes.ALOAD, 0); //pushing this pointer to the stack
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,	//Question #2
					subjectClass,
					"invariant", 	
					"()V");
		}
		mv.visitInsn(opcode);
	}

	static private boolean isTerminatingOpCode(int opcode) {
		return (opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN) || opcode == Opcodes.ATHROW;
	}

}//End class
