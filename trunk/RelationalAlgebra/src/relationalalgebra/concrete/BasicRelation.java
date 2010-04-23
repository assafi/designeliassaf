/**
 * Date: Apr 23, 2010
 * RelationalAlgebra - BasicRelation.java
 */
package relationalalgebra.concrete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import relationalalgebra.IRelation;
import conditions.Property;

/**
 * @author Assaf Israel & Eli Nazarov
 *
 */
public class BasicRelation implements IRelation{

	private List<Map <Property, Object>> table = new ArrayList<Map<Property,Object>>();
	private final Set<Property> properties;
	
	public BasicRelation(Set<Property> properties) {
		
		this.properties = properties;
	}

	@Override
	public boolean add(Map<Property, Object> entry) {
		
		if (!properties.equals(entry.keySet())) {
			throw new IllegalArgumentException("Illigal entry. Properties do not match the Relation definition");
		}
		
		return table.add(cloneEntry(entry));
	}

	static Map<Property, Object> cloneEntry(Map<Property, Object> entry) {
		Map<Property, Object> newEntry = new HashMap<Property, Object>();
		for (Property property : entry.keySet()) {
			newEntry.put(property, entry.get(property));
		}
		return newEntry;
	}

	@Override
	public void display() {
		throw new UnsupportedOperationException();		//TODO IMPLEMENT THIS !!!
	}

	@Override
	public IRelation evaluate() {
		return this;
	}

	@Override
	public Iterator<Map<Property, Object>> iterator() {
		return table.iterator();
	}

	@Override
	public Set<Property> getProperties() {
		return properties;
	}
}
