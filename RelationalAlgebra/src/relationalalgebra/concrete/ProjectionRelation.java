/**
 * Date: Apr 23, 2010
 * RelationalAlgebra - ProjectionRelation.java
 */
package relationalalgebra.concrete;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import conditions.Property;
import relationalalgebra.IRelation;

/**
 * @author Assaf Israel & Eli Nazarov
 *
 */
public class ProjectionRelation implements IRelation {

	private final IRelation initialRelation;
	private IRelation evaluatedRelation;
	
	private boolean evaluated = false;
	
	private Queue<Map<Property, Object>> delayedTasks = new ArrayDeque<Map<Property,Object>>();
	
	/**
	 * @param properties
	 */
	public ProjectionRelation(IRelation relation, Set<String> labels) {
		
		this.initialRelation = relation;
		if (checkLabels(relation.getProperties(), labels)) {
			throw new IllegalArgumentException("Projection labels are not valid for these relation");
		}
		this.evaluatedRelation = new BasicRelation(getProjectedProperties(labels));
	}

	@Override
	public boolean add(Map<Property, Object> entry) {
		
		if (evaluated) {
			return evaluatedRelation.add(entry);
		} 
		return delayedTasks.add(BasicRelation.cloneEntry(entry));
	}
	
	@Override
	public void display() {
		evaluate();
		evaluatedRelation.display();
	}
	
	@Override
	public IRelation evaluate() {
		Iterator<Map<Property, Object>> iter = this.initialRelation.iterator();
		while (iter.hasNext()) {
			Map<Property, Object> entry = BasicRelation.cloneEntry(iter.next());
			for (Property property : entry.keySet()) {
				if (!evaluatedRelation.getProperties().contains(property)) {
					entry.remove(property);
				}
			}
			evaluatedRelation.add(entry);
		}
		
		while (!delayedTasks.isEmpty()) {
			evaluatedRelation.add(delayedTasks.poll());
		}
		
		evaluated = true;
		return evaluatedRelation;
	}

	@Override
	public Iterator<Map<Property, Object>> iterator() {
		evaluate(); //TODO Document this !
		return evaluatedRelation.iterator();
	}

	private Set<Property> getProjectedProperties(	Set<String> labels) {
		
		Set<Property> retSet = new  HashSet<Property>();
		for (Property property : initialRelation.getProperties()) {
			if (labels.contains(property.getName())) {
				retSet.add(property);
			}
		}
		
		return retSet;
	}
	
	private boolean checkLabels(Set<Property> properties, Set<String> labels) {
		
		Set<String> initialLabels = new HashSet<String>();
		for (Property property : initialRelation.getProperties()) {
			initialLabels.add(property.getName());
		}
		
		for (String label : labels) {
			if (!(initialLabels.contains(label))) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public Set<Property> getProperties() {
		return evaluatedRelation.getProperties();
	}
}
