/**
 * 
 */
package il.cs.technion.c236700.hw4;

import il.cs.technion.c236700.hw4.parsers.BSParser;
import il.cs.technion.c236700.hw4.parsers.BookParser;
import RelationImplementation.Cell;
import RelationImplementation.Column;
import RelationImplementation.Relation;
import RelationImplementation.Row;

/**
 * This is a general utilities class which handles common static 
 * methods which are used in this assignment. its scope is default
 * because there is really no use for any of its methods outside 
 * this package. 
 * @author Assaf Israel
 *
 */
class Utils {

	static final String NAME = "name";
	static final String GENRE = "genre";
	static final String PRICE = "price";
	static final String SOLD_COPIES = "soldCopies";

	static Relation loadBooks(String xmlPath) {
		/*
		 * Preparing the Book relation
		 */
		
		Column nameC = new Column(NAME,String.class);
		Column genreC = new Column(GENRE,String.class);
		Column priceC = new Column(PRICE,Integer.class);
		
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
			for (Book b : new BookParser(xmlPath).parse()) {
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
		return rel;
	}

	static Relation loadBestSellers(String xmlPath) {
		
		Column nameC = new Column(NAME,String.class);
		Column soldCopiesC = new Column(SOLD_COPIES, Integer.class);
		
		Relation bestSellersRel = new Relation
		(
				nameC,
				soldCopiesC
		);
		
		try {
			for(BestSeller s : new BSParser(xmlPath).parse()) {
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
		return bestSellersRel;
	}
}
