package il.cs.technion.c236700.hw4;


import java.util.Iterator;

import QueryImplementation.Predicate;
import QueryImplementation.Query;
import RelationImplementation.Relation;
import RelationImplementation.Row;

public class SelectGenre {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Wrong number of arguments");
			System.exit(-1);
		}
		
		Relation rel = Utils.loadBooks(args[0]);
		
		/*
		 * Relation is ready. next - selection
		 */
		Query query = select(rel,args[1]);
		
		/*
		 * Printing the result
		 */
		Iterator<Row> iter = query.iterator();	//iterator support was added to the given API implementation
		while (iter.hasNext()) {
			Row row = iter.next();
			System.out.println(new Book(
					(String)row.getValue(Utils.NAME),
					(String)row.getValue(Utils.GENRE),
					(Integer)row.getValue(Utils.PRICE)
					));
		}
		
	}

	private static Query select(Relation rel, final String genreSelected) {
		return Query.Create(rel).Where(new Predicate()
			{
				@Override
				public boolean match(Row r) {
					return ((String)r.getValue(Utils.GENRE)).equals(genreSelected);
				}
			});
	}
}
