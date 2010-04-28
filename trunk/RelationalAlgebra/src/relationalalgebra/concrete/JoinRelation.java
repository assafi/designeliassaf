/**
 * Date: Apr 28, 2010
 * RelationalAlgebra - JoinRelation.java
 */
package relationalalgebra.concrete;

import java.util.ArrayDeque;
import java.util.HashMap;
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
public class JoinRelation implements IRelation {

	private final String name;
	private final IRelation rel1;
	private final IRelation rel2;
	private IRelation evaluatedRelation;
	
	private boolean evaluated = false;
	
	private Queue<Map<Property, Object>> delayedTasks = new ArrayDeque<Map<Property,Object>>();
	

	private Set<Property> propetryIntersect = new HashSet<Property>();
	
	public JoinRelation(String name,  IRelation rel1, IRelation rel2){
		this.name = name;
		this.rel1 = rel1;
		this.rel2 = rel2;
		evaluatedRelation = new BasicRelation(name, unionProperties(rel1.getProperties(), rel2.getProperties()));
	}
	
	private Set<Property> unionProperties(Set<Property> properties1,
			Set<Property> properties2) {
		Set<Property> propertyUnion = new HashSet<Property>();
		for (Property property : properties1) {
			propertyUnion.add(property);
			if (properties2.contains(property)){
				propetryIntersect.add(property);
			}
		}
		
		for (Property property : properties2) {
			if (!propertyUnion.contains(property)){
				propertyUnion.add(property);
			}
		}
		
		return propertyUnion;
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
		joinRelations(rel1.evaluate(), rel2.evaluate());
		evaluated = true;
		return evaluatedRelation;
	}
	
	private void joinRelations(IRelation rel1, IRelation rel2) {
		Iterator<Map<Property, Object>> iterRel1 = rel1.iterator();
		/*
		 * For each row in the first Relation
		 * add all rows from the second relation
		 */
		while(iterRel1.hasNext()){
			Map<Property, Object> entryRel1 = iterRel1.next();
			Iterator<Map<Property, Object>> iterRel2 = rel2.iterator();
			while(iterRel2.hasNext()){
				boolean joinEntry = true;
				Map<Property, Object> entryRel2 = iterRel2.next();
				for (Property prop : entryRel1.keySet()) {
					if(propetryIntersect.contains(prop) &&
						!entryRel1.get(prop).equals(entryRel2.get(prop))){
							joinEntry = false;
						}
				}
				
				if (joinEntry){
					evaluatedRelation.add(joinEntries(entryRel1,entryRel2));
				}
			}
		}
			
	}

	private Map<Property, Object> joinEntries(Map<Property, Object> entry1,
			Map<Property, Object> entry2) {
		Map<Property, Object> newEntry = new HashMap<Property, Object>();
		for (Property prop : entry1.keySet()) {
			newEntry.put(prop, entry1.get(prop));
		}
		
		for (Property prop : entry2.keySet()) {
			if (!newEntry.containsKey(prop))
				newEntry.put(prop, entry2.get(prop));
		}
		
		return newEntry;

	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Set<Property> getProperties() {
		return evaluatedRelation.getProperties();
	}

	@Override
	public Iterator<Map<Property, Object>> iterator() {
		evaluate(); //TODO Document this !
		return evaluatedRelation.iterator();
	}

}
