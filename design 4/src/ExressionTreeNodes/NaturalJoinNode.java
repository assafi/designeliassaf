package ExressionTreeNodes;

import QueryImplementation.Visitor;
import RelationImplementation.Relation;

/**
 * Class which purpose is to reflect natural join operation on two nodes, in the expression tree.
 * 
 * <p>
 * ASSUMPTION: the left and the right sons of this node will be subtrees, which can be evaluated
 * 				to two relations, on which natural join will be performed when evaluating the query 
 *
 * @see         TreeNode
 * @see			Relation
 * @see 		Visitor
 */
public class NaturalJoinNode extends TreeNode {
	/**
	 * Standard implementation of double dispatch - deliver "this" to visitor's visit method.
	 * 
	 * @see         TreeNode
	 * @see			Relation
	 * @see 		Visitor
	 */
	@Override
	public Relation accept(Visitor v)		{ return v.visit(this);	}

}
