package QueryImplementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

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
 * Represent relational algebra query performed by the user.
 * Query outputs can be composed to create more complex query.
 * See examples in QueryChecker class.
 * 
 * @see			Relation
 */
public class Query {

	private QueryVisitor qv = new QueryVisitor();
	private TreeNode root;
	
	//Private constructor - see static Create method for explanations 
	private Query(Relation r) {	root = new DataNode(r);	}
	
	//We decided to use static creation method for Query object
	//in order to make the query more elegant, and right.
	//Just see the example of using and creating the query for more
	//clarifications
	public static Query Create(Relation r) {
		return new Query(r);
	}
	public Query CrossJoin(Relation r) {
		DataNode right = new DataNode(r);
		CartesianProductNode cp = new CartesianProductNode();
		cp.setLeftSon(root);
		cp.setRightSon(right);
		this.root = cp;
		return this;
	}
	public Query NaturalJoin(Relation r) {
		DataNode right = new DataNode(r);
		NaturalJoinNode nj = new NaturalJoinNode();
		nj.setLeftSon(root);
		nj.setRightSon(right);
		this.root = nj;
		return this;
	}
	public Query Select(String... columnNames) {
		Column[] columns = new Column[columnNames.length];
		for(int i=0; i<columnNames.length; i++) {
			try {
				columns[i] = new Column(columnNames[i]);
			} catch (Exception e) {
				columns[i] = null;
			}
		}
		return this.Select(columns);
	}
	public Query Select(Column... columns) {
		ProjectionNode node = new ProjectionNode(columns);
		node.setLeftSon(root);
		this.root = node;
		return this;
	}
	public Query Where(Predicate p) {
		WhereNode node = new WhereNode(p);
		node.setLeftSon(root);
		this.root = node;
		return this;
	}
	public void Display() {
		Relation result = this.root.accept(qv);
		
		if (result == null)
		{
			System.out.println("NO RECORDS...");
			return;
		}

		Column[] columns = result.getColumns();
		if (columns == null || columns.length == 0) {
			System.out.println("NO COLUMNS, THUS NO RECORDS");
			return;
		}
		
		/* Actually, print the table with the results: */

		//Print table layout
		createTableBorder(columns.length);
		for(Column c:columns) {
			System.out.format("%1$20.20S |\t", c.getName());	
		}
		System.out.println();
		createTableBorder(columns.length);
		
		//Print the results
		for(Row r:result.getRows()) {
			for(Column c:columns) {
				System.out.format("%1$20.20s |\t", r.getValue(c));
			}
			System.out.println();
		}
	}
	
	
	private void createTableBorder(int numOfColumns) {
		for(int i=2; i<numOfColumns*24;i++) {
			System.out.print("-");
		}
		System.out.println();
	}
	
	/*
	 * Added by Assaf
	 */
	public Iterator<Row> iterator() {
		Relation result = this.root.accept(qv);
		
		if (result == null)
		{
			System.out.println("NO RECORDS...");
			return null;
		}
		
		return Arrays.asList(result.getRows()).iterator();
	}
	
	public Query AntiJoin(Relation r) {
		DataNode right = new DataNode(r);
		AntiJoinNode nj = new AntiJoinNode();
		nj.setLeftSon(root);
		nj.setRightSon(right);
		this.root = nj;
		return this;
	}
}
