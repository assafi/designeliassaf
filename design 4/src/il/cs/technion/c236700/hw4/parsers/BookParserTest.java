package il.cs.technion.c236700.hw4.parsers;

import il.cs.technion.c236700.hw4.BestSeller;
import il.cs.technion.c236700.hw4.Book;

import org.junit.Test;


public class BookParserTest {
	@Test
	public void testBooks() throws Exception{
		for ( Book b : new BookParser().parse() )
			System.out.println(b);
	}
	
	@Test
	public void testBestSellers() throws Exception{
		for(BestSeller s : new BSParser().parse())
			System.out.println(s);
	}
}
