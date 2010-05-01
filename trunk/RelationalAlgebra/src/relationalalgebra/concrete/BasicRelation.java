/**
 * Date: Apr 23, 2010
 * RelationalAlgebra - BasicRelation.java
 */
package relationalalgebra.concrete;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import relationalalgebra.IRelation;
import conditions.Property;

/**
 * @author Assaf Israel & Eli Nazarov
 *
 */
public class BasicRelation implements IRelation{

	private List<Map <Property, Object>> table = new ArrayList<Map<Property,Object>>();
	private final List<Property> properties;
	private final String name;
	private JFrame frame = new JFrame();
	private static Object lock = new Object();


	
	public BasicRelation(String name, List<Property> properties) {
		
		if(null == properties || null == name){
			throw new IllegalArgumentException("Parameters can't be null");
		}
		
		if(!checkProperties(properties)){
			throw new IllegalArgumentException("Different properties with same name are not allowd");
		}
		
		this.properties = properties;
		this.name = name;
	}

	private boolean checkProperties(List<Property> properties) {
		Property[] propArr = new Property[properties.size()];
		propArr = properties.toArray(propArr);
		for(int i = 0; i < propArr.length-1; i++){
			for(int j = i+1; j < propArr.length; j++){
				if(propArr[i].getName().equals(propArr[j].getName())
					|| propArr[i].equals(propArr[j])){
					return false;
				}
			}
		}
		
		return true;
		
	}

	@Override
	public boolean add(Map<Property, Object> entry) {
		/*
		 * Check if the properties in the entry 
		 * are equal to the properties of therelation	
		 */
		for (Property property : entry.keySet()) {
			if (!properties.contains(property))
				throw new IllegalArgumentException("Illigal entry. Properties do not match the Relation definition");
		}
		
		for (Property property : properties) {
			if (!entry.keySet().contains(property))
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
		//JFrame frame = new JFrame();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    Object rowData[][] = new Object[table.size()][properties.size()];
	    Object columnNames[] = new Object[properties.size()];
	    
	    int i = 0;
	    for (Property prop : properties) {
			columnNames[i++] = prop.getName();
		}
	    
	    i = 0;
	    int j = 0;
	    
	    for (Map <Property, Object> entry : table) {
	    	for (Property property : properties) {
				rowData[i][j++] = entry.get(property);
			}
//			for (Object object : entry.values()) {
//				rowData[i][j++] = object;
//			}
			j = 0;
			i++;
		}
	    
	    JTable table = new JTable(rowData, columnNames);

	    JScrollPane scrollPane = new JScrollPane(table);
	    frame.add(scrollPane, BorderLayout.CENTER);
	    frame.setSize(300, 150);
	    frame.setVisible(true);
	    
	    Thread t = new Thread() {
	    	public void run() {
	    		synchronized(lock) {
	    			while (frame.isVisible()){
	    				try {
	    					lock.wait();
	    				} catch (InterruptedException e) {
	    					e.printStackTrace();
	    				}
	    			}
	    		}
	    	}
	    };
	    t.start();
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
	public List<Property> getProperties() {
		return properties;
	}

	@Override
	public String getName() {
		return name;
	}
}
