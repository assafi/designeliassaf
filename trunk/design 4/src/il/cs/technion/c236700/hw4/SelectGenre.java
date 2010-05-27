package il.cs.technion.c236700.hw4;

import il.cs.technion.c236700.hw4.parsers.BookParser;

import java.util.Iterator;

import QueryImplementation.Predicate;
import QueryImplementation.Query;
import RelationImplementation.Cell;
import RelationImplementation.Column;
import RelationImplementation.Relation;
import RelationImplementation.Row;

public class SelectGenre {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Wrong number of arguments");
			System.exit(-1);
		}
		
		/*
		 * Preparing the Book relation
		 */
		
		Column nameC = new Column("name",String.class);
		Column genreC = new Column("genre",String.class);
		Column priceC = new Column("price",Integer.class);
		
		Relation rel = new Relation
		(
				nameC,
				genreC,
				priceC
		);
		
		/*
		 * Loading data into relation
		 */
		try {
			for (Book b : new BookParser().parse()) {
				rel.addRow(new Row
					(
						new Cell(nameC, b.name),
						new Cell(genreC, b.genre),
						new Cell(priceC, b.price)
					)
				);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		/*
		 * Relation is ready. next - selection
		 */

		final String genreSelected = new String(args[1]);
		Query query = Query.Create(rel).Where(new Predicate()
			{
				@Override
				public boolean match(Row r) {
					return ((String)r.getValue("genre")).equals(genreSelected);
				}
			});
		
		/*
		 * Printing the result
		 */
		Iterator<Row> iter = query.iterator();	//iterator support was added to the API implementation
		while (iter.hasNext()) {
			Row row = iter.next();
			System.out.println(new Book(
					(String)row.getValue("name"),
					(String)row.getValue("genre"),
					(Integer)row.getValue("price")
					));
		}
		
	}
}
