package ExressionTreeNodes;

import QueryImplementation.Predicate;
import QueryImplementation.Visitor;
import RelationImplementation.Relation;

/**
 * Class which purpose is to reflect selection operation on relation, in the expression tree.
 * User should specify the predicate, by which selection filtering will be performed.
 * <p>
 * ASSUMPTION: the left son of this node will be subtree, which can be evaluated
 * 				to relation, on which selection will be performed when evaluating the query 
 *
 * @see         TreeNode
 * @see			Relation
 * @see 		Visitor
 * @see			Predicate
 */
public class WhereNode extends TreeNode {

	private Predicate pred;
	
	public WhereNode(Predicate p) 			{ this.pred = p;		}
	
	public void setPredicate(Predicate p) 	{ this.pred = p;		}
	public Predicate getPredicate() 		{ return this.pred; 	}
	
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
