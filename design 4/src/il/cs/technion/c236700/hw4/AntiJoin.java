package il.cs.technion.c236700.hw4;

import java.util.Iterator;

import QueryImplementation.Query;
import RelationImplementation.Relation;
import RelationImplementation.Row;

public class AntiJoin {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Wrong number of arguments");
			System.exit(-1);
		}
		
		/*
		 * Prepare the two relations
		 */
		
		Relation bookRel = Utils.loadBooks(args[0]);
		
		Relation bestSellersRel = Utils.loadBestSellers(args[1]);
		
		/*
		 * The correct query to answer this question is "SELECT NAME FROM Book Join BestSellers"
		 */
		Query query = Query.Create(bookRel).AntiJoin(bestSellersRel);
		Iterator<Row> iter = query.iterator();
		while(iter.hasNext()) {
			Row r = iter.next();
			System.out.println(new Book(
					(String)r.getValue(Utils.NAME),
					(String)r.getValue(Utils.GENRE),
					(Integer)r.getValue(Utils.PRICE)
				));
		}
	}

}
