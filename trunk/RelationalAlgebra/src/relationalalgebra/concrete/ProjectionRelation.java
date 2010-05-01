/**
 * Date: Apr 23, 2010
 * RelationalAlgebra - ProjectionRelation.java
 */
package relationalalgebra.concrete;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import relationalalgebra.IRelation;
import conditions.Property;

/**
 * @author Assaf Israel & Eli Nazarov
 *
 */
public class ProjectionRelation implements IRelation {

	private final String name;
	private final IRelation initialRelation;
	private IRelation evaluatedRelation;
	
	private boolean evaluated = false;
	
	private Queue<Map<Property, Object>> delayedTasks = new ArrayDeque<Map<Property,Object>>();
	
	/**
	 * @param properties
	 */
	public ProjectionRelation(String name, IRelation relation, Set<String> labels) {
		
		if (null == name || null == relation || null == labels){
			throw new IllegalArgumentException("Parameters can't be null");
		}
		
		this.name = name;
		this.initialRelation = relation;
		if (!checkLabels(relation.getProperties(), labels)) {
			throw new IllegalArgumentException("Projection labels are not valid for these relation");
		}
		this.evaluatedRelation = new BasicRelation(name, getProjectedProperties(labels));
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
			Map<Property, Object> newEntry = BasicRelation.cloneEntry(entry);
			for (Property property : entry.keySet()) {
				if (!evaluatedRelation.getProperties().contains(property)) {
					newEntry.remove(property);
				}
			}
			evaluatedRelation.add(newEntry);
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

	private List<Property> getProjectedProperties(	Set<String> labels) {
		
		List<Property> retSet = new  ArrayList<Property>();
		for (Property property : initialRelation.getProperties()) {
			if (labels.contains(property.getName())) {
				retSet.add(property);
			}
		}
		
		return retSet;
	}
	
	private boolean checkLabels(List<Property> properties, Set<String> labels) {
		
		Set<String> initialLabels = new HashSet<String>();
		for (Property property : properties) {
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
	public List<Property> getProperties() {
		return evaluatedRelation.getProperties();
	}

	@Override
	public String getName() {
		return name;
	}
}
