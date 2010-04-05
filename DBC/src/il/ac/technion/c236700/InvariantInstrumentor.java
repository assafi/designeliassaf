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
		System.out.println("subjectClass - " + subjectClass);
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
		
		DBCClassAdapter dbcAdaptor = new DBCClassAdapter(cw,subjectClass);  
		
		cr.accept(dbcAdaptor, ClassReader.SKIP_DEBUG);
		return cw.toByteArray();
	}
}
