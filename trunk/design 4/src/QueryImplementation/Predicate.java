package QueryImplementation;

import RelationImplementation.Relation;
import RelationImplementation.Row;

/**
 * This interface represents the predicate which should be given to the WHERE clause of Relational Algebra.
 * We didn't want to implement concrete class which implements Predicate interface, in order to make the
 * query more "dynamic". Thus, in case of calling WHERE clause of the query, implement anonymous type,
 * as can be seen in our tests.
 * 
 * @see         Query
 * @see			Relation
 */
public interface Predicate {
	

	/**
	 * This method will be called on every row of the relation on which you perform WHERE clause.
	 * Those rows for which the match method will return true, will form the result of the WHERE clause.
	 * 
	 * @see         Query
	 * @see			Relation
	 */
	boolean match(Row r);
}
