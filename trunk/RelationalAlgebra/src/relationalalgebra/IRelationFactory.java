/**
 * Date: Apr 23, 2010
 * RelationalAlgebra - IRelationFactory.java
 */
package relationalalgebra;

import java.util.Set;

import conditions.ICondition;
import conditions.Property;

/**
 * @author Assaf Israel & Eli Nazarov
 *
 */
public interface IRelationFactory {
	
	public IRelation create(Set<Property> propertySet);
	public IRelation projection(IRelation relation, Set<String> labels);
	public IRelation selection(IRelation relation, ICondition condition);
	public IRelation cartesianProduct(IRelation relation1, IRelation relation2);
	public IRelation join(IRelation relation1, IRelation relation2);
}
