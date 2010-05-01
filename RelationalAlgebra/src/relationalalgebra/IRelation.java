/**
 * Date: Apr 23, 2010
 * RelationalAlgebra - IRelation.java
 */
package relationalalgebra;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	
	/**
	 * Lazy evaluation of the relation.
	 * @return The relation after evaluation of a composite relations
	 */
	public IRelation evaluate();
	
	/**
	 * Display the relation.
	 */
	public void display();
	
	/**
	 * Retrieve an iterator over the relation
	 * @return Iterator over the relation rows.<br>
	 * 		   The iterator retrieve single entry in the relation at a time
	 */
	public Iterator<Map<Property, Object>> iterator();
	
	/**
	 * Retrieves the properties of the relation.
	 * @return Ordered list of relation properties
	 */
	public List<Property> getProperties();
}
