/**
 * Date: Apr 23, 2010
 * RelationalAlgebra - IRelation.java
 */
package relationalalgebra;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import conditions.Property;

/**
 * @author Assaf Israel & Eli Nazarov
 *
 */
public interface IRelation {

	public boolean add(Map<Property, Object> entry);
	
	public IRelation evaluate();
	
	public void display();
	
	public Iterator<Map<Property, Object>> iterator();
	
	public Set<Property> getProperties();
}
