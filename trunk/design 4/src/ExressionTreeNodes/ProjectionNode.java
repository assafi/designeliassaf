package ExressionTreeNodes;

import java.util.HashSet;

import QueryImplementation.Visitor;
import RelationImplementation.Column;
import RelationImplementation.Relation;

/**
 * Class which purpose is to reflect Projection operation on relation, in the expression tree.
 * In order to perform projection, one should supply the Column instances, specifying which columns
 * should be included in projection.
 * <p>
 * ASSUMPTION: the left son of this node will be subtree, which can be evaluated
 * 				to relation, on which projection will be performed when evaluating the query 
 *
 * @see         TreeNode
 * @see			Relation
 * @see 		Visitor
 */
public class ProjectionNode extends TreeNode {

	private HashSet<Column> selectColumns = new HashSet<Column>();
	
	public ProjectionNode(Column[] sel) {
		for(Column c:sel) {
			this.selectColumns.add(c);
		}
	}
	
	public Column[] getColumns() 			{ return selectColumns.toArray(new Column[0]);	}
	public void AddColumn(Column c) 		{ this.selectColumns.add(c);					}
	
	/**
	 * Standard implementation of double dispatch - deliver "this" to visitor's visit method.
	 * 
	 * @see         TreeNode
	 * @see			Relation
	 * @see 		Visitor
	 */
	@Override
	public Relation accept(Visitor v)		{ return v.visit(this);							}

}

