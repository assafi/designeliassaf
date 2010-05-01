/**
 * Date: Apr 23, 2010
 * RelationalAlgebra - ICondition.java
 */
package conditions;

import java.util.Map;

/**
 * A condition can be defined in advance, and only checked 
 * later against a specific set of values. (corresponding to a 
 * set of pre-defined properties). 
 * @author Assaf Israel & Eli Nazarov 
 *
 */
public interface ICondition {
	
	public boolean check(Map<Property, Object> values);
}
