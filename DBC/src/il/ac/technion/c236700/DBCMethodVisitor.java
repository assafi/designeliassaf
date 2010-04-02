package il.ac.technion.c236700;

import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class DBCMethodVisitor extends MethodAdapter {

	public DBCMethodVisitor(MethodVisitor mv) {
		super(mv);
	}

	@Override
	public void visitInsn(int opcode) {
		if (isTerminatingOpCode(opcode)){
			mv.visitVarInsn(Opcodes.ALOAD, 0); //pushing this pointer to the stack
			mv.visitMethodInsn(
					Opcodes.INVOKESTATIC,	//static method 
					"il/ac/technion/cs/sd236700/hw2/InvariantChecker", //package name 
					"check", //method name
					"(Ljava/lang/Object;)V"); 	 //method signature
		}
		mv.visitInsn(opcode);
	}

	static private boolean isTerminatingOpCode(int opcode) {
		return (opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN) || opcode == Opcodes.ATHROW;
	}

}//End class
