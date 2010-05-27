package RelationImplementation;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Represents the relation on which queries will be performed
 * 
 * @see         Column
 * @see			Row
 * @see 		Cell
 */
public class Relation {
	private TreeSet<Column> columns = new TreeSet<Column>();
	private List<Row> rows = new ArrayList<Row>();
	
	public Relation() {
		
	}
	public Relation(Column... columns) {
		for(Column c:columns) {
			this.columns.add(c);
		}
	}
	
	public Row createRow() 									{	return new Row(this);							}
	public boolean addColumn(Column c) 						{	return columns.add(c);							}
	public boolean addColumn(String name, Class<?> type) 	{	return this.addColumn(new Column(name, type));	}
	public Column[] getColumns() 							{	return columns.toArray(new Column[0]);			}
	public Row[] getRows() 									{	return this.rows.toArray(new Row[0]);			}
	public void addRow(Row r) 								{	rows.add(r); r.setRelation(this);				}
	//Only package visible
	void deleteRow(Row r) 									{	rows.remove(r);									}
	
	public void addRows(Row... rows) {
		for(Row row:rows) {
			this.addRow(row);
		}
	}
	
	public boolean hasColumn(Column c) {
		for(Column column:columns) {
			if (column.equals(c)) {
				return true;
			}
		}
		return false;
	}
}
