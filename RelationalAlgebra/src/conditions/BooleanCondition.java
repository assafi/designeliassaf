/**
 * Date: Apr 23, 2010
 * RelationalAlgebra - BooleanCondition.java
 */
package conditions;

import java.util.Map;

/**
 * Represents a Boolean condition.
 * @author Assaf Israel & Eli Nazarov
 *
 */
public class BooleanCondition implements ICondition{

	private final Property property;
	
	public BooleanCondition(Property property) {
		
		if (!(property.getType().equals(Boolean.class))) {
			throw new IllegalArgumentException("Expected Boolean type");
		}
		this.property = property;
	}

	@Override
	public boolean check(Map<Property, Object> values) {
		
		if (!values.containsKey(property)) {
			throw new IllegalArgumentException("No " + property.getName() + " value in values");
		}
				
		return (Boolean) values.get(property);
	}
}
