/**
 * Date: Apr 23, 2010
 * RelationalAlgebra - NotCondition.java
 */
package conditions;

import java.util.Map;
import java.util.Set;

/**
 * @author Assaf Israel & Eli Nazarov
 *
 */
public class NotCondition implements ICondition{

	private final ICondition arg;
	
	public NotCondition(ICondition arg) {
		this.arg = arg;
	}

	@Override
	public boolean check(Map<Property, Object> values) {
	
		return !this.arg.check(values);
	}
}
