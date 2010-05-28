package il.cs.technion.c236700.hw4;

import java.util.Iterator;

import QueryImplementation.Query;
import RelationImplementation.Relation;
import RelationImplementation.Row;

public class BestSellers {
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
		 * The correct query to answer this question is 
		 * "SELECT NAME FROM (Book NaturalJoin BestSellers)"
		 */
		Query query = Query.Create(bookRel).NaturalJoin(bestSellersRel).Select(Utils.NAME);
		Iterator<Row> iter = query.iterator();
		while(iter.hasNext()) {
			System.out.println(iter.next().getValue(Utils.NAME));
		}
	}
}
