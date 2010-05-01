/**
 * Date: May 1, 2010
 * RelationalAlgebra - LikeCondition.java
 */
package conditions;

import java.util.Map;

/**
 * Represents a String comparison operator.
 * @author Assaf Israel & Eli Nazarov
 *
 */
public class LikeCondition implements ICondition {

	private Property parg1 = null;
	private Property parg2 = null;
	private String varg1;
	private String varg2;
	
	public LikeCondition(Property parg1, Property parg2) {

		this.parg1 = parg1;
		this.parg2 = parg2;
	}
	
	public LikeCondition(Property parg1, String varg2) {
		
		this.parg1 = parg1;
		this.varg2 = varg2;
	}
	
	public LikeCondition(String varg1, Property parg2) {
		
		this.varg1 = varg1;
		this.parg2 = parg2;
	}

	public LikeCondition(String varg1, String varg2) {
		
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
			varg1 = (String)values.get(parg1);
		}
		
		if (null != parg2) {
			varg2 = (String)values.get(parg2);
		}
		
		return varg1.equals(varg2);
	}
}
