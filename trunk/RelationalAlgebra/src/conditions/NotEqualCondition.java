/**
 * Date: Apr 26, 2010
 * RelationalAlgebra - NotEqualCondition.java
 */
package conditions;

import java.util.Map;

/**
 * Represents a Numeric comparison operator.
 * check whether two integers are different. 
 * @author Assaf Israel & Eli Nazarov
 *
 */
public class NotEqualCondition implements ICondition {

	private NotCondition not;
	
	public NotEqualCondition(Property parg1, Property parg2) {

		this.not = new NotCondition(new EqualCondition(parg1, parg2));
	}
	
	public NotEqualCondition(Property parg1, int varg2) {
		
		this.not = new NotCondition(new EqualCondition(parg1, varg2));
	}
	
	public NotEqualCondition(int varg1, Property parg2) {
		
		this.not = new NotCondition(new EqualCondition(varg1, parg2));
	}

	public NotEqualCondition(int varg1, int varg2) {
		
		this.not = new NotCondition(new EqualCondition(varg1, varg2));
	}
	
	@Override
	public boolean check(Map<Property, Object> values) {
		
		return not.check(values);
	}
}
