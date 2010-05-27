package ExressionTreeNodes;

import QueryImplementation.Visitor;
import RelationImplementation.Relation;

/**
 * TreeNode is an abstract class which purpose is to define basic expression tree node. 
 * <p>
 * IMPORTANT:
 * ----------
 * 
 * Here are the properties of our expression tree:
 * 
 * 1) Every node can contain left and right children nodes (thus, it's binary expression tree)
 * 2) Node can represent either binary or unary operation or data node.
 * 3) In case of Data Node(There is DataNode class which extends TreeNode), it can contain Relation object only
 * 4) Data Nodes can be only at the LEAF LEVEL of the tree.
 * 5) In case of any binary operation, it's left and right sons will be either DataNodes, or Subtrees, which can be evaluated into Relation object
 * 6) In case of any unary operation, it can contain LEFT SON ONLY!!! (It's our convension - we could use Right Son only too).
 * 7) The query expression tree represents the query tree(in order to allow lazy evaluation).
 * 		The operations on the tree could be performed by creating visitor instance and passing it into the accept method of the relevant node of the tree.
 * 		The evaluation will be performed recursively on	the tree which root is that node.
 *
 * @see         CartesianProductNode
 * @see			DataNode
 * @see 		NaturalJoinNode
 * @see			ProjectionNode
 * @see 		WhereNode
 */
public abstract class TreeNode {
	protected TreeNode left = null, right = null;

	/**
	 * Method used for performing operation with the tree(operation should be implemented in a visitor instance) 
	 *
	 * @param  v  instance of the visitor class, which should implement relational algebra operators
	 * @return      Relation object, which is a result of performing recursive evaluation of expression subtree,
	 * 				such that the current node is its root.
	 * @see         Visitor
	 * @see 		Relation
	 */
	public abstract Relation accept(Visitor v);
	public TreeNode getLeftSon() 			{ return left; 			}
	public TreeNode getRightSon() 			{ return right;			}
	public void setLeftSon(TreeNode son) 	{ this.left = son; 		}
	public void setRightSon(TreeNode son)	{ this.right = son; 	}
}
