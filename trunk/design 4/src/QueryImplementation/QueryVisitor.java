package QueryImplementation;

import java.util.ArrayList;
import java.util.Arrays;

import ExressionTreeNodes.AntiJoinNode;
import ExressionTreeNodes.CartesianProductNode;
import ExressionTreeNodes.DataNode;
import ExressionTreeNodes.NaturalJoinNode;
import ExressionTreeNodes.ProjectionNode;
import ExressionTreeNodes.TreeNode;
import ExressionTreeNodes.WhereNode;
import RelationImplementation.Column;
import RelationImplementation.Relation;
import RelationImplementation.Row;

/**
 * QueryVisitor is a concrete implementation of the Visitor class. Here all
 * relational algebra methods are implemented
 * 
 * @see Query
 * @see Relation
 */
public class QueryVisitor extends Visitor {

	/*
	 * In case of performing join on columns with the same names, we add prefix
	 * to the names of the columns in order to distinct between them
	 */
	private final String LEFT_TABLE_PREFIX = "A.";
	private final String RIGHT_TABLE_PREFIX = "B.";

	// The following method shouldn't be implemented, as the expression tree
	// should not include direct TreeNode, only entities which extend this
	// class.
	@Override
	public Relation visit(TreeNode node) {
		return null;
	}

	@Override
	public Relation visit(CartesianProductNode node) {
		if (node == null)
			return null;

		Relation left = node.getLeftSon().accept(this);
		Relation right = node.getRightSon().accept(this);

		Relation result = new Relation();

		// Create schema of joined relation
		for (Column c : left.getColumns()) {
			Column resColumn = new Column(c);
			resColumn.setName(LEFT_TABLE_PREFIX + resColumn.getName());
			result.addColumn(resColumn);
		}

		for (Column c : right.getColumns()) {
			Column resColumn = new Column(c);
			resColumn.setName(RIGHT_TABLE_PREFIX + resColumn.getName());
			result.addColumn(resColumn);
		}

		// Actually perform cross join between two relations
		for (Row rLeft : left.getRows()) {
			for (Row rRight : right.getRows()) {
				Row newR = Row.MergeRows(rLeft, rRight, LEFT_TABLE_PREFIX,
						RIGHT_TABLE_PREFIX);
				result.addRow(newR);
			}
		}

		return result;
	}

	@Override
	public Relation visit(DataNode node) {
		return (node != null) ? node.getRelation() : null;
	}

	@Override
	public Relation visit(NaturalJoinNode node) {
		if (node == null)
			return null;

		Relation left = node.getLeftSon().accept(this);
		Relation right = node.getRightSon().accept(this);

		Column[] relationColumns = Column.union(left.getColumns(), right
				.getColumns());
		Column[] commonColumns = Column.intersection(left.getColumns(), right
				.getColumns());
		Relation result = new Relation(relationColumns);

		// Actually perform inner join between two relations
		for (Row rLeft : left.getRows()) {
			for (Row rRight : right.getRows()) {
				Row merged = Row.MergeRowsByCommonColumns(rRight, rLeft, Arrays
						.asList(commonColumns));
				if (merged != null) {
					result.addRow(merged);
				}
			}
		}

		return result;
	}

	@Override
	public Relation visit(ProjectionNode node) {
		if (node == null)
			return null;

		Relation left = node.getLeftSon().accept(this);
		ArrayList<Column> columns = new ArrayList<Column>();
		for (Column requested : node.getColumns()) {
			for (Column c : left.getColumns()) {
				if (c.getName().equalsIgnoreCase(requested.getName())) {
					columns.add(c);
					break;
				}
			}
		}

		Relation result = new Relation(columns.toArray(new Column[0]));

		for (Row row : left.getRows()) {
			Row r = result.createRow();
			for (Column column : columns) {
				r.setValue(column, row.getValue(column));
			}
			result.addRow(r);
		}

		return result;
	}

	@Override
	public Relation visit(WhereNode node) {
		if (node == null)
			return null;

		Relation left = node.getLeftSon().accept(this);
		Column[] columns = left.getColumns();
		Predicate p = node.getPredicate();
		Relation result = new Relation(columns);

		// Actually perform FILTER
		for (Row rLeft : left.getRows()) {
			if (p.match(rLeft)) {
				result.addRow(rLeft);
			}
		}

		return result;
	}

	@Override
	public Relation visit(AntiJoinNode node) {
		if (node == null)
			return null;

		Relation left = node.getLeftSon().accept(this);
		Relation right = node.getRightSon().accept(this);

		Column[] commonColumns = Column.intersection(left.getColumns(), right
				.getColumns());

		Relation result = new Relation(left.getColumns());

		for (Row leftRow : left.getRows()) {
			boolean candidate = true;
			for (Row rightRow : right.getRows()) {
				for (int i = 0; i < commonColumns.length; i++) {
					if (!leftRow.getValue(commonColumns[i]).equals(
							rightRow.getValue(commonColumns[i]))) {
						break;
					}
					if (i == commonColumns.length - 1) {
						candidate = false;
					}
				}
			}
			
			if (candidate) {
				result.addRow(leftRow);
			}
		}
		
		return result;
	}
}
