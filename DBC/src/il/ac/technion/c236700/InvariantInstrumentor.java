package il.ac.technion.c236700;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

public class InvariantInstrumentor implements ClassFileTransformer{
	final String subjectClass;
	
	public InvariantInstrumentor(String subjectClass) {
		this.subjectClass = subjectClass; 
	}
	public static void premain(String agentArgs, Instrumentation inst){
		inst.addTransformer(new InvariantInstrumentor(agentArgs));
	}//end premain

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> clazz,
			ProtectionDomain dom, byte[] byteCode)
			throws IllegalClassFormatException {
		final String loadedClass = className.replaceAll("/", ".");
		if (null == subjectClass || subjectClass.isEmpty() || !subjectClass.equals(loadedClass))
			return byteCode;
		return instrument(byteCode);
	}
	
	private byte[] instrument(byte[] byteCode) {
		ClassReader cr = new ClassReader(byteCode);
		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);

		ClassReader cr2 = new ClassReader(byteCode);
		ClassWriter cw2 = new ClassWriter(cr2, ClassWriter.COMPUTE_MAXS);
		
		/*
		 * Finding out if invariant exists - Question #2
		 */
		InvariantAdaptor invAdaptor = new InvariantAdaptor(cw2);
		cr2.accept(invAdaptor, ClassReader.SKIP_DEBUG);

		/*
		 * Transforming the code as usual
		 */
		DBCClassAdapter dbcAdaptor = new DBCClassAdapter(cw,subjectClass,invAdaptor);  
		cr.accept(dbcAdaptor, ClassReader.SKIP_DEBUG);
		return cw.toByteArray();
	}
}
