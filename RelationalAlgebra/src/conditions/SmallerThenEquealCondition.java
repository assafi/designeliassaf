/**
 * Date: Apr 26, 2010
 * RelationalAlgebra - SmallerThenEquealCondition.java
 */
package conditions;

import java.util.Map;

/**
 * Represents a "<=" numeric operator.
 * @author Assaf Israel & Eli Nazarov
 *
 */
public class SmallerThenEquealCondition implements ICondition {

	private NotCondition not;
	
	public SmallerThenEquealCondition(Property parg1, Property parg2) {

		this.not = new NotCondition(new GreaterThenCondition(parg1, parg2));
	}
	
	public SmallerThenEquealCondition(Property parg1, int varg2) {
		
		this.not = new NotCondition(new GreaterThenCondition(parg1, varg2));
	}
	
	public SmallerThenEquealCondition(int varg1, Property parg2) {
		
		this.not = new NotCondition(new GreaterThenCondition(varg1, parg2));
	}

	public SmallerThenEquealCondition(int varg1, int varg2) {
		
		this.not = new NotCondition(new GreaterThenCondition(varg1, varg2));
	}
	
	@Override
	public boolean check(Map<Property, Object> values) {
		
		return not.check(values);
	}
}
