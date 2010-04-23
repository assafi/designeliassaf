/**
 * Date: Apr 23, 2010
 * RelationalAlgebra - ICondition.java
 */
package conditions;

import java.util.Map;

/**
 * @author Assaf Israel & Eli Nazarov 
 *
 */
public interface ICondition {
	
	public boolean check(Map<Property, Object> values);
}
