/**
 * Date: Apr 26, 2010
 * RelationalAlgebra - OrCondition.java
 */
package conditions;

import java.util.Map;

/**
 * Represents a Boolean Or operator.
 * @author Assaf Israel & Eli Nazarov
 *
 */
public class OrCondition implements ICondition {

	private final ICondition arg1, arg2;
	
	public OrCondition(ICondition arg1, ICondition arg2) {
		this.arg1 = arg1;
		this.arg2 = arg2;
	}

	@Override
	public boolean check(Map<Property, Object> values) {
		
		return arg1.check(values) || arg2.check(values);
	}

}
