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
 * An IRelation concrete object must implement the functionality
 * corresponding with the Relational Algebra basic functionality described here.
 * @author Assaf Israel & Eli Nazarov
 *
 */
public interface IRelation {

	
	/**
	 * @return String equals to the Relation name. 
	 */
	public String getName();
	
	
	/**
	 * Add a single entry to the relation.
	 * @param entry Mapping between relation properties and a concrete values.
	 * @return True if the relation state has changed following this action, False otheriwse. 
	 */
	public boolean add(Map<Property, Object> entry);
	
	public IRelation evaluate();
	
	public void display();
	
	public Iterator<Map<Property, Object>> iterator();
	
	public Set<Property> getProperties();
}
