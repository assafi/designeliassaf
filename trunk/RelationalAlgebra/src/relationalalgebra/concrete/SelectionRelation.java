/**
 * Date: Apr 23, 2010
 * RelationalAlgebra - SelectionRelation.java
 */
package relationalalgebra.concrete;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;

import conditions.ICondition;
import conditions.Property;
import relationalalgebra.IRelation;

/**
 * @author Assaf Israel & Eli Nazarov
 *
 */
public class SelectionRelation implements IRelation{
	
	private final String name;
	private final ICondition condition;
	private final IRelation initialRelation;
	private IRelation evaluatedRelation;
	
	private boolean evaluated = false;
	
	private Queue<Map<Property, Object>> delayedTasks = new ArrayDeque<Map<Property,Object>>();

	public SelectionRelation(String name, IRelation relation, ICondition condition){
		
		if (null == name || null == relation || null == condition){
			throw new IllegalArgumentException("Parameters can't be null");
		}
		
		this.name = name;
		this.initialRelation = relation;
		this.condition = condition;
		evaluatedRelation = new BasicRelation(name, initialRelation.getProperties());
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
			if (condition.check(entry)){
				evaluatedRelation.add(entry);
			}
		}
		
		while (!delayedTasks.isEmpty()) {
			evaluatedRelation.add(delayedTasks.poll());
		}
		
		return evaluatedRelation;
	}

	@Override
	public List<Property> getProperties() {
		return evaluatedRelation.getProperties();
	}

	@Override
	public Iterator<Map<Property, Object>> iterator() {
		evaluate(); //TODO Document this !
		return evaluatedRelation.iterator();
	}

	@Override
	public String getName() {
		return name;
	}

}
