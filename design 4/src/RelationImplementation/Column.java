package RelationImplementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import Resources.ExceptionMessages;

/**
 * Represents data column for given relation. Implements Comparable interface
 * in order to allow the users using String in a similar way to Column instances.
 * Contains columnName and it's type(currently we support only Integer, Boolean and String).
 * 
 * @see         Row
 * @see 		Relation
 * @see 		Cell
 * 
 */
public class Column implements Comparable<Column> {
	private String name;
	private Class<?> type;

	public Column(Column c) {
		if (c == null)
			throw new NullPointerException(Resources.ExceptionMessages.INVALID_COLUMN_OBJECT);
		
		this.name = c.name;
		this.type = c.type;
	}
	public Column()	{}
	public Column(String name) {
		this(name, String.class);
	}
	public Column(String name, Class<?> type) {
		ensureType(type);
		
		this.name = name;
		this.type = type;
	}

	public String getName() 	{ return name; }
	public Class<?> getType() 	{ return type; }

	public void setName(String name) {
		this.name = name;
	}
	public void setType(Class<?> type) {
		ensureType(type);
		this.type = type;
	}

	@Override
	public boolean equals(Object obj) {
		try {
			Column c = (Column)obj;
			return this.getName().equals(c.getName()) && this.getType().equals(c.getType());
		}
		catch (Exception e) {
			return false;
		}
	}

	public static boolean isValidValueType(Column c, Object o) {
		if (c == null) return false;
		if (o == null) return true;
		
		return c.getType().equals(o.getClass());
	}
	@Override
	public int compareTo(Column c) {
		if (c == null) return 1;
		return this.getName().compareToIgnoreCase(c.getName());
	}
	
	public static Column[] mergeColumns(Column[] columnsLeft, Column[] columnsRight) {
		ArrayList<Column> columns = new ArrayList<Column>();
		for(Column c:columnsLeft) {
			columns.add(c);	
		}
		for(Column c:columnsRight) {
			columns.add(c);
		}
		return columns.toArray(new Column[0]);
	}
	public static Column[] union(Column[] columnsLeft, Column[] columnsRight) {
		HashSet<Column> columns = new HashSet<Column>();
		for(Column c:columnsLeft) {
			columns.add(c);
		}
		List<Column> common = Arrays.asList(intersection(columnsLeft, columnsRight));
		
		for(Column c:columnsRight) {
			if (!common.contains(c)) {
				columns.add(c);
			}
		}
		return columns.toArray(new Column[0]);
	}
	public static Column[] intersection(Column[] columnsLeft, Column[] columnsRight) {
		//Find similar columns(by column name)
		HashSet<Column> commonColumns = new HashSet<Column>();
		for(Column l:columnsLeft) {
			for(Column r:columnsRight) {
				if (l.equals(r)) {
					commonColumns.add(l);
					continue;
				}
			}
		}
		return commonColumns.toArray(new Column[0]);
	}

	private void ensureType(Class<?> type) {
		if (type != Integer.class &&
			type != String.class &&
			type != Boolean.class)
		{
			throw new IllegalArgumentException(ExceptionMessages.INVALID_PROPERTY_TYPE);
		}
	}
}
