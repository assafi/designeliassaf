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
import java.util.Set;

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
	private final Set<Property> properties;
	private final String name;
	private JFrame frame = new JFrame();
	private static Object lock = new Object();


	
	public BasicRelation(String name, Set<Property> properties) {
		
		this.properties = properties;
		this.name = name;
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
			for (Object object : entry.values()) {
				rowData[i][j++] = object;
			}
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
	public Set<Property> getProperties() {
		return properties;
	}

	@Override
	public String getName() {
		return name;
	}
}
