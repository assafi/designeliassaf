/**
 * Date: Apr 24, 2010
 * RelationalAlgebra - CartesianRelation.java
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
public class CartesianRelation implements IRelation {

	private final String name;
	private final IRelation rel1;
	private final IRelation rel2;
	private IRelation evaluatedRelation;
	
	private boolean evaluated = false;
	
	// Stores the properties that have the same name in rel1 and rel2
	private Set<Property> equalNameProperties = new HashSet<Property>(); 
	
	private Queue<Map<Property, Object>> delayedTasks = new ArrayDeque<Map<Property,Object>>();
	
	public CartesianRelation(String name, IRelation rel1, IRelation rel2){
		this.name = name;
		this.rel1 = rel1;
		this.rel2 = rel2;
		
		this.evaluatedRelation = new BasicRelation(name, getCartesianProperties(rel1,rel2));
	}
	
	private Set<Property> getCartesianProperties(IRelation rel1, IRelation rel2) {
		
		Set<Property> properties1 = rel1.getProperties();
		Set<Property> properties2 = rel2.getProperties();
		Set<Property> cartesianProperties = new HashSet<Property>();
		
		for (Property propertyRel1 : properties1) {
			/* 
			 * if the two relations have property with same name
			 * we should create two properties. 
			 */
			for (Property propertyRel2 : properties2) {
				
				if(propertyRel2.getName().equals(propertyRel1.getName())){
					cartesianProperties.add(new Property(rel1.getName() + "." + propertyRel1.getName(), propertyRel1.getClass()));
					cartesianProperties.add(new Property(rel2.getName() + "." + propertyRel2.getName(), propertyRel2.getClass()));
					equalNameProperties.add(propertyRel1);
					equalNameProperties.add(propertyRel2);		
				}
				else{
					cartesianProperties.add(propertyRel1);
					// Avoid duplication
					if(!cartesianProperties.contains(propertyRel2)){
						cartesianProperties.add(propertyRel2);
					}
				}
			}
		}
		
		return cartesianProperties;
		
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
		calcCaresianData(rel1.evaluate(),rel2.evaluate());
		
		while (!delayedTasks.isEmpty()) {
			evaluatedRelation.add(delayedTasks.poll());
		}
		
		evaluated = true;
		return evaluatedRelation;
	}

	// TODO see if this could be done better and if we can remove the equalNameProperties set
	private void calcCaresianData(IRelation evaluatedRel1, IRelation evaluatedRel2) {
		Iterator<Map<Property, Object>> iterRel1 = evaluatedRel1.iterator();
		/*
		 * For each row in the first Relation
		 * add all rows from the second relation
		 */
		while(iterRel1.hasNext()){
			Map<Property, Object> entryRel1 = iterRel1.next();
			Map<Property, Object> cartesianEntry = extractEntry(entryRel1);

			/*
			 * combine the data from the second Relation
			 */
			Iterator<Map<Property, Object>> iterRel2 = evaluatedRel2.iterator();
			while(iterRel2.hasNext()){
				Map<Property, Object> entryRel2 = iterRel1.next();
				for (Property property : entryRel2.keySet()) {
					if(equalNameProperties.contains(property)){
						cartesianEntry.put(new Property(rel2.getName() + "." + property.getName(), property.getClass()), 
										   entryRel2.get(property));
					}
				}
				evaluatedRelation.add(cartesianEntry);
				cartesianEntry = extractEntry(entryRel1);
			}
			
		}
		
	}

	private Map<Property, Object> extractEntry(Map<Property, Object> entryRel1) {
		Map<Property, Object> newEntry = new HashMap<Property, Object>(); 
		for (Property property : entryRel1.keySet()) {
			if(equalNameProperties.contains(property)){
				newEntry.put(new Property(rel1.getName() + "." + property.getName(), property.getClass()), 
								   entryRel1.get(property));
			}
		}
		return newEntry;
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

	@Override
	public String getName() {
		return name;
	}

}