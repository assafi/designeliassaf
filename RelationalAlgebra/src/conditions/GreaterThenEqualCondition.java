/**
 * Date: Apr 26, 2010
 * RelationalAlgebra - GreaterThenEqualCondition.java
 */
package conditions;

import java.util.Map;

/**
 * @author Assaf Israel & Eli Nazarov
 *
 */
public class GreaterThenEqualCondition implements ICondition {

	private NotCondition not;
	
	public GreaterThenEqualCondition(Property parg1, Property parg2) {

		this.not = new NotCondition(new SmallerThenCondition(parg1, parg2));
	}
	
	public GreaterThenEqualCondition(Property parg1, int varg2) {
		
		this.not = new NotCondition(new SmallerThenCondition(parg1, varg2));
	}
	
	public GreaterThenEqualCondition(int varg1, Property parg2) {
		
		this.not = new NotCondition(new SmallerThenCondition(varg1, parg2));
	}

	public GreaterThenEqualCondition(int varg1, int varg2) {
		
		this.not = new NotCondition(new SmallerThenCondition(varg1, varg2));
	}
	
	@Override
	public boolean check(Map<Property, Object> values) {
		
		return not.check(values);
	}

}
