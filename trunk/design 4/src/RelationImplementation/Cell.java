package RelationImplementation;

/**
 * Encapsulates the cell of the table - given a row, we have a value by a given column
 * 
 * @see         Column
 * @see 		Row
 * @see			Relation
 */
public class Cell {
	private Column column;
	private Object value;
	
	public Cell(Column c, Object v) {
		this.setColumn(c);
		this.setValue(v);
	}
	
	public Cell(String columnName, Object v) {
		this(new Column(columnName, v.getClass()), v);
	}

	public Column getColumn() 		{ return this.column; 	}
	public Object getValue()  		{ return this.value;  	}
	public void setColumn(Column c) {	this.column = c;	}
	public void setValue(Object v) 	{	this.value = v;		}
}
