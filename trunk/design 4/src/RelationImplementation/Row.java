package RelationImplementation;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import Resources.ExceptionMessages;

/**
 * Implementation of data row
 * 
 * @see         Column
 * @see 		Relation
 * @see 		Cell
 */
public class Row {

	private Map<Column, Object> values = new TreeMap<Column, Object>();
	private Relation relation = null;
	
	//Client can't create the the instance without specifying the columns of table's schema.
	Row() {
		
	}
	public Row(Relation relation) {
		if (relation == null) return;
		
		this.relation = relation;
		Column[] columns = relation.getColumns();
		for(Column c:columns) {
			this.setNull(c);
		}
	}
	public Row(Cell... cells) {
		for(Cell cell:cells) {
			this.setValue(cell.getColumn(), cell.getValue());
		}
	}
	
	void setRelation(Relation r) 				{ this.relation = r;								}
	public Relation getRelation() 				{ return relation; 									}
	public Column[] getColumns() 				{ return values.keySet().toArray(new Column[0]);	}
	public Object getValue(Column c) 			{ return values.get(c);								}
	public Object getValue(String columnName) 	{ return this.getValue(new Column(columnName));		}
	public boolean isNull(Column c)				{ return values.get(c) != null;						}
	public boolean isNull(String columnName) 	{ return this.isNull(new Column(columnName));		}
	
	public void setNull(Column c)				{ values.put(c, null);								}
	public void delete() {
		if (relation != null) {
			this.relation.deleteRow(this);	
		}
	}
	public void setValues(Dictionary<Column, Object> values) {
		Enumeration<Column> columns = values.keys();
		while(columns.hasMoreElements()) {
			Column c = columns.nextElement();
			this.setValue(c, values.get(c));
		}
	}
	
	/**
	 * For given row, specifies the value for input column
	 * 
	 * @see         Column
	 */
	public void setValue(String column, Object value) {
		this.setValue(new Column(column), value);
	}
	
	/**
	 * For given row, specifies the value for input column
	 * 
	 * @see         Column
	 */
	public void setValue(Column c, Object o) {
		if (Column.isValidValueType(c, o)) {
			values.put(c, o);
		}
		else {
			throw new IllegalArgumentException(ExceptionMessages.INVALID_OBJECT_TYPE_FOR_COLUMN);
		}
	}
	
	public boolean hasColumn(Column c) {
		return values.containsKey(c);
	}
	public static Row MergeRows(Row r1, Row r2, String leftColumnPrefix, String rightColumnPrefix) {
		Row res = new Row();
		for(Column c:r1.getColumns()) {
			res.values.put(new Column(leftColumnPrefix + c.getName()), r1.values.get(c));
		}
		for(Column c:r2.getColumns()) {
			res.values.put(new Column(rightColumnPrefix + c.getName()), r2.values.get(c));
		}
		return res;
	}
	
	public static Row MergeRowsByCommonColumns(Row r1, Row r2, List<Column> commonColumns) {
		Row res = new Row();
		
		//Find similar columns(by column name)
		Column[] leftColumns = r1.getColumns();
		Column[] rightColumns = r2.getColumns();
		
		boolean shouldJoin = true;
		for(Column column:commonColumns) {
			if (!(r1.getValue(column).equals(r2.getValue(column)))) {
				shouldJoin = false;
				break;
			}
		}
		
		//Two rows aren't equal on common columns, thus we can't join them
		if (!shouldJoin) return null;
		
		//Here we know that both rows are equal on all the common columns, so we join the into one row
		for(Column c:leftColumns) {
			res.setValue(c, r1.getValue(c));
		}
		//Add new values by columns(those which doesn't exist in the left row)
		for(Column c:rightColumns) {
			if (!commonColumns.contains(c)) {
				res.setValue(c, r2.getValue(c));
			}
		}
		
		return res;
	}
}
