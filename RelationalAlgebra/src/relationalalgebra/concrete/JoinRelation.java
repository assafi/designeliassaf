/**
 * Date: Apr 28, 2010
 * RelationalAlgebra - JoinRelation.java
 */
package relationalalgebra.concrete;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
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
public class JoinRelation implements IRelation {

	private final String name;
	private final IRelation rel1;
	private final IRelation rel2;
	private IRelation evaluatedRelation;
	
	private boolean evaluated = false;
	
	private Queue<Map<Property, Object>> delayedTasks = new ArrayDeque<Map<Property,Object>>();
	

	private Set<Property> propetryIntersect = new HashSet<Property>();
	private Set<Property> propetryNameChanged = new HashSet<Property>();
	
	public JoinRelation(String name,  IRelation rel1, IRelation rel2){
		this.name = name;
		this.rel1 = rel1;
		this.rel2 = rel2;
		evaluatedRelation = new BasicRelation(name, unionProperties(rel1.getProperties(), rel2.getProperties()));
	}
	
	private List<Property> unionProperties(List<Property> properties1,
			List<Property> properties2) {
		List<Property> propertyUnion = new ArrayList<Property>();
		for (Property property1 : properties1) {
			for (Property property2 : properties2) {
				/*
				 * if they are equal add only one
				 */
				if(property1.equals(property2)){
					propertyUnion.add(property1);
					propetryIntersect.add(property1);
					if (!propetryIntersect.contains(property2))
						propetryIntersect.add(property2);
				}
				else{
					// if names are the same then change the names
					if (property1.getName().equals(property2.getName())){
						Property joinProp1 = new Property(rel1.getName() + "." + property1.getName(), property1.getType());
						Property joinProp2 = new Property(rel2.getName() + "." + property2.getName(), property2.getType());
						if (!propertyUnion.contains(joinProp1)){
							propertyUnion.add(joinProp1);
							propetryNameChanged.add(property1);
						}
						if (!propertyUnion.contains(joinProp2)){
							propertyUnion.add(joinProp2);
							propetryNameChanged.add(property2);
						}
					}
				}
			}
		}
		
		for (Property property1 : properties1) {
			if (!propertyUnion.contains(property1)
				&& !propetryNameChanged.contains(property1)){
				propertyUnion.add(property1);
			}
		}
		
		for (Property property2 : properties2) {
			if (!propertyUnion.contains(property2)
				&& !propetryNameChanged.contains(property2)){
				propertyUnion.add(property2);
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
			if (propetryNameChanged.contains(prop)){
				newEntry.put(new Property(rel1.getName() + "." + prop.getName(), prop.getType()), entry1.get(prop));
			}else{
				newEntry.put(prop, entry1.get(prop));
			}
		}
		
		for (Property prop : entry2.keySet()) {
			if (!newEntry.containsKey(prop))
				if (propetryNameChanged.contains(prop)){
					newEntry.put(new Property(rel2.getName() + "." + prop.getName(), prop.getType()), entry2.get(prop));
				}else{
					newEntry.put(prop, entry2.get(prop));
				}
				
		}
		
		return newEntry;

	}

	@Override
	public String getName() {
		return name;
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

}
