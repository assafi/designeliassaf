package ExressionTreeNodes;

import QueryImplementation.Visitor;
import RelationImplementation.Relation;

/**
 * Class which holds the relation which will be then used to evaluate the query
 * 
 * <p>
 * ASSUMPTION: nodes of type DataNode should be at the LEAF LEVEL of the expression tree
 *
 * @see         TreeNode
 * @see			Relation
 * @see 		Visitor
 */
public class DataNode extends TreeNode {

	private Relation r;
	
	public DataNode(Relation r) 		{	this.r = r;				}
	
	public void setRelation(Relation r) {	this.r = r;				}
	public Relation getRelation() 		{	return this.r;			}
	
	/**
	 * Standard implementation of double dispatch - deliver "this" to visitor's visit method.
	 * 
	 * @see         TreeNode
	 * @see			Relation
	 * @see 		Visitor
	 */
	@Override
	public Relation accept(Visitor v)	{ 	return v.visit(this);	}

}
