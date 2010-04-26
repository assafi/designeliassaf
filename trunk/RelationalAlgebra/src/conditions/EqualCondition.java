/**
 * Date: Apr 26, 2010
 * RelationalAlgebra - EqualCondition.java
 */
package conditions;

import java.util.Map;

/**
 * @author Assaf Israel & Eli Nazarov
 *
 */
public class EqualCondition implements ICondition {

	private AndCondition and;
	
	public EqualCondition(Property parg1, Property parg2) {

		this.and = new AndCondition(new GreaterThenEqualCondition(parg1, parg2), new SmallerThenEquealCondition(parg1, parg2));
	}
	
	public EqualCondition(Property parg1, int varg2) {
		
		this.and = new AndCondition(new GreaterThenEqualCondition(parg1, varg2), new SmallerThenEquealCondition(parg1, varg2));
	}
	
	public EqualCondition(int varg1, Property parg2) {
		
		this.and = new AndCondition(new GreaterThenEqualCondition(varg1, parg2), new SmallerThenEquealCondition(varg1, parg2));
	}

	public EqualCondition(int varg1, int varg2) {
		
		this.and = new AndCondition(new GreaterThenEqualCondition(varg1, varg2), new SmallerThenEquealCondition(varg1, varg2));
	}
	
	@Override
	public boolean check(Map<Property, Object> values) {
		
		return and.check(values);
	}
}
