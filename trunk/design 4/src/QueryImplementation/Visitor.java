package QueryImplementation;

import ExressionTreeNodes.AntiJoinNode;
import ExressionTreeNodes.CartesianProductNode;
import ExressionTreeNodes.DataNode;
import ExressionTreeNodes.NaturalJoinNode;
import ExressionTreeNodes.ProjectionNode;
import ExressionTreeNodes.TreeNode;
import ExressionTreeNodes.WhereNode;
import RelationImplementation.Relation;

/**
 * Visitor is an abstract class which defines method overloadings for
 * every type of TreeNode.
 * 
 * <p>
 * In case you add new type of TreeNode, you should add
 * new method: public abstract Relation visit(YourTypeTreeNode node);
 * and then implement it in concrete visitor.
 * 
 * <p>
 * The purpose of the visitor is to encapsulate the logic of traversing
 * the tree. Every visit method returns Relation object in order to enable
 * recursive evaluation of the tree nodes. 
 * 
 * @see         QueryVisitor
 * @see			Relation
 * @see 		Query
 */
public abstract class Visitor {
	public abstract Relation visit(TreeNode node);
	public abstract Relation visit(CartesianProductNode node);
	public abstract Relation visit(DataNode node);
	public abstract Relation visit(NaturalJoinNode node);
	public abstract Relation visit(ProjectionNode node);
	public abstract Relation visit(WhereNode node);
	public abstract Relation visit(AntiJoinNode node);
}

