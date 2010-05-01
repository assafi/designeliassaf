/**
 * Date: Apr 26, 2010
 * RelationalAlgebra - SmallerThenCondition.java
 */
package conditions;

import java.util.Map;

/**
 * Represents a "<" numeric operator.
 * @author Assaf Israel & Eli Nazarov
 *
 */
public class SmallerThenCondition implements ICondition {

	private Property parg1 = null;
	private Property parg2 = null;
	private int varg1;
	private int varg2;
	
	public SmallerThenCondition(Property parg1, Property parg2) {

		this.parg1 = parg1;
		this.parg2 = parg2;
	}
	
	public SmallerThenCondition(Property parg1, int varg2) {
		
		this.parg1 = parg1;
		this.varg2 = varg2;
	}
	
	public SmallerThenCondition(int varg1, Property parg2) {
		
		this.varg1 = varg1;
		this.parg2 = parg2;
	}

	public SmallerThenCondition(int varg1, int varg2) {
		
		this.varg1 = varg1;
		this.varg2 = varg2;
	}
	
	@Override
	public boolean check(Map<Property, Object> values) {
		
		if ((null != parg1 && !values.containsKey(parg1)) ||
				null != parg2 && !values.containsKey(parg2)) {
			throw new IllegalArgumentException();
		}
		
		if (null != parg1) {
			varg1 = (Integer)values.get(parg1);
		}
		
		if (null != parg2) {
			varg2 = (Integer)values.get(parg2);
		}
		
		return varg1 < varg2;
	}

}
