package il.cs.technion.c236700.hw4;

import il.cs.technion.c236700.hw4.parsers.BSParser;
import il.cs.technion.c236700.hw4.parsers.BookParser;

import java.util.Iterator;

import QueryImplementation.Query;
import RelationImplementation.Cell;
import RelationImplementation.Column;
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
		
		Column nameC = new Column("name",String.class);
		Column genreC = new Column("genre",String.class);
		Column priceC = new Column("price",Integer.class);
		Column soldCopiesC = new Column("soldCopies", Integer.class);
		
		/*
		 * Preparing the Book relation
		 */
		
		Relation bookRel = new Relation
		(
				nameC,
				genreC,
				priceC
		);
		
		try {
			for (Book b : new BookParser().parse()) {
				bookRel.addRow(new Row
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
		 * Preparing the Best-Seller relation
		 */
		
		Relation bestSellersRel = new Relation
		(
				nameC,
				soldCopiesC
		);
		
		try {
			for(BestSeller s : new BSParser().parse()) {
				bestSellersRel.addRow(new Row
					(
						new Cell(nameC, s.name),
						new Cell(soldCopiesC, s.copies)
					)
				);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		/*
		 * The correct query to answer this question is "SELECT NAME FROM Book Join BestSellers"
		 */
		Query query = Query.Create(bookRel).AntiJoin(bestSellersRel);
		Iterator<Row> iter = query.iterator();
		while(iter.hasNext()) {
			Row r = iter.next();
			System.out.println(new Book(
					(String)r.getValue(nameC),
					(String)r.getValue(genreC),
					(Integer)r.getValue(priceC)
				));
		}
	}

}
