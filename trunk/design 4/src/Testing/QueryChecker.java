package Testing;

import QueryImplementation.Predicate;
import QueryImplementation.Query;
import RelationImplementation.Cell;
import RelationImplementation.Column;
import RelationImplementation.Relation;
import RelationImplementation.Row;

/**
 * Class for testing the API and showing the capabilities of Query object.
 * 
 * <p>
 * ----------------------------------------------------
 * 	README - HW4 - Relational Algebra
 * ----------------------------------------------------
 * <P>
 * We implemented the Relational algebra as following:
 * <P>
 * 1) We have Expression Tree(binary tree consisting of TreeNode instances(using Composite Design Pattern)).
 *  Expression tree encapsulates the query data. The purpose of such representation is to enable lazy evaluation
 *  of the query.
 * <P>
 * 2) Query object includes all the operators of relational algebra we implemented.
 * When the user wants to perform the query, he creates the instance of the Query object(by using static CREATE method) 
 * and then performs the operations which construct the query. Each operation returns Query instance, 
 * so you can compose operation on operation, as can be seen in our example.
 * <P>
 * 3) Expression Tree and Visitor classes implement Visitor Design Pattern. Thus, QueryVisitor class implements all
 *  the operations of relational algebra and inside traverses the expression tree to evaluate the query.
 * <P>
 * 4) Evaluation of the query is lazy, thus the query result can be evaluated only when calling Display method.
 * <P>
 * 5) The relation entity is built as following: think of a relation as a data table. It has columns, rows and cells.
 * <P>
 * 6) In order to implement the new relational algebra operator you should do the following:
 * <P>
 * 	6.1) Create class (call it yourNode) which extends TreeNode
 *  <P>
 * 	6.2) Implement accept(Visitor v) method in yourNode class.
 *  <P>
 * 	6.3) Create new abstract method in Visitor as following: Relation visit
 * 	     (yourNode node);
 *  <P>
 * 	6.4) In QueryVisitor class, override visit(youNode node) with the actual 
 * 	     implementation of your operator.
 *  <P>
 * 	6.5) In Query class, add new method(possibly, returning Query instance) to 
 * 	     allow the user using your new operator.
 *  <P>
 * 	6.6) Enjoy! 
 */
public class QueryChecker {

	public static void main(String[] args) {
		//Create columns for the first relation
		Column idC = new Column("ID", Integer.class);
		Column firstNameC = new Column("FirstName", String.class);
		//Create first relation with two columns
		Relation r = new Relation
		(
			idC,
			firstNameC
		);
		//Create rows for the first relation - there are two options to do it (see r1 and r2)
		Row r1 = new Row
		(
			new Cell(idC, 1),
			new Cell(firstNameC, "Moshe")
		);
		Row r2 = new Row();
		r2.setValue(idC, 2);
		r2.setValue(firstNameC, "David");
		
		//Add rows into the first relation
		r.addRows(r1, r2);
		
		//Create the second relation and add rows into it
		Relation numbers = new Relation(new Column(idC), new Column(firstNameC));
		for(int i=0; i < 20; i++) {
			numbers.addRow(new Row
							(
								new Cell(idC, i),
								new Cell(firstNameC, "Moshe")
							)
						  );
		}
		
		//Perform some query
		Query.Create(r).Where(new Predicate()
								{
									@Override
									public boolean match(Row r) {
										return ((Integer)r.getValue("ID")) > 0;
									}
								}).CrossJoin(numbers)
								  .Select("A.ID", "A.FirstName", "B.ID", "B.FirstName")
								  .Display();
		
		//And another query...
		Query.Create(r).Where(new Predicate()
								{
									@Override
									public boolean match(Row r) {
										return ((Integer)r.getValue("ID")) % 2 == 1;
									}
								}).NaturalJoin(numbers)
								  .Select("ID", "FirstName")
								  .Display();
		
		//Now let's perform Wikipedia example of RA and Natural Join:
		Relation empl = new Relation();
		empl.addColumn("Name", String.class);
		empl.addColumn("EmpId", Integer.class);
		empl.addColumn("DeptName", String.class);
		
		Relation dept = new Relation();
		dept.addColumn("DeptName", String.class);
		dept.addColumn("Manager", String.class);
		
		//First option - create rows and add them to the relation straight away
		empl.addRows(	new Row
						(
								new Cell("Name", "Harry"),
								new Cell("EmpId", 3415),
								new Cell("DeptName", "Finance")
						),
						new Row
						(
								new Cell("Name", "Sally"),
								new Cell("EmpId", 2241),
								new Cell("DeptName", "Sales")
						),
						new Row
						(
								new Cell("Name", "George"),
								new Cell("EmpId", 3401),
								new Cell("DeptName", "Finance")
						),
						new Row
						(
								new Cell("Name", "Harriet"),
								new Cell("EmpId", 2202),
								new Cell("DeptName", "Sales")
						)
					);
		//Second option to create the rows
		Row d1 = dept.createRow();
		d1.setValue("DeptName", "Finance");
		d1.setValue("Manager", "George");

		Row d2 = dept.createRow();
		d2.setValue("DeptName", "Sales");
		d2.setValue("Manager", "Harriet");

		Row d3 = dept.createRow();
		d3.setValue("DeptName", "Production");
		d3.setValue("Manager", "Charles");

		dept.addRows(d1, d2, d3);
		
		System.out.println("Employee NATURAL JOIN Departments");
		Query.Create(empl).NaturalJoin(dept).Display();
	}
}
